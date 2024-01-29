package com.september22nd.quadApp.gui;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import com.september22nd.quadApp.quadLogic.CQuad;

public class QuadDetailPanel extends JTabbedPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5485251511491422583L;

	/**
	 * Create the panel.
	 * @param quad 
	 */
	public QuadDetailPanel(CQuad quad) {
		addTab("z", null, new ZTabPanel(quad), "Open circuit (z)");
		addTab("y", null, new YTabPanel(quad), "Short circuito (y)");
		addTab("h", null, new HTabPanel(quad), "Hybrid (h)");
		addTab("g", null, new GTabPanel(quad), "Inverse hybrid (g)");
		addTab("T", null, new TTabPanel(quad), "Transmission (T)");
		addTab("t", null, new ITTabPanel(quad), "Inverse transmission (it)");
		this.setPreferredSize(new Dimension(490, 120));
	}

}
