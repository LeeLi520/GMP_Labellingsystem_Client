package com.gmp.labeling.userIOs;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
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

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Product;
import com.gmp.labeling.printModels.FPQuarantineLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.SwingConstants;

public class FPQuarantineLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField FPQuarantine_itemCode;
	private JTextField FPQuarantine_batch;
	private JTextField FPQuarantine_startFrom;
	private JTextField FPQuarantine_labelPerPallet;
	private JTextField FPQuarantine_palletQuantity;
	private List<Product> products;
	private JTextField FPQuarantine_printQuantity;
	private String itemquantity;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FPQuarantineLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel FPQuarantine_lbl_title = new JLabel("FP Quarantine Label Printing");
		FPQuarantine_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		FPQuarantine_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		FPQuarantine_lbl_title.setBounds(60, 30, 300, 30);
		contentPane.add(FPQuarantine_lbl_title);
		
		JLabel FPQuarantine_lbl_labelType = new JLabel("Label Type:");
		FPQuarantine_lbl_labelType.setBounds(50, 80, 80, 20);
		contentPane.add(FPQuarantine_lbl_labelType);
		
		products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));

		String[] productNameArray = new String[products.size()];
		int i = 0;
		for(Product p : products) {
			if(p.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = p.getProductName();
				i++;
			}			
		}
		
		Canvas canvas = new Canvas();
		canvas.setBackground(new Color(255, 255, 0));
		canvas.setBounds(335, 80, 30, 20);
		contentPane.add(canvas);
		
		JComboBox FPQuarantine_labelType = new JComboBox();
		FPQuarantine_labelType.setModel(new DefaultComboBoxModel(new String[] {"Pharmaceutical", "Non-Pharmaceutical"}));
		FPQuarantine_labelType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) FPQuarantine_labelType.getSelectedItem();
				switch(value) {
				case "Pharmaceutical":
					canvas.setBackground(new Color(255, 255, 0));
					break;
				case "Non-Pharmaceutical":
					canvas.setBackground(new Color(255, 140, 0));
					break;
				}
			}
		});
		
		FPQuarantine_labelType.setBounds(130, 80, 200, 20);
		contentPane.add(FPQuarantine_labelType);		
		
		
		JLabel FPQuarantine_lbl_itemCode = new JLabel("Item Code:");
		FPQuarantine_lbl_itemCode.setBounds(50, 120, 80, 20);
		contentPane.add(FPQuarantine_lbl_itemCode);
		
		FPQuarantine_itemCode = new JTextField();
		FPQuarantine_itemCode.setBounds(130, 120, 200, 20);
		contentPane.add(FPQuarantine_itemCode);
		FPQuarantine_itemCode.setColumns(10);
		
		JLabel FPQuarantine_lbl_productName = new JLabel("Product:");
		FPQuarantine_lbl_productName.setBounds(50, 160, 80, 20);
		contentPane.add(FPQuarantine_lbl_productName);
		
		JComboBox FPQuarantine_ProductName = new JComboBox();
		FPQuarantine_ProductName.setBounds(130, 160, 200, 20);
		FPQuarantine_ProductName.setModel(new DefaultComboBoxModel(productNameArray));
		FPQuarantine_ProductName.setMaximumRowCount(10);
		FPQuarantine_ProductName.setSelectedItem(null);
		FPQuarantine_ProductName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) FPQuarantine_ProductName.getSelectedItem();
			        for(Product product : products) {
			        	if(product.getProductName().equals(value)) {
			        		FPQuarantine_itemCode.setText(product.getItemCode());
			        		product.getCompanyName();
			        		break;
			        	}	        	
			        }
			}
		});
		contentPane.add(FPQuarantine_ProductName);
		
		JLabel FPQuarantine_lbl_batch = new JLabel("Batch:");
		FPQuarantine_lbl_batch.setBounds(50, 200, 80, 20);
		contentPane.add(FPQuarantine_lbl_batch);
		
		FPQuarantine_batch = new JTextField();
		FPQuarantine_batch.setBounds(130, 200, 200, 20);
		contentPane.add(FPQuarantine_batch);
		FPQuarantine_batch.setColumns(10);
		
		JLabel FPQuarantine_lbl_printQuantity = new JLabel("Print Qty:");
		FPQuarantine_lbl_printQuantity.setBounds(50, 240, 80, 20);
		contentPane.add(FPQuarantine_lbl_printQuantity);
		
		FPQuarantine_printQuantity = new JTextField();
		FPQuarantine_printQuantity.setBounds(130, 240, 200, 20);
		contentPane.add(FPQuarantine_printQuantity);
		FPQuarantine_printQuantity.setColumns(10);
		
		JLabel FPQuarantine_lbl_palletFrom = new JLabel("Pallet No Start From:");
		FPQuarantine_lbl_palletFrom.setBounds(50, 280, 120, 20);
		contentPane.add(FPQuarantine_lbl_palletFrom);
		
		FPQuarantine_startFrom = new JTextField();
		FPQuarantine_startFrom.setBounds(170, 280, 50, 20);
		contentPane.add(FPQuarantine_startFrom);
		FPQuarantine_startFrom.setColumns(10);
		
		JLabel FPQuarantine_lbl_labelPerPallet = new JLabel("Labels Per Pallet:");
		FPQuarantine_lbl_labelPerPallet.setBounds(230, 280, 100, 20);
		contentPane.add(FPQuarantine_lbl_labelPerPallet);
		
		FPQuarantine_labelPerPallet = new JTextField();
		FPQuarantine_labelPerPallet.setBounds(330, 280, 50, 20);
		contentPane.add(FPQuarantine_labelPerPallet);
		FPQuarantine_labelPerPallet.setColumns(10);
		
		JLabel FPQuarantine_lbl_palletQuantity = new JLabel("Pallet Quantity:");
		FPQuarantine_lbl_palletQuantity.setBounds(50, 320, 100, 20);
		contentPane.add(FPQuarantine_lbl_palletQuantity);
		
		FPQuarantine_palletQuantity = new JTextField();
		FPQuarantine_palletQuantity.setBounds(169, 320, 160, 20);
		contentPane.add(FPQuarantine_palletQuantity);
		FPQuarantine_palletQuantity.setColumns(10);
		
		JButton FPQuarantine_btn_settings = new JButton("Settings");
		FPQuarantine_btn_settings.setFont(new Font("Tahoma", Font.PLAIN, 10));
		FPQuarantine_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		FPQuarantine_btn_settings.setBounds(130, 360, 80, 20);
		contentPane.add(FPQuarantine_btn_settings);
		
		Button FPQuarantine_btn_check = new Button("Check");
		FPQuarantine_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = FPQuarantine_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Product product : products) {
		        	if(product.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		FPQuarantine_ProductName.setSelectedItem(product.getProductName());
		        		FPQuarantine_itemCode.setText(product.getItemCode());
		        		product.getCompanyName(); 
		        		itemquantity = product.getItemQuantity();
		        		break;
		        	}	        	
		        }
			}
		});
		FPQuarantine_btn_check.setBounds(330, 120, 60, 20);
		contentPane.add(FPQuarantine_btn_check);
		
		JButton FPQuarantine_btn_print = new JButton("Print");
		FPQuarantine_btn_print.setFont(new Font("Tahoma", Font.PLAIN, 10));
		FPQuarantine_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(FPQuarantine_itemCode.getText(), FPQuarantine_ProductName.getSelectedItem().toString(), FPQuarantine_batch.getText(), FPQuarantine_startFrom.getText(), FPQuarantine_labelPerPallet.getText(), FPQuarantine_palletQuantity.getText(), settings)){
					int startFrom = Integer.valueOf(FPQuarantine_startFrom.getText());
					for(int quantity = 0; quantity < Integer.valueOf(FPQuarantine_palletQuantity.getText()); quantity ++) {						
						Label label = new FPQuarantineLabel(FPQuarantine_labelType.getSelectedItem().toString(), FPQuarantine_itemCode.getText(), FPQuarantine_ProductName.getSelectedItem().toString(), FPQuarantine_batch.getText(), itemquantity,  String.valueOf(startFrom));
						for(int labelPerPallet=0; labelPerPallet<Integer.valueOf(FPQuarantine_labelPerPallet.getText()); labelPerPallet++) {
							printqueue.addLabelToQueue(label);
						}						
						startFrom++;
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
		FPQuarantine_btn_print.setBounds(230, 360, 80, 20);
		contentPane.add(FPQuarantine_btn_print);
		
		JButton FPQuarantine_btn_back = new JButton("Back");
		FPQuarantine_btn_back.setFont(new Font("Tahoma", Font.PLAIN, 10));
		FPQuarantine_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		FPQuarantine_btn_back.setBounds(330, 360, 80, 20);
		contentPane.add(FPQuarantine_btn_back);
		
		JLabel label = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(144, 400, 280, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Designed and Implemented by Lee.L");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(244, 385, 170, 20);
		contentPane.add(label_1);
		
		
		JButton FPQuarantine_btn_update = new JButton("UpdateDB");
		FPQuarantine_btn_update.setFont(new Font("Tahoma", Font.PLAIN, 10));
		FPQuarantine_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				FPDBEditingIO fpdb  = new FPDBEditingIO(settings, printqueue);
				fpdb.setLocationRelativeTo(null);
				fpdb.setVisible(true);
				dispose();
			}
		});
		FPQuarantine_btn_update.setBounds(30, 360, 80, 20);
		contentPane.add(FPQuarantine_btn_update);
		

		

	}
	
	public boolean dataValidation(String itemCode, String itemName, String batch, String startFrom, String labelPerPallet, String quantity, Settings settings) {
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
		
		if(batch.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Batch number missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			if(batch.length()!=5) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Batch number invalid.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		
		try {
			Integer.parseInt(startFrom);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Start From value missing or Invalid input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}		
		try {
			Integer.parseInt(labelPerPallet);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Label per pallet value missing or Invalid input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			Float.parseFloat(quantity);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item Qty missing or Invalid input.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}		

		return true;
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
