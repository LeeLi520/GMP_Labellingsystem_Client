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

import javax.swing.ButtonGroup;
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

import com.gmp.labeling.connections.DateFormatModifier;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Product;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.LiquidShipperLabel;
import com.gmp.labeling.printModels.PrintingQueue;

public class LiquidShipperLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5698164412515653268L;
	private JPanel contentPane;
	private JTextField liquidShipper_itemCode;
	private JTextField liquidShipper_batch;
	private JTextField liquidShipper_useBy;
	private String selectedCompanyName;
	private List<Product> products;
	private String productQuantity;
	private JTextField liquidShipper_cartonNoStartFrom;
	private JTextField liquidShipper_labelPerCarton;
	private JTextField liquidShipper_cartonQuantity;
	private String format;
	private JTextField liquidShipper_shift;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LiquidShipperLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		products = inputItemdata(connection.loadProperties().getProperty("liquiddbList"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel liquidShipper_lbl_title = new JLabel("Liquid Shipper Label");
		liquidShipper_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		liquidShipper_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		liquidShipper_lbl_title.setBounds(135, 20, 240, 30);
		contentPane.add(liquidShipper_lbl_title);
		
		JLabel liquidShipper_lbl_itemCode = new JLabel("Item Code:");
		liquidShipper_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		liquidShipper_lbl_itemCode.setForeground(new Color(0, 0, 0));
		liquidShipper_lbl_itemCode.setBounds(60, 60, 70, 20);
		contentPane.add(liquidShipper_lbl_itemCode);
		
		JLabel liquidShipper_cartonUnitQuantity = new JLabel("");
		liquidShipper_cartonUnitQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		liquidShipper_cartonUnitQuantity.setForeground(new Color(255, 0, 0));
		liquidShipper_cartonUnitQuantity.setBounds(155, 260, 50, 20);
		
		String[] productNameArray = new String[products.size()];
		int i = 0;
		for(Product p : products) {
			if(p.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = p.getProductName();
				i++;
			}			
		}
		
		liquidShipper_itemCode = new JTextField();
		liquidShipper_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(liquidShipper_itemCode);
		liquidShipper_itemCode.setColumns(10);
		
		JLabel liquidShipper_lbl_shift = new JLabel("Shift:");
		liquidShipper_lbl_shift.setBounds(338, 260, 46, 20);
		contentPane.add(liquidShipper_lbl_shift);
		
		JComboBox liquidShipper_useByformat = new JComboBox();
		liquidShipper_useByformat.setModel(new DefaultComboBoxModel(new String[] {"Use By","Best Before","EXP","Expiry Date","EXPIRY DATE"}));
		liquidShipper_useByformat.setBounds(160, 180, 200, 20);
		contentPane.add(liquidShipper_useByformat);
		
		liquidShipper_shift = new JTextField();
		liquidShipper_shift.setBounds(384, 260, 80, 20);
		contentPane.add(liquidShipper_shift);
		liquidShipper_shift.setColumns(10);
		
		JComboBox liquidShipper_combox_productName = new JComboBox();
		liquidShipper_combox_productName.setModel(new DefaultComboBoxModel(productNameArray));
		liquidShipper_combox_productName.setMaximumRowCount(10);
		liquidShipper_combox_productName.setBounds(160, 100, 200, 20);
		liquidShipper_combox_productName.setSelectedItem(null);
		liquidShipper_combox_productName.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String value = (String) liquidShipper_combox_productName.getSelectedItem();
		        for(Product product : products) {
		        	if(product.getProductName().equals(value)) {
		        		selectedCompanyName = product.getCompanyName();
		        		liquidShipper_itemCode.setText(product.getItemCode());
		        		productQuantity = product.getItemQuantity();		        		
		        		liquidShipper_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }
		    }});
		contentPane.add(liquidShipper_combox_productName);
		
		JLabel liquidShipper_lbl_productName = new JLabel("Product:");
		liquidShipper_lbl_productName.setBounds(60, 100, 70, 20);
		contentPane.add(liquidShipper_lbl_productName);
				
		JButton liquidShipper_btn_check = new JButton("Check");
		liquidShipper_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = liquidShipper_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Product product : products) {
		        	if(product.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		liquidShipper_combox_productName.setSelectedItem(product.getProductName());
		        		selectedCompanyName = product.getCompanyName();
		        		productQuantity = product.getItemQuantity();
		        		liquidShipper_itemCode.setText(product.getItemCode());
		        		liquidShipper_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }				
			}
		});
		liquidShipper_btn_check.setBounds(340, 60, 80, 20);
		contentPane.add(liquidShipper_btn_check);
		
		JLabel liquidShipper_lbl_batch = new JLabel("Batch:");
		liquidShipper_lbl_batch.setBounds(60, 140, 70, 20);
		contentPane.add(liquidShipper_lbl_batch);
		
		liquidShipper_batch = new JTextField();
		liquidShipper_batch.setBounds(160, 140, 180, 20);
		contentPane.add(liquidShipper_batch);
		liquidShipper_batch.setColumns(10);
		
		JLabel liquidShipper_lbl_useBy = new JLabel("Use By:");
		liquidShipper_lbl_useBy.setBounds(60, 220, 100, 20);
		contentPane.add(liquidShipper_lbl_useBy);
		
		JLabel liquidShipper_lbl_useByformat = new JLabel("Use By format:");
		liquidShipper_lbl_useByformat.setBounds(60, 180, 100, 20);
		contentPane.add(liquidShipper_lbl_useByformat);
		
		liquidShipper_useBy = new JTextField();
		liquidShipper_useBy.setBounds(160, 220, 180, 20);
		contentPane.add(liquidShipper_useBy);
		liquidShipper_useBy.setColumns(10);
		
		JRadioButton liquidShipper_rdbtn_AU = new JRadioButton("AU");
		format = "D/M/Y";
		liquidShipper_rdbtn_AU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "D/M/Y";
			}
		});
		liquidShipper_rdbtn_AU.setSelected(true);
		liquidShipper_rdbtn_AU.setBounds(378, 220, 50, 20);
		contentPane.add(liquidShipper_rdbtn_AU);
		
		JRadioButton liquidShipper_rdbtn_CN = new JRadioButton("CN");
		liquidShipper_rdbtn_CN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "Y/M/D";
			}
		});
		liquidShipper_rdbtn_CN.setBounds(428, 220, 50, 20);
		contentPane.add(liquidShipper_rdbtn_CN);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(liquidShipper_rdbtn_AU);
		dateformatGroup.add(liquidShipper_rdbtn_CN);
		
		Button liquidShipper_btn_useByInfo = new Button("i");
		liquidShipper_btn_useByInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018"
					    );
			}
		});
		liquidShipper_btn_useByInfo.setBounds(340, 220, 20, 20);
		contentPane.add(liquidShipper_btn_useByInfo);
		
		JLabel liquidShipper_lbl_temperature = new JLabel("Temperature:");
		liquidShipper_lbl_temperature.setBounds(275, 340, 110, 20);
		contentPane.add(liquidShipper_lbl_temperature);
		
		JComboBox liquidShipper_temperature = new JComboBox();
		liquidShipper_temperature.setBounds(384, 340, 40, 20);
		liquidShipper_temperature.setModel(new DefaultComboBoxModel(new String[] {"30","25"}));
		contentPane.add(liquidShipper_temperature);
		
		JLabel liquidShipper_lbl_temperatureIcon = new JLabel("\u00B0C");
		liquidShipper_lbl_temperatureIcon.setBounds(424, 340, 30, 20);
		contentPane.add(liquidShipper_lbl_temperatureIcon);
		
		JButton liquidShipper_btn_updateDB = new JButton("Update DB");
		liquidShipper_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				LiquidDBEditingIO fpdb  = new LiquidDBEditingIO(settings, printqueue);
				fpdb.setLocationRelativeTo(null);
				fpdb.setVisible(true);
				dispose();
			}
		});
		liquidShipper_btn_updateDB.setBounds(30, 380, 95, 20);
		contentPane.add(liquidShipper_btn_updateDB);
		
		JButton liquidShipper_btn_print = new JButton("Print");
		liquidShipper_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(dataValidation(liquidShipper_itemCode.getText(), liquidShipper_combox_productName.getSelectedItem().toString(), liquidShipper_batch.getText(), 
						productQuantity, liquidShipper_cartonNoStartFrom.getText(), liquidShipper_labelPerCarton.getText(), liquidShipper_cartonQuantity.getText(), liquidShipper_useBy.getText(), settings)) {
					String dateResult = DateFormatModifier.changeDate(liquidShipper_useBy.getText(), format);
					int temp_startFrom = Integer.valueOf(liquidShipper_cartonNoStartFrom.getText());
					int temp_lblPerCarton = Integer.valueOf(liquidShipper_labelPerCarton.getText());
					int temp_cartonQuantity = Integer.valueOf(liquidShipper_cartonQuantity.getText());							
					while(temp_cartonQuantity>0) {
							for(int i = temp_lblPerCarton; i>0; i--) {
							    Label liquidshipperlabel = new LiquidShipperLabel(selectedCompanyName, liquidShipper_combox_productName.getSelectedItem().toString(), liquidShipper_itemCode.getText(), liquidShipper_batch.getText(),
							    		productQuantity, String.valueOf(temp_startFrom), liquidShipper_useByformat.getSelectedItem().toString(), dateResult, liquidShipper_shift.getText(), liquidShipper_temperature.getSelectedItem().toString());
							    printqueue.addLabelToQueue(liquidshipperlabel);
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
		liquidShipper_btn_print.setBounds(260, 380, 95, 20);
		contentPane.add(liquidShipper_btn_print);
		
		JButton liquidShipper_btn_settings = new JButton("Settings");
		liquidShipper_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		liquidShipper_btn_settings.setBounds(145, 380, 95, 20);
		contentPane.add(liquidShipper_btn_settings);
		
		JLabel liquidShipper_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		liquidShipper_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		liquidShipper_lbl_copyright_2.setBounds(214, 420, 280, 20);
		contentPane.add(liquidShipper_lbl_copyright_2);
		
		JLabel liquidShipper_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		liquidShipper_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		liquidShipper_lbl_copyright_1.setBounds(314, 405, 170, 20);
		contentPane.add(liquidShipper_lbl_copyright_1);
		
		JButton liquidShipper_btn_back = new JButton("Back");
		liquidShipper_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		liquidShipper_btn_back.setBounds(375, 380, 95, 20);
		contentPane.add(liquidShipper_btn_back);
		
		JLabel liquidShipper_lbl_cartonNoStartFrom = new JLabel("Carton No Start from:");
		liquidShipper_lbl_cartonNoStartFrom.setBounds(60, 300, 130, 20);
		contentPane.add(liquidShipper_lbl_cartonNoStartFrom);
		
		liquidShipper_cartonNoStartFrom = new JTextField();
		liquidShipper_cartonNoStartFrom.setBounds(194, 300, 60, 20);
		contentPane.add(liquidShipper_cartonNoStartFrom);
		liquidShipper_cartonNoStartFrom.setColumns(10);
		
		JLabel liquidShipper_lbl_LabelPerCarton = new JLabel("Labels Per Carton:");
		liquidShipper_lbl_LabelPerCarton.setBounds(275, 300, 110, 20);
		contentPane.add(liquidShipper_lbl_LabelPerCarton);
		
		liquidShipper_labelPerCarton = new JTextField();
		liquidShipper_labelPerCarton.setToolTipText("def 1");
		liquidShipper_labelPerCarton.setBounds(384, 300, 80, 20);
		contentPane.add(liquidShipper_labelPerCarton);
		liquidShipper_labelPerCarton.setColumns(10);
		
		JLabel liquidShipper_lbl_cartonCount = new JLabel("Carton Quantity:");
		liquidShipper_lbl_cartonCount.setBounds(60, 340, 100, 20);
		contentPane.add(liquidShipper_lbl_cartonCount);
		
		liquidShipper_cartonQuantity = new JTextField();
		liquidShipper_cartonQuantity.setBounds(160, 340, 95, 20);
		contentPane.add(liquidShipper_cartonQuantity);
		liquidShipper_cartonQuantity.setColumns(10);
		
		JLabel liquidShipper_displayQuantity = new JLabel("Display Quantity:");
		liquidShipper_displayQuantity.setBounds(60, 260, 100, 20);
		contentPane.add(liquidShipper_displayQuantity);
		
		JLabel liquidShipper_unitDisplay = new JLabel("units/carton");
		liquidShipper_unitDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
		liquidShipper_unitDisplay.setBounds(210, 260, 90, 20);
		contentPane.add(liquidShipper_unitDisplay);
		contentPane.add(liquidShipper_cartonUnitQuantity);
	
				
	}
	
	public boolean dataValidation(String itemCode, String productName, String batch, String quantity, String cartonFrom, String labelPerCarton, String cartonQuantity, String useBy, Settings settings) {
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(productName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "No product selection.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
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
            	if(line.contains("Company") && line.contains("Item Code")) {
            		//first line dont input
            	}else {
                String[] items = line.split(cvsSplitBy);
                Product product = new Product();
                product.setCompanyName(items[0]);
                product.setItemCode(items[1]);
                product.setProductName(items[2]);
                product.setItemQuantity(items[3]);
                products.add(product);
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
	}
}
