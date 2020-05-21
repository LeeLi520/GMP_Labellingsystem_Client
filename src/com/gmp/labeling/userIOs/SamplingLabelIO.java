package com.gmp.labeling.userIOs;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.HandWritingSamplingLabel;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.SamplingLabel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class SamplingLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField sample_trial_Number;
	private JTextField sample_sample_Name;
	private JTextField sample_printing_Number;

	@SuppressWarnings("deprecation")
	public SamplingLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(SamplingLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 340);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sampling Label Printing");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setBounds(80, 18, 230, 30);
		contentPane.add(lblNewLabel);
		
		JLabel sample_lbl_trialNumber = new JLabel("Trial Number:");
		sample_lbl_trialNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sample_lbl_trialNumber.setBounds(72, 120, 80, 20);
		contentPane.add(sample_lbl_trialNumber);
		
		JLabel sample_lbl_sampleName = new JLabel("Sample Name:");
		sample_lbl_sampleName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sample_lbl_sampleName.setBounds(72, 150, 80, 20);
		contentPane.add(sample_lbl_sampleName);
		
		JLabel sample_lbl_printingNumber = new JLabel("Printing Number:");
		sample_lbl_printingNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		sample_lbl_printingNumber.setBounds(70, 180, 110, 20);
		contentPane.add(sample_lbl_printingNumber);
		
		sample_trial_Number = new JTextField();
		sample_trial_Number.setBounds(160, 120, 120, 20);
		contentPane.add(sample_trial_Number);
		sample_trial_Number.setColumns(10);
		sample_trial_Number.setBackground(getBackground());
		sample_trial_Number.disable();
		
		sample_sample_Name = new JTextField();
		sample_sample_Name.setBounds(160, 150, 120, 20);
		contentPane.add(sample_sample_Name);
		sample_sample_Name.setColumns(10);
		sample_sample_Name.setBackground(getBackground());
		sample_sample_Name.disable();
				
		sample_printing_Number = new JTextField();
		sample_printing_Number.setBounds(180, 180, 120, 20);
		contentPane.add(sample_printing_Number);
		sample_printing_Number.setColumns(10);
				
		JRadioButton sample_rdbtn_HWL = new JRadioButton("Hand writing Label");
		sample_rdbtn_HWL.setFont(new Font("Tahoma", Font.BOLD, 12));
		sample_rdbtn_HWL.setBounds(50, 60, 150, 20);
		sample_rdbtn_HWL.setSelected(true);
		
		JRadioButton sample_rdbtn_TL = new JRadioButton("Typing Label");		
		sample_rdbtn_TL.setFont(new Font("Tahoma", Font.BOLD, 12));
		sample_rdbtn_TL.setBounds(50, 90, 150, 20);
		sample_rdbtn_TL.setSelected(false);
				
		sample_rdbtn_HWL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sample_rdbtn_HWL.isSelected()) {
					sample_rdbtn_TL.setSelected(false);
					sample_trial_Number.setBackground(getBackground());
					sample_trial_Number.disable();
					sample_sample_Name.setBackground(getBackground());
					sample_sample_Name.disable();
				}
			}
		});
		
		sample_rdbtn_TL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sample_rdbtn_TL.isSelected()) {
					sample_rdbtn_HWL.setSelected(false);
					sample_trial_Number.setBackground(Color.WHITE);
					sample_trial_Number.enable();
					sample_sample_Name.setBackground(Color.WHITE);
					sample_sample_Name.enable();
				}
			}
		});
				
		JButton sample_btn_print = new JButton("Print");
		sample_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sample_rdbtn_HWL.isSelected()) {
					if(printChecker(sample_printing_Number.getText(), settings)){
						int printNumber = Integer.valueOf(sample_printing_Number.getText());
						int i = 0;
						while(i<printNumber) {
							printqueue.addLabelToQueue(new HandWritingSamplingLabel());
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
				}else if(sample_rdbtn_TL.isSelected()){
					if(printChecker(sample_trial_Number.getText(), sample_sample_Name.getText(), sample_printing_Number.getText(), settings)) {
						int printNumber = Integer.valueOf(sample_printing_Number.getText());
						int i = 0;
						while(i<printNumber) {
							printqueue.addLabelToQueue(new SamplingLabel(sample_trial_Number.getText(), sample_sample_Name.getText()));
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
		sample_btn_print.setBounds(145, 220, 90, 20);
				
		JButton sample_btn_back = new JButton("Back");
		sample_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		sample_btn_back.setBounds(260, 220, 90, 20);
			
		JButton sample_btn_settings = new JButton("Settings");
		sample_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);
				dispose();
			}
		});
		sample_btn_settings.setBounds(30, 220, 90, 20);
		
		contentPane.add(sample_rdbtn_HWL);
		contentPane.add(sample_rdbtn_TL);
		contentPane.add(sample_btn_back);
		contentPane.add(sample_btn_print);
		contentPane.add(sample_btn_settings);
		
		JLabel sample_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		sample_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sample_copyright_developer.setBounds(215, 255, 170, 20);
		contentPane.add(sample_copyright_developer);
		
		JLabel sample_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		sample_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sample_copyright_company.setBounds(115, 270, 280, 20);
		contentPane.add(sample_copyright_company);
	}
	
	public boolean printChecker(String trialNo, String sampleName, String printingNumber, Settings settings) {
		if(trialNo.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Trial Number cannot be empty.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE
				    );
			return false;
		}
		if(sampleName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Sample Name cannot be empty.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE
				    );
			return false;
		}else if(sampleName.length()>64) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Input text out of limit",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE
				    );
			return false;
		}
		try {
			if(Integer.valueOf(printingNumber)<=0) {
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
	
	public boolean printChecker(String printingNumber, Settings settings) {
		try {
			if(Integer.valueOf(printingNumber)<=0) {
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
