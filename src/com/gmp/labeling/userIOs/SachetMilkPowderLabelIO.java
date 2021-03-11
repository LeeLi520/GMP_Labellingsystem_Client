package com.gmp.labeling.userIOs;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
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
import com.gmp.labeling.printModels.DairyProductLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.SachetMilkPowderLabel;

public class SachetMilkPowderLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4448243768515180275L;
	private JPanel contentPane;
	private JTextField sachetMilkPowder_itemCode;
	private JTextField sachetMilkPowder_batch;
	private JTextField sachetMilkPowder_useBy;
	private List<Product> products;
	private String productQuantity;
	private JTextField sachetMilkPowder_cartonNoStartFrom;
	private JTextField sachetMilkPowder_labelPerCarton;
	private JTextField sachetMilkPowder_cartonQuantity;
	private JTextField sachetMilkPowder_regNo;
	private JTextField sachetMilkPowder_bottomInfo;
	private String format;
	private JTextField sachetMilkPowder_shift;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SachetMilkPowderLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		products = inputItemdata(connection.loadProperties().getProperty("sachetMDB"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel sachetMilkPowder_lbl_title = new JLabel("Sachet Milk Powder Label");
		sachetMilkPowder_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		sachetMilkPowder_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		sachetMilkPowder_lbl_title.setBounds(80, 20, 340, 30);
		contentPane.add(sachetMilkPowder_lbl_title);
		
		JLabel sachetMilkPowder_lbl_itemCode = new JLabel("Item Code:");
		sachetMilkPowder_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sachetMilkPowder_lbl_itemCode.setForeground(new Color(0, 0, 0));
		sachetMilkPowder_lbl_itemCode.setBounds(60, 60, 70, 20);
		contentPane.add(sachetMilkPowder_lbl_itemCode);
		
		JLabel sachetMilkPowder_cartonUnitQuantity = new JLabel("");
		sachetMilkPowder_cartonUnitQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		sachetMilkPowder_cartonUnitQuantity.setForeground(new Color(255, 0, 0));
		sachetMilkPowder_cartonUnitQuantity.setBounds(155, 260, 80, 20);
		
		sachetMilkPowder_bottomInfo = new JTextField();
		sachetMilkPowder_bottomInfo.setBounds(160, 420, 304, 20);
		contentPane.add(sachetMilkPowder_bottomInfo);
		sachetMilkPowder_bottomInfo.setColumns(10);
		
		String[] productNameArray = new String[products.size()];
		int i = 0;
		for(Product p : products) {
			if(p.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = p.getProductName();
				i++;
			}			
		}
		
		sachetMilkPowder_itemCode = new JTextField();
		sachetMilkPowder_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(sachetMilkPowder_itemCode);
		sachetMilkPowder_itemCode.setColumns(10);
		
		sachetMilkPowder_regNo = new JTextField();
		sachetMilkPowder_regNo.setBounds(369, 340, 95, 20);
		contentPane.add(sachetMilkPowder_regNo);
		sachetMilkPowder_regNo.setColumns(10);
		
		JComboBox sachetMilkPowder_combox_productName = new JComboBox();
		sachetMilkPowder_combox_productName.setModel(new DefaultComboBoxModel(productNameArray));
		sachetMilkPowder_combox_productName.setMaximumRowCount(10);
		sachetMilkPowder_combox_productName.setBounds(160, 100, 200, 20);
		sachetMilkPowder_combox_productName.setSelectedItem(null);
		sachetMilkPowder_combox_productName.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String value = (String) sachetMilkPowder_combox_productName.getSelectedItem();
		        for(Product product : products) {
		        	if(product.getProductName().equals(value)) {
		        		sachetMilkPowder_itemCode.setText(product.getItemCode());
		        		productQuantity = product.getItemQuantity();		        		
		        		sachetMilkPowder_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }
		    }});
		contentPane.add(sachetMilkPowder_combox_productName);
		
		JLabel sachetMilkPowder_lbl_productName = new JLabel("Product:");
		sachetMilkPowder_lbl_productName.setBounds(60, 100, 70, 20);
		contentPane.add(sachetMilkPowder_lbl_productName);
				
		JButton sachetMilkPowder_btn_check = new JButton("Check");
		sachetMilkPowder_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = sachetMilkPowder_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Product product : products) {
		        	if(product.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		sachetMilkPowder_combox_productName.setSelectedItem(product.getProductName());
		        		productQuantity = product.getItemQuantity();
		        		sachetMilkPowder_itemCode.setText(product.getItemCode());
		        		sachetMilkPowder_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }				
			}
		});
		sachetMilkPowder_btn_check.setBounds(340, 60, 80, 20);
		contentPane.add(sachetMilkPowder_btn_check);
		
		JLabel sachetMilkPowder_lbl_batch = new JLabel("Batch:");
		sachetMilkPowder_lbl_batch.setBounds(60, 140, 70, 20);
		contentPane.add(sachetMilkPowder_lbl_batch);
		
		sachetMilkPowder_batch = new JTextField();
		sachetMilkPowder_batch.setBounds(160, 140, 180, 20);
		contentPane.add(sachetMilkPowder_batch);
		sachetMilkPowder_batch.setColumns(10);
		
		JLabel sachetMilkPowder_lbl_useBy = new JLabel("Use By:");
		sachetMilkPowder_lbl_useBy.setBounds(60, 220, 120, 20);
		contentPane.add(sachetMilkPowder_lbl_useBy);
		
		JComboBox sachetMilkPowder_userByformat = new JComboBox();
		sachetMilkPowder_userByformat.setBounds(160, 180, 200, 20);
		sachetMilkPowder_userByformat.setModel(new DefaultComboBoxModel(new String[] {"Use By", "Date of Manufacture", "Exp Date", "Exp", "Expiry date", "DOM", "Best Before", "Use Before"}));
		sachetMilkPowder_userByformat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sachetMilkPowder_lbl_useBy.setText(sachetMilkPowder_userByformat.getSelectedItem().toString() + ":");
			}
		});

		contentPane.add(sachetMilkPowder_userByformat);	
		
//		JRadioButton sachetMilkPowder_rdbtn_useby = new JRadioButton("Use By");
//		sachetMilkPowder_rdbtn_useby.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetMilkPowder_lbl_useBy.setText("Use By:");
//			}
//		});
//		sachetMilkPowder_rdbtn_useby.setBounds(160, 180, 80, 20);
//		sachetMilkPowder_rdbtn_useby.setSelected(true);
//		contentPane.add(sachetMilkPowder_rdbtn_useby);
//		
//		JRadioButton sachetMilkPowder_rdbtn_expDate = new JRadioButton("Exp Date");
//		sachetMilkPowder_rdbtn_expDate.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetMilkPowder_lbl_useBy.setText("Exp Date:");
//			}
//		});
//		sachetMilkPowder_rdbtn_expDate.setBounds(240, 180, 90, 20);
//		contentPane.add(sachetMilkPowder_rdbtn_expDate);
//		
//		JRadioButton sachetMilkPowder_rdbtn_bestBefore = new JRadioButton("Best Before");
//		sachetMilkPowder_rdbtn_bestBefore.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetMilkPowder_lbl_useBy.setText("Best Before:");
//			}
//		});
//		sachetMilkPowder_rdbtn_bestBefore.setBounds(330, 180, 100, 20);
//		contentPane.add(sachetMilkPowder_rdbtn_bestBefore);
		
		JLabel sachetMilkPowder_lbl_useByformat = new JLabel("Use By format:");
		sachetMilkPowder_lbl_useByformat.setBounds(60, 180, 100, 20);
		contentPane.add(sachetMilkPowder_lbl_useByformat);
		
//		ButtonGroup useByformatGroup = new ButtonGroup();
//		useByformatGroup.add(sachetMilkPowder_rdbtn_useby);
//		useByformatGroup.add(sachetMilkPowder_rdbtn_expDate);
//		useByformatGroup.add(sachetMilkPowder_rdbtn_bestBefore);
		
		
		
		sachetMilkPowder_useBy = new JTextField();
		sachetMilkPowder_useBy.setBounds(180, 220, 160, 20);
		contentPane.add(sachetMilkPowder_useBy);
		sachetMilkPowder_useBy.setColumns(10);
		
		JRadioButton sachetMilkPowder_rdbtn_AU = new JRadioButton("AU");
		format = "AU";
		sachetMilkPowder_rdbtn_AU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "AU";
//				format = "D/M/Y";
			}
		});
		sachetMilkPowder_rdbtn_AU.setSelected(true);
		sachetMilkPowder_rdbtn_AU.setBounds(378, 220, 50, 20);
		contentPane.add(sachetMilkPowder_rdbtn_AU);
		
		JRadioButton sachetMilkPowder_rdbtn_CN = new JRadioButton("CN");
		sachetMilkPowder_rdbtn_CN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "CN";
//				format = "Y/M/D";
			}
		});
		sachetMilkPowder_rdbtn_CN.setBounds(428, 220, 50, 20);
		contentPane.add(sachetMilkPowder_rdbtn_CN);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(sachetMilkPowder_rdbtn_AU);
		dateformatGroup.add(sachetMilkPowder_rdbtn_CN);
		
		Button sachetMilkPowder_btn_useByInfo = new Button("i");
		sachetMilkPowder_btn_useByInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct formats are dd/mm/yyyy or mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n eg. May 2019 => 05/2019"
					    );
			}
		});
		sachetMilkPowder_btn_useByInfo.setBounds(340, 220, 20, 20);
		contentPane.add(sachetMilkPowder_btn_useByInfo);
		
		JLabel sachetMilkPowder_lbl_temperature = new JLabel("Temperature:");
		sachetMilkPowder_lbl_temperature.setBounds(60, 380, 100, 20);
		contentPane.add(sachetMilkPowder_lbl_temperature);
		
		JComboBox sachetMilkPowder_temperature = new JComboBox();
		sachetMilkPowder_temperature.setBounds(160, 380, 60, 20);
		sachetMilkPowder_temperature.setModel(new DefaultComboBoxModel(new String[] {"30","25"}));
		contentPane.add(sachetMilkPowder_temperature);
		
		JLabel sachetMilkPowder_lbl_temperatureIcon = new JLabel("\u00B0C");
		sachetMilkPowder_lbl_temperatureIcon.setBounds(220, 380, 30, 20);
		contentPane.add(sachetMilkPowder_lbl_temperatureIcon);
		
		JButton sachetMilkPowder_btn_updateDB = new JButton("Update DB");
		sachetMilkPowder_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				SachetMilkDBIO dbediting = new SachetMilkDBIO(settings, printqueue);
				dbediting.setLocationRelativeTo(null);
				dbediting.setVisible(true);
				dispose();
			}
		});
		sachetMilkPowder_btn_updateDB.setBounds(30, 460, 95, 20);
		contentPane.add(sachetMilkPowder_btn_updateDB);
		
		JButton sachetMilkPowder_btn_print = new JButton("Print");
		sachetMilkPowder_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dateResult = null;
				if(dataValidation(sachetMilkPowder_itemCode.getText(), sachetMilkPowder_combox_productName.getSelectedItem().toString(), sachetMilkPowder_batch.getText(), productQuantity, sachetMilkPowder_cartonNoStartFrom.getText(), sachetMilkPowder_labelPerCarton.getText(), sachetMilkPowder_cartonQuantity.getText(),
						sachetMilkPowder_useBy.getText(), sachetMilkPowder_regNo.getText(), settings) ) {
					if(sachetMilkPowder_useBy.getText().length()==10) {
						if(format.equals("AU")) {
							dateResult = DateFormatModifier.changeDate(sachetMilkPowder_useBy.getText(), "D/M/Y");
						}else {
							dateResult = DateFormatModifier.changeDate(sachetMilkPowder_useBy.getText(), "Y/M/D");
						}
					}else {
						if(format.equals("AU")) {
							dateResult = DateFormatModifier.changeDate(sachetMilkPowder_useBy.getText(), "M/Y");
						}else {
							dateResult = DateFormatModifier.changeDate(sachetMilkPowder_useBy.getText(), "Y/M");
						}						
					}
					int temp_startFrom = Integer.valueOf(sachetMilkPowder_cartonNoStartFrom.getText());
					int temp_lblPerCarton = Integer.valueOf(sachetMilkPowder_labelPerCarton.getText());
					int temp_cartonQuantity = Integer.valueOf(sachetMilkPowder_cartonQuantity.getText());							
					while(temp_cartonQuantity>0) {
							for(int i = temp_lblPerCarton; i>0; i--) {
							    Label sachetmilkpowder = new SachetMilkPowderLabel(sachetMilkPowder_combox_productName.getSelectedItem().toString(), sachetMilkPowder_itemCode.getText(), sachetMilkPowder_batch.getText(), productQuantity,
							    		String.valueOf(temp_startFrom), sachetMilkPowder_lbl_useBy.getText(), dateResult, sachetMilkPowder_shift.getText(), sachetMilkPowder_regNo.getText(), sachetMilkPowder_temperature.getSelectedItem().toString(),
							    		sachetMilkPowder_bottomInfo.getText());
							    printqueue.addLabelToQueue(sachetmilkpowder);
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
		sachetMilkPowder_btn_print.setBounds(260, 460, 95, 20);
		contentPane.add(sachetMilkPowder_btn_print);
		
		JButton sachetMilkPowder_btn_settings = new JButton("Settings");
		sachetMilkPowder_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		sachetMilkPowder_btn_settings.setBounds(145, 460, 95, 20);
		contentPane.add(sachetMilkPowder_btn_settings);
		
		JLabel sachetMilkPowder_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		sachetMilkPowder_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sachetMilkPowder_lbl_copyright_2.setBounds(214, 500, 280, 20);
		contentPane.add(sachetMilkPowder_lbl_copyright_2);
		
		JLabel sachetMilkPowder_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		sachetMilkPowder_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sachetMilkPowder_lbl_copyright_1.setBounds(314, 485, 170, 20);
		contentPane.add(sachetMilkPowder_lbl_copyright_1);
		
		JButton sachetMilkPowder_btn_back = new JButton("Back");
		sachetMilkPowder_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		sachetMilkPowder_btn_back.setBounds(375, 460, 95, 20);
		contentPane.add(sachetMilkPowder_btn_back);
		
		JLabel sachetMilkPowder_lbl_cartonNoStartFrom = new JLabel("Carton No Start from:");
		sachetMilkPowder_lbl_cartonNoStartFrom.setBounds(60, 300, 130, 20);
		contentPane.add(sachetMilkPowder_lbl_cartonNoStartFrom);
		
		sachetMilkPowder_cartonNoStartFrom = new JTextField();
		sachetMilkPowder_cartonNoStartFrom.setBounds(194, 300, 60, 20);
		contentPane.add(sachetMilkPowder_cartonNoStartFrom);
		sachetMilkPowder_cartonNoStartFrom.setColumns(10);
		
		JLabel sachetMilkPowder_lbl_LabelPerCarton = new JLabel("Labels Per Carton:");
		sachetMilkPowder_lbl_LabelPerCarton.setBounds(275, 300, 110, 20);
		contentPane.add(sachetMilkPowder_lbl_LabelPerCarton);
		
		sachetMilkPowder_labelPerCarton = new JTextField();
		sachetMilkPowder_labelPerCarton.setToolTipText("def 1");
		sachetMilkPowder_labelPerCarton.setBounds(384, 300, 80, 20);
		contentPane.add(sachetMilkPowder_labelPerCarton);
		sachetMilkPowder_labelPerCarton.setColumns(10);
		
		JLabel sachetMilkPowder_lbl_cartonCount = new JLabel("Carton Quantity:");
		sachetMilkPowder_lbl_cartonCount.setBounds(60, 340, 100, 20);
		contentPane.add(sachetMilkPowder_lbl_cartonCount);
		
		sachetMilkPowder_cartonQuantity = new JTextField();
		sachetMilkPowder_cartonQuantity.setBounds(160, 340, 95, 20);
		contentPane.add(sachetMilkPowder_cartonQuantity);
		sachetMilkPowder_cartonQuantity.setColumns(10);
		
		JLabel sachetMilkPowder_displayQuantity = new JLabel("Display Quantity:");
		sachetMilkPowder_displayQuantity.setBounds(60, 260, 100, 20);
		contentPane.add(sachetMilkPowder_displayQuantity);
		
		JLabel sachetMilkPowder_unitDisplay = new JLabel("units/carton");
		sachetMilkPowder_unitDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
		sachetMilkPowder_unitDisplay.setBounds(240, 260, 90, 20);
		contentPane.add(sachetMilkPowder_unitDisplay);
		contentPane.add(sachetMilkPowder_cartonUnitQuantity);
		
		JLabel sachetMilkPowder_lbl_shift = new JLabel("Shift:");
		sachetMilkPowder_lbl_shift.setBounds(338, 260, 46, 20);
		contentPane.add(sachetMilkPowder_lbl_shift);	
		
		sachetMilkPowder_shift = new JTextField();
		sachetMilkPowder_shift.setBounds(384, 260, 80, 20);
		contentPane.add(sachetMilkPowder_shift);
		sachetMilkPowder_shift.setColumns(10);
			
		JLabel sachetMilkPowder_lbl_RegNo = new JLabel("Reg no:");
		sachetMilkPowder_lbl_RegNo.setBounds(275, 340, 110, 20);
		contentPane.add(sachetMilkPowder_lbl_RegNo);
		
		JLabel sachetMilkPowder_lbl_bottomMessage = new JLabel("Bottom Message:");
		sachetMilkPowder_lbl_bottomMessage.setBounds(60, 420, 100, 20);
		contentPane.add(sachetMilkPowder_lbl_bottomMessage);
		
		Button sachetMilkPowder_btn_bottomMsgInfo = new Button("i");
		sachetMilkPowder_btn_bottomMsgInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "This is the bottom description shown under the label. Leaving it blank will bring the default one on the label."
					    );
			}
		});
		sachetMilkPowder_btn_bottomMsgInfo.setBounds(464, 420, 20, 20);
		contentPane.add(sachetMilkPowder_btn_bottomMsgInfo);
		
				
	}
	
	public boolean dataValidation(String itemCode, String productName, String batch, String quantity, String cartonFrom, String labelPerCarton, String cartonQuantity, String useBy, String regNo, Settings settings) {
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
			}else if(temp.length == 7){
				int monthint = 0;
				int yearint = 0;
				String month = String.valueOf(temp[0]) + String.valueOf(temp[1]);
				String year = String.valueOf(temp[3])+ String.valueOf(temp[4])+ String.valueOf(temp[5])+ String.valueOf(temp[6]);
				if(temp[2]!='/') {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
							"Date input format error.",
							"Inane error",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
				try {
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
				}				
			}
			else{
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
		if(regNo.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Reg No missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else{
			try {
				@SuppressWarnings("unused")
				int regNumber = Integer.parseInt(regNo);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid Reg No",
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
            	if(line.contains("Item Code")) {
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
}