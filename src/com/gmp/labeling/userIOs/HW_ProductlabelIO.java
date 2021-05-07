package com.gmp.labeling.userIOs;

import java.awt.Button;
import java.awt.Canvas;
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
import com.gmp.labeling.printModels.HW_ProductLabel;
import com.gmp.labeling.printModels.PrintingQueue;

public class HW_ProductlabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3855345368857460859L;
	private JPanel contentPane;
	private JTextField product_gin_Number;
	private JTextField product_item_Code;
	private JTextField product_quantity;
	private JTextField product_received_Date;
	private JTextField product_printing_Count;
	private String itemUnit;
	private JTextField product_expired_Date;
	private List<Material> materials;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HW_ProductlabelIO(Settings settings, PrintingQueue printqueue) {
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
		
		JLabel Product_lbl_title = new JLabel("Product Label Printing for 60 HW");
		Product_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		Product_lbl_title.setBounds(88, 18, 360, 30);
		contentPane.add(Product_lbl_title);
		
		JLabel product_lbl_labeltype = new JLabel("Label Type:");
		product_lbl_labeltype.setBounds(50, 60, 90, 20);
		contentPane.add(product_lbl_labeltype);
		
		JLabel product_lbl_itemCode = new JLabel("Item Code:");
		product_lbl_itemCode.setBounds(50, 100, 90, 20);
		contentPane.add(product_lbl_itemCode);
		
		JLabel product_lbl_itemName = new JLabel("Item Name:");
		product_lbl_itemName.setBounds(50, 140, 90, 20);
		contentPane.add(product_lbl_itemName);
		
		JLabel product_lbl_ginNumber = new JLabel("Gin:");
		product_lbl_ginNumber.setBounds(50, 180, 90, 20);
		contentPane.add(product_lbl_ginNumber);
		
		JLabel product_lbl_quantity = new JLabel("Qty/Unit:");
		product_lbl_quantity.setBounds(50, 220, 90, 20);
		contentPane.add(product_lbl_quantity);
		
		JLabel product_lbl_date = new JLabel("Received date:");
		product_lbl_date.setBounds(50, 260, 90, 20);
		contentPane.add(product_lbl_date);
		
		JLabel product_lbl_expdate = new JLabel("Expired Date:");
		product_lbl_expdate.setBounds(50, 300, 90, 20);
		contentPane.add(product_lbl_expdate);
				
		JLabel lblNewLabel_1 = new JLabel("Printing count:");
		lblNewLabel_1.setBounds(50, 340, 90, 20);
		contentPane.add(lblNewLabel_1);
		
		product_gin_Number = new JTextField();
		product_gin_Number.setBounds(160, 180, 180, 20);
		contentPane.add(product_gin_Number);
		product_gin_Number.setColumns(10);
		
		product_item_Code = new JTextField();
		product_item_Code.setBounds(160, 100, 180, 20);
		contentPane.add(product_item_Code);
		product_item_Code.setColumns(10);
		
		product_quantity = new JTextField();
		product_quantity.setBounds(160, 220, 180, 20);
		contentPane.add(product_quantity);
		product_quantity.setColumns(10);
		
		product_received_Date = new JTextField();
		product_received_Date.setBounds(160, 260, 180, 20);
		contentPane.add(product_received_Date);
		product_received_Date.setColumns(10);
		
		product_expired_Date = new JTextField();
		product_expired_Date.setBounds(160, 300, 180, 20);
		contentPane.add(product_expired_Date);
		product_expired_Date.setColumns(10);
		
		product_printing_Count = new JTextField();
		product_printing_Count.setBounds(160, 340, 180, 20);
		contentPane.add(product_printing_Count);
		product_printing_Count.setColumns(10);
		

				
		JLabel product_lbl_item_unit = new JLabel("");
		product_lbl_item_unit.setBounds(340, 220, 80, 20);
		contentPane.add(product_lbl_item_unit);
		
		Button product_btn_infoReceivedDate = new Button("i");
		product_btn_infoReceivedDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using the system date."
					    );
			}
		});
		product_btn_infoReceivedDate.setBackground(Color.LIGHT_GRAY);
		product_btn_infoReceivedDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		product_btn_infoReceivedDate.setBounds(340, 260, 20, 20);
		contentPane.add(product_btn_infoReceivedDate);
		
		Button product_btn_infoExpiredDate = new Button("i");
		product_btn_infoExpiredDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means exp N/A for the item."
					    );
			}
		});
		product_btn_infoExpiredDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		product_btn_infoExpiredDate.setBackground(Color.LIGHT_GRAY);
		product_btn_infoExpiredDate.setBounds(340, 300, 20, 20);
		contentPane.add(product_btn_infoExpiredDate);
        
//		List<Material> materials = inputItemdata(".\\src\\com\\gmp\\labeling\\resources\\datafiles\\inv.csv");
		materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		Canvas canvas = new Canvas();
		canvas.setBackground(new Color(255, 255, 0));
		canvas.setBounds(346, 60, 30, 20);
		contentPane.add(canvas);
		
		JComboBox product_label_Type = new JComboBox();
		product_label_Type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) product_label_Type.getSelectedItem();
				switch(value) {
				case "Pharmaceutical Use":
					canvas.setBackground(new Color(255, 255, 0));
					break;
				case "Non-Pharmaceutical Use":
					canvas.setBackground(new Color(255, 140, 0));
					break;
				case "Packaging":
					canvas.setBackground(new Color(0, 255, 255));
					break;
				}
			}
		});
		product_label_Type.setModel(new DefaultComboBoxModel(new String[] {"Pharmaceutical Use", "Non-Pharmaceutical Use", "Packaging"}));
		product_label_Type.setBounds(160, 60, 180, 20);
		contentPane.add(product_label_Type);
		
		JComboBox product_item_Name = new JComboBox();
		product_item_Name.setModel(new DefaultComboBoxModel(sarray));
		product_item_Name.setMaximumRowCount(10);
		product_item_Name.setSelectedItem(null);
		product_item_Name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) product_item_Name.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		product_item_Code.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		product_lbl_item_unit.setText(itemUnit);
		        		break;
		        	}	        	
		        }
			}
		});
		product_item_Name.setBounds(160, 140, 180, 20);
		contentPane.add(product_item_Name);
		
		Button product_btn_Check = new Button("Check");
		product_btn_Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = product_item_Code.getText().replaceAll("([a-z])", "$1").toUpperCase();
				boolean itemcode_exist = false;
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		product_item_Name.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		product_lbl_item_unit.setText(itemUnit);
		        		product_item_Code.setText(itemCode_value);
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
		product_btn_Check.setBounds(340, 100, 60, 20);
		contentPane.add(product_btn_Check);
		JButton product_btn_Settings = new JButton("Settings");
		product_btn_Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		
		product_btn_Settings.setBounds(30, 380, 90, 20);
		contentPane.add(product_btn_Settings);
		
		JButton product_btn_Print = new JButton("Print");
		product_btn_Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(product_item_Code.getText(), product_item_Name.getSelectedItem().toString(), materials, product_gin_Number.getText(), product_quantity.getText(), product_received_Date.getText(), product_expired_Date.getText(), 
				  product_printing_Count.getText(), settings)) {
					int printNumber = Integer.valueOf(product_printing_Count.getText());
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new HW_ProductLabel(product_item_Code.getText(), product_item_Name.getSelectedItem().toString(), product_gin_Number.getText(), product_label_Type.getSelectedItem().toString(), product_quantity.getText(),
								product_received_Date.getText(), product_expired_Date.getText(), itemUnit, settings.getSetting().getLogined_user()));
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
		product_btn_Print.setBounds(140, 380, 90, 20);
		contentPane.add(product_btn_Print);
		
		JButton product_btn_Update = new JButton("Update");
		product_btn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings,"stk.csv", "materiallistpath");
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		product_btn_Update.setBounds(250, 380, 90, 20);
		contentPane.add(product_btn_Update);
		
		JButton product_btn_Back = new JButton("Back");
		product_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		product_btn_Back.setBounds(360, 380, 90, 20);
		contentPane.add(product_btn_Back);
		
		JLabel product_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		product_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		product_copyright_company.setBounds(200, 430, 280, 20);
		contentPane.add(product_copyright_company);
		
		JLabel product_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		product_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		product_copyright_developer.setBounds(300, 415, 170, 20);
		contentPane.add(product_copyright_developer);
		

		

		
	}
	
	public boolean dataValidation(String itemCode, String itemName, List<Material> materials, String ginNumber, String quantity, String receivedDate, String expiredDate, String printingCount, Settings settings) {
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
		
		if(!receivedDate.equals("")) {
		char[] temp = receivedDate.toCharArray();
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
