package com.gmp.labeling.userIOs;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.SamplingLabel;
import java.awt.Color;

public class CustomizedPrinting_SamplingLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5183422600695757598L;
	private JPanel contentPane;
	private JTextField sample_trial_Number;
	private JTextField sample_sample_Name;
	private JTextField sample_printing_Number;

	public CustomizedPrinting_SamplingLabelIO(Settings settings, PrintingQueue printqueue, List<Label> tempList, JLabel printCount, int x, int y) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(SamplingLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, 400, 300);
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
		sample_lbl_trialNumber.setBounds(72, 70, 80, 20);
		contentPane.add(sample_lbl_trialNumber);
		
		JLabel sample_lbl_sampleName = new JLabel("Sample Name:");
		sample_lbl_sampleName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sample_lbl_sampleName.setBounds(72, 105, 80, 20);
		contentPane.add(sample_lbl_sampleName);
		
		JLabel sample_lbl_printingNumber = new JLabel("Printing Number:");
		sample_lbl_printingNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		sample_lbl_printingNumber.setBounds(70, 140, 110, 20);
		contentPane.add(sample_lbl_printingNumber);
		
		sample_trial_Number = new JTextField();
		sample_trial_Number.setBounds(160, 70, 120, 20);
		contentPane.add(sample_trial_Number);
		sample_trial_Number.setColumns(10);
		sample_trial_Number.setBackground(Color.WHITE);
		
		sample_sample_Name = new JTextField();
		sample_sample_Name.setBounds(160, 105, 120, 20);
		contentPane.add(sample_sample_Name);
		sample_sample_Name.setColumns(10);
		sample_sample_Name.setBackground(Color.WHITE);
				
		sample_printing_Number = new JTextField();
		sample_printing_Number.setBounds(180, 140, 120, 20);
		contentPane.add(sample_printing_Number);
		sample_printing_Number.setColumns(10);
				
		JButton sample_btn_addToQueue = new JButton("Add To Queue");
		sample_btn_addToQueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(printChecker(sample_trial_Number.getText(), sample_sample_Name.getText(), sample_printing_Number.getText(), settings)) {
						tempList.clear();
						int printNumber = Integer.valueOf(sample_printing_Number.getText());
						int i = 0;
						while(i<printNumber) {
							SamplingLabel sample = new SamplingLabel(sample_trial_Number.getText(), sample_sample_Name.getText());
							printqueue.addLabelToQueue(sample);
							tempList.add(sample);
							i++;
						}
					}
					printCount.setText(String.valueOf(printqueue.getList().size()));
			}
		});
		sample_btn_addToQueue.setBounds(60, 180, 120, 20);
				
		JButton sample_btn_back = new JButton("Close");
		sample_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		sample_btn_back.setBounds(220, 180, 120, 20);
		contentPane.add(sample_btn_back);
		contentPane.add(sample_btn_addToQueue);
		
		JLabel sample_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		sample_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sample_copyright_developer.setBounds(215, 215, 170, 20);
		contentPane.add(sample_copyright_developer);
		
		JLabel sample_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		sample_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sample_copyright_company.setBounds(115, 230, 280, 20);
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
		}
		try {
			Integer.valueOf(printingNumber);
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
