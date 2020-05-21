package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gmp.labeling.models.User;

public class SachetSemiProductLabel extends Label {
	
	private String productName;
	private String itemCode;
	private String batch;
	private String quantity;
	private String cartonNo;
	private String useByformat;
	private String useBy;
	private String temperature;
	private String packedBy;
	private User user;

	public SachetSemiProductLabel(String productName, String itemCode, String batch,
			String quantity, String cartonNo, String useByformat, String useBy, String temperature, User user) {
		super();
		this.productName = productName;
		this.itemCode = itemCode;
		this.batch = batch;
		this.quantity = quantity;
		this.cartonNo = cartonNo;
		this.useByformat = useByformat;
		this.useBy = useBy;
		this.temperature = temperature;
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
		String log ="{"
				+ "{labeltype:\"SachetSemiProductLabel\"},"
				+ "{productName:\""+productName+"\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{batch:\""+batch+"\"},"
				+ "{quantity:\""+quantity+"\"},"
				+ "{cartonNo:\""+cartonNo+"\"},"
				+ "{usebyformat:\""+useByformat+"\"},"
				+ "{useBy:\""+useBy+"\"},"
				+ "{temperature:\""+ temperature +"\"},"
				+ "{packedBy:\""+packedBy+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String printZPLFormat() {
		
		double upperWeight = 1.0/42.0;
		double lowerWeight = 1.0/47.0;
		double numberWeight = 1.0/50.0;
		double spaceWeight = 1.0/99.0;
		String s = "^XA"
    	 		+ "^FO28,52,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDProduct:^FS"
    	 		+ "^FO152,20"
    	 		+ "^GB638,95,4^FS";
		
		String target = productName;
		target = target.replaceAll("  ", "");
		if(target.length()>55) {
			char[] targetChar = target.toCharArray();
			int index = 55;
			while(true) {				
				if(targetChar[index]==' ') {
					break;
				}else {
					index--;
				}
			}			
			String line_1 = target.substring(0, index);
			String line_2 = target.substring(index, target.length());
			int upper_line_1 = 0;
			int lower_line_1 = 0;
			int number_line_1 = 0;
			int space_line_1 = 0;
			for(int i = 0; i < line_1.length(); i++) {
				if(Character.isUpperCase(line_1.charAt(i))) {
					upper_line_1++;
				}
				if(Character.isLowerCase(line_1.charAt(i))) {
					lower_line_1++;
				}
				
				if(Character.isDigit(line_1.charAt(i))) {
					number_line_1++;
				}
				if(Character.isSpace(line_1.charAt(i))) {
					space_line_1++;
				}
			}
			double total_line_1 = (upper_line_1*upperWeight)+(lower_line_1*lowerWeight)+(number_line_1*numberWeight)+(space_line_1*spaceWeight);
			int dots_line_1 = (int)((1.0 - total_line_1)*648.0/2.0);
			
	    	   	s+= "^FO"+(200 + dots_line_1)+",30,0"  //70
	    	 		+ "^A@N,36,28,E:ARI002.TTF" 
	    	 		+ "^FD"+ line_1 +"^FS"; 
	    	   	
	    	   	int upper_line_2 = 0;
				int lower_line_2 = 0;
				int number_line_2 = 0;
				int space_line_2 = 0;
				for(int i = 0; i < line_2.length(); i++) {
					if(Character.isUpperCase(line_2.charAt(i))) {
						upper_line_2++;
					}
					if(Character.isLowerCase(line_2.charAt(i))) {
						lower_line_2++;
					}
					
					if(Character.isDigit(line_2.charAt(i))) {
						number_line_2++;
					}
					if(Character.isSpace(line_2.charAt(i))) {
						space_line_2++;
					}
				}
				double total_line_2 = (upper_line_2*upperWeight)+(lower_line_2*lowerWeight)+(number_line_2*numberWeight)+(space_line_2*spaceWeight);
				int dots_line_2 = (int)((1.0 - total_line_2)*648.0/2.0);
				
		    	   	s+= "^FO"+(200 + dots_line_2)+",70,0"  //70
		    	 		+ "^A@N,36,28,E:ARI002.TTF" 
		    	 		+ "^FD"+ line_2 +"^FS";    	
			
		}else {
			int upperCaseCount = 0;
			int lowerCaseCount = 0;
			int numberCount = 0;
			int spaceCount = 0;
			for(int i = 0; i < productName.length(); i++) {
				if(Character.isUpperCase(productName.charAt(i))) {
					upperCaseCount++;
				}
				if(Character.isLowerCase(productName.charAt(i))) {
					lowerCaseCount++;
				}
				
				if(Character.isDigit(productName.charAt(i))) {
					numberCount++;
				}
				if(Character.isSpace(productName.charAt(i))) {
					spaceCount++;
				}
			}
			double total = (upperCaseCount*upperWeight)+(lowerCaseCount*lowerWeight)+(numberCount*numberWeight)+(spaceCount*spaceWeight);
			int dots = (int)((1.0 - total)*648.0/2.0);
			
	    	   	s+= "^FO"+(180 + dots)+",30,0"  //70
	    	 		+ "^A@N,36,28,E:ARI002.TTF" 
	    	 		+ "^FD"+ productName +"^FS";  
		}
    	 		
    	 	s+= "^FO30,150,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDCode No:^FS";
		
				
			s  += "^FO210,148"
			    + "^A@N,45,35,E:TAH005.TTF"
			    + "^FD"+ itemCode +"^FS";
//				"^FO210,138^BY3"
//			  + "^BCN,68,N,N,Y"
//			  + "^FD"+ itemCode +"^FS"	        	
//			  + "^FO385,215,0"
//			  + "^A@N,30,28,E:TAH005.TTF" 
//			  + "^FD"+ itemCode +"^FS";			
			        	
	         s += "^FO30,250,0"
	            + "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDBatch No:^FS"
	            + "^FO210,248"
	            + "^A@N,45,35,E:TAH005.TTF"
	            + "^FD"+ batch +"^FS"
//    	 		+ "^FO255,248^BY3"
//    	 		+ "^BCN,65,N,N,Y"
//    	 		+ "^FD"+ batch +"^FS"
//    	 		+ "^FO350,318,0"
//    	 		+ "^A@N,30,28,E:TAH005.TTF" 
//    	 		+ "^FD"+ batch +"^FS"
    	 		+ "^FO30,355,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FDQuantity:^FS"
    	 		+ "^FO175,355,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FD"+ quantity +"^FS"
    	 		+ "^FO270,355,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FDunits/carton^FS"
    	 		+ "^FO500,355,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FDCarton No:^FS"
    	 		+ "^FO660,355,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FD"+ cartonNo +"^FS"
    	 		+ "^FO30,415,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FD"+ useByformat +"^FS"
    	 		+ "^FO205,415,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FD"+ useBy +"^FS";

	         
	    	 	Date systemDate = new Date();	
	    	 	SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");  
	    	    String strDate = formatter.format(systemDate);  
	    	    
	         s  +=  "^FO30,490"
		 	        + "^GB760,10,5^FS"
	        		+ "^FO50,510,0"           
 	    	 		+ "^A@N,30,30,E:ARI002.TTF"
 	    	 		+ "^CI27"    	 		
 	    	 		+ "^FDStore according to product label or under " + temperature + "°C whichever is lower^FS"
 	    	 		+ "^FO190,540,0"
 	    	 		+ "^A@N,30,30,E:ARI002.TTF"    	 		
 	    	 		+ "^FDin a dry place away from direct sunlight^FS"    	 		
	         
//	         s  +="^FO30,455"
//	    	 	+ "^GB760,10,5^FS";
    	 		

//        	 s  += "^FO50,510,0"           
//        	    + "^A@N,27,27,E:ARI002.TTF"
//        	    + "^CI27"    	 		
//        	    + "^FDInstructions :^FS"
//        	    
//        	    + "^FO190,480,0"
//        	    + "^A@N,22,22,E:ARI002.TTF"    	 		
//        	    + "^FD* Inspect this bulk product as soon as delivered^FS"
//        	    
//        	    + "^FO190,505,0"        	    
//        	    + "^A@N,22,22,E:ARI002.TTF"    	 		
//        	    + "^FD* Store in a cool dry place, below "+ temperature +"°C^FS"
//        	    
//        	    + "^FO190,530,0"        	    
//        	    + "^A@N,22,22,E:ARI002.TTF"    	 		
//        	    + "^FD* Fill into packs within three (3) months^FS"    
//        	    
//        	    + "^FO190,555,0"       	    
//        	    + "^A@N,22,22,E:ARI002.TTF"    	 		
//        	    + "^FD* This bulk pack is for shipment purposes only^FS"   
        	    
	            + "^FO680,390,0"
	            + "^BQN,2,3"
	            + "^FDXXX"+ user.getFullname() + " "+ strDate +"^FS"
        	    
        	    + "^XZ";
				
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}


}
