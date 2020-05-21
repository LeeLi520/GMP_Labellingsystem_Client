package com.gmp.labeling.userIOs;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class DownloadDataFile extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2232156230464453131L;
	private JPanel contentPane;
	private JLabel downloadfile_lbl_status;

	/**
	 * Create the frame.
	 */
	public DownloadDataFile() {		
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setResizable(false);
		setBounds(100, 100, 410, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.downloadfile_lbl_status = new JLabel("Updating...");
		downloadfile_lbl_status.setFont(new Font("Arial Black", Font.PLAIN, 13));
		downloadfile_lbl_status.setForeground(Color.BLACK);
		downloadfile_lbl_status.setHorizontalAlignment(SwingConstants.CENTER);
		downloadfile_lbl_status.setBounds(60, 20, 280, 25);
		contentPane.add(downloadfile_lbl_status);
	}
	
	public void finished() {
		downloadfile_lbl_status.setText("Completed.");
	}
	
	public void closeWindow() {
		try {
			Thread.sleep(300);
			dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
