package com.september22nd.quadApp.gui;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.september22nd.quadApp.quadLogic.Quad;

public class HTabPanel extends AbstractParameterDetailPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4403349403779040072L;
	
	public HTabPanel(Quad quad) {
		super();
		labelSymbol = "h";
		this.quad = quad;
		addTextToLabels();
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
		comboOptions11 = comboOptionsResist;
		comboOptions12 = comboOptionsNumb;
		comboOptions21 = comboOptionsNumb;
		comboOptions22 = comboOptionsConduct;
		addAllCombos();
		try {
			value11.setText(String.format("%f", this.quad.getHybridParams().get11()));
			value12.setText(String.format("%f", this.quad.getHybridParams().get12()));
			value21.setText(String.format("%f", this.quad.getHybridParams().get21()));
			value22.setText(String.format("%f", this.quad.getHybridParams().get22()));
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
				textField.setText(String.format("%f", this.quad.getHybridParams().get(i, j) * 0.000001));
				break;
			case 3:
				textField.setText(String.format("%f", this.quad.getHybridParams().get(i, j) * 0.001));
				break;
			case 2:
				textField.setText(String.format("%f", this.quad.getHybridParams().get(i, j)));
				break;
			case 1:
				textField.setText(String.format("%f", this.quad.getHybridParams().get(i, j) * 1000.0));
				break;
			case 0:
				textField.setText(String.format("%f", this.quad.getHybridParams().get(i, j) * 1000000.0));
			}
		}
	}

}
