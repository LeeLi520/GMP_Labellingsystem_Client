package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MaxiProductLabel extends Label {
	
	private String productName;
	private String itemCode;
	private String batch;
	private String quantity;
	private String cartonNo;
	private String usebyformat;
	private String useBy;
	private String packedBy;
	private String regNo;
	private String btmNoticeInfo;

	public MaxiProductLabel(String productName, String itemCode, String batch, String quantity, String cartonNo,
			String useByFormat, String useBy, String packedBy, String regNo, String btmNoticeInfo) {
		super();
		this.productName = productName;
		this.itemCode = itemCode;
		this.batch = batch;
		this.quantity = quantity;
		this.cartonNo = cartonNo;
		this.usebyformat = useByFormat;
		this.useBy = useBy;
		this.packedBy = packedBy;
		this.regNo = regNo;
		this.btmNoticeInfo = btmNoticeInfo;
	}
	
	
	public String getUsebyformat() {
		return usebyformat;
	}



	public void setUsebyformat(String usebyformat) {
		this.usebyformat = usebyformat;
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
	
	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	
	public String getBtmNoticeInfo() {
		return btmNoticeInfo;
	}

	public void setBtmNoticeInfo(String btmNoticeInfo) {
		this.btmNoticeInfo = btmNoticeInfo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String printZPLFormat() {
		String s = "^XA"
    	 		+ "^FO28,52,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDProduct:^FS"
    	 		+ "^FO152,20"
    	 		+ "^GB638,95,4^FS"
    	 		+ "^FO348,30,0"
    	 		+ "^A@N,38,38,E:ARI004.TTF" 
    	 		+ "^FDMaxigenes^FS";
		
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
		double upperWeight = 1.0/43.0;
		double lowerWeight = 1.0/50.0;
		double numberWeight = 1.0/50.0;
		double spaceWeight = 1.0/99.0;
		double total = (upperCaseCount*upperWeight)+(lowerCaseCount*lowerWeight)+(numberCount*numberWeight)+(spaceCount*spaceWeight);
		int dots = (int)((1.05 - total)*598.0/2.0);
		
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
    	 		+ "^FDBatch No:^FS"
    	 		+ "^FO255,248^BY3"
    	 		+ "^BCN,65,N,N,Y"
    	 		+ "^FD"+ batch +"^FS"
    	 		+ "^FO350,318,0"
    	 		+ "^A@N,30,28,E:TAH005.TTF" 
    	 		+ "^FD"+ batch +"^FS";
    	 	if(regNo.equals("")) {
    	 		//No input for reg No means no regNo output
    	 	}else {
    	 		s	+= "^FO580,260,0"
    		            + "^A@N,36,25,E:TAH005.TTF"
    	    	 		+ "^FDReg no:^FS"
    	    	 		+ "^FO680,260,0"
    		            + "^A@N,36,25,E:TAH005.TTF"
    	    	 		+ "^FD"+ regNo +"^FS";
    	 	}
    	 	 
	            
    	 		s   += "^FO30,365,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FDQuantity:^FS"
    	 		+ "^FO175,365,0"
    	 		+ "^A@N,33,29,E:TAH005.TTFF" 
    	 		+ "^FD"+ quantity +"^FS"
    	 		+ "^FO235,365,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FDunits/carton^FS"
    	 		+ "^FO440,365,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FDCarton No:^FS"
    	 		+ "^FO600,365,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FD"+ cartonNo +"^FS"
    	 		+ "^FO30,415,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FD"+ usebyformat +"^FS"
	            + "^FO235,415,0"
	         	+ "^A@N,33,29,E:TAH005.TTF" 
	         	+ "^FD"+ useBy +"^FS"
    	 	    + "^FO440,415,0"
    	 	    + "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDPacked by:^FS";

	         s  += "^FO600,415,0"
	            + "^A@N,33,29,E:TAH005.TTF" 
	            + "^FD"+ packedBy +"^FS";

//    	 	 s += "^FO30,460,0"
//    	        + "^A@N,30,28,E:ARI002.TTF"
//    	    	+ "^FDPacked in Australia with Imported Ingredients^FS"   	 			 
    	 	 s += "^FO30,490"
    	 		+ "^GB760,10,5^FS";
    	 	if(btmNoticeInfo.equals("")) { 
    	 		s  += "^FO50,510,0"           
    	    	 		+ "^A@N,30,30,E:ARI002.TTF"
    	    	 		+ "^CI27"    	 		
    	    	 		+ "^FDStore according to product label or under 30°C whichever is lower^FS"
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
				+ "{labeltype:\"MaxiProductLabel\"},"
				+ "{productName:\""+productName+"\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{batch:\""+batch+"\"},"
				+ "{quantity:\""+quantity+"\"},"
				+ "{cartonNo:\""+cartonNo+"\"},"
				+ "{useBy:\""+useBy+"\"},"
				+ "{packedBy:\""+packedBy+"\"},"
				+ "{regNo:\""+regNo+"\"},"
				+ "{btmNoticeInfo:\""+btmNoticeInfo+"\"},"
				+"{printMachineName:\""+printMachineName+"\"},"
		        +"{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

}
