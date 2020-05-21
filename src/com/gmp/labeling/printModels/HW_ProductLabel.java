package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class HW_ProductLabel extends Label {
	
	private String itemCode;
	private String itemName;
	private String ginNumber;
	private String type;
	private String quantity;
	private String receivedDate;
	private String expiredDate;
	private String itemUnit;
	private User user;
	

	public HW_ProductLabel(String itemCode, String itemName, String ginNumber, String type, String quantity,
			String receivedDate, String expiredDate, String itemUnit, User user) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.ginNumber = ginNumber;
		this.type = type;
		this.quantity = quantity;
		this.receivedDate = receivedDate;
		this.expiredDate = expiredDate;
		this.itemUnit = itemUnit;
		this.user = user;
	}
		
	
	public String getExpiredDate() {
		return expiredDate;
	}



	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
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



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getQuantity() {
		return quantity;
	}



	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}



	public String getReceivedDate() {
		return receivedDate;
	}



	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}


	public String getItemUnit() {
		return itemUnit;
	}



	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
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
				+ "{labeltype:\"HW_ProductLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{ginNumber:\""+ginNumber+"\"},"
				+ "{type:\""+type+"\"},"
				+ "{quantity:\""+quantity+"\"},"
				+ "{receivedDate:\""+receivedDate+"\"},"
				+ "{expiredDate:\""+expiredDate+"\"},"
				+ "{itemUnit:\""+itemUnit+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
//		Date dateTime = new Date();
//		DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		String timeData = dateTimeFormat.format(dateTime);
		if(receivedDate.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			receivedDate = dateData;
		}
		if(expiredDate.equals("")) {
			expiredDate = "N/A";
		}
		String s ="^XA" +
	             "^FO188,30"+ 
	             "^A0N,30,45"+ 
	             "^FDGMP Pharmaceuticals(HW)^FS";
		       switch(type) {
		       case "Pharmaceutical Use":
		    	   s += "^FO240,60"+ 
		  	                       "^A0N,25,36"+ 
			                       "^FD"+type+"^FS";
		    	   break;
		       case "Non-Pharmaceutical Use":
		    	   s += "^FO200,60"+ 
		  	                       "^A0N,25,36"+ 
			                       "^FD"+type+"^FS";
		    	   break;
		       case "Packaging":
		    	   break;
		       }
		       s +=
	             "^FO28,95,0"+
	             "^A0N,30,35" +
	             "^FDMaterial:^FS" +
	             "^FO170,95,0" +
	             "^AEN,31,14" +
	             "^FD"+itemName+"^FS"+
	             "^FO28,150,0"+
	             "^A0N,30,35"+
	             "^FDCode:^FS" +
	             "^FO150,150^BY2"+
	             "^BCN,60,N,N,N"+
	             "^FD"+itemCode+"^FS"+
	             "^FO210,215,0"+
	             "^AAN,31,13" +
	             "^FD"+itemCode+"^FS" +
	             "^FO480,150,0"+
	             "^A0N,30,32"+
	             "^FDReceived: "+receivedDate+"^FS"+
	             "^FO480,190,0"+
	             "^A0N,30,32"+
	             "^FDExp date: "+expiredDate+"^FS"+
	             "^FO28,255,0"+
	             "^A0N,30,35" +
	             "^FDQuantity:"+ quantity + itemUnit +"^FS" +
	             "^FO300,255,0"+
	             "^A0N,30,35" +
	             "^FDGIN:^FS" +
	             "^FO380,255^BY1"+
	             "^BCN,50,N,N,N"+
	             "^FD"+ginNumber+"^FS"+
	             "^FO375,310,0"+
	             "^AAN,31,13" +
	             "^FD"+ginNumber+"^FS" +
	             "^FO520,255,0"+
	             "^A0N,30,35" +
	             "^FDChecked By:^FS" +
//	             "^FO680,365,0"+
//	             "^A0N,20,20" +
//	             "^FD"+"No.C:(1/10)"+"^FS" +
//	             "^FO110,365,0"+
//	             "^A0N,20,20" +
//	             "^FD"+timeData+"^FS" +
	             "^FO300,355,0"+
	             "^A0N,35,40" +
	             "^FDQUARANTINE^FS" +
	             "^FO28,300,0"+
	             "^BQN,2,3" +
	             "^FDXXX"+user.getFullname()+"^FS" +
	             "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
