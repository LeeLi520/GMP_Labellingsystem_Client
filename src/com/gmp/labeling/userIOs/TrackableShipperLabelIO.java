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
import com.gmp.labeling.printModels.TrackableShipperLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;

public class TrackableShipperLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6776038921255165863L;
	private JPanel contentPane;
	private JTextField trackableShipper_itemCode;
	private JTextField trackableShipper_batch;
	private JTextField trackableShipper_useBy;
	private String selectedCompanyName;
	private List<Product> products;
	private String productQuantity;
	private JTextField trackableShipper_cartonNoStartFrom;
	private JTextField trackableShipper_labelPerCarton;
	private JTextField trackableShipper_cartonQuantity;
	private JTextField trackableShipper_bottomInfo;
	private String format;
	private JTextField trackableShipper_regNo;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TrackableShipperLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		
		
		
		
//		products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		
		products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel trackableShipper_lbl_title = new JLabel("Trackable Product Label");
		trackableShipper_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		trackableShipper_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		trackableShipper_lbl_title.setBounds(130, 20, 260, 30);
		contentPane.add(trackableShipper_lbl_title);
		
		JLabel trackableShipper_lbl_itemCode = new JLabel("Item Code:");
		trackableShipper_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		trackableShipper_lbl_itemCode.setForeground(new Color(0, 0, 0));
		trackableShipper_lbl_itemCode.setBounds(50, 60, 70, 20);
		contentPane.add(trackableShipper_lbl_itemCode);
		
		JLabel trackableShipper_cartonUnitQuantity = new JLabel("");
		trackableShipper_cartonUnitQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		trackableShipper_cartonUnitQuantity.setForeground(new Color(255, 0, 0));
		trackableShipper_cartonUnitQuantity.setBounds(155, 260, 50, 20);
		
		trackableShipper_bottomInfo = new JTextField();
		trackableShipper_bottomInfo.setBounds(155, 380, 305, 20);
		contentPane.add(trackableShipper_bottomInfo);
		trackableShipper_bottomInfo.setColumns(10);
		
		JComboBox trackableShipper_combox_shift = new JComboBox();
		trackableShipper_combox_shift.setBounds(375, 260, 100, 20);
		trackableShipper_combox_shift.setModel(new DefaultComboBoxModel(new String[] {"D/S","A/S","N/S"}));
		contentPane.add(trackableShipper_combox_shift);
		
		String[] productNameArray = new String[products.size()];
		int i = 0;
		for(Product p : products) {
			if(p.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = p.getProductName();
				i++;
			}			
		}
		
		trackableShipper_itemCode = new JTextField();
		trackableShipper_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(trackableShipper_itemCode);
		trackableShipper_itemCode.setColumns(10);
		
		JComboBox trackableShipper_combox_productName = new JComboBox();
		trackableShipper_combox_productName.setModel(new DefaultComboBoxModel(productNameArray));
		trackableShipper_combox_productName.setMaximumRowCount(10);
		trackableShipper_combox_productName.setBounds(160, 100, 200, 20);
		trackableShipper_combox_productName.setSelectedItem(null);
		trackableShipper_combox_productName.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String value = (String) trackableShipper_combox_productName.getSelectedItem();
		        for(Product product : products) {
		        	if(product.getProductName().equals(value)) {
		        		selectedCompanyName = product.getCompanyName();
		        		trackableShipper_itemCode.setText(product.getItemCode());
		        		productQuantity = product.getItemQuantity();		        		
		        		trackableShipper_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }
		    }});
		contentPane.add(trackableShipper_combox_productName);
		
		JLabel trackableShipper_lbl_productName = new JLabel("Product:");
		trackableShipper_lbl_productName.setBounds(50, 100, 70, 20);
		contentPane.add(trackableShipper_lbl_productName);
				
		JButton trackableShipper_btn_check = new JButton("Check");
		trackableShipper_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = trackableShipper_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Product product : products) {
		        	if(product.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		trackableShipper_combox_productName.setSelectedItem(product.getProductName());
		        		selectedCompanyName = product.getCompanyName();
		        		productQuantity = product.getItemQuantity();
		        		trackableShipper_itemCode.setText(product.getItemCode());
		        		trackableShipper_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }				
			}
		});
		trackableShipper_btn_check.setBounds(340, 60, 80, 20);
		contentPane.add(trackableShipper_btn_check);
		
		JLabel trackableShipper_lbl_batch = new JLabel("Batch:");
		trackableShipper_lbl_batch.setBounds(50, 140, 70, 20);
		contentPane.add(trackableShipper_lbl_batch);
		
		trackableShipper_batch = new JTextField();
		trackableShipper_batch.setBounds(160, 140, 180, 20);
		contentPane.add(trackableShipper_batch);
		trackableShipper_batch.setColumns(10);
		
		JLabel trackableShipper_lbl_useBy = new JLabel("Use By:");
		trackableShipper_lbl_useBy.setBounds(50, 220, 100, 20);
		contentPane.add(trackableShipper_lbl_useBy);
		
		JRadioButton trackableShipper_rdbtn_useby = new JRadioButton("Use By");
		trackableShipper_rdbtn_useby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trackableShipper_lbl_useBy.setText("Use By:");
			}
		});
		trackableShipper_rdbtn_useby.setBounds(160, 180, 80, 20);
		trackableShipper_rdbtn_useby.setSelected(true);
		contentPane.add(trackableShipper_rdbtn_useby);
		
		JRadioButton trackableShipper_rdbtn_expDate = new JRadioButton("Exp Date");
		trackableShipper_rdbtn_expDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trackableShipper_lbl_useBy.setText("Exp Date:");
			}
		});
		trackableShipper_rdbtn_expDate.setBounds(240, 180, 90, 20);
		contentPane.add(trackableShipper_rdbtn_expDate);
		
		JRadioButton trackableShipper_rdbtn_bestBefore = new JRadioButton("Best Before");
		trackableShipper_rdbtn_bestBefore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trackableShipper_lbl_useBy.setText("Best Before:");
			}
		});
		trackableShipper_rdbtn_bestBefore.setBounds(330, 180, 100, 20);
		contentPane.add(trackableShipper_rdbtn_bestBefore);
		
		JLabel trackableShipper_lbl_useByformat = new JLabel("Use By format:");
		trackableShipper_lbl_useByformat.setBounds(50, 180, 100, 20);
		contentPane.add(trackableShipper_lbl_useByformat);
		
		ButtonGroup useByformatGroup = new ButtonGroup();
		useByformatGroup.add(trackableShipper_rdbtn_useby);
		useByformatGroup.add(trackableShipper_rdbtn_expDate);
		useByformatGroup.add(trackableShipper_rdbtn_bestBefore);
		
		
		
		trackableShipper_useBy = new JTextField();
		trackableShipper_useBy.setBounds(160, 220, 180, 20);
		contentPane.add(trackableShipper_useBy);
		trackableShipper_useBy.setColumns(10);
		
		JRadioButton trackableShipper_rdbtn_AU = new JRadioButton("AU");
		format = "D/M/Y";
		trackableShipper_rdbtn_AU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "D/M/Y";
			}
		});
		trackableShipper_rdbtn_AU.setSelected(true);
		trackableShipper_rdbtn_AU.setBounds(378, 220, 50, 20);
		contentPane.add(trackableShipper_rdbtn_AU);
		
		JRadioButton trackableShipper_rdbtn_CN = new JRadioButton("CN");
		trackableShipper_rdbtn_CN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "Y/M/D";
			}
		});
		trackableShipper_rdbtn_CN.setBounds(428, 220, 50, 20);
		contentPane.add(trackableShipper_rdbtn_CN);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(trackableShipper_rdbtn_AU);
		dateformatGroup.add(trackableShipper_rdbtn_CN);
		
		Button trackableShipper_btn_useByInfo = new Button("i");
		trackableShipper_btn_useByInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018"
					    );
			}
		});
		trackableShipper_btn_useByInfo.setBounds(340, 220, 20, 20);
		contentPane.add(trackableShipper_btn_useByInfo);
		
		JLabel trackableShipper_lbl_temperature = new JLabel("Temperature:");
		trackableShipper_lbl_temperature.setBounds(230, 340, 90, 20);
		contentPane.add(trackableShipper_lbl_temperature);
		
		JLabel trackableShipper_lbl_temperatureIcon = new JLabel("\u00B0C");
		trackableShipper_lbl_temperatureIcon.setBounds(355, 340, 25, 20);
		contentPane.add(trackableShipper_lbl_temperatureIcon);
		
		JLabel trackableShipper_lbl_regNo = new JLabel("Reg No:");
		trackableShipper_lbl_regNo.setBounds(380, 340, 50, 20);
		contentPane.add(trackableShipper_lbl_regNo);
		
		trackableShipper_regNo = new JTextField();
		trackableShipper_regNo.setColumns(10);
		trackableShipper_regNo.setBounds(430, 340, 45, 20);
		contentPane.add(trackableShipper_regNo);
		
		JComboBox trackableShipper_temperature = new JComboBox();
		trackableShipper_temperature.setBounds(315, 340, 40, 20);
		trackableShipper_temperature.setModel(new DefaultComboBoxModel(new String[] {"30","25"}));
		contentPane.add(trackableShipper_temperature);
		
		JButton trackableShipper_btn_updateDB = new JButton("Update DB");
		trackableShipper_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				FPDBEditingIO fpdb  = new FPDBEditingIO(settings, printqueue);
				fpdb.setLocationRelativeTo(null);
				fpdb.setVisible(true);
				dispose();
			}
		});
		trackableShipper_btn_updateDB.setBounds(30, 410, 95, 20);
		contentPane.add(trackableShipper_btn_updateDB);
		
		JButton trackableShipper_btn_print = new JButton("Print");
		trackableShipper_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dateResult = DateFormatModifier.changeDate(trackableShipper_useBy.getText(), format);
				if(dataValidation(trackableShipper_itemCode.getText(), trackableShipper_combox_productName.getSelectedItem().toString(), trackableShipper_batch.getText(), productQuantity, trackableShipper_cartonNoStartFrom.getText(), trackableShipper_labelPerCarton.getText(), trackableShipper_cartonQuantity.getText(), trackableShipper_useBy.getText(), dateResult, trackableShipper_regNo.getText(),settings)) {
					    int temp_startFrom = Integer.valueOf(trackableShipper_cartonNoStartFrom.getText());
						int temp_lblPerCarton = Integer.valueOf(trackableShipper_labelPerCarton.getText());
						int temp_cartonQuantity = Integer.valueOf(trackableShipper_cartonQuantity.getText());							
						while(temp_cartonQuantity>0) {
								for(int i = temp_lblPerCarton; i>0; i--) {
								    Label productLabel = new TrackableShipperLabel(selectedCompanyName ,trackableShipper_combox_productName.getSelectedItem().toString(), trackableShipper_itemCode.getText(), trackableShipper_batch.getText(),
								    		productQuantity, String.valueOf(temp_startFrom), trackableShipper_lbl_useBy.getText(), dateResult, trackableShipper_combox_shift.getSelectedItem().toString(), trackableShipper_temperature.getSelectedItem().toString(), trackableShipper_regNo.getText(), trackableShipper_bottomInfo.getText());
								    printqueue.addLabelToQueue(productLabel);
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
		trackableShipper_btn_print.setBounds(260, 410, 95, 20);
		contentPane.add(trackableShipper_btn_print);
		
		JButton trackableShipper_btn_settings = new JButton("Settings");
		trackableShipper_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		trackableShipper_btn_settings.setBounds(145, 410, 95, 20);
		contentPane.add(trackableShipper_btn_settings);
		
		JLabel label = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(214, 450, 280, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Designed and Implemented by Lee.L");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(314, 435, 170, 20);
		contentPane.add(label_1);
		
		JButton trackableShipper_btn_back = new JButton("Back");
		trackableShipper_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		trackableShipper_btn_back.setBounds(375, 410, 95, 20);
		contentPane.add(trackableShipper_btn_back);
		
		JLabel trackableShipper_lbl_cartonNoStartFrom = new JLabel("Carton No Start from:");
		trackableShipper_lbl_cartonNoStartFrom.setBounds(50, 300, 130, 20);
		contentPane.add(trackableShipper_lbl_cartonNoStartFrom);
		
		trackableShipper_cartonNoStartFrom = new JTextField();
		trackableShipper_cartonNoStartFrom.setBounds(180, 300, 60, 20);
		contentPane.add(trackableShipper_cartonNoStartFrom);
		trackableShipper_cartonNoStartFrom.setColumns(10);
		
		JLabel trackableShipper_lbl_LabelPerCarton = new JLabel("Labels Per Carton:");
		trackableShipper_lbl_LabelPerCarton.setBounds(290, 300, 110, 20);
		contentPane.add(trackableShipper_lbl_LabelPerCarton);
		
		trackableShipper_labelPerCarton = new JTextField();
		trackableShipper_labelPerCarton.setToolTipText("def 1");
		trackableShipper_labelPerCarton.setBounds(405, 300, 70, 20);
		contentPane.add(trackableShipper_labelPerCarton);
		trackableShipper_labelPerCarton.setColumns(10);
		
		JLabel trackableShipper_lbl_cartonCount = new JLabel("Carton Quantity:");
		trackableShipper_lbl_cartonCount.setBounds(50, 340, 100, 20);
		contentPane.add(trackableShipper_lbl_cartonCount);
		
		trackableShipper_cartonQuantity = new JTextField();
		trackableShipper_cartonQuantity.setBounds(150, 340, 60, 20);
		contentPane.add(trackableShipper_cartonQuantity);
		trackableShipper_cartonQuantity.setColumns(10);
		
		JLabel trackableShipper_displayQuantity = new JLabel("Display Quantity:");
		trackableShipper_displayQuantity.setBounds(50, 260, 100, 20);
		contentPane.add(trackableShipper_displayQuantity);
		
		JLabel trackableShipper_unitDisplay = new JLabel("units/carton");
		trackableShipper_unitDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
		trackableShipper_unitDisplay.setBounds(220, 260, 90, 20);
		contentPane.add(trackableShipper_unitDisplay);
		contentPane.add(trackableShipper_cartonUnitQuantity);
		
		JLabel trackableShipper_lbl_shift = new JLabel("Shift:");
		trackableShipper_lbl_shift.setBounds(335, 260, 46, 20);
		contentPane.add(trackableShipper_lbl_shift);
		
		JLabel trackableShipper_lbl_bottomMessage = new JLabel("Bottom Message:");
		trackableShipper_lbl_bottomMessage.setBounds(50, 380, 100, 20);
		contentPane.add(trackableShipper_lbl_bottomMessage);
		
		Button trackableShipper_btn_bottomMsgInfo = new Button("i");
		trackableShipper_btn_bottomMsgInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "This is the bottom description shown under the label. Leaving it blank will bring the default one on the label."
					    );
			}
		});
		trackableShipper_btn_bottomMsgInfo.setBounds(460, 380, 20, 20);
		contentPane.add(trackableShipper_btn_bottomMsgInfo);
						
	}
	
	public boolean dataValidation(String itemCode, String productName, String batch, String quantity, String cartonFrom, String labelPerCarton, String cartonQuantity, String useBy, String dateResult, String regNo,Settings settings) {
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
		}else if(batch.length()>6&&batch.length()<5) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Invalid batch number",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(!useBy.equals("")) {
			char[] temp = useBy.toCharArray();
			if(useBy.length() ==10) {
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
		if(dateResult.equals(null)) {
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
		if(!regNo.equals("")) {
			try {
				@SuppressWarnings("unused")
				int regNumber = Integer.parseInt(regNo);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Reg no error",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
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
