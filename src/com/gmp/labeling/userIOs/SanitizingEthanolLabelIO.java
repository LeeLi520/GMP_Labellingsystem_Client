package com.gmp.labeling.userIOs;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.SanitizingEthanolLabel_Plain;

public class SanitizingEthanolLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5342171041685879887L;
	private JPanel contentPane;
	private JTextField SanitizingEthanol_plain_printCount;

	public SanitizingEthanolLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel SanitizingEthanol_plain_lbl_title = new JLabel("Sanitizing 70% Ethanol - Plain label");
		SanitizingEthanol_plain_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		SanitizingEthanol_plain_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		SanitizingEthanol_plain_lbl_title.setBounds(30, 46, 380, 30);
		contentPane.add(SanitizingEthanol_plain_lbl_title);
		
		JLabel SanitizingEthanol_plain_lbl_printCount = new JLabel("Print count:");
		SanitizingEthanol_plain_lbl_printCount.setBounds(50, 100, 80, 20);
		contentPane.add(SanitizingEthanol_plain_lbl_printCount);
		
		SanitizingEthanol_plain_printCount = new JTextField();
		SanitizingEthanol_plain_printCount.setBounds(130, 100, 180, 20);
		contentPane.add(SanitizingEthanol_plain_printCount);
		SanitizingEthanol_plain_printCount.setColumns(10);
		
		JButton SanitizingEthanol_plain_btn_settings = new JButton("Settings");
		SanitizingEthanol_plain_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		SanitizingEthanol_plain_btn_settings.setBounds(170, 140, 90, 20);
		contentPane.add(SanitizingEthanol_plain_btn_settings);
		
		JButton SanitizingEthanol_plain_btn_printCount = new JButton("Print");
		SanitizingEthanol_plain_btn_printCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(validator(SanitizingEthanol_plain_printCount.getText(), settings)) {
					int printNumber = Integer.valueOf(SanitizingEthanol_plain_printCount.getText());
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new SanitizingEthanolLabel_Plain());
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
		SanitizingEthanol_plain_btn_printCount.setBounds(35, 140, 90, 20);
		contentPane.add(SanitizingEthanol_plain_btn_printCount);
		
		JButton SanitizingEthanol_plain_btn_back = new JButton("Back");
		SanitizingEthanol_plain_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		SanitizingEthanol_plain_btn_back.setBounds(305, 140, 90, 20);
		contentPane.add(SanitizingEthanol_plain_btn_back);
		
		JLabel SanitizingEthanol_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		SanitizingEthanol_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		SanitizingEthanol_copyright_developer.setBounds(245, 171, 170, 20);
		contentPane.add(SanitizingEthanol_copyright_developer);
		
		JLabel SanitizingEthanol_copyright_company = new JLabel(connection.loadProperties().getProperty("applicationCopyrightText"));
		SanitizingEthanol_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		SanitizingEthanol_copyright_company.setBounds(145, 186, 280, 20);
		contentPane.add(SanitizingEthanol_copyright_company);
		
	}
	
	@SuppressWarnings("unused")
	public static boolean validator(String printCount, Settings settings) {
		try {
			int test = Integer.valueOf(printCount);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Trial Number cannot be empty.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE
				    );
			return false;
		}
		return true;
	}
}
