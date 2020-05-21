package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class OuterTunLabel extends Label {
	
	String productName;
	String pcCode;
	String quantity;
	String batch;
	String expiryDate;
	String barcode;
    String grossWeight;
  
	public OuterTunLabel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OuterTunLabel(String productName, String pcCode, String quantity, String batch, String expiryDate,
			String barcode, String grossWeight) {
		super();
		this.productName = productName;
		this.pcCode = pcCode;
		this.quantity = quantity;
		this.batch = batch;
		this.expiryDate = expiryDate;
		this.barcode = barcode;
		this.grossWeight = grossWeight;
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

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
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
				   +"{grossweight:\""+grossWeight+"\"},"
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
				"^FO30,485,0"+			         
	 		  	"^A@N,28,28,E:ARI002.TTF"+
	 		  	"^CI27" +
				"^FDGross Weight "+ grossWeight +"^FS" + 
				"^FO460,485,0"+			         
	 		  	"^A@N,28,28,E:ARI002.TTF"+
	 		  	"^CI27" +
				"^FDPharmacare Laboratories Pty Ltd^FS" + 
				"^FO30,525,0"+			         
	 		  	"^A@N,24,24,E:ARI002.TTF"+
	 		  	"^CI27" +
				"^FDStore in a cool dry place^FS" + 	
				"^FO30,550,0"+			         
	 		  	"^A@N,24,24,E:ARI002.TTF"+
	 		  	"^CI27" +
				"^FDaway from direct sunlight^FS" + 	
				"^FO620,525,0"+			         
	 		  	"^A@N,22,22,E:ARI002.TTF"+
	 		  	"^CI27" +
				"^FD18 Jubilee Avenue^FS" + 
				"^FO575,548,0"+			         
	 		  	"^A@N,22,22,E:ARI002.TTF"+
	 		  	"^CI27" +
				"^FDWarriewood NSW 2102^FS" + 
				"^FO688,571,0"+			         
	 		  	"^A@N,22,22,E:ARI002.TTF"+
	 		  	"^CI27" +
				"^FDAustralia^FS" + 
				"^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
