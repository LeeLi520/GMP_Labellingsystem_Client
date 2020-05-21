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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Material;
import com.gmp.labeling.printModels.HW_ReleaseForSupplyLabel;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.JRadioButton;

public class HW_ReleaseForSupplyLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -257392917612682230L;
	private JPanel contentPane;
	private JTextField releaseForSupply_itemCode;
	private JTextField releaseForSupply_batch;
	private JTextField releaseForSupply_quantity;
	private JTextField releaseForSupply_manufactureDate;
	private JTextField releaseForSupply_expiryDate;
	private JTextField releaseForSupply_noContainer_1;
	private String itemUnit;
	private List<Material> materials;
	private JTextField releaseForSupply_noContainer_2;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HW_ReleaseForSupplyLabelIO(Settings settings, PrintingQueue printqueue) {
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
		
		JLabel releaseForSupply_lbl_title = new JLabel("HW Release for Supply Label");
		releaseForSupply_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		releaseForSupply_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		releaseForSupply_lbl_title.setBounds(68, 18, 360, 30);
		contentPane.add(releaseForSupply_lbl_title);
		
		JLabel releaseForSupply_lbl_itemCode = new JLabel("Item Code:");
		releaseForSupply_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(releaseForSupply_lbl_itemCode);
		
		JLabel releaseForSupply_lbl_itemName = new JLabel("Item Name:");
		releaseForSupply_lbl_itemName.setBounds(50, 100, 90, 20);
		contentPane.add(releaseForSupply_lbl_itemName);
		
		JLabel releaseForSupply_lbl_batch = new JLabel("Batch No:");
		releaseForSupply_lbl_batch.setBounds(50, 140, 90, 20);
		contentPane.add(releaseForSupply_lbl_batch);
		
		JLabel releaseForSupply_lbl_quantity = new JLabel("Quantity:");
		releaseForSupply_lbl_quantity.setBounds(50, 180, 90, 20);
		contentPane.add(releaseForSupply_lbl_quantity);
		
		JLabel releaseForSupply_lbl_manufactureDate = new JLabel("Manufacture Date:");
		releaseForSupply_lbl_manufactureDate.setBounds(50, 220, 110, 20);
		contentPane.add(releaseForSupply_lbl_manufactureDate);
		
		JLabel releaseForSupply_lbl_expiryDate = new JLabel("Expiry Date:");
		releaseForSupply_lbl_expiryDate.setBounds(50, 260, 90, 20);
		contentPane.add(releaseForSupply_lbl_expiryDate);
		
		JLabel releaseForSupply_lbl_noContainer = new JLabel("No. of Pallets:");
		releaseForSupply_lbl_noContainer.setBounds(50, 300, 110, 20);
		contentPane.add(releaseForSupply_lbl_noContainer);
		
		JLabel releaseForSupply_lbl_itemUnit = new JLabel("");
		releaseForSupply_lbl_itemUnit.setBounds(340, 180, 80, 20);
		contentPane.add(releaseForSupply_lbl_itemUnit);

		
		releaseForSupply_itemCode = new JTextField();
		releaseForSupply_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(releaseForSupply_itemCode);
		releaseForSupply_itemCode.setColumns(10);
		
		releaseForSupply_batch = new JTextField();
		releaseForSupply_batch.setBounds(160, 140, 180, 20);
		contentPane.add(releaseForSupply_batch);
		releaseForSupply_batch.setColumns(10);
		
		releaseForSupply_quantity = new JTextField();
		releaseForSupply_quantity.setBounds(160, 180, 180, 20);
		contentPane.add(releaseForSupply_quantity);
		releaseForSupply_quantity.setColumns(10);
		
		releaseForSupply_manufactureDate = new JTextField();
		releaseForSupply_manufactureDate.setBounds(160, 220, 180, 20);
		contentPane.add(releaseForSupply_manufactureDate);
		releaseForSupply_manufactureDate.setColumns(10);
		
		releaseForSupply_expiryDate = new JTextField();
		releaseForSupply_expiryDate.setBounds(160, 260, 180, 20);
		contentPane.add(releaseForSupply_expiryDate);
		releaseForSupply_expiryDate.setColumns(10);
		
		releaseForSupply_noContainer_1 = new JTextField();
		releaseForSupply_noContainer_1.setBounds(160, 300, 60, 20);
		contentPane.add(releaseForSupply_noContainer_1);
		releaseForSupply_noContainer_1.setColumns(10);
		
		JLabel releaseForSupply_lbl_of = new JLabel("of");
		releaseForSupply_lbl_of.setHorizontalAlignment(SwingConstants.CENTER);
		releaseForSupply_lbl_of.setBounds(220, 300, 40, 20);
		contentPane.add(releaseForSupply_lbl_of);
		
		releaseForSupply_noContainer_2 = new JTextField();
		releaseForSupply_noContainer_2.setColumns(10);
		releaseForSupply_noContainer_2.setBounds(260, 300, 80, 20);
		contentPane.add(releaseForSupply_noContainer_2);
		
		JRadioButton releaseForSupply_rdbtn_printAll = new JRadioButton("Print All");
		releaseForSupply_rdbtn_printAll.setBounds(340, 300, 80, 20);
		contentPane.add(releaseForSupply_rdbtn_printAll);
		
		materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JComboBox releaseForSupply_itemName = new JComboBox();
		releaseForSupply_itemName.setModel(new DefaultComboBoxModel(sarray));
		releaseForSupply_itemName.setMaximumRowCount(10);
		releaseForSupply_itemName.setSelectedItem(null);
		releaseForSupply_itemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) releaseForSupply_itemName.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		releaseForSupply_itemCode.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		releaseForSupply_lbl_itemUnit.setText(itemUnit);
		        		break;
		        	}	        	
		        }
			}
		});
		releaseForSupply_itemName.setBounds(160, 100, 180, 20);
		contentPane.add(releaseForSupply_itemName);
		
		Button releaseForSupply_btn_check = new Button("Check");
		releaseForSupply_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = releaseForSupply_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				boolean itemcode_exist = false;
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		releaseForSupply_itemName.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		releaseForSupply_lbl_itemUnit.setText(itemUnit);
		        		releaseForSupply_itemCode.setText(itemCode_value);
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
		
		JButton releaseForSupply_btn_settings = new JButton("Settings");
		releaseForSupply_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		releaseForSupply_btn_settings.setBounds(30, 340, 90, 20);
		contentPane.add(releaseForSupply_btn_settings);
		
		JButton releaseForSupply_btn_print = new JButton("Print");
		releaseForSupply_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(releaseForSupply_itemCode.getText(), releaseForSupply_itemName.getSelectedItem().toString(), materials, releaseForSupply_batch.getText(), releaseForSupply_quantity.getText(), releaseForSupply_manufactureDate.getText(), 
						releaseForSupply_expiryDate.getText(), releaseForSupply_rdbtn_printAll.isSelected(), releaseForSupply_noContainer_1.getText(), releaseForSupply_noContainer_2.getText(), settings)) {
					int printNumber = Integer.valueOf(releaseForSupply_noContainer_2.getText());
					if(releaseForSupply_rdbtn_printAll.isSelected()) {
						if(releaseForSupply_noContainer_1.getText().equals("")) {
							int i = 1;
							while(i <= printNumber) {
								printqueue.addLabelToQueue(new HW_ReleaseForSupplyLabel(releaseForSupply_itemCode.getText(), releaseForSupply_itemName.getSelectedItem().toString(), releaseForSupply_batch.getText(), releaseForSupply_quantity.getText(), itemUnit,
										releaseForSupply_manufactureDate.getText(), releaseForSupply_expiryDate.getText(), String.valueOf(i), releaseForSupply_noContainer_2.getText(), settings.getSetting().getLogined_user()));							
								i++;
							}
						}else {
							int currentIndex = Integer.valueOf(releaseForSupply_noContainer_1.getText());
							int i = currentIndex;
							while(i <= printNumber) {
								printqueue.addLabelToQueue(new HW_ReleaseForSupplyLabel(releaseForSupply_itemCode.getText(), releaseForSupply_itemName.getSelectedItem().toString(), releaseForSupply_batch.getText(), releaseForSupply_quantity.getText(), itemUnit,
										releaseForSupply_manufactureDate.getText(), releaseForSupply_expiryDate.getText(), String.valueOf(i), releaseForSupply_noContainer_2.getText(), settings.getSetting().getLogined_user()));											
								i++;
							}
						}																		
					}else {
						int currentNumber = Integer.valueOf(releaseForSupply_noContainer_1.getText());
						printqueue.addLabelToQueue(new HW_ReleaseForSupplyLabel(releaseForSupply_itemCode.getText(), releaseForSupply_itemName.getSelectedItem().toString(), releaseForSupply_batch.getText(), releaseForSupply_quantity.getText(), itemUnit,
								releaseForSupply_manufactureDate.getText(), releaseForSupply_expiryDate.getText(), String.valueOf(currentNumber), releaseForSupply_noContainer_2.getText(), settings.getSetting().getLogined_user()));		
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
		releaseForSupply_btn_print.setBounds(140, 340, 90, 20);
		contentPane.add(releaseForSupply_btn_print);

		
		JButton releaseForSupply_btn_update = new JButton("Update");
		releaseForSupply_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		releaseForSupply_btn_update.setBounds(250, 340, 90, 20);
		contentPane.add(releaseForSupply_btn_update);
		
		JButton releaseForSupply_btn_back = new JButton("Back");
		releaseForSupply_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		releaseForSupply_btn_back.setBounds(360, 340, 90, 20);
		contentPane.add(releaseForSupply_btn_back);
		
		JLabel label = new JLabel("Designed and Implemented by Lee.L");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(300, 375, 170, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(200, 390, 280, 20);
		contentPane.add(label_1);
		

		releaseForSupply_btn_check.setBounds(340, 60, 70, 20);
		contentPane.add(releaseForSupply_btn_check);
		
		Button releaseForSupply_btn_info_manufactureDate = new Button("i");
		releaseForSupply_btn_info_manufactureDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using the system date."
					    );
			}
		});
		releaseForSupply_btn_info_manufactureDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		releaseForSupply_btn_info_manufactureDate.setBackground(Color.LIGHT_GRAY);
		releaseForSupply_btn_info_manufactureDate.setBounds(340, 220, 20, 20);
		contentPane.add(releaseForSupply_btn_info_manufactureDate);
		
		Button releaseForSupply_btn_info_expiryDate = new Button("i");
		releaseForSupply_btn_info_expiryDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018."
					    );
			}
		});
		releaseForSupply_btn_info_expiryDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		releaseForSupply_btn_info_expiryDate.setBackground(Color.LIGHT_GRAY);
		releaseForSupply_btn_info_expiryDate.setBounds(340, 260, 20, 20);
		contentPane.add(releaseForSupply_btn_info_expiryDate);
		

		

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
