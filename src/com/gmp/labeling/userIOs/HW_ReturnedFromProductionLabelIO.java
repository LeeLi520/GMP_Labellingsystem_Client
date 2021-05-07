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
import com.gmp.labeling.printModels.HW_ReturnedFromProductionLabel;
import com.gmp.labeling.printModels.PrintingQueue;

public class HW_ReturnedFromProductionLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5983610209235866842L;
	private JPanel contentPane;
	private JTextField returnedFromProduction_itemCode;
	private JTextField returnedFromProduction_gin;
	private JTextField returnedFromProduction_grossWeight;
	private JTextField returnedFromProduction_netWeight;
	private JTextField returnedFromProduction_datePrepared;
	private JTextField returnedFromProduction_printingCount;
	private String itemUnit;
	private List<Material> materials;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HW_ReturnedFromProductionLabelIO(Settings settings, PrintingQueue printqueue) {
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
		
		JLabel returnedFromProduction_lbl_title = new JLabel("Returned From Production Label");
		returnedFromProduction_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		returnedFromProduction_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		returnedFromProduction_lbl_title.setBounds(68, 18, 360, 30);
		contentPane.add(returnedFromProduction_lbl_title);
		
		JLabel returnedFromProduction_lbl_itemCode = new JLabel("Item code:");
		returnedFromProduction_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(returnedFromProduction_lbl_itemCode);
		
		JLabel returnedFromProduction_lbl_itemName = new JLabel("Item name:");
		returnedFromProduction_lbl_itemName.setBounds(50, 100, 90, 20);
		contentPane.add(returnedFromProduction_lbl_itemName);
		
		JLabel returnedFromProduction_lbl_gin = new JLabel("Gin:");
		returnedFromProduction_lbl_gin.setBounds(50, 140, 90, 20);
		contentPane.add(returnedFromProduction_lbl_gin);
		
		JLabel returnedFromProduction_lbl_grossWeight = new JLabel("Gross weight:");
		returnedFromProduction_lbl_grossWeight.setBounds(50, 180, 90, 20);
		contentPane.add(returnedFromProduction_lbl_grossWeight);
		
		JLabel returnedFromProduction_lbl_netWeight = new JLabel("Net weight");
		returnedFromProduction_lbl_netWeight.setBounds(50, 220, 90, 20);
		contentPane.add(returnedFromProduction_lbl_netWeight);
		
		JLabel returnedFromProduction_lbl_preparedDate = new JLabel("Date prepared:");
		returnedFromProduction_lbl_preparedDate.setBounds(50, 260, 90, 20);
		contentPane.add(returnedFromProduction_lbl_preparedDate);
		
		JLabel returnedFromProduction_lbl_printCount = new JLabel("Printing Count:");
		returnedFromProduction_lbl_printCount.setBounds(50, 300, 90, 20);
		contentPane.add(returnedFromProduction_lbl_printCount);
		
		JLabel returnedFromProduction_lbl_itemUnit = new JLabel("");
		returnedFromProduction_lbl_itemUnit.setBounds(340, 180, 80, 20);
		contentPane.add(returnedFromProduction_lbl_itemUnit);
				
		JLabel returnedFromProduction_lbl_itemUnit_c = new JLabel("");
		returnedFromProduction_lbl_itemUnit_c.setBounds(340, 220, 80, 20);
		contentPane.add(returnedFromProduction_lbl_itemUnit_c);
		
		returnedFromProduction_itemCode = new JTextField();
		returnedFromProduction_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(returnedFromProduction_itemCode);
		returnedFromProduction_itemCode.setColumns(10);
		
		returnedFromProduction_gin = new JTextField();
		returnedFromProduction_gin.setBounds(160, 140, 180, 20);
		contentPane.add(returnedFromProduction_gin);
		returnedFromProduction_gin.setColumns(10);
		
		returnedFromProduction_grossWeight = new JTextField();
		returnedFromProduction_grossWeight.setBounds(160, 180, 180, 20);
		contentPane.add(returnedFromProduction_grossWeight);
		returnedFromProduction_grossWeight.setColumns(10);
		
		returnedFromProduction_netWeight = new JTextField();
		returnedFromProduction_netWeight.setBounds(160, 220, 180, 20);
		contentPane.add(returnedFromProduction_netWeight);
		returnedFromProduction_netWeight.setColumns(10);
		
		returnedFromProduction_datePrepared = new JTextField();
		returnedFromProduction_datePrepared.setBounds(160, 260, 180, 20);
		contentPane.add(returnedFromProduction_datePrepared);
		returnedFromProduction_datePrepared.setColumns(10);
		
		returnedFromProduction_printingCount = new JTextField();
		returnedFromProduction_printingCount.setBounds(160, 300, 180, 20);
		contentPane.add(returnedFromProduction_printingCount);
		returnedFromProduction_printingCount.setColumns(10);
		
		materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JComboBox returnedFromProduction_itemName = new JComboBox();
		returnedFromProduction_itemName.setModel(new DefaultComboBoxModel(sarray));
		returnedFromProduction_itemName.setMaximumRowCount(10);
		returnedFromProduction_itemName.setSelectedItem(null);
		returnedFromProduction_itemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) returnedFromProduction_itemName.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		returnedFromProduction_itemCode.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		returnedFromProduction_lbl_itemUnit.setText(itemUnit);
		        		returnedFromProduction_lbl_itemUnit_c.setText(itemUnit);
		        		break;
		        	}	        	
		        }
			}
		});
		returnedFromProduction_itemName.setBounds(160, 100, 180, 20);
		contentPane.add(returnedFromProduction_itemName);
		
		Button returnedFromProduction_btn_check = new Button("Check");
		returnedFromProduction_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = returnedFromProduction_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				boolean itemcode_exist = false;
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		returnedFromProduction_itemName.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		returnedFromProduction_lbl_itemUnit.setText(itemUnit);
		        		returnedFromProduction_lbl_itemUnit_c.setText(itemUnit);
		        		returnedFromProduction_itemCode.setText(itemCode_value);
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
		
		JButton returnedFromProduction_btn_settings = new JButton("Settings");
		returnedFromProduction_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		returnedFromProduction_btn_settings.setBounds(30, 340, 90, 20);
		contentPane.add(returnedFromProduction_btn_settings);
		
		JButton returnedFromProduction_btn_print = new JButton("Print");
		returnedFromProduction_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(returnedFromProduction_itemCode.getText(), returnedFromProduction_itemName.getSelectedItem().toString(), materials, returnedFromProduction_gin.getText(), returnedFromProduction_grossWeight.getText(),
						returnedFromProduction_netWeight.getText(), returnedFromProduction_datePrepared.getText(), returnedFromProduction_printingCount.getText(), settings)) {
							int printNumber = Integer.valueOf(returnedFromProduction_printingCount.getText());
							int i = 0;
							while(i<printNumber) {
								printqueue.addLabelToQueue(new HW_ReturnedFromProductionLabel(returnedFromProduction_itemCode.getText(), returnedFromProduction_itemName.getSelectedItem().toString(), returnedFromProduction_gin.getText(), returnedFromProduction_grossWeight.getText(), 
										returnedFromProduction_netWeight.getText(), itemUnit, returnedFromProduction_datePrepared.getText(), settings.getSetting().getLogined_user()));
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
		returnedFromProduction_btn_print.setBounds(140, 340, 90, 20);
		contentPane.add(returnedFromProduction_btn_print);
		
		JButton returnedFromProduction_btn_update = new JButton("Update");
		returnedFromProduction_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings,"stk.csv", "materiallistpath");
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		returnedFromProduction_btn_update.setBounds(250, 340, 90, 20);
		contentPane.add(returnedFromProduction_btn_update);
		
		JButton returnedFromProduction_btn_back = new JButton("Back");
		returnedFromProduction_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		returnedFromProduction_btn_back.setBounds(360, 340, 90, 20);
		contentPane.add(returnedFromProduction_btn_back);
		
		JLabel label = new JLabel("Designed and Implemented by Lee.L");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(300, 375, 170, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(200, 390, 280, 20);
		contentPane.add(label_1);
		

		returnedFromProduction_btn_check.setBounds(340, 60, 70, 20);
		contentPane.add(returnedFromProduction_btn_check);
		
		Button returnedFromProduction_btn_info_preparedDate = new Button("i");
		returnedFromProduction_btn_info_preparedDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using system date."
					    );
			}
		});
		returnedFromProduction_btn_info_preparedDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		returnedFromProduction_btn_info_preparedDate.setBackground(Color.LIGHT_GRAY);
		returnedFromProduction_btn_info_preparedDate.setBounds(340, 260, 20, 20);
		contentPane.add(returnedFromProduction_btn_info_preparedDate);

	}
	
	public boolean dataValidation(String itemCode, String itemName, List<Material> materials, String ginNumber, String grossWeight, String netWeight, String preparedDate, String printingCount, Settings settings) {
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
		
		try {
			if(Float.parseFloat(grossWeight)<0) {
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
		
		try {
			if(Float.parseFloat(netWeight)<0) {
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
		
		if(!preparedDate.equals("")) {
		char[] temp = preparedDate.toCharArray();
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
		
		try {
			if(Integer.parseInt(printingCount)<=0) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "PrintingQty missing or Invalid input",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "PrintingQty missing or Invalid input",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
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


