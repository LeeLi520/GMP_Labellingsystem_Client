package com.gmp.labeling.printModels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java. net. InetAddress;
import java.net.UnknownHostException;

import com.gmp.labeling.models.User;

public class AssetLabel extends Label{
	
	private String company;
    private String assetNumber;
    private String deviceName;
    private String department;
    private String supplier;
    private String date;
    private String poNumber;
	private User logined_user;
	
	

	public AssetLabel(String company, String assetNumber, String deviceName, String department, String supplier,
			String date, String poNumber, User logined_user) {
		super();
		this.company = company;
		this.assetNumber = assetNumber;
		this.deviceName = deviceName;
		this.department = department;
		this.supplier = supplier;
		this.date = date;
		this.poNumber = poNumber;
		this.logined_user = logined_user;
	}
	
	

	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}



	public String getAssetNumber() {
		return assetNumber;
	}



	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}



	public String getDeviceName() {
		return deviceName;
	}



	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}



	public String getDepartment() {
		return department;
	}



	public void setDepartment(String department) {
		this.department = department;
	}



	public String getSupplier() {
		return supplier;
	}



	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getPoNumber() {
		return poNumber;
	}



	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}



	public User getLogined_user() {
		return logined_user;
	}



	public void setLogined_user(User logined_user) {
		this.logined_user = logined_user;
	}



	@Override
	public String printZPLFormat() {
		DateFormat defaultDf = DateFormat.getDateTimeInstance();
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		String displayDate;
		Date currentDate = new Date();
		if(date.equals("")) {
			displayDate = dateformat.format(currentDate);
		}else {
			displayDate = date;
		}
   	    
   	    String realDate = defaultDf.format(currentDate); 
		String s = "^XA"
    	 		+ "^FO38,50^A0N,30,41^FD"+company+"^FS"
    	 		+ "^FO38,120,0"
                + "^A0N,30,30"
                + "^FDAsset Number: "+ assetNumber +"^FS"
                + "^FO448,120,0"
                + "^A0N,30,30"
                + "^FDDepartment: "+ department +"^FS"
                + "^FO38,170,0"
                + "^A0N,30,30"
                + "^FDPO Number: "+ poNumber +"^FS"
                + "^FO448,170,0"
                + "^A0N,30,30"
                + "^FDDate: "+ displayDate +"^FS"
                + "^FO38,220,0"
                + "^A0N,30,30"
                + "^FDDevice Name: "+ deviceName +"^FS"               
                + "^FO38,270,0"
                + "^A0N,30,30"
                + "^FDSupplier: "+ supplier +"^FS"
                +"^FO568,220,0"
	            +"^BQN,2,5" 
	            +"^FDXXX"+logined_user.getFullname()+" "+realDate+"^FS"
    	 		+ "^XZ";
		
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
				+ "{labeltype:\"AssetLabel\"},"
				+    "{company:\""+company+"\"},"
				+    "{assetnumber:\""+assetNumber+"\"},"
				+    "{deviceName:\""+deviceName+"\"},"
				+    "{department:\""+department+"\"},"
				+    "{supplier:\""+supplier+"\"},"
				+    "{date:\""+date+"\"},"
				+    "{poNumber:\""+poNumber+"\"},"
				+    "{printMachineName:\""+printMachineName+"\"},"
				+    "{printMachineIp:\""+printMachineIP+"\"}"
				+    "}";

		return log;
	}

}
