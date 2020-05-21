package com.gmp.labeling.userIOs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.printModels.PrintingQueue;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

public class PrintingIO extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel PrintingIO_status;
	private JLabel PrintingIO_jobcount;
	private JButton PrintingIO_btn_cancel;
	private PrintingQueue queue;
	private SwingWorker<Boolean, Integer> worker;

	public PrintingIO(Settings settings, PrintingQueue printqueue, String jobcount, String ipAddress, int port, int pageDelay) {
		queue = printqueue;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		RestConnection connection = new RestConnection();
		setTitle("Labeling System version"+connection.loadProperties().getProperty("version"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductLabelIO.class.getResource(connection.loadProperties().getProperty("logo"))));
		setBounds(100, 100, 420, 148);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JLabel PrintingIO_lbl_printstatus = new JLabel("Printing Status:");
		PrintingIO_lbl_printstatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		PrintingIO_lbl_printstatus.setBounds(48, 35, 110, 20);
		contentPane.add(PrintingIO_lbl_printstatus);
				
		PrintingIO_btn_cancel = new JButton("Cancel");
		PrintingIO_btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel(worker, printqueue);
				dispose();
			}
		});
		PrintingIO_btn_cancel.setBounds(160, 70, 90, 20);
		contentPane.add(PrintingIO_btn_cancel);
		
		PrintingIO_status = new JLabel("(0");
		PrintingIO_status.setHorizontalAlignment(SwingConstants.RIGHT);
		PrintingIO_status.setFont(new Font("Tahoma", Font.PLAIN, 12));
		PrintingIO_status.setBounds(148, 35, 45, 20);
		contentPane.add(PrintingIO_status);
		
		PrintingIO_jobcount = new JLabel("/" + jobcount+")");
		PrintingIO_jobcount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		PrintingIO_jobcount.setHorizontalAlignment(SwingConstants.LEFT);
		PrintingIO_jobcount.setBounds(193, 35, 65, 20);
		contentPane.add(PrintingIO_jobcount);
		worker = new SwingWorker<Boolean, Integer>() {
			   @Override
			   protected Boolean doInBackground() throws Exception {
				   int i = 0;
				   if(queue.listIsEmpty()) {
					 return false;  
				   }else {
				   Thread.sleep(1000);
				    for (Label label : queue.getList()) {
				    	if(ipAddress==null) {
				    		label.printLocal(label.printZPLFormat());
				    		connection.sendLog(settings.getSetting().getLogined_user(), label);
				    		i++;
						    publish(i);
						    Thread.sleep(pageDelay);	
				    	}else {
				    		label.printNetwork(ipAddress, port, label.printZPLFormat());
				    		connection.sendLog(settings.getSetting().getLogined_user(), label, ipAddress, String.valueOf(port));
				    		i++;
						    publish(i);
						    Thread.sleep(pageDelay);
				    	}				    	
				    }
				    return true;
				   }
			   }

			   // Can safely update the GUI from this method.
			   protected void done() {
                try {
			        PrintingIO_jobcount.setText("");
				    PrintingIO_status.setText("Done");
				    printqueue.remoreAllObject();
					Thread.sleep(1000);
					dispose();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }

			   @Override
			   // Can safely update the GUI from this method.
			   protected void process(List<Integer> chunks) {
			    // Here we receive the values that we publish().
			    // They may come grouped in chunks.
			    int mostRecentValue = chunks.get(chunks.size()-1);
			    
			    PrintingIO_status.setText("("+Integer.toString(mostRecentValue));
			   }
			   
			   
			  };
			  
		worker.execute();
				
	}
	
	
	private void cancel(SwingWorker<Boolean, Integer> worker, PrintingQueue printqueue) {
		if(!worker.isDone()) {
			worker.cancel(true);
			printqueue.remoreAllObject();
			dispose();
		}
	}
}
