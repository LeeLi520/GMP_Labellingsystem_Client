package com.gmp.labeling.userIOs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Product;
import com.gmp.labeling.printModels.PrintingQueue;

public class LiquidDBEditingIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4348782434484991541L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton dbFPSettings_btn_add;
	private JButton bdFPSettings_btn_delete;
	private JButton dbFPSettings_btn_save;
	private JButton dbFPSettings_btn_back;
	private JLabel label;
	private JLabel label_1;
	private JTextField dbFPSettings_itemCode;
	private JTextField dbFPSettings_productName;
	private JLabel dbFPSettings_lbl_quantity;
	private JTextField dbFPSettings_quantity;
	private JLabel dbFDSettings_lbl_caution;
	private JTextField dbFPSettings_companyName;

	public LiquidDBEditingIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		
		
		
		
//		List<Product> products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		
		
		List<Product> products = inputItemdata(connection.loadProperties().getProperty("liquiddbList"));
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Liquid Shipper DB Settings");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(148, 28, 300, 30);
		contentPane.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		
		
		scrollPane.setBounds(30, 68, 520, 180);
		contentPane.add(scrollPane);
		
		table = new JTable();
		Object[] columns = {"Company", "Item Code", "Product Name", "Quantity P/C"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(Product p: products) {
			Object[] row = {p.getCompanyName(), p.getItemCode(), p.getProductName(), p.getItemQuantity()};
			model.addRow(row);
		}
		
		table.setModel(model);
		table.setRowHeight(20);
								
		dbFPSettings_btn_back = new JButton("Back");
		dbFPSettings_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LiquidShipperLabelIO liquidshipperlabel = new LiquidShipperLabelIO(settings, printqueue);
				settings.setComponentPage(liquidshipperlabel);
				liquidshipperlabel.setLocationRelativeTo(null);
				liquidshipperlabel.setVisible(true);
	        	dispose();
			}
		});
		dbFPSettings_btn_back.setBounds(440, 410, 90, 20);
		contentPane.add(dbFPSettings_btn_back);
		
		label = new JLabel("Designed and Implemented by Lee.L");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(394, 433, 170, 20);
		contentPane.add(label);
		
		label_1 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(294, 448, 280, 20);
		contentPane.add(label_1);
		scrollPane.setViewportView(table);
		
		JLabel dbFPSettings_lbl_itemCode = new JLabel("Item Code:");
		dbFPSettings_lbl_itemCode.setBounds(50, 298, 100, 20);
		contentPane.add(dbFPSettings_lbl_itemCode);
		
		dbFPSettings_itemCode = new JTextField();
		dbFPSettings_itemCode.setBounds(148, 298, 320, 20);
		contentPane.add(dbFPSettings_itemCode);
		dbFPSettings_itemCode.setColumns(10);
		
		JLabel dbFPSettings_lbl_productName = new JLabel("Product Name:");
		dbFPSettings_lbl_productName.setBounds(50, 333, 100, 20);
		contentPane.add(dbFPSettings_lbl_productName);
		
		dbFPSettings_productName = new JTextField();
		dbFPSettings_productName.setBounds(148, 333, 320, 20);
		contentPane.add(dbFPSettings_productName);
		dbFPSettings_productName.setColumns(10);
		
		dbFPSettings_lbl_quantity = new JLabel("Quantity:");
		dbFPSettings_lbl_quantity.setBounds(50, 368, 100, 20);
		contentPane.add(dbFPSettings_lbl_quantity);
		
		dbFPSettings_quantity = new JTextField();
		dbFPSettings_quantity.setColumns(10);
		dbFPSettings_quantity.setBounds(148, 368, 320, 20);
		contentPane.add(dbFPSettings_quantity);
		
		dbFPSettings_btn_add = new JButton("Add");
		dbFPSettings_btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(dbFPSettings_itemCode.getText(), dbFPSettings_productName.getText(), dbFPSettings_quantity.getText(), settings)) {
					@SuppressWarnings("unused")
					String extraInfomessage = null;
					DefaultTableModel tempModel = (DefaultTableModel) table.getModel();
					Object[] row = {dbFPSettings_companyName.getText() ,dbFPSettings_itemCode.getText(), dbFPSettings_productName.getText(), dbFPSettings_quantity.getText()};
					tempModel.addRow(row);
					table.setModel(tempModel);
					dbFDSettings_lbl_caution.setText("You have unsaved data! Without saving you will lost your entry.");
				}
			}
		});
		
		dbFPSettings_btn_add.setBounds(50, 410, 90, 20);
		contentPane.add(dbFPSettings_btn_add);
		
		bdFPSettings_btn_delete = new JButton("Delete");
		
		bdFPSettings_btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
			
				int[] rows = table.getSelectedRows();
				if(rows.length<= 0) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "No targeted item",
						    "Caution",
						    JOptionPane.INFORMATION_MESSAGE);
				}else {
					for(int i=0;i<rows.length;i++){
						   model.removeRow(rows[i]-i);	    
					}					
				}
				dbFDSettings_lbl_caution.setText("You have unsaved data! Without saving you will lose your entry.");
			}
		});
			
		
		bdFPSettings_btn_delete.setBounds(180, 410, 90, 20);
		contentPane.add(bdFPSettings_btn_delete);
		
		dbFPSettings_btn_save = new JButton("Save");
		dbFPSettings_btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(updateDB(connection.loadProperties().getProperty("liquiddbList"))) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Saved!",
						    "Caution",
						    JOptionPane.INFORMATION_MESSAGE);
					dbFDSettings_lbl_caution.setText("");
//				if(updateDB(connection.loadProperties().getProperty("finishedProductList"))) {
//					JOptionPane.showMessageDialog(settings.getComponentPage(),
//						    "Saved!",
//						    "Caution",
//						    JOptionPane.INFORMATION_MESSAGE);
//					dbFDSettings_lbl_caution.setText("");
				}else {
					dbFDSettings_lbl_caution.setText("Error occurred while saving the data.");
					dbFDSettings_lbl_caution.setForeground(Color.RED);
				}				
			}
		});
		
		dbFPSettings_btn_save.setBounds(310, 410, 90, 20);
		contentPane.add(dbFPSettings_btn_save);
		
		dbFDSettings_lbl_caution = new JLabel("");
		dbFDSettings_lbl_caution.setFont(new Font("Tahoma", Font.PLAIN, 9));
		dbFDSettings_lbl_caution.setForeground(new Color(0, 100, 0));
		dbFDSettings_lbl_caution.setBounds(128, 248, 400, 15);
		contentPane.add(dbFDSettings_lbl_caution);
		
		JLabel dbFPSettings_lbl_companyName = new JLabel("Company:");
		dbFPSettings_lbl_companyName.setBounds(50, 263, 100, 20);
		contentPane.add(dbFPSettings_lbl_companyName);
		
		dbFPSettings_companyName = new JTextField();
		dbFPSettings_companyName.setBounds(148, 263, 320, 20);
		contentPane.add(dbFPSettings_companyName);
		dbFPSettings_companyName.setColumns(10);
	}
	
	@SuppressWarnings("deprecation")
	public boolean dataValidation(String itemCode, String productName, String quantity, Settings settings) {
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(productName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Product name missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		int upperCaseCount = 0;
		int lowerCaseCount = 0;
		int numberCount = 0;
		int spaceCount = 0;
		for(int i = 0; i < productName.length(); i++) {
			if(Character.isUpperCase(productName.charAt(i))) {
				upperCaseCount++;
			}
			if(Character.isLowerCase(productName.charAt(i))) {
				lowerCaseCount++;
			}
			
			if(Character.isDigit(productName.charAt(i))) {
				numberCount++;
			}
			if(Character.isSpace(productName.charAt(i))) {
				spaceCount++;
			}
		}
		double upperWeight = 1.0/43.0;
		double lowerWeight = 1.0/50.0;
		double numberWeight = 1.0/50.0;
		double spaceWeight = 1.0/99.0;
		double total = (upperCaseCount*upperWeight)+(lowerCaseCount*lowerWeight)+(numberCount*numberWeight)+(spaceCount*spaceWeight);
		if(total>1.05) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Product name too long",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(quantity.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Quantity missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean updateDB(String path) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		List<Product> realList = new ArrayList<Product>();
		for(int row = 0; row < model.getRowCount(); row++) {
			Product tempP = new Product();
			tempP.setCompanyName(model.getValueAt(row, 0).toString());
			tempP.setItemCode(model.getValueAt(row, 1).toString());
			tempP.setProductName(model.getValueAt(row, 2).toString());
			tempP.setItemQuantity(model.getValueAt(row, 3).toString());
			realList.add(tempP);
		}
		FileWriter fw;
		try {
			String data ="Company"+','+ "Item Code"+','+"Product Name"+','+"Quantity P/C"+'\n';
	        for(Product product : realList) {
		        data += product.getCompanyName()+','+product.getItemCode()+','+product.getProductName()+','+product.getItemQuantity()+ '\n';
			}
			fw = new FileWriter(new File(path),false);
			fw.write(data);
			fw.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}       
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
                products.add(product);
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
	}
}
