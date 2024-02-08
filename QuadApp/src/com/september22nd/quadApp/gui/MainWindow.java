package com.september22nd.quadApp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1764919469280782641L;
	
//	private final static String STATE_PATH = "./resources/state.txt";
	
	private MainPanelC mpc;
	
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Two Port Network Characterization");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu settingsMenu = new JMenu("Settings");
		JMenuItem decPrecMenuItem = new JMenuItem("Decimal precision...");
		settingsMenu.add(decPrecMenuItem);
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpWindowMenuItem = new JMenuItem("Help...");
		JMenuItem infoWindowMenuItem = new JMenuItem("Info...");
		helpMenu.add(helpWindowMenuItem);
		helpMenu.add(infoWindowMenuItem);
		
		mpc = new MainPanelC();
		add(mpc);
		
		decPrecMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showDecPrecDialog();
			}
		});
		helpWindowMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showHelpDialog();
			}
		});
		
		infoWindowMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showInfoDialog();
			}
		});
		
		menuBar.add(settingsMenu);
		menuBar.add(helpMenu);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	private double decPrec1Current = 2.0;
	private double decPrec2Current = 2.0;
	private void showDecPrecDialog() {
		DecPrecDialog dpd = new DecPrecDialog(mpc, decPrec1Current, decPrec2Current);
		dpd.setLocationRelativeTo(this);
		dpd.setVisible(true);
		decPrec1Current = mpc.numPrec;
		decPrec2Current = mpc.angPrec;
	}
	
	private void showHelpDialog() {
		HelpDialog hd = new HelpDialog();
		hd.setLocationRelativeTo(this);
		hd.setVisible(true);
	}
	
	private void showInfoDialog() {
		InfoDialog id = new InfoDialog();
		id.setLocationRelativeTo(this);
		id.setModal(true);
		id.setVisible(true);
	}
	
//	@Override
//	public void dispose() {
//		serialize();
//		super.dispose();
//	}
//	
//	private void serialize() {
//		File file = new File(STATE_PATH);
//		FileOutputStream fileOut = null;
//		ObjectOutputStream objOut = null;
//		try {
//			file.createNewFile();
//			fileOut = new FileOutputStream(STATE_PATH);
//			objOut = new ObjectOutputStream(fileOut);
//			objOut.writeObject(this);
//			objOut.close();
//			fileOut.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static MainWindow deserialize() {
//		FileInputStream fileIn = null;
//		ObjectInputStream objIn = null;
//		try {
//			fileIn = new FileInputStream(STATE_PATH);
//			objIn = new ObjectInputStream(fileIn);
//			MainWindow mw = (MainWindow) objIn.readObject();
//			fileIn.close();
//			objIn.close();
//			return mw;
//		}
//		catch (FileNotFoundException e) {
//			return null;
//		}
//		catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}
