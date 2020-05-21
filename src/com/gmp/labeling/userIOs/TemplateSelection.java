package com.gmp.labeling.userIOs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.User;
import com.gmp.labeling.printModels.PrintingQueue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Toolkit;
import javax.swing.SwingConstants;
import java.awt.Color;

public class TemplateSelection extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PrintingQueue printqueue;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TemplateSelection(User logined_user, Settings settings) {
		RestConnection restconnection = new RestConnection(); //Connect to Rest api
		printqueue = null;
		setTitle("Labeling System version"+restconnection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(TemplateSelection.class.getResource(restconnection.loadProperties().getProperty("logo"))));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 400);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel templateSelection_lbl_title = new JLabel("Label Template Selection");
		templateSelection_lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		templateSelection_lbl_title.setFont(new Font("Tahoma", Font.BOLD, 21));
		templateSelection_lbl_title.setBounds(50, 28, 280, 20);
		
		
		JComboBox templateSelection_comboBox = new JComboBox();		
		templateSelection_comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		List<String> list = restconnection.getUserTemplateList(logined_user);
		if(list != null) {
			String[] templateArray = new String[list.size()];
			printqueue = new PrintingQueue(settings.getSetting().getLogined_user(), restconnection);
			int i = 0;
			for(String temp : list) {
				templateArray[i] = temp;
				i++;
			}
			templateSelection_comboBox.setModel(new DefaultComboBoxModel(templateArray));
			templateSelection_comboBox.setSelectedIndex(0);
		}else {
			templateSelection_comboBox.setSelectedItem(null);
		}		
		templateSelection_comboBox.setBounds(48, 68, 320, 23);
		
		JButton templateSelection_btn_Next = new JButton("Next");

		templateSelection_btn_Next.setFont(new Font("Tahoma", Font.PLAIN, 12));
		templateSelection_btn_Next.setBounds(50, 294, 90, 20);
				
		JButton templateSelection_btn_Exit = new JButton("Exit");
		
		templateSelection_btn_Exit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		templateSelection_btn_Exit.setBounds(275, 294, 90, 20);
		
		JLabel templateSelection_lbl_labelPreview = new JLabel();
		
		templateSelection_lbl_labelPreview.setForeground(new Color(0, 0, 0));
		templateSelection_lbl_labelPreview.setBounds(48, 100, 320, 180);
		templateSelection_lbl_labelPreview.setHorizontalAlignment(SwingConstants.CENTER);
		templateSelection_lbl_labelPreview.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		
		if(templateSelection_comboBox.getSelectedItem()==null) {		
			templateSelection_lbl_labelPreview.setFont(new Font("Tahoma", Font.PLAIN, 20));
			templateSelection_lbl_labelPreview.setText("No template available... Please contact IT department for related assignment");
		}else {						
		     switch (templateSelection_comboBox.getSelectedItem().toString()) {
		          case "Deviation":					        	  
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("deviation"))));
		        	  break;					    	  
		          case "Sampling":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("sample"))));
		        	  break;					        	  
		          case "Product":	
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("product"))));
		        	  break;
		          case "Asset":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("asset"))));
					  break;
		          case "ExternalAnalysis":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("externalAnalysis"))));
					  break;
		          case "FProduct":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("fProduct"))));
					  break;
		          case "PalletLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("pallet"))));
					  break;								  
		          case "CustomerSampleLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("customerSample"))));
					  break;
		          case "IdentifyDiscardedLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("identifyDiscarded"))));
		        	  break;
		          case "QcApprovalLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("qcapproval"))));
		        	  break;
		          case "InterimApprovalLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("interimApproval"))));
		        	  break;
                  case "SpecialItemLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("specialitem"))));
					  break;
                  case "HW_PlacardLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("placard"))));
					  break;
		          case "HW_Quarantine":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_quarantine"))));
					  break;
                  case "HW_ReleasedLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_released"))));
					  break;
                  case "HW_DispensaryLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_dispensary"))));
					  break;
                  case "TrackableShipperLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("trackable"))));
					  break;
                  case "DairyProductLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("dairy"))));
                      break;
                  case "SachetProductLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("sachet"))));
                	  break;
                  case "NWInnerTunLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("nw_inner"))));
                	  break;
                  case "NWOuterTunLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("nw_outer"))));
                	  break;
                  case "SachetVGShipperLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("sachetVG"))));
                	  break; 
                  case "HW_ProvisionalReleaseLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_provisionalrelease"))));
                	  break; 
                  case "HW_Reject":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_reject"))));
                	  break; 
                  case "HW_ReleaseForSupplyLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_releaseforsupply"))));
                	  break; 
                  case "HW_ReturnedFromProductionLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_returnedfromproduction"))));
                	  break; 
                  case "HW_BulkLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_bulk"))));
                	  break; 
                  case "FPQuarantine":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("fpquarantine"))));
                	  break; 
                  case "SachetSemiProductLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("sachetsemi"))));               	  
                	  break;
		        	  
		    }
		}
				
		templateSelection_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (templateSelection_comboBox.getSelectedItem().toString()) {
		          case "Deviation":					        	  
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("deviation"))));
		        	  break;					    	  
		          case "Sampling":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("sample"))));
		        	  break;					        	  
		          case "Product":	
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("product"))));
		        	  break;
		          case "Asset":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("asset"))));
					  break;
		          case "ExternalAnalysis":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("externalAnalysis"))));
					  break;
		          case "FProduct":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("fProduct"))));
					  break;
		          case "PalletLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("pallet"))));
					  break;							  
		          case "CustomerSampleLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("customerSample"))));
					  break;
		          case "IdentifyDiscardedLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("identifyDiscarded"))));
		        	  break;
		          case "QcApprovalLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("qcapproval"))));
		        	  break;
		          case "InterimApprovalLabel":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("interimApproval"))));
		        	  break;
                  case "SpecialItemLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("specialitem"))));
					  break;
                  case "HW_PlacardLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("placard"))));
					  break;
                  case "HW_Quarantine":
		        	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_quarantine"))));
					  break;
                  case "HW_ReleasedLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_released"))));
					  break;
                  case "HW_DispensaryLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_dispensary"))));
					  break;
                  case "TrackableShipperLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("trackable"))));
					  break;
                  case "DairyProductLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("dairy"))));
                      break;
                  case "SachetProductLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("sachet"))));
                	  break;
                  case "NWInnerTunLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("nw_inner"))));
                	  break;
                  case "NWOuterTunLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("nw_outer"))));
                	  break;
                  case "SachetVGShipperLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("sachetVG"))));
                	  break;
                  case "HW_ProvisionalReleaseLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_provisionalrelease"))));
                	  break; 
                  case "HW_Reject":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_reject"))));
                	  break; 
                  case "HW_ReleaseForSupplyLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_releaseforsupply"))));
                	  break; 
                  case "HW_ReturnedFromProductionLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_returnedfromproduction"))));
                	  break; 
                  case "HW_BulkLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("hw_bulk"))));
                	  break; 
                  case "FPQuarantine":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("fpquarantine"))));
                	  break; 
                  case "SachetSemiProductLabel":
                	  templateSelection_lbl_labelPreview.setIcon(new ImageIcon(Login.class.getResource(restconnection.loadProperties().getProperty("sachetsemi"))));       
                	  break;
		    }
			}
		});
				
		templateSelection_btn_Next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(templateSelection_comboBox.getSelectedItem()==null) {						
						JOptionPane.showMessageDialog(settings.getComponentPage(),
							    "No available printing template for the user",
							    "Inane error",
							    JOptionPane.ERROR_MESSAGE);
					}else {						
					     switch (templateSelection_comboBox.getSelectedItem().toString()) {
					          case "Deviation":					        	  
					        	  settings.setVisible(false);
					        	  DeviationLabelIO deviation = new DeviationLabelIO(settings, printqueue);
					        	  settings.setComponentPage(deviation);
					        	  deviation.setLocationRelativeTo(null);
					        	  deviation.setVisible(true);
					        	  dispose();
					        	  break;					    	  
					          case "Sampling":
					        	  SamplingLabelIO sample = new SamplingLabelIO(settings, printqueue);
					        	  settings.setComponentPage(sample);
					        	  sample.setLocationRelativeTo(null);
					        	  sample.setVisible(true);
					        	  dispose();
					        	  break;					        	  
					          case "Product":	
					        	  ProductLabelIO product = new ProductLabelIO(settings, printqueue);
					        	  settings.setComponentPage(product);
					        	  product.setLocationRelativeTo(null);
					        	  product.setVisible(true);
					        	  dispose();
					        	  break;
					          case "Asset":
					        	  AssetLabelIO asset = new AssetLabelIO(settings, printqueue);
					        	  settings.setComponentPage(asset);
					        	  asset.setLocationRelativeTo(null);
					        	  asset.setVisible(true);
					        	  dispose();
								  break;
					          case "ExternalAnalysis":
					        	  ExternalAnalysisLabelIO externalAnalysis = new ExternalAnalysisLabelIO(settings, printqueue);
					        	  settings.setComponentPage(externalAnalysis);
					        	  externalAnalysis.setLocationRelativeTo(null);
					        	  externalAnalysis.setVisible(true);
					        	  dispose();
								  break;
					          case "FProduct":
					        	  FinishProductLabelIO fProductLabel = new FinishProductLabelIO(settings, printqueue);
					        	  settings.setComponentPage(fProductLabel);
					        	  fProductLabel.setLocationRelativeTo(null);
					        	  fProductLabel.setVisible(true);
					        	  dispose();
								  break;
					          case "PalletLabel":
					        	  PalletLabelIO palletLabel = new PalletLabelIO(settings, printqueue);
					        	  settings.setComponentPage(palletLabel);
					        	  palletLabel.setLocationRelativeTo(null);
					        	  palletLabel.setVisible(true);
					        	  dispose();
								  break;
					          case "HW_Quarantine":
					        	  HW_ProductlabelIO hw_productlabel = new HW_ProductlabelIO(settings, printqueue);
					        	  settings.setComponentPage(hw_productlabel);
					        	  hw_productlabel.setLocationRelativeTo(null);
					        	  hw_productlabel.setVisible(true);
					        	  dispose();
								  break;								  
					          case "CustomerSampleLabel":
					        	  CustomerSampleIO customersamplelabel = new CustomerSampleIO(settings, printqueue);
					        	  settings.setComponentPage(customersamplelabel);
					        	  customersamplelabel.setLocationRelativeTo(null);
					        	  customersamplelabel.setVisible(true);
					        	  dispose();
								  break;
					          case "IdentifyDiscardedLabel":
					        	  IdentifyDiscardedIO identifydiscardedlabel = new IdentifyDiscardedIO(settings, printqueue);
					        	  settings.setComponentPage(identifydiscardedlabel);
					        	  identifydiscardedlabel.setLocationRelativeTo(null);
					        	  identifydiscardedlabel.setVisible(true);
					        	  dispose();
					        	  break;
					          case "QcApprovalLabel":
					        	  QcApprovalLabelIO qcapprovallabel = new QcApprovalLabelIO(settings, printqueue);
					        	  settings.setComponentPage(qcapprovallabel);
					        	  qcapprovallabel.setLocationRelativeTo(null);
					        	  qcapprovallabel.setVisible(true);
					        	  dispose();
					        	  break;
					          case "InterimApprovalLabel":
					        	  InterimApprovalLabelIO interimApprovalLabel = new  InterimApprovalLabelIO(settings, printqueue);
					        	  settings.setComponentPage(interimApprovalLabel);
					        	  interimApprovalLabel.setLocationRelativeTo(null);
					        	  interimApprovalLabel.setVisible(true);
					        	  dispose();
					        	  break;
					          case "SpecialItemLabel":
					        	  SpecialItemLabelIO specialItemLabel = new SpecialItemLabelIO(settings, printqueue);
					        	  settings.setComponentPage(specialItemLabel);
					        	  specialItemLabel.setLocationRelativeTo(null);
					        	  specialItemLabel.setVisible(true);
					        	  dispose();
								  break;
					          case "HW_PlacardLabel":
					        	  PlacardLabelIO placardLabel = new PlacardLabelIO(settings, printqueue);
					        	  settings.setComponentPage(placardLabel);
					        	  placardLabel.setLocationRelativeTo(null);
					        	  placardLabel.setVisible(true);
					        	  dispose();
								  break;
					          case "HW_ReleasedLabel":
					        	  HW_ReleasedLabelIO releasedlabel = new HW_ReleasedLabelIO(settings, printqueue);
					        	  settings.setComponentPage(releasedlabel);
					        	  releasedlabel.setLocationRelativeTo(null);
					        	  releasedlabel.setVisible(true);
					        	  dispose();
								  break;
					          case "HW_DispensaryLabel":
					        	  HW_DispensaryLabelIO dispensarylabel = new HW_DispensaryLabelIO(settings, printqueue);
					        	  settings.setComponentPage(dispensarylabel);
					        	  dispensarylabel.setLocationRelativeTo(null);
					        	  dispensarylabel.setVisible(true);
					        	  dispose();
								  break;
			                  case "TrackableShipperLabel":
			                	  TrackableShipperLabelIO trackableshipper = new TrackableShipperLabelIO(settings, printqueue);
					        	  settings.setComponentPage(trackableshipper);
					        	  trackableshipper.setLocationRelativeTo(null);
					        	  trackableshipper.setVisible(true);
					        	  dispose();
								  break;
			                  case "DairyProductLabel":
			                	  DairyProductLabelIO dairyproductlabel = new DairyProductLabelIO(settings, printqueue);
			                	  settings.setComponentPage(dairyproductlabel);
			                	  dairyproductlabel.setLocationRelativeTo(null);
			                	  dairyproductlabel.setVisible(true);
			                	  dispose();
			                      break;			                      
			                  case "SachetProductLabel":
			                	  SachetProductLabelIO sachetproductlabel = new SachetProductLabelIO(settings, printqueue);
			                	  settings.setComponentPage(sachetproductlabel);
			                	  sachetproductlabel.setLocationRelativeTo(null);
			                	  sachetproductlabel.setVisible(true);
			                	  dispose();
			                	  break;
			                  case "NWInnerTunLabel":
			                	  NWInnerTunLabelIO innertunlabel = new NWInnerTunLabelIO(settings, printqueue);
			                	  settings.setComponentPage(innertunlabel);
			                	  innertunlabel.setLocationRelativeTo(null);
			                	  innertunlabel.setVisible(true);
			                	  dispose();			                 	 
			                	  break;
			                  case "NWOuterTunLabel":
			                	  NWOuterTunLabelIO outertunlabel = new NWOuterTunLabelIO(settings, printqueue);
			                	  settings.setComponentPage(outertunlabel);
			                	  outertunlabel.setLocationRelativeTo(null);
			                	  outertunlabel.setVisible(true);
			                	  dispose();			                 	 
			                	  break;
			                  case "SachetVGShipperLabel":
			                	  SachetVGShipperLabelIO sachetVGShipperLabel = new SachetVGShipperLabelIO(settings, printqueue);
			                	  settings.setComponentPage(sachetVGShipperLabel);
			                	  sachetVGShipperLabel.setLocationRelativeTo(null);
			                	  sachetVGShipperLabel.setVisible(true);
			                	  dispose();
			                	  break;
			                  case "HW_ProvisionalReleaseLabel":
			                	  HW_ProvisionalReleaseLabelIO hw_provisionalReleaseLabel = new HW_ProvisionalReleaseLabelIO(settings, printqueue);
			                	  settings.setComponentPage(hw_provisionalReleaseLabel);
			                	  hw_provisionalReleaseLabel.setLocationRelativeTo(null);
			                	  hw_provisionalReleaseLabel.setVisible(true);
			                	  dispose();
			                	  break; 
			                  case "HW_Reject":
			                	  HW_RejectLabelIO hw_rejectLabel = new HW_RejectLabelIO(settings, printqueue);
			                	  settings.setComponentPage(hw_rejectLabel);
			                	  hw_rejectLabel.setLocationRelativeTo(null);
			                	  hw_rejectLabel.setVisible(true);
			                	  dispose();
			                	  break; 
			                  case "HW_ReleaseForSupplyLabel":
			                	  HW_ReleaseForSupplyLabelIO hw_releaseForSupplyLabel = new HW_ReleaseForSupplyLabelIO(settings, printqueue);
			                	  settings.setComponentPage(hw_releaseForSupplyLabel);
			                	  hw_releaseForSupplyLabel.setLocationRelativeTo(null);
			                	  hw_releaseForSupplyLabel.setVisible(true);
			                	  dispose();
			                	  break; 
			                  case "HW_ReturnedFromProductionLabel":
			                	  HW_ReturnedFromProductionLabelIO hw_returnedFromProductionLabel = new HW_ReturnedFromProductionLabelIO(settings, printqueue);
			                	  settings.setComponentPage(hw_returnedFromProductionLabel);
			                	  hw_returnedFromProductionLabel.setLocationRelativeTo(null);
			                	  hw_returnedFromProductionLabel.setVisible(true);
			                	  dispose();
			                	  break; 
			                  case "HW_BulkLabel":
			                	  HW_BulkLabelIO hw_bulkLabel = new HW_BulkLabelIO(settings, printqueue);
			                	  settings.setComponentPage(hw_bulkLabel);
			                	  hw_bulkLabel.setLocationRelativeTo(null);
			                	  hw_bulkLabel.setVisible(true);
			                	  dispose();
			                	  break; 
			                  case "FPQuarantine":
			                	  FPQuarantineLabelIO fpquantrantlabel = new FPQuarantineLabelIO(settings, printqueue);
			                	  settings.setComponentPage(fpquantrantlabel);
			                	  fpquantrantlabel.setLocationRelativeTo(null);
			                	  fpquantrantlabel.setVisible(true);
			                	  dispose();
			                	  break; 
			                  case "SachetSemiProductLabel":
			                	  SachetSemiProductLabelIO sachetsemiproduct = new SachetSemiProductLabelIO(settings, printqueue);
			                	  settings.setComponentPage(sachetsemiproduct);
			                	  sachetsemiproduct.setLocationRelativeTo(null);
			                	  sachetsemiproduct.setVisible(true);
			                	  dispose();
			                	  break;			                	  
					    }
					}
			}
		});
		
		templateSelection_btn_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(templateSelection_lbl_title);
		contentPane.add(templateSelection_btn_Next);
		contentPane.add(templateSelection_btn_Exit);
		contentPane.add(templateSelection_comboBox);
		contentPane.add(templateSelection_lbl_labelPreview);
		
		JLabel label = new JLabel("Designed and Implemented by Lee.L");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setBounds(235, 325, 170, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(135, 340, 280, 20);
		contentPane.add(label_1);

	}
}