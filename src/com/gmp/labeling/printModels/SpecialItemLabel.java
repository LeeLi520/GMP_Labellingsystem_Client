package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class SpecialItemLabel extends Label {
	
	private String site;
	private String material;
	private String code;
	private String quantity;
	private String unit;
	private String gin;
	private String receivedDate;
	private User logined_user;
	
	public SpecialItemLabel(User logined_user, String site, String material, String code, String quantity, String unit,
			String gin, String receivedDate) {
		super();
		this.site = site;
		this.material = material;
		this.code = code;
		this.quantity = quantity;
		this.unit = unit;
		this.gin = gin;
		this.receivedDate = receivedDate;
		this.logined_user = logined_user;
	}
	
	
	public User getLogined_user() {
		return logined_user;
	}


	public void setLogined_user(User logined_user) {
		this.logined_user = logined_user;
	}


	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getGin() {
		return gin;
	}

	public void setGin(String gin) {
		this.gin = gin;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
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
				+ "{labeltype:\"SpecialItemLabel\"},"
				+ "{site:\""+site+"\"},"
				+ "{material:\""+material+"\"},"
				+ "{itemCode:\""+code+"\"},"
				+ "{quantity:\""+quantity+"\"},"
				+ "{unit:\""+unit+"\"},"
				+ "{gin:\""+gin+"\"},"
				+ "{receivedDate:\""+receivedDate+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		Date dateTime = new Date();
		DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String timeData = dateTimeFormat.format(dateTime);
		if(receivedDate.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			String dateData = dateFormat.format(currentDate);
			receivedDate = dateData;
		}
		String s ="^XA" +
	             "^FO188,30"+ 
	             "^A0N,30,45"+ 
	             "^FDGMP Pharmaceuticals^FS";
	           switch(site) {
	                case "14 Amax Ave, Girraween":
	                s +="^FO188,60"+ 
	           		  	"^A0N,25,36"+ 
	           			"^FDSpecial item for Girraween^FS";
	                	
	                break;
	                case "60 Huntingwood Drive, Huntingwood":
	                s +="^FO188,60"+ 
	    	            "^A0N,25,36"+ 
	    	           	"^FDSpecial item for Huntingwood^FS";	
		            break;	           
	           }
	                s += "^FO28,100,0"+
	      	             "^A0N,30,35" +
	      	             "^FDMaterial:^FS";
	               
	                String line_1;
	                String line_2;
	           if(material.length()<=30) {	        	   
	      	        s += "^FO170,100,0" +
	      	             "^AEN,31,14" +
	      	             "^FD"+material+"^FS";
	           }else if(Character.isWhitespace(material.charAt(30))) {
	        	   line_1 = material.substring(0, 30);
        		   line_2 = material.substring(31, material.length());
        		   s += "^FO170,100,0" +
  	      	             "^AEN,31,14" +
  	      	             "^FD"+line_1+"^FS";
        		   s +=  "^FO28,130,0" +
    	      	         "^AEN,31,14" +
    	      	         "^FD"+line_2+"^FS";      
	           }
	           else{	        	   
	        	   int sentenceIndex=-1;
	        	   for(int i = 30; i>=0; i--) {
	        		   if(Character.isWhitespace(material.charAt(i))) {
	        			   sentenceIndex = i;
	        			   break;
	        		   }
	        		   
	        	   }
	        	   if(sentenceIndex==-1) {
	        		   line_1 = material.substring(0, 30);
	        		   line_2 = material.substring(30, material.length());
	        	   }else {
	        		   line_1 = material.substring(0, sentenceIndex);
	        		   line_2 = material.substring(sentenceIndex+1, material.length());
	        	   }
	        	   s += "^FO170,100,0" +
	  	      	        "^AEN,31,14" +
	  	      	        "^FD"+line_1+"^FS";
	        	   s += "^FO28,130,0" +
	    	      	    "^AEN,31,14" +
	    	      	    "^FD"+line_2+"^FS"; 
	           }

	             s+= "^FO28,190,0"+
	                 "^A0N,30,35"+
	                 "^FDCode:^FS" +
	                 "^FO150,190,0"+	             
	                 "^AAN,31,13" +
	                 "^FD"+code.toUpperCase()+"^FS" +
	                 "^FO380,190,0"+
	                 "^A0N,30,32"+
	                 "^FDReceived: "+receivedDate+"^FS"+
	                 "^FO28,240,0"+
	                 "^A0N,30,35" +
	                 "^FDQuantity:"+ quantity +" "+unit +"^FS" +
	                 "^FO380,240,0"+
	                 "^A0N,30,35" +
	                 "^FDGIN:^FS" +
	                 "^FO460,240,0"+	        
	                 "^AAN,31,13" +
	                 "^FD"+gin.toUpperCase()+"^FS" +
	                 "^FO628,230,0"+
	 	             "^BQN,2,5"+ 
	 	             "^FDXXX"+logined_user.getFullname()+"^FS" +
	                 "^FO15,365,0"+
	                 "^A0N,20,20" +
	                 "^FDPrint time: "+timeData+"^FS" +
	                 "^FO300,355,0"+
	                 "^A0N,35,40" +
	                 "^FDQUARANTINE^FS" +
	                 "^XZ";
		return s;
	}
	

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
