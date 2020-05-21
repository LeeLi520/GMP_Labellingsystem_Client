package com.gmp.labeling.userIOs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.DateFormatModifier;
import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.FPLabelObject;
import com.gmp.labeling.models.Product;
import com.gmp.labeling.printModels.BioProductLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.UniversalProductLabel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Button;
import javax.swing.JRadioButton;

public class FinishProductLabelIO extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -81813253414069125L;
	private JTextField bioProduct_itemCode;
	private JTextField bioProduct_batch;
	private JTextField bioProduct_useBy;
	private String selectedCompanyName;
	private List<Product> products;
	private String productQuantity;
	private JTextField bioProduct_cartonNoStartFrom;
	private JTextField bioProduct_labelPerCarton;
	private JTextField bioProduct_cartonQuantity;
	private JPanel contentPane;
	private JTextField bioProduct_bottomInfo;
	private FPLabelObject previousLabel_1;
	private FPLabelObject previousLabel_2;
	private int repeatCount;
	private String format;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FinishProductLabelIO(Settings settings, PrintingQueue printqueue) {
		repeatCount = 0;
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		previousLabel_1 = null;
		previousLabel_2 = null;
		
		JLabel bioProduct_lbl_title = new JLabel("Finished Product Label");
		bioProduct_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		bioProduct_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		bioProduct_lbl_title.setBounds(135, 20, 240, 30);
		contentPane.add(bioProduct_lbl_title);
		
		JLabel bioProduct_lbl_itemCode = new JLabel("Item Code:");
		bioProduct_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		bioProduct_lbl_itemCode.setForeground(new Color(0, 0, 0));
		bioProduct_lbl_itemCode.setBounds(60, 60, 70, 20);
		contentPane.add(bioProduct_lbl_itemCode);
		
		JLabel bioProduct_cartonUnitQuantity = new JLabel("");
		bioProduct_cartonUnitQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		bioProduct_cartonUnitQuantity.setForeground(new Color(255, 0, 0));
		bioProduct_cartonUnitQuantity.setBounds(155, 260, 50, 20);
		
		bioProduct_bottomInfo = new JTextField();
		bioProduct_bottomInfo.setBounds(160, 380, 304, 20);
		contentPane.add(bioProduct_bottomInfo);
		bioProduct_bottomInfo.setColumns(10);
		
		JButton bioProduct_btn_goPrevious = new JButton("<");
		bioProduct_btn_goPrevious.setBorderPainted(false);
		bioProduct_btn_goPrevious.setFont(new Font("Arial", Font.BOLD, 18));
		bioProduct_btn_goPrevious.setBounds(20, 15, 50, 40);
		bioProduct_btn_goPrevious.setEnabled(false);
		
		JComboBox bioProduct_combox_shift = new JComboBox();
		bioProduct_combox_shift.setBounds(384, 260, 80, 20);
		bioProduct_combox_shift.setModel(new DefaultComboBoxModel(new String[] {"D/S","A/S","N/S"}));
		contentPane.add(bioProduct_combox_shift);
		
		String[] productNameArray = new String[products.size()];
		int i = 0;
		for(Product p : products) {
			if(p.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = p.getProductName();
				i++;
			}			
		}
		
		bioProduct_itemCode = new JTextField();
		bioProduct_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(bioProduct_itemCode);
		bioProduct_itemCode.setColumns(10);
		
		JComboBox bioProduct_combox_productName = new JComboBox();
		bioProduct_combox_productName.setModel(new DefaultComboBoxModel(productNameArray));
		bioProduct_combox_productName.setMaximumRowCount(10);
		bioProduct_combox_productName.setBounds(160, 100, 200, 20);
		bioProduct_combox_productName.setSelectedItem(null);
		bioProduct_combox_productName.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String value = (String) bioProduct_combox_productName.getSelectedItem();
		        for(Product product : products) {
		        	if(product.getProductName().equals(value)) {
		        		selectedCompanyName = product.getCompanyName();
		        		bioProduct_itemCode.setText(product.getItemCode());
		        		productQuantity = product.getItemQuantity();		        		
		        		bioProduct_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }
		    }});
		contentPane.add(bioProduct_combox_productName);
		
		JLabel bioProduct_lbl_productName = new JLabel("Product:");
		bioProduct_lbl_productName.setBounds(60, 100, 70, 20);
		contentPane.add(bioProduct_lbl_productName);
				
		JButton bioProduct_btn_check = new JButton("Check");
		bioProduct_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = bioProduct_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				boolean itemcode_exist = false;
				for(Product product : products) {
		        	if(product.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		bioProduct_combox_productName.setSelectedItem(product.getProductName());
		        		selectedCompanyName = product.getCompanyName();
		        		productQuantity = product.getItemQuantity();
		        		bioProduct_itemCode.setText(product.getItemCode());
		        		bioProduct_cartonUnitQuantity.setText(product.getItemQuantity());
		        		itemcode_exist = true;
		        		break;
		        	}	        	
		        }
				if(!itemcode_exist) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Item code does not exist. Please contact Department Supervisors to set up new items properly.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		bioProduct_btn_check.setBounds(340, 60, 80, 20);
		contentPane.add(bioProduct_btn_check);
		
		JLabel bioProduct_lbl_batch = new JLabel("Batch:");
		bioProduct_lbl_batch.setBounds(60, 140, 70, 20);
		contentPane.add(bioProduct_lbl_batch);
		
		bioProduct_batch = new JTextField();
		bioProduct_batch.setBounds(160, 140, 180, 20);
		contentPane.add(bioProduct_batch);
		bioProduct_batch.setColumns(10);
		
		JLabel bioProduct_lbl_useBy = new JLabel("Use By:");
		bioProduct_lbl_useBy.setBounds(60, 220, 100, 20);
		contentPane.add(bioProduct_lbl_useBy);
		
		JRadioButton BioProduct_rdbtn_useby = new JRadioButton("Use By");
		BioProduct_rdbtn_useby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bioProduct_lbl_useBy.setText("Use By:");
			}
		});
		BioProduct_rdbtn_useby.setBounds(160, 180, 80, 20);
		BioProduct_rdbtn_useby.setSelected(true);
		contentPane.add(BioProduct_rdbtn_useby);
		
		JRadioButton BioProduct_rdbtn_expDate = new JRadioButton("Exp Date");
		BioProduct_rdbtn_expDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bioProduct_lbl_useBy.setText("Exp Date:");
			}
		});
		BioProduct_rdbtn_expDate.setBounds(240, 180, 90, 20);
		contentPane.add(BioProduct_rdbtn_expDate);
		
		JRadioButton BioProduct_rdbtn_bestBefore = new JRadioButton("Best Before");
		BioProduct_rdbtn_bestBefore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bioProduct_lbl_useBy.setText("Best Before:");
			}
		});
		BioProduct_rdbtn_bestBefore.setBounds(330, 180, 100, 20);
		contentPane.add(BioProduct_rdbtn_bestBefore);
		
		JLabel bioProduct_lbl_useByformat = new JLabel("Use By format:");
		bioProduct_lbl_useByformat.setBounds(60, 180, 100, 20);
		contentPane.add(bioProduct_lbl_useByformat);
		
		ButtonGroup useByformatGroup = new ButtonGroup();
		useByformatGroup.add(BioProduct_rdbtn_useby);
		useByformatGroup.add(BioProduct_rdbtn_expDate);
		useByformatGroup.add(BioProduct_rdbtn_bestBefore);
		
		bioProduct_useBy = new JTextField();
		bioProduct_useBy.setBounds(160, 220, 180, 20);
		contentPane.add(bioProduct_useBy);
		bioProduct_useBy.setColumns(10);
		
		JRadioButton BioProduct_rdbtn_AU = new JRadioButton("AU");
		format = "D/M/Y";
		BioProduct_rdbtn_AU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "D/M/Y";
			}
		});
		BioProduct_rdbtn_AU.setSelected(true);
		BioProduct_rdbtn_AU.setBounds(378, 220, 50, 20);
		contentPane.add(BioProduct_rdbtn_AU);
		
		JRadioButton BioProduct_rdbtn_CN = new JRadioButton("CN");
		BioProduct_rdbtn_CN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "Y/M/D";
			}
		});
		BioProduct_rdbtn_CN.setBounds(428, 220, 50, 20);
		contentPane.add(BioProduct_rdbtn_CN);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(BioProduct_rdbtn_AU);
		dateformatGroup.add(BioProduct_rdbtn_CN);
		
		Button bioProduct_btn_useByInfo = new Button("i");
		bioProduct_btn_useByInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018"
					    );
			}
		});
		bioProduct_btn_useByInfo.setBounds(340, 220, 20, 20);
		contentPane.add(bioProduct_btn_useByInfo);
		
		JLabel bioProduct_lbl_temperature = new JLabel("Temperature:");
		bioProduct_lbl_temperature.setBounds(275, 340, 110, 20);
		contentPane.add(bioProduct_lbl_temperature);
		
		JComboBox bioProduct_temperature = new JComboBox();
		bioProduct_temperature.setBounds(384, 340, 60, 20);
		bioProduct_temperature.setModel(new DefaultComboBoxModel(new String[] {"30","25"}));
		contentPane.add(bioProduct_temperature);
		
		JLabel bioProduct_lbl_temperatureIcon = new JLabel("\u00B0C");
		bioProduct_lbl_temperatureIcon.setBounds(445, 340, 30, 20);
		contentPane.add(bioProduct_lbl_temperatureIcon);
		
		JButton bioProduct_btn_updateDB = new JButton("Update DB");
		bioProduct_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				FPDBEditingIO fpdb  = new FPDBEditingIO(settings, printqueue);
				fpdb.setLocationRelativeTo(null);
				fpdb.setVisible(true);
				dispose();
			}
		});
		bioProduct_btn_updateDB.setBounds(30, 410, 95, 20);
		contentPane.add(bioProduct_btn_updateDB);
		
		JButton bioProduct_btn_print = new JButton("Print");
		bioProduct_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dateResult = DateFormatModifier.changeDate(bioProduct_useBy.getText(), format);
				if(dataValidation(bioProduct_itemCode.getText(), bioProduct_combox_productName.getSelectedItem().toString(), products, bioProduct_batch.getText(), productQuantity, bioProduct_cartonNoStartFrom.getText(), bioProduct_labelPerCarton.getText(), bioProduct_cartonQuantity.getText(), bioProduct_useBy.getText(), dateResult, settings)) {
					    int temp_startFrom = Integer.valueOf(bioProduct_cartonNoStartFrom.getText());
						int temp_lblPerCarton = Integer.valueOf(bioProduct_labelPerCarton.getText());
						int temp_cartonQuantity = Integer.valueOf(bioProduct_cartonQuantity.getText());
						if(repeatCount == 0) {
							previousLabel_1 = new FPLabelObject(selectedCompanyName, bioProduct_combox_productName.getSelectedItem().toString(), bioProduct_itemCode.getText(), bioProduct_batch.getText(),
						    		productQuantity, bioProduct_cartonNoStartFrom.getText(), bioProduct_labelPerCarton.getText(), bioProduct_cartonQuantity.getText(), bioProduct_useBy.getText(), bioProduct_combox_shift.getSelectedItem().toString(), bioProduct_temperature.getSelectedItem().toString(), bioProduct_bottomInfo.getText());
						   repeatCount ++;
						}else if(repeatCount == 1) {
							previousLabel_2 = new FPLabelObject(selectedCompanyName, bioProduct_combox_productName.getSelectedItem().toString(), bioProduct_itemCode.getText(), bioProduct_batch.getText(),
						    		productQuantity, bioProduct_cartonNoStartFrom.getText(), bioProduct_labelPerCarton.getText(), bioProduct_cartonQuantity.getText(), bioProduct_useBy.getText(), bioProduct_combox_shift.getSelectedItem().toString(), bioProduct_temperature.getSelectedItem().toString(), bioProduct_bottomInfo.getText());
							bioProduct_btn_goPrevious.setEnabled(true);
							repeatCount ++;
						}else {
							previousLabel_1 = previousLabel_2;
							previousLabel_2 = new FPLabelObject(selectedCompanyName, bioProduct_combox_productName.getSelectedItem().toString(), bioProduct_itemCode.getText(), bioProduct_batch.getText(),
						    		productQuantity, bioProduct_cartonNoStartFrom.getText(), bioProduct_labelPerCarton.getText(), bioProduct_cartonQuantity.getText(), bioProduct_useBy.getText(), bioProduct_combox_shift.getSelectedItem().toString(), bioProduct_temperature.getSelectedItem().toString(), bioProduct_bottomInfo.getText());
							bioProduct_btn_goPrevious.setEnabled(true);
						}
											
						switch(selectedCompanyName) {
						case "Bio Island":
							while(temp_cartonQuantity>0) {
								for(int i = temp_lblPerCarton; i>0; i--) {
								    Label productLabel = new BioProductLabel(bioProduct_combox_productName.getSelectedItem().toString(), bioProduct_itemCode.getText(), bioProduct_batch.getText(),
								    		productQuantity, String.valueOf(temp_startFrom), bioProduct_lbl_useBy.getText(), dateResult, bioProduct_combox_shift.getSelectedItem().toString(), bioProduct_temperature.getSelectedItem().toString(), bioProduct_bottomInfo.getText());
								    printqueue.addLabelToQueue(productLabel);
								}						
								temp_cartonQuantity--;
								temp_startFrom++;
							}
							break;	
							
						default:
							while(temp_cartonQuantity>0) {
								for(int i = temp_lblPerCarton; i>0; i--) {
								    Label productLabel = new UniversalProductLabel(selectedCompanyName ,bioProduct_combox_productName.getSelectedItem().toString(), bioProduct_itemCode.getText(), bioProduct_batch.getText(),
								    		productQuantity, String.valueOf(temp_startFrom), bioProduct_lbl_useBy.getText(), dateResult, bioProduct_combox_shift.getSelectedItem().toString(), bioProduct_temperature.getSelectedItem().toString(), bioProduct_bottomInfo.getText());
								    printqueue.addLabelToQueue(productLabel);
								}						
								temp_cartonQuantity--;
								temp_startFrom++;
							}
							break;
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
		bioProduct_btn_print.setBounds(260, 410, 95, 20);
		contentPane.add(bioProduct_btn_print);
		
		JButton bioProduct_btn_settings = new JButton("Settings");
		bioProduct_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		bioProduct_btn_settings.setBounds(145, 410, 95, 20);
		contentPane.add(bioProduct_btn_settings);
		
		JLabel label = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(214, 450, 280, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Designed and Implemented by Lee.L");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(314, 435, 170, 20);
		contentPane.add(label_1);
		
		JButton bioProduct_btn_back = new JButton("Back");
		bioProduct_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		bioProduct_btn_back.setBounds(375, 410, 95, 20);
		contentPane.add(bioProduct_btn_back);
		
		JLabel bioProduct_lbl_cartonNoStartFrom = new JLabel("Carton No Start from:");
		bioProduct_lbl_cartonNoStartFrom.setBounds(60, 300, 130, 20);
		contentPane.add(bioProduct_lbl_cartonNoStartFrom);
		
		bioProduct_cartonNoStartFrom = new JTextField();
		bioProduct_cartonNoStartFrom.setBounds(194, 300, 60, 20);
		contentPane.add(bioProduct_cartonNoStartFrom);
		bioProduct_cartonNoStartFrom.setColumns(10);
		
		JLabel bioProduct_lbl_LabelPerCarton = new JLabel("Labels Per Carton:");
		bioProduct_lbl_LabelPerCarton.setBounds(275, 300, 110, 20);
		contentPane.add(bioProduct_lbl_LabelPerCarton);
		
		bioProduct_labelPerCarton = new JTextField();
		bioProduct_labelPerCarton.setToolTipText("def 1");
		bioProduct_labelPerCarton.setBounds(384, 300, 80, 20);
		contentPane.add(bioProduct_labelPerCarton);
		bioProduct_labelPerCarton.setColumns(10);
		
		JLabel bioProduct_lbl_cartonCount = new JLabel("Carton Quantity:");
		bioProduct_lbl_cartonCount.setBounds(60, 340, 100, 20);
		contentPane.add(bioProduct_lbl_cartonCount);
		
		bioProduct_cartonQuantity = new JTextField();
		bioProduct_cartonQuantity.setBounds(160, 340, 95, 20);
		contentPane.add(bioProduct_cartonQuantity);
		bioProduct_cartonQuantity.setColumns(10);
		
		JLabel bioProduct_displayQuantity = new JLabel("Display Quantity:");
		bioProduct_displayQuantity.setBounds(60, 260, 100, 20);
		contentPane.add(bioProduct_displayQuantity);
		
		JLabel bioProduct_unitDisplay = new JLabel("units/carton");
		bioProduct_unitDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
		bioProduct_unitDisplay.setBounds(210, 260, 90, 20);
		contentPane.add(bioProduct_unitDisplay);
		contentPane.add(bioProduct_cartonUnitQuantity);
		
		JLabel bioProduct_lbl_shift = new JLabel("Shift:");
		bioProduct_lbl_shift.setBounds(338, 260, 46, 20);
		contentPane.add(bioProduct_lbl_shift);
		
		JLabel bioProduct_lbl_bottomMessage = new JLabel("Bottom Message:");
		bioProduct_lbl_bottomMessage.setBounds(60, 380, 100, 20);
		contentPane.add(bioProduct_lbl_bottomMessage);
		
		Button bioProduct_btn_bottomMsgInfo = new Button("i");
		bioProduct_btn_bottomMsgInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "This is the bottom description shown under the label. Leaving it blank will bring the default one on the label."
					    );
			}
		});
		bioProduct_btn_bottomMsgInfo.setBounds(464, 380, 20, 20);
		contentPane.add(bioProduct_btn_bottomMsgInfo);
		
		
		bioProduct_btn_goPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedCompanyName = previousLabel_1.getCompanyName();
				bioProduct_itemCode.setText(previousLabel_1.getItemCode());
				bioProduct_combox_productName.setSelectedItem(previousLabel_1.getProductName());
				bioProduct_batch.setText(previousLabel_1.getBatch());
				bioProduct_useBy.setText(previousLabel_1.getUseBy());
				bioProduct_cartonUnitQuantity.setText(previousLabel_1.getQuantity());
				productQuantity = previousLabel_1.getQuantity();
				bioProduct_cartonNoStartFrom.setText(previousLabel_1.getStartFrom());
				bioProduct_labelPerCarton.setText(previousLabel_1.getLabelPerCarton());
				bioProduct_cartonQuantity.setText(previousLabel_1.getCartonQuantity());
				bioProduct_bottomInfo.setText(previousLabel_1.getBtmNoticeInfo());
				previousLabel_1 = previousLabel_2;
				previousLabel_2 = null;
				repeatCount = 1;
				bioProduct_btn_goPrevious.setEnabled(false);
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Please Change the Date format for previous label if necessary.");				
			}
		});
		contentPane.add(bioProduct_btn_goPrevious);
		
				
	}
	
	public boolean dataValidation(String itemCode, String productName, List<Product> products, String batch, String quantity, String cartonFrom, String labelPerCarton, String cartonQuantity, String useBy, String dateResult, Settings settings) {
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
