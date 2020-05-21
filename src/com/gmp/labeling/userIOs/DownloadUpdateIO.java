package com.gmp.labeling.userIOs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;

import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.SwingConstants;

public class DownloadUpdateIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6265711681774244119L;
	private JPanel contentPane;
	private JLabel Download_lbl_status;
	private JProgressBar progressBar;
	private JLabel Download_lbl_percent;

	public DownloadUpdateIO() {
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(TemplateSelection.class.getResource(connection.loadProperties().getProperty("logo"))));
		setResizable(false);
		setBounds(100, 100, 430, 140);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(50, 48, 330, 20);
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		contentPane.add(progressBar);
		
		
		Download_lbl_status = new JLabel("Downloading...");
		Download_lbl_status.setFont(new Font("Arial", Font.BOLD, 20));
		Download_lbl_status.setBounds(140, 15, 150, 25);
		contentPane.add(Download_lbl_status);
		
	    Download_lbl_percent = new JLabel("0%");
	    Download_lbl_percent.setHorizontalAlignment(SwingConstants.CENTER);
		Download_lbl_percent.setFont(new Font("Arial", Font.BOLD, 14));
		Download_lbl_percent.setBounds(180, 70, 50, 20);
		contentPane.add(Download_lbl_percent);
	}
	
	public void setProgress(int currentProgress){
		this.progressBar.setValue(currentProgress);
		String display = currentProgress+"%";
		this.Download_lbl_percent.setText(display);
	}
	
	public void completeStatus(){
		this.Download_lbl_status.setText("Completed.");
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
