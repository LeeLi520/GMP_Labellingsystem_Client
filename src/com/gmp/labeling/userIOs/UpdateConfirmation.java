package com.gmp.labeling.userIOs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.User;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateConfirmation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7289768039770192416L;
	private JPanel contentPane;

	public UpdateConfirmation(User logined_user, Settings settings) {
		RestConnection connection = new RestConnection();
		setTitle("System Update");
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(TemplateSelection.class.getResource(connection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 420, 165);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("There is an available update for the system. ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(70, 30, 265, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Do you want to update the system?");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(70, 50, 265, 20);
		contentPane.add(lblNewLabel_1);
		
		JButton updateIO_btn_update = new JButton("Update");
		updateIO_btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.updateApplication();
				dispose();
			}
		});
		updateIO_btn_update.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateIO_btn_update.setBounds(50, 90, 90, 20);
		contentPane.add(updateIO_btn_update);
		
		JButton updateIO_btn_notNow = new JButton("Later");
		updateIO_btn_notNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				TemplateSelection templateSelection = new TemplateSelection(logined_user, settings);
				settings.setTmpltSelectionPage(templateSelection);
				templateSelection.setLocationRelativeTo(null);
				templateSelection.setVisible(true);
				dispose();	
			}
		});
		updateIO_btn_notNow.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateIO_btn_notNow.setBounds(280, 90, 90, 20);
		contentPane.add(updateIO_btn_notNow);
	}
}
