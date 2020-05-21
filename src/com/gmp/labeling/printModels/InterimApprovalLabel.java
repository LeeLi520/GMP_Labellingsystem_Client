package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class InterimApprovalLabel extends Label {
	
	private String itemCode;
	private String itemName;
	private String ginNumber;
	private User logined_user;
	private String expiry_date;
	private String date;
	

	public InterimApprovalLabel(String itemCode, String itemName, String ginNumber, User logined_user,
			String expiry_date, String date) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.ginNumber = ginNumber;
		this.logined_user = logined_user;
		this.expiry_date = expiry_date;
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

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
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
				+ "{labeltype:\"InterimApprovalLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{ginNumber:\""+ginNumber+"\"},"
				+ "{expiryDate:\""+expiry_date+"\"},"
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
	             "^FO238,10"+ 
	             "^A0N,25,35"+ 
	             "^FDGMP Pharmaceuticals^FS"+
	             "^FO248,45"+ 
	             "^A0N,25,35"+ 
	             "^FDINTERIM APPROVAL^FS"+
	             "^FO20,75"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDThis raw material has been conditionary released^FS"+
	             "^FO50,120"+ 
	             "^A0N,38,50"+
	             "^FDAPPROVAL FROM QA IS REQUIRED^FS"+
	             "^FO190,165"+ 
	             "^A0N,38,50"+
	             "^FDBEFORE EACH USE^FS"+
	             "^FO40,220"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDCode:"+this.getItemCode()+"^FS"+
	             "^FO500,220"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDGin:"+this.getGinNumber()+"^FS"+
	             "^FO250,265"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDExpiry: "+this.expiryDateTran(this.expiry_date)+"^FS"+
	             "^FO40,310"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDSign:"+this.getLogined_user().getFullname()+"^FS"+
	             "^FO500,310"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDDate:"+this.date+"^FS"+
	             "^XZ";
		      
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String expiryDateTran(String expiryDate) {
		String result = "";
		char[] temp = expiryDate.toCharArray();
		String day = String.valueOf(temp[0])+String.valueOf(temp[1]);
		String month = String.valueOf(temp[3]) + String.valueOf(temp[4]);
		String year = String.valueOf(temp[6])+ String.valueOf(temp[7]) + String.valueOf(temp[8]) + String.valueOf(temp[9]);
		
		result = day+" ";
		
		switch(month) {
		case "01":
			result += "JAN ";
			break;
			
		case "02":
			result += "FEB ";
			break;
			
		case "03":
			result += "MAR ";
			break;
			
		case "04":
			result += "APR ";
			break;
			
		case "05":
			result += "MAY ";
			break;
			
		case "06":
			result += "JUN ";
			break;
			
		case "07":
			result += "JUL ";
			break;
			
		case "08":
			result += "AUG ";
			break;
			
		case "09":
			result += "SEP ";
			break;
			
		case "10":
			result += "OCT ";
			break;
			
		case "11":
			result += "NOV ";
			break;
			
		case "12":
			result += "DEC ";
			break;
		}
		
		result += year;

		
		return result;

	}

}
