package com.september22nd.quadApp.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

public class DecPrecDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3440312840287865774L;

	public DecPrecDialog(MainPanelC mpc, double decPrec1Current, double decPrec2Current) {
		getContentPane().setLayout(null);
		setTitle("Decimal precision");
		setResizable(false);
		setModal(true);
		setSize(new Dimension(275, 150));
		setPreferredSize(new Dimension(365, 115));
		
		JLabel label1 = new JLabel("Numbers");
		label1.setBounds(12, 13, 57, 14);
		getContentPane().add(label1);
		
		JLabel label2 = new JLabel("Angles");
		label2.setBounds(22, 44, 57, 14);
		getContentPane().add(label2);
		
		SpinnerNumberModel snm1 = new SpinnerNumberModel(decPrec1Current, 0.0, 15.0, 1.0);
		SpinnerNumberModel snm2 = new SpinnerNumberModel(decPrec2Current, 0.0, 15.0, 1.0);
		
		JSpinner spinner1 = new JSpinner(snm1);
		spinner1.setBounds(75, 10, 44, 20);
		getContentPane().add(spinner1);
		
		JSpinner spinner2 = new JSpinner(snm2);
		spinner2.setBounds(75, 41, 44, 20);
		getContentPane().add(spinner2);
		
		JButton okButton = new JButton("OK");
		okButton.setBounds(96, 73, 51, 26);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mpc.numPrec = (int) (double) snm1.getNumber();
				mpc.angPrec = (int) (double) snm2.getNumber();
				mpc.reRenderTable();
				kill();
			}
		});
		getContentPane().add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(159, 73, 88, 26);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				kill();
			}
		});
		getContentPane().add(cancelButton);
	}
	
	private void kill() {
		this.dispose();
	}
}
