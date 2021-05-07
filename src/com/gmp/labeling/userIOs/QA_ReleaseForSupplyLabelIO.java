package com.gmp.labeling.userIOs;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Material;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.QA_ApprovedForSupply;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class QA_ReleaseForSupplyLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4628810846800582849L;
	private JPanel contentPane;
	private JTextField releaseForSupply_itemCode;
	private JTextField releaseForSupply_itemName;
	private JTextField releaseForSupply_batch;
	private JTextField releaseForSupply_printCount;

	public QA_ReleaseForSupplyLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel releaseForSupply_lbl_title = new JLabel("Release For Supply(QA only)");
		releaseForSupply_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		releaseForSupply_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		releaseForSupply_lbl_title.setBounds(88, 28, 300, 30);
		contentPane.add(releaseForSupply_lbl_title);
		
		JLabel releaseForSupply_lbl_itemCode = new JLabel("Product Code:");
		releaseForSupply_lbl_itemCode.setBounds(50, 80, 90, 20);
		contentPane.add(releaseForSupply_lbl_itemCode);
		
		releaseForSupply_itemCode = new JTextField();
		releaseForSupply_itemCode.setBounds(140, 80, 180, 20);
		contentPane.add(releaseForSupply_itemCode);
		releaseForSupply_itemCode.setColumns(10);
		
		releaseForSupply_itemName = new JTextField();
		releaseForSupply_itemName.setBounds(140, 120, 240, 20);
		contentPane.add(releaseForSupply_itemName);
		releaseForSupply_itemName.setColumns(10);
		
		List<Material> materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));
		
		Button releaseForSupply_btn_check = new Button("Check");
		releaseForSupply_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = releaseForSupply_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		releaseForSupply_itemName.setText(JsonParser.takeoffComma(temp.getItem_name()));
		        		releaseForSupply_itemCode.setText(itemCode_value);
		        		break;
		        	}	        	
		        }
			}
		});
		releaseForSupply_btn_check.setBounds(320, 80, 60, 20);
		contentPane.add(releaseForSupply_btn_check);
		
		JLabel releaseForSupply_lbl_itemName = new JLabel("Product Name:");
		releaseForSupply_lbl_itemName.setBounds(50, 120, 90, 20);
		contentPane.add(releaseForSupply_lbl_itemName);
		

		
		JLabel releaseForSupply_lbl_batch = new JLabel("Batch:");
		releaseForSupply_lbl_batch.setBounds(50, 160, 90, 20);
		contentPane.add(releaseForSupply_lbl_batch);
		
		releaseForSupply_batch = new JTextField();
		releaseForSupply_batch.setBounds(140, 160, 180, 20);
		contentPane.add(releaseForSupply_batch);
		releaseForSupply_batch.setColumns(10);
		
		JLabel releaseForSupply_lbl_printCount = new JLabel("Print Count:");
		releaseForSupply_lbl_printCount.setBounds(50, 200, 90, 20);
		contentPane.add(releaseForSupply_lbl_printCount);
		
		releaseForSupply_printCount = new JTextField();
		releaseForSupply_printCount.setBounds(140, 200, 86, 20);
		contentPane.add(releaseForSupply_printCount);
		releaseForSupply_printCount.setColumns(10);
		
		JRadioButton releaseForSupply_rdbtn_blankLabel = new JRadioButton("Blank Label");
		releaseForSupply_rdbtn_blankLabel.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(releaseForSupply_rdbtn_blankLabel.isSelected()) {
					releaseForSupply_itemCode.disable();
					releaseForSupply_itemCode.setBackground(Color.GRAY);
					releaseForSupply_itemName.disable();
					releaseForSupply_itemName.setBackground(Color.GRAY);
					releaseForSupply_batch.disable();
					releaseForSupply_batch.setBackground(Color.GRAY);
				}else {
					releaseForSupply_itemCode.enable();
					releaseForSupply_itemCode.setBackground(Color.WHITE);
					releaseForSupply_itemName.enable();
					releaseForSupply_itemName.setBackground(Color.WHITE);
					releaseForSupply_batch.enable();
					releaseForSupply_batch.setBackground(Color.WHITE);
				}
			}
		});
		releaseForSupply_rdbtn_blankLabel.setBounds(250, 200, 120, 20);
		contentPane.add(releaseForSupply_rdbtn_blankLabel);
		
		JButton releaseForSupply_btn_Settings = new JButton("Settings");
		releaseForSupply_btn_Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		releaseForSupply_btn_Settings.setBounds(30, 250, 85, 20);
		contentPane.add(releaseForSupply_btn_Settings);
		
		JButton releaseForSupply_btn_Update = new JButton("Update");
		releaseForSupply_btn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings,"stk.csv", "materiallistpath");
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		releaseForSupply_btn_Update.setBounds(250, 250, 85, 20);
		contentPane.add(releaseForSupply_btn_Update);
		
		JButton releaseForSupply_btn_Print = new JButton("Print");
		releaseForSupply_btn_Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(releaseForSupply_rdbtn_blankLabel.isSelected(), releaseForSupply_itemCode.getText(), releaseForSupply_itemName.getText(), releaseForSupply_batch.getText(), releaseForSupply_printCount.getText(), settings)) {
				int printNumber = Integer.valueOf(releaseForSupply_printCount.getText());
				if(releaseForSupply_rdbtn_blankLabel.isSelected()) {
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new QA_ApprovedForSupply("","",""));
						i++;
					}
				}else {									
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new QA_ApprovedForSupply(releaseForSupply_itemCode.getText(), releaseForSupply_itemName.getText(), releaseForSupply_batch.getText()));
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
		releaseForSupply_btn_Print.setBounds(140, 250, 85, 20);
		contentPane.add(releaseForSupply_btn_Print);
		
		JButton releaseForSupply_btn_Back = new JButton("Back");
		releaseForSupply_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		releaseForSupply_btn_Back.setBounds(360, 250, 85, 20);
		contentPane.add(releaseForSupply_btn_Back);
		
		JLabel releaseForSupply_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		releaseForSupply_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		releaseForSupply_lbl_copyright_1.setBounds(295, 281, 170, 20);
		contentPane.add(releaseForSupply_lbl_copyright_1);
		
		JLabel releaseForSupply_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		releaseForSupply_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		releaseForSupply_lbl_copyright_2.setBounds(194, 296, 280, 20);
		contentPane.add(releaseForSupply_lbl_copyright_2);
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
