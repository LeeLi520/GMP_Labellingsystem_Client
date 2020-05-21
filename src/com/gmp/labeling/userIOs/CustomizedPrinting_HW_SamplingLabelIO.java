package com.gmp.labeling.userIOs;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.HandWritingSamplingLabel;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;

public class CustomizedPrinting_HW_SamplingLabelIO extends JFrame {

	    /**
	 * 
	 */
	    private static final long serialVersionUID = 5032731449955604434L;
		private JPanel contentPane;		
		private JTextField sample_printing_Number;

		public CustomizedPrinting_HW_SamplingLabelIO(Settings settings, PrintingQueue printqueue, List<Label> tempList, JLabel printCount, int x, int y) {
			RestConnection connection = new RestConnection();
			setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
			setIconImage(Toolkit.getDefaultToolkit().getImage(SamplingLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(x, y, 400, 250);
			setResizable(false);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("HW Sampling Label Printing");
			lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
			lblNewLabel.setBounds(60, 18, 280, 30);
			contentPane.add(lblNewLabel);
			
			JLabel sample_lbl_printingNumber = new JLabel("Printing Number:");
			sample_lbl_printingNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
			sample_lbl_printingNumber.setBounds(60, 80, 110, 20);
			contentPane.add(sample_lbl_printingNumber);
					
			sample_printing_Number = new JTextField();
			sample_printing_Number.setBounds(180, 80, 120, 20);
			contentPane.add(sample_printing_Number);
			sample_printing_Number.setColumns(10);
					
			JButton sample_btn_addToQueue = new JButton("Add To Queue");
			sample_btn_addToQueue.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						if(printChecker(sample_printing_Number.getText(), settings)) {
							tempList.clear();
							int printNumber = Integer.valueOf(sample_printing_Number.getText());
							int i = 0;
							while(i<printNumber) {
								HandWritingSamplingLabel hw_sample = new HandWritingSamplingLabel();
								printqueue.addLabelToQueue(hw_sample);
								tempList.add(hw_sample);
								i++;
							}
						}
						printCount.setText(String.valueOf(printqueue.getList().size()));
				}
			});
			sample_btn_addToQueue.setBounds(60, 130, 120, 20);
					
			JButton sample_btn_back = new JButton("Close");
			sample_btn_back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			sample_btn_back.setBounds(220, 130, 120, 20);
			contentPane.add(sample_btn_back);
			contentPane.add(sample_btn_addToQueue);
			
			JLabel sample_copyright_developer = new JLabel("Designed and Implemented by Lee.L");
			sample_copyright_developer.setFont(new Font("Tahoma", Font.PLAIN, 10));
			sample_copyright_developer.setBounds(215, 165, 170, 20);
			contentPane.add(sample_copyright_developer);
			
			JLabel sample_copyright_company = new JLabel("Copyright \u00A9 2018 GMP IT Department. All Rights Reserved.");
			sample_copyright_company.setFont(new Font("Tahoma", Font.PLAIN, 10));
			sample_copyright_company.setBounds(115, 180, 280, 20);
			contentPane.add(sample_copyright_company);
	}
		
		public boolean printChecker(String printingNumber, Settings settings) {
			try {
				Integer.valueOf(printingNumber);
			}catch(Exception e){
				JOptionPane.showMessageDialog(settings.getComponentPage(),
					    "Invalid or missing printing number.",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE
					    );
				return false;
			}
			return true;	
		}

}
