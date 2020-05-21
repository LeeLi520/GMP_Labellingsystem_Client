package com.gmp.labeling.userIOs;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.SpecialItemLabel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Button;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpecialItemLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5126472911140178329L;
	private JPanel contentPane;
	private JTextField specialItem_material;
	private JTextField specialItem_code;
	private JTextField specialItem_quantity;
	private JTextField specialItem_unit;
	private JTextField specialItem_gin;
	private JTextField specialItem_receivedDate;
	private JTextField specialItem_printingCount;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SpecialItemLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel specialItem_lbl_title = new JLabel("Special Item Label");
		specialItem_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		specialItem_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		specialItem_lbl_title.setBounds(100, 18, 200, 30);
		contentPane.add(specialItem_lbl_title);
		
		JLabel specialItem_lbl_site = new JLabel("Site:");
		specialItem_lbl_site.setBounds(50, 60, 50, 20);
		contentPane.add(specialItem_lbl_site);
		
		JComboBox specialItem_site = new JComboBox();
		specialItem_site.setModel(new DefaultComboBoxModel(new String[] {"14 Amax Ave, Girraween","60 Huntingwood Drive, Huntingwood"}));
		specialItem_site.setBounds(120, 60, 220, 20);
		contentPane.add(specialItem_site);
		
		JLabel specialItem_lbl_material = new JLabel("Material:");
		specialItem_lbl_material.setBounds(50, 100, 50, 20);
		contentPane.add(specialItem_lbl_material);
		
		specialItem_material = new JTextField();
		specialItem_material.setBounds(120, 100, 220, 20);
		contentPane.add(specialItem_material);
		specialItem_material.setColumns(10);
		
		JLabel specialItem_lbl_code = new JLabel("Code:");
		specialItem_lbl_code.setBounds(50, 140, 50, 20);
		contentPane.add(specialItem_lbl_code);
		
		specialItem_code = new JTextField();
		specialItem_code.setBounds(120, 140, 220, 20);
		contentPane.add(specialItem_code);
		specialItem_code.setColumns(10);
		
		JLabel specialItem_lbl_quantity = new JLabel("Quantity:");
		specialItem_lbl_quantity.setBounds(50, 180, 70, 20);
		contentPane.add(specialItem_lbl_quantity);
		
		specialItem_quantity = new JTextField();
		specialItem_quantity.setBounds(130, 180, 80, 20);
		contentPane.add(specialItem_quantity);
		specialItem_quantity.setColumns(10);
		
		JLabel specialItem_lbl_unit = new JLabel("Unit:");
		specialItem_lbl_unit.setBounds(220, 180, 30, 20);
		contentPane.add(specialItem_lbl_unit);
		
		specialItem_unit = new JTextField();
		specialItem_unit.setBounds(260, 180, 80, 20);
		contentPane.add(specialItem_unit);
		specialItem_unit.setColumns(10);
		
		JLabel specialItem_lbl_gin = new JLabel("Gin:");
		specialItem_lbl_gin.setBounds(50, 220, 30, 20);
		contentPane.add(specialItem_lbl_gin);
		
		specialItem_gin = new JTextField();
		specialItem_gin.setBounds(130, 220, 80, 20);
		contentPane.add(specialItem_gin);
		specialItem_gin.setColumns(10);
		
		JLabel specialItem_lbl_receivedDate = new JLabel("Received Date:");
		specialItem_lbl_receivedDate.setBounds(50, 260, 90, 20);
		contentPane.add(specialItem_lbl_receivedDate);
		
		specialItem_receivedDate = new JTextField();
		specialItem_receivedDate.setBounds(160, 260, 160, 20);
		contentPane.add(specialItem_receivedDate);
		specialItem_receivedDate.setColumns(10);
		
		Button specialItem_btn_receivedDateInfo = new Button("i");
		specialItem_btn_receivedDateInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Received Date correct format is dd/mm/yy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using the system date."
					    );
			}
		});
		specialItem_btn_receivedDateInfo.setBounds(320, 260, 20, 20);
		contentPane.add(specialItem_btn_receivedDateInfo);
		
		JButton specialItem_btn_settings = new JButton("Settings");
		specialItem_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		specialItem_btn_settings.setBounds(40, 340, 90, 20);
		contentPane.add(specialItem_btn_settings);
		
		JButton specialItem_btn_print = new JButton("Print");
		specialItem_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(settings, specialItem_site.getSelectedItem().toString(),specialItem_material.getText(), specialItem_code.getText(),
						specialItem_quantity.getText(), specialItem_unit.getText(), specialItem_gin.getText(), specialItem_receivedDate.getText(), specialItem_printingCount.getText())) {
					int printNumber = Integer.valueOf(specialItem_printingCount.getText());
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new SpecialItemLabel(settings.getSetting().getLogined_user(), specialItem_site.getSelectedItem().toString(), specialItem_material.getText() , specialItem_code.getText(),
								specialItem_quantity.getText(), specialItem_unit.getText(), specialItem_gin.getText(), specialItem_receivedDate.getText()));
						i++;
					}
					if(settings.getSetting().isLocalPrintingMode()) {
						PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), null, 0, 620);
						printio.setLocationRelativeTo(null);
						printio.setVisible(true);
					}else {
						PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), settings.getSetting().getIpAddress(), settings.getSetting().getPort(), 620);
						printio.setLocationRelativeTo(null);
						printio.setVisible(true);
					}
				}				
			}
		});
		specialItem_btn_print.setBounds(170, 340, 90, 20);
		contentPane.add(specialItem_btn_print);
		
		JButton specialItem_btn_back = new JButton("Back");
		specialItem_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		specialItem_btn_back.setBounds(300, 340, 90, 20);
		contentPane.add(specialItem_btn_back);
		
		JLabel specialItem_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		specialItem_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		specialItem_lbl_copyright_1.setBounds(244, 366, 170, 20);
		contentPane.add(specialItem_lbl_copyright_1);
		
		JLabel specialItem_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		specialItem_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		specialItem_lbl_copyright_2.setBounds(144, 381, 280, 20);
		contentPane.add(specialItem_lbl_copyright_2);
		
		JLabel specialItem_lbl_printingCount = new JLabel("Printing Count:");
		specialItem_lbl_printingCount.setBounds(50, 300, 90, 20);
		contentPane.add(specialItem_lbl_printingCount);
		
		specialItem_printingCount = new JTextField();
		specialItem_printingCount.setBounds(160, 300, 180, 20);
		contentPane.add(specialItem_printingCount);
		specialItem_printingCount.setColumns(10);
		
		Button specialItem_btn_GinInfo = new Button("i");
		specialItem_btn_GinInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Gin Number correct format is Location code + Original Gin number. \n eg. Girraween site-> SP00000 and Huntingwood site -> SPH00000 "
					    );
			}
		});
		specialItem_btn_GinInfo.setBounds(210, 220, 20, 20);
		contentPane.add(specialItem_btn_GinInfo);
	}
	public boolean dataValidation(Settings settings, String site, String itemName, String itemCode,
			String quantity, String unit, String gin, String receivedDate, String printingCount) {
		if(itemName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Material name missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(itemName.length()>66){
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Material name cannot be over 66 characters",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(itemCode.length()!=10){
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code must be 10 digits, start from 4 digits item type and end with 6 digits item id.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}

		
		try {
			if(Float.parseFloat(quantity)<0) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Item Qty missing or Invalid input.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item Qty missing or Invalid input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(unit.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Unit missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
				
		if(gin.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "GIN number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		switch(site) {
        case "14 Amax Ave, Girraween":
        	if(gin.length()!=7) {
        		JOptionPane.showMessageDialog(settings.getComponentPage(),
    				    "Invalid GIN number format",
    				    "Inane error",
    				    JOptionPane.ERROR_MESSAGE);
    			return false;
        	}else {
        		String ginType = String.valueOf(gin.charAt(0))+String.valueOf(gin.charAt(1));
        		ginType = ginType.toUpperCase();
        		if(!ginType.equals("SP")) {
        			JOptionPane.showMessageDialog(settings.getComponentPage(),
        				    "Invalid GIN number format for Girraween Site",
        				    "Inane error",
        				    JOptionPane.ERROR_MESSAGE);
        			return false;
        		}
        	}

        break;
        case "60 Huntingwood Drive, Huntingwood":
        	if(gin.length()!=8) {
        		JOptionPane.showMessageDialog(settings.getComponentPage(),
    				    "Invalid GIN number format",
    				    "Inane error",
    				    JOptionPane.ERROR_MESSAGE);
    			return false;
        	}else {
        		String ginType = String.valueOf(gin.charAt(0))+String.valueOf(gin.charAt(1))+String.valueOf(gin.charAt(2));
        		ginType = ginType.toUpperCase();
        		if(!ginType.equals("SPH")) {
        			JOptionPane.showMessageDialog(settings.getComponentPage(),
        				    "Invalid GIN number format for Huntingwood Site",
        				    "Inane error",
        				    JOptionPane.ERROR_MESSAGE);
        			return false;
        		}
        	}
        break;	           
   }
		
		if(!receivedDate.equals("")) {
		char[] temp = receivedDate.toCharArray();
		if(temp.length == 10) {
		String day = String.valueOf(temp[0])+String.valueOf(temp[1]);
		String month = String.valueOf(temp[3]) + String.valueOf(temp[4]);
		String year = String.valueOf(temp[6])+ String.valueOf(temp[7]) + String.valueOf(temp[8]) + String.valueOf(temp[9]);
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
		}
		try {
			if(Integer.parseInt(printingCount)<=0) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "PrintingQty missing or Invalid input",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "PrintingQty missing or Invalid input",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}
}
