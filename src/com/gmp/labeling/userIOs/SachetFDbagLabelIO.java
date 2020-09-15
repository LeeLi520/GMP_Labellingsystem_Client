package com.gmp.labeling.userIOs;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.SachetFDbagLabel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SachetFDbagLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1864586564940099505L;
	private JPanel contentPane;
	private JTextField sachetFDbaglabel_printCount;
	
	public SachetFDbagLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel sachetFDbaglabel_lbl_title = new JLabel("Sachet FD Bag Label");
		sachetFDbaglabel_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		sachetFDbaglabel_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		sachetFDbaglabel_lbl_title.setBounds(60, 30, 300, 30);
		contentPane.add(sachetFDbaglabel_lbl_title);
		
		JLabel sachetFDbaglabel_lbl_printCount = new JLabel("Print count:");
		sachetFDbaglabel_lbl_printCount.setBounds(60, 80, 80, 20);
		contentPane.add(sachetFDbaglabel_lbl_printCount);
		
		sachetFDbaglabel_printCount = new JTextField();
		sachetFDbaglabel_printCount.setBounds(140, 80, 180, 20);
		contentPane.add(sachetFDbaglabel_printCount);
		sachetFDbaglabel_printCount.setColumns(10);
		
		JButton sachetFDbaglabel_btn_settings = new JButton("Settings");
		sachetFDbaglabel_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		sachetFDbaglabel_btn_settings.setBounds(30, 125, 90, 20);
		contentPane.add(sachetFDbaglabel_btn_settings);
		
		JButton sachetFDbaglabel_btn_print = new JButton("Print");
		sachetFDbaglabel_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validation(sachetFDbaglabel_printCount.getText(), settings)) {
				int printNumber = Integer.valueOf(sachetFDbaglabel_printCount.getText());
				int i = 0;
				while(i<printNumber) {
					printqueue.addLabelToQueue(new SachetFDbagLabel());
					i++;
				}
				if(settings.getSetting().isLocalPrintingMode()) {
					PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), null, 0, 620);
					printio.setLocationRelativeTo(null);
					printio.setVisible(true);
				}else {
					PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), settings.getSetting().getIpAddress(), settings.getSetting().getPort(), 620);
					printio.setLocationRelativeTo(null);
					printio.setVisible(true);
				}
			}
			}
		});
		sachetFDbaglabel_btn_print.setBounds(170, 125, 90, 20);
		contentPane.add(sachetFDbaglabel_btn_print);
		
		JButton sachetFDbaglabel_btn_back = new JButton("Back");
		sachetFDbaglabel_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		sachetFDbaglabel_btn_back.setBounds(310, 125, 90, 20);
		contentPane.add(sachetFDbaglabel_btn_back);
	}
	
	@SuppressWarnings("unused")
	public static boolean validation(String printCount, Settings settings) {
		try {
			int i = Integer.valueOf(printCount);			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Print count needs to be number input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
