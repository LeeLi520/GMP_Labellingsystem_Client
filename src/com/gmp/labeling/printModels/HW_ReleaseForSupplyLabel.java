package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class HW_ReleaseForSupplyLabel extends Label {
	
	private String itemCode;
	private String itemName;
	private String batch;
	private String quantity;
	private String unit;
	private String manufactureDate;
	private String expiryDate;
	private String noPallet_1;
	private String noPallet_2;
	private User user;
	
	public HW_ReleaseForSupplyLabel(String itemCode, String itemName, String batch, String quantity, String unit,
			String manufactureDate, String expiryDate, String noPallet_1, String noPallet_2, User user) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.batch = batch;
		this.quantity = quantity;
		this.unit = unit;
		this.manufactureDate = manufactureDate;
		this.expiryDate = expiryDate;
		this.noPallet_1 = noPallet_1;
		this.noPallet_2 = noPallet_2;
		this.user = user;
	}
	
	public HW_ReleaseForSupplyLabel() {
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

	public String getnoPallet_1() {
		return noPallet_1;
	}

	public void setnoPallet_1(String noPallet_1) {
		this.noPallet_1 = noPallet_1;
	}

	public String getnoPallet_2() {
		return noPallet_2;
	}

	public void setnoPallet_2(String noPallet_2) {
		this.noPallet_2 = noPallet_2;
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
				+ "{labeltype:\"HW_ReleaseForSupply\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{batch:\""+ batch +"\"},"
				+ "{quantity:\""+quantity + unit +"\"},"
				+ "{expiryDate:\""+expiryDate+"\"},"
				+ "{manufactureDate:\""+manufactureDate+"\"},"
				+ "{No. of Pallet:\""+ noPallet_1+" of "+ noPallet_2 +"\"},"
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
		String s ="^XA" +
	             "^FO228,20"+ 
	             "^A0N,25,35"+ 
	             "^FDGMP Pharmaceuticals(HW)^FS"+
	             "^FO60,70"+ 
	             "^A0N,60,75"+ 
	             "^FDRELEASE FOR SUPPLY^FS"+
	             "^FO28,135,0"+
	             "^A0N,30,35" +
	             "^FDProduct:^FS" +
	             "^FO170,135,0" +
	             "^AEN,31,14" +
	             "^FD"+ itemName +"^FS"+
	             
	             "^FO28,180"+ 
	             "^A0N,30,35"+
	             "^FDCode:^FS"+
	             "^FO170,180,0" +
	             "^AEN,31,14" +
	             "^FD"+ itemCode +"^FS"+
	             "^FO28,220"+ 
	             "^A0N,30,35"+
	             "^FDBatch:^FS"+
	             "^FO170,220,0" +
	             "^AEN,31,14" +
	             "^FD"+ batch +"^FS"+
	             
	             "^FO28,260"+ 
	             "^A0N,30,35"+
	             "^FDQuantity:^FS"+
	             "^FO170,260,0" +
	             "^AEN,31,14" +
	             "^FD"+ quantity + " " + unit +"^FS"+
	             
                 "^FO400,180,0"+
                 "^A0N,30,32"+
                 "^FDExpiry date: "+ expiryDate +"^FS"+
                 "^FO400,220,0"+
                 "^A0N,30,32"+
                 "^FDManufacture date: "+ manufactureDate+"^FS"+
                 
                 
				 "^FO30,300,0"+
				 "^A0N,30,32"+
				 "^FDApproved By:^FS"+
	             "^FO260,300,0"+
	             "^BQN,2,3" +
	             "^FDXXX"+ user.getFullname() +"^FS" +
	             "^FO400,260"+
	             "^A0N,30,35" +
	             "^FDNo. of Pallet: "+ noPallet_1 + " of "+ noPallet_2 +"^FS" +
	             
				 "^FO400,300"+
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
