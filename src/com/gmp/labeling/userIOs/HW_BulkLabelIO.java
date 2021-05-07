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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import com.gmp.labeling.printModels.HW_BulkLabel;
import com.gmp.labeling.printModels.PrintingQueue;

public class HW_BulkLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8398256615777742953L;
	private JPanel contentPane;
	private JTextField bulkLabel_itemCode;
	private JTextField bulkLabel_batch;
	private JTextField bulkLabel_quantity;
	private JTextField bulkLabel_manufactureDate;
	private JTextField bulkLabel_expiryDate;
	private JTextField bulkLabel_noContainer_1;
	private String itemUnit;
	private List<Material> materials;
	private JTextField bulkLabel_noContainer_2;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HW_BulkLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 460);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel bulkLabel_lbl_title = new JLabel("Bulk Label for 60 HW");
		bulkLabel_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		bulkLabel_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		bulkLabel_lbl_title.setBounds(68, 18, 360, 30);
		contentPane.add(bulkLabel_lbl_title);
		
		JLabel bulkLabel_lbl_itemCode = new JLabel("Item Code:");
		bulkLabel_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(bulkLabel_lbl_itemCode);
		
		JLabel bulkLabel_lbl_itemName = new JLabel("Item Name:");
		bulkLabel_lbl_itemName.setBounds(50, 100, 90, 20);
		contentPane.add(bulkLabel_lbl_itemName);
		
		JLabel bulkLabel_lbl_batch = new JLabel("Batch No:");
		bulkLabel_lbl_batch.setBounds(50, 140, 90, 20);
		contentPane.add(bulkLabel_lbl_batch);
		
		JLabel bulkLabel_lbl_quantity = new JLabel("Quantity:");
		bulkLabel_lbl_quantity.setBounds(50, 180, 90, 20);
		contentPane.add(bulkLabel_lbl_quantity);
		
		JLabel bulkLabel_lbl_manufactureDate = new JLabel("Manufacture Date:");
		bulkLabel_lbl_manufactureDate.setBounds(50, 220, 110, 20);
		contentPane.add(bulkLabel_lbl_manufactureDate);
		
		JLabel bulkLabel_lbl_expiryDate = new JLabel("Expiry Date:");
		bulkLabel_lbl_expiryDate.setBounds(50, 260, 90, 20);
		contentPane.add(bulkLabel_lbl_expiryDate);
		
		JLabel bulkLabel_lbl_noContainer = new JLabel("No. of Pallets:");
		bulkLabel_lbl_noContainer.setBounds(50, 300, 110, 20);
		contentPane.add(bulkLabel_lbl_noContainer);
		
		JLabel bulkLabel_lbl_itemUnit = new JLabel("");
		bulkLabel_lbl_itemUnit.setBounds(340, 180, 80, 20);
		contentPane.add(bulkLabel_lbl_itemUnit);

		
		bulkLabel_itemCode = new JTextField();
		bulkLabel_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(bulkLabel_itemCode);
		bulkLabel_itemCode.setColumns(10);
		
		bulkLabel_batch = new JTextField();
		bulkLabel_batch.setBounds(160, 140, 180, 20);
		contentPane.add(bulkLabel_batch);
		bulkLabel_batch.setColumns(10);
		
		bulkLabel_quantity = new JTextField();
		bulkLabel_quantity.setBounds(160, 180, 180, 20);
		contentPane.add(bulkLabel_quantity);
		bulkLabel_quantity.setColumns(10);
		
		bulkLabel_manufactureDate = new JTextField();
		bulkLabel_manufactureDate.setBounds(160, 220, 180, 20);
		contentPane.add(bulkLabel_manufactureDate);
		bulkLabel_manufactureDate.setColumns(10);
		
		bulkLabel_expiryDate = new JTextField();
		bulkLabel_expiryDate.setBounds(160, 260, 180, 20);
		contentPane.add(bulkLabel_expiryDate);
		bulkLabel_expiryDate.setColumns(10);
		
		bulkLabel_noContainer_1 = new JTextField();
		bulkLabel_noContainer_1.setBounds(160, 300, 60, 20);
		contentPane.add(bulkLabel_noContainer_1);
		bulkLabel_noContainer_1.setColumns(10);
		
		JLabel bulkLabel_lbl_of = new JLabel("of");
		bulkLabel_lbl_of.setHorizontalAlignment(SwingConstants.CENTER);
		bulkLabel_lbl_of.setBounds(220, 300, 40, 20);
		contentPane.add(bulkLabel_lbl_of);
		
		bulkLabel_noContainer_2 = new JTextField();
		bulkLabel_noContainer_2.setColumns(10);
		bulkLabel_noContainer_2.setBounds(260, 300, 80, 20);
		contentPane.add(bulkLabel_noContainer_2);
		
		JRadioButton bulkLabel_rdbtn_printAll = new JRadioButton("Print All");
		bulkLabel_rdbtn_printAll.setBounds(340, 300, 80, 20);
		contentPane.add(bulkLabel_rdbtn_printAll);
		
		materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JComboBox bulkLabel_itemName = new JComboBox();
		bulkLabel_itemName.setModel(new DefaultComboBoxModel(sarray));
		bulkLabel_itemName.setMaximumRowCount(10);
		bulkLabel_itemName.setSelectedItem(null);
		bulkLabel_itemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) bulkLabel_itemName.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		bulkLabel_itemCode.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		bulkLabel_lbl_itemUnit.setText(itemUnit);
		        		break;
		        	}	        	
		        }
			}
		});
		bulkLabel_itemName.setBounds(160, 100, 180, 20);
		contentPane.add(bulkLabel_itemName);
		
		Button bulkLabel_btn_check = new Button("Check");
		bulkLabel_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = bulkLabel_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				boolean itemcode_exist = false;
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		bulkLabel_itemName.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		bulkLabel_lbl_itemUnit.setText(itemUnit);
		        		bulkLabel_itemCode.setText(itemCode_value);
		        		itemcode_exist = true;
		        		break;
		        	}	        	
		        }	
				if(!itemcode_exist) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Item code does not exist. Please click \"Update\" to get latest database.\n If problem cannot be solved by update, please contact IT for the case.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton bulkLabel_btn_settings = new JButton("Settings");
		bulkLabel_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		bulkLabel_btn_settings.setBounds(30, 340, 90, 20);
		contentPane.add(bulkLabel_btn_settings);
		
		JButton bulkLabel_btn_print = new JButton("Print");
		bulkLabel_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(bulkLabel_itemCode.getText(), bulkLabel_itemName.getSelectedItem().toString(), materials, bulkLabel_batch.getText(), bulkLabel_quantity.getText(), bulkLabel_manufactureDate.getText(), 
						bulkLabel_expiryDate.getText(), bulkLabel_rdbtn_printAll.isSelected(), bulkLabel_noContainer_1.getText(), bulkLabel_noContainer_2.getText(), settings)) {
					int printNumber = Integer.valueOf(bulkLabel_noContainer_2.getText());
					if(bulkLabel_rdbtn_printAll.isSelected()) {
						if(bulkLabel_noContainer_1.getText().equals("")) {
							int i = 1;
							while(i <= printNumber) {
								printqueue.addLabelToQueue(new HW_BulkLabel(bulkLabel_itemCode.getText(), bulkLabel_itemName.getSelectedItem().toString(), bulkLabel_batch.getText(), bulkLabel_quantity.getText(), itemUnit,
										bulkLabel_manufactureDate.getText(), bulkLabel_expiryDate.getText(), String.valueOf(i), bulkLabel_noContainer_2.getText(), settings.getSetting().getLogined_user()));							
								i++;
							}
						}else {
							int currentIndex = Integer.valueOf(bulkLabel_noContainer_1.getText());
							int i = currentIndex;
							while(i <= printNumber) {
								printqueue.addLabelToQueue(new HW_BulkLabel(bulkLabel_itemCode.getText(), bulkLabel_itemName.getSelectedItem().toString(), bulkLabel_batch.getText(), bulkLabel_quantity.getText(), itemUnit,
										bulkLabel_manufactureDate.getText(), bulkLabel_expiryDate.getText(), String.valueOf(i), bulkLabel_noContainer_2.getText(), settings.getSetting().getLogined_user()));											
								i++;
							}
						}																		
					}else {
						int currentNumber = Integer.valueOf(bulkLabel_noContainer_1.getText());
						printqueue.addLabelToQueue(new HW_BulkLabel(bulkLabel_itemCode.getText(), bulkLabel_itemName.getSelectedItem().toString(), bulkLabel_batch.getText(), bulkLabel_quantity.getText(), itemUnit,
								bulkLabel_manufactureDate.getText(), bulkLabel_expiryDate.getText(), String.valueOf(currentNumber), bulkLabel_noContainer_2.getText(), settings.getSetting().getLogined_user()));		
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
		bulkLabel_btn_print.setBounds(140, 340, 90, 20);
		contentPane.add(bulkLabel_btn_print);

		
		JButton bulkLabel_btn_update = new JButton("Update");
		bulkLabel_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings,"stk.csv", "materiallistpath");
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		bulkLabel_btn_update.setBounds(250, 340, 90, 20);
		contentPane.add(bulkLabel_btn_update);
		
		JButton bulkLabel_btn_back = new JButton("Back");
		bulkLabel_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		bulkLabel_btn_back.setBounds(360, 340, 90, 20);
		contentPane.add(bulkLabel_btn_back);
		
		JLabel label = new JLabel("Designed and Implemented by Lee.L");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(300, 375, 170, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(200, 390, 280, 20);
		contentPane.add(label_1);
		

		bulkLabel_btn_check.setBounds(340, 60, 70, 20);
		contentPane.add(bulkLabel_btn_check);
		
		Button bulkLabel_btn_info_releasedDate = new Button("i");
		bulkLabel_btn_info_releasedDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using the system date."
					    );
			}
		});
		bulkLabel_btn_info_releasedDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		bulkLabel_btn_info_releasedDate.setBackground(Color.LIGHT_GRAY);
		bulkLabel_btn_info_releasedDate.setBounds(340, 220, 20, 20);
		contentPane.add(bulkLabel_btn_info_releasedDate);
		
		Button bulkLabel_btn_info_expiredDate = new Button("i");
		bulkLabel_btn_info_expiredDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018."
					    );
			}
		});
		bulkLabel_btn_info_expiredDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		bulkLabel_btn_info_expiredDate.setBackground(Color.LIGHT_GRAY);
		bulkLabel_btn_info_expiredDate.setBounds(340, 260, 20, 20);
		contentPane.add(bulkLabel_btn_info_expiredDate);
		

		

	}
	
	public boolean dataValidation(String itemCode, String itemName, List<Material> materials, String batch, String quantity, String manufactureDate, String expiryDate, boolean printMode, String printCount_1, String printCount_2, Settings settings) {
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			boolean itemcodeExist = false;
			for(Material code_temp: materials) {
				if(JsonParser.takeoffComma(code_temp.getItem_code()).equals(itemCode.replaceAll("([a-z])", "$1").toUpperCase())) {
					itemcodeExist = true;
					break;
				}
			}
			if(!itemcodeExist) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Item code does not exist ",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if(itemName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item name missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		for(Material m : materials) {
			if(JsonParser.takeoffComma(m.getItem_code()).equals(itemCode.replaceAll("([a-z])", "$1").toUpperCase())) {
				if(!JsonParser.takeoffComma(m.getItem_name()).equals(itemName)) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Item Code and Item Name are not correlated. Please Click \"Check\" to get the correct Item name in the system.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}else {
					break;
				}			
			}
		}
		if(batch.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Batch No. missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			if(batch.length()!=6) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid Batch format.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}else {
				char[] temp = batch.toCharArray();
				String head = String.valueOf(temp[0]).toUpperCase();
				if(!head.equals("H")) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid Batch format.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}
				String number = String.valueOf(temp[1])+String.valueOf(temp[2])+String.valueOf(temp[3])+String.valueOf(temp[4])+String.valueOf(temp[5]);
				try {
					@SuppressWarnings("unused")
					int test = Integer.valueOf(number);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid Batch format.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
		
		try {
			if(Float.parseFloat(quantity)<0) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Item Qty missing or Invalid input.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item Qty missing or Invalid input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(!manufactureDate.equals("")) {
		char[] temp = manufactureDate.toCharArray();
		if(temp.length == 10) {
		String day = String.valueOf(temp[0])+String.valueOf(temp[1]);
		String month = String.valueOf(temp[3]) + String.valueOf(temp[4]);
		String year = String.valueOf(temp[6])+ String.valueOf(temp[7]) + String.valueOf(temp[8]) + String.valueOf(temp[9]);
		if(temp[2]!='/' || temp[5]!='/') {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
					"Date input format error.",
					"Inane error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		int dayint = 0;
		int monthint = 0;
		int yearint = 0;
		try {
			dayint = Integer.parseInt(day);
			monthint = Integer.parseInt(month);
			yearint = Integer.parseInt(year);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Date input error.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(monthint<=0 || monthint > 12) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Date month value input error.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(monthint<= 7 && monthint%2==1){
			if(dayint <= 0 || dayint > 31) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date day value input error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}			
		}else if(monthint<=7 && monthint%2==0 && monthint != 2){
			if(dayint <= 0 || dayint > 30) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date day value input error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}else if (monthint == 2) {
			int realyear = yearint;
			if(realyear%400==0 || (realyear%4 ==0 && realyear%100 != 0)) {
				if(dayint <= 0 || dayint > 29) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Date day value input error.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}else {
				if(dayint <= 0 || dayint > 28) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Date day value input error.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}else if(monthint%2 == 0) {
			if(dayint <= 0 || dayint > 31) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date day value input error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}else {
			if(dayint <= 0 || dayint > 30) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date day value input error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			    }
		     }
		}else {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Date input format error.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		}
		
		if(!expiryDate.equals("")) {
		char[] temp = expiryDate.toCharArray();
		if(temp.length == 10) {
		String day = String.valueOf(temp[0])+String.valueOf(temp[1]);
		String month = String.valueOf(temp[3]) + String.valueOf(temp[4]);
		String year = String.valueOf(temp[6])+ String.valueOf(temp[7]) + String.valueOf(temp[8]) + String.valueOf(temp[9]);
		if(temp[2]!='/' || temp[5]!='/') {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
					"Date input format error.",
					"Inane error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		int dayint = 0;
		int monthint = 0;
		int yearint = 0;
		try {
			dayint = Integer.parseInt(day);
			monthint = Integer.parseInt(month);
			yearint = Integer.parseInt(year);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Date input error.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(monthint<=0 || monthint > 12) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Date month value input error.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(monthint<= 7 && monthint%2==1){
			if(dayint <= 0 || dayint > 31) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date day value input error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}			
		}else if(monthint<=7 && monthint%2==0 && monthint != 2){
			if(dayint <= 0 || dayint > 30) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date day value input error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}else if (monthint == 2) {
			int realyear = yearint;
			if(realyear%400==0 || (realyear%4 ==0 && realyear%100 != 0)) {
				if(dayint <= 0 || dayint > 29) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Date day value input error.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}else {
				if(dayint <= 0 || dayint > 28) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Date day value input error.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}else if(monthint%2 == 0) {
			if(dayint <= 0 || dayint > 31) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date day value input error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}else {
			if(dayint <= 0 || dayint > 30) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date day value input error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			    }
		     }
		}else {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Date input format error.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		}else {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Expiry Date Missing..",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
        if(printMode) {
        	if(printCount_1.equals("")) {
        		try {
        			@SuppressWarnings("unused")
					int test_2 = Integer.valueOf(printCount_2);
        		}catch(Exception e) {
        			JOptionPane.showMessageDialog(settings.getComponentPage(),
        				    "Invalid No Container Input.",
        				    "Inane error",
        				    JOptionPane.ERROR_MESSAGE);
        			return false;
        		}
        	}else {
        		try {
        			@SuppressWarnings("unused")
					int test_1 = Integer.valueOf(printCount_1);
        			@SuppressWarnings("unused")
					int test_2 = Integer.valueOf(printCount_2);
        		}catch(Exception e) {
        			JOptionPane.showMessageDialog(settings.getComponentPage(),
        				    "Invalid No Container Input.",
        				    "Inane error",
        				    JOptionPane.ERROR_MESSAGE);
        			return false;
        		}
        	}
        }else {
        	try {
    			@SuppressWarnings("unused")
				int test_1 = Integer.valueOf(printCount_1);
    			@SuppressWarnings("unused")
				int test_2 = Integer.valueOf(printCount_2);
    		}catch(Exception e) {
    			JOptionPane.showMessageDialog(settings.getComponentPage(),
    				    "Invalid No Container Input.",
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

