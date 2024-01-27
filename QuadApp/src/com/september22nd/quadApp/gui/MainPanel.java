package com.september22nd.quadApp.gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.september22nd.quadApp.quadLogic.Matrix;
import com.september22nd.quadApp.quadLogic.ParamType;
import com.september22nd.quadApp.quadLogic.Quad;

public class MainPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2124907119225376279L;

	private String unitStrings[][] = {{"Ω", "Ω"}, {"Ω", "Ω"}};
	
	private static final String[] COMBO_OPTIONS = {
			"Open circuit (z)",
			"Short circuit (y)",
			"Hybrid (h)",
			"Inverse hybrid (g)",
			"Transmission (T)",
			"Inverse transmission (t)",
	};
	
	public ParamType paramTypeSelected = ParamType.OPEN;
	
	private Quad quad;
	private ArrayList<Quad> quadList;
	
	private JTextField inputField11;
	private JTextField inputField21;
	private JTextField inputField12;
	private JTextField inputField22;
	private JComboBox<String> paramTypeComboBox;
	private JButton calcButton;
	private JButton deleteButton;
	private JButton cleanTableButton;
	private JLabel unitLabel11;
	private JLabel unitLabel12;
	private JLabel unitLabel21;
	private JLabel unitLabel22;
	private JTable table;
	private JScrollPane tableScrollPane;
	private MultiLineTableCellRenderer renderer;
	
	private int tableHeight = 0;

	public MainPanel() {
		setFocusable(true);
		setPreferredSize(new Dimension(620, 300));
		setLayout(null);
		
		inputField11 = new JTextField();
		inputField11.setBounds(10, 11, 86, 20);
		add(inputField11);
		inputField11.setColumns(10);	
		
		inputField12 = new JTextField();
		inputField12.setBounds(162, 11, 86, 20);
		add(inputField12);
		inputField12.setColumns(10);
		
		inputField21 = new JTextField();
		inputField21.setBounds(10, 42, 86, 20);
		add(inputField21);
		inputField21.setColumns(10);
		
		inputField22 = new JTextField();
		inputField22.setBounds(162, 42, 86, 20);
		add(inputField22);
		inputField22.setColumns(10);
		
		unitLabel11 = new JLabel(unitStrings[0][0]);
		unitLabel12 = new JLabel(unitStrings[0][1]);
		unitLabel21 = new JLabel(unitStrings[1][0]);
		unitLabel22 = new JLabel(unitStrings[1][1]);
		unitLabel11.setBounds(106, 14, 46, 14);
		add(unitLabel11);
		unitLabel12.setBounds(258, 14, 46, 14);
		add(unitLabel12);
		unitLabel21.setBounds(106, 45, 46, 14);
		add(unitLabel21);
		unitLabel22.setBounds(258, 45, 46, 14);
		add(unitLabel22);
		
		paramTypeComboBox = new JComboBox<String>(COMBO_OPTIONS);
		paramTypeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateInputOptions();
			}
		});
		paramTypeComboBox.setBounds(314, 41, 140, 22);
		add(paramTypeComboBox);
		
		calcButton = new JButton("Add");
		JPanel tempPanel = this;
		calcButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addQuadToTable();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(tempPanel, "Introduce a valid number.", "Number format error", JOptionPane.INFORMATION_MESSAGE);
				}
				//cleanInputFields();
			}
		});
		calcButton.setBounds(512, 41, 98, 23);
		add(calcButton);
		
		deleteButton = new JButton("Clear fields");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cleanInputFields();
			}
		});
		deleteButton.setBounds(512, 10, 98, 23);
		add(deleteButton);
		
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
		tableScrollPane.setBounds(10, 75, 600, 190);
		add(tableScrollPane);
		
		cleanTableButton = new JButton("Dump table");
		cleanTableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				putCleanTable();
			}
		});
		cleanTableButton.setBounds(512, 270, 98, 23);
		add(cleanTableButton);
		
		JLabel text1 = new JLabel("Parameter type");
		text1.setBounds(314, 24, 124, 16);
		add(text1);
		
	}
	
	private void updateInputOptions() {
		switch(paramTypeComboBox.getSelectedIndex()) {
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
	}
	
	private void putCleanTable() {
		quadList = new ArrayList<Quad>();
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
		table.setBounds(10, 75, 570, 200);
		table.setDefaultEditor(Object.class, null);
	}
	
	private void addQuadToTable() throws NumberFormatException {
		Matrix ma = readInputs();
		quad = new Quad(ma, paramTypeSelected);
		quadList.add(quad);
		
		if (quad != null) {
			Matrix _z = quad.getOpenCircuitParams();
			Matrix _y = quad.getShortCircuitParams();
			Matrix _h = quad.getHybridParams();
			Matrix _g = quad.getInvHybridParams();
			Matrix _t = quad.getTransParams();
			Matrix _it = quad.getInvTransParams();
			
			String[] zStr = new String[2];
			try {
				zStr[0] = String.format("%.3f %.3f", _z.get11(), _z.get12());
				zStr[1] = String.format("%.3f %.3f", _z.get21(), _z.get22());
			} catch (NullPointerException e) {
				zStr[0] = "DNE";
				zStr[1] = "";
			}
			String[] yStr = new String[2];
			try {
				yStr[0] = String.format("%.3f %.3f", _y.get11(), _y.get12());
				yStr[1] = String.format("%.3f %.3f", _y.get21(), _y.get22());
			} catch (NullPointerException e) {
				yStr[0] = "DNE";
				yStr[1] = "";
			}
			String[] hStr = new String[2];
			try {
				hStr[0] = String.format("%.3f %.3f", _h.get11(), _h.get12());
				hStr[1] = String.format("%.3f %.3f", _h.get21(), _h.get22());
			} catch (NullPointerException e) {
				hStr[0] = "DNE";
				hStr[1] = "";
			}
			String[] gStr = new String[2];
			try {
				gStr[0] = String.format("%.3f %.3f", _g.get11(), _g.get12());
				gStr[1] = String.format("%.3f %.3f", _g.get21(), _g.get22());
			} catch (NullPointerException e) {
				gStr[0] = "DNE";
				gStr[1] = "";
			}
			String[] tStr = new String[2];
			try {
				tStr[0] = String.format("%.3f %.3f", _t.get11(), _t.get12());
				tStr[1] = String.format("%.3f %.3f", _t.get21(), _t.get22());
			} catch (NullPointerException e) {
				tStr[0] = "DNE";
				tStr[1] = "";
			}
			String[] itStr = new String[2];
			try {
				itStr[0] = String.format("%.3f %.3f", _it.get11(), _it.get12());
				itStr[1] = String.format("%.3f %.3f", _it.get21(), _it.get22());
			} catch (NullPointerException e) {
				itStr[0] = "DNE";
				itStr[1] = "";
			}
			
			//System.out.println(table.getRowHeight() * 2); //this prints 32
			tableHeight += 36; //is there a better way to do this?
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
	
	private Matrix readInputs() throws NumberFormatException {
		return new Matrix(
				readInputField(inputField11),
				readInputField(inputField12),
				readInputField(inputField21),
				readInputField(inputField22)
				);
	}
	
	private Double readInputField(JTextField inputField) {
		try {
			return Double.parseDouble(inputField.getText());
		}
		catch (NumberFormatException e) {
			switch(inputField.getText().charAt(inputField.getText().length() - 1)) {
			case 'p': //pico
				return Double.parseDouble(inputField.getText().substring(0, inputField.getText().length() - 1)) * 0.000000000001;
			case 'n': //nano
				return Double.parseDouble(inputField.getText().substring(0, inputField.getText().length() - 1)) * 0.000000001;
			case 'u': //micro
				return Double.parseDouble(inputField.getText().substring(0, inputField.getText().length() - 1)) * 0.000001;
			case 'm': //mili
				return Double.parseDouble(inputField.getText().substring(0, inputField.getText().length() - 1)) * 0.001;
			case 'k': //kilo
				return Double.parseDouble(inputField.getText().substring(0, inputField.getText().length() - 1)) * 1000.0;
			case 'M': //mega
				return Double.parseDouble(inputField.getText().substring(0, inputField.getText().length() - 1)) * 1000000.0;
			default:
				throw new NumberFormatException();
			}
		}
	}

	private void cleanInputFields() {
		inputField11.setText("");
		inputField12.setText("");
		inputField21.setText("");
		inputField22.setText("");
	}
	
	private void showQuadWindow(int row) {
		JDialog quadDetail = new JDialog();
		quadDetail.setTitle("Selected two-port-network parameters");
		quadDetail.add(new QuadDetailPanel(quadList.get(row)));
		quadDetail.setSize(new Dimension(520, 135));
		quadDetail.setLocationRelativeTo(this.getParent());
		quadDetail.setModal(true);
		quadDetail.setResizable(false);
		quadDetail.setVisible(true);
	}

}
