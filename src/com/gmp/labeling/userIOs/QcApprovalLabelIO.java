package com.gmp.labeling.userIOs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Material;
import com.gmp.labeling.printModels.PrintingQueue;
import com.gmp.labeling.printModels.QcApprovalLabel;
import com.gmp.labeling.printModels.QcApprovalLabelRaw;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class QcApprovalLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6631945895637841848L;

	private JPanel contentPane;
	private JTextField qcApproval_itemCode;
	private JTextField qcApproval_printDate;
	private JTextField qcApproval_printingCount;
	private JTextField qcApproval_ginNumber;
	private JTextField qcApproval_expiryDate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QcApprovalLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel qcApproval_lbl_title = new JLabel("QC Approval Label");
		qcApproval_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		qcApproval_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		qcApproval_lbl_title.setBounds(90, 18, 300, 30);
		contentPane.add(qcApproval_lbl_title);
		
		JLabel qcApproval_lbl_itemCode = new JLabel("Item code:");
		qcApproval_lbl_itemCode.setBounds(50, 110, 90, 20);
		contentPane.add(qcApproval_lbl_itemCode);
		
		JLabel qcApproval_lbl_itemName = new JLabel("Item name:");
		qcApproval_lbl_itemName.setBounds(50, 150, 90, 20);
		contentPane.add(qcApproval_lbl_itemName);
		
		qcApproval_itemCode = new JTextField();
		qcApproval_itemCode.setBounds(160, 110, 180, 20);
		contentPane.add(qcApproval_itemCode);
		qcApproval_itemCode.setColumns(10);
		
		JComboBox qcApproval_labelType = new JComboBox();
		qcApproval_labelType.setModel(new DefaultComboBoxModel(new String[] {"Raw Material", "Packaging"}));		
		qcApproval_labelType.setBounds(160, 70, 180, 20);
		
		qcApproval_labelType.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(qcApproval_labelType.getSelectedItem().equals("Raw Material")) {
					qcApproval_expiryDate.enable();
					qcApproval_expiryDate.setBackground(Color.WHITE);						
				}else if(qcApproval_labelType.getSelectedItem().equals("Packaging")) {
					qcApproval_expiryDate.disable();
					qcApproval_expiryDate.setBackground(getBackground());					
				}
			}
		});
		contentPane.add(qcApproval_labelType);
		
		List<Material> materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JComboBox qcApproval_itemName = new JComboBox();
		qcApproval_itemName.setModel(new DefaultComboBoxModel(sarray));
		qcApproval_itemName.setMaximumRowCount(10);
		qcApproval_itemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) qcApproval_itemName.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		qcApproval_itemCode.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		break;
		        	}	        	
		        }
			}
		});
		qcApproval_itemName.setBounds(160, 150, 180, 20);
		contentPane.add(qcApproval_itemName);
		
		Button qcApproval_btn_check = new Button("Check");
		qcApproval_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = qcApproval_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		qcApproval_itemName.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		qcApproval_itemCode.setText(itemCode_value);
		        		break;
		        	}	        	
		        }
			}
		});
		qcApproval_btn_check.setBounds(340, 110, 60, 20);
		contentPane.add(qcApproval_btn_check);
		
		
		
		JLabel qcApproval_lbl_date = new JLabel("Print date:");
		qcApproval_lbl_date.setBounds(50, 230, 90, 20);
		contentPane.add(qcApproval_lbl_date);
		
		qcApproval_printDate = new JTextField();
		qcApproval_printDate.setBounds(160, 230, 180, 20);
		contentPane.add(qcApproval_printDate);
		qcApproval_printDate.setColumns(10);
		
		Button qcApproval_btn_info_1 = new Button("i");
		qcApproval_btn_info_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Print data correct format is dd/mm/yy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using the system date."
					    );
			}
		});
		qcApproval_btn_info_1.setBounds(340, 230, 20, 20);
		contentPane.add(qcApproval_btn_info_1);
		

		
		JLabel qcApproval_lbl_printingCount = new JLabel("Printing count:");
		qcApproval_lbl_printingCount.setBounds(50, 270, 90, 20);
		contentPane.add(qcApproval_lbl_printingCount);
		
		qcApproval_printingCount = new JTextField();
		qcApproval_printingCount.setBounds(160, 270, 180, 20);
		contentPane.add(qcApproval_printingCount);
		qcApproval_printingCount.setColumns(10);
		
		qcApproval_expiryDate = new JTextField();
		qcApproval_expiryDate.setBounds(160, 310, 180, 20);
		contentPane.add(qcApproval_expiryDate);
		qcApproval_expiryDate.setColumns(10);
		
		Button qcApproval_btn_info_2 = new Button("i");
		qcApproval_btn_info_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Expiry data correct format is dd/mm/yy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018."
					    );
			}
		});
		qcApproval_btn_info_2.setBounds(340, 310, 20, 20);
		contentPane.add(qcApproval_btn_info_2);
		
		JButton qcApproval_btn_settings = new JButton("Settings");
		qcApproval_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		qcApproval_btn_settings.setBounds(25, 360, 90, 20);
		contentPane.add(qcApproval_btn_settings);
		
		JButton qcApproval_btn_print = new JButton("Print");
		qcApproval_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean type = true;
				if(qcApproval_labelType.getSelectedItem().equals("Raw Material")) {
					type = true;
				}else if(qcApproval_labelType.getSelectedItem().equals("Packaging")){
					type = false;
				}
				if(dataValidation(qcApproval_itemCode.getText(), qcApproval_itemName.getSelectedItem().toString(), qcApproval_ginNumber.getText(), qcApproval_printDate.getText(), qcApproval_printingCount.getText(), settings, type, qcApproval_expiryDate.getText())) {
					int printNumber = Integer.valueOf(qcApproval_printingCount.getText());
					int i = 0;
					if(type) {
						while(i<printNumber) {
							printqueue.addLabelToQueue(new QcApprovalLabelRaw(qcApproval_itemCode.getText(), qcApproval_itemName.getSelectedItem().toString(), qcApproval_ginNumber.getText().replaceAll("([a-z])", "$1").toUpperCase(), settings.getSetting().getLogined_user(), qcApproval_expiryDate.getText(),qcApproval_printDate.getText()));
							i++;
						}
					}else {
						while(i<printNumber) {
							printqueue.addLabelToQueue(new QcApprovalLabel(qcApproval_itemCode.getText(), qcApproval_itemName.getSelectedItem().toString(), qcApproval_ginNumber.getText().replaceAll("([a-z])", "$1").toUpperCase(), settings.getSetting().getLogined_user(), qcApproval_printDate.getText()));
							i++;
						}
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
		qcApproval_btn_print.setBounds(135, 360, 90, 20);
		contentPane.add(qcApproval_btn_print);
		
		JButton qcApproval_btn_update = new JButton("Update");
		qcApproval_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		qcApproval_btn_update.setBounds(245, 360, 90, 20);
		contentPane.add(qcApproval_btn_update);
		
		JButton qcApproval_btn_back = new JButton("Back");
		qcApproval_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		qcApproval_btn_back.setBounds(355, 360, 90, 20);
		contentPane.add(qcApproval_btn_back);
		
		JLabel qcApproval_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		qcApproval_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		qcApproval_lbl_copyright_1.setBounds(295, 395, 170, 20);
		contentPane.add(qcApproval_lbl_copyright_1);
		
		JLabel qcApproval_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		qcApproval_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		qcApproval_lbl_copyright_2.setBounds(194, 410, 280, 20);
		contentPane.add(qcApproval_lbl_copyright_2);
		
		JLabel qcApproval_lbl_ginNumber = new JLabel("Gin:");
		qcApproval_lbl_ginNumber.setBounds(50, 190, 90, 20);
		contentPane.add(qcApproval_lbl_ginNumber);
		
		qcApproval_ginNumber = new JTextField();
		qcApproval_ginNumber.setBounds(160, 190, 180, 20);
		contentPane.add(qcApproval_ginNumber);
		qcApproval_ginNumber.setColumns(10);
		
		JLabel qcApproval_lbl_lableType = new JLabel("Label type:");
		qcApproval_lbl_lableType.setBounds(50, 70, 90, 20);
		contentPane.add(qcApproval_lbl_lableType);
		
		JLabel qcApproval_lbl_expiryDate = new JLabel("Expiry date:");
		qcApproval_lbl_expiryDate.setBounds(50, 310, 90, 20);
		contentPane.add(qcApproval_lbl_expiryDate);
		

		
	}
	
	public boolean dataValidation(String itemCode, String itemName, String ginNumber, String printDate, String printingCount, Settings settings, boolean labelType, String expiryDate) {		
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(itemName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item name missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(ginNumber.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "GIN number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			if(ginNumber.length()!=7) {				
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid Gin Number Format Error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}else {
				String aCode = String.valueOf(ginNumber.charAt(0))+String.valueOf(ginNumber.charAt(1));
				aCode = aCode.replaceAll("([a-z])", "$1").toUpperCase();
				if(!aCode.equals("AU")&&!aCode.equals("HW")&&!aCode.equals("SP")) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid Gin Number Site Code Error.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}				
			}			
		}
		
		if(!printDate.equals("")) {
		char[] temp = printDate.toCharArray();
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
		if(labelType) {
			if(expiryDate.equals("")) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Expiry date missing.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}else {
				char[] temp = expiryDate.toCharArray();
				if(temp.length == 10) {
				String day = String.valueOf(temp[0])+String.valueOf(temp[1]);
				String month = String.valueOf(temp[3]) + String.valueOf(temp[4]);
				String year = String.valueOf(temp[6])+ String.valueOf(temp[7]) + String.valueOf(temp[8]) + String.valueOf(temp[9]);
				if(temp[2]!='/' || temp[5]!='/') {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
							"Expiry date input format error.",
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
						    "Invalid Exiry date.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}
				if(monthint<=0 || monthint > 12) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid Exiry date.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}else if(monthint<= 7 && monthint%2==1){
					if(dayint <= 0 || dayint > 31) {
						JOptionPane.showMessageDialog(settings.getComponentPage(),
							    "Invalid Exiry date.",
							    "Inane error",
							    JOptionPane.ERROR_MESSAGE);
						return false;
					}			
				}else if(monthint<=7 && monthint%2==0 && monthint != 2){
					if(dayint <= 0 || dayint > 30) {
						JOptionPane.showMessageDialog(settings.getComponentPage(),
							    "Invalid Exiry date.",
							    "Inane error",
							    JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}else if (monthint == 2) {
					int realyear = yearint;
					if(realyear%400==0 || (realyear%4 ==0 && realyear%100 != 0)) {
						if(dayint <= 0 || dayint > 29) {
							JOptionPane.showMessageDialog(settings.getComponentPage(),
								    "Invalid Exiry date.",
								    "Inane error",
								    JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}else {
						if(dayint <= 0 || dayint > 28) {
							JOptionPane.showMessageDialog(settings.getComponentPage(),
								    "Invalid Exiry date.",
								    "Inane error",
								    JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
				}else if(monthint%2 == 0) {
					if(dayint <= 0 || dayint > 31) {
						JOptionPane.showMessageDialog(settings.getComponentPage(),
							    "Invalid Exiry date.",
							    "Inane error",
							    JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}else {
					if(dayint <= 0 || dayint > 30) {
						JOptionPane.showMessageDialog(settings.getComponentPage(),
							    "Invalid Exiry date.",
							    "Inane error",
							    JOptionPane.ERROR_MESSAGE);
						return false;
					    }
				     }
				}else {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Invalid Exiry date.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}

		return true;
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
}
