package com.gmp.labeling.userIOs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Product;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Button;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BulkProductLabelIO extends JFrame {

	private JPanel contentPane;
	private List<Product> products;
	private JTextField bulkProduct_itemCode;
	private JTextField bulkProduct_batch;
	private JTextField bulkProduct_useBy;
	private JTextField bulkProduct_shift;
	private JTextField bulkProduct_cartonStartFrom;
	private JTextField bulkProduct_labelsPerCarton;
	private JTextField bulkProduct_cartonQuantity;
	private JTextField bulkProduct_productName;
	private JTextField bulkProduct_quantity;
	private JTextField bulkProduct_temperature;
	private String format;
	

	public BulkProductLabelIO() {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		products = inputItemdata(connection.loadProperties().getProperty("finishedProductList"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel bulkProduct_lbl_title = new JLabel("Bulk Product Label");
		bulkProduct_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		bulkProduct_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		bulkProduct_lbl_title.setBounds(123, 13, 260, 30);
		contentPane.add(bulkProduct_lbl_title);
		
		JLabel bulkProduct_lbl_itemCode = new JLabel("Item Code:");
		bulkProduct_lbl_itemCode.setForeground(Color.BLACK);
		bulkProduct_lbl_itemCode.setFont(new Font("Tahoma", Font.PLAIN, 13));
		bulkProduct_lbl_itemCode.setBounds(50, 60, 90, 20);
		contentPane.add(bulkProduct_lbl_itemCode);
		
		bulkProduct_itemCode = new JTextField();
		bulkProduct_itemCode.setColumns(10);
		bulkProduct_itemCode.setBounds(150, 60, 260, 20);
		contentPane.add(bulkProduct_itemCode);
		
		JLabel bulkProduct_lbl_productName = new JLabel("Product name:");
		bulkProduct_lbl_productName.setBounds(50, 100, 90, 20);
		contentPane.add(bulkProduct_lbl_productName);
		
		JLabel bulkProduct_lbl_batch = new JLabel("Batch:");
		bulkProduct_lbl_batch.setBounds(50, 140, 90, 20);
		contentPane.add(bulkProduct_lbl_batch);
		
		bulkProduct_batch = new JTextField();
		bulkProduct_batch.setColumns(10);
		bulkProduct_batch.setBounds(150, 140, 260, 20);
		contentPane.add(bulkProduct_batch);
		
		JLabel bulkProduct_lbl_useByFormat = new JLabel("Use By format:");
		bulkProduct_lbl_useByFormat.setBounds(50, 180, 100, 20);
		contentPane.add(bulkProduct_lbl_useByFormat);
		
		JRadioButton bulkProduct_rdbtn_useBy = new JRadioButton("Use By");
		bulkProduct_rdbtn_useBy.setSelected(true);
		bulkProduct_rdbtn_useBy.setBounds(150, 180, 80, 20);
		contentPane.add(bulkProduct_rdbtn_useBy);
		
		JRadioButton bulkProduct_rdbtn_expDate = new JRadioButton("Exp Date");
		bulkProduct_rdbtn_expDate.setBounds(230, 180, 90, 20);
		contentPane.add(bulkProduct_rdbtn_expDate);
		
		JRadioButton bulkProduct_rdbtn_bestBefore = new JRadioButton("Best Before");
		bulkProduct_rdbtn_bestBefore.setBounds(320, 180, 100, 20);
		contentPane.add(bulkProduct_rdbtn_bestBefore);
		
		JLabel bulkProduct_lbl_useBy = new JLabel("Use By:");
		bulkProduct_lbl_useBy.setBounds(50, 220, 100, 20);
		contentPane.add(bulkProduct_lbl_useBy);
		
		bulkProduct_useBy = new JTextField();
		bulkProduct_useBy.setColumns(10);
		bulkProduct_useBy.setBounds(150, 220, 180, 20);
		contentPane.add(bulkProduct_useBy);
		
		Button bulkProduct_btn_useBy_info = new Button("i");
		bulkProduct_btn_useBy_info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		bulkProduct_btn_useBy_info.setBounds(330, 220, 20, 20);
		contentPane.add(bulkProduct_btn_useBy_info);
		
		format = "D/M/Y";
		JRadioButton bulkProduct_rdbtn_dateFormat_au = new JRadioButton("AU");
		bulkProduct_rdbtn_dateFormat_au.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		bulkProduct_rdbtn_dateFormat_au.setSelected(true);
		bulkProduct_rdbtn_dateFormat_au.setBounds(368, 220, 50, 20);
		contentPane.add(bulkProduct_rdbtn_dateFormat_au);
		
		JRadioButton bulkProduct_rdbtn_dateFormat_cn = new JRadioButton("CN");
		bulkProduct_rdbtn_dateFormat_cn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		bulkProduct_rdbtn_dateFormat_cn.setBounds(418, 220, 50, 20);
		contentPane.add(bulkProduct_rdbtn_dateFormat_cn);
		
		ButtonGroup dateformatGroup = new ButtonGroup();
		dateformatGroup.add(bulkProduct_rdbtn_dateFormat_au);
		dateformatGroup.add(bulkProduct_rdbtn_dateFormat_cn);
		
		JLabel bulkProduct_lbl_quantity = new JLabel("Display Quantity:");
		bulkProduct_lbl_quantity.setBounds(50, 260, 100, 20);
		contentPane.add(bulkProduct_lbl_quantity);
		
		JLabel bulkProduct_lbl_displayUnit = new JLabel("units/carton");
		bulkProduct_lbl_displayUnit.setFont(new Font("Tahoma", Font.BOLD, 11));
		bulkProduct_lbl_displayUnit.setBounds(240, 260, 90, 20);
		contentPane.add(bulkProduct_lbl_displayUnit);
		
		JLabel bulkProduct_lbl_shift = new JLabel("Shift:");
		bulkProduct_lbl_shift.setBounds(328, 260, 46, 20);
		contentPane.add(bulkProduct_lbl_shift);
		
		bulkProduct_shift = new JTextField();
		bulkProduct_shift.setColumns(10);
		bulkProduct_shift.setBounds(374, 260, 80, 20);
		contentPane.add(bulkProduct_shift);
		
		JLabel bulkProduct_lbl_cartonStartFrom = new JLabel("Carton No Start from:");
		bulkProduct_lbl_cartonStartFrom.setBounds(50, 300, 130, 20);
		contentPane.add(bulkProduct_lbl_cartonStartFrom);
		
		bulkProduct_cartonStartFrom = new JTextField();
		bulkProduct_cartonStartFrom.setColumns(10);
		bulkProduct_cartonStartFrom.setBounds(184, 300, 60, 20);
		contentPane.add(bulkProduct_cartonStartFrom);
		
		JLabel bulkProduct_lbl_labelsPerCarton = new JLabel("Labels Per Carton:");
		bulkProduct_lbl_labelsPerCarton.setBounds(265, 300, 110, 20);
		contentPane.add(bulkProduct_lbl_labelsPerCarton);
		
		bulkProduct_labelsPerCarton = new JTextField();
		bulkProduct_labelsPerCarton.setToolTipText("def 1");
		bulkProduct_labelsPerCarton.setColumns(10);
		bulkProduct_labelsPerCarton.setBounds(374, 300, 80, 20);
		contentPane.add(bulkProduct_labelsPerCarton);
		
		JLabel bulkProduct_lbl_cartonQuantity = new JLabel("Carton Quantity:");
		bulkProduct_lbl_cartonQuantity.setBounds(50, 340, 100, 20);
		contentPane.add(bulkProduct_lbl_cartonQuantity);
		
		bulkProduct_cartonQuantity = new JTextField();
		bulkProduct_cartonQuantity.setColumns(10);
		bulkProduct_cartonQuantity.setBounds(150, 340, 95, 20);
		contentPane.add(bulkProduct_cartonQuantity);
		
		JLabel bulkProduct_lbl_temperature = new JLabel("Temperature:");
		bulkProduct_lbl_temperature.setBounds(265, 340, 110, 20);
		contentPane.add(bulkProduct_lbl_temperature);
		
		JButton bulkProduct_btn_settings = new JButton("Settings");
		bulkProduct_btn_settings.setBounds(40, 378, 110, 20);
		contentPane.add(bulkProduct_btn_settings);
		
		JButton bulkProduct_btn_print = new JButton("Print");
		bulkProduct_btn_print.setBounds(190, 378, 110, 20);
		contentPane.add(bulkProduct_btn_print);
		
		JButton bulkProduct_btn_back = new JButton("Back");
		bulkProduct_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		bulkProduct_btn_back.setBounds(340, 378, 110, 20);
		contentPane.add(bulkProduct_btn_back);
		
		JLabel bulkProduct_lbl_copyright_info_1 = new JLabel("Designed and Implemented by Lee.L");
		bulkProduct_lbl_copyright_info_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		bulkProduct_lbl_copyright_info_1.setBounds(315, 405, 170, 20);
		contentPane.add(bulkProduct_lbl_copyright_info_1);
		
		JLabel bulkProduct_lbl_copyright_info_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		bulkProduct_lbl_copyright_info_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		bulkProduct_lbl_copyright_info_2.setBounds(215, 420, 280, 20);
		contentPane.add(bulkProduct_lbl_copyright_info_2);
		
		bulkProduct_productName = new JTextField();
		bulkProduct_productName.setBounds(150, 100, 260, 20);
		contentPane.add(bulkProduct_productName);
		bulkProduct_productName.setColumns(10);
		
		bulkProduct_quantity = new JTextField();
		bulkProduct_quantity.setBounds(150, 260, 80, 22);
		contentPane.add(bulkProduct_quantity);
		bulkProduct_quantity.setColumns(10);
		
		JLabel bulkProduct_lbl_temperatureIcon = new JLabel("\u00B0C");
		bulkProduct_lbl_temperatureIcon.setBounds(434, 340, 30, 20);
		contentPane.add(bulkProduct_lbl_temperatureIcon);
		
		bulkProduct_temperature = new JTextField();
		bulkProduct_temperature.setBounds(374, 340, 60, 20);
		contentPane.add(bulkProduct_temperature);
		bulkProduct_temperature.setColumns(10);
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
                product.setExtraInfo(items[4]);
                products.add(product);
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
	}

}
