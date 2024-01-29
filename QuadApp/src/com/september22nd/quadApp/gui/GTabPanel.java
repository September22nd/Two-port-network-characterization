package com.september22nd.quadApp.gui;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.september22nd.quadApp.quadLogic.CQuad;
import com.september22nd.quadApp.quadLogic.Complex;
import com.september22nd.quadApp.quadLogic.DisplayForm;

public class GTabPanel extends AbstractParameterDetailPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 82859884206573279L;
	
	public GTabPanel(CQuad quad) {
		super();
		labelSymbol = "g";
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
				"E-6",
				"E-3",
				"E0",
				"E3",
				"E6",
		};
		comboOptions11 = comboOptionsConduct;
		comboOptions12 = comboOptionsNumb;
		comboOptions21 = comboOptionsNumb;
		comboOptions22 = comboOptionsResist;
		addAllCombos();
		try {
			String s11 = (displayForm == DisplayForm.RECT) ?
					String.format("%s", this.quad.getInvHybridParams().get(0, 0).toRectString()):
					String.format("%s", this.quad.getInvHybridParams().get(0, 0).toPolarString(angUnit));
			String s12 = (displayForm == DisplayForm.RECT) ?
					String.format("%s", this.quad.getInvHybridParams().get(0, 1).toRectString()):
					String.format("%s", this.quad.getInvHybridParams().get(0, 1).toPolarString(angUnit));
			String s21 = (displayForm == DisplayForm.RECT) ?
					String.format("%s", this.quad.getInvHybridParams().get(1, 0).toRectString()):
					String.format("%s", this.quad.getInvHybridParams().get(1, 0).toPolarString(angUnit));
			String s22 = (displayForm == DisplayForm.RECT) ?
					String.format("%s", this.quad.getInvHybridParams().get(1, 1).toRectString()):
					String.format("%s", this.quad.getInvHybridParams().get(1, 1).toPolarString(angUnit));
			value11.setText(s11);
			value12.setText(s12);
			value21.setText(s21);
			value22.setText(s22);
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
			Complex factor;
			String s = "";
			switch(comboBox.getSelectedIndex()) {
			case 4:
				factor = new Complex(0.000001, 0.0);
				s = (displayForm == DisplayForm.RECT) ?
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toRectString()):
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toPolarString(angUnit));
				break;
			case 3:
				factor = new Complex(0.001, 0.0);
				s = (displayForm == DisplayForm.RECT) ?
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toRectString()):
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toPolarString(angUnit));
				break;
			case 2:
				factor = new Complex(1.0, 0.0);
				s = (displayForm == DisplayForm.RECT) ?
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toRectString()):
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toPolarString(angUnit));
				break;
			case 1:
				factor = new Complex(1000.0, 0.0);
				s = (displayForm == DisplayForm.RECT) ?
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toRectString()):
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toPolarString(angUnit));
				break;
			case 0:
				factor = new Complex(1000000.0, 0.0);
				s = (displayForm == DisplayForm.RECT) ?
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toRectString()):
						String.format("%s", Complex.prod(this.quad.getInvHybridParams().get(i, j), factor).toPolarString(angUnit));
			}
			textField.setText(s);
		}
	}

}
