package com.gmp.labeling.userIOs;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.DateFormatModifier;
import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Product;
import com.gmp.labeling.printModels.BulkProductLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Button;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BulkProductLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3177587421197017959L;
	private JPanel contentPane;
	private JTextField bulkProduct_itemCode;
	private JTextField bulkProduct_batch;
	private JTextField bulkProduct_useBy;
	private List<Product> products;
	private String productQuantity;
	private JTextField bulkProduct_cartonNoStartFrom;
	private JTextField bulkProduct_labelPerCarton;
	private JTextField bulkProduct_cartonQuantity;
	private String format;

	public BulkProductLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		products = inputItemdata(connection.loadProperties().getProperty("bulkdbList"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel bulkProduct_lbl_title = new JLabel("Bulk Shipper Label");
		bulkProduct_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		bulkProduct_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		bulkProduct_lbl_title.setBounds(135, 20, 240, 30);
		contentPane.add(bulkProduct_lbl_title);
		
		JLabel bulkProduct_lbl_itemCode = new JLabel("Item Code:");
		bulkProduct_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		bulkProduct_lbl_itemCode.setForeground(new Color(0, 0, 0));
		bulkProduct_lbl_itemCode.setBounds(60, 60, 70, 20);
		contentPane.add(bulkProduct_lbl_itemCode);
		
		JLabel bulkProduct_cartonUnitQuantity = new JLabel("");
		bulkProduct_cartonUnitQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		bulkProduct_cartonUnitQuantity.setForeground(new Color(255, 0, 0));
		bulkProduct_cartonUnitQuantity.setBounds(155, 220, 50, 20);
		
		String[] productNameArray = new String[products.size()];
		int i = 0;
		for(Product p : products) {
			if(p.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = p.getProductName();
				i++;
			}			
		}
		
		bulkProduct_itemCode = new JTextField();
		bulkProduct_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(bulkProduct_itemCode);
		bulkProduct_itemCode.setColumns(10);
		
		JComboBox bulkProduct_combox_productName = new JComboBox();
		bulkProduct_combox_productName.setModel(new DefaultComboBoxModel(productNameArray));
		bulkProduct_combox_productName.setMaximumRowCount(10);
		bulkProduct_combox_productName.setBounds(160, 100, 200, 20);
		bulkProduct_combox_productName.setSelectedItem(null);
		bulkProduct_combox_productName.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String value = (String) bulkProduct_combox_productName.getSelectedItem();
		        for(Product product : products) {
		        	if(product.getProductName().equals(value)) {
		        		bulkProduct_itemCode.setText(product.getItemCode());
		        		productQuantity = product.getItemQuantity();
		    	 		DecimalFormat formatter = new DecimalFormat("###,###,###");
		    	 		int quantity = Integer.valueOf(productQuantity);
		        		bulkProduct_cartonUnitQuantity.setText(formatter.format(quantity));
		        		break;
		        	}	        	
		        }
		    }});
		contentPane.add(bulkProduct_combox_productName);
		
		JLabel bulkProduct_lbl_productName = new JLabel("Product:");
		bulkProduct_lbl_productName.setBounds(60, 100, 70, 20);
		contentPane.add(bulkProduct_lbl_productName);
				
		JButton bulkProduct_btn_check = new JButton("Check");
		bulkProduct_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = bulkProduct_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Product product : products) {
		        	if(product.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		bulkProduct_combox_productName.setSelectedItem(product.getProductName());
		        		productQuantity = product.getItemQuantity();
		        		bulkProduct_itemCode.setText(product.getItemCode());
		    	 		DecimalFormat formatter = new DecimalFormat("###,###,###");
		    	 		int quantity = Integer.valueOf(productQuantity);
		        		bulkProduct_cartonUnitQuantity.setText(formatter.format(quantity));
		        		break;
		        	}	        	
		        }				
			}
		});
		bulkProduct_btn_check.setBounds(340, 60, 80, 20);
		contentPane.add(bulkProduct_btn_check);
		
		JLabel bulkProduct_lbl_batch = new JLabel("Batch:");
		bulkProduct_lbl_batch.setBounds(60, 140, 70, 20);
		contentPane.add(bulkProduct_lbl_batch);
		
		bulkProduct_batch = new JTextField();
		bulkProduct_batch.setBounds(160, 140, 180, 20);
		contentPane.add(bulkProduct_batch);
		bulkProduct_batch.setColumns(10);
		
		JLabel bulkProduct_lbl_useBy = new JLabel("DOM:");
		bulkProduct_lbl_useBy.setBounds(60, 180, 100, 20);
		contentPane.add(bulkProduct_lbl_useBy);
		
		ButtonGroup useByformatGroup = new ButtonGroup();
		
		
		
		bulkProduct_useBy = new JTextField();
		bulkProduct_useBy.setBounds(160, 180, 180, 20);
		contentPane.add(bulkProduct_useBy);
		bulkProduct_useBy.setColumns(10);
		
		JRadioButton bulkProduct_rdbtn_AU = new JRadioButton("AU");
		format = "D/M/Y";
		bulkProduct_rdbtn_AU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "D/M/Y";
			}
		});
		bulkProduct_rdbtn_AU.setSelected(true);
		bulkProduct_rdbtn_AU.setBounds(378, 180, 50, 20);
		contentPane.add(bulkProduct_rdbtn_AU);
		
		JRadioButton bulkProduct_rdbtn_CN = new JRadioButton("CN");
		bulkProduct_rdbtn_CN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "Y/M/D";
			}
		});
		bulkProduct_rdbtn_CN.setBounds(428, 180, 50, 20);
		contentPane.add(bulkProduct_rdbtn_CN);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(bulkProduct_rdbtn_AU);
		dateformatGroup.add(bulkProduct_rdbtn_CN);
		
		Button bulkProduct_btn_useByInfo = new Button("i");
		bulkProduct_btn_useByInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018"
					    );
			}
		});
		bulkProduct_btn_useByInfo.setBounds(340, 180, 20, 20);
		contentPane.add(bulkProduct_btn_useByInfo);
		
		JLabel bulkProduct_lbl_temperature = new JLabel("Temperature:");
		bulkProduct_lbl_temperature.setBounds(275, 300, 110, 20);
		contentPane.add(bulkProduct_lbl_temperature);
		
		JComboBox bulkProduct_temperature = new JComboBox();
		bulkProduct_temperature.setBounds(384, 300, 40, 20);
		bulkProduct_temperature.setModel(new DefaultComboBoxModel(new String[] {"25","30"}));
		contentPane.add(bulkProduct_temperature);
		
		JLabel bulkProduct_lbl_temperatureIcon = new JLabel("\u00B0C");
		bulkProduct_lbl_temperatureIcon.setBounds(424, 300, 30, 20);
		contentPane.add(bulkProduct_lbl_temperatureIcon);
		
		JButton bulkProduct_btn_updateDB = new JButton("Update DB");
		bulkProduct_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				BulkDBEditingIO blkdb  = new BulkDBEditingIO(settings, printqueue);
				blkdb.setLocationRelativeTo(null);
				blkdb.setVisible(true);
				dispose();
			}
		});
		bulkProduct_btn_updateDB.setBounds(30, 340, 95, 20);
		contentPane.add(bulkProduct_btn_updateDB);
		
		JButton bulkProduct_btn_print = new JButton("Print");
		bulkProduct_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(dataValidation(bulkProduct_itemCode.getText(), bulkProduct_combox_productName.getSelectedItem().toString(), products, bulkProduct_batch.getText(), bulkProduct_cartonNoStartFrom.getText(), bulkProduct_labelPerCarton.getText(), bulkProduct_cartonQuantity.getText(), bulkProduct_useBy.getText(), settings)) {
					String dateResult = DateFormatModifier.changeDate(bulkProduct_useBy.getText(), format);
					int temp_startFrom = Integer.valueOf(bulkProduct_cartonNoStartFrom.getText());
					int temp_lblPerCarton = Integer.valueOf(bulkProduct_labelPerCarton.getText());
					int temp_cartonQuantity = Integer.valueOf(bulkProduct_cartonQuantity.getText());							
					while(temp_cartonQuantity>0) {
							for(int i = temp_lblPerCarton; i>0; i--) {
								Label bulkproductlabel = new BulkProductLabel(bulkProduct_combox_productName.getSelectedItem().toString(), bulkProduct_itemCode.getText(), bulkProduct_batch.getText(), productQuantity,
										String.valueOf(temp_startFrom), dateResult, bulkProduct_temperature.getSelectedItem().toString());
								 printqueue.addLabelToQueue(bulkproductlabel);
							}						
							temp_cartonQuantity--;
							temp_startFrom++;
					}
					
					if(settings.getSetting().isLocalPrintingMode()) {
									PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), null, 0, 680);
									printio.setLocationRelativeTo(null);
									printio.setVisible(true);						
					}else {
									PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), settings.getSetting().getIpAddress(), settings.getSetting().getPort(), 680);
									printio.setLocationRelativeTo(null);
									printio.setVisible(true);			
					}
				}
			}
		});
		bulkProduct_btn_print.setBounds(260, 340, 95, 20);
		contentPane.add(bulkProduct_btn_print);
		
		JButton bulkProduct_btn_settings = new JButton("Settings");
		bulkProduct_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		bulkProduct_btn_settings.setBounds(145, 340, 95, 20);
		contentPane.add(bulkProduct_btn_settings);
		
		JLabel bulkProduct_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		bulkProduct_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		bulkProduct_lbl_copyright_2.setBounds(214, 380, 280, 20);
		contentPane.add(bulkProduct_lbl_copyright_2);
		
		JLabel bulkProduct_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		bulkProduct_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		bulkProduct_lbl_copyright_1.setBounds(314, 365, 170, 20);
		contentPane.add(bulkProduct_lbl_copyright_1);
		
		JButton bulkProduct_btn_back = new JButton("Back");
		bulkProduct_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		bulkProduct_btn_back.setBounds(375, 340, 95, 20);
		contentPane.add(bulkProduct_btn_back);
		
		JLabel bulkProduct_lbl_cartonNoStartFrom = new JLabel("Carton No Start from:");
		bulkProduct_lbl_cartonNoStartFrom.setBounds(60, 260, 130, 20);
		contentPane.add(bulkProduct_lbl_cartonNoStartFrom);
		
		bulkProduct_cartonNoStartFrom = new JTextField();
		bulkProduct_cartonNoStartFrom.setBounds(194, 260, 60, 20);
		contentPane.add(bulkProduct_cartonNoStartFrom);
		bulkProduct_cartonNoStartFrom.setColumns(10);
		
		JLabel bulkProduct_lbl_LabelPerCarton = new JLabel("Labels Per Carton:");
		bulkProduct_lbl_LabelPerCarton.setBounds(275, 260, 110, 20);
		contentPane.add(bulkProduct_lbl_LabelPerCarton);
		
		bulkProduct_labelPerCarton = new JTextField();
		bulkProduct_labelPerCarton.setToolTipText("def 1");
		bulkProduct_labelPerCarton.setBounds(384, 260, 80, 20);
		contentPane.add(bulkProduct_labelPerCarton);
		bulkProduct_labelPerCarton.setColumns(10);
		
		JLabel bulkProduct_lbl_cartonCount = new JLabel("Carton Quantity:");
		bulkProduct_lbl_cartonCount.setBounds(60, 300, 100, 20);
		contentPane.add(bulkProduct_lbl_cartonCount);
		
		bulkProduct_cartonQuantity = new JTextField();
		bulkProduct_cartonQuantity.setBounds(160, 300, 95, 20);
		contentPane.add(bulkProduct_cartonQuantity);
		bulkProduct_cartonQuantity.setColumns(10);
		
		JLabel bulkProduct_displayQuantity = new JLabel("Contents:");
		bulkProduct_displayQuantity.setBounds(60, 220, 100, 20);
		contentPane.add(bulkProduct_displayQuantity);
		
		JLabel bulkProduct_unitDisplay = new JLabel("units/carton");
		bulkProduct_unitDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
		bulkProduct_unitDisplay.setBounds(210, 220, 90, 20);
		contentPane.add(bulkProduct_unitDisplay);
		contentPane.add(bulkProduct_cartonUnitQuantity);
	

	}
	
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public List<Product> inputItemdata(String path) {
		String csvFile = path;
        String line = "";
        String cvsSplitBy = ",";
        List<Product> products = new ArrayList<Product>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
            	if(line.contains("Item Code") && line.contains("Product Name")) {
            		//first line dont input
            	}else {
                String[] items = line.split(cvsSplitBy);
                Product product = new Product();
                product.setItemCode(items[0]);
                product.setProductName(items[1]);
                product.setItemQuantity(items[2]);
                products.add(product);
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
	}
	
	public boolean dataValidation(String itemCode, String productName, List<Product> products, String batch, String cartonFrom, String labelPerCarton, String cartonQuantity, String useBy, Settings settings) {
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			boolean itemcodeExist = false;
			for(Product code_temp: products) {
				if(JsonParser.takeoffComma(code_temp.getItemCode()).equals(itemCode.replaceAll("([a-z])", "$1").toUpperCase())) {
					itemcodeExist = true;
					break;
				}
			}
			if(!itemcodeExist) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Item code does not exist. Please ask Department Supervisors for related configuration.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if(productName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "No product selection.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		for(Product p : products) {
			if(JsonParser.takeoffComma(p.getItemCode()).equals(itemCode.replaceAll("([a-z])", "$1").toUpperCase())) {
				if(!JsonParser.takeoffComma(p.getProductName()).equals(productName)) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Item Code and Product Name are not correlated. Please Click \"Check\" to get the correct Item name in the system. "
						    + "\n"
						    + "Or Ask department supervisors to update details of the items.",
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
				    "Batch missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!useBy.equals("")) {
			char[] temp = useBy.toCharArray();
			if(temp.length == 10) {
			String day = String.valueOf(temp[0])+String.valueOf(temp[1]);
			String month = String.valueOf(temp[3]) + String.valueOf(temp[4]);
			String year = String.valueOf(temp[6])+ String.valueOf(temp[7])+ String.valueOf(temp[8])+ String.valueOf(temp[9]);
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
					    "Used By value missing.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		if(cartonFrom.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Carton Start from value missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(labelPerCarton.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Label Per Carton value missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}				
		if(cartonQuantity.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Carton Quantity missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}		
		return true;
	}

}
