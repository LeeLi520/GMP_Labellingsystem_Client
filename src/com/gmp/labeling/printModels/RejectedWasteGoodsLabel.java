package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RejectedWasteGoodsLabel extends Label {
	
	private String itemName;
	private String itemCode;
	private String batch;
	private String quantity;
	private String unit;
	private String quantityNo;
	
	public RejectedWasteGoodsLabel(String itemName, String itemCode, String batch, String quantity, String unit,
			String quantityNo) {
		super();
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.batch = batch;
		this.quantity = quantity;
		this.unit = unit;
		this.quantityNo = quantityNo;
	}	

	public String getItemName() {
		return itemName;
	}



	public void setItemName(String itemName) {
		this.itemName = itemName;
	}



	public String getItemCode() {
		return itemCode;
	}



	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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



	public String getQuantityNo() {
		return quantityNo;
	}



	public void setQuantityNo(String quntityNo) {
		this.quantityNo = quntityNo;
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
				+ "{labeltype:\"RejectedWasteGoodsLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{batch:\""+batch+"\"},"
				+ "{quantity:\""+quantity+unit+"\"},"
				+ "{quantityNo:\""+quantityNo+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
	       String s = "^XA"       		 
	    	       +"^FO188,30" 
	    	       +"^A0N,35,50" 
	    	       +"^FDGMP Pharmaceuticals^FS"
	    	       +"^FO18,85" 
	    	       +"^A0N,50,70" 
	    	       +"^FDREJECTED WASTE GOODS^FS"
	    	       +"^FO18,150" 
	    	       +"^A0N,30,40"  
	    	       +"^FDProduct Name: "+ this.itemName +"^FS"
	    	       +"^FO18,200" 
	    	       +"^A0N,30,40" 
	    	       +"^FDCode: "+ this.itemCode +"^FS"
	    	       +"^FO448,200" 
	    	       +"^A0N,30,40" 
	    	       +"^FDBatch No: "+ this.batch +"^FS"
	    	       +"^FO18,260" 
	    	       +"^A0N,30,40" 
	    	       +"^FDQty(kg): "+ this.quantity +" "+ this.unit +"^FS"
	    	       +"^FO448,260" 
	    	       +"^A0N,30,40"  
	    	       +"^FDQty(No): "+ this.quantityNo +"^FS"
	    	       +"^FO18,320" 
	    	       +"^A0N,30,40"  
	    	       +"^FDSign/Date:^FS"
	    	       + "^XZ" ;
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
