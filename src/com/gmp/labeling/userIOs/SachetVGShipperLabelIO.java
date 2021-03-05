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
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.SachetVidaShipperLabel;

public class SachetVGShipperLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5201201399315510285L;
	private JPanel contentPane;
	private JTextField sachetVGProduct_itemCode;
	private JTextField sachetVGProduct_workOrder;
	private JTextField sachetVGProduct_useBy;
	private String selectedCompanyName;
	private List<Product> products;
	private String productQuantity;
	private JTextField sachetVGProduct_cartonNoStartFrom;
	private JTextField sachetVGProduct_labelPerCarton;
	private JTextField sachetVGProduct_cartonQuantity;
	private String format;
	private JTextField sachetVGProduct_shift;
	private JTextField sachetVGProduct_batch;
	private JTextField sachetVGProduct_btmMessage;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SachetVGShipperLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel sachetVGProduct_lbl_title = new JLabel("Sachet Product Label");
		sachetVGProduct_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		sachetVGProduct_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		sachetVGProduct_lbl_title.setBounds(130, 20, 260, 30);
		contentPane.add(sachetVGProduct_lbl_title);
		
		JLabel sachetVGProduct_lbl_itemCode = new JLabel("Item Code:");
		sachetVGProduct_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sachetVGProduct_lbl_itemCode.setForeground(new Color(0, 0, 0));
		sachetVGProduct_lbl_itemCode.setBounds(60, 60, 70, 20);
		contentPane.add(sachetVGProduct_lbl_itemCode);
		
		JLabel sachetVGProduct_cartonUnitQuantity = new JLabel("");
		sachetVGProduct_cartonUnitQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		sachetVGProduct_cartonUnitQuantity.setForeground(new Color(255, 0, 0));
		sachetVGProduct_cartonUnitQuantity.setBounds(155, 260, 50, 20);
		
		String[] productNameArray = new String[products.size()];
		int i = 0;
		for(Product p : products) {
			if(p.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = p.getProductName();
				i++;
			}			
		}
		
		sachetVGProduct_itemCode = new JTextField();
		sachetVGProduct_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(sachetVGProduct_itemCode);
		sachetVGProduct_itemCode.setColumns(10);
		
		JComboBox sachetVGProduct_combox_productName = new JComboBox();
		sachetVGProduct_combox_productName.setModel(new DefaultComboBoxModel(productNameArray));
		sachetVGProduct_combox_productName.setMaximumRowCount(10);
		sachetVGProduct_combox_productName.setBounds(160, 100, 200, 20);
		sachetVGProduct_combox_productName.setSelectedItem(null);
		sachetVGProduct_combox_productName.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String value = (String) sachetVGProduct_combox_productName.getSelectedItem();
		        for(Product product : products) {
		        	if(product.getProductName().equals(value)) {
		        		selectedCompanyName = product.getCompanyName();
		        		sachetVGProduct_itemCode.setText(product.getItemCode());
		        		productQuantity = product.getItemQuantity();		        		
		        		sachetVGProduct_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }
		    }});
		contentPane.add(sachetVGProduct_combox_productName);
		
		JLabel sachetVGProduct_lbl_productName = new JLabel("Product:");
		sachetVGProduct_lbl_productName.setBounds(60, 100, 70, 20);
		contentPane.add(sachetVGProduct_lbl_productName);
				
		JButton sachetVGProduct_btn_check = new JButton("Check");
		sachetVGProduct_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = sachetVGProduct_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Product product : products) {
		        	if(product.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		sachetVGProduct_combox_productName.setSelectedItem(product.getProductName());
		        		selectedCompanyName = product.getCompanyName();
		        		productQuantity = product.getItemQuantity();
		        		sachetVGProduct_itemCode.setText(product.getItemCode());
		        		sachetVGProduct_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }				
			}
		});
		sachetVGProduct_btn_check.setBounds(340, 60, 80, 20);
		contentPane.add(sachetVGProduct_btn_check);
		
		JLabel sachetVGProduct_lbl_workOrder = new JLabel("W/O:");
		sachetVGProduct_lbl_workOrder.setBounds(60, 140, 70, 20);
		contentPane.add(sachetVGProduct_lbl_workOrder);
		
		sachetVGProduct_workOrder = new JTextField();
		sachetVGProduct_workOrder.setBounds(160, 140, 110, 20);
		contentPane.add(sachetVGProduct_workOrder);
		sachetVGProduct_workOrder.setColumns(10);
		
		JLabel sachetVGProduct_lbl_batch = new JLabel("Batch:");
		sachetVGProduct_lbl_batch.setBounds(275, 140, 70, 20);
		contentPane.add(sachetVGProduct_lbl_batch);
		
		sachetVGProduct_batch = new JTextField();
		sachetVGProduct_batch.setColumns(10);
		sachetVGProduct_batch.setBounds(340, 140, 110, 20);
		contentPane.add(sachetVGProduct_batch);
		
		JLabel sachetVGProduct_lbl_cartonNoStartFrom = new JLabel("Carton No Start from:");
		sachetVGProduct_lbl_cartonNoStartFrom.setBounds(60, 300, 130, 20);
		contentPane.add(sachetVGProduct_lbl_cartonNoStartFrom);
		
		sachetVGProduct_cartonNoStartFrom = new JTextField();
		sachetVGProduct_cartonNoStartFrom.setBounds(194, 300, 60, 20);
		contentPane.add(sachetVGProduct_cartonNoStartFrom);
		sachetVGProduct_cartonNoStartFrom.setColumns(10);
		
		JLabel sachetVGProduct_lbl_LabelPerCarton = new JLabel("Labels Per Carton:");
		sachetVGProduct_lbl_LabelPerCarton.setBounds(275, 300, 110, 20);
		contentPane.add(sachetVGProduct_lbl_LabelPerCarton);
		
		sachetVGProduct_labelPerCarton = new JTextField();
		sachetVGProduct_labelPerCarton.setToolTipText("def 1");
		sachetVGProduct_labelPerCarton.setBounds(384, 300, 80, 20);
		contentPane.add(sachetVGProduct_labelPerCarton);
		sachetVGProduct_labelPerCarton.setColumns(10);
		
		JLabel sachetVGProduct_lbl_cartonCount = new JLabel("Carton Quantity:");
		sachetVGProduct_lbl_cartonCount.setBounds(60, 340, 100, 20);
		contentPane.add(sachetVGProduct_lbl_cartonCount);
		
		sachetVGProduct_cartonQuantity = new JTextField();
		sachetVGProduct_cartonQuantity.setBounds(170, 340, 85, 20);
		contentPane.add(sachetVGProduct_cartonQuantity);
		sachetVGProduct_cartonQuantity.setColumns(10);
		
		JLabel sachetVGProduct_displayQuantity = new JLabel("Display Quantity:");
		sachetVGProduct_displayQuantity.setBounds(60, 260, 100, 20);
		contentPane.add(sachetVGProduct_displayQuantity);
		
		JLabel sachetVGProduct_unitDisplay = new JLabel("units/carton");
		sachetVGProduct_unitDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
		sachetVGProduct_unitDisplay.setBounds(210, 260, 90, 20);
		contentPane.add(sachetVGProduct_unitDisplay);
		contentPane.add(sachetVGProduct_cartonUnitQuantity);
		
		JLabel sachetVGProduct_lbl_shift = new JLabel("Shift:");
		sachetVGProduct_lbl_shift.setBounds(338, 260, 46, 20);
		contentPane.add(sachetVGProduct_lbl_shift);
		
		sachetVGProduct_shift = new JTextField();
		sachetVGProduct_shift.setBounds(384, 260, 80, 20);
		contentPane.add(sachetVGProduct_shift);
		sachetVGProduct_shift.setColumns(10);
		
		JLabel sachetVGProduct_lbl_btmMessage = new JLabel("Bottom message:");
		sachetVGProduct_lbl_btmMessage.setBounds(60, 380, 100, 20);
		contentPane.add(sachetVGProduct_lbl_btmMessage);
		
		sachetVGProduct_btmMessage = new JTextField();
		sachetVGProduct_btmMessage.setBounds(170, 380, 295, 20);
		contentPane.add(sachetVGProduct_btmMessage);
		sachetVGProduct_btmMessage.setColumns(10);
			
		JLabel sachetVGProduct_lbl_useBy = new JLabel("Use By:");
		sachetVGProduct_lbl_useBy.setBounds(60, 220, 140, 20);
		contentPane.add(sachetVGProduct_lbl_useBy);
		
		JComboBox sachetVGProduct_combox_useByFormat = new JComboBox();
		sachetVGProduct_combox_useByFormat.setBounds(160, 180, 200, 20);
		sachetVGProduct_combox_useByFormat.setModel(new DefaultComboBoxModel(new String[] {"Use By", "Date of Manufacture", "Exp Date", "Exp", "Expiry date", "DOM", "Best Before", "Use Before"}));
		sachetVGProduct_combox_useByFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sachetVGProduct_lbl_useBy.setText(sachetVGProduct_combox_useByFormat.getSelectedItem().toString() + ":");
			}
		});

		contentPane.add(sachetVGProduct_combox_useByFormat);
		
//		JRadioButton sachetVGProduct_rdbtn_useby = new JRadioButton("Use By");
//		sachetVGProduct_rdbtn_useby.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetVGProduct_lbl_useBy.setText("Use By:");
//			}
//		});
//		sachetVGProduct_rdbtn_useby.setBounds(160, 180, 80, 20);
//		sachetVGProduct_rdbtn_useby.setSelected(true);
//		contentPane.add(sachetVGProduct_rdbtn_useby);
//		
//		JRadioButton sachetVGProduct_rdbtn_expDate = new JRadioButton("Exp Date");
//		sachetVGProduct_rdbtn_expDate.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetVGProduct_lbl_useBy.setText("Exp Date:");
//			}
//		});
//		sachetVGProduct_rdbtn_expDate.setBounds(240, 180, 90, 20);
//		contentPane.add(sachetVGProduct_rdbtn_expDate);
//		
//		JRadioButton sachetVGProduct_rdbtn_bestBefore = new JRadioButton("Best Before");
//		sachetVGProduct_rdbtn_bestBefore.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetVGProduct_lbl_useBy.setText("Best Before:");
//			}
//		});
//		sachetVGProduct_rdbtn_bestBefore.setBounds(330, 180, 100, 20);
//		contentPane.add(sachetVGProduct_rdbtn_bestBefore);
		
		JLabel sachetVGProduct_lbl_useByformat = new JLabel("Use By format:");
		sachetVGProduct_lbl_useByformat.setBounds(60, 180, 100, 20);
		contentPane.add(sachetVGProduct_lbl_useByformat);
		
//		ButtonGroup useByformatGroup = new ButtonGroup();
//		useByformatGroup.add(sachetVGProduct_rdbtn_useby);
//		useByformatGroup.add(sachetVGProduct_rdbtn_expDate);
//		useByformatGroup.add(sachetVGProduct_rdbtn_bestBefore);
		
		
		
		sachetVGProduct_useBy = new JTextField();
		sachetVGProduct_useBy.setBounds(200, 220, 140, 20);
		contentPane.add(sachetVGProduct_useBy);
		sachetVGProduct_useBy.setColumns(10);
		
		JRadioButton sachetVGProduct_rdbtn_AU = new JRadioButton("AU");
		format = "D/M/Y";
		sachetVGProduct_rdbtn_AU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "D/M/Y";
			}
		});
		sachetVGProduct_rdbtn_AU.setSelected(true);
		sachetVGProduct_rdbtn_AU.setBounds(378, 220, 50, 20);
		contentPane.add(sachetVGProduct_rdbtn_AU);
		
		JRadioButton sachetVGProduct_rdbtn_CN = new JRadioButton("CN");
		sachetVGProduct_rdbtn_CN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "Y/M/D";
			}
		});
		sachetVGProduct_rdbtn_CN.setBounds(428, 220, 50, 20);
		contentPane.add(sachetVGProduct_rdbtn_CN);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(sachetVGProduct_rdbtn_AU);
		dateformatGroup.add(sachetVGProduct_rdbtn_CN);
		
		Button sachetVGProduct_btn_useByInfo = new Button("i");
		sachetVGProduct_btn_useByInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018"
					    );
			}
		});
		sachetVGProduct_btn_useByInfo.setBounds(340, 220, 20, 20);
		contentPane.add(sachetVGProduct_btn_useByInfo);
		
		JLabel sachetVGProduct_lbl_temperature = new JLabel("Temperature:");
		sachetVGProduct_lbl_temperature.setBounds(275, 340, 110, 20);
		contentPane.add(sachetVGProduct_lbl_temperature);
		
		JComboBox sachetVGProduct_temperature = new JComboBox();
		sachetVGProduct_temperature.setBounds(384, 340, 60, 20);
		sachetVGProduct_temperature.setModel(new DefaultComboBoxModel(new String[] {"30","25"}));
		contentPane.add(sachetVGProduct_temperature);
		
		JLabel sachetVGProduct_lbl_temperatureIcon = new JLabel("\u00B0C");
		sachetVGProduct_lbl_temperatureIcon.setBounds(445, 340, 30, 20);
		contentPane.add(sachetVGProduct_lbl_temperatureIcon);
		
		JButton sachetVGProduct_btn_updateDB = new JButton("Update DB");
		sachetVGProduct_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				FPDBEditingIO fpdb  = new FPDBEditingIO(settings, printqueue);
				fpdb.setLocationRelativeTo(null);
				fpdb.setVisible(true);
				dispose();
			}
		});
		sachetVGProduct_btn_updateDB.setBounds(30, 430, 95, 20);
		contentPane.add(sachetVGProduct_btn_updateDB);
		
		JButton sachetVGProduct_btn_print = new JButton("Print");
		sachetVGProduct_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dateResult = DateFormatModifier.changeDate(sachetVGProduct_useBy.getText(), format);
				if(dataValidation(sachetVGProduct_itemCode.getText(), sachetVGProduct_combox_productName.getSelectedItem().toString(), sachetVGProduct_workOrder.getText(), sachetVGProduct_batch.getText(), productQuantity, sachetVGProduct_cartonNoStartFrom.getText(), sachetVGProduct_labelPerCarton.getText(), sachetVGProduct_cartonQuantity.getText(), sachetVGProduct_useBy.getText(), dateResult, settings)) {
					    int temp_startFrom = Integer.valueOf(sachetVGProduct_cartonNoStartFrom.getText());
						int temp_lblPerCarton = Integer.valueOf(sachetVGProduct_labelPerCarton.getText());
						int temp_cartonQuantity = Integer.valueOf(sachetVGProduct_cartonQuantity.getText());							
						while(temp_cartonQuantity>0) {
								for(int i = temp_lblPerCarton; i>0; i--) {
								    Label productLabel = new SachetVidaShipperLabel(selectedCompanyName ,sachetVGProduct_combox_productName.getSelectedItem().toString(), sachetVGProduct_itemCode.getText(), sachetVGProduct_workOrder.getText(), sachetVGProduct_batch.getText().toUpperCase(),
								    		productQuantity, String.valueOf(temp_startFrom), sachetVGProduct_lbl_useBy.getText(), dateResult, sachetVGProduct_shift.getText(), sachetVGProduct_temperature.getSelectedItem().toString(), sachetVGProduct_btmMessage.getText());
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
		sachetVGProduct_btn_print.setBounds(260, 430, 95, 20);
		contentPane.add(sachetVGProduct_btn_print);
		
		JButton sachetVGProduct_btn_settings = new JButton("Settings");
		sachetVGProduct_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		sachetVGProduct_btn_settings.setBounds(145, 430, 95, 20);
		contentPane.add(sachetVGProduct_btn_settings);
		
		JLabel label = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(214, 470, 280, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Designed and Implemented by Lee.L");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(314, 455, 170, 20);
		contentPane.add(label_1);
		
		JButton sachetVGProduct_btn_back = new JButton("Back");
		sachetVGProduct_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		sachetVGProduct_btn_back.setBounds(375, 430, 95, 20);
		contentPane.add(sachetVGProduct_btn_back);
		

	}
	
	public boolean dataValidation(String itemCode, String productName, String workOrder, String batch, String quantity, String cartonFrom, String labelPerCarton, String cartonQuantity, String useBy, String dateResult, Settings settings) {
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
		
		if(workOrder.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Work order missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(workOrder.length()!=5){
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Work order format error.",
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
//		}else if(batch.length()!=9) {
//			JOptionPane.showMessageDialog(settings.getComponentPage(),
//				    "Batch format error.",
//				    "Inane error",
//				    JOptionPane.ERROR_MESSAGE);
//			return false;
//		}else {
//			char[] temp = batch.toCharArray();
//			String title = String.valueOf(temp[0])+String.valueOf(temp[1])+String.valueOf(temp[2])+String.valueOf(temp[3])+String.valueOf(temp[4]);
//			title = title.toUpperCase();
//			String number = String.valueOf(temp[5])+String.valueOf(temp[6])+String.valueOf(temp[7])+String.valueOf(temp[8]);
//			if(!title.equals("VGGMP")) {
//				JOptionPane.showMessageDialog(settings.getComponentPage(),
//					    "Batch format error.",
//					    "Inane error",
//					    JOptionPane.ERROR_MESSAGE);
//				return false;
//			}
//			try {
//				@SuppressWarnings("unused")
//				int test = Integer.valueOf(number);
//			}catch(Exception e) {
//				JOptionPane.showMessageDialog(settings.getComponentPage(),
//					    "Batch format error.",
//					    "Inane error",
//					    JOptionPane.ERROR_MESSAGE);
//				return false;
//			}
//		}
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
		}else {
			try {
				@SuppressWarnings("unused")
				int testCartonFrom = Integer.valueOf(cartonFrom);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Carton Start from value needs to be number.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if(labelPerCarton.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Label Per Carton value missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			try {
				@SuppressWarnings("unused")
				int testlabelPerCarton = Integer.valueOf(labelPerCarton);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Label Per Carton needs to be number.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}				
		if(cartonQuantity.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Carton Quantity missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			try {
				@SuppressWarnings("unused")
				int testlcartonQuantity = Integer.valueOf(cartonQuantity);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Carton Quantity needs to be number.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}			
		return true;
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
                product.setExtraInfo(items[4]);
                products.add(product);
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
	}
}
