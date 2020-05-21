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
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Material;
import com.gmp.labeling.printModels.HW_ProvisionalReleaseLabel;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class HW_ProvisionalReleaseLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3374126740724347890L;
	private JPanel contentPane;
	private JTextField provisionalRelease_gin_Number;
	private JTextField provisionalRelease_item_Code;
	private JTextField provisionalRelease_quantity;
	private JTextField provisionalRelease_releaseDate;
	private JTextField provisionalRelease_noContainer_1;
	private String itemUnit;
	private List<Material> materials;
	private JTextField provisionalRelease_expired_Date;
	private JTextField provisionalRelease_PRNo;
	private JTextField provisionalRelease_noContainer_2;


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HW_ProvisionalReleaseLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel provisionalRelease_lbl_title = new JLabel("Provisional Release Label for 60 HW");
		provisionalRelease_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		provisionalRelease_lbl_title.setBounds(68, 18, 360, 30);
		contentPane.add(provisionalRelease_lbl_title);
		
		JLabel provisionalRelease_lbl_itemCode = new JLabel("Item Code:");
		provisionalRelease_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(provisionalRelease_lbl_itemCode);
		
		JLabel provisionalRelease_lbl_itemName = new JLabel("Item Name:");
		provisionalRelease_lbl_itemName.setBounds(50, 100, 90, 20);
		contentPane.add(provisionalRelease_lbl_itemName);
		
		JLabel provisionalRelease_lbl_ginNumber = new JLabel("Gin No:");
		provisionalRelease_lbl_ginNumber.setBounds(50, 140, 90, 20);
		contentPane.add(provisionalRelease_lbl_ginNumber);
		
		JLabel provisionalRelease_lbl_quantity = new JLabel("Quantity:");
		provisionalRelease_lbl_quantity.setBounds(50, 220, 90, 20);
		contentPane.add(provisionalRelease_lbl_quantity);
		
		JLabel provisionalRelease_lbl_releaseDate = new JLabel("Date Released:");
		provisionalRelease_lbl_releaseDate.setBounds(50, 260, 90, 20);
		contentPane.add(provisionalRelease_lbl_releaseDate);
		
		JLabel provisionalRelease_lbl_expdate = new JLabel("Expired Date:");
		provisionalRelease_lbl_expdate.setBounds(50, 300, 90, 20);
		contentPane.add(provisionalRelease_lbl_expdate);
				
		JLabel provisionalRelease_lbl_NoContainer_title = new JLabel("No. of Containers:");
		provisionalRelease_lbl_NoContainer_title.setBounds(50, 340, 110, 20);
		contentPane.add(provisionalRelease_lbl_NoContainer_title);
		
		provisionalRelease_gin_Number = new JTextField();
		provisionalRelease_gin_Number.setBounds(160, 140, 180, 20);
		contentPane.add(provisionalRelease_gin_Number);
		provisionalRelease_gin_Number.setColumns(10);
		
		provisionalRelease_item_Code = new JTextField();
		provisionalRelease_item_Code.setBounds(160, 60, 180, 20);
		contentPane.add(provisionalRelease_item_Code);
		provisionalRelease_item_Code.setColumns(10);
		
		provisionalRelease_quantity = new JTextField();
		provisionalRelease_quantity.setBounds(160, 220, 180, 20);
		contentPane.add(provisionalRelease_quantity);
		provisionalRelease_quantity.setColumns(10);
		
		provisionalRelease_releaseDate = new JTextField();
		provisionalRelease_releaseDate.setBounds(160, 260, 180, 20);
		contentPane.add(provisionalRelease_releaseDate);
		provisionalRelease_releaseDate.setColumns(10);
		
		provisionalRelease_expired_Date = new JTextField();
		provisionalRelease_expired_Date.setBounds(160, 300, 180, 20);
		contentPane.add(provisionalRelease_expired_Date);
		provisionalRelease_expired_Date.setColumns(10);
		
		provisionalRelease_noContainer_1 = new JTextField();
		provisionalRelease_noContainer_1.setBounds(160, 340, 60, 20);
		contentPane.add(provisionalRelease_noContainer_1);
		provisionalRelease_noContainer_1.setColumns(10);
		
		provisionalRelease_PRNo = new JTextField();
		provisionalRelease_PRNo.setBounds(160, 180, 180, 20);
		contentPane.add(provisionalRelease_PRNo);
		provisionalRelease_PRNo.setColumns(10);
		
		JLabel provisionalRelease_lbl_PRnumber = new JLabel("PR No:");
		provisionalRelease_lbl_PRnumber.setBounds(50, 180, 90, 20);
		contentPane.add(provisionalRelease_lbl_PRnumber);
		
		JLabel provisionalRelease_lbl_of = new JLabel("of");
		provisionalRelease_lbl_of.setHorizontalAlignment(SwingConstants.CENTER);
		provisionalRelease_lbl_of.setBounds(220, 340, 40, 20);
		contentPane.add(provisionalRelease_lbl_of);
		
		provisionalRelease_noContainer_2 = new JTextField();
		provisionalRelease_noContainer_2.setBounds(260, 340, 80, 20);
		contentPane.add(provisionalRelease_noContainer_2);
		provisionalRelease_noContainer_2.setColumns(10);
		
		JRadioButton provisionalRelease_rdbtn_printAll = new JRadioButton("Print All");
		provisionalRelease_rdbtn_printAll.setBounds(340, 340, 80, 20);
		contentPane.add(provisionalRelease_rdbtn_printAll);
		
		

				
		JLabel provisionalRelease_lbl_item_unit = new JLabel("");
		provisionalRelease_lbl_item_unit.setBounds(340, 220, 80, 20);
		contentPane.add(provisionalRelease_lbl_item_unit);
		
		Button provisionalRelease_btn_infoReleaseDate = new Button("i");
		provisionalRelease_btn_infoReleaseDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Released Date correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using the system date."
					    );
			}
		});
		provisionalRelease_btn_infoReleaseDate.setBackground(Color.LIGHT_GRAY);
		provisionalRelease_btn_infoReleaseDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		provisionalRelease_btn_infoReleaseDate.setBounds(340, 260, 20, 20);
		contentPane.add(provisionalRelease_btn_infoReleaseDate);
		
		Button provisionalRelease_btn_infoExpiredDate = new Button("i");
		provisionalRelease_btn_infoExpiredDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means exp N/A for the item."
					    );
			}
		});
		provisionalRelease_btn_infoExpiredDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		provisionalRelease_btn_infoExpiredDate.setBackground(Color.LIGHT_GRAY);
		provisionalRelease_btn_infoExpiredDate.setBounds(340, 300, 20, 20);
		contentPane.add(provisionalRelease_btn_infoExpiredDate);
        
//		List<Material> materials = inputItemdata(".\\src\\com\\gmp\\labeling\\resources\\datafiles\\inv.csv");
		materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JComboBox provisionalRelease_item_Name = new JComboBox();
		provisionalRelease_item_Name.setModel(new DefaultComboBoxModel(sarray));
		provisionalRelease_item_Name.setMaximumRowCount(10);
		provisionalRelease_item_Name.setSelectedItem(null);
		provisionalRelease_item_Name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) provisionalRelease_item_Name.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		provisionalRelease_item_Code.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		provisionalRelease_lbl_item_unit.setText(itemUnit);
		        		break;
		        	}	        	
		        }
			}
		});
		provisionalRelease_item_Name.setBounds(160, 100, 180, 20);
		contentPane.add(provisionalRelease_item_Name);
		
		Button provisionalRelease_btn_Check = new Button("Check");
		provisionalRelease_btn_Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = provisionalRelease_item_Code.getText().replaceAll("([a-z])", "$1").toUpperCase();
				boolean itemcode_exist = false;
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		provisionalRelease_item_Name.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		provisionalRelease_lbl_item_unit.setText(itemUnit);
		        		provisionalRelease_item_Code.setText(itemCode_value);
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
		provisionalRelease_btn_Check.setBounds(340, 60, 60, 20);
		contentPane.add(provisionalRelease_btn_Check);
		JButton provisionalRelease_btn_Settings = new JButton("Settings");
		provisionalRelease_btn_Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		
		provisionalRelease_btn_Settings.setBounds(30, 380, 90, 20);
		contentPane.add(provisionalRelease_btn_Settings);
		
		JButton provisionalRelease_btn_Print = new JButton("Print");
		provisionalRelease_btn_Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(provisionalRelease_item_Code.getText(), provisionalRelease_item_Name.getSelectedItem().toString(), materials, provisionalRelease_gin_Number.getText(), provisionalRelease_PRNo.getText(), provisionalRelease_quantity.getText(), provisionalRelease_releaseDate.getText(), 
						provisionalRelease_expired_Date.getText(), provisionalRelease_rdbtn_printAll.isSelected(), provisionalRelease_noContainer_1.getText(), provisionalRelease_noContainer_2.getText(), settings)) {
					int printNumber = Integer.valueOf(provisionalRelease_noContainer_2.getText());
					if(provisionalRelease_rdbtn_printAll.isSelected()) {
						if(provisionalRelease_noContainer_1.getText().equals("")) {
							int i = 1;
							while(i <= printNumber) {
								printqueue.addLabelToQueue(new HW_ProvisionalReleaseLabel(provisionalRelease_item_Name.getSelectedItem().toString(), provisionalRelease_item_Code.getText(), provisionalRelease_gin_Number.getText().toUpperCase(), provisionalRelease_PRNo.getText().toUpperCase(),
										provisionalRelease_releaseDate.getText(), provisionalRelease_expired_Date.getText(), provisionalRelease_quantity.getText(), itemUnit, String.valueOf(i),
										provisionalRelease_noContainer_2.getText(), settings.getSetting().getLogined_user()));								
								i++;
							}
						}else {
							int currentIndex = Integer.valueOf(provisionalRelease_noContainer_1.getText());
							int i = currentIndex;
							while(i <= printNumber) {
								printqueue.addLabelToQueue(new HW_ProvisionalReleaseLabel(provisionalRelease_item_Name.getSelectedItem().toString(), provisionalRelease_item_Code.getText(), provisionalRelease_gin_Number.getText().toUpperCase(), provisionalRelease_PRNo.getText().toUpperCase(),
										provisionalRelease_releaseDate.getText(), provisionalRelease_expired_Date.getText(), provisionalRelease_quantity.getText(), itemUnit, String.valueOf(i),
										provisionalRelease_noContainer_2.getText(), settings.getSetting().getLogined_user()));									
								i++;
							}
						}																		
					}else {
						int currentNumber = Integer.valueOf(provisionalRelease_noContainer_1.getText());
						printqueue.addLabelToQueue(new HW_ProvisionalReleaseLabel(provisionalRelease_item_Name.getSelectedItem().toString(), provisionalRelease_item_Code.getText(), provisionalRelease_gin_Number.getText().toUpperCase(), provisionalRelease_PRNo.getText().toUpperCase(),
								provisionalRelease_releaseDate.getText(), provisionalRelease_expired_Date.getText(), provisionalRelease_quantity.getText(), itemUnit, String.valueOf(currentNumber),
								provisionalRelease_noContainer_2.getText(), settings.getSetting().getLogined_user()));
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
		provisionalRelease_btn_Print.setBounds(140, 380, 90, 20);
		contentPane.add(provisionalRelease_btn_Print);
		
		JButton provisionalRelease_btn_Update = new JButton("Update");
		provisionalRelease_btn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		provisionalRelease_btn_Update.setBounds(250, 380, 90, 20);
		contentPane.add(provisionalRelease_btn_Update);
		
		JButton provisionalRelease_btn_Back = new JButton("Back");
		provisionalRelease_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		provisionalRelease_btn_Back.setBounds(360, 380, 90, 20);
		contentPane.add(provisionalRelease_btn_Back);
		
		JLabel provisionalRelease_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		provisionalRelease_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		provisionalRelease_copyright_company.setBounds(200, 430, 280, 20);
		contentPane.add(provisionalRelease_copyright_company);
		
		JLabel provisionalRelease_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		provisionalRelease_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		provisionalRelease_copyright_developer.setBounds(300, 415, 170, 20);
		contentPane.add(provisionalRelease_copyright_developer);
		
	}
	
	public boolean dataValidation(String itemCode, String itemName, List<Material> materials, String ginNumber, String PRNumber, String quantity, String releasedDate, String expiredDate, boolean printMode, String printCount_1, String printCount_2, Settings settings) {
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
		
		if(ginNumber.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "GIN number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			if(ginNumber.length()!=7) {				
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid Gin Number Format Error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}else {
				String aCode = String.valueOf(ginNumber.charAt(0))+String.valueOf(ginNumber.charAt(1));
				aCode = aCode.replaceAll("([a-z])", "$1").toUpperCase();
				if(!aCode.equals("HW")) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid Gin Number Site Code Error.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}				
			}
			
		}
		if(PRNumber.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "PR No missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
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
		
		if(!releasedDate.equals("")) {
		char[] temp = releasedDate.toCharArray();
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
		
		if(!expiredDate.equals("")) {
		char[] temp = expiredDate.toCharArray();
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

