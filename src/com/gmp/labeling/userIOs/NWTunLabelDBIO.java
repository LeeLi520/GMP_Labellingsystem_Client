package com.gmp.labeling.userIOs;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.TUNLabel;
import com.gmp.labeling.printModels.PrintingQueue;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class NWTunLabelDBIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6411447089396768627L;
	private JPanel contentPane;
	private JTextField nw_TUNlabelDB_barcode;
	private JTextField nw_TUNlabelDB_qty;
	private JTextField nw_TUNlabelDB_pcCode;
	private JTextField nw_TUNlabelDB_itemName;
	private JTextField nw_TUNlabelDB_itemCode;
	private JTextField nw_TUNlabelDB_grossWeight;
	private JTable table;

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public NWTunLabelDBIO(Settings settings, PrintingQueue printqueue) {		
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		List<TUNLabel> labels = inputItemdata(connection.loadProperties().getProperty("tunlabelList"));
		
		
		table = new JTable();
		Object[] columns = {"Label Type", "Item Code", "Product Name", "PC Code","Quantity","Barcode","Gross Weight"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(TUNLabel nw_TUNlabelDB_lbl_caution : labels) {
			Object[] row = {nw_TUNlabelDB_lbl_caution.getLabelType(),nw_TUNlabelDB_lbl_caution.getItemCode(),nw_TUNlabelDB_lbl_caution.getProductName(),nw_TUNlabelDB_lbl_caution.getPcCode(),nw_TUNlabelDB_lbl_caution.getQuantity(),nw_TUNlabelDB_lbl_caution.getBarcode(),nw_TUNlabelDB_lbl_caution.getGrossWeight()};
			model.addRow(row);
		}
		
		table.setModel(model);
		table.setRowHeight(20);
		
		JLabel nw_TUNlabelDB_lbl_title = new JLabel("NW TUN Label DB Settings");
		nw_TUNlabelDB_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		nw_TUNlabelDB_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		nw_TUNlabelDB_lbl_title.setBounds(200, 13, 280, 30);
		contentPane.add(nw_TUNlabelDB_lbl_title);
		
		JScrollPane nw_TUNlabelDB_display = new JScrollPane();
		nw_TUNlabelDB_display.setBounds(25, 55, 650, 250);
		contentPane.add(nw_TUNlabelDB_display);
		
		nw_TUNlabelDB_display.setViewportView(table);
		
		JLabel nw_TUNlabelDB_lbl_itemCode = new JLabel("Item code:");
		nw_TUNlabelDB_lbl_itemCode.setBounds(30, 355, 100, 20);
		contentPane.add(nw_TUNlabelDB_lbl_itemCode);
		
		JLabel nw_TUNlabelDB_lbl_itemName = new JLabel("Product Name:");
		nw_TUNlabelDB_lbl_itemName.setBounds(30, 390, 100, 20);
		contentPane.add(nw_TUNlabelDB_lbl_itemName);
		
		JLabel nw_TUNlabelDB_lbl_pcCode = new JLabel("PC Code:");
		nw_TUNlabelDB_lbl_pcCode.setBounds(30, 425, 100, 20);
		contentPane.add(nw_TUNlabelDB_lbl_pcCode);
		
		JLabel nw_TUNlabelDB_lbl_qty = new JLabel("Quantity:");
		nw_TUNlabelDB_lbl_qty.setBounds(350, 320, 100, 20);
		contentPane.add(nw_TUNlabelDB_lbl_qty);
		
		JLabel nw_TUNlabelDB_lbl_barcode = new JLabel("Barcode:");
		nw_TUNlabelDB_lbl_barcode.setBounds(350, 355, 100, 20);
		contentPane.add(nw_TUNlabelDB_lbl_barcode);
		
		nw_TUNlabelDB_barcode = new JTextField();
		nw_TUNlabelDB_barcode.setColumns(10);
		nw_TUNlabelDB_barcode.setBounds(450, 355, 200, 20);
		contentPane.add(nw_TUNlabelDB_barcode);
		
		nw_TUNlabelDB_qty = new JTextField();
		nw_TUNlabelDB_qty.setColumns(10);
		nw_TUNlabelDB_qty.setBounds(450, 320, 200, 20);
		contentPane.add(nw_TUNlabelDB_qty);
		
		nw_TUNlabelDB_pcCode = new JTextField();
		nw_TUNlabelDB_pcCode.setColumns(10);
		nw_TUNlabelDB_pcCode.setBounds(130, 425, 200, 20);
		contentPane.add(nw_TUNlabelDB_pcCode);
		
		nw_TUNlabelDB_itemName = new JTextField();
		nw_TUNlabelDB_itemName.setColumns(10);
		nw_TUNlabelDB_itemName.setBounds(130, 390, 200, 20);
		contentPane.add(nw_TUNlabelDB_itemName);
		
		nw_TUNlabelDB_itemCode = new JTextField();
		nw_TUNlabelDB_itemCode.setColumns(10);
		nw_TUNlabelDB_itemCode.setBounds(130, 355, 200, 20);
		contentPane.add(nw_TUNlabelDB_itemCode);
		
		JLabel nw_TUNlabelDB_lbl_grossWeight = new JLabel("Gross weight:");
		nw_TUNlabelDB_lbl_grossWeight.setBounds(350, 390, 100, 20);
		contentPane.add(nw_TUNlabelDB_lbl_grossWeight);
		
		nw_TUNlabelDB_grossWeight = new JTextField();
		nw_TUNlabelDB_grossWeight.setText("N/A");
		nw_TUNlabelDB_grossWeight.setBounds(450, 390, 200, 20);
		nw_TUNlabelDB_grossWeight.setBackground(getBackground());
		nw_TUNlabelDB_grossWeight.disable();
		contentPane.add(nw_TUNlabelDB_grossWeight);
		nw_TUNlabelDB_grossWeight.setColumns(10);
		
		JLabel nw_TUNlabelDB_lbl_labelType = new JLabel("Label type:");
		nw_TUNlabelDB_lbl_labelType.setBounds(30, 320, 100, 20);
		contentPane.add(nw_TUNlabelDB_lbl_labelType);
		
		JComboBox nw_TUNlabelDB_labelType = new JComboBox();
		nw_TUNlabelDB_labelType.setBounds(130, 320, 200, 20);
		nw_TUNlabelDB_labelType.setModel(new DefaultComboBoxModel(new String[] {"Inner TUN","Outer TUN"}));		
		nw_TUNlabelDB_labelType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nw_TUNlabelDB_labelType.getSelectedItem().toString().equals("Inner TUN")) {
					nw_TUNlabelDB_grossWeight.disable();
					nw_TUNlabelDB_grossWeight.setBackground(getBackground());
					nw_TUNlabelDB_grossWeight.setText("N/A");
				}else if(nw_TUNlabelDB_labelType.getSelectedItem().toString().equals("Outer TUN")) {
					nw_TUNlabelDB_grossWeight.enable();
					nw_TUNlabelDB_grossWeight.setBackground(Color.WHITE);
					nw_TUNlabelDB_grossWeight.setText("");
				}
			}
		});

		contentPane.add(nw_TUNlabelDB_labelType);
		
		JLabel nw_TUNlabelDB_lbl_caution = new JLabel("");
		nw_TUNlabelDB_lbl_caution.setBounds(350, 425, 300, 20);
		contentPane.add(nw_TUNlabelDB_lbl_caution);
		
		JButton nw_TUNlabelDB_btn_add = new JButton("Add");
		nw_TUNlabelDB_btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValidation(nw_TUNlabelDB_labelType.getSelectedItem().toString(),nw_TUNlabelDB_itemCode.getText(),nw_TUNlabelDB_itemName.getText(),nw_TUNlabelDB_pcCode.getText(),nw_TUNlabelDB_qty.getText(),nw_TUNlabelDB_barcode.getText(), nw_TUNlabelDB_grossWeight.getText(), settings)) {
					String grossWeight = null;
					if(nw_TUNlabelDB_grossWeight.getText().equals("")){
						grossWeight = "N/A";
					}else {
						grossWeight = nw_TUNlabelDB_grossWeight.getText();
					}
					DefaultTableModel tempModel = (DefaultTableModel) table.getModel();
					Object[] row = {nw_TUNlabelDB_labelType.getSelectedItem().toString(),nw_TUNlabelDB_itemCode.getText(),nw_TUNlabelDB_itemName.getText(),nw_TUNlabelDB_pcCode.getText(),nw_TUNlabelDB_qty.getText(),nw_TUNlabelDB_barcode.getText(),grossWeight};
					tempModel.addRow(row);
					table.setModel(tempModel);
					nw_TUNlabelDB_lbl_caution.setText("You have unsaved data! Without saving you will lost your entry.");
				}
			}
		});
		nw_TUNlabelDB_btn_add.setBounds(40, 465, 110, 20);
		contentPane.add(nw_TUNlabelDB_btn_add);
		
		JButton nw_TUNlabelDB_btn_delete = new JButton("Delete");
		nw_TUNlabelDB_btn_delete.addActionListener(new ActionListener() {
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
				nw_TUNlabelDB_lbl_caution.setText("You have unsaved data! Without saving you will lose your entry.");				
			}
		});
		nw_TUNlabelDB_btn_delete.setBounds(210, 465, 110, 20);
		contentPane.add(nw_TUNlabelDB_btn_delete);
		
		JButton nw_TUNlabelDB_btn_save = new JButton("Save");
		nw_TUNlabelDB_btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(updateDB(connection.loadProperties().getProperty("tunlabelList"))) {
					JOptionPane.showMessageDialog(settings.getComponentPage(),
						    "Saved!",
						    "Caution",
						    JOptionPane.INFORMATION_MESSAGE);
					nw_TUNlabelDB_lbl_caution.setText("");
				}else {
					nw_TUNlabelDB_lbl_caution.setText("Error occurred while saving the data.");
					nw_TUNlabelDB_lbl_caution.setForeground(Color.RED);
				}							
			}
		});
		nw_TUNlabelDB_btn_save.setBounds(380, 465, 110, 20);
		contentPane.add(nw_TUNlabelDB_btn_save);
		
		JButton nw_TUNlabelDB_btn_back = new JButton("Back");
		nw_TUNlabelDB_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(settings.getComponentPage().getClass().getSimpleName()) {
				case "NWInnerTunLabelIO":
					  NWInnerTunLabelIO nwinner = new NWInnerTunLabelIO(settings, printqueue);
		        	  settings.setComponentPage(nwinner);
		        	  nwinner.setLocationRelativeTo(null);
		        	  nwinner.setVisible(true);
		        	  dispose();
		        	  break;
				case "NWOuterTunLabelIO":
					 NWOuterTunLabelIO nwouter = new NWOuterTunLabelIO(settings, printqueue);
		        	  settings.setComponentPage(nwouter);
		        	  nwouter.setLocationRelativeTo(null);
		        	  nwouter.setVisible(true);
		        	  dispose();
		        	  break;
		        	  }
			}
		});
		nw_TUNlabelDB_btn_back.setBounds(550, 465, 110, 20);
		contentPane.add(nw_TUNlabelDB_btn_back);
		
		JLabel label_6 = new JLabel("Designed and Implemented by Lee.L");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_6.setBounds(510, 497, 170, 20);
		contentPane.add(label_6);
		
		JLabel label_7 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_7.setBounds(410, 512, 280, 20);
		contentPane.add(label_7);
		

		

		
	}
	
	public boolean updateDB(String path) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		List<TUNLabel> labellist = new ArrayList<TUNLabel>();
		for(int row = 0; row < model.getRowCount(); row++) {
			TUNLabel tempL = new TUNLabel();
			tempL.setLabelType(model.getValueAt(row, 0).toString());
			tempL.setItemCode(model.getValueAt(row, 1).toString());
			tempL.setProductName(model.getValueAt(row, 2).toString());
			tempL.setPcCode(model.getValueAt(row, 3).toString());
			tempL.setQuantity(model.getValueAt(row, 4).toString());
			tempL.setBarcode(model.getValueAt(row, 5).toString());
			tempL.setGrossWeight(model.getValueAt(row, 6).toString());			
			labellist.add(tempL);
		}
		
		FileWriter fw;
		try {
			String data ="TUN Type"+','+ "Item Code"+','+"Product Name"+','+"PC Code"+','+"Quantity"+','+"Barcode"+','+"Gross Weight"+'\n';
	        for(TUNLabel label : labellist) {
		        data += label.getLabelType()+ ',' + label.getItemCode()+ ',' + label.getProductName()+ ',' + label.getPcCode()+ ',' + label.getQuantity()+ ',' + label.getBarcode() + ',' + label.getGrossWeight() +'\n';
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
	
	public List<TUNLabel> inputItemdata(String path) {
		String csvFile = path;
        String line = "";
        String cvsSplitBy = ",";
        List<TUNLabel> labels = new ArrayList<TUNLabel>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
            	if(line.contains("TUN Type") && line.contains("Item Code")) {
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
	
	@SuppressWarnings("deprecation")
	public boolean dataValidation(String type, String itemCode, String productName, String pcCode, String quantity, String barcode, String grossWeight, Settings settings) {
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
		if(pcCode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "PC Code missing.",
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
		if(barcode.equals("")) {
			JOptionPane.showMessageDialog(settings.getComponentPage(),
				    "Barcode missing.",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(type.equals("Outer TUN")) {
			if(grossWeight.equals("")) {
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Gross Weight missing.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
}
