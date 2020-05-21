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
import com.gmp.labeling.printModels.ExternalAnalysisLabel;
import com.gmp.labeling.printModels.PrintingQueue;

public class ExternalAnalysisLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1232269909030812515L;
	private JPanel contentPane;
	private JTextField externalAnalysis_printing_Number;

	public ExternalAnalysisLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(SamplingLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 400, 250);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("External Analysis Label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setBounds(60, 18, 280, 30);
		contentPane.add(lblNewLabel);
		
		JLabel externalAnalysis_lbl_printingNumber = new JLabel("Printing Number:");
		externalAnalysis_lbl_printingNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		externalAnalysis_lbl_printingNumber.setBounds(60, 80, 110, 20);
		contentPane.add(externalAnalysis_lbl_printingNumber);
				
		externalAnalysis_printing_Number = new JTextField();
		externalAnalysis_printing_Number.setBounds(180, 80, 120, 20);
		contentPane.add(externalAnalysis_printing_Number);
		externalAnalysis_printing_Number.setColumns(10);
				
		JButton externalAnalysis_btn_print = new JButton("Print");
		externalAnalysis_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(printChecker(externalAnalysis_printing_Number.getText(), settings)) {																		
						int printNumber = Integer.valueOf(externalAnalysis_printing_Number.getText());
						int i = 0;
						while(i<printNumber) {
							ExternalAnalysisLabel exalabel = new ExternalAnalysisLabel();
							printqueue.addLabelToQueue(exalabel);							
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
		externalAnalysis_btn_print.setBounds(145, 135, 100, 20);
				
		JButton externalAnalysis_btn_back = new JButton("Back");
		externalAnalysis_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		externalAnalysis_btn_back.setBounds(260, 135, 100, 20);
		contentPane.add(externalAnalysis_btn_back);
		contentPane.add(externalAnalysis_btn_print);
		
		JLabel sample_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		sample_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sample_copyright_developer.setBounds(215, 165, 170, 20);
		contentPane.add(sample_copyright_developer);
		
		JLabel sample_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		sample_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sample_copyright_company.setBounds(115, 180, 280, 20);
		contentPane.add(sample_copyright_company);
		
		JButton externalAnalysis_btn_Settings = new JButton("Settings");
		externalAnalysis_btn_Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();				
			}
		});
		externalAnalysis_btn_Settings.setBounds(30, 135, 100, 20);
		contentPane.add(externalAnalysis_btn_Settings);
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
}
