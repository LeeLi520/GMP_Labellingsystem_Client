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
import com.gmp.labeling.printModels.HW_RejectLabel;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.ButtonGroup;

public class HW_RejectLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7188411833411425629L;
	private JPanel contentPane;
	private JTextField rejectLabel_rejectedValue;
	private JTextField rejectLabel_itemCode;
	private JTextField rejectLabel_quantity;
	private JTextField rejectLabel_noContainer_1;
	private String itemUnit;
	private List<Material> materials;
	private JTextField rejectLabel_rejectedDate;
	private JTextField rejectLabel_riaNumber;
	private JTextField rejectLabel_noContainer_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HW_RejectLabelIO(Settings settings, PrintingQueue printqueue) {
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
		
		JLabel rejectLabel_lbl_title = new JLabel("Reject Label for 60 HW");
		rejectLabel_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		rejectLabel_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		rejectLabel_lbl_title.setBounds(68, 18, 360, 30);
		contentPane.add(rejectLabel_lbl_title);
		
		JLabel rejectLabel_lbl_itemCode = new JLabel("Item Code:");
		rejectLabel_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(rejectLabel_lbl_itemCode);
		
		JLabel rejectLabel_lbl_itemName = new JLabel("Item Name:");
		rejectLabel_lbl_itemName.setBounds(50, 100, 90, 20);
		contentPane.add(rejectLabel_lbl_itemName);
		
		JLabel rejectLabel_lbl_rejectedType = new JLabel("Gin No:");
		rejectLabel_lbl_rejectedType.setBounds(50, 180, 90, 20);
		contentPane.add(rejectLabel_lbl_rejectedType);
		
		JLabel rejectLabel_lbl_quantity = new JLabel("Quantity:");
		rejectLabel_lbl_quantity.setBounds(50, 260, 90, 20);
		contentPane.add(rejectLabel_lbl_quantity);
		
		JLabel rejectLabel_lbl_rejectedDate = new JLabel("Date Rejected:");
		rejectLabel_lbl_rejectedDate.setBounds(50, 300, 90, 20);
		contentPane.add(rejectLabel_lbl_rejectedDate);
				
		JLabel rejectLabel_lbl_NoContainer_title = new JLabel("No. of Containers:");
		rejectLabel_lbl_NoContainer_title.setBounds(50, 340, 110, 20);
		contentPane.add(rejectLabel_lbl_NoContainer_title);
		
		rejectLabel_rejectedValue = new JTextField();
		rejectLabel_rejectedValue.setBounds(160, 180, 180, 20);
		contentPane.add(rejectLabel_rejectedValue);
		rejectLabel_rejectedValue.setColumns(10);
		
		rejectLabel_itemCode = new JTextField();
		rejectLabel_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(rejectLabel_itemCode);
		rejectLabel_itemCode.setColumns(10);
		
		rejectLabel_quantity = new JTextField();
		rejectLabel_quantity.setBounds(160, 260, 180, 20);
		contentPane.add(rejectLabel_quantity);
		rejectLabel_quantity.setColumns(10);
		
		rejectLabel_rejectedDate = new JTextField();
		rejectLabel_rejectedDate.setBounds(160, 300, 180, 20);
		contentPane.add(rejectLabel_rejectedDate);
		rejectLabel_rejectedDate.setColumns(10);
		
		rejectLabel_noContainer_1 = new JTextField();
		rejectLabel_noContainer_1.setBounds(160, 340, 60, 20);
		contentPane.add(rejectLabel_noContainer_1);
		rejectLabel_noContainer_1.setColumns(10);
		
		rejectLabel_riaNumber = new JTextField();
		rejectLabel_riaNumber.setBounds(160, 220, 180, 20);
		contentPane.add(rejectLabel_riaNumber);
		rejectLabel_riaNumber.setColumns(10);
		
		JRadioButton rejectLabel_rdbtn_ginNumber = new JRadioButton("Gin No:");
		rejectLabel_rdbtn_ginNumber.setSelected(true);
		rejectLabel_rdbtn_ginNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rejectLabel_lbl_rejectedType.setText("Gin No:");
			}
		});
		buttonGroup.add(rejectLabel_rdbtn_ginNumber);
		rejectLabel_rdbtn_ginNumber.setBounds(160, 140, 100, 20);
		contentPane.add(rejectLabel_rdbtn_ginNumber);
		
		JRadioButton rejectLabel_rdbtn_batch = new JRadioButton("Batch No:");
		rejectLabel_rdbtn_batch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rejectLabel_lbl_rejectedType.setText("Batch No:");
			}
		});
		buttonGroup.add(rejectLabel_rdbtn_batch);
		rejectLabel_rdbtn_batch.setBounds(260, 140, 100, 20);
		contentPane.add(rejectLabel_rdbtn_batch);
		
		JLabel rejectLabel_lbl_rejectedTypeOption = new JLabel("Reject Type:");
		rejectLabel_lbl_rejectedTypeOption.setBounds(50, 140, 100, 20);
		contentPane.add(rejectLabel_lbl_rejectedTypeOption);
		
		JLabel rejectLabel_lbl_riaNumber = new JLabel("RIA No:");
		rejectLabel_lbl_riaNumber.setBounds(50, 220, 90, 20);
		contentPane.add(rejectLabel_lbl_riaNumber);
		
		JLabel rejectLabel_lbl_of = new JLabel("of");
		rejectLabel_lbl_of.setHorizontalAlignment(SwingConstants.CENTER);
		rejectLabel_lbl_of.setBounds(220, 340, 40, 20);
		contentPane.add(rejectLabel_lbl_of);
		
		rejectLabel_noContainer_2 = new JTextField();
		rejectLabel_noContainer_2.setBounds(260, 340, 80, 20);
		contentPane.add(rejectLabel_noContainer_2);
		rejectLabel_noContainer_2.setColumns(10);
		
		JRadioButton rejectLabel_rdbtn_printAll = new JRadioButton("Print All");
		rejectLabel_rdbtn_printAll.setBounds(340, 340, 80, 20);
		contentPane.add(rejectLabel_rdbtn_printAll);
		
				
		JLabel rejectLabel_lbl_item_unit = new JLabel("");
		rejectLabel_lbl_item_unit.setBounds(340, 260, 80, 20);
		contentPane.add(rejectLabel_lbl_item_unit);
		
		Button rejectLabel_btn_infoRejectedDate = new Button("i");
		rejectLabel_btn_infoRejectedDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means the system date."
					    );
			}
		});
		rejectLabel_btn_infoRejectedDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		rejectLabel_btn_infoRejectedDate.setBackground(Color.LIGHT_GRAY);
		rejectLabel_btn_infoRejectedDate.setBounds(340, 300, 20, 20);
		contentPane.add(rejectLabel_btn_infoRejectedDate);
        
//		List<Material> materials = inputItemdata(".\\src\\com\\gmp\\labeling\\resources\\datafiles\\inv.csv");
		materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JComboBox rejectLabel_itemName = new JComboBox();
		rejectLabel_itemName.setModel(new DefaultComboBoxModel(sarray));
		rejectLabel_itemName.setMaximumRowCount(10);
		rejectLabel_itemName.setSelectedItem(null);
		rejectLabel_itemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) rejectLabel_itemName.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		rejectLabel_itemCode.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		rejectLabel_lbl_item_unit.setText(itemUnit);
		        		break;
		        	}	        	
		        }
			}
		});
		rejectLabel_itemName.setBounds(160, 100, 180, 20);
		contentPane.add(rejectLabel_itemName);
		
		Button rejectLabel_btn_Check = new Button("Check");
		rejectLabel_btn_Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = rejectLabel_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				boolean itemcode_exist = false;
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		rejectLabel_itemName.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		rejectLabel_lbl_item_unit.setText(itemUnit);
		        		rejectLabel_itemCode.setText(itemCode_value);
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
		rejectLabel_btn_Check.setBounds(340, 60, 60, 20);
		contentPane.add(rejectLabel_btn_Check);
		JButton rejectLabel_btn_Settings = new JButton("Settings");
		rejectLabel_btn_Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		
		rejectLabel_btn_Settings.setBounds(30, 380, 90, 20);
		contentPane.add(rejectLabel_btn_Settings);
		
		JButton rejectLabel_btn_Print = new JButton("Print");
		rejectLabel_btn_Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(rejectLabel_itemCode.getText(), rejectLabel_itemName.getSelectedItem().toString(), materials, rejectLabel_lbl_rejectedType.getText(), rejectLabel_rejectedValue.getText(),
						rejectLabel_riaNumber.getText(), rejectLabel_quantity.getText(), rejectLabel_rejectedDate.getText(), rejectLabel_rdbtn_printAll.isSelected(), rejectLabel_noContainer_1.getText(), rejectLabel_noContainer_2.getText(), settings)) {
					int printNumber = Integer.valueOf(rejectLabel_noContainer_2.getText());
					if(rejectLabel_rdbtn_printAll.isSelected()) {
						if(rejectLabel_noContainer_1.getText().equals("")) {
							int i = 1;
							while(i <= printNumber) {
								printqueue.addLabelToQueue(new HW_RejectLabel(rejectLabel_itemCode.getText(), rejectLabel_itemName.getSelectedItem().toString(), rejectLabel_lbl_rejectedType.getText(), rejectLabel_rejectedValue.getText(), rejectLabel_riaNumber.getText(),
										rejectLabel_rejectedDate.getText(), rejectLabel_quantity.getText(), itemUnit, String.valueOf(i), rejectLabel_noContainer_2.getText(), settings.getSetting().getLogined_user()));							
								i++;
							}
						}else {
							int currentIndex = Integer.valueOf(rejectLabel_noContainer_1.getText());
							int i = currentIndex;
							while(i <= printNumber) {
								printqueue.addLabelToQueue(new HW_RejectLabel(rejectLabel_itemCode.getText(), rejectLabel_itemName.getSelectedItem().toString(), rejectLabel_lbl_rejectedType.getText(), rejectLabel_rejectedValue.getText(), rejectLabel_riaNumber.getText(),
										rejectLabel_rejectedDate.getText(), rejectLabel_quantity.getText(), itemUnit, String.valueOf(i), rejectLabel_noContainer_2.getText(), settings.getSetting().getLogined_user()));								
								i++;
							}
						}																		
					}else {
						int currentNumber = Integer.valueOf(rejectLabel_noContainer_1.getText());
						printqueue.addLabelToQueue(new HW_RejectLabel(rejectLabel_itemCode.getText(), rejectLabel_itemName.getSelectedItem().toString(), rejectLabel_lbl_rejectedType.getText(), rejectLabel_rejectedValue.getText(), rejectLabel_riaNumber.getText(),
								rejectLabel_rejectedDate.getText(), rejectLabel_quantity.getText(), itemUnit, String.valueOf(currentNumber), rejectLabel_noContainer_2.getText(), settings.getSetting().getLogined_user()));
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
		rejectLabel_btn_Print.setBounds(140, 380, 90, 20);
		contentPane.add(rejectLabel_btn_Print);
		
		JButton rejectLabel_btn_Update = new JButton("Update");
		rejectLabel_btn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings,"stk.csv", "materiallistpath");
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		rejectLabel_btn_Update.setBounds(250, 380, 90, 20);
		contentPane.add(rejectLabel_btn_Update);
		
		JButton rejectLabel_btn_Back = new JButton("Back");
		rejectLabel_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		rejectLabel_btn_Back.setBounds(360, 380, 90, 20);
		contentPane.add(rejectLabel_btn_Back);
		
		JLabel rejectLabel_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		rejectLabel_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rejectLabel_copyright_company.setBounds(200, 430, 280, 20);
		contentPane.add(rejectLabel_copyright_company);
		
		JLabel rejectLabel_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		rejectLabel_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rejectLabel_copyright_developer.setBounds(300, 415, 170, 20);
		contentPane.add(rejectLabel_copyright_developer);
		
	}
	
	public boolean dataValidation(String itemCode, String itemName, List<Material> materials, String rejectedType, String rejectedValue, String riaNumber, String quantity, String rejectedDate, boolean printMode, String printCount_1, String printCount_2, Settings settings) {
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
		
		switch(rejectedType) {
		case "Gin No:":
			if(rejectedValue.equals("")) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "GIN number missing.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}else {
				if(rejectedValue.length()!=7) {				
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid Gin Number Format Error.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}else {
					String aCode = String.valueOf(rejectedValue.charAt(0))+String.valueOf(rejectedValue.charAt(1));
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
			break;
		case "Batch No:":
			if(rejectedValue.equals("")) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Batch number missing.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}else {
				if(rejectedValue.length()!=6) {				
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid Gin Number Format Error.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}else {
					String aCode = String.valueOf(rejectedValue.charAt(0));
					aCode = aCode.replaceAll("([a-z])", "$1").toUpperCase();
					if(!aCode.equals("H")) {
						JOptionPane.showMessageDialog(settings.getComponentPage(),
							    "Invalid Batch Number Site Code Error.",
							    "Inane error",
							    JOptionPane.ERROR_MESSAGE);
						return false;
					}				
				}
				
			}
			break;
		}
		
		if(riaNumber.equals("")) {
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
		
		if(!rejectedDate.equals("")) {
		char[] temp = rejectedDate.toCharArray();
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
