package com.gmp.labeling.userIOs;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.TUNLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.OuterTunLabel;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class NWOuterTunLabelIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6519477498139277119L;
	private JPanel contentPane;
	private JTextField nw_outerTun_batch;
	private JTextField nw_outerTun_expiryDate;
	private JTextField nw_outerTun_labelsPerCarton;
	private JTextField nw_outerTun_cartonQty;
	private JTextField nw_outerTun_itemCode;
	private List<TUNLabel> labels;
	private List<TUNLabel> outerTunList;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NWOuterTunLabelIO(Settings settings, PrintingQueue printqueue) {
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
		
        outerTunList = new ArrayList<TUNLabel>();
		
		for(TUNLabel label : labels) {
			if(label.getLabelType().equals("Outer TUN")) {
				outerTunList.add(label);
			}
		}
		
		String[] productNameArray = new String[outerTunList.size()];
		int i = 0;
		for(TUNLabel l : outerTunList) {
			if(l.getItemCode().equals("Item Code")) {
				
			}else {
				productNameArray[i] = l.getProductName();
				i++;
			}			
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 492, 453);
		contentPane.add(panel);
		
		JLabel nw_outerTun_lbl_title = new JLabel("NW Outer Tun Label");
		nw_outerTun_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		nw_outerTun_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		nw_outerTun_lbl_title.setBounds(90, 18, 300, 30);
		panel.add(nw_outerTun_lbl_title);
		
		JLabel nw_outerTun_lbl_itemCode = new JLabel("Item code:");
		nw_outerTun_lbl_itemCode.setBounds(50, 60, 90, 20);
		panel.add(nw_outerTun_lbl_itemCode);
		
		JLabel nw_outerTun_lbl_itemName = new JLabel("Item name:");
		nw_outerTun_lbl_itemName.setBounds(50, 100, 90, 20);
		panel.add(nw_outerTun_lbl_itemName);
		

		

		
		JLabel nw_outerTun_lbl_qty = new JLabel("Qty:");
		nw_outerTun_lbl_qty.setBounds(50, 180, 40, 20);
		panel.add(nw_outerTun_lbl_qty);
		
		JLabel nw_outerTun_qty = new JLabel("");
		nw_outerTun_qty.setForeground(Color.RED);
		nw_outerTun_qty.setBounds(90, 180, 50, 20);
		panel.add(nw_outerTun_qty);
		
		JLabel nw_outerTun_lbl_pcCode = new JLabel("PC code:");
		nw_outerTun_lbl_pcCode.setBounds(50, 140, 70, 20);
		panel.add(nw_outerTun_lbl_pcCode);
		
		JLabel nw_outerTun_pcCode = new JLabel("");
		nw_outerTun_pcCode.setHorizontalAlignment(SwingConstants.CENTER);
		nw_outerTun_pcCode.setForeground(Color.RED);
		nw_outerTun_pcCode.setBounds(120, 140, 90, 20);
		panel.add(nw_outerTun_pcCode);
		
		JLabel nw_outerTun_lbl_barcode = new JLabel("Barcode:");
		nw_outerTun_lbl_barcode.setBounds(210, 140, 70, 20);
		panel.add(nw_outerTun_lbl_barcode);
		
		JLabel nw_outerTun_barcode = new JLabel("");
		nw_outerTun_barcode.setHorizontalAlignment(SwingConstants.CENTER);
		nw_outerTun_barcode.setForeground(Color.RED);
		nw_outerTun_barcode.setBounds(280, 140, 150, 20);
		panel.add(nw_outerTun_barcode);
		
		JLabel nw_outerTun_lbl_grossWeight = new JLabel("Gross weight:");
		nw_outerTun_lbl_grossWeight.setBounds(210, 180, 90, 20);
		panel.add(nw_outerTun_lbl_grossWeight);
		
		JLabel nw_outerTun_grossWeight = new JLabel("");
		nw_outerTun_grossWeight.setHorizontalAlignment(SwingConstants.CENTER);
		nw_outerTun_grossWeight.setForeground(Color.RED);
		nw_outerTun_grossWeight.setBounds(300, 180, 80, 20);
		panel.add(nw_outerTun_grossWeight);
		
		JLabel nw_outerTun_lbl_batch = new JLabel("Batch:");
		nw_outerTun_lbl_batch.setBounds(50, 220, 90, 20);
		panel.add(nw_outerTun_lbl_batch);
		
		nw_outerTun_batch = new JTextField();
		nw_outerTun_batch.setColumns(10);
		nw_outerTun_batch.setBounds(140, 220, 180, 20);
		panel.add(nw_outerTun_batch);
		
		JLabel nw_outerTun_lbl_expiryDate = new JLabel("Expiry date:");
		nw_outerTun_lbl_expiryDate.setBounds(50, 260, 90, 20);
		panel.add(nw_outerTun_lbl_expiryDate);
		
		nw_outerTun_expiryDate = new JTextField();
		nw_outerTun_expiryDate.setColumns(10);
		nw_outerTun_expiryDate.setBounds(140, 260, 180, 20);
		panel.add(nw_outerTun_expiryDate);
		
		JLabel nw_outerTun_lbl_labelsPerCarton = new JLabel("Labels Per Carton:");
		nw_outerTun_lbl_labelsPerCarton.setBounds(50, 300, 110, 20);
		panel.add(nw_outerTun_lbl_labelsPerCarton);
		
		nw_outerTun_labelsPerCarton = new JTextField();
		nw_outerTun_labelsPerCarton.setToolTipText("def 1");
		nw_outerTun_labelsPerCarton.setColumns(10);
		nw_outerTun_labelsPerCarton.setBounds(160, 300, 160, 20);
		panel.add(nw_outerTun_labelsPerCarton);
		
		JLabel nw_outerTun_lbl_cartonQty = new JLabel("Carton Quantity:");
		nw_outerTun_lbl_cartonQty.setBounds(50, 340, 100, 20);
		panel.add(nw_outerTun_lbl_cartonQty);
		
		nw_outerTun_cartonQty = new JTextField();
		nw_outerTun_cartonQty.setColumns(10);
		nw_outerTun_cartonQty.setBounds(150, 340, 170, 20);
		panel.add(nw_outerTun_cartonQty);
		
		nw_outerTun_itemCode = new JTextField();
		nw_outerTun_itemCode.setColumns(10);
		nw_outerTun_itemCode.setBounds(140, 60, 180, 20);
		panel.add(nw_outerTun_itemCode);
		
		JComboBox nw_outerTun_itemName = new JComboBox();
		nw_outerTun_itemName.setModel(new DefaultComboBoxModel(productNameArray));
		nw_outerTun_itemName.setSelectedIndex(-1);
		nw_outerTun_itemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String) nw_outerTun_itemName.getSelectedItem();
		        for(TUNLabel label : outerTunList) {
		        	if(label.getProductName().equals(value)) {
		        		nw_outerTun_itemCode.setText(label.getItemCode());
		        		nw_outerTun_pcCode.setText(label.getPcCode());
		        		nw_outerTun_qty.setText(label.getQuantity());
		        		nw_outerTun_barcode.setText(label.getBarcode());
		        		nw_outerTun_grossWeight.setText(label.getGrossWeight());
		        		break;
		        	}	        	
		        }
			}
		});
		nw_outerTun_itemName.setBounds(140, 100, 200, 20);
		panel.add(nw_outerTun_itemName);
		
		JButton nw_outerTun_btn_check = new JButton("Check");
		nw_outerTun_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = nw_outerTun_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(TUNLabel label : outerTunList) {
		        	if(label.getItemCode().replaceAll("([a-z])", "$1").toUpperCase().equals(itemCode_value)) {
		        		nw_outerTun_itemCode.setText(label.getItemCode());
		        		nw_outerTun_itemName.setSelectedItem(label.getProductName());
		        		nw_outerTun_pcCode.setText(label.getPcCode());
		        		nw_outerTun_qty.setText(label.getQuantity());
		        		nw_outerTun_barcode.setText(label.getBarcode());
		        		nw_outerTun_grossWeight.setText(label.getGrossWeight());
		        		break;
		        	}	        	
		        }	
			}
		});
		nw_outerTun_btn_check.setBounds(320, 60, 80, 20);
		panel.add(nw_outerTun_btn_check);
		
		JButton nw_outerTun_btn_updateDB = new JButton("Update DB");
		nw_outerTun_btn_updateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NWTunLabelDBIO tunlabelIO  = new NWTunLabelDBIO(settings, printqueue);
				tunlabelIO.setLocationRelativeTo(null);
				tunlabelIO.setVisible(true);
				dispose();
			}
		});
		nw_outerTun_btn_updateDB.setBounds(20, 380, 95, 20);
		panel.add(nw_outerTun_btn_updateDB);
		
		JButton nw_outerTun_btn_settings = new JButton("Settings");
		nw_outerTun_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		nw_outerTun_btn_settings.setBounds(140, 380, 95, 20);
		panel.add(nw_outerTun_btn_settings);
		
		JButton nw_outerTun_btn_print = new JButton("Print");
		nw_outerTun_btn_print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(nw_outerTun_itemCode.getText(), nw_outerTun_itemName.getSelectedItem().toString(), nw_outerTun_batch.getText(), nw_outerTun_expiryDate.getText(), nw_outerTun_labelsPerCarton.getText(), nw_outerTun_cartonQty.getText(), settings)) {					
					int temp_lblPerCarton = Integer.valueOf(nw_outerTun_labelsPerCarton.getText());
					int temp_cartonQuantity = Integer.valueOf(nw_outerTun_cartonQty.getText());							
					while(temp_cartonQuantity>0) {
							for(int i = temp_lblPerCarton; i>0; i--) {
							    Label outerTunLabel = new OuterTunLabel(nw_outerTun_itemName.getSelectedItem().toString(), nw_outerTun_pcCode.getText(), nw_outerTun_qty.getText(), nw_outerTun_batch.getText(), nw_outerTun_expiryDate.getText(),
							    		nw_outerTun_barcode.getText(), nw_outerTun_grossWeight.getText());
							    printqueue.addLabelToQueue(outerTunLabel);
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
		nw_outerTun_btn_print.setBounds(260, 380, 95, 20);
		panel.add(nw_outerTun_btn_print);
		
		JButton nw_outerTun_btn_back = new JButton("Back");
		nw_outerTun_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		nw_outerTun_btn_back.setBounds(380, 380, 95, 20);
		panel.add(nw_outerTun_btn_back);
		
		JLabel nw_outerTun_lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		nw_outerTun_lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		nw_outerTun_lbl_copyright_1.setBounds(310, 410, 170, 20);
		panel.add(nw_outerTun_lbl_copyright_1);
		
		JLabel nw_outerTun_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		nw_outerTun_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		nw_outerTun_lbl_copyright_2.setBounds(210, 424, 280, 20);
		panel.add(nw_outerTun_lbl_copyright_2);
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
