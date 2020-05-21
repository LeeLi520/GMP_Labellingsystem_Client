package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class FPQuarantineLabel extends Label {
	
	private String title;
	private String itemCode;
	private String productName;
	private String batch;
	private String printQuantity;
	private String palletNo;

	public FPQuarantineLabel(String title, String itemCode, String productName, String batch,
			String printQuantity, String palletNo) {
		super();
		this.title = title;
		this.itemCode = itemCode;
		this.productName = productName;
		this.batch = batch;
		this.printQuantity = printQuantity;
		this.palletNo = palletNo;
	}
	

	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getItemCode() {
		return itemCode;
	}



	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getBatch() {
		return batch;
	}



	public void setBatch(String batch) {
		this.batch = batch;
	}



	public String getPrintQuantity() {
		return printQuantity;
	}



	public void setPrintQuantity(String printQuantity) {
		this.printQuantity = printQuantity;
	}



	public String getPalletNo() {
		return palletNo;
	}



	public void setPalletNo(String palletNo) {
		this.palletNo = palletNo;
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
		String log ="{"
				+ "{labeltype:\"PalletLabel\"},"
				+ "{title:\""+title+"\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{productName:\""+productName+"\"},"
				+ "{batch:\""+batch+"\"},"
				+ "{printQuantity:\""+printQuantity+"\"},"
				+ "{palletNo:\""+palletNo+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		String s = "^XA"+
				"^FO208,30"+ 
	            "^A0N,30,45"+ 
	            "^FDGMP Pharmaceuticals^FS";
		
		if(title.equals("Pharmaceutical")) {
			s +="^FO285,60"+ 
                "^A0N,25,36"+ 
                "^FD"+title+"^FS";
		}else {
			s +="^FO255,60"+ 
  	            "^A0N,25,36"+ 
	            "^FD"+title+"^FS";
		}
		
		if(productName.length()>38) {
			String line_1;
	 		String line_2;
	 		int i = 38;
	 		char temp = productName.charAt(i);
	 		while(temp!=' ') {
	 			i--;
	 			if(i<0) {
	 				break;
	 			}else {
	 			temp = productName.charAt(i);
	 			}
	 		}
	 		if(i == -1) {
	 			line_1 = productName.substring(0,38);
	 			line_2 = productName.substring(39,productName.length());
	 		}else {
	 			line_1 = productName.substring(0, i);
		 		line_2 = productName.substring(i+1, productName.length());
	 		}	 		
	 		s += "^FO60,120,0"
	 		   + "^A0N,28,35"
	 		   + "^FDProduct:^FS"
	 		   + "^FO200,120,0"
	 		   + "^A0N,28,35"
	 		   + "^FD"+line_1+"^FS"
	 		   + "^FO200,155,0"
	 		   + "^A0N,28,35"
	 		   + "^FD"+line_2+"^FS";
		}else {
			s += "^FO60,120,0"
			   + "^A0N,28,35"
			   + "^FDProduct:^FS"
			   + "^FO200,120,0"
	 		   + "^A0N,28,35"
	 		   + "^FD"+productName+"^FS";
		}
	    
	    s += "^FO60,200,0"
        + "^A0N,28,35"
	    + "^FDCode:^FS"
	    + "^FO200,200,0"
        + "^A0N,28,35"
	    + "^FD"+itemCode+"^FS"
	    + "^FO60,235,0"
        + "^A0N,28,35"
	    + "^FDContainer/Quantity:"+printQuantity+"^FS"
	    + "^FO60,275,0"
        + "^A0N,28,35"
	    + "^FDBatch:^FS"
	    + "^FO200,275,0"
        + "^A0N,28,35"
	    + "^FD"+batch+"^FS"
	    + "^FO580,275,0"
        + "^A0N,25,28"
	    + "^FDPallet NO."+palletNo+"^FS"	    
	    + "^FO265,330,0"
        + "^A0N,60,60"
	    + "^FDQUARANTINE^FS"
	 	+ "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
