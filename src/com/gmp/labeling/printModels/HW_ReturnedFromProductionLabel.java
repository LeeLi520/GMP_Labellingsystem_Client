package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class HW_ReturnedFromProductionLabel extends Label {
	
	private String itemCode;
	private String itemName;
	private String ginNumber;
	private String grossWeight;
	private String netWeight;
	private String unit;
	private String preparedDate;
	private User user;
	
	public HW_ReturnedFromProductionLabel(String itemCode, String itemName, String ginNumber, String grossWeight,
			String netWeight, String unit,String preparedDate, User user) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.ginNumber = ginNumber;
		this.grossWeight = grossWeight;
		this.netWeight = netWeight;
		this.unit = unit;
		this.preparedDate = preparedDate;
		this.user = user;
	}
	

	public HW_ReturnedFromProductionLabel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getPreparedDate() {
		return preparedDate;
	}

	public void setPreparedDate(String preparedDate) {
		this.preparedDate = preparedDate;
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
				+ "{labeltype:\"ReturnedFromProductionLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{ginNumber:\""+ginNumber+"\"},"
				+ "{grossWeight:\""+ grossWeight + unit +"\"},"
				+ "{netWeight:\""+ netWeight + unit +"\"},"
				+ "{datePrepared:\""+preparedDate+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		if(preparedDate.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			preparedDate = dateData;
		}
		String s = null;
		s = "^XA" +
	        "^FO180,80"+ 
	        "^A@N,38,38,E:ARI004.TTF"+
	        "^FDGMP Pharmaceuticals(HW)^FS" +	             
	        "^FO182,120,0"+ 
	        "^A@N,30,30,E:ARI004.TTF"+
	        "^FDRETURNED FROM PRODUCTION^FS" +
	             
	        "^FO68,160,0"+ 
	        "^A@N,36,25,E:TAH005.TTF"+
	        "^FDItem Name: "+ itemName +"^FS" +
	             
            "^FO68,240,0"+ 
   			"^A@N,36,25,E:TAH005.TTF"+
   			"^FDItem Code:^FS" +
   			 
            "^FO220,210^BY2"+
            "^BCN,60,N,N,N"+
            "^FD"+ itemCode +"^FS"+
            "^FO280,275,0"+
            "^AAN,31,13" +
            "^FD"+ itemCode +"^FS" +
   			 
			"^FO68,320,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDGIN No:^FS" +
			
            "^FO220,320^BY2"+
            "^BCN,60,N,N,N"+
            "^FD"+ ginNumber +"^FS"+
            "^FO280,385,0"+
            "^AAN,31,13" +
            "^FD"+ ginNumber +"^FS" +
				 
			"^FO480,320,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDGross Weight: "+ grossWeight + " "+ unit +"^FS" +
				 
			"^FO480,360,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDNet Weight: "+ netWeight + " "+ unit +"^FS" +
				 
			"^FO98,420,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDDate Prepared: "+ preparedDate +"^FS" +
	             
				 
			"^FO128,460,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDApproved By:^FS" +
				 
	        "^FO305,450,0"+
	        "^BQN,2,3" +
	        "^FDXXX"+ user.getFullname()+"^FS" +
				 
			"^FO390,460,0"+ 
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
