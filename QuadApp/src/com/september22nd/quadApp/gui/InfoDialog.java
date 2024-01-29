package com.september22nd.quadApp.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class InfoDialog extends JDialog {

    private static final long serialVersionUID = 5136975490190900807L;

    public InfoDialog() {
        getContentPane().setLayout(null);
        setTitle("Info");
        setSize(new Dimension(245, 215));
        setPreferredSize(new Dimension(245, 200));

        JLabel lblNewLabel = new JLabel("TPNC by September22nd");
        lblNewLabel.setBounds(10, 11, 152, 16);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Version 2024w05a");
        lblNewLabel_1.setBounds(10, 31, 116, 16);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Added support for complex numbers!");
        lblNewLabel_2.setBounds(10, 58, 225, 16);
        getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("Contact:");
        lblNewLabel_2_1.setBounds(10, 86, 64, 16);
        getContentPane().add(lblNewLabel_2_1);

        JLabel lblNewLabel_2_1_1 = new JLabel("Link to GitHub repo.");
        lblNewLabel_2_1_1.setBounds(10, 149, 157, 16);
        lblNewLabel_2_1_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openWebpage("https://github.com/September22nd/Two-port-network-characterization");
            }
        });
        lblNewLabel_2_1_1.setForeground(Color.BLUE);
        lblNewLabel_2_1_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        getContentPane().add(lblNewLabel_2_1_1);
        
        JLabel lblNewLabel_3 = new JLabel("Discord: @september22nd");
        lblNewLabel_3.setBounds(10, 105, 157, 16);
        getContentPane().add(lblNewLabel_3);
        
        JLabel lblNewLabel_2_2 = new JLabel("Please let me know if you find a bug.");
        lblNewLabel_2_2.setBounds(10, 133, 225, 16);
        getContentPane().add(lblNewLabel_2_2);
    }

    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
