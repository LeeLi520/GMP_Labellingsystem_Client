package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class ProductionInProcessLabel extends Label {
	
	private String code;
	private String batch;
	private User user;
	private String date;
	
	public ProductionInProcessLabel(String code, String batch, User user, String date) {
		super();
		this.code = code;
		this.batch = batch;
		this.user = user;
		this.date = date;
	}
	

	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getBatch() {
		return batch;
	}



	public void setBatch(String batch) {
		this.batch = batch;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
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
		SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		String printdate = format.format(new Date());
		
		try {
			InetAddress inetAddress = InetAddress. getLocalHost();
			
			printMachineName = inetAddress.getHostName();
			printMachineIP = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Unable to access machine name and ip.");
		}
		String log = "{"
				+ "{labeltype:\"ProductionInProcessLabel\"},"
				+ "{itemCode:\""+ this.code +"\"},"
				+ "{batch:\""+this.batch+"\"},"
				+ "{Sign:\""+this.user.getFullname() +"\"},"
				+ "{date:\""+this.date+"\"},"
				+ "{printdate:\""+printdate+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		String s =  "^XA"       		 
				+"^FO188,30" 
	            +"^A0N,30,45" 
	            +"^FDGMP Pharmaceuticals^FS"
	            +"^FO218,80" 
	            +"^A0N,38,60" 
	            +"^FDIN-PROCESS^FS"
	            +"^FO88,130" 
	            +"^A0N,38,60"  
	            +"^FDPRODUCTION APPROVAL^FS"
	            +"^FO38,210" 
	            +"^AEN,32,15"  
	            +"^FDCode:" + this.code + "^FS"
	            +"^FO448,210" 
	            +"^AEN,32,15"  
	            +"^FDBatch No:" + this.batch + "^FS"
	            +"^FO38,290" 
	            +"^AEN,32,15"  
	            +"^FDSign:"+this.user.getFullname()+"^FS"
	            +"^FO448,290" 
	            +"^AEN,32,15"  
	            +"^FDDate:"+this.date+"^FS"
		        + "^XZ" ;
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
