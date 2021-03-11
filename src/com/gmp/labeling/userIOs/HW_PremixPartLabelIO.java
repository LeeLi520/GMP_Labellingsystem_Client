package com.gmp.labeling.userIOs;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
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
import com.gmp.labeling.printModels.HW_PreMixPartLabel;
import com.gmp.labeling.printModels.PrintingQueue;

public class HW_PremixPartLabelIO extends JFrame {

	private JPanel contentPane;
	private JTextField HW_premixPartLabel_batch;
	private JTextField HW_premixPartLabel_item_Code;
	private JTextField HW_premixPartLabel_weight;
	private JTextField HW_premixPartLabel_printDate;
	private JTextField HW_premixPartLabel_bag;
	private String itemUnit;
	private List<Material> materials;
	private JTextField HW_premixPartLabel_Part;
	private JTextField HW_premixPartLabel_bags;

	public HW_PremixPartLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 460);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel HW_premixPartLabel_lbl_title = new JLabel("Pre-mix Part Label for 60 HW");
		HW_premixPartLabel_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		HW_premixPartLabel_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		HW_premixPartLabel_lbl_title.setBounds(88, 18, 360, 30);
		contentPane.add(HW_premixPartLabel_lbl_title);
		
		JLabel HW_premixPartLabel_lbl_itemCode = new JLabel("Product Code:");
		HW_premixPartLabel_lbl_itemCode.setBounds(50, 60, 130, 20);
		contentPane.add(HW_premixPartLabel_lbl_itemCode);
		
		JLabel HW_premixPartLabel_lbl_itemName = new JLabel("Product Name:");
		HW_premixPartLabel_lbl_itemName.setBounds(50, 100, 130, 20);
		contentPane.add(HW_premixPartLabel_lbl_itemName);
		
		JLabel HW_premixPartLabel_lbl_batch = new JLabel("Batch:");
		HW_premixPartLabel_lbl_batch.setBounds(50, 140, 130, 20);
		contentPane.add(HW_premixPartLabel_lbl_batch);
		
		JLabel HW_premixPartLabel_lbl_weight = new JLabel("Net Weight:");
		HW_premixPartLabel_lbl_weight.setBounds(50, 220, 130, 20);
		contentPane.add(HW_premixPartLabel_lbl_weight);
		
		JLabel HW_premixPartLabel_lbl_printDate = new JLabel("Print Date:");
		HW_premixPartLabel_lbl_printDate.setBounds(50, 260, 130, 20);
		contentPane.add(HW_premixPartLabel_lbl_printDate);
				
		JLabel HW_premixPartLabel_lbl_NoContainer_title = new JLabel("No. of Bags:");
		HW_premixPartLabel_lbl_NoContainer_title.setBounds(50, 300, 130, 20);
		contentPane.add(HW_premixPartLabel_lbl_NoContainer_title);
		
		HW_premixPartLabel_batch = new JTextField();
		HW_premixPartLabel_batch.setBounds(180, 140, 200, 20);
		contentPane.add(HW_premixPartLabel_batch);
		HW_premixPartLabel_batch.setColumns(10);
		
		HW_premixPartLabel_item_Code = new JTextField();
		HW_premixPartLabel_item_Code.setBounds(180, 60, 200, 20);
		contentPane.add(HW_premixPartLabel_item_Code);
		HW_premixPartLabel_item_Code.setColumns(10);
		
		HW_premixPartLabel_weight = new JTextField();
		HW_premixPartLabel_weight.setBounds(180, 220, 200, 20);
		contentPane.add(HW_premixPartLabel_weight);
		HW_premixPartLabel_weight.setColumns(10);
		
		HW_premixPartLabel_printDate = new JTextField();
		HW_premixPartLabel_printDate.setBounds(180, 260, 200, 20);
		contentPane.add(HW_premixPartLabel_printDate);
		HW_premixPartLabel_printDate.setColumns(10);
		
		HW_premixPartLabel_bag = new JTextField();
		HW_premixPartLabel_bag.setBounds(180, 300, 60, 20);
		contentPane.add(HW_premixPartLabel_bag);
		HW_premixPartLabel_bag.setColumns(10);
		
		HW_premixPartLabel_Part = new JTextField();
		HW_premixPartLabel_Part.setBounds(180, 180, 200, 20);
		contentPane.add(HW_premixPartLabel_Part);
		HW_premixPartLabel_Part.setColumns(10);
		
		JLabel HW_premixPartLabel_lbl_PRnumber = new JLabel("Pre-Mix Part No.:");
		HW_premixPartLabel_lbl_PRnumber.setBounds(50, 180, 130, 20);
		contentPane.add(HW_premixPartLabel_lbl_PRnumber);
		
		JLabel HW_premixPartLabel_lbl_of = new JLabel("of");
		HW_premixPartLabel_lbl_of.setHorizontalAlignment(SwingConstants.CENTER);
		HW_premixPartLabel_lbl_of.setBounds(240, 300, 40, 20);
		contentPane.add(HW_premixPartLabel_lbl_of);
		
		HW_premixPartLabel_bags = new JTextField();
		HW_premixPartLabel_bags.setBounds(280, 300, 80, 20);
		contentPane.add(HW_premixPartLabel_bags);
		HW_premixPartLabel_bags.setColumns(10);
		
		JRadioButton HW_premixPartLabel_rdbtn_printAll = new JRadioButton("Print All");
		HW_premixPartLabel_rdbtn_printAll.setBounds(360, 300, 100, 20);
		contentPane.add(HW_premixPartLabel_rdbtn_printAll);
		
		

				
		JLabel HW_premixPartLabel_lbl_item_unit = new JLabel("");
		HW_premixPartLabel_lbl_item_unit.setBounds(380, 220, 80, 20);
		contentPane.add(HW_premixPartLabel_lbl_item_unit);
		
		Button HW_premixPartLabel_btn_infoReleaseDate = new Button("i");
		HW_premixPartLabel_btn_infoReleaseDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Released Date correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using the system date."
					    );
			}
		});
		HW_premixPartLabel_btn_infoReleaseDate.setBackground(Color.LIGHT_GRAY);
		HW_premixPartLabel_btn_infoReleaseDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		HW_premixPartLabel_btn_infoReleaseDate.setBounds(380, 260, 20, 20);
		contentPane.add(HW_premixPartLabel_btn_infoReleaseDate);
        
//		List<Material> materials = inputItemdata(".\\src\\com\\gmp\\labeling\\resources\\datafiles\\inv.csv");
		materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JComboBox HW_premixPartLabel_item_Name = new JComboBox();
		HW_premixPartLabel_item_Name.setModel(new DefaultComboBoxModel(sarray));
		HW_premixPartLabel_item_Name.setMaximumRowCount(10);
		HW_premixPartLabel_item_Name.setSelectedItem(null);
		HW_premixPartLabel_item_Name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) HW_premixPartLabel_item_Name.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		HW_premixPartLabel_item_Code.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		HW_premixPartLabel_lbl_item_unit.setText(itemUnit);
		        		break;
		        	}	        	
		        }
			}
		});
		HW_premixPartLabel_item_Name.setBounds(180, 100, 200, 20);
		contentPane.add(HW_premixPartLabel_item_Name);
		
		Button HW_premixPartLabel_btn_Check = new Button("Check");
		HW_premixPartLabel_btn_Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = HW_premixPartLabel_item_Code.getText().replaceAll("([a-z])", "$1").toUpperCase();
				boolean itemcode_exist = false;
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		HW_premixPartLabel_item_Name.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		HW_premixPartLabel_lbl_item_unit.setText(itemUnit);
		        		HW_premixPartLabel_item_Code.setText(itemCode_value);
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
		HW_premixPartLabel_btn_Check.setBounds(380, 60, 60, 20);
		contentPane.add(HW_premixPartLabel_btn_Check);
		JButton HW_premixPartLabel_btn_Settings = new JButton("Settings");
		HW_premixPartLabel_btn_Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		
		HW_premixPartLabel_btn_Settings.setBounds(30, 340, 100, 20);
		contentPane.add(HW_premixPartLabel_btn_Settings);
		
		JButton HW_premixPartLabel_btn_Print = new JButton("Print");
		HW_premixPartLabel_btn_Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(HW_premixPartLabel_item_Code.getText(), HW_premixPartLabel_item_Name.getSelectedItem().toString(), materials, HW_premixPartLabel_batch.getText().toUpperCase(), HW_premixPartLabel_Part.getText(), 
						HW_premixPartLabel_weight.getText(), HW_premixPartLabel_printDate.getText(), HW_premixPartLabel_rdbtn_printAll.isSelected(), HW_premixPartLabel_bag.getText(), HW_premixPartLabel_bags.getText(), settings)) {
					int printNumber = Integer.valueOf(HW_premixPartLabel_bags.getText());
					if(HW_premixPartLabel_rdbtn_printAll.isSelected()) {
						if(HW_premixPartLabel_bag.getText().equals("")) {
							int i = 1;
							while(i <= printNumber) {								
								printqueue.addLabelToQueue(new HW_PreMixPartLabel(HW_premixPartLabel_item_Code.getText(), HW_premixPartLabel_item_Name.getSelectedItem().toString(), HW_premixPartLabel_batch.getText().toUpperCase(), HW_premixPartLabel_Part.getText(), String.valueOf(i),
										HW_premixPartLabel_bags.getText(), HW_premixPartLabel_weight.getText(), itemUnit, settings.getSetting().getLogined_user().getFullname(), HW_premixPartLabel_printDate.getText()));								
								i++;
							}
						}else {
							int currentIndex = Integer.valueOf(HW_premixPartLabel_bag.getText());
							int i = currentIndex;
							while(i <= printNumber) {
								printqueue.addLabelToQueue(new HW_PreMixPartLabel(HW_premixPartLabel_item_Code.getText(), HW_premixPartLabel_item_Name.getSelectedItem().toString(), HW_premixPartLabel_batch.getText().toUpperCase(), HW_premixPartLabel_Part.getText(), String.valueOf(i),
										HW_premixPartLabel_bags.getText(), HW_premixPartLabel_weight.getText(), itemUnit, settings.getSetting().getLogined_user().getFullname(), HW_premixPartLabel_printDate.getText()));									
								i++;
							}
						}																		
					}else {
						int currentNumber = Integer.valueOf(HW_premixPartLabel_bag.getText());
						printqueue.addLabelToQueue(new HW_PreMixPartLabel(HW_premixPartLabel_item_Code.getText(), HW_premixPartLabel_item_Name.getSelectedItem().toString(), HW_premixPartLabel_batch.getText().toUpperCase(), HW_premixPartLabel_Part.getText(), String.valueOf(currentNumber),
								HW_premixPartLabel_bags.getText(), HW_premixPartLabel_weight.getText(), itemUnit, settings.getSetting().getLogined_user().getFullname(), HW_premixPartLabel_printDate.getText()));
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
		HW_premixPartLabel_btn_Print.setBounds(150, 340, 100, 20);
		contentPane.add(HW_premixPartLabel_btn_Print);
		
		JButton HW_premixPartLabel_btn_Update = new JButton("Update");
		HW_premixPartLabel_btn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		HW_premixPartLabel_btn_Update.setBounds(270, 340, 100, 20);
		contentPane.add(HW_premixPartLabel_btn_Update);
		
		JButton HW_premixPartLabel_btn_Back = new JButton("Back");
		HW_premixPartLabel_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		HW_premixPartLabel_btn_Back.setBounds(390, 340, 100, 20);
		contentPane.add(HW_premixPartLabel_btn_Back);
		
		JLabel HW_premixPartLabel_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		HW_premixPartLabel_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		HW_premixPartLabel_copyright_company.setBounds(230, 390, 280, 20);
		contentPane.add(HW_premixPartLabel_copyright_company);
		
		JLabel HW_premixPartLabel_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		HW_premixPartLabel_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		HW_premixPartLabel_copyright_developer.setBounds(330, 375, 170, 20);
		contentPane.add(HW_premixPartLabel_copyright_developer);
		
	}
	
	public boolean dataValidation(String itemCode, String itemName, List<Material> materials, String batch, String partNumber, String weight, String printDate, boolean printMode, String bag, String bags, Settings settings) {
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
				    "Batch number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			if(batch.length()!=6) {				
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid Batch Format Error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}else {
				String aCode = String.valueOf(batch.charAt(0));
				aCode = aCode.replaceAll("([a-z])", "$1").toUpperCase();
				if(!aCode.equals("H")) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid Batch number for the Site.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}				
			}
			
		}
		if(partNumber.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Part number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			try {
				int test = Integer.valueOf(partNumber);
				if(test < 0) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid part number input. part number must be bigger than 0 integer number.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid part number input. part number must be bigger than 0 integer number.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			} 
		}
		
		try {
			if(Float.parseFloat(weight)<0) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Weight missing or Invalid input.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Weight missing or Invalid input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(!printDate.equals("")) {
		char[] temp = printDate.toCharArray();
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
        	if(bag.equals("")) {
        		try {
        			@SuppressWarnings("unused")
					int test_2 = Integer.valueOf(bags);
        		}catch(Exception e) {
        			JOptionPane.showMessageDialog(settings.getComponentPage(),
        				    "Invalid bags Input.",
        				    "Inane error",
        				    JOptionPane.ERROR_MESSAGE);
        			return false;
        		}
        	}else {
        		try {
        			@SuppressWarnings("unused")
					int test_1 = Integer.valueOf(bag);
        			@SuppressWarnings("unused")
					int test_2 = Integer.valueOf(bags);
        		}catch(Exception e) {
        			JOptionPane.showMessageDialog(settings.getComponentPage(),
        				    "Invalid bags Input.",
        				    "Inane error",
        				    JOptionPane.ERROR_MESSAGE);
        			return false;
        		}
        	}
        }else {
        	try {
    			@SuppressWarnings("unused")
				int test_1 = Integer.valueOf(bag);
    			@SuppressWarnings("unused")
				int test_2 = Integer.valueOf(bags);
    		}catch(Exception e) {
    			JOptionPane.showMessageDialog(settings.getComponentPage(),
    				    "Invalid bag Input.",
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