package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class DeviationLabel extends Label{

	private String item_Code;
	private String item_Name;
	private String gin_Number;
	private String item_Qty;
	private String item_unit;
	private String date;
	private User logined_user;
		
	public DeviationLabel(String item_Code, String item_Name, String gin_Number, String item_Qty, String item_unit, String date, User logined_user) {
		super();
		this.item_Code = item_Code;
		this.item_Name = item_Name;
		this.gin_Number = gin_Number;
		this.item_Qty = item_Qty;
		this.item_unit = item_unit;
		this.date = date;
		this.logined_user = logined_user;
	}
		

	
	
	public String getItem_Code() {
		return item_Code;
	}



	public void setItem_Code(String item_Code) {
		this.item_Code = item_Code;
	}



	public String getItem_Name() {
		return item_Name;
	}



	public void setItem_Name(String item_Name) {
		this.item_Name = item_Name;
	}



	public String getGin_Number() {
		return gin_Number;
	}



	public void setGin_Number(String gin_Number) {
		this.gin_Number = gin_Number;
	}



	public String getItem_Qty() {
		return item_Qty;
	}

	

	public String getItem_unit() {
		return item_unit;
	}



	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
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
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		Date currentDate = new Date();
		if(date.equals("")) {			
			String dateData = dateFormat.format(currentDate);
			date = dateData;
		}
		String RealDateData = defaultDf.format(currentDate);		
		String s ="^XA" +
	             "^FO238,50"+ 
	             "^A0N,25,36"+ 
	             "^FDGMP Pharmaceuticals^FS" +
	             "^FO238,80"+ 
	             "^A0N,25,36"+ 
	             "^FDDEVIATION MATERIAL^FS" +
	             "^FO88,140,0"+ 
	             "^AEN,23,14"+
	             "^FDCode:^FS" +
	             "^FO225,120^BY2"+
	             "^BCN,50,N,N,N"+
	             "^FD"+item_Code+"^FS"+
	             "^FO285,175,0"+
	             "^AAN,23,14"+
	             "^FD"+item_Code+"^FS" +
	             "^FO38,240,0"+  
	             "^AEN,23,14" +
	             "^FDMaterial:^FS";
		if(item_Name.length()<=27) {
			s += "^FO220,240,0" +
			       "^AEN,23,14" +
			     "^FD"+item_Name+"^FS";
		}else{
			s += "^FO220,240,0" +
				   "^AFN,26,13" +
			     "^FD"+item_Name+"^FS";
		}
	    s   +=   "^FO38,340,0"+  
	             "^AEN,23,14" +
	             "^FDGin:^FS" + 
	             "^FO125,320^BY2";
	    
	    String gin_Code = null;
		String aCode = String.valueOf(gin_Number.charAt(0)) + String.valueOf(gin_Number.charAt(1));
		if(aCode.equals("OR")) {
			gin_Code = String.valueOf(gin_Number.charAt(2))+String.valueOf(gin_Number.charAt(3))+String.valueOf(gin_Number.charAt(4))+String.valueOf(gin_Number.charAt(5))+String.valueOf(gin_Number.charAt(6));
		    s += "^BCN,50,N,N,N"+
		         "^FD"+gin_Code+"^FS"+
		         "^FO155,375,0"+
		         "^AAN,23,14" +
		         "^FD"+gin_Number+"^FS";
		}else {
			gin_Code = gin_Number;
			s += "^BCN,50,N,N,N"+
			     "^FD"+gin_Code+"^FS"+
			     "^FO180,375,0"+
			     "^AAN,23,14" +
			     "^FD"+gin_Number+"^FS";
		}
	    
	    s   +=   "^FO360,340,0"+
	             "^AEN,23,14" +
	             "^FDIssued date:^FS" +
	             "^FO610,340,0"+
	             "^AEN,23,14" +
	             "^FD" +date+"^FS" ;
	            
	    s  +=    "^FO88,440,0"+ 
	             "^AEN,23,14"+
	             "^FDQTY:^FS"+
	             "^FO185,420^BY2"+
	             "^BCN,50,N,N,N"+
	             "^FD"+item_Qty+"^FS"+
	             "^FO230,475,0"+
	             "^AAN,23,14"+
	             "^FD"+item_Qty+"^FS"+
	             "^FO400,475,0"+
	             "^AEN,23,14"+
	             "^FD"+item_unit+"^FS"+	 	                         
	             "^FO480,390,0"+
	             "^BQN,2,5" +
	             "^FDXXX"+logined_user.getFullname()+" "+RealDateData+"^FS" +
	             "^XZ";
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
		String log ="{"
				  + "{labeltype:\"DeviationLabel\"},"
				  + "{item_Code:\""+item_Code+"\"},"
				  + "{item_Name:\""+item_Name+"\"},"
				  + "{gin_Number:\""+gin_Number+"\"},"
				  + "{item_Qty:\""+item_Qty+"\"},"
				  + "{item_unit:\""+item_unit+"\"},"
				  + "{date:\""+date+"\"},"
				  + "{printMachineName:\""+printMachineName+"\"},"
				  + "{printMachineIp:\""+printMachineIP+"\"}"
				  + "}";
		return log;
	}

}
