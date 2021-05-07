package com.gmp.labeling.userIOs;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Material;
import com.gmp.labeling.printModels.InterimApprovalLabel;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Button;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class InterimApprovalLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8104727041274480052L;
	private JPanel contentPane;
	private JTextField interimApproval_itemCode;
	private JTextField interimApproval_ginNumber;
	private JTextField interimApproval_printDate;
	private JTextField interimApproval_printingCount;
	private JTextField interimApproval_expiryDate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public InterimApprovalLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		List<Material> materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));

		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JLabel interimApproval_lbl_title = new JLabel("Interim Approval Label");
		interimApproval_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		interimApproval_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		interimApproval_lbl_title.setBounds(90, 18, 300, 30);
		contentPane.add(interimApproval_lbl_title);
		
		JLabel interimApproval_lbl_itemCode = new JLabel("Item code:");
		interimApproval_lbl_itemCode.setBounds(50, 70, 90, 20);
		contentPane.add(interimApproval_lbl_itemCode);
		
		interimApproval_itemCode = new JTextField();
		interimApproval_itemCode.setColumns(10);
		interimApproval_itemCode.setBounds(160, 70, 180, 20);
		contentPane.add(interimApproval_itemCode);
		
		JComboBox interimApproval_itemName = new JComboBox();
		interimApproval_itemName.setMaximumRowCount(10);
		interimApproval_itemName.setModel(new DefaultComboBoxModel(sarray));
		interimApproval_itemName.setBounds(160, 110, 180, 20);
		interimApproval_itemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				String value = (String) interimApproval_itemName.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		interimApproval_itemCode.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		break;
		        	}	        	
		        }
			}
		});
				
		Button interimApproval_btn_check = new Button("Check");
		interimApproval_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = interimApproval_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		interimApproval_itemName.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		interimApproval_itemCode.setText(itemCode_value);
		        		break;
		        	}	        	
		        }				
			}
		});
		interimApproval_btn_check.setBounds(340, 70, 60, 20);
		contentPane.add(interimApproval_btn_check);
		
		JLabel interimApproval_lbl_itemName = new JLabel("Item name:");
		interimApproval_lbl_itemName.setBounds(50, 110, 90, 20);
		contentPane.add(interimApproval_lbl_itemName);						
		contentPane.add(interimApproval_itemName);
		
		JLabel interimApproval_lbl_ginNumber = new JLabel("Gin:");
		interimApproval_lbl_ginNumber.setBounds(50, 150, 90, 20);
		contentPane.add(interimApproval_lbl_ginNumber);
		
		interimApproval_ginNumber = new JTextField();
		interimApproval_ginNumber.setColumns(10);
		interimApproval_ginNumber.setBounds(160, 150, 180, 20);
		contentPane.add(interimApproval_ginNumber);
		
		JLabel interimApproval_lbl_printDate = new JLabel("Print date:");
		interimApproval_lbl_printDate.setBounds(50, 190, 90, 20);
		contentPane.add(interimApproval_lbl_printDate);
		
		interimApproval_printDate = new JTextField();
		interimApproval_printDate.setColumns(10);
		interimApproval_printDate.setBounds(160, 190, 180, 20);
		contentPane.add(interimApproval_printDate);
		
		Button interimApproval_btn_info_pd = new Button("i");
		interimApproval_btn_info_pd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Print data correct format is dd/mm/yy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018 \n Leave it blank means using the system date."
					    );
			}
		});
		interimApproval_btn_info_pd.setBounds(340, 190, 20, 20);
		contentPane.add(interimApproval_btn_info_pd);
		
		JLabel interimApproval_lbl_printingCount = new JLabel("Printing count:");
		interimApproval_lbl_printingCount.setBounds(50, 230, 90, 20);
		contentPane.add(interimApproval_lbl_printingCount);
		
		interimApproval_printingCount = new JTextField();
		interimApproval_printingCount.setColumns(10);
		interimApproval_printingCount.setBounds(160, 230, 180, 20);
		contentPane.add(interimApproval_printingCount);
		
		JLabel interimApproval_lbl_expiryDate = new JLabel("Expiry date:");
		interimApproval_lbl_expiryDate.setBounds(50, 270, 90, 20);
		contentPane.add(interimApproval_lbl_expiryDate);
		
		interimApproval_expiryDate = new JTextField();
		interimApproval_expiryDate.setColumns(10);
		interimApproval_expiryDate.setBounds(160, 270, 180, 20);
		contentPane.add(interimApproval_expiryDate);
		
		Button interimApproval_btn_info_ed = new Button("i");
		interimApproval_btn_info_ed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Expiry data correct format is dd/mm/yy. \n dd=>day  mm=>month  yyyy=>year \n eg. 20th May 2018 => 20/05/2018."
					    );
			}
		});
		interimApproval_btn_info_ed.setBounds(340, 270, 20, 20);
		contentPane.add(interimApproval_btn_info_ed);
		
		JButton interimApproval_btn_settings = new JButton("Settings");
		interimApproval_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		interimApproval_btn_settings.setBounds(25, 320, 90, 20);
		contentPane.add(interimApproval_btn_settings);
		
		JButton interimApproval_btn_print = new JButton("Print");
		interimApproval_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(interimApproval_itemCode.getText(), interimApproval_itemName.getSelectedItem().toString(), interimApproval_ginNumber.getText(), interimApproval_printDate.getText(), interimApproval_printingCount.getText(), interimApproval_expiryDate.getText(), settings)) {
					int printNumber = Integer.valueOf(interimApproval_printingCount.getText());
					int i = 0;
					while(i<printNumber) {
						printqueue.addLabelToQueue(new InterimApprovalLabel(interimApproval_itemCode.getText(), interimApproval_itemName.getSelectedItem().toString(), interimApproval_ginNumber.getText().replaceAll("([a-z])", "$1").toUpperCase(), settings.getSetting().getLogined_user(),
								interimApproval_expiryDate.getText(), interimApproval_printDate.getText()));
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
		interimApproval_btn_print.setBounds(135, 320, 90, 20);
		contentPane.add(interimApproval_btn_print);
		
		JButton interimApproval_btn_update = new JButton("Update");
		interimApproval_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings,"stk.csv", "materiallistpath");
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		interimApproval_btn_update.setBounds(245, 320, 90, 20);
		contentPane.add(interimApproval_btn_update);
		
		JButton interimApproval_btn_back = new JButton("Back");
		interimApproval_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		interimApproval_btn_back.setBounds(355, 320, 90, 20);
		contentPane.add(interimApproval_btn_back);
		
		JLabel interimApproval_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		interimApproval_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		interimApproval_lbl_copyright_1.setBounds(295, 355, 170, 20);
		contentPane.add(interimApproval_lbl_copyright_1);
		
		JLabel interimApproval_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		interimApproval_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		interimApproval_lbl_copyright_2.setBounds(194, 370, 280, 20);
		contentPane.add(interimApproval_lbl_copyright_2);
	}
	
	public boolean dataValidation(String itemCode, String itemName, String ginNumber, String printDate, String printingCount, String expiryDate, Settings settings) {		
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
				if(!aCode.equals("AU")&&!aCode.equals("HW")) {
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
