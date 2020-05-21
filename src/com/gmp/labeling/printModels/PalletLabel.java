package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PalletLabel extends Label {
	
	private String brand;
	private String title;
	private String itemCode;
	private String productName;
	private String batch;
	private String printQuantity;
	private String palletNo;
	
	public PalletLabel(String title, String brand, String itemCode, String productName, String batch, String printQuantity, String palletNo) {
		super();
		this.title = title;
		this.brand = brand;
		this.itemCode = itemCode;
		this.productName = productName;
		this.batch = batch;
		this.printQuantity = printQuantity;
		this.palletNo = palletNo;
	}
	

	public String getPrintQuantity() {
		return printQuantity;
	}



	public void setPrintQuantity(String printQuantity) {
		this.printQuantity = printQuantity;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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

	public String getPalletNo() {
		return palletNo;
	}

	public void setPalletNo(String palletNo) {
		this.palletNo = palletNo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String printZPLFormat() {
		
		String s = "^XA";
		if(title.equals("Pharmaceutical")) {
			s +="^FO180,20,0"
	          + "^A0N,50,60"
	    	  + "^FDPHARMACEUTICAL^FS";
		}else {
			s +="^FO80,20,0"
    		  + "^A0N,50,60"
    		  + "^FDNON-PHARMACEUTICAL^FS";
		}
		
		double upperWeight = 1.0/43.0;
		double lowerWeight = 1.0/50.0;
		double numberWeight = 1.0/50.0;
		double spaceWeight = 1.0/99.0;
		
		int Com_upperCaseCount = 0;
		int Com_lowerCaseCount = 0;
		int Com_numberCount = 0;
		int Com_spaceCount = 0;
		
		for(int i = 0; i < brand.length(); i++) {
			if(Character.isUpperCase(brand.charAt(i))) {
				Com_upperCaseCount++;
			}
			if(Character.isLowerCase(brand.charAt(i))) {
				Com_lowerCaseCount++;
			}
			
			if(Character.isDigit(brand.charAt(i))) {
				Com_numberCount++;
			}
			if(Character.isSpace(brand.charAt(i))) {
				Com_spaceCount++;
			}
		}
		
		double Com_total = (Com_upperCaseCount*upperWeight)+(Com_lowerCaseCount*lowerWeight)+(Com_numberCount*numberWeight)+(Com_spaceCount*spaceWeight);
		int Com_dots = (int)((1.05 - Com_total)*548.0/2.0);
		    
		s += "^FO"+ Com_dots +",70,0"
		   + "^A0N,32,50"
		   + "^FD"+brand+"^FS";
		
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
	    + "^FO60,250,0"
        + "^A0N,28,35"
	    + "^FDContainer/Quantity:"+printQuantity+"^FS"
	    + "^FO60,300,0"
        + "^A0N,28,35"
	    + "^FDBatch:^FS"
	    + "^FO200,300,0"
        + "^A0N,28,35"
	    + "^FD"+batch+"^FS"
	    + "^FO580,320,0"
        + "^A0N,25,28"
	    + "^FDPallet NO."+palletNo+"^FS"
	 	+ "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
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

}
