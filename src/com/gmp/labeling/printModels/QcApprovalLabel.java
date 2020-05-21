package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class QcApprovalLabel extends Label {
	
	private String itemCode;
	private String itemName;
	private String ginNumber;
	private User logined_user;
	private String date;
	
	
	public QcApprovalLabel(String itemCode, String itemName, String ginNumber, User logined_user, String date) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.ginNumber = ginNumber;
		this.logined_user = logined_user;
		this.date = date;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
				+ "{labeltype:\"QcApprovalLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{ginNumber:\""+ginNumber+"\"},"
				+ "{date:\""+date+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		if(date.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			date = dateData;
		}
		String s ="^XA" +
	             "^FO228,20"+ 
	             "^A0N,25,35"+ 
	             "^FDGMP Pharmaceuticals^FS"+
	             "^FO80,70"+ 
	             "^A0N,60,75"+ 
	             "^FDAPPROVED FOR USE^FS"+
	             "^FO80,140"+ 
	             "^AEN,28,15"+ 
	             "^FDCode:"+this.getItemCode()+"^FS"+
	             "^FO500,140"+ 
	             "^AEN,28,15"+ 
	             "^FDGin:"+this.getGinNumber()+"^FS"+
	             "^FO80,230"+ 
	             "^AEN,28,15"+ 
	             "^FDSign:"+this.getLogined_user().getFullname()+"^FS"+
	             "^FO500,230"+ 
	             "^AEN,28,15"+ 
	             "^FDDate:"+this.date+"^FS"+
	             "^XZ";
		      
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
