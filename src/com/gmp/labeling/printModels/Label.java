package com.gmp.labeling.printModels;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;

public abstract class Label {
	
	public synchronized void printLocal(String s){
		try {	           
	           PrintService psZebra = null;
	           String sPrinterName = null;
	           PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
	           
	           for (int i = 0; i < services.length; i++) {
	               
	               PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
	               sPrinterName = ((PrinterName) attr).getValue();
	               
	               if (sPrinterName.toLowerCase().indexOf("zebra")>=0) {
	                   psZebra = services[i];
	                   break;
	               }
	           }
	           
	           DocPrintJob job = psZebra.createPrintJob();

	           byte[] by = s.getBytes();
	           DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	           Doc doc = new SimpleDoc(by, flavor, null);	           
	        	   job.print(doc, null);
	           	           
	       } catch (PrintException e) {
	           e.printStackTrace();
	       } 
	}
	
	public synchronized void printNetwork(String ipAddress, int port, String s) {
	    Socket clientSocket;
		try {
			clientSocket = new Socket(ipAddress ,port); //The data being sent in the lines below illustrate CPCL  one can change the data for the corresponding 
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream() );//language being used (ZPL, EPL)   
		    outToServer.writeBytes(s);
		    clientSocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public abstract String toString();
	
	public abstract String printZPLFormat();
	
	public abstract String printEPLFormat();
	
}
