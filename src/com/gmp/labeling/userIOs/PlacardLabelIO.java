package com.gmp.labeling.userIOs;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.PlacardLabel;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlacardLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7370382842967466668L;
	private JPanel contentPane;
	private JTextField Placard_line1;
	private JTextField Placard_line2;
	private JButton Placard_btn_settings;
	private JButton Placard_btn_print;
	private JButton Placard_btn_back;
	private JLabel Placard_lbl_copyright_2;
	private JLabel Placard_lbl_copyright_1;
	private JLabel Placard_lbl_printCount;
	private JTextField Placard_printCount;

	public PlacardLabelIO(Settings settings, PrintingQueue printqueue) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 320);
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel PlacardLabel_lbl_title = new JLabel("Placard Label(Temp)");
		PlacardLabel_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		PlacardLabel_lbl_title.setBounds(120, 30, 230, 30);
		contentPane.add(PlacardLabel_lbl_title);
		
		JLabel Placard_lbl_line1 = new JLabel("Line 1:");
		Placard_lbl_line1.setBounds(50, 80, 60, 20);
		contentPane.add(Placard_lbl_line1);
		
		JLabel Placard_lbl_line2 = new JLabel("Line 2:");
		Placard_lbl_line2.setBounds(50, 120, 60, 20);
		contentPane.add(Placard_lbl_line2);
		
		Placard_line1 = new JTextField();
		Placard_line1.setBounds(110, 80, 180, 20);
		contentPane.add(Placard_line1);
		Placard_line1.setColumns(10);
		
		Placard_line2 = new JTextField();
		Placard_line2.setBounds(110, 120, 180, 20);
		contentPane.add(Placard_line2);
		Placard_line2.setColumns(10);
		
		Placard_lbl_printCount = new JLabel("Print count:");
		Placard_lbl_printCount.setBounds(50, 160, 80, 20);
		contentPane.add(Placard_lbl_printCount);
		
		Placard_printCount = new JTextField();
		Placard_printCount.setBounds(130, 160, 160, 20);
		contentPane.add(Placard_printCount);
		Placard_printCount.setColumns(10);
		
		Placard_btn_settings = new JButton("Settings");
		Placard_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		Placard_btn_settings.setBounds(30, 205, 90, 20);
		contentPane.add(Placard_btn_settings);
		
		Placard_btn_print = new JButton("Print");
		Placard_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(Placard_line1.getText(), Placard_line2.getText(), Placard_printCount.getText(), settings)) {																		
					int printNumber = Integer.valueOf(Placard_printCount.getText());
					int i = 0;
					while(i<printNumber) {
						PlacardLabel placardlabel = new PlacardLabel(Placard_line1.getText(), Placard_line2.getText());
						printqueue.addLabelToQueue(placardlabel);							
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
		Placard_btn_print.setBounds(160, 205, 90, 20);
		contentPane.add(Placard_btn_print);
		
		Placard_btn_back = new JButton("Back");
		Placard_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		Placard_btn_back.setBounds(290, 205, 90, 20);
		contentPane.add(Placard_btn_back);
		
		Placard_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		Placard_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Placard_lbl_copyright_2.setBounds(114, 251, 280, 20);
		contentPane.add(Placard_lbl_copyright_2);
		
		Placard_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		Placard_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Placard_lbl_copyright_1.setBounds(214, 236, 170, 20);
		contentPane.add(Placard_lbl_copyright_1);
	}
	
	public boolean dataValidation(String line_1, String line_2, String printCount, Settings settings) {
		if(line_1.length()>14) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Line 1 exceed length limit, a string less than 14 Characters is recommended.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(line_2.length()>14) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Line 2 exceed length limit, a string less than 14 Characters is recommended.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			Integer.parseInt(printCount);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Print count missing or Invalid input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
