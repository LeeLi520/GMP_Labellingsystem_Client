package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class HW_BulkLabel extends Label {
	
	private String itemCode;
	private String itemName;
	private String batch;
	private String quantity;
	private String unit;
	private String manufactureDate;
	private String expiryDate;
	private String noContainer_1;
	private String noContainer_2;
	private User user;	

	public HW_BulkLabel(String itemCode, String itemName, String batch, String quantity, String unit,
			String manufactureDate, String expiryDate, String noContainer_1, String noContainer_2, User user) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.batch = batch;
		this.quantity = quantity;
		this.unit = unit;
		this.manufactureDate = manufactureDate;
		this.expiryDate = expiryDate;
		this.noContainer_1 = noContainer_1;
		this.noContainer_2 = noContainer_2;
		this.user = user;
	}

	
	public HW_BulkLabel() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getNoContainer_1() {
		return noContainer_1;
	}

	public void setNoContainer_1(String noContainer_1) {
		this.noContainer_1 = noContainer_1;
	}

	public String getNoContainer_2() {
		return noContainer_2;
	}

	public void setNoContainer_2(String noContainer_2) {
		this.noContainer_2 = noContainer_2;
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
				+ "{labeltype:\"BulkLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{batch:\""+ batch +"\"},"
				+ "{quantity:\""+ quantity +" "+ unit +"\"},"
				+ "{ManufactureDate:\""+ manufactureDate +"\"},"
				+ "{expiryDate:\""+ expiryDate +"\"},"
				+ "{No. of Containers:\""+ noContainer_1 +" of "+ noContainer_2 +"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		if(manufactureDate.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			manufactureDate = dateData;
		}
		String s = null;
		s = "^XA" +
	        "^FO180,30"+ 
	        "^A@N,38,38,E:ARI004.TTF"+
	        "^FDGMP Pharmaceuticals(HW)^FS" +	  
	        
            "^FO320,70"+ 
            "^A0N,60,75"+ 
            "^FDBULK^FS"+

	             
	        "^FO28,140,0"+ 
	        "^A@N,36,25,E:TAH005.TTF"+
	        "^FDItem Name: "+ itemName +"^FS" +
	             
            "^FO28,220,0"+ 
   			"^A@N,36,25,E:TAH005.TTF"+
   			"^FDItem Code:^FS" +
   			 
            "^FO220,200^BY2"+
            "^BCN,60,N,N,N"+
            "^FD"+ itemCode +"^FS"+
            "^FO280,265,0"+
            "^AAN,31,13" +
            "^FD"+ itemCode+"^FS" +
   			 
			"^FO450,300,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDBatch No: "+ batch +"^FS" +
			
			"^FO450,360,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDQuantity: "+ quantity +" "+ unit +"^FS" +
				 
			"^FO28,300,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDManufacture Date: "+ manufactureDate +"^FS" +
				 
			"^FO28,360,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDExpiry Date: "+ expiryDate +"^FS" +
				 

			
            "^FO28,420,0"+ 
            "^A@N,36,25,E:TAH005.TTF"+
            "^FDNo. of Containers: "+ noContainer_1 +" of "+ noContainer_2 +"^FS" +
				 
			"^FO28,480,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDApproved By:^FS" +
				 
	        "^FO220,470,0"+
	        "^BQN,2,3" +
	        "^FDXXX"+ user.getFullname() +"^FS" +
				 
			"^FO450,480,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
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
