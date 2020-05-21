package com.gmp.labeling.userIOs;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.AssetLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;
import java.awt.Button;

public class CustomizedPrinting_AssetLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5635767719859499935L;
	private JPanel contentPane;
	private JTextField Asset_assetNumber;
	private JTextField Asset_department;
	private JTextField Asset_poNumber;
	private JTextField Asset_deviceName;
	private JTextField Asset_supplier;
	private JTextField Asset_date;
	private JTextField Asset_printingQuantity;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CustomizedPrinting_AssetLabelIO(Settings settings, PrintingQueue printqueue, List<Label> tempList, JLabel printCount, int x, int y) {
		RestConnection connection =new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, 480, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Asset_lbl_title = new JLabel("Asset Label Printing");
		Asset_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		Asset_lbl_title.setBounds(140, 18, 200, 30);
		contentPane.add(Asset_lbl_title);
		
		JLabel Asset_lbl_company = new JLabel("Company:");
		Asset_lbl_company.setBounds(50, 60, 90, 20);
		contentPane.add(Asset_lbl_company);
		
		JComboBox Asset_company = new JComboBox();
		Asset_company.setModel(new DefaultComboBoxModel(new String[] {"GMP Pharmaceuticals", "Health Sharing Group", "Aunew"}));
		Asset_company.setBounds(150, 60, 180, 20);
		contentPane.add(Asset_company);
		
		JLabel Asset_lbl_assetNumber = new JLabel("Asset Number:");
		Asset_lbl_assetNumber.setBounds(50, 100, 90, 20);
		contentPane.add(Asset_lbl_assetNumber);
		
		Asset_assetNumber = new JTextField();
		Asset_assetNumber.setBounds(150, 100, 180, 20);
		contentPane.add(Asset_assetNumber);
		Asset_assetNumber.setColumns(10);
		
		JLabel Asset_lbl_department = new JLabel("Department:");
		Asset_lbl_department.setBounds(50, 140, 90, 20);
		contentPane.add(Asset_lbl_department);
		
		Asset_department = new JTextField();
		Asset_department.setBounds(150, 140, 180, 20);
		contentPane.add(Asset_department);
		Asset_department.setColumns(10);
		
		JLabel Asset_lbl_poNumber = new JLabel("PO Number:");
		Asset_lbl_poNumber.setBounds(50, 180, 90, 20);
		contentPane.add(Asset_lbl_poNumber);
		
		Asset_poNumber = new JTextField();
		Asset_poNumber.setBounds(150, 180, 180, 20);
		contentPane.add(Asset_poNumber);
		Asset_poNumber.setColumns(10);
		
		JLabel Asset_lbl_deviceName = new JLabel("Device Name:");
		Asset_lbl_deviceName.setBounds(50, 220, 90, 20);
		contentPane.add(Asset_lbl_deviceName);
		
		Asset_deviceName = new JTextField();
		Asset_deviceName.setBounds(150, 220, 180, 20);
		contentPane.add(Asset_deviceName);
		Asset_deviceName.setColumns(10);
		
		JLabel Asset_lbl_supplier = new JLabel("Supplier:");
		Asset_lbl_supplier.setBounds(50, 260, 90, 20);
		contentPane.add(Asset_lbl_supplier);
		
		Asset_supplier = new JTextField();
		Asset_supplier.setBounds(150, 260, 180, 20);
		contentPane.add(Asset_supplier);
		Asset_supplier.setColumns(10);
		
		JLabel Asset_lbl_date = new JLabel("Date:");
		Asset_lbl_date.setBounds(50, 300, 90, 20);
		contentPane.add(Asset_lbl_date);
		
		Asset_date = new JTextField();
		Asset_date.setBounds(150, 300, 180, 20);
		contentPane.add(Asset_date);
		Asset_date.setColumns(10);
		
		JButton Asset_btn_addToQueue = new JButton("Add To Queue");
		Asset_btn_addToQueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if( dataValidation(Asset_assetNumber.getText(), Asset_department.getText(), Asset_poNumber.getText(), Asset_deviceName.getText(),
						Asset_supplier.getText(), Asset_date.getText(), Asset_printingQuantity.getText(), settings)) {
					tempList.clear();
					int printNumber = Integer.valueOf(Asset_printingQuantity.getText());
					int i = 0;
					while(i<printNumber) {
						AssetLabel asset = new AssetLabel(Asset_company.getSelectedItem().toString(), Asset_assetNumber.getText(), Asset_deviceName.getText(), Asset_department.getText(), 
								Asset_supplier.getText(), Asset_date.getText(), Asset_poNumber.getText(), settings.getSetting().getLogined_user());
						printqueue.addLabelToQueue(asset);
						tempList.add(asset);
						i++;
					}
				}
				printCount.setText(String.valueOf(printqueue.getList().size()));				
			}
		});
		
		JButton Asset_btn_back = new JButton("Close");
		Asset_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		Asset_btn_back.setBounds(300, 390, 120, 20);
		contentPane.add(Asset_btn_back);
		
		JLabel Asset_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		Asset_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Asset_copyright_company.setBounds(150, 430, 280, 20);
		contentPane.add(Asset_copyright_company);
		
		JLabel Asset_btn_developer = new JLabel("Designed and Implemented by Lee.L");
		Asset_btn_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Asset_btn_developer.setBounds(250, 415, 170, 20);
		contentPane.add(Asset_btn_developer);
		
		
		Asset_btn_addToQueue.setBounds(50, 390, 120, 20);
		contentPane.add(Asset_btn_addToQueue);
		
		JLabel Asset_lbl_printingQuantity = new JLabel("Printing Qty:");
		Asset_lbl_printingQuantity.setBounds(50, 340, 90, 20);
		contentPane.add(Asset_lbl_printingQuantity);
		
		Asset_printingQuantity = new JTextField();
		Asset_printingQuantity.setBounds(150, 340, 180, 20);
		contentPane.add(Asset_printingQuantity);
		Asset_printingQuantity.setColumns(10);
		
		Button Asset_btn_info = new Button("i");
		Asset_btn_info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yy. \n dd=>day  mm=>month  yy=>year \n eg. 20th May 2018 => 20/05/18 \n Leave it blank means using the system date."
					    );
			}
		});
		Asset_btn_info.setBounds(330, 300, 20, 20);
		contentPane.add(Asset_btn_info);
		

	}
	
	public boolean dataValidation(String assetNumber, String department, String poNumber, String deviceName, String supplier, String receivedDate, String printingCount, Settings settings) {
		if(assetNumber.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Asset number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(department.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Department missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(poNumber.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "PO number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(deviceName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Device name missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(supplier.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Supplier missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(!receivedDate.equals("")) {
		char[] temp = receivedDate.toCharArray();
		if(temp.length == 8) {
		String day = String.valueOf(temp[0])+String.valueOf(temp[1]);
		String month = String.valueOf(temp[3]) + String.valueOf(temp[4]);
		String year = String.valueOf(temp[6])+ String.valueOf(temp[7]);
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
			int realyear = yearint +2000;
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
			Integer.parseInt(printingCount);
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
