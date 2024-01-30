package com.september22nd.quadApp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class HelpDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9008489499493550891L;
	
	private enum Lang {
		ENGLISH,
		SPANISH,
	}
	
	private Lang lang = Lang.ENGLISH;
	
	private JTextArea textArea;
	
	private JScrollPane scrollPane;

	/**
	 * Create the dialog.
	 */
	public HelpDialog() {
		setBounds(100, 100, 380, 485);
		setTitle("Help");
		setResizable(false);
		setSize(new Dimension(640, 415));
		setPreferredSize(new Dimension(380, 485));
		getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(new LineBorder(new Color(171, 173, 179)));
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(110, 11, 500, 350);
		getContentPane().add(scrollPane);
		
		JLabel langLabel = new JLabel("Language");
		langLabel.setBounds(10, 16, 66, 14);
		getContentPane().add(langLabel);
		
		JRadioButton englishRadio = new JRadioButton("English");
		englishRadio.setBounds(10, 38, 78, 24);
		englishRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lang = Lang.ENGLISH;
				update();
			}
		});
		getContentPane().add(englishRadio);
		
		JRadioButton spanishRadio = new JRadioButton("Spanish");
		spanishRadio.setBounds(10, 71, 78, 24);
		spanishRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lang = Lang.SPANISH;
				update();
			}
		});
		getContentPane().add(spanishRadio);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(englishRadio);
		buttonGroup.add(spanishRadio);
		buttonGroup.setSelected(englishRadio.getModel(), true);
		
		update();
	}
	
	private void update() {
	    textArea.setText("");
	    String path = "";
	    switch (lang) {
	        case ENGLISH:
	            path = "help_en.txt";
	            break;
	        case SPANISH:
	            path = "help_es.txt";
	            break;
	    }
	    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
	        if (inputStream != null) {
	            try (Scanner fileIn = new Scanner(inputStream, "UTF-8")) {
	                while (fileIn.hasNextLine()) {
	                    String line = fileIn.nextLine() + "\n";
	                    textArea.append(line);
	                }
	                SwingUtilities.invokeLater(() -> {
	                    JViewport viewport = scrollPane.getViewport();
	                    Point viewPosition = viewport.getViewPosition();
	                    viewPosition.setLocation(0, 0);
	                    viewport.setViewPosition(viewPosition);
	                });
	            }
	        }
	        else {
	            System.err.println("Resource file not found: " + path);
	        }
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
}
