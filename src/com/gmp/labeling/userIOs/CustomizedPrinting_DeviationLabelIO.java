package com.gmp.labeling.userIOs;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Material;
import com.gmp.labeling.printModels.DeviationLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;

public class CustomizedPrinting_DeviationLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2464810923940452352L;
	private JPanel contentPane;
	private JTextField deviation_item_Code;
	private JTextField deviation_gin_Number;
	private JTextField deviation_item_Qty;
	private JTextField deviation_printing_Qty;
	private String item_unit;
	private JTextField deviation_date;
	private List<Material> materials;

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CustomizedPrinting_DeviationLabelIO(Settings settings, PrintingQueue printqueue, List<Label> tempList, JLabel printCount, int x, int y) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		item_unit = null;
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, 480, 440);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel deviation_lbl_title = new JLabel("Deviation Label Printing");
		deviation_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		deviation_lbl_title.setForeground(SystemColor.desktop);
		deviation_lbl_title.setBounds(130, 18, 230, 30);
		contentPane.add(deviation_lbl_title);
		
		JLabel deviation_lbl_itemCode = new JLabel("Item Code:");
		deviation_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(deviation_lbl_itemCode);
		
		JLabel deviation_lbl_itemName = new JLabel("Item name:");
		deviation_lbl_itemName.setBounds(50, 100, 90, 20);
		contentPane.add(deviation_lbl_itemName);
		
		JLabel deviation_lbl_gin = new JLabel("Gin:");
		deviation_lbl_gin.setBounds(50, 140, 90, 20);
		contentPane.add(deviation_lbl_gin);
		
		JLabel deviation_lbl_itemQty = new JLabel("Item Qty:");
		deviation_lbl_itemQty.setBounds(50, 180, 90, 20);
		contentPane.add(deviation_lbl_itemQty);
		
		JLabel deviation_lbl_item_unit = new JLabel("");
		deviation_lbl_item_unit.setBounds(340, 180, 80, 20);
		contentPane.add(deviation_lbl_item_unit);
				
		deviation_item_Code = new JTextField();
		deviation_item_Code.setBounds(160, 60, 180, 20);
		contentPane.add(deviation_item_Code);
		deviation_item_Code.setColumns(10);
		
		deviation_gin_Number = new JTextField();
		deviation_gin_Number.setBounds(160, 140, 180, 20);
		contentPane.add(deviation_gin_Number);
		deviation_gin_Number.setColumns(10);
		
		deviation_item_Qty = new JTextField();
		deviation_item_Qty.setBounds(160, 180, 180, 20);
		contentPane.add(deviation_item_Qty);
		deviation_item_Qty.setColumns(10);
		
//		List<Material> materials = inputItemdata(".\\src\\com\\gmp\\labeling\\resources\\datafiles\\inv.csv");
		
		synchronized(this) {
			 materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));
		}		
		String[] sarray = new String[materials.size()];
		int i = 0;
		for(Material temp : materials) {
			sarray[i] = JsonParser.takeoffComma(temp.getItem_name());
			i++;
		}
		
		JComboBox deviation_item_Name = new JComboBox();
		deviation_item_Name.setModel(new DefaultComboBoxModel(sarray));
		deviation_item_Name.setMaximumRowCount(10);
		deviation_item_Name.setBounds(160, 100, 180, 20);
		deviation_item_Name.setSelectedItem(null);
		deviation_item_Name.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String value = (String) deviation_item_Name.getSelectedItem();
		        for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_name()).equals(value)) {
		        		deviation_item_Code.setText(JsonParser.takeoffComma(temp.getItem_code()));
		        		item_unit = JsonParser.takeoffComma(temp.getItem_unit());
		        		deviation_lbl_item_unit.setText(item_unit);
		        		break;
		        	}	        	
		        }
		    }});
		contentPane.add(deviation_item_Name);
		
		Button deviation_btn_Check = new Button("Check");
		deviation_btn_Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				String itemCode_value = deviation_item_Code.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		deviation_item_Name.setSelectedItem(JsonParser.takeoffComma(temp.getItem_name()));
		        		item_unit = JsonParser.takeoffComma(temp.getItem_unit());
		        		deviation_lbl_item_unit.setText(item_unit);
		        		deviation_item_Code.setText(itemCode_value);
		        		break;
		        	}	        	
		        }
			}
		});
		deviation_btn_Check.setBounds(340, 60, 60, 20);
		contentPane.add(deviation_btn_Check);
		
		JLabel deviation_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		deviation_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		deviation_copyright_developer.setBounds(300, 355, 170, 20);
		contentPane.add(deviation_copyright_developer);
		
		JLabel deviation_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		deviation_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		deviation_copyright_company.setBounds(200, 370, 280, 20);
		contentPane.add(deviation_copyright_company);
		
		JLabel deviation_lbl_printingQty = new JLabel("Printing Qty:");
		deviation_lbl_printingQty.setBounds(50, 260, 90, 20);
		contentPane.add(deviation_lbl_printingQty);
		deviation_printing_Qty = new JTextField();
		deviation_printing_Qty.setBounds(160, 260, 180, 20);
		contentPane.add(deviation_printing_Qty);
		deviation_printing_Qty.setColumns(10);		
		deviation_date = new JTextField();
		deviation_date.setBounds(160, 220, 180, 20);
		contentPane.add(deviation_date);
		deviation_date.setColumns(10);
		
		JLabel deviation_lbl_receivedDate = new JLabel("Issued date:");
		deviation_lbl_receivedDate.setBounds(50, 220, 90, 20);
		contentPane.add(deviation_lbl_receivedDate);
		
		Button deviation_btn_Info = new Button("i");
		deviation_btn_Info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date data correct format is dd/mm/yy. \n dd=>day  mm=>month  yy=>year \n eg. 20th May 2018 => 20/05/18 \n Leave it blank means using the system date."
					    );
			}
		});
		deviation_btn_Info.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		deviation_btn_Info.setForeground(new Color(0, 0, 0));
		deviation_btn_Info.setBackground(Color.LIGHT_GRAY);
		deviation_btn_Info.setBounds(340, 220, 20, 20);
		
		JButton deviation_btn_addToQueue = new JButton("Add To Queue");
		deviation_btn_addToQueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(deviation_item_Code.getText(), deviation_item_Name.getSelectedItem().toString(), deviation_gin_Number.getText(),
				   deviation_item_Qty.getText(), deviation_date.getText(),
				   deviation_printing_Qty.getText(), settings)) {
					tempList.clear();
					int printNumber = Integer.valueOf(deviation_printing_Qty.getText());
					int i = 0;
					while(i<printNumber) {
						DeviationLabel deviation = new DeviationLabel(deviation_item_Code.getText(), deviation_item_Name.getSelectedItem().toString(), deviation_gin_Number.getText(),
								deviation_item_Qty.getText(), item_unit, deviation_date.getText(), 
								settings.getSetting().getLogined_user());
						printqueue.addLabelToQueue(deviation);
						tempList.add(deviation);
						i++;
					}					
				}
				printCount.setText(String.valueOf(printqueue.getList().size()));				
			}
		});

		deviation_btn_addToQueue.setBounds(40, 320, 120, 20);
		contentPane.add(deviation_btn_Info);
		contentPane.add(deviation_btn_addToQueue);
		
		JButton deviation_btn_Back = new JButton("Close");
		deviation_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		deviation_btn_Back.setBounds(340, 320, 120, 20);
		contentPane.add(deviation_btn_Back);
		
		JButton deviation_btn_update = new JButton("Update");
		deviation_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings, "stk.csv", "materiallistpath");
				dispose();
			}
		});
		deviation_btn_update.setBounds(190, 320, 120, 20);
		contentPane.add(deviation_btn_update);
		
	}
	
	public boolean dataValidation(String itemCode, String itemName, String ginNumber, String quantity, String receivedDate, String printingCount, Settings settings) {
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
		}
		
		try {
			Float.valueOf(quantity);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item Qty missing or Invalid input.",
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
	
	public List<Material> inputItemdata(String path) {
		String csvFile = path;
        String line = "";
        String cvsSplitBy = ",";
        List<Material> materials = new ArrayList<Material>();

        try{
        	BufferedReader br = new BufferedReader(new FileReader(csvFile));
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
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return materials;
	}

}
