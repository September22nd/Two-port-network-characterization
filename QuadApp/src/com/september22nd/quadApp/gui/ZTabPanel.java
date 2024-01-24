package com.september22nd.quadApp.gui;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.september22nd.quadApp.quadLogic.Quad;

public class ZTabPanel extends AbstractParameterDetailPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 672352011175098710L;
	
	public ZTabPanel(Quad quad) {
		super();
		labelSymbol = "z";
		addTextToLabels();
		this.quad = quad;
		String[] comboOptions = {
				"μΩ",
				"mΩ",
				"Ω",
				"kΩ",
				"MΩ",
		};
		comboOptions11 = comboOptions;
		comboOptions12 = comboOptions;
		comboOptions21 = comboOptions;
		comboOptions22 = comboOptions;
		addAllCombos();
		try {
			value11.setText(String.format("%f", this.quad.getOpenCircuitParams().get11()));
			value12.setText(String.format("%f", this.quad.getOpenCircuitParams().get12()));
			value21.setText(String.format("%f", this.quad.getOpenCircuitParams().get21()));
			value22.setText(String.format("%f", this.quad.getOpenCircuitParams().get22()));
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
				textField.setText(String.format("%f", this.quad.getOpenCircuitParams().get(i, j) * 0.000001));
				break;
			case 3:
				textField.setText(String.format("%f", this.quad.getOpenCircuitParams().get(i, j) * 0.001));
				break;
			case 2:
				textField.setText(String.format("%f", this.quad.getOpenCircuitParams().get(i, j)));
				break;
			case 1:
				textField.setText(String.format("%f", this.quad.getOpenCircuitParams().get(i, j) * 1000.0));
				break;
			case 0:
				textField.setText(String.format("%f", this.quad.getOpenCircuitParams().get(i, j) * 1000000.0));
			}
		}
	}
	
}
