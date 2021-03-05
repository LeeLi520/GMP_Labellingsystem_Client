package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SachetVidaShipperLabel extends Label {
	
	String companyName;
	String productName; 
	String itemCode; 
	String workOrder; 
	String batch;
	String quantity; 
	String cartonNo; 
	String usebyformat; 
	String useBy; 
	String packedBy; 
	String temperature;
	String btmNoticeInfo;
	
	public SachetVidaShipperLabel(String companyName, String productName, String itemCode, String workOrder,
			String batch, String quantity, String cartonNo, String usebyformat, String useBy, String packedBy,
			String temperature, String btmNoticeInfo) {
		super();
		this.companyName = companyName;
		this.productName = productName;
		this.itemCode = itemCode;
		this.workOrder = workOrder;
		this.batch = batch;
		this.quantity = quantity;
		this.cartonNo = cartonNo;
		this.usebyformat = usebyformat;
		this.useBy = useBy;
		this.packedBy = packedBy;
		this.temperature = temperature;
		this.btmNoticeInfo = btmNoticeInfo;
	}
	
	
	public SachetVidaShipperLabel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
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

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getBtmNoticeInfo() {
		return btmNoticeInfo;
	}

	public void setBtmNoticeInfo(String btmNoticeInfo) {
		this.btmNoticeInfo = btmNoticeInfo;
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
				+ "{labeltype:\"UniversalProductLabel\"},"
				+ "{companyName:\""+companyName+"\"},"
				+ "{productName:\""+productName+"\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{W/O:\""+ workOrder +"\"},"
				+ "{batch:\""+batch+"\"},"
				+ "{quantity:\""+quantity+"\"},"
				+ "{cartonNo:\""+cartonNo+"\"},"
				+ "{useByFormat:\""+usebyformat+"\"},"
				+ "{useBy:\""+useBy+"\"},"
				+ "{packedBy:\""+packedBy+"\"},"
				+ "{temperature:\""+temperature+"\"},"
				+ "{btmNoticeInfo:\""+btmNoticeInfo+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String printZPLFormat() {
		double upperWeight = 1.0/43.0;
		double lowerWeight = 1.0/50.0;
		double numberWeight = 1.0/50.0;
		double spaceWeight = 1.0/99.0;
		String s = "^XA"
    	 		+ "^FO28,52,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDProduct:^FS"
    	 		+ "^FO152,20"
    	 		+ "^GB638,95,4^FS";
    	 		
		
		int Com_upperCaseCount = 0;
		int Com_lowerCaseCount = 0;
		int Com_numberCount = 0;
		int Com_spaceCount = 0;
		
		for(int i = 0; i < companyName.length(); i++) {
			if(Character.isUpperCase(companyName.charAt(i))) {
				Com_upperCaseCount++;
			}
			if(Character.isLowerCase(companyName.charAt(i))) {
				Com_lowerCaseCount++;
			}
			
			if(Character.isDigit(companyName.charAt(i))) {
				Com_numberCount++;
			}
			if(Character.isSpace(companyName.charAt(i))) {
				Com_spaceCount++;
			}
		}
		
		double Com_total = (Com_upperCaseCount*upperWeight)+(Com_lowerCaseCount*lowerWeight)+(Com_numberCount*numberWeight)+(Com_spaceCount*spaceWeight);
		int Com_dots = (int)((1.05 - Com_total)*648.0/2.0);
		
		    s +=   "^FO"+(80 + Com_dots)+",30,0"
	    	 	 + "^A@N,38,38,E:ARI004.TTF" 
	    	 	 + "^FD"+ companyName +"^FS";
		
		
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
		int dots = (int)((1.00 - total)*598.0/2.0);
		
    	   	s+= "^FO"+(160 + dots)+",70,0"
    	 		+ "^A@N,36,28,E:ARI002.TTF" 
    	 		+ "^FD"+ productName +"^FS"    	 		
    	 		+ "^FO30,150,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF"
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
			        	
	         s += "^FO30,260,0"
	            + "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDW/O No:^FS"
    	 		+ "^FO210,248^BY3"
    	 		+ "^BCN,65,N,N,Y"
    	 		+ "^FD"+ workOrder +"^FS"
    	 		+ "^FO305,318,0"
    	 		+ "^A@N,30,28,E:TAH005.TTF" 
    	 		+ "^FD"+ workOrder +"^FS"
    	 		+ "^FO500,260,0"
	            + "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDBatch No:^FS"
    	 		+ "^FO630,260,0"
	            + "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FD"+ batch +"^FS"
    	 		+ "^FO30,365,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FDQuantity:^FS"
    	 		+ "^FO175,365,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FD"+ quantity +"^FS"
    	 		+ "^FO310,365,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FDunits/carton^FS"
    	 		+ "^FO520,365,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FDCarton No:^FS"
    	 		+ "^FO680,365,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FD"+ cartonNo +"^FS"
    	 		+ "^FO30,415,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FD"+ usebyformat +"^FS"
	            + "^FO310,415,0"
	         	+ "^A@N,33,29,E:TAH005.TTF" 
	         	+ "^FD"+ useBy +"^FS"
    	 	    + "^FO520,415,0"
    	 	    + "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDPacked by:^FS";
	         
	         s  += "^FO680,415,0"
	 	        + "^A@N,33,29,E:TAH005.TTF" 
	 	        + "^FD"+ packedBy +"^FS"
	            + "^FO30,490"
	    	 	+ "^GB760,10,5^FS";
    	 		
    	 		if(btmNoticeInfo.equals("")) { 
        	 		s  += "^FO50,510,0"           
        	    	 		+ "^A@N,30,30,E:ARI002.TTF"
        	    	 		+ "^CI27"    	 		
        	    	 		+ "^FDStore according to product label or under "+ temperature +"°C whichever is lower^FS"
        	    	 		+ "^FO190,540,0"
        	    	 		+ "^A@N,30,30,E:ARI002.TTF"    	 		
        	    	 		+ "^FDin a dry place away from direct sunlight^FS"    	 		
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

}
