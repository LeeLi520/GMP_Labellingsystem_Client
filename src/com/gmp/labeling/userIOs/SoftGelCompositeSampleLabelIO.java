package com.gmp.labeling.userIOs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import com.gmp.labeling.printModels.RetentionLabel_Plain;
import com.gmp.labeling.printModels.SoftGelCompositeLabel_Plain;

public class SoftGelCompositeSampleLabelIO extends JFrame {

	private JPanel contentPane;
	private JTextField SoftGelCompositeSampleLabel_plain_printCount;

	public SoftGelCompositeSampleLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel SoftGelCompositeSampleLabel_plain_lbl_title = new JLabel("Soft Gel Composite Sample  - Plain Label");
		SoftGelCompositeSampleLabel_plain_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		SoftGelCompositeSampleLabel_plain_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		SoftGelCompositeSampleLabel_plain_lbl_title.setBounds(10, 46, 420, 30);
		contentPane.add(SoftGelCompositeSampleLabel_plain_lbl_title);
		
		JLabel SoftGelCompositeSampleLabel_plain_lbl_printCount = new JLabel("Print count:");
		SoftGelCompositeSampleLabel_plain_lbl_printCount.setBounds(50, 100, 80, 20);
		contentPane.add(SoftGelCompositeSampleLabel_plain_lbl_printCount);
		
		SoftGelCompositeSampleLabel_plain_printCount = new JTextField();
		SoftGelCompositeSampleLabel_plain_printCount.setBounds(130, 100, 180, 20);
		contentPane.add(SoftGelCompositeSampleLabel_plain_printCount);
		SoftGelCompositeSampleLabel_plain_printCount.setColumns(10);
		
		JButton SoftGelCompositeSampleLabel_plain_btn_settings = new JButton("Settings");
		SoftGelCompositeSampleLabel_plain_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		SoftGelCompositeSampleLabel_plain_btn_settings.setBounds(170, 140, 90, 20);
		contentPane.add(SoftGelCompositeSampleLabel_plain_btn_settings);
		
		JButton SoftGelCompositeSampleLabel_plain_btn_printCount = new JButton("Print");
		SoftGelCompositeSampleLabel_plain_btn_printCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(validator(SoftGelCompositeSampleLabel_plain_printCount.getText(), settings)) {
					int printNumber = Integer.valueOf(SoftGelCompositeSampleLabel_plain_printCount.getText());
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new SoftGelCompositeLabel_Plain());
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
		SoftGelCompositeSampleLabel_plain_btn_printCount.setBounds(35, 140, 90, 20);
		contentPane.add(SoftGelCompositeSampleLabel_plain_btn_printCount);
		
		JButton SoftGelCompositeSampleLabel_plain_btn_back = new JButton("Back");
		SoftGelCompositeSampleLabel_plain_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		SoftGelCompositeSampleLabel_plain_btn_back.setBounds(305, 140, 90, 20);
		contentPane.add(SoftGelCompositeSampleLabel_plain_btn_back);
		
		JLabel SoftGelCompositeSampleLabel_plain_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		SoftGelCompositeSampleLabel_plain_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		SoftGelCompositeSampleLabel_plain_copyright_developer.setBounds(245, 171, 170, 20);
		contentPane.add(SoftGelCompositeSampleLabel_plain_copyright_developer);
		
		JLabel SoftGelCompositeSampleLabel_plain_copyright_company = new JLabel(connection.loadProperties().getProperty("applicationCopyrightText"));
		SoftGelCompositeSampleLabel_plain_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		SoftGelCompositeSampleLabel_plain_copyright_company.setBounds(145, 186, 280, 20);
		contentPane.add(SoftGelCompositeSampleLabel_plain_copyright_company);
		
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
