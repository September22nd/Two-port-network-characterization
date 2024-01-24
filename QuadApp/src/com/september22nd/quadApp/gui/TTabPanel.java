package com.september22nd.quadApp.gui;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.september22nd.quadApp.quadLogic.Quad;

public class TTabPanel extends AbstractParameterDetailPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1538304320408447274L;
	
	public TTabPanel(Quad quad) {
		super();
		addTextToLabels();
		this.quad = quad;
		String[] comboOptionsResist = {
				"μΩ",
				"mΩ",
				"Ω",
				"kΩ",
				"MΩ",
		};
		String[] comboOptionsConduct = {
				"μS",
				"mS",
				"S",
				"kS",
				"Ms",
		};
		String[] comboOptionsNumb = {
				"*10^(-6)",
				"*10^(-3)",
				"*10^(0)",
				"*10^3",
				"*10^6",
		};
		comboOptions11 = comboOptionsNumb;
		comboOptions12 = comboOptionsResist;
		comboOptions21 = comboOptionsConduct;
		comboOptions22 = comboOptionsNumb;
		addAllCombos();
		try {
			value11.setText(String.format("%f", this.quad.getTransParams().get11()));
			value12.setText(String.format("%f", this.quad.getTransParams().get12()));
			value21.setText(String.format("%f", this.quad.getTransParams().get21()));
			value22.setText(String.format("%f", this.quad.getTransParams().get22()));
		}
		catch (NullPointerException e) {
			value11.setVisible(false);
			value12.setVisible(false);
			value21.setVisible(false);
			value22.setVisible(false);
			isDNE = true;
		}
		addComboListeners();

	}

	@Override
	protected void updateUnits(JComboBox<String> comboBox, JTextField textField, int i, int j) {
		if (!isDNE) {
			switch(comboBox.getSelectedIndex()) {
			case 4:
				textField.setText(String.format("%f", this.quad.getTransParams().get(i, j) * 0.000001));
				break;
			case 3:
				textField.setText(String.format("%f", this.quad.getTransParams().get(i, j) * 0.001));
				break;
			case 2:
				textField.setText(String.format("%f", this.quad.getTransParams().get(i, j)));
				break;
			case 1:
				textField.setText(String.format("%f", this.quad.getTransParams().get(i, j) * 1000.0));
				break;
			case 0:
				textField.setText(String.format("%f", this.quad.getTransParams().get(i, j) * 1000000.0));
			}
		}
	}
	
	@Override
	protected void addTextToLabels() {
		label11.setText("A");
		label12.setText("B");
		label21.setText("C");
		label22.setText("D");
	}

}
