package com.gmp.labeling.userIOs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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

public class SachetMilkDBIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6813287432830778255L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton sachetMDB_btn_add;
	private JButton sachetMDB_btn_delete;
	private JButton sachetMDB_btn_save;
	private JButton sachetMDB_btn_back;
	private JLabel label;
	private JLabel label_1;
	private JTextField sachetMDB_itemCode;
	private JTextField sachetMDB_productName;
	private JLabel sachetMDB_lbl_quantity;
	private JTextField sachetMDB_quantity;
	private JLabel dbFDSettings_lbl_caution;

	public SachetMilkDBIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		
		
		
		
//		List<Product> products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		
		
		List<Product> products = inputItemdata(connection.loadProperties().getProperty("sachetMDB"));
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel sachetMDB_lbl_title = new JLabel("Sachet Milk Powder DB Settings");
		sachetMDB_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		sachetMDB_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		sachetMDB_lbl_title.setBounds(128, 28, 340, 30);
		contentPane.add(sachetMDB_lbl_title);
		
		scrollPane = new JScrollPane();
		
		
		scrollPane.setBounds(30, 68, 520, 180);
		contentPane.add(scrollPane);
		
		table = new JTable();
		Object[] columns = {"Item Code", "Product Name", "Quantity P/C"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(Product p: products) {
			Object[] row = {p.getItemCode(), p.getProductName(), p.getItemQuantity()};
			model.addRow(row);
		}
		
		table.setModel(model);
		table.setRowHeight(20);
								
		sachetMDB_btn_back = new JButton("Back");
		sachetMDB_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SachetMilkPowderLabelIO sachetmilkpowder = new SachetMilkPowderLabelIO(settings, printqueue);
				settings.setComponentPage(sachetmilkpowder);
				sachetmilkpowder.setLocationRelativeTo(null);
				sachetmilkpowder.setVisible(true);
	        	dispose();
			}
		});
		sachetMDB_btn_back.setBounds(440, 368, 90, 20);
		contentPane.add(sachetMDB_btn_back);
		
		label = new JLabel("Designed and Implemented by Lee.L");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(394, 403, 170, 20);
		contentPane.add(label);
		
		label_1 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(294, 418, 280, 20);
		contentPane.add(label_1);
		scrollPane.setViewportView(table);
		
		JLabel sachetMDB_lbl_itemCode = new JLabel("Item Code:");
		sachetMDB_lbl_itemCode.setBounds(50, 263, 100, 20);
		contentPane.add(sachetMDB_lbl_itemCode);
		
		sachetMDB_itemCode = new JTextField();
		sachetMDB_itemCode.setBounds(148, 263, 320, 20);
		contentPane.add(sachetMDB_itemCode);
		sachetMDB_itemCode.setColumns(10);
		
		JLabel sachetMDB_lbl_productName = new JLabel("Product Name:");
		sachetMDB_lbl_productName.setBounds(50, 298, 100, 20);
		contentPane.add(sachetMDB_lbl_productName);
		
		sachetMDB_productName = new JTextField();
		sachetMDB_productName.setBounds(148, 298, 320, 20);
		contentPane.add(sachetMDB_productName);
		sachetMDB_productName.setColumns(10);
		
		sachetMDB_lbl_quantity = new JLabel("Quantity:");
		sachetMDB_lbl_quantity.setBounds(50, 333, 100, 20);
		contentPane.add(sachetMDB_lbl_quantity);
		
		sachetMDB_quantity = new JTextField();
		sachetMDB_quantity.setColumns(10);
		sachetMDB_quantity.setBounds(148, 333, 320, 20);
		contentPane.add(sachetMDB_quantity);
		
		sachetMDB_btn_add = new JButton("Add");
		sachetMDB_btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(sachetMDB_itemCode.getText(), sachetMDB_productName.getText(), sachetMDB_quantity.getText(), settings)) {
					@SuppressWarnings("unused")
					String extraInfomessage = null;
					DefaultTableModel tempModel = (DefaultTableModel) table.getModel();
					Object[] row = {sachetMDB_itemCode.getText(), sachetMDB_productName.getText(), sachetMDB_quantity.getText()};
					tempModel.addRow(row);
					table.setModel(tempModel);
					dbFDSettings_lbl_caution.setText("You have unsaved data! Without saving you will lost your entry.");
				}
			}
		});
		
		sachetMDB_btn_add.setBounds(50, 368, 90, 20);
		contentPane.add(sachetMDB_btn_add);
		
		sachetMDB_btn_delete = new JButton("Delete");
		
		sachetMDB_btn_delete.addActionListener(new ActionListener() {
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
			
		
		sachetMDB_btn_delete.setBounds(180, 368, 90, 20);
		contentPane.add(sachetMDB_btn_delete);
		
		sachetMDB_btn_save = new JButton("Save");
		sachetMDB_btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(updateDB(connection.loadProperties().getProperty("sachetMDB"))) {
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
		
		sachetMDB_btn_save.setBounds(310, 368, 90, 20);
		contentPane.add(sachetMDB_btn_save);
		
		dbFDSettings_lbl_caution = new JLabel("");
		dbFDSettings_lbl_caution.setFont(new Font("Tahoma", Font.PLAIN, 9));
		dbFDSettings_lbl_caution.setForeground(new Color(0, 100, 0));
		dbFDSettings_lbl_caution.setBounds(128, 248, 400, 15);
		contentPane.add(dbFDSettings_lbl_caution);
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
			tempP.setItemCode(model.getValueAt(row, 0).toString());
			tempP.setProductName(model.getValueAt(row, 1).toString());
			tempP.setItemQuantity(model.getValueAt(row, 2).toString());
			realList.add(tempP);
		}
		FileWriter fw;
		try {
			String data = "Item Code"+','+"Product Name"+','+"Quantity P/C"+'\n';
	        for(Product product : realList) {
		        data += product.getItemCode()+','+product.getProductName()+','+product.getItemQuantity()+ '\n';
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
            	if(line.contains("Item Code")) {
            		//first line dont input
            	}else {
                String[] items = line.split(cvsSplitBy);
                Product product = new Product();
                product.setItemCode(items[0]);
                product.setProductName(items[1]);
                product.setItemQuantity(items[2]);
                products.add(product);
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
	}
}
