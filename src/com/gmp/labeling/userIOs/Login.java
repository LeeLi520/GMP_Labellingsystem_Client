package com.gmp.labeling.userIOs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.JsonParser;
import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.User;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import javax.swing.SwingConstants;

public class Login extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField login_username;
	private JPasswordField login_password;
	private User logined_user;
	private TemplateSelection templateSelection;
	
	public User getLogined_user() {
		return logined_user;
	}

	public TemplateSelection getTemplateSelection() {
		return templateSelection;
	}

	public Login() {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource(connection.loadProperties().getProperty("logo"))));
		logined_user = null;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 215);
		setResizable(false);
				
		contentPane = new JPanel();
     	contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel login_lbl_logoImage = new JLabel("");
		login_lbl_logoImage.setIcon(new ImageIcon(Login.class.getResource(connection.loadProperties().getProperty("companylogo"))));
		login_lbl_logoImage.setBounds(0, 5, 255, 180);
		contentPane.add(login_lbl_logoImage);
		
		JButton login_btn_Login = new JButton("Login");
		login_btn_Login.setBounds(265, 148, 80, 20);
		contentPane.add(login_btn_Login);
		
		JButton login_btn_Exit = new JButton("Exit");
		login_btn_Exit.setBounds(423, 148, 80, 20);
		contentPane.add(login_btn_Exit);
		
		login_password = new JPasswordField();
		login_password.setBounds(348, 103, 155, 20);
		contentPane.add(login_password);
		
		login_username = new JTextField();
		login_username.setBounds(348, 68, 155, 20);
		contentPane.add(login_username);
		login_username.setColumns(10);
		
		JLabel login_lbl_title = new JLabel("Label Printing System");
		login_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		login_lbl_title.setBounds(251, 11, 267, 50);
		contentPane.add(login_lbl_title);
		login_lbl_title.setForeground(new Color(0, 0, 0));
		login_lbl_title.setFont(new Font("Tahoma", Font.BOLD, 22));
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(265, 67, 70, 20);
		contentPane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(265, 102, 70, 20);
		contentPane.add(lblPassword);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel errorMessage = new JLabel("");
		errorMessage.setForeground(new Color(255, 51, 51));
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setBounds(290, 128, 225, 16);
		contentPane.add(errorMessage);
		login_password.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					logined_user = connection.getUser(login_username.getText(), login_password.getText());
					if(logined_user == null) {
						errorMessage.setText("Invalid username or password!");
					}else {
						Settings settings = new Settings(logined_user);
						settings.getSetting().setVersion(connection.loadProperties().getProperty("version"));
						errorMessage.setText(null);
						settings.getSetting().setLogined_user(logined_user);
						if(settings.getSetting().getVersion().equals(connection.CompareVersion())) {					
							templateSelection = new TemplateSelection(logined_user, settings);
							settings.setTmpltSelectionPage(templateSelection);
							templateSelection.setLocationRelativeTo(null);
							templateSelection.setVisible(true);
							dispose();						
						}else {
							String serverVersion = connection.CompareVersion();
							if(JsonParser.getVersionPositionNumber(1, serverVersion)>JsonParser.getVersionPositionNumber(1, settings.getSetting().getVersion())) {
								UpdateConfirmation updateConfirmation = new UpdateConfirmation(logined_user, settings);
								updateConfirmation.setLocationRelativeTo(null);
								updateConfirmation.setVisible(true);
								dispose();
							}else if(JsonParser.getVersionPositionNumber(1, serverVersion)==JsonParser.getVersionPositionNumber(1, settings.getSetting().getVersion())){
								if(JsonParser.getVersionPositionNumber(2, serverVersion)>JsonParser.getVersionPositionNumber(2, settings.getSetting().getVersion())) {
									UpdateConfirmation updateConfirmation = new UpdateConfirmation(logined_user, settings);
									updateConfirmation.setLocationRelativeTo(null);
									updateConfirmation.setVisible(true);
									dispose();
								}else if(JsonParser.getVersionPositionNumber(2, serverVersion)==JsonParser.getVersionPositionNumber(2, settings.getSetting().getVersion())) {
									if(JsonParser.getVersionPositionNumber(3, serverVersion)>JsonParser.getVersionPositionNumber(3, settings.getSetting().getVersion())) {
										UpdateConfirmation updateConfirmation = new UpdateConfirmation(logined_user, settings);
										updateConfirmation.setLocationRelativeTo(null);
										updateConfirmation.setVisible(true);
										dispose();
									}else if(JsonParser.getVersionPositionNumber(3, serverVersion)==JsonParser.getVersionPositionNumber(3, settings.getSetting().getVersion())) {
										if(JsonParser.getVersionPositionNumber(4, serverVersion)>JsonParser.getVersionPositionNumber(4, settings.getSetting().getVersion())) {
											UpdateConfirmation updateConfirmation = new UpdateConfirmation(logined_user, settings);
											updateConfirmation.setLocationRelativeTo(null);
											updateConfirmation.setVisible(true);
											dispose();										
										}else {
											templateSelection = new TemplateSelection(logined_user, settings);
											settings.setTmpltSelectionPage(templateSelection);
											templateSelection.setLocationRelativeTo(null);
											templateSelection.setVisible(true);
											dispose();
										}
									}else {
										templateSelection = new TemplateSelection(logined_user, settings);
										settings.setTmpltSelectionPage(templateSelection);
										templateSelection.setLocationRelativeTo(null);
										templateSelection.setVisible(true);
										dispose();
									}								
								}else {
									templateSelection = new TemplateSelection(logined_user, settings);
									settings.setTmpltSelectionPage(templateSelection);
									templateSelection.setLocationRelativeTo(null);
									templateSelection.setVisible(true);
									dispose();
								}							
							}else {
								templateSelection = new TemplateSelection(logined_user, settings);
								settings.setTmpltSelectionPage(templateSelection);
								templateSelection.setLocationRelativeTo(null);
								templateSelection.setVisible(true);
								dispose();
							}
						}					
					}
				}
			}
		});
		login_btn_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		login_btn_Login.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {				
				logined_user = connection.getUser(login_username.getText(), login_password.getText());
				if(logined_user == null) {
					errorMessage.setText("Invalid username or password!");
				}else {
					Settings settings = new Settings(logined_user);
					settings.getSetting().setVersion(connection.loadProperties().getProperty("version"));
					errorMessage.setText(null);
					settings.getSetting().setLogined_user(logined_user);
					if(settings.getSetting().getVersion().equals(connection.CompareVersion())) {					
						templateSelection = new TemplateSelection(logined_user, settings);
						settings.setTmpltSelectionPage(templateSelection);
						templateSelection.setLocationRelativeTo(null);
						templateSelection.setVisible(true);
						dispose();						
					}else {
						String serverVersion = connection.CompareVersion();
						if(JsonParser.getVersionPositionNumber(1, serverVersion)>JsonParser.getVersionPositionNumber(1, settings.getSetting().getVersion())) {
							UpdateConfirmation updateConfirmation = new UpdateConfirmation(logined_user, settings);
							updateConfirmation.setLocationRelativeTo(null);
							updateConfirmation.setVisible(true);
							dispose();
						}else if(JsonParser.getVersionPositionNumber(1, serverVersion)==JsonParser.getVersionPositionNumber(1, settings.getSetting().getVersion())){
							if(JsonParser.getVersionPositionNumber(2, serverVersion)>JsonParser.getVersionPositionNumber(2, settings.getSetting().getVersion())) {
								UpdateConfirmation updateConfirmation = new UpdateConfirmation(logined_user, settings);
								updateConfirmation.setLocationRelativeTo(null);
								updateConfirmation.setVisible(true);
								dispose();
							}else if(JsonParser.getVersionPositionNumber(2, serverVersion)==JsonParser.getVersionPositionNumber(2, settings.getSetting().getVersion())) {
								if(JsonParser.getVersionPositionNumber(3, serverVersion)>JsonParser.getVersionPositionNumber(3, settings.getSetting().getVersion())) {
									UpdateConfirmation updateConfirmation = new UpdateConfirmation(logined_user, settings);
									updateConfirmation.setLocationRelativeTo(null);
									updateConfirmation.setVisible(true);
									dispose();
								}else if(JsonParser.getVersionPositionNumber(3, serverVersion)==JsonParser.getVersionPositionNumber(3, settings.getSetting().getVersion())) {
									if(JsonParser.getVersionPositionNumber(4, serverVersion)>JsonParser.getVersionPositionNumber(4, settings.getSetting().getVersion())) {
										UpdateConfirmation updateConfirmation = new UpdateConfirmation(logined_user, settings);
										updateConfirmation.setLocationRelativeTo(null);
										updateConfirmation.setVisible(true);
										dispose();										
									}else {
										templateSelection = new TemplateSelection(logined_user, settings);
										settings.setTmpltSelectionPage(templateSelection);
										templateSelection.setLocationRelativeTo(null);
										templateSelection.setVisible(true);
										dispose();
									}
								}else {
									templateSelection = new TemplateSelection(logined_user, settings);
									settings.setTmpltSelectionPage(templateSelection);
									templateSelection.setLocationRelativeTo(null);
									templateSelection.setVisible(true);
									dispose();
								}								
							}else {
								templateSelection = new TemplateSelection(logined_user, settings);
								settings.setTmpltSelectionPage(templateSelection);
								templateSelection.setLocationRelativeTo(null);
								templateSelection.setVisible(true);
								dispose();
							}							
						}else {
							templateSelection = new TemplateSelection(logined_user, settings);
							settings.setTmpltSelectionPage(templateSelection);
							templateSelection.setLocationRelativeTo(null);
							templateSelection.setVisible(true);
							dispose();
						}
					}					
				}
			}
		});
		
	}
}
