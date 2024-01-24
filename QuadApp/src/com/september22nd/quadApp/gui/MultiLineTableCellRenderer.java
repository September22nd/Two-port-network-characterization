package com.september22nd.quadApp.gui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class MultiLineTableCellRenderer extends JList<String> implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2490766901953881050L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof String[]) {
            setListData((String[]) value);
        }
        
        int preferredHeight = this.getPreferredSize().height;
        if(preferredHeight > table.getRowHeight(row)){
            table.setRowHeight(row,preferredHeight);
        }

        if (isSelected) {
            setBackground(UIManager.getColor("Table.selectionBackground"));
        } else {
            setBackground(UIManager.getColor("Table.background"));
        }

        return this;
	}

}
