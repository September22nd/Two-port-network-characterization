package com.september22nd.quadApp.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1764919469280782641L;

	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Two Port Network Characterization");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu optionsMenu = new JMenu("Options...");
		optionsMenu.setMnemonic(KeyEvent.VK_O);
		JMenuItem helpItem = new JMenuItem("Help...");
		optionsMenu.add(helpItem);
		helpItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				putHelpDialog();
			}
		});
		JMenuItem infoItem = new JMenuItem("Info...");
		infoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				putInfoDialog();
			}
		});
		optionsMenu.add(infoItem);		
		menuBar.add(optionsMenu);
		
		add(new MainPanel());
		pack();
		
		setLocationRelativeTo(null);
	}
	
	private void putHelpDialog() {
		String str1 = "Puede hacer doble click sobre";
		String str2 = "las filas de la tabla para acceder";
		String str3 = "a información más detallada del";
		String str4 = "cuadripolo.";
		JLabel label1 = new JLabel(str1);
		JLabel label2 = new JLabel(str2);
		JLabel label3 = new JLabel(str3);
		JLabel label4 = new JLabel(str4);
		
		JDialog infoDialog = new JDialog();
		infoDialog.setTitle("Ayuda");
		infoDialog.setModal(true);
		infoDialog.setResizable(false);
		infoDialog.setLayout(new GridLayout(0, 1));
		infoDialog.add(label1);
		infoDialog.add(label2);
		infoDialog.add(label3);
		infoDialog.add(label4);
		infoDialog.pack();
		infoDialog.setLocationRelativeTo(infoDialog.getParent());
		infoDialog.setVisible(true);
	}
	
	private void putInfoDialog() {
		String str1 = "Autor: @september22nd en enero de 2024.";
		String str2 = "Cualquier sugerencia, reporte de error o insulto, al Discord @september22nd";
		String str3 = "Open source, cualquiera puede colaborar.";
		String str4 = "Repositorio: https://github.com/September22nd";
		JLabel label1 = new JLabel(str1);
		JLabel label2 = new JLabel(str2);
		JLabel label3 = new JLabel(str3);
		JLabel label4 = new JLabel(str4);
		
		JDialog infoDialog = new JDialog();
		infoDialog.setTitle("Información");
		infoDialog.setModal(true);
		infoDialog.setResizable(false);
		infoDialog.setLayout(new GridLayout(0, 1));
		infoDialog.add(label1);
		infoDialog.add(label2);
		infoDialog.add(label3);
		infoDialog.add(label4);
		infoDialog.pack();
		infoDialog.setLocationRelativeTo(infoDialog.getParent());
		infoDialog.setVisible(true);
	}

}
