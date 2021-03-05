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
import com.gmp.labeling.printModels.SachetFProductLabel;

public class SachetProductLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1086862663218588092L;
	private JPanel contentPane;
	private JTextField sachetProduct_itemCode;
	private JTextField sachetProduct_batch;
	private JTextField sachetProduct_useBy;
	private String selectedCompanyName;
	private List<Product> products;
	private String productQuantity;
	private JTextField sachetProduct_cartonNoStartFrom;
	private JTextField sachetProduct_labelPerCarton;
	private JTextField sachetProduct_cartonQuantity;
	private String format;
	private JTextField sachetProduct_shift;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SachetProductLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel sachetProduct_lbl_title = new JLabel("Sachet Product Label");
		sachetProduct_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		sachetProduct_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		sachetProduct_lbl_title.setBounds(130, 20, 260, 30);
		contentPane.add(sachetProduct_lbl_title);
		
		JLabel sachetProduct_lbl_itemCode = new JLabel("Item Code:");
		sachetProduct_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sachetProduct_lbl_itemCode.setForeground(new Color(0, 0, 0));
		sachetProduct_lbl_itemCode.setBounds(60, 60, 70, 20);
		contentPane.add(sachetProduct_lbl_itemCode);
		
		JLabel sachetProduct_cartonUnitQuantity = new JLabel("");
		sachetProduct_cartonUnitQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		sachetProduct_cartonUnitQuantity.setForeground(new Color(255, 0, 0));
		sachetProduct_cartonUnitQuantity.setBounds(155, 260, 50, 20);
		
		String[] productNameArray = new String[products.size()];
		int i = 0;
		for(Product p : products) {
			if(p.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = p.getProductName();
				i++;
			}			
		}
		
		sachetProduct_itemCode = new JTextField();
		sachetProduct_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(sachetProduct_itemCode);
		sachetProduct_itemCode.setColumns(10);
		
		JComboBox sachetProduct_combox_productName = new JComboBox();
		sachetProduct_combox_productName.setModel(new DefaultComboBoxModel(productNameArray));
		sachetProduct_combox_productName.setMaximumRowCount(10);
		sachetProduct_combox_productName.setBounds(160, 100, 200, 20);
		sachetProduct_combox_productName.setSelectedItem(null);
		sachetProduct_combox_productName.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String value = (String) sachetProduct_combox_productName.getSelectedItem();
		        for(Product product : products) {
		        	if(product.getProductName().equals(value)) {
		        		selectedCompanyName = product.getCompanyName();
		        		sachetProduct_itemCode.setText(product.getItemCode());
		        		productQuantity = product.getItemQuantity();		        		
		        		sachetProduct_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }
		    }});
		contentPane.add(sachetProduct_combox_productName);
		
		JLabel sachetProduct_lbl_productName = new JLabel("Product:");
		sachetProduct_lbl_productName.setBounds(60, 100, 70, 20);
		contentPane.add(sachetProduct_lbl_productName);
				
		JButton sachetProduct_btn_check = new JButton("Check");
		sachetProduct_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = sachetProduct_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Product product : products) {
		        	if(product.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		sachetProduct_combox_productName.setSelectedItem(product.getProductName());
		        		selectedCompanyName = product.getCompanyName();
		        		productQuantity = product.getItemQuantity();
		        		sachetProduct_itemCode.setText(product.getItemCode());
		        		sachetProduct_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }				
			}
		});
		sachetProduct_btn_check.setBounds(340, 60, 80, 20);
		contentPane.add(sachetProduct_btn_check);
		
		JLabel sachetProduct_lbl_batch = new JLabel("Batch:");
		sachetProduct_lbl_batch.setBounds(60, 140, 70, 20);
		contentPane.add(sachetProduct_lbl_batch);
		
		sachetProduct_batch = new JTextField();
		sachetProduct_batch.setBounds(160, 140, 180, 20);
		contentPane.add(sachetProduct_batch);
		sachetProduct_batch.setColumns(10);
		
		JLabel sachetProduct_lbl_useBy = new JLabel("Use By:");
		sachetProduct_lbl_useBy.setBounds(60, 220, 140, 20);
		contentPane.add(sachetProduct_lbl_useBy);
		
		JComboBox sachetProduct_combox_useByFormat = new JComboBox();
		sachetProduct_combox_useByFormat.setBounds(160, 180, 200, 20);
		sachetProduct_combox_useByFormat.setModel(new DefaultComboBoxModel(new String[] {"Use By", "Date of Manufacture", "Exp Date", "Exp", "Expiry date", "DOM", "Best Before", "Use Before"}));
		sachetProduct_combox_useByFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sachetProduct_lbl_useBy.setText(sachetProduct_combox_useByFormat.getSelectedItem().toString() + ":");
			}
		});

		contentPane.add(sachetProduct_combox_useByFormat);
		
//		JRadioButton sachetProduct_rdbtn_useby = new JRadioButton("Use By");
//		sachetProduct_rdbtn_useby.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetProduct_lbl_useBy.setText("Use By:");
//			}
//		});
//		sachetProduct_rdbtn_useby.setBounds(160, 180, 80, 20);
//		sachetProduct_rdbtn_useby.setSelected(true);
//		contentPane.add(sachetProduct_rdbtn_useby);
//		
//		JRadioButton sachetProduct_rdbtn_expDate = new JRadioButton("Exp Date");
//		sachetProduct_rdbtn_expDate.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetProduct_lbl_useBy.setText("Exp Date:");
//			}
//		});
//		sachetProduct_rdbtn_expDate.setBounds(240, 180, 90, 20);
//		contentPane.add(sachetProduct_rdbtn_expDate);
//		
//		JRadioButton sachetProduct_rdbtn_bestBefore = new JRadioButton("Best Before");
//		sachetProduct_rdbtn_bestBefore.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetProduct_lbl_useBy.setText("Best Before:");
//			}
//		});
//		sachetProduct_rdbtn_bestBefore.setBounds(330, 180, 100, 20);
//		contentPane.add(sachetProduct_rdbtn_bestBefore);
		
		JLabel sachetProduct_lbl_useByformat = new JLabel("Use By format:");
		sachetProduct_lbl_useByformat.setBounds(60, 180, 100, 20);
		contentPane.add(sachetProduct_lbl_useByformat);
		
//		ButtonGroup useByformatGroup = new ButtonGroup();
//		useByformatGroup.add(sachetProduct_rdbtn_useby);
//		useByformatGroup.add(sachetProduct_rdbtn_expDate);
//		useByformatGroup.add(sachetProduct_rdbtn_bestBefore);
		
		
		
		sachetProduct_useBy = new JTextField();
		sachetProduct_useBy.setBounds(200, 220, 140, 20);
		contentPane.add(sachetProduct_useBy);
		sachetProduct_useBy.setColumns(10);
		
		JRadioButton sachetProduct_rdbtn_AU = new JRadioButton("AU");
		format = "D/M/Y";
		sachetProduct_rdbtn_AU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "D/M/Y";
			}
		});
		sachetProduct_rdbtn_AU.setSelected(true);
		sachetProduct_rdbtn_AU.setBounds(378, 220, 50, 20);
		contentPane.add(sachetProduct_rdbtn_AU);
		
		JRadioButton sachetProduct_rdbtn_CN = new JRadioButton("CN");
		sachetProduct_rdbtn_CN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "Y/M/D";
			}
		});
		sachetProduct_rdbtn_CN.setBounds(428, 220, 50, 20);
		contentPane.add(sachetProduct_rdbtn_CN);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(sachetProduct_rdbtn_AU);
		dateformatGroup.add(sachetProduct_rdbtn_CN);
		
		Button sachetProduct_btn_useByInfo = new Button("i");
		sachetProduct_btn_useByInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018"
					    );
			}
		});
		sachetProduct_btn_useByInfo.setBounds(340, 220, 20, 20);
		contentPane.add(sachetProduct_btn_useByInfo);
		
		JLabel sachetProduct_lbl_temperature = new JLabel("Temperature:");
		sachetProduct_lbl_temperature.setBounds(275, 340, 110, 20);
		contentPane.add(sachetProduct_lbl_temperature);
		
		JComboBox sachetProduct_temperature = new JComboBox();
		sachetProduct_temperature.setBounds(384, 340, 60, 20);
		sachetProduct_temperature.setModel(new DefaultComboBoxModel(new String[] {"30","25"}));
		contentPane.add(sachetProduct_temperature);
		
		JLabel sachetProduct_lbl_temperatureIcon = new JLabel("\u00B0C");
		sachetProduct_lbl_temperatureIcon.setBounds(445, 340, 30, 20);
		contentPane.add(sachetProduct_lbl_temperatureIcon);
		
		JButton sachetProduct_btn_updateDB = new JButton("Update DB");
		sachetProduct_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				FPDBEditingIO fpdb  = new FPDBEditingIO(settings, printqueue);
				fpdb.setLocationRelativeTo(null);
				fpdb.setVisible(true);
				dispose();
			}
		});
		sachetProduct_btn_updateDB.setBounds(30, 380, 95, 20);
		contentPane.add(sachetProduct_btn_updateDB);
		
		JButton sachetProduct_btn_print = new JButton("Print");
		sachetProduct_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dateResult = DateFormatModifier.changeDate(sachetProduct_useBy.getText(), format);
				if(dataValidation(sachetProduct_itemCode.getText(), sachetProduct_combox_productName.getSelectedItem().toString(), sachetProduct_batch.getText(), productQuantity, sachetProduct_cartonNoStartFrom.getText(), sachetProduct_labelPerCarton.getText(), sachetProduct_cartonQuantity.getText(), sachetProduct_useBy.getText(), dateResult, settings)) {
					    int temp_startFrom = Integer.valueOf(sachetProduct_cartonNoStartFrom.getText());
						int temp_lblPerCarton = Integer.valueOf(sachetProduct_labelPerCarton.getText());
						int temp_cartonQuantity = Integer.valueOf(sachetProduct_cartonQuantity.getText());							
						while(temp_cartonQuantity>0) {
								for(int i = temp_lblPerCarton; i>0; i--) {
								    Label productLabel = new SachetFProductLabel(selectedCompanyName ,sachetProduct_combox_productName.getSelectedItem().toString(), sachetProduct_itemCode.getText(), sachetProduct_batch.getText(),
								    		productQuantity, String.valueOf(temp_startFrom), sachetProduct_lbl_useBy.getText(), dateResult, sachetProduct_shift.getText(), sachetProduct_temperature.getSelectedItem().toString(), "");
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
		sachetProduct_btn_print.setBounds(260, 380, 95, 20);
		contentPane.add(sachetProduct_btn_print);
		
		JButton sachetProduct_btn_settings = new JButton("Settings");
		sachetProduct_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		sachetProduct_btn_settings.setBounds(145, 380, 95, 20);
		contentPane.add(sachetProduct_btn_settings);
		
		JLabel label = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(214, 420, 280, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Designed and Implemented by Lee.L");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(314, 405, 170, 20);
		contentPane.add(label_1);
		
		JButton sachetProduct_btn_back = new JButton("Back");
		sachetProduct_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		sachetProduct_btn_back.setBounds(375, 380, 95, 20);
		contentPane.add(sachetProduct_btn_back);
		
		JLabel sachetProduct_lbl_cartonNoStartFrom = new JLabel("Carton No Start from:");
		sachetProduct_lbl_cartonNoStartFrom.setBounds(60, 300, 130, 20);
		contentPane.add(sachetProduct_lbl_cartonNoStartFrom);
		
		sachetProduct_cartonNoStartFrom = new JTextField();
		sachetProduct_cartonNoStartFrom.setBounds(194, 300, 60, 20);
		contentPane.add(sachetProduct_cartonNoStartFrom);
		sachetProduct_cartonNoStartFrom.setColumns(10);
		
		JLabel sachetProduct_lbl_LabelPerCarton = new JLabel("Labels Per Carton:");
		sachetProduct_lbl_LabelPerCarton.setBounds(275, 300, 110, 20);
		contentPane.add(sachetProduct_lbl_LabelPerCarton);
		
		sachetProduct_labelPerCarton = new JTextField();
		sachetProduct_labelPerCarton.setToolTipText("def 1");
		sachetProduct_labelPerCarton.setBounds(384, 300, 80, 20);
		contentPane.add(sachetProduct_labelPerCarton);
		sachetProduct_labelPerCarton.setColumns(10);
		
		JLabel sachetProduct_lbl_cartonCount = new JLabel("Carton Quantity:");
		sachetProduct_lbl_cartonCount.setBounds(60, 340, 100, 20);
		contentPane.add(sachetProduct_lbl_cartonCount);
		
		sachetProduct_cartonQuantity = new JTextField();
		sachetProduct_cartonQuantity.setBounds(160, 340, 95, 20);
		contentPane.add(sachetProduct_cartonQuantity);
		sachetProduct_cartonQuantity.setColumns(10);
		
		JLabel sachetProduct_displayQuantity = new JLabel("Display Quantity:");
		sachetProduct_displayQuantity.setBounds(60, 260, 100, 20);
		contentPane.add(sachetProduct_displayQuantity);
		
		JLabel sachetProduct_unitDisplay = new JLabel("units/carton");
		sachetProduct_unitDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
		sachetProduct_unitDisplay.setBounds(210, 260, 90, 20);
		contentPane.add(sachetProduct_unitDisplay);
		contentPane.add(sachetProduct_cartonUnitQuantity);
		
		JLabel sachetProduct_lbl_shift = new JLabel("Shift:");
		sachetProduct_lbl_shift.setBounds(338, 260, 46, 20);
		contentPane.add(sachetProduct_lbl_shift);
		
		sachetProduct_shift = new JTextField();
		sachetProduct_shift.setBounds(384, 260, 80, 20);
		contentPane.add(sachetProduct_shift);
		sachetProduct_shift.setColumns(10);
				
	}
	
	public boolean dataValidation(String itemCode, String productName, String batch, String quantity, String cartonFrom, String labelPerCarton, String cartonQuantity, String useBy, String dateResult, Settings settings) {
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
					    "Label Per Carton value needs to be number.",
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
				int testcartonQuantity = Integer.valueOf(cartonQuantity);
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
