package com.gmp.labeling.userIOs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.CustomerSampleLabel;
import com.gmp.labeling.printModels.CustomerSampleLabelT;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.JRadioButton;

public class CustomerSampleIO extends JFrame{

	private static final long serialVersionUID = -1103024151460624315L;
	private JPanel contentPane;
	private JTextField customerSample_printing_Number;
	private JTextField customerSample_sampleName;
	private JTextField customerSample_customer;
	
	@SuppressWarnings("deprecation")
	public CustomerSampleIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(SamplingLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 400, 330);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel customerSample_lbl_title = new JLabel("Customer Sample Label");
		customerSample_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		customerSample_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		customerSample_lbl_title.setBounds(55, 18, 280, 30);
		contentPane.add(customerSample_lbl_title);
		
		customerSample_sampleName = new JTextField();
		customerSample_sampleName.setBounds(180, 120, 120, 20);
		contentPane.add(customerSample_sampleName);
		customerSample_sampleName.setColumns(10);
		customerSample_sampleName.disable();
		customerSample_sampleName.setBackground(getBackground());
		
		customerSample_customer = new JTextField();
		customerSample_customer.setBounds(180, 149, 120, 20);
		contentPane.add(customerSample_customer);
		customerSample_customer.setColumns(10);
		customerSample_customer.disable();
		customerSample_customer.setBackground(getBackground());
		
		JRadioButton customerSample_rdbtn_handwritingLabel = new JRadioButton("Hand writing Label");
		customerSample_rdbtn_handwritingLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customerSample_sampleName.disable();
				customerSample_sampleName.setBackground(getBackground());
				customerSample_customer.disable();
				customerSample_customer.setBackground(getBackground());
			}
		});
		customerSample_rdbtn_handwritingLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		customerSample_rdbtn_handwritingLabel.setSelected(true);
		customerSample_rdbtn_handwritingLabel.setBounds(60, 60, 150, 20);
		contentPane.add(customerSample_rdbtn_handwritingLabel);
		
		JRadioButton customerSample_rdbtn_typingLabel = new JRadioButton("Typing Label");
		customerSample_rdbtn_typingLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customerSample_sampleName.enable();
				customerSample_sampleName.setBackground(Color.WHITE);
				customerSample_customer.enable();
				customerSample_customer.setBackground(Color.WHITE);				
			}
		});
		customerSample_rdbtn_typingLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		customerSample_rdbtn_typingLabel.setBounds(60, 90, 150, 20);
		contentPane.add(customerSample_rdbtn_typingLabel);
		
		ButtonGroup labelType = new ButtonGroup();
		labelType.add(customerSample_rdbtn_handwritingLabel);
		labelType.add(customerSample_rdbtn_typingLabel);
		
		
		JLabel customerSample_lbl_printingNumber = new JLabel("Printing Number:");
		customerSample_lbl_printingNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		customerSample_lbl_printingNumber.setBounds(68, 180, 110, 20);
		contentPane.add(customerSample_lbl_printingNumber);
				
		customerSample_printing_Number = new JTextField();
		customerSample_printing_Number.setBounds(188, 180, 120, 20);
		contentPane.add(customerSample_printing_Number);
		customerSample_printing_Number.setColumns(10);
		
		JLabel customerSample_lbl_sampleName = new JLabel("Sample Name:");
		customerSample_lbl_sampleName.setBounds(80, 120, 100, 20);
		contentPane.add(customerSample_lbl_sampleName);
		
		JLabel customerSample_lbl_customer = new JLabel("Customer:");
		customerSample_lbl_customer.setBounds(80, 150, 100, 20);
		contentPane.add(customerSample_lbl_customer);
		

				
		JButton customerSample_btn_print = new JButton("Print");
		customerSample_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(customerSample_rdbtn_handwritingLabel.isSelected()) {
					if(printChecker(customerSample_printing_Number.getText(), settings)) {																		
						int printNumber = Integer.valueOf(customerSample_printing_Number.getText());
						int i = 0;
						while(i<printNumber) {
							CustomerSampleLabel cuslabel = new CustomerSampleLabel();
							printqueue.addLabelToQueue(cuslabel);							
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
				}else {
					if(validation(customerSample_sampleName.getText(), customerSample_customer.getText(),customerSample_printing_Number.getText(), settings)){
						int printNumber = Integer.valueOf(customerSample_printing_Number.getText());
						int i = 0;
						while(i<printNumber) {
							CustomerSampleLabelT cuslabel = new CustomerSampleLabelT(customerSample_sampleName.getText(), customerSample_customer.getText());
							printqueue.addLabelToQueue(cuslabel);							
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
			}
		});
		customerSample_btn_print.setBounds(150, 220, 100, 20);
				
		JButton customerSample_btn_back = new JButton("Back");
		customerSample_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		customerSample_btn_back.setBounds(270, 220, 100, 20);
		contentPane.add(customerSample_btn_back);
		contentPane.add(customerSample_btn_print);
		
		JLabel sample_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		sample_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sample_copyright_developer.setBounds(215, 250, 170, 20);
		contentPane.add(sample_copyright_developer);
		
		JLabel sample_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		sample_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sample_copyright_company.setBounds(115, 265, 280, 20);
		contentPane.add(sample_copyright_company);
		
		JButton customerSample_btn_Settings = new JButton("Settings");
		customerSample_btn_Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();				
			}
		});
		customerSample_btn_Settings.setBounds(30, 220, 100, 20);
		contentPane.add(customerSample_btn_Settings);
	
	}
	
	public boolean printChecker(String printingNumber, Settings settings) {
		try {
			if(Integer.valueOf(printingNumber)<= 0) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid or missing printing number.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE
					    );
				return false;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Invalid or missing printing number.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE
				    );
			return false;
		}
		return true;	
	}
	
	public boolean validation(String sampleName, String customer, String printingNumber, Settings settings) {
		if(sampleName.length()>30) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Only 30 Characters are allowed.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE
				    );
			return false;
		}
		if(customer.length()>30) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Only 30 Characters are allowed.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE
				    );
			return false;
		}
		try {
			if(Integer.valueOf(printingNumber)<= 0) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid or missing printing number.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE
					    );
				return false;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Invalid or missing printing number.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE
				    );
			return false;
		}
		return true;	
	}
}
