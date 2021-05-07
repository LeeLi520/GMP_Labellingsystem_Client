package com.gmp.labeling.userIOs;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.SachetSemiProductLabel;
import javax.swing.JComboBox;

public class SachetSemiProductLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1082710500290300215L;
	private JPanel contentPane;
	private JTextField sachetSemiP_itemCode;
	private JTextField sachetSemiP_batch;
	private JTextField sachetSemiP_useBy;
	private JTextField sachetSemiP_cartonStartFrom;
	private JTextField sachetSemiP_labelsPerCarton;
	private JTextField sachetSemiP_cartonQuantity;
	private JTextField sachetSemiP_productName;
	private JTextField sachetSemiP_quantity;
	private String format;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SachetSemiProductLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel sachetSemiP_lbl_title = new JLabel("Sachet Semi Product Label");
		sachetSemiP_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		sachetSemiP_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		sachetSemiP_lbl_title.setBounds(103, 13, 300, 30);
		contentPane.add(sachetSemiP_lbl_title);
		
		JLabel sachetSemiP_lbl_itemCode = new JLabel("Item Code:");
		sachetSemiP_lbl_itemCode.setForeground(Color.BLACK);
		sachetSemiP_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 13));
		sachetSemiP_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(sachetSemiP_lbl_itemCode);
		
		sachetSemiP_itemCode = new JTextField();
		sachetSemiP_itemCode.setColumns(10);
		sachetSemiP_itemCode.setBounds(150, 60, 260, 20);
		contentPane.add(sachetSemiP_itemCode);
		
		JLabel sachetSemiP_lbl_productName = new JLabel("Product name:");
		sachetSemiP_lbl_productName.setBounds(50, 100, 90, 20);
		contentPane.add(sachetSemiP_lbl_productName);
		
		JLabel sachetSemiP_lbl_batch = new JLabel("Batch:");
		sachetSemiP_lbl_batch.setBounds(50, 140, 90, 20);
		contentPane.add(sachetSemiP_lbl_batch);
		
		sachetSemiP_batch = new JTextField();
		sachetSemiP_batch.setColumns(10);
		sachetSemiP_batch.setBounds(150, 140, 260, 20);
		contentPane.add(sachetSemiP_batch);
		
		JLabel sachetSemiP_lbl_useByFormat = new JLabel("Use By format:");
		sachetSemiP_lbl_useByFormat.setBounds(50, 180, 100, 20);
		contentPane.add(sachetSemiP_lbl_useByFormat);
		
		JLabel sachetSemiP_lbl_useBy = new JLabel("Use By:");
		sachetSemiP_lbl_useBy.setBounds(50, 220, 140, 20);
		contentPane.add(sachetSemiP_lbl_useBy);
		
		JComboBox sachetSemiP_userByformat = new JComboBox();
		sachetSemiP_userByformat.setBounds(150, 180, 260, 20);
		sachetSemiP_userByformat.setModel(new DefaultComboBoxModel(new String[] {"Use By", "Date of Manufacture", "Exp Date", "Exp", "Expiry date", "DOM", "Best Before", "Use Before"}));
		sachetSemiP_userByformat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sachetSemiP_lbl_useBy.setText(sachetSemiP_userByformat.getSelectedItem().toString() + ":");
			}
		});

		contentPane.add(sachetSemiP_userByformat);		
		
//		JRadioButton sachetSemiP_rdbtn_useBy = new JRadioButton("Use By");
//		sachetSemiP_rdbtn_useBy.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetSemiP_lbl_useBy.setText("Use By:");
//			}
//		});
//		sachetSemiP_rdbtn_useBy.setSelected(true);
//		sachetSemiP_rdbtn_useBy.setBounds(150, 180, 80, 20);
//		contentPane.add(sachetSemiP_rdbtn_useBy);
//		
//		JRadioButton sachetSemiP_rdbtn_expDate = new JRadioButton("Exp Date");
//		sachetSemiP_rdbtn_expDate.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetSemiP_lbl_useBy.setText("Exp Date:");
//			}
//		});
//		sachetSemiP_rdbtn_expDate.setBounds(230, 180, 90, 20);
//		contentPane.add(sachetSemiP_rdbtn_expDate);
//		
//		JRadioButton sachetSemiP_rdbtn_bestBefore = new JRadioButton("Best Before");
//		sachetSemiP_rdbtn_bestBefore.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sachetSemiP_lbl_useBy.setText("Best Before:");
//			}
//		});
//		sachetSemiP_rdbtn_bestBefore.setBounds(320, 180, 100, 20);
//		contentPane.add(sachetSemiP_rdbtn_bestBefore);
//		
//		ButtonGroup useByGroup = new ButtonGroup();
//		useByGroup.add(sachetSemiP_rdbtn_useBy);
//		useByGroup.add(sachetSemiP_rdbtn_expDate);
//		useByGroup.add(sachetSemiP_rdbtn_bestBefore);
		
		JComboBox sachetSemiP_temperature = new JComboBox();
		sachetSemiP_temperature.setModel(new DefaultComboBoxModel(new String[] {"30", "25"}));
		sachetSemiP_temperature.setBounds(374, 340, 55, 20);
		contentPane.add(sachetSemiP_temperature);
				
		sachetSemiP_useBy = new JTextField();
		sachetSemiP_useBy.setColumns(10);
		sachetSemiP_useBy.setBounds(190, 220, 140, 20);
		contentPane.add(sachetSemiP_useBy);
		
		
		Button sachetSemiP_btn_useBy_info = new Button("i");
		sachetSemiP_btn_useBy_info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		sachetSemiP_btn_useBy_info.setBounds(330, 220, 20, 20);
		contentPane.add(sachetSemiP_btn_useBy_info);
		
		format = "D/M/Y";
		JRadioButton sachetSemiP_rdbtn_dateFormat_au = new JRadioButton("AU");
		sachetSemiP_rdbtn_dateFormat_au.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "D/M/Y";
			}
		});
		sachetSemiP_rdbtn_dateFormat_au.setSelected(true);
		sachetSemiP_rdbtn_dateFormat_au.setBounds(368, 220, 50, 20);
		contentPane.add(sachetSemiP_rdbtn_dateFormat_au);
		
		JRadioButton sachetSemiP_rdbtn_dateFormat_cn = new JRadioButton("CN");
		sachetSemiP_rdbtn_dateFormat_cn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				format = "Y/M/D";
			}
		});
		sachetSemiP_rdbtn_dateFormat_cn.setBounds(418, 220, 50, 20);
		contentPane.add(sachetSemiP_rdbtn_dateFormat_cn);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(sachetSemiP_rdbtn_dateFormat_au);
		dateformatGroup.add(sachetSemiP_rdbtn_dateFormat_cn);
		
		JLabel sachetSemiP_lbl_quantity = new JLabel("Display Quantity:");
		sachetSemiP_lbl_quantity.setBounds(50, 260, 100, 20);
		contentPane.add(sachetSemiP_lbl_quantity);
		
		JLabel sachetSemiP_lbl_displayUnit = new JLabel("units/carton");
		sachetSemiP_lbl_displayUnit.setFont(new Font("Tahoma", Font.BOLD, 11));
		sachetSemiP_lbl_displayUnit.setBounds(240, 260, 90, 20);
		contentPane.add(sachetSemiP_lbl_displayUnit);
		
		JLabel sachetSemiP_lbl_cartonStartFrom = new JLabel("Carton No Start from:");
		sachetSemiP_lbl_cartonStartFrom.setBounds(50, 300, 130, 20);
		contentPane.add(sachetSemiP_lbl_cartonStartFrom);
		
		sachetSemiP_cartonStartFrom = new JTextField();
		sachetSemiP_cartonStartFrom.setColumns(10);
		sachetSemiP_cartonStartFrom.setBounds(184, 300, 60, 20);
		contentPane.add(sachetSemiP_cartonStartFrom);
		
		JLabel sachetSemiP_lbl_labelsPerCarton = new JLabel("Labels Per Carton:");
		sachetSemiP_lbl_labelsPerCarton.setBounds(265, 300, 110, 20);
		contentPane.add(sachetSemiP_lbl_labelsPerCarton);
		
		sachetSemiP_labelsPerCarton = new JTextField();
		sachetSemiP_labelsPerCarton.setColumns(10);
		sachetSemiP_labelsPerCarton.setBounds(374, 300, 80, 20);
		contentPane.add(sachetSemiP_labelsPerCarton);
		
		JLabel sachetSemiP_lbl_cartonQuantity = new JLabel("Carton Quantity:");
		sachetSemiP_lbl_cartonQuantity.setBounds(50, 340, 100, 20);
		contentPane.add(sachetSemiP_lbl_cartonQuantity);
		
		sachetSemiP_cartonQuantity = new JTextField();
		sachetSemiP_cartonQuantity.setColumns(10);
		sachetSemiP_cartonQuantity.setBounds(150, 340, 95, 20);
		contentPane.add(sachetSemiP_cartonQuantity);
		
		JLabel sachetSemiP_lbl_temperature = new JLabel("Temperature:");
		sachetSemiP_lbl_temperature.setBounds(265, 340, 110, 20);
		contentPane.add(sachetSemiP_lbl_temperature);
		
		JButton sachetSemiP_btn_settings = new JButton("Settings");
		sachetSemiP_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		sachetSemiP_btn_settings.setBounds(40, 378, 110, 20);
		contentPane.add(sachetSemiP_btn_settings);
		
		JButton sachetSemiP_btn_print = new JButton("Print");
		sachetSemiP_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(dataValidation(sachetSemiP_itemCode.getText(), sachetSemiP_productName.getText(), sachetSemiP_batch.getText(), sachetSemiP_quantity.getText(), sachetSemiP_cartonStartFrom.getText(), sachetSemiP_labelsPerCarton.getText(), 
						sachetSemiP_cartonQuantity.getText(), sachetSemiP_useBy.getText(), settings)) {
					    String dateResult = DateFormatModifier.changeDate(sachetSemiP_useBy.getText(), format);
					    int temp_startFrom = Integer.valueOf(sachetSemiP_cartonStartFrom.getText());
						int temp_lblPerCarton = Integer.valueOf(sachetSemiP_labelsPerCarton.getText());
						int temp_cartonQuantity = Integer.valueOf(sachetSemiP_cartonQuantity.getText());							
						while(temp_cartonQuantity>0) {
								for(int i = temp_lblPerCarton; i>0; i--) {
									Label SemiProductLabel = new SachetSemiProductLabel(sachetSemiP_productName.getText(), sachetSemiP_itemCode.getText(), sachetSemiP_batch.getText(),
											sachetSemiP_quantity.getText(), String.valueOf(temp_startFrom), sachetSemiP_lbl_useBy.getText(), dateResult, sachetSemiP_temperature.getSelectedItem().toString(), settings.getSetting().getLogined_user());
								    printqueue.addLabelToQueue(SemiProductLabel);
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
		sachetSemiP_btn_print.setBounds(190, 378, 110, 20);
		contentPane.add(sachetSemiP_btn_print);
		
		JButton sachetSemiP_btn_back = new JButton("Back");
		sachetSemiP_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		sachetSemiP_btn_back.setBounds(340, 378, 110, 20);
		contentPane.add(sachetSemiP_btn_back);
		
		JLabel sachetSemiP_lbl_copyright_info_1 = new JLabel("Designed and Implemented by Lee.L");
		sachetSemiP_lbl_copyright_info_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sachetSemiP_lbl_copyright_info_1.setBounds(315, 405, 170, 20);
		contentPane.add(sachetSemiP_lbl_copyright_info_1);
		
		JLabel sachetSemiP_lbl_copyright_info_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		sachetSemiP_lbl_copyright_info_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sachetSemiP_lbl_copyright_info_2.setBounds(215, 420, 280, 20);
		contentPane.add(sachetSemiP_lbl_copyright_info_2);
		
		sachetSemiP_productName = new JTextField();
		sachetSemiP_productName.setBounds(150, 100, 260, 20);
		contentPane.add(sachetSemiP_productName);
		sachetSemiP_productName.setColumns(10);
		
		sachetSemiP_quantity = new JTextField();
		sachetSemiP_quantity.setBounds(150, 260, 80, 22);
		contentPane.add(sachetSemiP_quantity);
		sachetSemiP_quantity.setColumns(10);
		
		JLabel sachetSemiP_lbl_temperatureIcon = new JLabel("\u00B0C");
		sachetSemiP_lbl_temperatureIcon.setBounds(434, 340, 30, 20);
		contentPane.add(sachetSemiP_lbl_temperatureIcon);
		

		
		
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
				    "Product name missing",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(productName.length() > 110) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Product name over length \nlimitation.",
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
		if(quantity.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Content value missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			try {
				@SuppressWarnings("unused")
				int test = Integer.valueOf(quantity);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Content value invalid.\nPlease fill in number only.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
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
}
