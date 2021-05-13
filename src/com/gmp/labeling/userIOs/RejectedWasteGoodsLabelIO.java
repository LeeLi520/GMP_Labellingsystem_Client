package com.gmp.labeling.userIOs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class RejectedWasteGoodsLabelIO extends JFrame {

	private JPanel contentPane;

	public RejectedWasteGoodsLabelIO() {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel rejectedWaste_lbl_title = new JLabel("Rejected Waste Goods Label");
		rejectedWaste_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		rejectedWaste_lbl_title.setFont(new Font("Arial", Font.BOLD, 22));
		rejectedWaste_lbl_title.setBounds(40, 30, 340, 30);
		contentPane.add(rejectedWaste_lbl_title);
	}
}
