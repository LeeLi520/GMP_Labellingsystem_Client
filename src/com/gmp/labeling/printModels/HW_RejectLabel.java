package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class HW_RejectLabel extends Label {
	
	private String itemCode;
	private String itemName;
	private String rejectedType;
	private String rejectedValue;
	private String riaNumber;
	private String rejectedDate;
	private String quantity;
	private String unit;
	private String noContainer_1;
	private String noContainer_2;
	private User user;
		

	public HW_RejectLabel(String itemCode, String itemName, String rejectedType, String rejectedValue, String riaNumber,
			String rejectedDate, String quantity, String unit, String noContainer_1, String noContainer_2, User user) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.rejectedType = rejectedType;
		this.rejectedValue = rejectedValue;
		this.riaNumber = riaNumber;
		this.rejectedDate = rejectedDate;
		this.quantity = quantity;
		this.unit = unit;
		this.noContainer_1 = noContainer_1;
		this.noContainer_2 = noContainer_2;
		this.user = user;
	}
	
	public HW_RejectLabel() {
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

	public String getRejectedType() {
		return rejectedType;
	}

	public void setRejectedType(String rejectedType) {
		this.rejectedType = rejectedType;
	}

	public String getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

	public String getRiaNumber() {
		return riaNumber;
	}

	public void setRiaNumber(String riaNumber) {
		this.riaNumber = riaNumber;
	}

	public String getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(String rejectedDate) {
		this.rejectedDate = rejectedDate;
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
				+ "{labeltype:\"HW_ReleaseForSupply\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{rejectedType:\""+ rejectedType +"\"},"
				+ "{rejectedValue:\""+ rejectedValue +"\"},"
				+ "{riaNumber:\""+ riaNumber +"\"},"
				+ "{quantity:\""+quantity + unit +"\"},"
				+ "{rejectedDate:\""+rejectedDate+"\"},"
				+ "{No. of Container:\""+ noContainer_1+" of "+ noContainer_2 +"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"				
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		if(rejectedDate.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			rejectedDate = dateData;
		}
		String s ="^XA" +
	       		  "^FO228,20"+ 
	       		  "^A0N,25,35"+ 
	       		  "^FDGMP Pharmaceuticals(HW)^FS"+
	       		  "^FO300,70"+ 
	       		  "^A0N,60,75"+ 
	       		  "^FDREJECT^FS"+
	       		  "^FO28,135,0"+
	       		  "^A0N,30,35" +
	       		  "^FDItem:^FS" +
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
                  "^FD"+ rejectedType +"^FS"+
                  "^FO170,220,0" +
                  "^AEN,31,14" +
                  "^FD"+ rejectedValue +"^FS"+          
          		  "^FO28,260"+ 
          		  "^A0N,30,35"+
          		  "^FDQuantity:^FS"+
          		  "^FO170,260,0" +
          		  "^AEN,31,14" +
          		  "^FD"+ quantity + " " + unit +"^FS"+         
                  "^FO400,180,0"+
                  "^A0N,30,32"+
                  "^FDRIA No: " + riaNumber + "^FS"+
                  "^FO400,220,0"+
                  "^A0N,30,32"+
                  "^FDDate Rejected: "+ rejectedDate +"^FS"+                  
			      "^FO30,300,0"+
			      "^A0N,30,32"+
			      "^FDApproved By:^FS"+
			      "^FO260,300,0"+
			      "^BQN,2,3" +
			      "^FDXXX"+ user.getFullname() +"^FS" +
			      "^FO400,260"+
			      "^A0N,30,35" +
			      "^FDNo. of Containers: " + noContainer_1 +" of " + noContainer_2 +"^FS" +          
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
