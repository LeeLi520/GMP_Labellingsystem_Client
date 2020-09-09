package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class QA_ApprovedForFurtherProcessing extends Label {
	
	private String itemCode;
	private String itemName;
	private String batch;

	public QA_ApprovedForFurtherProcessing(String itemCode, String itemName, String batch) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.batch = batch;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
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
		String log = "{"
				+ "{labeltype:\"QA_ApprovedForFurtherProcessingLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{batch:\""+batch+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		String s = "^XA" +
	       		 "^FO238,20"+ 
		             "^A0N,25,35"+ 
		             "^FDGMP Pharmaceuticals^FS"+
		             "^FO158,55"+ 
		             "^A0N,28,38"+ 
		             "^FDApproved for further processing^FS"+
		             "^FO50,100"+
		             "^A@N,28,35,E:ARI002.TTF"+ 
		             "^FDProduct Name: ^FS";
		if(itemName.length()>35) {
		    String line_1;
		    String line_2;
		    int i = 35;
		    char temp = itemName.charAt(i);
		    while(temp!=' ') {
		     i--;
		     if(i<0) {
		     	break;
		     }else {
		     temp = itemName.charAt(i);
		     }
		    }
		    if(i == -1) {
		     line_1 = itemName.substring(0,35);
		     line_2 = itemName.substring(36,itemName.length());
		    }else {
		     line_1 = itemName.substring(0, i);
		     line_2 = itemName.substring(i+1, itemName.length());
		    }	 		
		    s += "^FO250,100"+
		    	 "^A@N,28,35,E:ARI002.TTF"+ 
		     	 "^FD"+line_1+"^FS"+
		     	 "^FO50,140,0"+
		     	 "^A@N,28,35,E:ARI002.TTF"+ 
		     	 "^FD"+line_2+"^FS";
    }else {
		    s += "^FO250,100"+
		     	 "^A@N,28,35,E:ARI002.TTF"+ 
		     	 "^FD"+ itemName +"^FS";
	}		             
		
		       s +=  "^FO50,195"+
		             "^A@N,28,35,E:ARI002.TTF"+ 
		             "^FDProduct Code: "+ itemCode +"^FS"+
		             "^FO480,195"+
		             "^A@N,28,35,E:ARI002.TTF"+ 
		             "^FDBatch No: "+ batch +"^FS"+
		             "^FO50,235"+
		             "^A@N,28,35,E:ARI002.TTF"+ 
		             "^FDSign/Date:^FS"+
	                 "^XZ";	
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
