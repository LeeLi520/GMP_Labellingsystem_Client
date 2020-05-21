package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductLabel extends Label{

	private String itemCode;
	private String itemName;
	private String ginNumber;
	private String type;
	private String quantity;
	private String receivedDate;
	private String itemUnit;
	
	
	
	
	public ProductLabel(String itemCode, String itemName, String ginNumber, String type, String quantity,
			String receivedDate, String itemUnit) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.ginNumber = ginNumber;
		this.type = type;
		this.quantity = quantity;
		this.receivedDate = receivedDate;
		this.itemUnit = itemUnit;
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



	@Override
	public String printZPLFormat() {
		Date dateTime = new Date();
		DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String timeData = dateTimeFormat.format(dateTime);
		if(receivedDate.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			receivedDate = dateData;
		}
		String s ="^XA" +
	             "^FO188,30"+ 
	             "^A0N,30,45"+ 
	             "^FDGMP Pharmaceuticals^FS";
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
	             "^FO28,100,0"+
	             "^A0N,30,35" +
	             "^FDMaterial:^FS" +
	             "^FO170,100,0" +
	             "^AEN,31,14" +
	             "^FD"+itemName+"^FS"+
	             "^FO28,160,0"+
	             "^A0N,30,35"+
	             "^FDCode:^FS" +
	             "^FO150,160^BY2"+
	             "^BCN,60,N,N,N"+
	             "^FD"+itemCode+"^FS"+
	             "^FO210,225,0"+
	             "^AAN,31,13" +
	             "^FD"+itemCode+"^FS" +
	             "^FO480,160,0"+
	             "^A0N,30,32"+
	             "^FDReceived: "+receivedDate+"^FS"+
	             "^FO28,275,0"+
	             "^A0N,30,35" +
	             "^FDQuantity:"+ quantity + itemUnit +"^FS" +
	             "^FO480,275,0"+
	             "^A0N,30,35" +
	             "^FDGIN:^FS";
	             String aCode = String.valueOf(ginNumber.charAt(0))+String.valueOf(ginNumber.charAt(1));
	             String orginalGin = String.valueOf(ginNumber.charAt(2))+String.valueOf(ginNumber.charAt(3))+String.valueOf(ginNumber.charAt(4))+String.valueOf(ginNumber.charAt(5))+String.valueOf(ginNumber.charAt(6));					aCode = aCode.replaceAll("([a-z])", "$1").toUpperCase();
					if(aCode.equals("OR")) {
						s+= "^FO560,275^BY2"+
					             "^BCN,50,N,N,N"+
					             "^FD"+orginalGin+"^FS"+
					             "^FO620,330,0"+
					             "^AAN,31,13" +
					             "^FD"+orginalGin+"^FS";
					}else {
						 s+= "^FO560,275^BY2"+
					             "^BCN,50,N,N,N"+
					             "^FD"+ginNumber+"^FS"+
					             "^FO600,330,0"+
					             "^AAN,31,13" +
					             "^FD"+ginNumber+"^FS";
					}
	         s+= "^FO15,365,0"+
	             "^A0N,20,20" +
	             "^FDPrint time: "+timeData+"^FS" +
	             "^FO300,355,0"+
	             "^A0N,35,40" +
	             "^FDQUARANTINE^FS" +
	             "^XZ";
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
		String log = "{"
				+ "{labeltype:\"ProductLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{ginNumber:\""+ginNumber+"\"},"
				+ "{type:\""+type+"\"},"
				+ "{quantity:\""+quantity+"\"},"
				+ "{receivedDate:\""+receivedDate+"\"},"
				+ "{itemUnit:\""+itemUnit+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

}
