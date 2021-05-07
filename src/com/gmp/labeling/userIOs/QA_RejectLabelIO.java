package com.gmp.labeling.userIOs;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Material;
import com.gmp.labeling.printModels.PrintingQueue;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Button;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class QA_RejectLabelIO extends JFrame {

	private JPanel contentPane;
	private JTextField qaRejectLabel_itemCode;
	private JTextField qaRejectLabel_gin;
	private JTextField qaRejectLabel_rejectedQty;
	private JTextField qaRejectLabel_reportNo;
	private JTextField qaRejectLabel_reason;

	public QA_RejectLabelIO(Settings settings, PrintingQueue printqueue) {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel qaRejectLabel_lbl_title = new JLabel("QA Reject Label");
		qaRejectLabel_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		qaRejectLabel_lbl_title.setFont(new Font("Arial", Font.BOLD, 20));
		qaRejectLabel_lbl_title.setBounds(80, 30, 300, 30);
		contentPane.add(qaRejectLabel_lbl_title);
		
		JLabel qaRejectLabel_lbl_itemCode = new JLabel("Item Code:");
		qaRejectLabel_lbl_itemCode.setBounds(50, 80, 80, 20);
		contentPane.add(qaRejectLabel_lbl_itemCode);
		
		qaRejectLabel_itemCode = new JTextField();
		qaRejectLabel_itemCode.setBounds(130, 80, 220, 20);
		contentPane.add(qaRejectLabel_itemCode);
		qaRejectLabel_itemCode.setColumns(10);
		
		JLabel qaRejectLabel_lbl_itemName = new JLabel("Item Name:");
		qaRejectLabel_lbl_itemName.setBounds(50, 120, 80, 20);
		contentPane.add(qaRejectLabel_lbl_itemName);
		
		JLabel qaRejectLabel_lbl_displayitemName = new JLabel("");
		qaRejectLabel_lbl_displayitemName.setBounds(130, 120, 290, 20);
		contentPane.add(qaRejectLabel_lbl_displayitemName);
		
		List<Material> materials = inputItemdata(connection.loadProperties().getProperty("materiallistpath"));
		
		Button qaRejectLabel_btn_check = new Button("Check");
		qaRejectLabel_btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemCode_value = qaRejectLabel_itemCode.getText().replaceAll("([a-z])", "$1").toUpperCase();
				for(Material temp :materials) {
		        	if(JsonParser.takeoffComma(temp.getItem_code()).equals(itemCode_value)) {
		        		qaRejectLabel_lbl_displayitemName.setText(JsonParser.takeoffComma(temp.getItem_name()));
		        		qaRejectLabel_itemCode.setText(itemCode_value);
		        		break;
		        	}	        	
		        }
			}
		});
		qaRejectLabel_btn_check.setBounds(350, 80, 70, 20);
		contentPane.add(qaRejectLabel_btn_check);
		
		JLabel qaRejectLabel_lbl_gin = new JLabel("Gin No:");
		qaRejectLabel_lbl_gin.setBounds(50, 160, 80, 20);
		contentPane.add(qaRejectLabel_lbl_gin);
		
		qaRejectLabel_gin = new JTextField();
		qaRejectLabel_gin.setBounds(130, 160, 220, 20);
		contentPane.add(qaRejectLabel_gin);
		qaRejectLabel_gin.setColumns(10);
		
		JLabel qaRejectLabel_lbl_rejectedQty = new JLabel("Rejected Qty:");
		qaRejectLabel_lbl_rejectedQty.setBounds(50, 200, 80, 20);
		contentPane.add(qaRejectLabel_lbl_rejectedQty);
		
		qaRejectLabel_rejectedQty = new JTextField();
		qaRejectLabel_rejectedQty.setBounds(130, 200, 220, 20);
		contentPane.add(qaRejectLabel_rejectedQty);
		qaRejectLabel_rejectedQty.setColumns(10);
		
		JLabel qaRejectLabel_lbl_reportNo = new JLabel("Report No:");
		qaRejectLabel_lbl_reportNo.setBounds(50, 240, 80, 20);
		contentPane.add(qaRejectLabel_lbl_reportNo);
		
		qaRejectLabel_reportNo = new JTextField();
		qaRejectLabel_reportNo.setColumns(10);
		qaRejectLabel_reportNo.setBounds(130, 240, 220, 20);
		contentPane.add(qaRejectLabel_reportNo);
		
		JLabel qaRejectLabel_lbl_reason = new JLabel("Reason:");
		qaRejectLabel_lbl_reason.setBounds(50, 280, 80, 20);
		contentPane.add(qaRejectLabel_lbl_reason);
		
		qaRejectLabel_reason = new JTextField();
		qaRejectLabel_reason.setColumns(10);
		qaRejectLabel_reason.setBounds(130, 280, 220, 20);
		contentPane.add(qaRejectLabel_reason);
		
		
		JButton qaRejectLabel_btn_Settings = new JButton("Settings");
		qaRejectLabel_btn_Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				settings.setLocationRelativeTo(null);
				dispose();
			}
		});
		qaRejectLabel_btn_Settings.setBounds(30, 322, 85, 20);
		contentPane.add(qaRejectLabel_btn_Settings);
		
		JButton qaRejectLabel_btn_Print = new JButton("Print");
		qaRejectLabel_btn_Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		qaRejectLabel_btn_Print.setBounds(140, 322, 85, 20);
		contentPane.add(qaRejectLabel_btn_Print);
		
		JButton qaRejectLabel_btn_Update = new JButton("Update");
		qaRejectLabel_btn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.FtpUpdateFiles(settings,"stk.csv", "materiallistpath");
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		qaRejectLabel_btn_Update.setBounds(250, 322, 85, 20);
		contentPane.add(qaRejectLabel_btn_Update);
		
		JButton qaRejectLabel_btn_Back = new JButton("Back");
		qaRejectLabel_btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		qaRejectLabel_btn_Back.setBounds(360, 322, 85, 20);
		contentPane.add(qaRejectLabel_btn_Back);
		
		JLabel qaRejectLabel__lbl_copyright_1 = new JLabel("Designed and Implemented by Lee.L");
		qaRejectLabel__lbl_copyright_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		qaRejectLabel__lbl_copyright_1.setBounds(295, 353, 170, 20);
		contentPane.add(qaRejectLabel__lbl_copyright_1);
		
		JLabel qaRejectLabel_lbl_copyright_2 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		qaRejectLabel_lbl_copyright_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		qaRejectLabel_lbl_copyright_2.setBounds(194, 368, 280, 20);
		contentPane.add(qaRejectLabel_lbl_copyright_2);
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
