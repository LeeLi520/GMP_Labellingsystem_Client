package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SachetMilkPowderLabel extends Label {

	private String productName;
	private String itemCode;
	private String batch;
	private String quantity;
	private String cartonNo;
	private String usebyformat;
	private String useBy;
	private String packedBy;
	private String regNo;
	private String temperature;
	private String btmNoticeInfo;
	
	public SachetMilkPowderLabel(String productName, String itemCode, String batch, String quantity,
			String cartonNo, String usebyformat, String useBy, String packedBy, String regNo, String temperature,
			String btmNoticeInfo) {
		super();
		this.productName = productName;
		this.itemCode = itemCode;
		this.batch = batch;
		this.quantity = quantity;
		this.cartonNo = cartonNo;
		this.usebyformat = usebyformat;
		this.useBy = useBy;
		this.packedBy = packedBy;
		this.regNo = regNo;
		this.temperature = temperature;
		this.btmNoticeInfo = btmNoticeInfo;
	}
		

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getCartonNo() {
		return cartonNo;
	}

	public void setCartonNo(String cartonNo) {
		this.cartonNo = cartonNo;
	}

	public String getUsebyformat() {
		return usebyformat;
	}

	public void setUsebyformat(String usebyformat) {
		this.usebyformat = usebyformat;
	}

	public String getUseBy() {
		return useBy;
	}

	public void setUseBy(String useBy) {
		this.useBy = useBy;
	}

	public String getPackedBy() {
		return packedBy;
	}

	public void setPackedBy(String packedBy) {
		this.packedBy = packedBy;
	}

	public String getBtmNoticeInfo() {
		return btmNoticeInfo;
	}

	public void setBtmNoticeInfo(String btmNoticeInfo) {
		this.btmNoticeInfo = btmNoticeInfo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	@Override
	public String printZPLFormat() {
		double upperWeight = 1.0/43.0;
		double lowerWeight = 1.0/50.0;
		double numberWeight = 1.0/50.0;
		double spaceWeight = 1.0/99.0;
		
		String s = "^XA"
    	 		+ "^FO28,52,0"
    	 		+ "^A@N,32,24,E:TAH005.TTF"
    	 		+ "^FDProduct:^FS"
    	 		+ "^FO152,20"
    	 		+ "^GB638,95,4^FS";
		
//		int upperCaseCount = 0;
//		int lowerCaseCount = 0;
//		int numberCount = 0;
//		int spaceCount = 0;
//		for(int i = 0; i < productName.length(); i++) {
//			if(Character.isUpperCase(productName.charAt(i))) {
//				upperCaseCount++;
//			}
//			if(Character.isLowerCase(productName.charAt(i))) {
//				lowerCaseCount++;
//			}
//			
//			if(Character.isDigit(productName.charAt(i))) {
//				numberCount++;
//			}
//			if(Character.isSpace(productName.charAt(i))) {
//				spaceCount++;
//			}
//		}
//		
//		double total = (upperCaseCount*upperWeight)+(lowerCaseCount*lowerWeight)+(numberCount*numberWeight)+(spaceCount*spaceWeight);
//		int dots = (int)((1.05 - total)*648.0/2.0);
//    	 		
		
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
			
	    	   	s+= "^FO"+(180 + dots)+",50,0"  //70
	    	 		+ "^A@N,36,28,E:ARI002.TTF" 
	    	 		+ "^FD"+ productName +"^FS";  
		}
		
 			s += "^FO30,155,0"
 			  + "^A@N,32,24,E:TAH005.TTF"
 			  + "^FDCode No:^FS";
		
		if(itemCode.length()==10) {
			String temp_1 = itemCode.substring(0, 4);
			String temp_2 = itemCode.substring(4, 10);
			s += "^FO210,138^BY3"
	    	  + "^BCN,68,N,N,Y"
	    	  + "^FD>6 "+ temp_1 +">9"+ temp_2 +"^FS"	        	
		      + "^FO385,215,0"
	    	  + "^A@N,30,28,E:TAH005.TTF" 
	    	  + "^FD"+ itemCode +"^FS";
		}else {			
			s += "^FO210,138^BY3"
			  + "^BCN,68,N,N,Y"
			  + "^FD"+ itemCode +"^FS"	        	
			  + "^FO385,215,0"
			  + "^A@N,30,28,E:TAH005.TTF" 
			  + "^FD"+ itemCode +"^FS";			
		}
			        	
	         s += "^FO30,270,0"
	        	+ "^A@N,32,24,E:TAH005.TTF"
    	 		+ "^FDBatch No:^FS"
    	 		+ "^FO255,248^BY3"
    	 		+ "^BCN,65,N,N,Y"
    	 		+ "^FD"+ batch +"^FS"
    	 		+ "^FO350,318,0"
    	 		+ "^A@N,30,28,E:TAH005.TTF" 
    	 		+ "^FD"+ batch +"^FS"
    	 		+ "^FO30,365,0"
    	 		+ "^A@N,32,24,E:TAH005.TTF"
    	 		+ "^FDQuantity:^FS"
    	 		+ "^FO175,365,0"
    	 		+ "^A@N,32,24,E:TAH005.TTF"
    	 		+ "^FD"+ quantity +"^FS"
    	 		+ "^FO288,365,0"
    	 		+ "^A@N,32,24,E:TAH005.TTF"
    	 		+ "^FDunits/carton^FS"
    	 		+ "^FO488,365,0"
    	 		+ "^A@N,32,24,E:TAH005.TTF"
    	 		+ "^FDCarton No:^FS"
    	 		+ "^FO648,365,0"
    	 		+ "^A@N,32,24,E:TAH005.TTF"
    	 		+ "^FD"+ cartonNo +"^FS"
    	 		+ "^FO30,415,0"
    	 		+ "^A@N,32,24,E:TAH005.TTF"
    	 		+ "^FD"+ usebyformat +"^FS"
	            + "^FO288,415,0"
	            + "^A@N,32,24,E:TAH005.TTF" 
	         	+ "^FD"+ useBy +"^FS"
    	 	    + "^FO488,415,0"
    	 	    + "^A@N,32,24,E:TAH005.TTF"
    	 		+ "^FDPacked by:^FS";
	         s	+= "^FO580,270,0"
	        		+ "^A@N,32,24,E:TAH005.TTF"
 	    	 		+ "^FDReg no.^FS"
 	    	 		+ "^FO680,270,0"
 	    	 		+ "^A@N,32,24,E:TAH005.TTF"
 	    	 		+ "^FD"+ regNo +"^FS";
	         s  += "^FO648,415,0"
	            + "^A@N,32,24,E:TAH005.TTF"
	 	        + "^FD"+ packedBy +"^FS";
	      
	             s += "^FO30,490"
	    	 	   +  "^GB760,8,4^FS";
    	 		
    	 		if(btmNoticeInfo.equals("")) { 
        	 		s  += "^FO50,510,0"           
        	    	 		+ "^A@N,35,35,E:ARI002.TTF"
        	    	 		+ "^CI27"    	 		
        	    	 		+ "^FDStorage Condition: Store in a cool dry place below "+ temperature +"°C^FS"   	 		
        	    	 		+ "^XZ";
        	 	}else {
        	 	   if(btmNoticeInfo.length()>66) {
        	 		  String line_1;
        	 		  String line_2;
        	 		  int i = 65;
        	 		  char temp = btmNoticeInfo.charAt(i);
        	 		  while(temp!=' ') {
        	 			  i--;
        	 			  temp = btmNoticeInfo.charAt(i);
        	 		  }
        	 		  line_1 = btmNoticeInfo.substring(0, i);
        	 		  line_2 = btmNoticeInfo.substring(i+1, btmNoticeInfo.length());
        	 		  s  += "^FO50,510,0"           
         	    	 		+ "^A@N,30,30,E:ARI002.TTF"
         	    	 		+ "^CI27"    	 		
         	    	 		+ "^FD"+line_1+"^FS"
         	    	 		+ "^FO50,540,0"
         	    	 		+ "^A@N,30,30,E:ARI002.TTF"
         	    	 		+ "^CI27"
         	    	 		+ "^FD"+line_2+"^FS"    	 		
         	    	 		+ "^XZ";
        	 	   }else {
        	 		  s  += "^FO50,510,0"           
          	    	 		+ "^A@N,30,30,E:ARI002.TTF"
          	    	 		+ "^CI27"
          	    	 		+"^FD"+ btmNoticeInfo +"^FS";
        	 	   }
        	 	}
    	 		s += "^XZ";			
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
				+ "{labeltype:\"SachetMilkPowderLabel\"},"
				+ "{productName:\""+productName+"\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{batch:\""+batch+"\"},"
				+ "{quantity:\""+quantity+"\"},"
				+ "{cartonNo:\""+cartonNo+"\"},"
				+ "{useBy:\""+useBy+"\"},"
				+ "{packedBy:\""+packedBy+"\"},"
				+ "{temperature:\""+temperature+"\"},"
				+ "{btmNoticeInfo:\""+btmNoticeInfo+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}
}
