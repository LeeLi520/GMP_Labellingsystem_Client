package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public HW_ReleasedLabel(String itemCode, String itemName, String ginNumber, String quantity, String unit, User logined_user, String released_date,
			String expiry_date) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.ginNumber = ginNumber;
		this.quantity = quantity;
		this.unit = unit;
		this.logined_user = logined_user;
		this.released_date = released_date;
		this.expiry_date = expiry_date;
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
		
		try {
			InetAddress inetAddress = InetAddress. getLocalHost();
			
			printMachineName = inetAddress.getHostName();
			printMachineIP = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Unable to access machine name and ip.");
		}
		String log = "{"
				+ "{labeltype:\"QcApprovalLabelRaw\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{ginNumber:\""+ginNumber+"\"},"
				+ "{quantity:\""+quantity + unit +"\"},"
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
		String s ="^XA" +
	             "^FO228,20"+ 
	             "^A0N,25,35"+ 
	             "^FDGMP Pharmaceuticals^FS"+
	             "^FO60,70"+ 
	             "^A0N,60,75"+ 
	             "^FDRELEASED BY QA(HW)^FS"+
	             "^FO28,135,0"+
	             "^A0N,30,35" +
	             "^FDMaterial:^FS" +
	             "^FO170,135,0" +
	             "^AEN,31,14" +
	             "^FD"+this.getItemName()+"^FS"+
	             
	             "^FO28,180"+ 
	             "^A0N,30,35"+
	             "^FDCode:^FS"+
	             "^FO170,180,0" +
	             "^AEN,31,14" +
	             "^FD"+this.getItemCode()+"^FS"+
	             "^FO28,220"+ 
	             "^A0N,30,35"+
	             "^FDGin:^FS"+
	             "^FO170,220,0" +
	             "^AEN,31,14" +
	             "^FD"+this.getGinNumber()+"^FS"+
	             
	             "^FO28,260"+ 
	             "^A0N,30,35"+
	             "^FDQuantity:^FS"+
	             "^FO170,260,0" +
	             "^AEN,31,14" +
	             "^FD"+this.getQuantity() + this.getUnit()+"^FS"+
	             
                 "^FO480,180,0"+
                 "^A0N,30,32"+
                 "^FDExp date: "+this.getExpiry_date()+"^FS"+
                 "^FO480,220,0"+
                 "^A0N,30,32"+
                 "^FDReleased: "+this.getReleased_date()+"^FS"+
                 
	             "^FO120,300,0"+
	             "^BQN,2,3" +
	             "^FDXXX"+this.getLogined_user().getFullname()+"^FS" +
	             "^FO400,260"+
	             "^A0N,30,35" +
	             "^FDChecked By:^FS" +
	             "^XZ";
		      
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
