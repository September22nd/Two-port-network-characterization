package com.september22nd.quadApp.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.september22nd.quadApp.quadLogic.AngUnit;
import com.september22nd.quadApp.quadLogic.CQuad;
import com.september22nd.quadApp.quadLogic.DisplayForm;

public abstract class AbstractParameterDetailPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7341639506024525851L;
	
	protected CQuad quad;
	protected boolean isDNE = false;
	
	protected DisplayForm displayForm = DisplayForm.RECT;
	
	protected AngUnit angUnit = AngUnit.DEG;
	
	protected final static String[] COMBO_OPTIONS  = {
		"°",
		"rad",
	};
	
	protected JTextField value11;
	protected JTextField value21;
	protected JTextField value22;
	protected JTextField value12;
	protected JLabel label11;
	protected JLabel label12;
	protected JLabel label21;
	protected JLabel label22;
	protected JComboBox<String> unitCombo11;
	protected JComboBox<String> unitCombo12;
	protected JComboBox<String> unitCombo21;
	protected JComboBox<String> unitCombo22;
	protected String[] comboOptions11;
	protected String[] comboOptions12;
	protected String[] comboOptions21;
	protected String[] comboOptions22;
	protected String labelSymbol;
	private JComboBox<String> angUnitCombo;
	
	/**
	 * Create the panel.
	 */
	protected AbstractParameterDetailPanel() {
		setLayout(null);
		
		label11 = new JLabel();
		label11.setBounds(10, 11, 46, 14);
		add(label11);
		label12 = new JLabel();
		label12.setBounds(250, 11, 46, 14);
		add(label12);
		label21 = new JLabel();
		label21.setBounds(10, 36, 46, 14);
		add(label21);
		label22 = new JLabel();
		label22.setBounds(250, 36, 46, 14);
		add(label22);
		
		value11 = new JTextField();
		value11.setEditable(false);
		value11.setBounds(42, 8, 145, 20);
		add(value11);
		value11.setColumns(10);
		value12 = new JTextField();
		value12.setEditable(false);
		value12.setColumns(10);
		value12.setBounds(282, 8, 145, 20);
		add(value12);
		value21 = new JTextField();
		value21.setEditable(false);
		value21.setColumns(10);
		value21.setBounds(42, 33, 145, 20);
		add(value21);
		value22 = new JTextField();
		value22.setEditable(false);
		value22.setColumns(10);
		value22.setBounds(282, 33, 145, 20);
		add(value22);
		
		JRadioButton rectangularRadio = new JRadioButton("Rectangular");
		rectangularRadio.setBounds(310, 58, 121, 24);
		rectangularRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayForm = DisplayForm.RECT;
				updateUnits(unitCombo11, value11, 0, 0);
				updateUnits(unitCombo12, value12, 0, 1);
				updateUnits(unitCombo21, value21, 1, 0);
				updateUnits(unitCombo22, value22, 1, 1);
			}
		});
		add(rectangularRadio);
		JRadioButton polarRadio = new JRadioButton("Polar");
		polarRadio.setBounds(241, 58, 55, 24);
		polarRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayForm = DisplayForm.POLAR;
				updateUnits(unitCombo11, value11, 0, 0);
				updateUnits(unitCombo12, value12, 0, 1);
				updateUnits(unitCombo21, value21, 1, 0);
				updateUnits(unitCombo22, value22, 1, 1);
			}
		});
		add(polarRadio);
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(polarRadio);
		radioGroup.add(rectangularRadio);
		radioGroup.setSelected(rectangularRadio.getModel(), true);
		
		angUnitCombo = new JComboBox<String>(COMBO_OPTIONS);
		angUnitCombo.setBounds(97, 58, 55, 25);
		angUnitCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAngUnits();
			}
		});
		add(angUnitCombo);
		
		JLabel comboBoxLabel = new JLabel("Angle units");
		comboBoxLabel.setBounds(20, 62, 69, 16);
		add(comboBoxLabel);
	}
	
	protected void addAllCombos() {
		unitCombo11 = new JComboBox<String>(comboOptions11);
		unitCombo11.setBounds(192, 7, 50, 22);
		unitCombo11.setSelectedIndex(2);
		add(unitCombo11);
		unitCombo12 = new JComboBox<String>(comboOptions12);
		unitCombo12.setBounds(432, 7, 50, 22);
		unitCombo12.setSelectedIndex(2);
		add(unitCombo12);
		unitCombo21 = new JComboBox<String>(comboOptions21);
		unitCombo21.setBounds(192, 32, 50, 22);
		unitCombo21.setSelectedIndex(2);
		add(unitCombo21);
		unitCombo22 = new JComboBox<String>(comboOptions22);
		unitCombo22.setBounds(432, 32, 50, 22);
		unitCombo22.setSelectedIndex(2);
		add(unitCombo22);
		
	}
	
	protected void addComboListeners() {
		unitCombo11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateUnits(unitCombo11, value11, 0, 0);
			}
		});
		unitCombo12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateUnits(unitCombo12, value12, 0, 1);
			}
		});	
		unitCombo21.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateUnits(unitCombo21, value21, 1, 0);
			}
		});	
		unitCombo22.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateUnits(unitCombo22, value22, 1, 1);
			}
		});
	}
	
	protected abstract void updateUnits(JComboBox<String> comboBox, JTextField textField, int i, int j);
	
	protected void addTextToLabels() {
		label11.setText(String.format("%s11", labelSymbol));
		label12.setText(String.format("%s12", labelSymbol));
		label21.setText(String.format("%s21", labelSymbol));
		label22.setText(String.format("%s22", labelSymbol));
	}
	
	private void updateAngUnits() {
		switch(angUnitCombo.getSelectedIndex()) {
		case 0:
			angUnit = AngUnit.DEG;
			break;
		case 1:
			angUnit = AngUnit.RAD;
		}
	}
	
}
