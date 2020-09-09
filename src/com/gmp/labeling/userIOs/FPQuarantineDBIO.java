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

public class FPQuarantineDBIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7005667228463264340L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton fpQsettings_btn_back;
	private JButton fpQsettings_btn_add;
	private JButton bdFPSettings_btn_delete;
	private JButton fpQsettings_btn_save;
	private JLabel fpQsettings_lbl_copyright_1;
	private JLabel fpQsettings_lbl_copyright_2;
	private JTextField fpQsettings_itemCode;
	private JTextField fpQsettings_productName;
	private JLabel fpQsettings_lbl_quantity;
	private JTextField fpQsettings_quantity;
	private JLabel dbFDSettings_lbl_caution;
	

	public FPQuarantineDBIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
				
		
//		List<Product> products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		
		
		List<Product> products = inputItemdata(connection.loadProperties().getProperty("fpquarantineList"));
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel fpQsettings_lbl_title = new JLabel("FQ Quarantine DB");
		fpQsettings_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		fpQsettings_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		fpQsettings_lbl_title.setBounds(148, 28, 280, 30);
		contentPane.add(fpQsettings_lbl_title);
		
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
								
		fpQsettings_btn_back = new JButton("Back");
		fpQsettings_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				FPQuarantineLabelIO fpQuarantinelabel = new FPQuarantineLabelIO(settings, printqueue);
		        	  settings.setComponentPage(fpQuarantinelabel);
		        	  fpQuarantinelabel.setLocationRelativeTo(null);
		        	  fpQuarantinelabel.setVisible(true);
		        	  dispose();
			}
		});
		fpQsettings_btn_back.setBounds(440, 382, 90, 20);
		contentPane.add(fpQsettings_btn_back);
		
		fpQsettings_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		fpQsettings_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		fpQsettings_lbl_copyright_1.setBounds(394, 413, 170, 20);
		contentPane.add(fpQsettings_lbl_copyright_1);
		
		fpQsettings_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		fpQsettings_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		fpQsettings_lbl_copyright_2.setBounds(294, 428, 280, 20);
		contentPane.add(fpQsettings_lbl_copyright_2);
		scrollPane.setViewportView(table);
		
		JLabel fpQsettings_lbl_itemCode = new JLabel("Item Code:");
		fpQsettings_lbl_itemCode.setBounds(50, 270, 100, 20);
		contentPane.add(fpQsettings_lbl_itemCode);
		
		fpQsettings_itemCode = new JTextField();
		fpQsettings_itemCode.setBounds(148, 270, 320, 20);
		contentPane.add(fpQsettings_itemCode);
		fpQsettings_itemCode.setColumns(10);
		
		JLabel fpQsettings_lbl_productName = new JLabel("Product Name:");
		fpQsettings_lbl_productName.setBounds(50, 305, 100, 20);
		contentPane.add(fpQsettings_lbl_productName);
		
		fpQsettings_productName = new JTextField();
		fpQsettings_productName.setBounds(148, 305, 320, 20);
		contentPane.add(fpQsettings_productName);
		fpQsettings_productName.setColumns(10);
		
		fpQsettings_lbl_quantity = new JLabel("Quantity:");
		fpQsettings_lbl_quantity.setBounds(50, 340, 100, 20);
		contentPane.add(fpQsettings_lbl_quantity);
		
		fpQsettings_quantity = new JTextField();
		fpQsettings_quantity.setColumns(10);
		fpQsettings_quantity.setBounds(148, 340, 320, 20);
		contentPane.add(fpQsettings_quantity);
		
		fpQsettings_btn_add = new JButton("Add");
		fpQsettings_btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(fpQsettings_itemCode.getText(), fpQsettings_productName.getText(), fpQsettings_quantity.getText(), settings)) {
					@SuppressWarnings("unused")
					DefaultTableModel tempModel = (DefaultTableModel) table.getModel();
					Object[] row = {fpQsettings_itemCode.getText(), fpQsettings_productName.getText(), fpQsettings_quantity.getText()};
					tempModel.addRow(row);
					table.setModel(tempModel);
					dbFDSettings_lbl_caution.setText("You have unsaved data! Without saving you will lost your entry.");
				}
			}
		});
		
		fpQsettings_btn_add.setBounds(50, 382, 90, 20);
		contentPane.add(fpQsettings_btn_add);
		
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
			
		
		bdFPSettings_btn_delete.setBounds(180, 382, 90, 20);
		contentPane.add(bdFPSettings_btn_delete);
		
		fpQsettings_btn_save = new JButton("Save");
		fpQsettings_btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(updateDB(connection.loadProperties().getProperty("fpquarantineList"))) {
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
		
		fpQsettings_btn_save.setBounds(310, 382, 90, 20);
		contentPane.add(fpQsettings_btn_save);
		
		dbFDSettings_lbl_caution = new JLabel("");
		dbFDSettings_lbl_caution.setFont(new Font("Tahoma", Font.PLAIN, 9));
		dbFDSettings_lbl_caution.setForeground(new Color(0, 100, 0));
		dbFDSettings_lbl_caution.setBounds(128, 248, 400, 15);
		contentPane.add(dbFDSettings_lbl_caution);
	}

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
		}else if(productName.length() > 86) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Product name too long. (over 86 chars not allowed)",
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
			String data ="Item Code"+','+"Product Name"+','+"Quantity P/C"+'\n';
	        for(Product product : realList) {
		        data += product.getItemCode()+','+product.getProductName()+','+product.getItemQuantity()+'\n';
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
            	if(line.contains("Item Code") && line.contains("Product Name") ) {
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
