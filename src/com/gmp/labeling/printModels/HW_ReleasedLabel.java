package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.gmp.labeling.models.User;

public class HW_ReleasedLabel extends Label {
	
	private String itemCode;
	private String itemName;
	private String ginNumber;
	private String quantity;
	private String unit;
	private User logined_user;
	private String released_date;
	private String expiry_date;
	private String storageType;
	
	public HW_ReleasedLabel(String itemCode, String itemName, String ginNumber, String storageType,String quantity, String unit, User logined_user, String released_date,
			String expiry_date) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.ginNumber = ginNumber;
		this.storageType = storageType;
		this.quantity = quantity;
		this.unit = unit;
		this.logined_user = logined_user;
		this.released_date = released_date;
		this.expiry_date = expiry_date;
	}
	
	public String getStorageType() {
		return storageType;
	}




	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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

	public String getGinNumber() {
		return ginNumber;
	}

	public void setGinNumber(String ginNumber) {
		this.ginNumber = ginNumber;
	}

	public User getLogined_user() {
		return logined_user;
	}

	public void setLogined_user(User logined_user) {
		this.logined_user = logined_user;
	}

	public String getReleased_date() {
		return released_date;
	}

	public void setReleased_date(String released_date) {
		this.released_date = released_date;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	@Override
	public String toString() {
		String printMachineName = null;
		String printMachineIP = null;
		Scanner san = new Scanner(this.itemName);
		
		String result = null;
		while(san.hasNext()) {
			if(result==null) {
				result = san.next();
			}else {
				result += " " + san.next();
			}
		}
		
		try {
			InetAddress inetAddress = InetAddress. getLocalHost();
			
			printMachineName = inetAddress.getHostName();
			printMachineIP = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Unable to access machine name and ip.");
		}
		String log = "{"
				+ "{labeltype:\"HW_ReleasedByQALabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+ result +"\"},"
				+ "{ginNumber:\""+ginNumber+"\"},"
				+ "{quantity:\""+quantity + unit +"\"},"
				+ "{storageType:\""+storageType+"\"},"
				+ "{expiryDate:\""+expiry_date+"\"},"
				+ "{releasedDate:\""+released_date+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		if(released_date.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			released_date = dateData;
		}
		if(expiry_date.equals("")) {
			expiry_date = "N/A";
		}
		
		@SuppressWarnings("resource")
		Scanner san = new Scanner(this.itemName);
		
		String result = null;
		while(san.hasNext()) {
			if(result==null) {
				result = san.next();
			}else {
				result += " " + san.next();
			}
		}
		
		String s ="^XA" +
	             "^FO228,20"+ 
	             "^A0N,25,35"+ 
	             "^FDGMP Pharmaceuticals^FS"+
	             "^FO60,50"+ 
	             "^A0N,60,75"+ 
	             "^FDRELEASED BY QA(HW)^FS"+
	             "^FO28,115,0"+
	             "^A0N,30,35" +
	             "^FDMaterial:^FS" ;
	             
	             if(result.length()>42) {
	            	 String line_1;
	            	 String line_2;
	            	 int i = 45;
	            	 char temp = result.charAt(i);
	            	    while(temp!=' ') {
	            	     i--;
	            	     if(i<0) {
	            	     	break;
	            	     }else {
	            	     temp = result.charAt(i);
	            	     }
	            	    }
	            	    if(i == -1) {
	            	     line_1 = result.substring(0,45);
	            	     line_2 = result.substring(46,result.length());
	            	    }else {
	            	     line_1 = result.substring(0, i);
	            	     line_2 = result.substring(i+1, result.length());
	            	    }
	     	     s +=   "^FO170,105,0" +
	    				"^A0N,30,26" +
	    				"^FD"+ line_1 +"^FS"+
	    				"^FO170,140,0" +
	    				"^A0N,30,26" +
	    				"^FD"+ line_2 +"^FS";       
	             }else {
	 			 s+=  "^FO170,115,0"+
	 				  "^A0N, 30,26"+
	 				  "^FD"+ result +"^FS"; 
	             }

	             
	     s+=     "^FO28,180"+ 
	             "^A0N,30,35"+
	             "^FDCode:^FS"+
	             "^FO170,180,0" +
	             "^AEN,31,14" +
	             "^FD" + this.itemCode + "^FS"+
	             "^FO28,220"+ 
	             "^A0N,30,35"+
	             "^FDGin:^FS"+
	             "^FO170,220,0" +
	             "^AEN,31,14" +
	             "^FD"+ this.ginNumber +"^FS"+
	             
	             "^FO28,260"+ 
	             "^A0N,30,35"+
	             "^FDQuantity:^FS"+
	             "^FO170,260,0" +
	             "^AEN,31,14" +
	             "^FD"+this.quantity +" "+ this.unit +"^FS"+
	             
                "^FO480,180,0"+
                "^A0N,30,32"+
                "^FDExp date: "+ this.expiry_date +"^FS"+
                "^FO480,220,0"+
                "^A0N,30,32"+
                "^FDReleased: "+ this.released_date +"^FS"+
                
	             "^FO120,300,0"+
	             "^BQN,2,3" +
	             "^FDXXX"+ this.logined_user.getFullname()+"^FS" +
	             
	             "^FO400,260"+
	             "^A0N,30,35" +
	             "^FDStorage Type: "+ this.storageType +"^FS" +
	             
	             "^FO340,320"+
	             "^A0N,30,35" +
	             "^FDChecked By:^FS" +
	             "^XZ";
//		String s ="^XA" +
//	             "^FO228,20"+ 
//	             "^A0N,25,35"+ 
//	             "^FDGMP Pharmaceuticals^FS"+
//	             "^FO60,70"+ 
//	             "^A0N,60,75"+ 
//	             "^FDRELEASED BY QA(HW)^FS"+
//	             "^FO28,135,0"+
//	             "^A0N,30,35" +
//	             "^FDMaterial:^FS" +
//	             "^FO170,135,0" +
//	             "^AEN,31,14" +
//	             "^FD"+this.getItemName()+"^FS"+
//	             
//	             "^FO28,180"+ 
//	             "^A0N,30,35"+
//	             "^FDCode:^FS"+
//	             "^FO170,180,0" +
//	             "^AEN,31,14" +
//	             "^FD"+this.getItemCode()+"^FS"+
//	             "^FO28,220"+ 
//	             "^A0N,30,35"+
//	             "^FDGin:^FS"+
//	             "^FO170,220,0" +
//	             "^AEN,31,14" +
//	             "^FD"+this.getGinNumber()+"^FS"+
//	             
//	             "^FO28,260"+ 
//	             "^A0N,30,35"+
//	             "^FDQuantity:^FS"+
//	             "^FO170,260,0" +
//	             "^AEN,31,14" +
//	             "^FD"+this.getQuantity() + this.getUnit()+"^FS"+
//	             
//                 "^FO480,180,0"+
//                 "^A0N,30,32"+
//                 "^FDExp date: "+this.getExpiry_date()+"^FS"+
//                 "^FO480,220,0"+
//                 "^A0N,30,32"+
//                 "^FDReleased: "+this.getReleased_date()+"^FS"+
//                 
//	             "^FO120,300,0"+
//	             "^BQN,2,3" +
//	             "^FDXXX"+this.getLogined_user().getFullname()+"^FS" +
//	             "^FO400,260"+
//	             "^A0N,30,35" +
//	             "^FDChecked By:^FS" +
//	             "^XZ";
//		      
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
