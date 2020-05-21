package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InnerTunLabel extends Label {
	
	String productName;
	String pcCode;
	String quantity;
	String batch;
	String expiryDate;
	String barcode;

	public InnerTunLabel(String productName, String pcCode, String quantity, String batch, String expiryDate,
			String barcode) {
		super();
		this.productName = productName;
		this.pcCode = pcCode;
		this.quantity = quantity;
		this.batch = batch;
		this.expiryDate = expiryDate;
		this.barcode = barcode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPcCode() {
		return pcCode;
	}

	public void setPcCode(String pcCode) {
		this.pcCode = pcCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Override
	public String toString() {
		String printMachineName = null;
		String printMachineIP = null;
		
		try {
			InetAddress inetAddress = InetAddress. getLocalHost();
			
			printMachineName = inetAddress.getHostName();
			printMachineIP = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Unable to access machine name and ip.");
		}
		String log ="{{labeltype:\"InnerTunLabel\"},"
				   +"{productName:\""+productName+"\"},"
				   +"{pcCode:\""+pcCode+"\"},"
				   +"{quantity:\""+quantity+"\"},"
				   +"{batch:\""+batch+"\"},"
				   +"{expiryDate:\""+expiryDate+"\"},"
				   +"{barcode:\""+barcode+"\"},"
		           +"{printMachineName:\""+printMachineName+"\"},"
                   +"{printMachineIp:\""+printMachineIP+"\"}"
                   +"}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		String s = "^XA" + 
				"^FO30,10,0"+
    	 		"^A@N,80,50,E:TAH005.TTF"+
				"^FD"+ pcCode +"^FS" + 	
				"^FO510,10,0"+
    	 		"^A@N,80,50,E:TAH005.TTF"+
				"^FDQTY "+ quantity +"^FS" + 
				"^FO30,90,0"+
    	 		"^A@N,56,28,E:TAH005.TTF"+
				"^FD"+ productName +"^FS" + 
				"^FO610,80,0"+
    	 		"^A@N,28,18,E:TAH005.TTF"+
				"^FDBatch "+ batch +"^FS" + 
				"^FO610,110,0"+
    	 		"^A@N,28,18,E:TAH005.TTF"+
				"^FDEXPIRY "+ expiryDate +"^FS" + 
				"^FO100,152"+
    	 		"^GB634,18,9^FS"+
				"^FO60,170^BRN,11,5,2,52" + 
				"^FD"+barcode+"^FS" + 
				"^FO100,430"+
    	 		"^GB634,18,9^FS"+
				"^FO300,458,0"+			         
	 		  	"^A@N,28,36,E:ARI002.TTF"+
	 		  	"^CI27" +
				"^FD"+barcode+"^FS" + 	
				"^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
