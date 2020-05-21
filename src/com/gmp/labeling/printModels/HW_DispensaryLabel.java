package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class HW_DispensaryLabel extends Label {
	
	private String itemCode;
	private String itemName;
	private String ginNumber;
	private String grossWeight;
	private String netWeight;
	private String unit;
	private String batchUsedIn;
	private User logined_user;
	private String date_prepared;
	
	
	public HW_DispensaryLabel(String itemCode, String itemName, String ginNumber, String grossWeight, String netWeight,
			String unit, String batchUsedIn, User logined_user, String date_prepared) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.ginNumber = ginNumber;
		this.grossWeight = grossWeight;
		this.netWeight = netWeight;
		this.unit = unit;
		this.batchUsedIn = batchUsedIn;
		this.logined_user = logined_user;
		this.date_prepared = date_prepared;
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



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
	public String getBatchUsedIn() {
		return batchUsedIn;
	}


	public void setBatchUsedIn(String batchUsedIn) {
		this.batchUsedIn = batchUsedIn;
	}


	public User getLogined_user() {
		return logined_user;
	}



	public void setLogined_user(User logined_user) {
		this.logined_user = logined_user;
	}



	public String getDate_prepared() {
		return date_prepared;
	}



	public void setDate_prepared(String date_prepared) {
		this.date_prepared = date_prepared;
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
				+ "{grossWeight:\""+ grossWeight + unit +"\"},"
				+ "{netWeight:\""+ netWeight + unit +"\"},"
				+ "{datePrepared:\""+date_prepared+"\"},"
				+ "{batchUsedIn:\""+batchUsedIn+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		if(date_prepared.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			date_prepared = dateData;
		}
		String s ="^XA" +
	             "^FO188,30"+ 
	             "^A0N,30,45"+ 
	             "^FDGMP Pharmaceuticals(HW)^FS";

		       s +="^FO300,60"+ 
		  	       "^A0N,25,36"+ 
			       "^FDDispensary^FS";
		       
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
	             "^FDGross weight: "+grossWeight + unit +"^FS"+
	             "^FO480,190,0"+
	             "^A0N,30,32"+
	             "^FDNet weight: "+netWeight + unit + "^FS"+
	             "^FO480,230,0"+
	             "^A0N,30,32"+
	             "^FDBatch Used In: "+ batchUsedIn + "^FS"+
	             "^FO28,255,0"+
	             "^A0N,30,35" +
	             "^FDGIN:^FS" +
	             "^FO150,255^BY2"+
	             "^BCN,50,N,N,N"+
	             "^FD"+ginNumber+"^FS"+
	             "^FO205,310,0"+
	             "^AAN,31,13" +
	             "^FD"+ginNumber+"^FS" +
	             "^FO480,270,0"+
	             "^A0N,30,35" +
	             "^FDChecked By:^FS" +
//	             "^FO680,365,0"+
//	             "^A0N,20,20" +
//	             "^FD"+"No.C:(1/10)"+"^FS" +
	             "^FO110,365,0"+
	             "^A0N,20,20" +
	             "^FDDate Prepared: "+date_prepared+"^FS" +
	             "^FO28,300,0"+
	             "^BQN,2,3" +
	             "^FDXXX"+logined_user.getFullname()+"^FS" +
	             "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
