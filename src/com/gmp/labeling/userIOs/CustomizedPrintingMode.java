package com.gmp.labeling.userIOs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;

import java.awt.Graphics2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomizedPrintingMode extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8663283133459372412L;
	private JPanel contentPane;
	private ImageIcon image;
	private JLabel CustomLP_printingCountDisplay_value;
	private List<Label> tempList;
	private JFrame currentIOFrame;
	private RestConnection connection;

	
	public JLabel getCustomLP_printingCountDisplay_value() {
		return CustomLP_printingCountDisplay_value;
	}

	public void setCustomLP_printingCountDisplay_value(JLabel customLP_printingCountDisplay_value) {
		CustomLP_printingCountDisplay_value = customLP_printingCountDisplay_value;
	}

	public CustomizedPrintingMode(Settings settings, PrintingQueue printqueue) {
		connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeviationLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		currentIOFrame = null;
		tempList = new ArrayList<>();
		RestConnection restconnection = new RestConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel panel = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane(panel);

		scrollPane.setBounds(0, 0, 784, 400);
		panel.setBorder(BorderFactory.createTitledBorder("Available Templates:"));
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{230, 230, 230};
		gbl_panel.rowHeights = new int[]{23, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		contentPane.add(scrollPane);
		List<String> list = restconnection.getUserTemplateList(settings.getSetting().getLogined_user());
		ImageButtonGenerator(list, panel, settings, printqueue);
		
		JLabel CustomLP_printingCountDisplay_title = new JLabel("Current In Queue Labels:");
		CustomLP_printingCountDisplay_title.setFont(new Font("Tahoma", Font.BOLD, 12));
		CustomLP_printingCountDisplay_title.setBounds(130, 420, 165, 20);
		contentPane.add(CustomLP_printingCountDisplay_title);
		
		CustomLP_printingCountDisplay_value = new JLabel("0");
		CustomLP_printingCountDisplay_value.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CustomLP_printingCountDisplay_value.setForeground(Color.BLUE);
		CustomLP_printingCountDisplay_value.setBounds(300, 420, 50, 20);
		
		
		JButton CustomLP_btn_printAll = new JButton("Print All");
		CustomLP_btn_printAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(settings.getSetting().isLocalPrintingMode()) {
					PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), null, 0, 620);
					printio.setLocationRelativeTo(null);
					printio.setVisible(true);
					CustomLP_printingCountDisplay_value.setText("0");
				}else {
					PrintingIO printio = new PrintingIO(settings, printqueue, String.valueOf(printqueue.getList().size()), settings.getSetting().getIpAddress(), settings.getSetting().getPort(), 620);
					printio.setLocationRelativeTo(null);
					printio.setVisible(true);
					CustomLP_printingCountDisplay_value.setText("0");
				}						
			}
		});
		CustomLP_btn_printAll.setFont(new Font("Tahoma", Font.PLAIN, 11));
		CustomLP_btn_printAll.setBounds(180, 475, 110, 20);
		contentPane.add(CustomLP_btn_printAll);
		
		JButton CustomLP_btn_clear = new JButton("Clear Queue");
		CustomLP_btn_clear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		CustomLP_btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printqueue.remoreAllObject();
				CustomLP_printingCountDisplay_value.setText(String.valueOf(printqueue.getList().size()));
			}
		});
		CustomLP_btn_clear.setBounds(330, 475, 110, 20);
		contentPane.add(CustomLP_btn_clear);
		
		JButton CustomLP_ClearP = new JButton("Clear Previous");
		CustomLP_ClearP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Label temp: tempList) {
					printqueue.getList().remove(temp);
				}
				CustomLP_printingCountDisplay_value.setText(String.valueOf(printqueue.getList().size()));
			}
		});
		CustomLP_ClearP.setFont(new Font("Tahoma", Font.PLAIN, 11));
		CustomLP_ClearP.setBounds(480, 475, 110, 20);
		contentPane.add(CustomLP_ClearP);
		
		JButton CustomLP_btn_back = new JButton("Back");
		CustomLP_btn_back.setFont(new Font("Tahoma", Font.PLAIN, 11));
		CustomLP_btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentIOFrame!= null) {
					currentIOFrame.dispose();
				}
				settings.getTmpltSelectionPage().setLocationRelativeTo(null);
				settings.getTmpltSelectionPage().setVisible(true);
				dispose();
			}
		});
		CustomLP_btn_back.setBounds(630, 475, 110, 20);
		contentPane.add(CustomLP_btn_back);
		
		JButton CustomLP_btn_settings = new JButton("Settings");
		CustomLP_btn_settings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		CustomLP_btn_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);				
				dispose();
			}
		});
		CustomLP_btn_settings.setBounds(30, 475, 110, 20);
		contentPane.add(CustomLP_printingCountDisplay_value);
		contentPane.add(CustomLP_btn_settings);
		
		JLabel CustomLP_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		CustomLP_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
		CustomLP_copyright_company.setBounds(500, 525, 280, 20);
		contentPane.add(CustomLP_copyright_company);
		
		JLabel CustomLP_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
		CustomLP_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		CustomLP_copyright_developer.setBounds(600, 510, 170, 20);
		contentPane.add(CustomLP_copyright_developer);
	}
	
	public void ImageButtonGenerator(List<String> list, JPanel panel, Settings settings, PrintingQueue printqueue) {
		GridBagConstraints gbc_imageButton = new GridBagConstraints();
		JButton imageButton;
		int count = 0;
		for(String temp : list) {
			switch(temp) {
			case "Deviation":
				try {
					URL url = getClass().getResource(connection.loadProperties().getProperty("deviation"));
					image = new ImageIcon(ImageIO.read(url));
					Image result = getScaledImage(image.getImage(),200, 150);
					image.setImage(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				imageButton = new JButton("Deviation Label");
				imageButton.setIcon(image);
				imageButton.setHorizontalTextPosition(AbstractButton.CENTER);
				imageButton.setVerticalTextPosition(AbstractButton.BOTTOM);
				imageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(currentIOFrame!= null) {
							currentIOFrame.dispose();
						}
						CustomizedPrinting_DeviationLabelIO deviation = new CustomizedPrinting_DeviationLabelIO(settings, printqueue, tempList, CustomLP_printingCountDisplay_value, 1350, 220);
						currentIOFrame = deviation;
						deviation.setResizable(false);					
						deviation.setVisible(true);						
					}
				});
				
				gbc_imageButton.weightx =1;
				gbc_imageButton.anchor = GridBagConstraints.LINE_START;
				gbc_imageButton.gridx = count%3;
				gbc_imageButton.gridy = count/3;
				panel.add(imageButton, gbc_imageButton);
				count++;
				break;
			case "Product":
				try {
					URL url = getClass().getResource(connection.loadProperties().getProperty("product"));
					image = new ImageIcon(ImageIO.read(url));
					Image result = getScaledImage(image.getImage(),200, 150);
					image.setImage(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageButton = new JButton("Product Label");
				imageButton.setIcon(image);
				imageButton.setHorizontalTextPosition(AbstractButton.CENTER);
				imageButton.setVerticalTextPosition(AbstractButton.BOTTOM);
				imageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(currentIOFrame!= null) {
							currentIOFrame.dispose();
						}
						CustomizedPrinting_ProductLabelIO product = new CustomizedPrinting_ProductLabelIO(settings, printqueue, tempList, CustomLP_printingCountDisplay_value, 1350, 220);
						currentIOFrame = product;
						product.setResizable(false);					
						product.setVisible(true);						
					}
				});
				
				gbc_imageButton.weightx =1;
				gbc_imageButton.anchor = GridBagConstraints.LINE_START;
				gbc_imageButton.gridx = count%3;
				gbc_imageButton.gridy = count/3;
				panel.add(imageButton, gbc_imageButton);
				count++;
				break;
			case "Sampling":
				try {
					URL url = getClass().getResource(connection.loadProperties().getProperty("sample"));
					image = new ImageIcon(ImageIO.read(url));
					Image result = getScaledImage(image.getImage(),200, 150);
					image.setImage(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageButton = new JButton("Sample Label");
				imageButton.setIcon(image);
				imageButton.setHorizontalTextPosition(AbstractButton.CENTER);
				imageButton.setVerticalTextPosition(AbstractButton.BOTTOM);
				imageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(currentIOFrame!= null) {
							currentIOFrame.dispose();
						}
						CustomizedPrinting_SamplingLabelIO sample = new CustomizedPrinting_SamplingLabelIO(settings, printqueue, tempList, CustomLP_printingCountDisplay_value, 1350, 220);
						currentIOFrame = sample;
						sample.setResizable(false);					
						sample.setVisible(true);						
					}
				});
				
				gbc_imageButton.weightx =1;
				gbc_imageButton.anchor = GridBagConstraints.LINE_START;
				gbc_imageButton.gridx = count%3;
				gbc_imageButton.gridy = count/3;
				panel.add(imageButton, gbc_imageButton);
				count++;
				try {
					URL url = getClass().getResource(connection.loadProperties().getProperty("hwsample"));
					image = new ImageIcon(ImageIO.read(url));
					Image result = getScaledImage(image.getImage(),200, 150);
					image.setImage(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageButton = new JButton("HW Sample Label");
				imageButton.setIcon(image);
				imageButton.setHorizontalTextPosition(AbstractButton.CENTER);
				imageButton.setVerticalTextPosition(AbstractButton.BOTTOM);
				imageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(currentIOFrame!= null) {
							currentIOFrame.dispose();
						}
						CustomizedPrinting_HW_SamplingLabelIO hw_sample = new CustomizedPrinting_HW_SamplingLabelIO(settings, printqueue, tempList, CustomLP_printingCountDisplay_value, 1350, 220);
						currentIOFrame = hw_sample;
						hw_sample.setResizable(false);					
						hw_sample.setVisible(true);						
					}
				});
				
				gbc_imageButton.weightx =1;
				gbc_imageButton.anchor = GridBagConstraints.LINE_START;
				gbc_imageButton.gridx = count%3;
				gbc_imageButton.gridy = count/3;
				panel.add(imageButton, gbc_imageButton);
				count++;
				break;				
			case "Asset":
				try {
					URL url = getClass().getResource(connection.loadProperties().getProperty("asset"));
					image = new ImageIcon(ImageIO.read(url));
					Image result = getScaledImage(image.getImage(),200, 150);
					image.setImage(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageButton = new JButton("Asset Label");
				imageButton.setIcon(image);
				imageButton.setHorizontalTextPosition(AbstractButton.CENTER);
				imageButton.setVerticalTextPosition(AbstractButton.BOTTOM);
				imageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(currentIOFrame!= null) {
							currentIOFrame.dispose();
						}
						CustomizedPrinting_AssetLabelIO asset = new CustomizedPrinting_AssetLabelIO(settings, printqueue, tempList, CustomLP_printingCountDisplay_value, 1350, 220);
						currentIOFrame = asset;
						asset.setResizable(false);					
						asset.setVisible(true);						
					}
				});
				
				gbc_imageButton.weightx =1;
				gbc_imageButton.anchor = GridBagConstraints.LINE_START;
				gbc_imageButton.gridx = count%3;
				gbc_imageButton.gridy = count/3;
				panel.add(imageButton, gbc_imageButton);
				count++;				
				break;
				
			case "ExternalAnalysis":
				try {
					URL url = getClass().getResource(connection.loadProperties().getProperty("externalAnalysis"));
					image = new ImageIcon(ImageIO.read(url));
					Image result = getScaledImage(image.getImage(),200, 150);
					image.setImage(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageButton = new JButton("External Analysis");
				imageButton.setIcon(image);
				imageButton.setHorizontalTextPosition(AbstractButton.CENTER);
				imageButton.setVerticalTextPosition(AbstractButton.BOTTOM);
				imageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(currentIOFrame!= null) {
							currentIOFrame.dispose();
						}
						CustomizedPrinting_ExternalAnalysisLabelIO exalabel = new CustomizedPrinting_ExternalAnalysisLabelIO(settings, printqueue, tempList, CustomLP_printingCountDisplay_value, 1350, 220);
						currentIOFrame = exalabel;
						exalabel.setResizable(false);					
						exalabel.setVisible(true);						
					}
				});
				
				gbc_imageButton.weightx =1;
				gbc_imageButton.anchor = GridBagConstraints.LINE_START;
				gbc_imageButton.gridx = count%3;
				gbc_imageButton.gridy = count/3;
				panel.add(imageButton, gbc_imageButton);
				count++;				
				break;
			}
		}
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	public void changePrintingValue(String value) {
		this.CustomLP_printingCountDisplay_value.setText(value);
	}
}
