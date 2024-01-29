package com.september22nd.quadApp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import com.september22nd.quadApp.quadLogic.AngUnit;
import com.september22nd.quadApp.quadLogic.CMatrix;
import com.september22nd.quadApp.quadLogic.CQuad;
import com.september22nd.quadApp.quadLogic.Complex;
import com.september22nd.quadApp.quadLogic.DisplayForm;
import com.september22nd.quadApp.quadLogic.ParamType;

public class MainPanelC extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5726706206567286033L;
	
	private JTextField inputField11;
	private JTextField inputField21;
	private JTextField inputField12;
	private JTextField inputField22;
	private JLabel label11;
	private JLabel label12;
	private JLabel label21;
	private JLabel label22;
	private JLabel unitLabel11;
	private JLabel unitLabel12;
	private JLabel unitLabel21;
	private JLabel unitLabel22;
	private JTable table;
	private JComboBox<String> paramTypeCombo;
	private JComboBox<String> unitCombo;
	private JScrollPane tableScrollPane;
	private MultiLineTableCellRenderer renderer;
	private JRadioButton rectangularRadio;
	private JRadioButton polarRadio;
	private ButtonGroup radioGroup;
	
	private ParamType paramTypeSelected = ParamType.OPEN;
	
	private AngUnit angUnit = AngUnit.DEG;
	
	private DisplayForm displayForm = DisplayForm.RECT;

	private String unitStrings[][] = {{"Ω", "Ω"}, {"Ω", "Ω"}};
	
	private static final String[] PARAM_COMBO_OPTIONS = {
		"Open circuit (z)",
		"Short circuit (y)",
		"Hybrid (h)",
		"Inverse hybrid (g)",
		"Transmission (T)",
		"Inverse transmission (t)",
	};
	
	private static final String[] UNIT_COMBO_OPTIONS = {
		"°",
		"rad",
	};
	
	private static final String[] SYMBOLS = {
		"z",
		"y",
		"h",
		"g",
	};
	
	private CQuad quad;
	
	private ArrayList<CQuad> quadList;
	
	private int tableHeight;
	
	public int numPrec = 2;
	
	public int angPrec = 2;
	
	/**
	 * Create the panel.
	 */
	public MainPanelC() {
		setFocusable(true);
		setPreferredSize(new Dimension(620, 400));
		setLayout(null);
		JPanel tp1 = this;
		
		label11 = new JLabel(String.format("%s11=", SYMBOLS[0]));
		label11.setBounds(12, 53, 28, 14);
		add(label11);
		
		label12 = new JLabel(String.format("%s12=", SYMBOLS[0]));
		label12.setBounds(237, 53, 28, 14);
		add(label12);
		
		label21 = new JLabel(String.format("%s21=", SYMBOLS[0]));
		label21.setBounds(12, 79, 28, 14);
		add(label21);
		
		label22 = new JLabel(String.format("%s22=", SYMBOLS[0]));
		label22.setBounds(237, 79, 28, 14);
		add(label22);
		
		inputField11 = new JTextField();
		inputField11.setBounds(44, 50, 152, 20);
		add(inputField11);
		inputField11.setColumns(10);
		
		inputField21 = new JTextField();
		inputField21.setColumns(10);
		inputField21.setBounds(44, 76, 152, 20);
		add(inputField21);
		
		inputField12 = new JTextField();
		inputField12.setColumns(10);
		inputField12.setBounds(267, 50, 152, 20);
		add(inputField12);
		
		inputField22 = new JTextField();
		inputField22.setColumns(10);
		inputField22.setBounds(267, 76, 152, 20);
		add(inputField22);
		
		JLabel labelUnitCombo = new JLabel("Angle units");
		labelUnitCombo.setBounds(12, 15, 68, 14);
		add(labelUnitCombo);
		
		unitCombo = new JComboBox<String>(UNIT_COMBO_OPTIONS);
		unitCombo.setBounds(79, 12, 86, 20);
		unitCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAngUnitUsed();
				reRenderTable();
			}
		});
		add(unitCombo);
		
		JLabel labelParamTypeCombo = new JLabel("Parameter type");
		labelParamTypeCombo.setBounds(182, 15, 94, 14);
		add(labelParamTypeCombo);
		
		paramTypeCombo = new JComboBox<String>(PARAM_COMBO_OPTIONS);
		paramTypeCombo.setBounds(279, 12, 157, 20);
		paramTypeCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateInputOptions();
			}
		});
		add(paramTypeCombo);
		
		JButton resetTableButton = new JButton("Reset table");
		resetTableButton.setBounds(464, 9, 144, 26);
		resetTableButton.setMnemonic('R');
		resetTableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				putCleanTable();
			}
		});
		add(resetTableButton);
		
		JButton cleanInputsButton = new JButton("Clear fields");
		cleanInputsButton.setBounds(464, 41, 144, 26);
		cleanInputsButton.setMnemonic('C');
		cleanInputsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		add(cleanInputsButton);
		
		JButton addToTableButton = new JButton("Add to table");
		addToTableButton.setBounds(464, 73, 144, 26);
		addToTableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addQuadToTable();
				}
				catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(tp1, "Introduce a valid number.", "Number format error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(addToTableButton);
		
		unitLabel11 = new JLabel(unitStrings[0][0]);
		unitLabel11.setBounds(203, 52, 28, 14);
		add(unitLabel11);
		
		unitLabel21 = new JLabel(unitStrings[1][0]);
		unitLabel21.setBounds(203, 78, 28, 14);
		add(unitLabel21);
		
		unitLabel12 = new JLabel(unitStrings[0][1]);
		unitLabel12.setBounds(424, 52, 28, 14);
		add(unitLabel12);
		
		unitLabel22 = new JLabel(unitStrings[1][1]);
		unitLabel22.setBounds(424, 78, 28, 14);
		add(unitLabel22);
		
		table = new JTable();
		putCleanTable();
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					showQuadWindow(table.getSelectedRow());
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
		add(table);
		
		tableScrollPane = new JScrollPane(table);
		tableScrollPane.setBounds(12, 105, 600, 263);
		add(tableScrollPane);
		
		JLabel labelFormRadio = new JLabel("Display as...?");
		labelFormRadio.setBounds(12, 372, 86, 16);
		add(labelFormRadio);
		
		rectangularRadio = new JRadioButton("Rectangular");
		rectangularRadio.setBounds(110, 368, 94, 24);
		rectangularRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayForm = DisplayForm.RECT;
				reRenderTable();
			}
		});
		add(rectangularRadio);
		
		polarRadio = new JRadioButton("Polar");
		polarRadio.setBounds(208, 368, 94, 24);
		polarRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayForm = DisplayForm.POLAR;
				reRenderTable();
			}
		});
		add(polarRadio);
		
		radioGroup = new ButtonGroup();
		radioGroup.add(rectangularRadio);
		radioGroup.add(polarRadio);
		radioGroup.setSelected(rectangularRadio.getModel(), true);
	}
	
	private void updateInputOptions() {
		switch(paramTypeCombo.getSelectedIndex()) {
		case 0:
			paramTypeSelected = ParamType.OPEN;
			unitStrings[0][0] = "Ω";
			unitStrings[0][1] = "Ω";
			unitStrings[1][0] = "Ω";
			unitStrings[1][1] = "Ω";
			break;
		case 1:
			paramTypeSelected = ParamType.SHORT;
			unitStrings[0][0] = "S";
			unitStrings[0][1] = "S";
			unitStrings[1][0] = "S";
			unitStrings[1][1] = "S";
			break;
		case 2:
			paramTypeSelected = ParamType.HYBRID;
			unitStrings[0][0] = "Ω";
			unitStrings[0][1] = "";
			unitStrings[1][0] = "";
			unitStrings[1][1] = "S";
			break;
		case 3:
			paramTypeSelected = ParamType.INV_HYBRID;
			unitStrings[0][0] = "S";
			unitStrings[0][1] = "";
			unitStrings[1][0] = "";
			unitStrings[1][1] = "Ω";
			break;
		case 4:
			paramTypeSelected = ParamType.TRANS;
			unitStrings[0][0] = "";
			unitStrings[0][1] = "Ω";
			unitStrings[1][0] = "S";
			unitStrings[1][1] = "";
			break;
		case 5:
			paramTypeSelected = ParamType.INV_TRANS;
			unitStrings[0][0] = "";
			unitStrings[0][1] = "S";
			unitStrings[1][0] = "Ω";
			unitStrings[1][1] = "";
		}
		unitLabel11.setText(unitStrings[0][0]);
		unitLabel12.setText(unitStrings[0][1]);
		unitLabel21.setText(unitStrings[1][0]);
		unitLabel22.setText(unitStrings[1][1]);
		try {
			label11.setText(String.format("%s11=", SYMBOLS[paramTypeCombo.getSelectedIndex()]));
			label12.setText(String.format("%s12=", SYMBOLS[paramTypeCombo.getSelectedIndex()]));
			label21.setText(String.format("%s21=", SYMBOLS[paramTypeCombo.getSelectedIndex()]));
			label22.setText(String.format("%s22=", SYMBOLS[paramTypeCombo.getSelectedIndex()]));
		}
		catch (ArrayIndexOutOfBoundsException e) {
			switch(paramTypeCombo.getSelectedIndex()) {
			case 4:
				label11.setText("A  =");
				label12.setText("B  =");
				label21.setText("C  =");
				label22.setText("D  =");
				break;
			case 5:
				label11.setText("a  =");
				label12.setText("b  =");
				label21.setText("c  =");
				label22.setText("d  =");
			}
		}
	}
	
	private CMatrix readInputs() {
		return new CMatrix(
				Complex.parseComplex(inputField11.getText(), angUnit),
				Complex.parseComplex(inputField12.getText(), angUnit),
				Complex.parseComplex(inputField21.getText(), angUnit),
				Complex.parseComplex(inputField22.getText(), angUnit)
				);
	}
	
	private void addQuadToTable() {
		//System.out.println(Complex.parseComplex(inputField11.getText(), AngUnit.DEG).toPolarString(AngUnit.DEG, 4, 2));
		CMatrix m = readInputs();
		quad = new CQuad(m, paramTypeSelected);
		quadList.add(quad);
		
		String[] zStr = cellArray(quad.getOpenCircuitParams());
		String[] yStr = cellArray(quad.getShortCircuitParams());
		String[] hStr = cellArray(quad.getHybridParams());
		String[] gStr = cellArray(quad.getInvHybridParams());
		String[] tStr = cellArray(quad.getTransParams());
		String[] itStr = cellArray(quad.getInvTransParams());
		
		//System.out.println(table.getRowHeight() * 2); //this prints 32
		tableHeight += 36; //is there a better way to do this?
		//if I want to set the look and feel to system, this will mess up the table height. 36 is the size of it
		//only in the java crossplatform look and feel, if you know how to do it without hardcoding the row height please make me know
		table.setPreferredSize(new Dimension(600, tableHeight));
		
		((DefaultTableModel) table.getModel()).addRow(new Object[]{
				zStr, 
				yStr, 
				hStr, 
				gStr, 
				tStr, 
				itStr,
				});
	}
	
	public void reRenderTable() {
		tableHeight = 0;
		table.setPreferredSize(new Dimension(600, tableHeight));
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"z", "y", "h", "g", "T", "t"
				}
			));
	    renderer = new MultiLineTableCellRenderer();
	    for (int i = 0; i < 6; i++) {
	    	table.getColumnModel().getColumn(i).setCellRenderer(renderer);
	    }
		table.setBorder(new LineBorder(new Color(171, 173, 179)));
		
		for (int i = 0; i < quadList.size(); i++) {
			String[] zStr = cellArray(quadList.get(i).getOpenCircuitParams());
			String[] yStr = cellArray(quadList.get(i).getShortCircuitParams());
			String[] hStr = cellArray(quadList.get(i).getHybridParams());
			String[] gStr = cellArray(quadList.get(i).getInvHybridParams());
			String[] tStr = cellArray(quadList.get(i).getTransParams());
			String[] itStr = cellArray(quadList.get(i).getInvTransParams());
			
			tableHeight += 36; 
			table.setPreferredSize(new Dimension(600, tableHeight));
			
			((DefaultTableModel) table.getModel()).addRow(new Object[]{
					zStr, 
					yStr, 
					hStr, 
					gStr, 
					tStr, 
					itStr,
					});
		}
	}
	
	private void showQuadWindow(int row) {
		JDialog quadDetail = new JDialog();
		quadDetail.setTitle("Selected two-port-network parameters");
		quadDetail.add(new QuadDetailPanel(quadList.get(row)));
		quadDetail.setSize(new Dimension(520, 135));
		quadDetail.setLocationRelativeTo(this.getParent());
		quadDetail.setModal(true);
		quadDetail.setResizable(false);
		quadDetail.pack();
		quadDetail.setVisible(true);
	}
	
	private String[] cellArray(CMatrix m) {
		String[] s = new String[2];
		try {
			if (displayForm == DisplayForm.RECT) {
				s[0] = String.format("%s %s", m.get(0, 0).toRectString(numPrec), m.get(0, 1).toRectString(numPrec));
				s[1] = String.format("%s %s", m.get(1, 0).toRectString(numPrec), m.get(1, 1).toRectString(numPrec));
			}
			else {
				s[0] = String.format("%s %s", m.get(0, 0).toPolarString(angUnit, numPrec, angPrec), m.get(0, 1).toPolarString(angUnit, numPrec, angPrec));
				s[1] = String.format("%s %s", m.get(1, 0).toPolarString(angUnit, numPrec, angPrec), m.get(1, 1).toPolarString(angUnit, numPrec, angPrec));
			}
		}
		catch (NullPointerException e) {
			s[0] = "Does not exist";
			s[1] = "";
		}
		return s;
	}
	
	private void putCleanTable() {
		quadList = new ArrayList<CQuad>();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"z", "y", "h", "g", "T", "t"
				}
			));
		tableHeight = 0;
		table.setPreferredSize(new Dimension(600, tableHeight));
	    renderer = new MultiLineTableCellRenderer();
	    for (int i = 0; i < 6; i++) {
	    	table.getColumnModel().getColumn(i).setCellRenderer(renderer);
	    }
		table.setBorder(new LineBorder(new Color(171, 173, 179)));
		table.setBounds(12, 116, 596, 252);
		table.setDefaultEditor(Object.class, null);
	}
	
	private void clearFields() {
		inputField11.setText("");
		inputField12.setText("");
		inputField21.setText("");
		inputField22.setText("");
	}
	
	private void updateAngUnitUsed() {
		switch(unitCombo.getSelectedIndex()) {
		case 0:
			angUnit = AngUnit.DEG;
			break;
		case 1:
			angUnit = AngUnit.RAD;
		}
	}
}
