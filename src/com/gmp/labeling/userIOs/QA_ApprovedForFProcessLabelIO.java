package com.gmp.labeling.userIOs;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Material;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.QA_ApprovedForFurtherProcessing;

public class QA_ApprovedForFProcessLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7585973513514916549L;
	private JPanel contentPane;
	private JTextField approvedForFProcess_itemCode;
	private JTextField approvedForFProcess_itemName;
	private JTextField approvedForFProcess_batch;
	private JTextField approvedForFProcess_printCount;

	public QA_ApprovedForFProcessLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel approvedForFProcess_lbl_title = new JLabel("Approved for further processing(QA only)");
		approvedForFProcess_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		approvedForFProcess_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		approvedForFProcess_lbl_title.setBounds(28, 28, 440, 30);
		contentPane.add(approvedForFProcess_lbl_title);
		
		JLabel approvedForFProcess_lbl_itemCode = new JLabel("Product Code:");
		approvedForFProcess_lbl_itemCode.setBounds(50, 80, 90, 20);
		contentPane.add(approvedForFProcess_lbl_itemCode);
		
		approvedForFProcess_itemCode = new JTextField();
		approvedForFProcess_itemCode.setBounds(140, 80, 180, 20);
		contentPane.add(approvedForFProcess_itemCode);
		approvedForFProcess_itemCode.setColumns(10);
		
		approvedForFProcess_itemName = new JTextField();
		approvedForFProcess_itemName.setBounds(140, 120, 240, 20);
		contentPane.add(approvedForFProcess_itemName);
		approvedForFProcess_itemName.setColumns(10);
		
		List<Material> materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));
		
		Button approvedForFProcess_btn_check = new Button("Check");
		approvedForFProcess_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = approvedForFProcess_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		approvedForFProcess_itemName.setText(JsonParser.takeoffComma(temp.getItem_name()));
		        		approvedForFProcess_itemCode.setText(itemCode_value);
		        		break;
		        	}	        	
		        }
			}
		});
		approvedForFProcess_btn_check.setBounds(320, 80, 60, 20);
		contentPane.add(approvedForFProcess_btn_check);
		
		JLabel approvedForFProcess_lbl_itemName = new JLabel("Product Name:");
		approvedForFProcess_lbl_itemName.setBounds(50, 120, 90, 20);
		contentPane.add(approvedForFProcess_lbl_itemName);
		

		
		JLabel approvedForFProcess_lbl_batch = new JLabel("Batch:");
		approvedForFProcess_lbl_batch.setBounds(50, 160, 90, 20);
		contentPane.add(approvedForFProcess_lbl_batch);
		
		approvedForFProcess_batch = new JTextField();
		approvedForFProcess_batch.setBounds(140, 160, 180, 20);
		contentPane.add(approvedForFProcess_batch);
		approvedForFProcess_batch.setColumns(10);
		
		JLabel approvedForFProcess_lbl_printCount = new JLabel("Print Count:");
		approvedForFProcess_lbl_printCount.setBounds(50, 200, 90, 20);
		contentPane.add(approvedForFProcess_lbl_printCount);
		
		approvedForFProcess_printCount = new JTextField();
		approvedForFProcess_printCount.setBounds(140, 200, 86, 20);
		contentPane.add(approvedForFProcess_printCount);
		approvedForFProcess_printCount.setColumns(10);
		
		JRadioButton approvedForFProcess_rdbtn_blankLabel = new JRadioButton("Blank Label");
		approvedForFProcess_rdbtn_blankLabel.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(approvedForFProcess_rdbtn_blankLabel.isSelected()) {
					approvedForFProcess_itemCode.disable();
					approvedForFProcess_itemCode.setBackground(Color.GRAY);
					approvedForFProcess_itemName.disable();
					approvedForFProcess_itemName.setBackground(Color.GRAY);
					approvedForFProcess_batch.disable();
					approvedForFProcess_batch.setBackground(Color.GRAY);
				}else {
					approvedForFProcess_itemCode.enable();
					approvedForFProcess_itemCode.setBackground(Color.WHITE);
					approvedForFProcess_itemName.enable();
					approvedForFProcess_itemName.setBackground(Color.WHITE);
					approvedForFProcess_batch.enable();
					approvedForFProcess_batch.setBackground(Color.WHITE);
				}
			}
		});
		approvedForFProcess_rdbtn_blankLabel.setBounds(250, 200, 120, 20);
		contentPane.add(approvedForFProcess_rdbtn_blankLabel);
		
		JButton approvedForFProcess_btn_Settings = new JButton("Settings");
		approvedForFProcess_btn_Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		approvedForFProcess_btn_Settings.setBounds(30, 250, 85, 20);
		contentPane.add(approvedForFProcess_btn_Settings);
		
		JButton approvedForFProcess_btn_Update = new JButton("Update");
		approvedForFProcess_btn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		approvedForFProcess_btn_Update.setBounds(250, 250, 85, 20);
		contentPane.add(approvedForFProcess_btn_Update);
		
		JButton approvedForFProcess_btn_Print = new JButton("Print");
		approvedForFProcess_btn_Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(approvedForFProcess_rdbtn_blankLabel.isSelected(), approvedForFProcess_itemCode.getText(), approvedForFProcess_itemName.getText(), approvedForFProcess_batch.getText(), approvedForFProcess_printCount.getText(), settings)) {
				int printNumber = Integer.valueOf(approvedForFProcess_printCount.getText());
				if(approvedForFProcess_rdbtn_blankLabel.isSelected()) {
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new QA_ApprovedForFurtherProcessing("","",""));
						i++;
					}
				}else {									
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new QA_ApprovedForFurtherProcessing(approvedForFProcess_itemCode.getText(), approvedForFProcess_itemName.getText(), approvedForFProcess_batch.getText()));
						i++;
					}
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
		approvedForFProcess_btn_Print.setBounds(140, 250, 85, 20);
		contentPane.add(approvedForFProcess_btn_Print);
		
		JButton approvedForFProcess_btn_Back = new JButton("Back");
		approvedForFProcess_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		approvedForFProcess_btn_Back.setBounds(360, 250, 85, 20);
		contentPane.add(approvedForFProcess_btn_Back);
		
		JLabel approvedForFProcess_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		approvedForFProcess_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		approvedForFProcess_lbl_copyright_1.setBounds(295, 281, 170, 20);
		contentPane.add(approvedForFProcess_lbl_copyright_1);
		
		JLabel approvedForFProcess_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		approvedForFProcess_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		approvedForFProcess_lbl_copyright_2.setBounds(194, 296, 280, 20);
		contentPane.add(approvedForFProcess_lbl_copyright_2);
	}
	
	public boolean dataValidation(boolean blank, String itemCode, String itemName, String batch, String printCount, Settings settings) {
		if(blank) {
			try {
				Float.parseFloat(printCount);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Printing Count must be number.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}	
		}else{
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(itemName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Product name cannot be blank.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			if(itemName.length()>80) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Product name cannot exceed 80 characters.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		
		if(batch.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Batch number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			Float.parseFloat(printCount);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Printing Count must be number.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}	
		}
		return true;
	}
	
	public List<Material> inputItemdata(String path) {
		String csvFile = path;
        String line = "";
        String cvsSplitBy = ",";
        List<Material> materials = new ArrayList<Material>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {            	
            	if(line.equals("")) {
            		//first line dont input
            	}else {
            	String[] items = line.split(cvsSplitBy);
                Material m = new Material();
                m.setItem_code(items[0]);
                m.setItem_name(items[1]);
                m.setItem_unit(items[2]);
                materials.add(m);
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return materials;
	}

}
