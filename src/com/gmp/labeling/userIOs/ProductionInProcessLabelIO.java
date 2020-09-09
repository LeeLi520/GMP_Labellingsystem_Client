package com.gmp.labeling.userIOs;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Material;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.ProductionInProcessLabel;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductionInProcessLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8037004927561327172L;
	private JPanel contentPane;
	private JTextField productIP_itemCode;
	private JTextField productIP_batch;
	private JTextField productIP_date;
	private JTextField productIP_printCount;

	/**
	 * Create the frame.
	 */
	public ProductionInProcessLabelIO(Settings settings, PrintingQueue printqueue) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 390);
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel productIP_lbl_title = new JLabel("Production In Process");
		productIP_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		productIP_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		productIP_lbl_title.setBounds(130, 18, 230, 30);
		contentPane.add(productIP_lbl_title);
		
		JLabel productIP_lbl_itemCode = new JLabel("Item code:");
		productIP_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(productIP_lbl_itemCode);
		
		productIP_itemCode = new JTextField();
		productIP_itemCode.setBounds(140, 60, 200, 20);
		contentPane.add(productIP_itemCode);
		productIP_itemCode.setColumns(10);
		
		JLabel productIP_lbl_itemName = new JLabel("Item name:");
		productIP_lbl_itemName.setBounds(50, 100, 90, 20);
		contentPane.add(productIP_lbl_itemName);
		

		
		JLabel productIP_itemNamelbl = new JLabel("");
		productIP_itemNamelbl.setBounds(140, 100, 280, 20);
		contentPane.add(productIP_itemNamelbl);
		
		JLabel productIP_lbl_batch = new JLabel("Batch:");
		productIP_lbl_batch.setBounds(50, 140, 90, 20);
		contentPane.add(productIP_lbl_batch);
		
		productIP_batch = new JTextField();
		productIP_batch.setBounds(140, 140, 200, 20);
		contentPane.add(productIP_batch);
		productIP_batch.setColumns(10);
		
		JLabel productIP_lbl_date = new JLabel("Issue date:");
		productIP_lbl_date.setBounds(50, 180, 90, 20);
		contentPane.add(productIP_lbl_date);
		
		productIP_date = new JTextField();
		productIP_date.setBounds(140, 180, 200, 20);
		contentPane.add(productIP_date);
		productIP_date.setColumns(10);
		
		JLabel productIP_lbl_printCount = new JLabel("Print count:");
		productIP_lbl_printCount.setBounds(50, 220, 90, 20);
		contentPane.add(productIP_lbl_printCount);
		
		productIP_printCount = new JTextField();
		productIP_printCount.setBounds(140, 220, 200, 20);
		contentPane.add(productIP_printCount);
		productIP_printCount.setColumns(10);
		
		List<Material> materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		Button productIP_btn_check = new Button("Check");
		productIP_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = productIP_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		productIP_itemNamelbl.setText(JsonParser.takeoffComma(temp.getItem_name()));
		        		productIP_itemCode.setText(itemCode_value);
		        		break;
		        	}	        	
		        }
			}
		});
		productIP_btn_check.setBounds(340, 60, 80, 20);
		contentPane.add(productIP_btn_check);
		
		JLabel productIP_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		productIP_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		productIP_copyright_developer.setBounds(284, 305, 170, 20);
		contentPane.add(productIP_copyright_developer);
		
		JLabel productIP_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		productIP_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		productIP_copyright_company.setBounds(184, 320, 280, 20);
		contentPane.add(productIP_copyright_company);
		
		JButton productIP_btn_settings = new JButton("Settings");
		productIP_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}		
		});
		productIP_btn_settings.setBounds(30, 265, 90, 20);
		contentPane.add(productIP_btn_settings);
		
		JButton productIP_btn_print = new JButton("Print");
		productIP_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(productIP_itemCode.getText(), productIP_itemNamelbl.getText(), productIP_batch.getText(), productIP_date.getText(), productIP_printCount.getText(), settings)) {
					int printNumber = Integer.valueOf(productIP_printCount.getText());
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new ProductionInProcessLabel(productIP_itemCode.getText(), productIP_batch.getText(), settings.getSetting().getLogined_user(), productIP_date.getText()));
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
					productIP_itemNamelbl.setText("");
				}
			}
		});
		productIP_btn_print.setBounds(140, 265, 90, 20);
		contentPane.add(productIP_btn_print);
		
		JButton productIP_btn_update = new JButton("Update");
		productIP_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		productIP_btn_update.setBounds(250, 265, 90, 20);
		contentPane.add(productIP_btn_update);
		
		JButton productIP_btn_Back = new JButton("Back");
		productIP_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		productIP_btn_Back.setBounds(360, 265, 90, 20);
		contentPane.add(productIP_btn_Back);	
		
	}
	
	
	public List<Material> inputItemdata(String path) {
		String csvFile = path;
        String line = "";
        String cvsSplitBy = ",";
        List<Material> materials = new ArrayList<Material>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {            	
            	if(line.equals("")) {
            		//first line dont input
            	}else {
            	String[] items = line.split(cvsSplitBy);
                Material m = new Material();
                m.setItem_code(items[0]);
                m.setItem_name(items[1]);
                m.setItem_unit(items[2]);
                materials.add(m);
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return materials;
	}
	
	public boolean dataValidation(String itemCode, String itemName, String batch, String issueDate, String printCount, Settings settings) {
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(itemName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Please check item first.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(batch.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Batch number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!issueDate.equals("")) {
			char[] temp = issueDate.toCharArray();
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
			@SuppressWarnings("unused")
			int test_count = Integer.valueOf(printCount);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Invalid print count. Please enter number to the field.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
