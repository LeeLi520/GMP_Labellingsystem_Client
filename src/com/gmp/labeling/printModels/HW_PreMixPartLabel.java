package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HW_PreMixPartLabel extends Label {
	private String itemCode;
	private String itemName;
	private String batch;
	private String premixPart;
	private String bag;
	private String bags;
	private String weight;
	private String unit;
	private String doneby;
	private String date;	

	public HW_PreMixPartLabel(String itemCode, String itemName, String batch, String premixPart, String bag,
			String bags, String weight, String unit, String doneby, String date) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.batch = batch;
		this.premixPart = premixPart;
		this.bag = bag;
		this.bags = bags;
		this.weight = weight;
		this.unit = unit;
		this.doneby = doneby;
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



	public String getBatch() {
		return batch;
	}



	public void setBatch(String batch) {
		this.batch = batch;
	}



	public String getPremixPart() {
		return premixPart;
	}



	public void setPremixPart(String premixPart) {
		this.premixPart = premixPart;
	}



	public String getBag() {
		return bag;
	}



	public void setBag(String bag) {
		this.bag = bag;
	}



	public String getBags() {
		return bags;
	}



	public void setBags(String bags) {
		this.bags = bags;
	}



	public String getWeight() {
		return weight;
	}



	public void setWeight(String weight) {
		this.weight = weight;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public String getDoneby() {
		return doneby;
	}



	public void setDoneby(String doneby) {
		this.doneby = doneby;
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
				+ "{labeltype:\"HW_PremixPartLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{batch:\""+batch+"\"},"
				+ "{premixPart:\""+premixPart+"\"},"
				+ "{No. of Bags:\""+bag+" of "+ bags+"\"},"
				+ "{NetWeight:\""+weight+" "+ unit +"\"},"
				+ "{DoneBy:\""+doneby+"\"},"
				+ "{printedDate:\""+date+"\"},"
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
		
      String s = 
    		  "^XA" +
    		  "^FO180,30"+ 
    		  "^A@N,38,38,E:ARI004.TTF"+
    		  "^FDGMP Pharmaceuticals(HW)^FS" +	             
    		  "^FO240,70,0"+ 
    		  "^A@N,30,30,E:ARI004.TTF"+
    		  "^FDFD Pre-mix Part Label^FS"+
    		  "^FO48,120,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FDProduct Name: "+ this.itemName+"^FS" +        
    		  "^FO48,200,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FDProduct Code:^FS" +
    		  "^FO240,190^BY2"+
    		  "^BCN,60,N,N,N"+
    		  "^FD"+ this.itemCode +"^FS"+
    		  "^FO300,255,0"+
    		  "^AAN,31,13" +
    		  "^FD"+ this.itemCode +"^FS" +    
    		  "^FO48,300,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FDBatch No.:^FS" +
    		  "^FO180,300,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FD"+ this.batch +"^FS" +	
    		  "^FO48,360,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FDPre-mixing Part:^FS" +
    		  "^FO270,360,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FD"+ this.premixPart +"^FS" + 
    		  "^FO48,420,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FDNo. of Bags: "+ this.bag +" of "+ this.bags +"^FS" +
    		  "^FO420,360,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FDNet weight: "+ this.weight +" "+ this.unit +"^FS" +		 
    		  "^FO420,420,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FDPrinted Date:^FS" +
    		  "^FO600,420,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FD"+ this.date +"^FS" +
    		  "^FO48,500,0"+ 
    		  "^A@N,36,25,E:TAH005.TTF"+
    		  "^FDDone By:^FS" +
    		  "^FO240,490,0"+
    		  "^BQN,2,3" +
    		  "^FDXXX"+ this.doneby +"^FS" +		 	 
    		  "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
