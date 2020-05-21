package com.gmp.labeling.userIOs;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.Setting;
import com.gmp.labeling.models.User;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Settings extends JFrame {

	private JPanel contentPane;
	private JTextField tcpipInput;
	private JTextField printerportInput;
	private Setting setting;
	private JFrame TmpltSelectionPage = null;
	private JFrame ComponentPage=null;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public Settings(User logined_user) {
		RestConnection connection = new RestConnection();	
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Settings.class.getResource(connection.loadProperties().getProperty("logo"))));			
		setting = connection.getSetting(logined_user);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 500, 400);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSystemSettings = new JLabel("System Settings");
		lblSystemSettings.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSystemSettings.setBounds(30, 10, 150, 28);
		contentPane.add(lblSystemSettings);
		
		JLabel lblPrintingType = new JLabel("Printing type:");
		lblPrintingType.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPrintingType.setBounds(30, 60, 90, 20);
		contentPane.add(lblPrintingType);
		
		JRadioButton rdbtnLocalPrinter = new JRadioButton("Local Printer");
		rdbtnLocalPrinter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnLocalPrinter.setBounds(140, 60, 110, 20);		
		JRadioButton rdbtnWebPrinter = new JRadioButton("Web Printer");
		rdbtnWebPrinter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnWebPrinter.setBounds(260, 60, 110, 20);		
				
		JLabel lblTcpipAddress = new JLabel("TCP/IP address:");
		lblTcpipAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTcpipAddress.setBounds(30, 100, 100, 20);
		contentPane.add(lblTcpipAddress);
		
		tcpipInput = new JTextField();
		tcpipInput.setBounds(140, 100, 180, 20);
		tcpipInput.setBackground(getBackground());
		tcpipInput.setText(setting.getIpAddress());
		contentPane.add(tcpipInput);
		tcpipInput.setColumns(10);
		
		JLabel lblPrinterport = new JLabel("Printer port:");
		lblPrinterport.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPrinterport.setBounds(30, 140, 90, 20);
		contentPane.add(lblPrinterport);
		
		printerportInput = new JTextField();
		printerportInput.setBounds(140, 140, 180, 20);
		printerportInput.setBackground(getBackground());
		printerportInput.setText(String.valueOf(setting.getPort()));
		contentPane.add(printerportInput);
		printerportInput.setColumns(10);
		
		if(setting.isLocalPrintingMode()) {
			rdbtnLocalPrinter.setSelected(true);
			rdbtnWebPrinter.setSelected(false);
			tcpipInput.disable();
			tcpipInput.setBackground(getBackground());
			printerportInput.disable();
			printerportInput.setBackground(getBackground());
		}else {
			rdbtnLocalPrinter.setSelected(false);
			rdbtnWebPrinter.setSelected(true);
			tcpipInput.enable();
			tcpipInput.setBackground(Color.WHITE);
			printerportInput.enable();
			printerportInput.setBackground(Color.WHITE);
		}
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ComponentPage != null) {
					ComponentPage.setVisible(true);
					dispose();
				}
			}
		});
		btnBack.setBounds(295, 310, 100, 20);
		contentPane.add(btnBack);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnLocalPrinter);
		group.add(rdbtnWebPrinter);
		
		rdbtnLocalPrinter.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(rdbtnLocalPrinter.isSelected()) {
		        	setting.setLocalPrintingMode(true); 
		        	tcpipInput.disable();
		        	tcpipInput.setBackground(getBackground());
		        	printerportInput.disable();
		        	printerportInput.setBackground(getBackground());
		        }
		    }});
		rdbtnWebPrinter.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(rdbtnWebPrinter.isSelected()) {
		        	setting.setLocalPrintingMode(false);
		        	tcpipInput.enable();
		        	tcpipInput.setBackground(Color.WHITE);
		        	printerportInput.enable();
		        	printerportInput.setBackground(Color.WHITE);
		        }
		    }});
		contentPane.add(rdbtnLocalPrinter);
		contentPane.add(rdbtnWebPrinter);
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!setting.isLocalPrintingMode()) {
					setting.setIpAddress(tcpipInput.getText());
					setting.setPort(Integer.parseInt(printerportInput.getText()));
					RestConnection connection = new RestConnection();
					connection.saveSettings(setting.getLogined_user(), setting);					
				}else {
					RestConnection connection = new RestConnection();
					connection.saveSettings(setting.getLogined_user(), setting);	
				}
				ComponentPage.setVisible(true);
				dispose();
			}
		});
		btnSave.setBounds(105, 310, 100, 20);
		contentPane.add(btnSave);
		
	}



	public JFrame getTmpltSelectionPage() {
		return TmpltSelectionPage;
	}



	public void setTmpltSelectionPage(JFrame tmpltSelectionPage) {
		TmpltSelectionPage = tmpltSelectionPage;
	}



	public JFrame getComponentPage() {
		return ComponentPage;
	}



	public void setComponentPage(JFrame componentPage) {
		ComponentPage = componentPage;
	}



	public Setting getSetting() {
		return setting;
	}



	public void setSetting(Setting setting) {
		this.setting = setting;
	}
	
	
}
