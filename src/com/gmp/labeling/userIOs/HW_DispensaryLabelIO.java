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
import com.gmp.labeling.printModels.HW_DispensaryLabel;
import com.gmp.labeling.printModels.PrintingQueue;

public class HW_DispensaryLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8308226632749949195L;
	private JPanel contentPane;
	private JTextField dispensary_itemCode;
	private JTextField dispensary_gin;
	private JTextField dispensary_grossWeight;
	private JTextField dispensary_netWeight;
	private JTextField dispensary_datePrepared;
	private JTextField dispensary_printingCount;
	private String itemUnit;
	private List<Material> materials;
	private JTextField dispensary_batchUsedIn;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HW_DispensaryLabelIO(Settings settings, PrintingQueue printqueue) {
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
		
		JLabel dispensary_lbl_title = new JLabel("HW Dispensary Label");
		dispensary_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		dispensary_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		dispensary_lbl_title.setBounds(88, 18, 300, 30);
		contentPane.add(dispensary_lbl_title);
		
		JLabel dispensary_lbl_itemCode = new JLabel("Item code:");
		dispensary_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(dispensary_lbl_itemCode);
		
		JLabel dispensary_lbl_itemName = new JLabel("Item name:");
		dispensary_lbl_itemName.setBounds(50, 100, 90, 20);
		contentPane.add(dispensary_lbl_itemName);
		
		JLabel dispensary_lbl_gin = new JLabel("Gin:");
		dispensary_lbl_gin.setBounds(50, 140, 90, 20);
		contentPane.add(dispensary_lbl_gin);
		
		JLabel dispensary_lbl_grossWeight = new JLabel("Gross weight:");
		dispensary_lbl_grossWeight.setBounds(50, 180, 90, 20);
		contentPane.add(dispensary_lbl_grossWeight);
		
		JLabel dispensary_lbl_netWeight = new JLabel("Net weight");
		dispensary_lbl_netWeight.setBounds(50, 220, 90, 20);
		contentPane.add(dispensary_lbl_netWeight);
		
		JLabel dispensary_lbl_preparedDate = new JLabel("Date prepared:");
		dispensary_lbl_preparedDate.setBounds(50, 300, 90, 20);
		contentPane.add(dispensary_lbl_preparedDate);
		
		JLabel dispensary_lbl_printCount = new JLabel("Printing Count:");
		dispensary_lbl_printCount.setBounds(50, 340, 90, 20);
		contentPane.add(dispensary_lbl_printCount);
		
		JLabel dispensary_lbl_itemUnit = new JLabel("");
		dispensary_lbl_itemUnit.setBounds(340, 180, 80, 20);
		contentPane.add(dispensary_lbl_itemUnit);
				
		JLabel dispensary_lbl_itemUnit_c = new JLabel("");
		dispensary_lbl_itemUnit_c.setBounds(340, 220, 80, 20);
		contentPane.add(dispensary_lbl_itemUnit_c);
		
		JLabel dispensary_lbl_batchUsedIn = new JLabel("Batch used in:");
		dispensary_lbl_batchUsedIn.setBounds(50, 260, 90, 20);
		contentPane.add(dispensary_lbl_batchUsedIn);
		
		dispensary_batchUsedIn = new JTextField();
		dispensary_batchUsedIn.setBounds(160, 260, 180, 20);
		contentPane.add(dispensary_batchUsedIn);
		dispensary_batchUsedIn.setColumns(10);
		
		dispensary_itemCode = new JTextField();
		dispensary_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(dispensary_itemCode);
		dispensary_itemCode.setColumns(10);
		
		dispensary_gin = new JTextField();
		dispensary_gin.setBounds(160, 140, 180, 20);
		contentPane.add(dispensary_gin);
		dispensary_gin.setColumns(10);
		
		dispensary_grossWeight = new JTextField();
		dispensary_grossWeight.setBounds(160, 180, 180, 20);
		contentPane.add(dispensary_grossWeight);
		dispensary_grossWeight.setColumns(10);
		
		dispensary_netWeight = new JTextField();
		dispensary_netWeight.setBounds(160, 220, 180, 20);
		contentPane.add(dispensary_netWeight);
		dispensary_netWeight.setColumns(10);
		
		dispensary_datePrepared = new JTextField();
		dispensary_datePrepared.setBounds(160, 300, 180, 20);
		contentPane.add(dispensary_datePrepared);
		dispensary_datePrepared.setColumns(10);
		
		dispensary_printingCount = new JTextField();
		dispensary_printingCount.setBounds(160, 340, 180, 20);
		contentPane.add(dispensary_printingCount);
		dispensary_printingCount.setColumns(10);
		
		materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JComboBox dispensary_itemName = new JComboBox();
		dispensary_itemName.setModel(new DefaultComboBoxModel(sarray));
		dispensary_itemName.setMaximumRowCount(10);
		dispensary_itemName.setSelectedItem(null);
		dispensary_itemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) dispensary_itemName.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		dispensary_itemCode.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		dispensary_lbl_itemUnit.setText(itemUnit);
		        		dispensary_lbl_itemUnit_c.setText(itemUnit);
		        		break;
		        	}	        	
		        }
			}
		});
		dispensary_itemName.setBounds(160, 100, 180, 20);
		contentPane.add(dispensary_itemName);
		
		Button dispensary_btn_check = new Button("Check");
		dispensary_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = dispensary_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				boolean itemcode_exist = false;
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		dispensary_itemName.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		dispensary_lbl_itemUnit.setText(itemUnit);
		        		dispensary_lbl_itemUnit_c.setText(itemUnit);
		        		dispensary_itemCode.setText(itemCode_value);
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
		
		JButton dispensary_btn_settings = new JButton("Settings");
		dispensary_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		dispensary_btn_settings.setBounds(30, 380, 90, 20);
		contentPane.add(dispensary_btn_settings);
		
		JButton dispensary_btn_print = new JButton("Print");
		dispensary_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(dispensary_itemCode.getText(), dispensary_itemName.getSelectedItem().toString(), materials, dispensary_gin.getText(), dispensary_grossWeight.getText(), dispensary_netWeight.getText(), dispensary_datePrepared.getText(), dispensary_printingCount.getText(), dispensary_batchUsedIn.getText(),settings)) {
							int printNumber = Integer.valueOf(dispensary_printingCount.getText());
							int i = 0;
							while(i<printNumber) {
								printqueue.addLabelToQueue(new HW_DispensaryLabel(dispensary_itemCode.getText(), dispensary_itemName.getSelectedItem().toString(), dispensary_gin.getText(), dispensary_grossWeight.getText(), dispensary_netWeight.getText(), itemUnit, dispensary_batchUsedIn.getText(), settings.getSetting().getLogined_user(), 
										dispensary_datePrepared.getText()));
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
		dispensary_btn_print.setBounds(140, 380, 90, 20);
		contentPane.add(dispensary_btn_print);
		
		JButton dispensary_btn_update = new JButton("Update");
		dispensary_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings,"stk.csv", "materiallistpath");
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		dispensary_btn_update.setBounds(250, 380, 90, 20);
		contentPane.add(dispensary_btn_update);
		
		JButton dispensary_btn_back = new JButton("Back");
		dispensary_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		dispensary_btn_back.setBounds(360, 380, 90, 20);
		contentPane.add(dispensary_btn_back);
		
		JLabel label = new JLabel("Designed and Implemented by Lee.L");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(300, 415, 170, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(200, 430, 280, 20);
		contentPane.add(label_1);
		

		dispensary_btn_check.setBounds(340, 60, 70, 20);
		contentPane.add(dispensary_btn_check);
		
		Button dispensary_btn_info_preparedDate = new Button("i");
		dispensary_btn_info_preparedDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using system date."
					    );
			}
		});
		dispensary_btn_info_preparedDate.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		dispensary_btn_info_preparedDate.setBackground(Color.LIGHT_GRAY);
		dispensary_btn_info_preparedDate.setBounds(340, 300, 20, 20);
		contentPane.add(dispensary_btn_info_preparedDate);
		


	}
	
	public boolean dataValidation(String itemCode, String itemName, List<Material> materials, String ginNumber, String grossWeight, String netWeight, String preparedDate, String printingCount, String batchUsedIn, Settings settings) {
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
		
		if(batchUsedIn.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Batch Used In field missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			if(batchUsedIn.length()!=6) {				
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Batch Used In field Format Error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}else {
				String aCode = String.valueOf(batchUsedIn.charAt(0));
				aCode = aCode.replaceAll("([a-z])", "$1").toUpperCase();
				if(!aCode.equals("H")) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Batch Used In field format Error.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}				
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
