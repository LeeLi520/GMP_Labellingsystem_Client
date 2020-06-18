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
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;
import java.awt.Canvas;

public class CustomizedPrinting_ProductLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2909607591145389035L;
	private JPanel contentPane;
	private JTextField product_gin_Number;
	private JTextField product_item_Code;
	private JTextField product_quantity;
	private JTextField product_no_Container;
	private JTextField product_received_Date;
	private JTextField product_printing_Count;
	private String itemUnit;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CustomizedPrinting_ProductLabelIO(Settings settings, PrintingQueue printqueue, List<Label> tempList, JLabel printCount, int x, int y) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, 500, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Product Label Printing");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setBounds(130, 18, 230, 30);
		contentPane.add(lblNewLabel);
		
		JLabel product_lbl_labeltype = new JLabel("Label type:");
		product_lbl_labeltype.setBounds(50, 60, 90, 20);
		contentPane.add(product_lbl_labeltype);
		
		JLabel product_lbl_itemCode = new JLabel("Item code:");
		product_lbl_itemCode.setBounds(50, 100, 90, 20);
		contentPane.add(product_lbl_itemCode);
		
		JLabel product_lbl_itemName = new JLabel("Item name:");
		product_lbl_itemName.setBounds(50, 140, 90, 20);
		contentPane.add(product_lbl_itemName);
		
		JLabel product_lbl_ginNumber = new JLabel("Gin:");
		product_lbl_ginNumber.setBounds(50, 180, 90, 20);
		contentPane.add(product_lbl_ginNumber);
		
		JLabel product_lbl_quantity = new JLabel("Qty/Unit:");
		product_lbl_quantity.setBounds(50, 220, 90, 20);
		contentPane.add(product_lbl_quantity);
		
		JLabel product_lbl_noContainer = new JLabel("No. Container:");
		product_lbl_noContainer.setBounds(50, 260, 90, 20);
		contentPane.add(product_lbl_noContainer);
		
		JLabel product_lbl_date = new JLabel("Received date:");
		product_lbl_date.setBounds(50, 300, 90, 20);
		contentPane.add(product_lbl_date);
				
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
		
		product_no_Container = new JTextField();
		product_no_Container.setBounds(160, 260, 180, 20);
		contentPane.add(product_no_Container);
		product_no_Container.setColumns(10);
		
		product_received_Date = new JTextField();
		product_received_Date.setBounds(160, 300, 180, 20);
		contentPane.add(product_received_Date);
		product_received_Date.setColumns(10);
		
		product_printing_Count = new JTextField();
		product_printing_Count.setBounds(160, 340, 180, 20);
		contentPane.add(product_printing_Count);
		product_printing_Count.setColumns(10);
		
		JLabel product_lbl_item_unit = new JLabel("");
		product_lbl_item_unit.setBounds(340, 220, 80, 20);
		contentPane.add(product_lbl_item_unit);
		
		Button product_btn_info = new Button("i");
		product_btn_info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yy. \n dd=>day  mm=>month  yy=>year \n eg. 20th May 2018 => 20/05/18 \n Leave it blank means using the system date."
					    );
			}
		});
		product_btn_info.setBackground(Color.LIGHT_GRAY);
		product_btn_info.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		product_btn_info.setBounds(340, 300, 20, 20);
		contentPane.add(product_btn_info);
        
//		List<Material> materials = inputItemdata(".\\src\\com\\gmp\\labeling\\resources\\datafiles\\inv.csv");
		List<Material> materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

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
		
		product_label_Type.setModel(new DefaultComboBoxModel(new String[] {"Pharmaceutical Use", "Non-Pharmaceutical Use", "Packaging"}));
		product_label_Type.setBounds(160, 60, 180, 20);
		product_label_Type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) product_label_Type.getSelectedItem();
				switch(value) {
				case "Pharmaceutical Use":
					canvas.setBackground(new Color(255, 255, 0));
					break;
				case "Non-Pharmaceutical Use":
					canvas.setBackground(new Color(255, 69, 0));
					break;
				case "Packaging":
					canvas.setBackground(new Color(0, 255, 255));
					break;
				}
			}
		});
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
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		product_item_Name.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		itemUnit = JsonParser.takeoffComma(temp.getItem_unit());
		        		product_lbl_item_unit.setText(itemUnit);
		        		product_item_Code.setText(itemCode_value);
		        		break;
		        	}	        	
		        }
			}
		});
		product_btn_Check.setBounds(340, 100, 60, 20);
		contentPane.add(product_btn_Check);
		
		JButton product_btn_Print = new JButton("Add To Queue");
		product_btn_Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(product_item_Code.getText(), product_item_Name.getSelectedItem().toString(), product_label_Type.getSelectedItem().toString(), product_gin_Number.getText(), product_quantity.getText(), product_received_Date.getText(), product_no_Container.getText(), product_printing_Count.getText(), settings)) {
					int printNumber = Integer.valueOf(product_printing_Count.getText());
					tempList.clear();
					int i = 0;
//					while(i<printNumber) {
//						ProductLabel product = new ProductLabel(product_item_Code.getText(), product_item_Name.getSelectedItem().toString(),
//								product_gin_Number.getText(), product_label_Type.getSelectedItem().toString(), product_quantity.getText(), product_received_Date.getText(),
//								product_no_Container.getText(), itemUnit);
//						printqueue.addLabelToQueue(product);
//						tempList.add(product);
//						i++;
//					}
				}
				printCount.setText(String.valueOf(printqueue.getList().size()));
			}
		});
		product_btn_Print.setBounds(40, 380, 120, 20);
		contentPane.add(product_btn_Print);
		
		JButton product_btn_Back = new JButton("Close");
		product_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		product_btn_Back.setBounds(340, 380, 120, 20);
		contentPane.add(product_btn_Back);
		
		JLabel product_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		product_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		product_copyright_company.setBounds(200, 430, 280, 20);
		contentPane.add(product_copyright_company);
		
		JLabel product_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		product_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		product_copyright_developer.setBounds(300, 415, 170, 20);
		contentPane.add(product_copyright_developer);
		
		JButton product_btn_update = new JButton("Update");
		product_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings);
				dispose();
			}
		});
		product_btn_update.setBounds(190, 380, 120, 20);
		contentPane.add(product_btn_update);
					
	}
	
	public boolean dataValidation(String itemCode, String itemName, String itemType, String ginNumber, String quantity, String receivedDate, String noContainer, String printingCount, Settings settings) {
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(itemName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item name missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(ginNumber.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "GIN number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
			Float.parseFloat(quantity);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item Qty missing or Invalid input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
			Integer.parseInt(noContainer);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "No.Container missing or Invalid input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!receivedDate.equals("")) {
		char[] temp = receivedDate.toCharArray();
		if(temp.length == 8) {
		String day = String.valueOf(temp[0])+String.valueOf(temp[1]);
		String month = String.valueOf(temp[3]) + String.valueOf(temp[4]);
		String year = String.valueOf(temp[6])+ String.valueOf(temp[7]);
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
			int realyear = yearint +2000;
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
			Integer.parseInt(printingCount);
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
