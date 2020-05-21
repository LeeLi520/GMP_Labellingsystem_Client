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
import com.gmp.labeling.printModels.DairyProductLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;

public class DairyProductLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7432097168176703447L;
	private JPanel contentPane;
	private JTextField dairyProduct_itemCode;
	private JTextField dairyProduct_batch;
	private JTextField dairyProduct_useBy;
	private String selectedCompanyName;
	private String selectedItemExtraInfo;
	private List<Product> products;
	private String productQuantity;
	private JTextField dairyProduct_cartonNoStartFrom;
	private JTextField dairyProduct_labelPerCarton;
	private JTextField dairyProduct_cartonQuantity;
	private JTextField dairyProduct_regNo;
	private JTextField dairyProduct_bottomInfo;
	private String format;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DairyProductLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel dairyProduct_lbl_title = new JLabel("Dairy Product Label");
		dairyProduct_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		dairyProduct_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		dairyProduct_lbl_title.setBounds(135, 20, 240, 30);
		contentPane.add(dairyProduct_lbl_title);
		
		JLabel dairyProduct_lbl_itemCode = new JLabel("Item Code:");
		dairyProduct_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		dairyProduct_lbl_itemCode.setForeground(new Color(0, 0, 0));
		dairyProduct_lbl_itemCode.setBounds(60, 60, 70, 20);
		contentPane.add(dairyProduct_lbl_itemCode);
		
		JLabel dairyProduct_cartonUnitQuantity = new JLabel("");
		dairyProduct_cartonUnitQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		dairyProduct_cartonUnitQuantity.setForeground(new Color(255, 0, 0));
		dairyProduct_cartonUnitQuantity.setBounds(155, 260, 50, 20);
		
		dairyProduct_bottomInfo = new JTextField();
		dairyProduct_bottomInfo.setBounds(160, 420, 304, 20);
		contentPane.add(dairyProduct_bottomInfo);
		dairyProduct_bottomInfo.setColumns(10);
		
		JComboBox dairyProduct_combox_shift = new JComboBox();
		dairyProduct_combox_shift.setBounds(384, 260, 80, 20);
		dairyProduct_combox_shift.setModel(new DefaultComboBoxModel(new String[] {"D/S","A/S","N/S"}));
		contentPane.add(dairyProduct_combox_shift);
		
		String[] productNameArray = new String[products.size()];
		int i = 0;
		for(Product p : products) {
			if(p.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = p.getProductName();
				i++;
			}			
		}
		
		dairyProduct_itemCode = new JTextField();
		dairyProduct_itemCode.setBounds(160, 60, 180, 20);
		contentPane.add(dairyProduct_itemCode);
		dairyProduct_itemCode.setColumns(10);
		
		dairyProduct_regNo = new JTextField();
		dairyProduct_regNo.setBounds(369, 340, 95, 20);
		contentPane.add(dairyProduct_regNo);
		dairyProduct_regNo.setColumns(10);
		
		JComboBox dairyProduct_combox_productName = new JComboBox();
		dairyProduct_combox_productName.setModel(new DefaultComboBoxModel(productNameArray));
		dairyProduct_combox_productName.setMaximumRowCount(10);
		dairyProduct_combox_productName.setBounds(160, 100, 200, 20);
		dairyProduct_combox_productName.setSelectedItem(null);
		dairyProduct_combox_productName.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String value = (String) dairyProduct_combox_productName.getSelectedItem();
		        for(Product product : products) {
		        	if(product.getProductName().equals(value)) {
		        		selectedCompanyName = product.getCompanyName();
		        		selectedItemExtraInfo = product.getExtraInfo();
		        		dairyProduct_itemCode.setText(product.getItemCode());
		        		productQuantity = product.getItemQuantity();		        		
		        		dairyProduct_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }
		    }});
		contentPane.add(dairyProduct_combox_productName);
		
		JLabel dairyProduct_lbl_productName = new JLabel("Product:");
		dairyProduct_lbl_productName.setBounds(60, 100, 70, 20);
		contentPane.add(dairyProduct_lbl_productName);
				
		JButton dairyProduct_btn_check = new JButton("Check");
		dairyProduct_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = dairyProduct_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Product product : products) {
		        	if(product.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		dairyProduct_combox_productName.setSelectedItem(product.getProductName());
		        		selectedCompanyName = product.getCompanyName();
		        		selectedItemExtraInfo = product.getExtraInfo();
		        		productQuantity = product.getItemQuantity();
		        		dairyProduct_itemCode.setText(product.getItemCode());
		        		dairyProduct_cartonUnitQuantity.setText(product.getItemQuantity());
		        		break;
		        	}	        	
		        }				
			}
		});
		dairyProduct_btn_check.setBounds(340, 60, 80, 20);
		contentPane.add(dairyProduct_btn_check);
		
		JLabel dairyProduct_lbl_batch = new JLabel("Batch:");
		dairyProduct_lbl_batch.setBounds(60, 140, 70, 20);
		contentPane.add(dairyProduct_lbl_batch);
		
		dairyProduct_batch = new JTextField();
		dairyProduct_batch.setBounds(160, 140, 180, 20);
		contentPane.add(dairyProduct_batch);
		dairyProduct_batch.setColumns(10);
		
		JLabel dairyProduct_lbl_useBy = new JLabel("Use By:");
		dairyProduct_lbl_useBy.setBounds(60, 220, 100, 20);
		contentPane.add(dairyProduct_lbl_useBy);
		
		JRadioButton dairyProduct_rdbtn_useby = new JRadioButton("Use By");
		dairyProduct_rdbtn_useby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dairyProduct_lbl_useBy.setText("Use By:");
			}
		});
		dairyProduct_rdbtn_useby.setBounds(160, 180, 80, 20);
		dairyProduct_rdbtn_useby.setSelected(true);
		contentPane.add(dairyProduct_rdbtn_useby);
		
		JRadioButton dairyProduct_rdbtn_expDate = new JRadioButton("Exp Date");
		dairyProduct_rdbtn_expDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dairyProduct_lbl_useBy.setText("Exp Date:");
			}
		});
		dairyProduct_rdbtn_expDate.setBounds(240, 180, 90, 20);
		contentPane.add(dairyProduct_rdbtn_expDate);
		
		JRadioButton dairyProduct_rdbtn_bestBefore = new JRadioButton("Best Before");
		dairyProduct_rdbtn_bestBefore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dairyProduct_lbl_useBy.setText("Best Before:");
			}
		});
		dairyProduct_rdbtn_bestBefore.setBounds(330, 180, 100, 20);
		contentPane.add(dairyProduct_rdbtn_bestBefore);
		
		JLabel dairyProduct_lbl_useByformat = new JLabel("Use By format:");
		dairyProduct_lbl_useByformat.setBounds(60, 180, 100, 20);
		contentPane.add(dairyProduct_lbl_useByformat);
		
		ButtonGroup useByformatGroup = new ButtonGroup();
		useByformatGroup.add(dairyProduct_rdbtn_useby);
		useByformatGroup.add(dairyProduct_rdbtn_expDate);
		useByformatGroup.add(dairyProduct_rdbtn_bestBefore);
		
		
		
		dairyProduct_useBy = new JTextField();
		dairyProduct_useBy.setBounds(160, 220, 180, 20);
		contentPane.add(dairyProduct_useBy);
		dairyProduct_useBy.setColumns(10);
		
		JRadioButton dairyProduct_rdbtn_AU = new JRadioButton("AU");
		format = "D/M/Y";
		dairyProduct_rdbtn_AU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "D/M/Y";
			}
		});
		dairyProduct_rdbtn_AU.setSelected(true);
		dairyProduct_rdbtn_AU.setBounds(378, 220, 50, 20);
		contentPane.add(dairyProduct_rdbtn_AU);
		
		JRadioButton dairyProduct_rdbtn_CN = new JRadioButton("CN");
		dairyProduct_rdbtn_CN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "Y/M/D";
			}
		});
		dairyProduct_rdbtn_CN.setBounds(428, 220, 50, 20);
		contentPane.add(dairyProduct_rdbtn_CN);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(dairyProduct_rdbtn_AU);
		dateformatGroup.add(dairyProduct_rdbtn_CN);
		
		Button dairyProduct_btn_useByInfo = new Button("i");
		dairyProduct_btn_useByInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yyyy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018"
					    );
			}
		});
		dairyProduct_btn_useByInfo.setBounds(340, 220, 20, 20);
		contentPane.add(dairyProduct_btn_useByInfo);
		
		JLabel dairyProduct_lbl_temperature = new JLabel("Temperature:");
		dairyProduct_lbl_temperature.setBounds(60, 380, 100, 20);
		contentPane.add(dairyProduct_lbl_temperature);
		
		JComboBox dairyProduct_temperature = new JComboBox();
		dairyProduct_temperature.setBounds(160, 380, 60, 20);
		dairyProduct_temperature.setModel(new DefaultComboBoxModel(new String[] {"30","25"}));
		contentPane.add(dairyProduct_temperature);
		
		JLabel dairyProduct_lbl_temperatureIcon = new JLabel("\u00B0C");
		dairyProduct_lbl_temperatureIcon.setBounds(220, 380, 30, 20);
		contentPane.add(dairyProduct_lbl_temperatureIcon);
		
		JButton dairyProduct_btn_updateDB = new JButton("Update DB");
		dairyProduct_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				FPDBEditingIO fpdb  = new FPDBEditingIO(settings, printqueue);
				fpdb.setLocationRelativeTo(null);
				fpdb.setVisible(true);
				dispose();
			}
		});
		dairyProduct_btn_updateDB.setBounds(30, 460, 95, 20);
		contentPane.add(dairyProduct_btn_updateDB);
		
		JButton dairyProduct_btn_print = new JButton("Print");
		dairyProduct_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dateResult = DateFormatModifier.changeDate(dairyProduct_useBy.getText(), format);
				if(dataValidation(dairyProduct_itemCode.getText(), dairyProduct_combox_productName.getSelectedItem().toString(), dairyProduct_batch.getText(), productQuantity, dairyProduct_cartonNoStartFrom.getText(), dairyProduct_labelPerCarton.getText(), dairyProduct_cartonQuantity.getText(),
						dairyProduct_useBy.getText(), dairyProduct_regNo.getText(), settings) ) {
					int temp_startFrom = Integer.valueOf(dairyProduct_cartonNoStartFrom.getText());
					int temp_lblPerCarton = Integer.valueOf(dairyProduct_labelPerCarton.getText());
					int temp_cartonQuantity = Integer.valueOf(dairyProduct_cartonQuantity.getText());							
					while(temp_cartonQuantity>0) {
							for(int i = temp_lblPerCarton; i>0; i--) {
							    Label dairyproductLabel = new DairyProductLabel(selectedCompanyName, dairyProduct_combox_productName.getSelectedItem().toString(), dairyProduct_itemCode.getText(), dairyProduct_batch.getText(), productQuantity,
							    		String.valueOf(temp_startFrom), dairyProduct_lbl_useBy.getText(), dateResult, dairyProduct_combox_shift.getSelectedItem().toString(), selectedItemExtraInfo, dairyProduct_regNo.getText(), dairyProduct_temperature.getSelectedItem().toString(),
							    		dairyProduct_bottomInfo.getText());
							    printqueue.addLabelToQueue(dairyproductLabel);
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
		dairyProduct_btn_print.setBounds(260, 460, 95, 20);
		contentPane.add(dairyProduct_btn_print);
		
		JButton dairyProduct_btn_settings = new JButton("Settings");
		dairyProduct_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		dairyProduct_btn_settings.setBounds(145, 460, 95, 20);
		contentPane.add(dairyProduct_btn_settings);
		
		JLabel dairyProduct_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		dairyProduct_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		dairyProduct_lbl_copyright_2.setBounds(214, 500, 280, 20);
		contentPane.add(dairyProduct_lbl_copyright_2);
		
		JLabel dairyProduct_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		dairyProduct_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		dairyProduct_lbl_copyright_1.setBounds(314, 485, 170, 20);
		contentPane.add(dairyProduct_lbl_copyright_1);
		
		JButton dairyProduct_btn_back = new JButton("Back");
		dairyProduct_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		dairyProduct_btn_back.setBounds(375, 460, 95, 20);
		contentPane.add(dairyProduct_btn_back);
		
		JLabel dairyProduct_lbl_cartonNoStartFrom = new JLabel("Carton No Start from:");
		dairyProduct_lbl_cartonNoStartFrom.setBounds(60, 300, 130, 20);
		contentPane.add(dairyProduct_lbl_cartonNoStartFrom);
		
		dairyProduct_cartonNoStartFrom = new JTextField();
		dairyProduct_cartonNoStartFrom.setBounds(194, 300, 60, 20);
		contentPane.add(dairyProduct_cartonNoStartFrom);
		dairyProduct_cartonNoStartFrom.setColumns(10);
		
		JLabel dairyProduct_lbl_LabelPerCarton = new JLabel("Labels Per Carton:");
		dairyProduct_lbl_LabelPerCarton.setBounds(275, 300, 110, 20);
		contentPane.add(dairyProduct_lbl_LabelPerCarton);
		
		dairyProduct_labelPerCarton = new JTextField();
		dairyProduct_labelPerCarton.setToolTipText("def 1");
		dairyProduct_labelPerCarton.setBounds(384, 300, 80, 20);
		contentPane.add(dairyProduct_labelPerCarton);
		dairyProduct_labelPerCarton.setColumns(10);
		
		JLabel dairyProduct_lbl_cartonCount = new JLabel("Carton Quantity:");
		dairyProduct_lbl_cartonCount.setBounds(60, 340, 100, 20);
		contentPane.add(dairyProduct_lbl_cartonCount);
		
		dairyProduct_cartonQuantity = new JTextField();
		dairyProduct_cartonQuantity.setBounds(160, 340, 95, 20);
		contentPane.add(dairyProduct_cartonQuantity);
		dairyProduct_cartonQuantity.setColumns(10);
		
		JLabel dairyProduct_displayQuantity = new JLabel("Display Quantity:");
		dairyProduct_displayQuantity.setBounds(60, 260, 100, 20);
		contentPane.add(dairyProduct_displayQuantity);
		
		JLabel dairyProduct_unitDisplay = new JLabel("units/carton");
		dairyProduct_unitDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
		dairyProduct_unitDisplay.setBounds(210, 260, 90, 20);
		contentPane.add(dairyProduct_unitDisplay);
		contentPane.add(dairyProduct_cartonUnitQuantity);
		
		JLabel dairyProduct_lbl_shift = new JLabel("Shift:");
		dairyProduct_lbl_shift.setBounds(338, 260, 46, 20);
		contentPane.add(dairyProduct_lbl_shift);		
			
		JLabel dairyProduct_lbl_RegNo = new JLabel("Reg no:");
		dairyProduct_lbl_RegNo.setBounds(275, 340, 110, 20);
		contentPane.add(dairyProduct_lbl_RegNo);
		
		JLabel dairyProduct_lbl_bottomMessage = new JLabel("Bottom Message:");
		dairyProduct_lbl_bottomMessage.setBounds(60, 420, 100, 20);
		contentPane.add(dairyProduct_lbl_bottomMessage);
		
		Button dairyProduct_btn_bottomMsgInfo = new Button("i");
		dairyProduct_btn_bottomMsgInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "This is the bottom description shown under the label. Leaving it blank will bring the default one on the label."
					    );
			}
		});
		dairyProduct_btn_bottomMsgInfo.setBounds(464, 420, 20, 20);
		contentPane.add(dairyProduct_btn_bottomMsgInfo);
		
				
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
