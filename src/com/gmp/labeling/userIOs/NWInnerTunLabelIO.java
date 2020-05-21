package com.gmp.labeling.userIOs;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.TUNLabel;
import com.gmp.labeling.printModels.InnerTunLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class NWInnerTunLabelIO extends JFrame {

	private JPanel contentPane;
	private JTextField nw_innerTun_batch;
	private JTextField nw_innerTun_expiryDate;
	private JTextField nw_innerTun_labelsPerCarton;
	private JTextField nw_innerTun_cartonQty;
	private JTextField nw_innerTun_itemCode;
	private List<TUNLabel> labels;
	private List<TUNLabel> innerTunList;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NWInnerTunLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		labels = inputItemdata(connection.loadProperties().getProperty("tunlabelList"));
		
		innerTunList = new ArrayList<TUNLabel>();
		
		for(TUNLabel label : labels) {
			if(label.getLabelType().equals("Inner TUN")) {
				innerTunList.add(label);
			}
		}
		
		String[] productNameArray = new String[innerTunList.size()];
		int i = 0;
		for(TUNLabel l : innerTunList) {
			if(l.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = l.getProductName();
				i++;
			}			
		}
		
		JLabel nw_innerTun_lbl_title = new JLabel("NW Inner Tun Label");
		nw_innerTun_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		nw_innerTun_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		nw_innerTun_lbl_title.setBounds(90, 18, 300, 30);
		contentPane.add(nw_innerTun_lbl_title);
		
		JLabel nw_innerTun_lbl_intemCode = new JLabel("Item code:");
		nw_innerTun_lbl_intemCode.setBounds(50, 60, 90, 20);
		contentPane.add(nw_innerTun_lbl_intemCode);
		
		JLabel nw_innerTun_lbl_itemName = new JLabel("Item name:");
		nw_innerTun_lbl_itemName.setBounds(50, 100, 90, 20);
		contentPane.add(nw_innerTun_lbl_itemName);
		
		JLabel nw_innerTun_lbl_qty = new JLabel("Qty:");
		nw_innerTun_lbl_qty.setBounds(50, 180, 40, 20);
		contentPane.add(nw_innerTun_lbl_qty);
		
		JLabel nw_innerTun_qty = new JLabel("");
		nw_innerTun_qty.setForeground(Color.RED);
		nw_innerTun_qty.setBounds(90, 180, 50, 20);
		contentPane.add(nw_innerTun_qty);
		
		JLabel nw_innerTun_lbl_pcCode = new JLabel("PC code:");
		nw_innerTun_lbl_pcCode.setBounds(50, 140, 70, 20);
		contentPane.add(nw_innerTun_lbl_pcCode);
		
		JLabel nw_innerTun_pcCode = new JLabel("");
		nw_innerTun_pcCode.setForeground(Color.RED);
		nw_innerTun_pcCode.setHorizontalAlignment(SwingConstants.CENTER);
		nw_innerTun_pcCode.setBounds(120, 140, 90, 20);
		contentPane.add(nw_innerTun_pcCode);
		
		JLabel nw_innerTun_lbl_barcode = new JLabel("Barcode:");
		nw_innerTun_lbl_barcode.setBounds(210, 140, 70, 20);
		contentPane.add(nw_innerTun_lbl_barcode);
		
		JLabel nw_innerTun_barcode = new JLabel("");
		nw_innerTun_barcode.setForeground(Color.RED);
		nw_innerTun_barcode.setHorizontalAlignment(SwingConstants.CENTER);
		nw_innerTun_barcode.setBounds(280, 140, 150, 20);
		contentPane.add(nw_innerTun_barcode);
		
		JLabel nw_innerTun_lbl_grossWeight = new JLabel("Gross weight:");
		nw_innerTun_lbl_grossWeight.setBounds(210, 180, 90, 20);
		contentPane.add(nw_innerTun_lbl_grossWeight);
		
		JLabel nw_innerTun_grossWeight = new JLabel("");
		nw_innerTun_grossWeight.setForeground(Color.RED);
		nw_innerTun_grossWeight.setHorizontalAlignment(SwingConstants.CENTER);
		nw_innerTun_grossWeight.setBounds(300, 180, 80, 20);
		contentPane.add(nw_innerTun_grossWeight);
		
		JLabel nw_innerTun_lbl_batch = new JLabel("Batch:");
		nw_innerTun_lbl_batch.setBounds(50, 220, 90, 20);
		contentPane.add(nw_innerTun_lbl_batch);
		
		nw_innerTun_batch = new JTextField();
		nw_innerTun_batch.setBounds(140, 220, 180, 20);
		contentPane.add(nw_innerTun_batch);
		nw_innerTun_batch.setColumns(10);
		
		JLabel nw_innerTun_lbl_expiryDate = new JLabel("Expiry date:");
		nw_innerTun_lbl_expiryDate.setBounds(50, 260, 90, 20);
		contentPane.add(nw_innerTun_lbl_expiryDate);
		
		nw_innerTun_expiryDate = new JTextField();
		nw_innerTun_expiryDate.setBounds(140, 260, 180, 20);
		contentPane.add(nw_innerTun_expiryDate);
		nw_innerTun_expiryDate.setColumns(10);
		
		JLabel nw_innerTun_lbl_labelsPerCarton = new JLabel("Labels Per Carton:");
		nw_innerTun_lbl_labelsPerCarton.setBounds(50, 300, 110, 20);
		contentPane.add(nw_innerTun_lbl_labelsPerCarton);
		
		nw_innerTun_labelsPerCarton = new JTextField();
		nw_innerTun_labelsPerCarton.setToolTipText("def 1");
		nw_innerTun_labelsPerCarton.setColumns(10);
		nw_innerTun_labelsPerCarton.setBounds(160, 300, 160, 20);
		contentPane.add(nw_innerTun_labelsPerCarton);
		
		JLabel nw_innerTun_lbl_cartonQty = new JLabel("Carton Quantity:");
		nw_innerTun_lbl_cartonQty.setBounds(50, 340, 100, 20);
		contentPane.add(nw_innerTun_lbl_cartonQty);
		
		nw_innerTun_cartonQty = new JTextField();
		nw_innerTun_cartonQty.setColumns(10);
		nw_innerTun_cartonQty.setBounds(150, 340, 170, 20);
		contentPane.add(nw_innerTun_cartonQty);
		
		nw_innerTun_itemCode = new JTextField();
		nw_innerTun_itemCode.setBounds(140, 60, 180, 20);
		contentPane.add(nw_innerTun_itemCode);
		nw_innerTun_itemCode.setColumns(10);
		
		JComboBox nw_innerTun_itemName = new JComboBox();		
		nw_innerTun_itemName.setModel(new DefaultComboBoxModel(productNameArray));
		nw_innerTun_itemName.setSelectedIndex(-1);
		nw_innerTun_itemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) nw_innerTun_itemName.getSelectedItem();
		        for(TUNLabel label : innerTunList) {
		        	if(label.getProductName().equals(value)) {
		        		nw_innerTun_itemCode.setText(label.getItemCode());
		        		nw_innerTun_pcCode.setText(label.getPcCode());
		        		nw_innerTun_qty.setText(label.getQuantity());
		        		nw_innerTun_barcode.setText(label.getBarcode());
		        		nw_innerTun_grossWeight.setText(label.getGrossWeight());
		        		break;
		        	}	        	
		        }
			}
		});
		nw_innerTun_itemName.setBounds(140, 100, 200, 20);
		contentPane.add(nw_innerTun_itemName);
		
		JButton nw_innerTun_btn_check = new JButton("Check");
		nw_innerTun_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = nw_innerTun_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(TUNLabel label : innerTunList) {
		        	if(label.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		nw_innerTun_itemCode.setText(label.getItemCode());
		        		nw_innerTun_itemName.setSelectedItem(label.getProductName());
		        		nw_innerTun_pcCode.setText(label.getPcCode());
		        		nw_innerTun_qty.setText(label.getQuantity());
		        		nw_innerTun_barcode.setText(label.getBarcode());
		        		nw_innerTun_grossWeight.setText(label.getGrossWeight());
		        		break;
		        	}	        	
		        }	
			}
		});
		nw_innerTun_btn_check.setBounds(320, 60, 80, 20);
		contentPane.add(nw_innerTun_btn_check);
		
		JButton nw_innerTun_btn_updateDB = new JButton("Update DB");
		nw_innerTun_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NWTunLabelDBIO tunlabelIO  = new NWTunLabelDBIO(settings, printqueue);
				tunlabelIO.setLocationRelativeTo(null);
				tunlabelIO.setVisible(true);
				dispose();
			}
		});
		nw_innerTun_btn_updateDB.setBounds(20, 380, 95, 20);
		contentPane.add(nw_innerTun_btn_updateDB);
		
		JButton nw_innerTun_btn_settings = new JButton("Settings");
		nw_innerTun_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		nw_innerTun_btn_settings.setBounds(140, 380, 95, 20);
		contentPane.add(nw_innerTun_btn_settings);
		
		JButton nw_innerTun_btn_print = new JButton("Print");
		nw_innerTun_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(nw_innerTun_itemCode.getText(), nw_innerTun_itemName.getSelectedItem().toString(), nw_innerTun_batch.getText(), nw_innerTun_expiryDate.getText(), nw_innerTun_labelsPerCarton.getText(), nw_innerTun_cartonQty.getText(), settings)) {					
						int temp_lblPerCarton = Integer.valueOf(nw_innerTun_labelsPerCarton.getText());
						int temp_cartonQuantity = Integer.valueOf(nw_innerTun_cartonQty.getText());							
						while(temp_cartonQuantity>0) {
								for(int i = temp_lblPerCarton; i>0; i--) {
								    Label innerTunLabel = new InnerTunLabel(nw_innerTun_itemName.getSelectedItem().toString(), nw_innerTun_pcCode.getText(), nw_innerTun_qty.getText(), nw_innerTun_batch.getText(), nw_innerTun_expiryDate.getText(),
								    		nw_innerTun_barcode.getText());
								    printqueue.addLabelToQueue(innerTunLabel);
								}						
								temp_cartonQuantity--;
						}
						
						if(settings.getSetting().isLocalPrintingMode()) {
										PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), null, 0, 680);
										printio.setLocationRelativeTo(null);
										printio.setVisible(true);						
						}else {
										PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), settings.getSetting().getIpAddress(), settings.getSetting().getPort(), 680);
										printio.setLocationRelativeTo(null);
										printio.setVisible(true);			
						}
				}
			}
		});
		nw_innerTun_btn_print.setBounds(260, 380, 95, 20);
		contentPane.add(nw_innerTun_btn_print);
		
		JButton nw_innerTun_btn_back = new JButton("Back");
		nw_innerTun_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		nw_innerTun_btn_back.setBounds(380, 380, 95, 20);
		contentPane.add(nw_innerTun_btn_back);
		
		JLabel nw_innerTun_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		nw_innerTun_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		nw_innerTun_lbl_copyright_1.setBounds(310, 410, 170, 20);
		contentPane.add(nw_innerTun_lbl_copyright_1);
		
		JLabel nw_innerTun_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		nw_innerTun_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		nw_innerTun_lbl_copyright_2.setBounds(210, 424, 280, 20);
		contentPane.add(nw_innerTun_lbl_copyright_2);
	}
	
	public List<TUNLabel> inputItemdata(String path) {
		String csvFile = path;
        String line = "";
        String cvsSplitBy = ",";
        List<TUNLabel> labels = new ArrayList<TUNLabel>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
            	if(line.contains("Company") && line.contains("Item Code")) {
            		//first line dont input
            	}else {
                String[] items = line.split(cvsSplitBy);
                TUNLabel label = new TUNLabel();
                label.setLabelType(items[0]);
                label.setItemCode(items[1]);
                label.setProductName(items[2]);
                label.setPcCode(items[3]);
                label.setQuantity(items[4]);
                label.setBarcode(items[5]);
                label.setGrossWeight(items[6]);
                labels.add(label);
                
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return labels;
	}
	
	public boolean dataValidation(String itemCode, String productName, String batch, String useBy, String labelPerCarton, String cartonQuantity, Settings settings) {
		if(itemCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Item code missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(productName.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "No product selection.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(batch.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Batch missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!useBy.equals("")) {
			char[] temp = useBy.toCharArray();
			if(temp.length == 7) {			
			String month = String.valueOf(temp[0]) + String.valueOf(temp[1]);
			String year = String.valueOf(temp[3])+ String.valueOf(temp[4])+ String.valueOf(temp[5])+ String.valueOf(temp[6]);
			if(temp[2]!='/') {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
						"Date input format error.",
						"Inane error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			int monthint = 0;
			@SuppressWarnings("unused")
			int yearint = 0;
			try {
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
			}
			}else {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Date input format error.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
			}else {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Used By value missing.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		try {
			int labelpercatonTest = Integer.parseInt(labelPerCarton);
			if(labelpercatonTest<=0) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid Labels Per Carton input",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Label Carton must be number.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
			int labelpercatonTest = Integer.parseInt(cartonQuantity);
			if(labelpercatonTest<=0) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid Carton Quantity input",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Carton Quantity must be number.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
				
		return true;
	}
	
	
}
