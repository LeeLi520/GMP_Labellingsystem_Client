package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class HW_ProvisionalReleaseLabel extends Label {
	
	private String itemName;
	private String itemCode;
	private String ginNumber;
	private String prNumber;
	private String releasedDate;
	private String expiryDate;
	private String quantity;
	private String unit;
	private String noContainer_1;
	private String noContainer_2;
	private User user;

	public HW_ProvisionalReleaseLabel(String itemName, String itemCode, String ginNumber, String prNumber,
			String releasedDate, String expiryDate, String quantity, String unit, String noContainer_1,
			String noContainer_2, User user) {
		super();
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.ginNumber = ginNumber;
		this.prNumber = prNumber;
		this.releasedDate = releasedDate;
		this.expiryDate = expiryDate;
		this.quantity = quantity;
		this.unit = unit;
		this.noContainer_1 = noContainer_1;
		this.noContainer_2 = noContainer_2;
		this.user = user;
	}
	
	public HW_ProvisionalReleaseLabel() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getGinNumber() {
		return ginNumber;
	}

	public void setGinNumber(String ginNumber) {
		this.ginNumber = ginNumber;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}

	public String getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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
				+ "{labeltype:\"HW_ProvisionalReleaseLabel\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{itemName:\""+itemName+"\"},"
				+ "{ginNumber:\""+ginNumber+"\"},"
				+ "{prNumber:\""+prNumber+"\"},"
				+ "{quantity:\""+quantity+"\"},"
				+ "{unit:\""+unit+"\"},"
				+ "{releasedDate:\""+releasedDate+"\"},"
				+ "{expiryDate:\""+expiryDate+"\"},"
				+ "{No. of Container:\""+ noContainer_1 +" of "+ noContainer_2 +"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		if(releasedDate.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			releasedDate = dateData;
		}
		if(expiryDate.equals("")) {
			expiryDate = "N/A";
		}
		String s = null;
		s = "^XA" +
	        "^FO180,30"+ 
	        "^A@N,38,38,E:ARI004.TTF"+
	        "^FDGMP Pharmaceuticals(HW)^FS" +	             
	        "^FO240,70,0"+ 
	        "^A@N,30,30,E:ARI004.TTF"+
	        "^FDPROVISIONAL RELEASE^FS" +
	             
	        "^FO48,120,0"+ 
	        "^A@N,36,25,E:TAH005.TTF"+
	        "^FDItem Name: "+ itemName +"^FS" +
	             
            "^FO48,200,0"+ 
   			"^A@N,36,25,E:TAH005.TTF"+
   			"^FDItem Code:^FS" +
   			 
            "^FO240,190^BY2"+
            "^BCN,60,N,N,N"+
            "^FD"+ itemCode +"^FS"+
            "^FO300,255,0"+
            "^AAN,31,13" +
            "^FD"+ itemCode +"^FS" +
   			 
			"^FO48,300,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDGIN No:^FS" +
			
            "^FO180,300,0"+ 
            "^A@N,36,25,E:TAH005.TTF"+
            "^FD"+ ginNumber +"^FS" +
						
            "^FO48,360,0"+ 
            "^A@N,36,25,E:TAH005.TTF"+
            "^FDPR No:^FS" +
            
            "^FO180,360,0"+ 
            "^A@N,36,25,E:TAH005.TTF"+
            "^FD"+ prNumber +"^FS" +
            
            "^FO48,420,0"+ 
            "^A@N,36,25,E:TAH005.TTF"+
            "^FDQuantity:^FS" +
            
            "^FO180,420,0"+ 
            "^A@N,36,25,E:TAH005.TTF"+
            "^FD"+ quantity +" "+ unit +"^FS" +
				 
			"^FO420,300,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDDate Released:^FS" +
			
			"^FO620,300,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FD"+ releasedDate +"^FS" +
							 
			"^FO420,360,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDExpiry Date:^FS" +
			
			"^FO620,360,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FD"+ expiryDate +"^FS" +
			
            "^FO420,420,0"+ 
            "^A@N,36,25,E:TAH005.TTF"+
            "^FDNo. of Containers: "+ noContainer_1+" of "+ noContainer_2 +"^FS" +
				 
			"^FO48,500,0"+ 
			"^A@N,36,25,E:TAH005.TTF"+
			"^FDApproved By:^FS" +
				 
	        "^FO240,490,0"+
	        "^BQN,2,3" +
	        "^FDXXX"+ user.getFullname() +"^FS" +
				 
			"^FO420,500,0"+ 
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
