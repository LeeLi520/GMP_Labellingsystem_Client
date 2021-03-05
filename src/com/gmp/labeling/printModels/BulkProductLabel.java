package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

public class BulkProductLabel extends Label {
	
	private String productName;
	private String itemCode;
	private String batch;
	private String quantity;
	private String cartonNo;
	private String useBy;
	private String temperature;
	

	public BulkProductLabel(String productName, String itemCode, String batch, String quantity,
			String cartonNo, String useBy, String temperature) {
		super();
		this.productName = productName;
		this.itemCode = itemCode;
		this.batch = batch;
		this.quantity = quantity;
		this.cartonNo = cartonNo;
		this.useBy = useBy;
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

	public String getUseBy() {
		return useBy;
	}

	public void setUseBy(String useBy) {
		this.useBy = useBy;
	}
	
	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
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
				+ "{labeltype:\"BulkShipperLabel\"},"
				+ "{productName:\""+productName+"\"},"
				+ "{itemCode:\""+itemCode+"\"},"
				+ "{batch:\""+batch+"\"},"
				+ "{quantity:\""+quantity+"\"},"
				+ "{cartonNo:\""+cartonNo+"\"},"
				+ "{useBy:\""+useBy+"\"},"
				+ "{temperature:\""+ temperature +"\"},"
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
    	 		+ "^FO200,20"
    	 		+ "^GB560,95,4^FS";		
		
	    if(productName.length()>42) {
		    String line_1;
		    String line_2;
		    int i = 42;
		    char temp = productName.charAt(i);
		    while(temp!=' ') {
		     i--;
		     if(i<0) {
		     	break;
		     }else {
		     temp = productName.charAt(i);
		     }
		    }
		    if(i == -1) {
		     line_1 = productName.substring(0,42);
		     line_2 = productName.substring(43,productName.length());
		    }else {
		     line_1 = productName.substring(0, i);
		     line_2 = productName.substring(i+1, productName.length());
		    }
		    
			int upperCaseCount = 0;
			int lowerCaseCount = 0;
			int numberCount = 0;
			int spaceCount = 0;
			
			for(int j = 0; j < line_2.length(); j++) {
				if(Character.isUpperCase(line_2.charAt(j))) {
					upperCaseCount++;
				}
				if(Character.isLowerCase(line_2.charAt(j))) {
					lowerCaseCount++;
				}
				
				if(Character.isDigit(line_2.charAt(j))) {
					numberCount++;
				}
				if(Character.isSpace(line_2.charAt(j))) {
					spaceCount++;
				}
			}
			
			double total = (upperCaseCount*upperWeight)+(lowerCaseCount*lowerWeight)+(numberCount*numberWeight)+(spaceCount*spaceWeight);
			int dots = (int)((1.0 - total)*648.0/2.0);
			
		    s += "^FO210,30,0"
	    	   + "^A@N,36,32,E:ARI002.TTF" 
	    	   + "^FD"+ line_1 +"^FS"
	    	   + "^FO"+(140 + dots)+",70,0"
	    	   + "^A@N,36,32,E:ARI002.TTF" 
	    	   + "^FD"+ line_2 +"^FS";
    }else {
			int upperCaseCount = 0;
			int lowerCaseCount = 0;
			int numberCount = 0;
			int spaceCount = 0;
		
			for(int j = 0; j < productName.length(); j++) {
				if(Character.isUpperCase(productName.charAt(j))) {
					upperCaseCount++;
				}
				if(Character.isLowerCase(productName.charAt(j))) {
					lowerCaseCount++;
				}
			
				if(Character.isDigit(productName.charAt(j))) {
					numberCount++;
				}
				if(Character.isSpace(productName.charAt(j))) {
					spaceCount++;
				}
			}
		
			double total = (upperCaseCount*upperWeight)+(lowerCaseCount*lowerWeight)+(numberCount*numberWeight)+(spaceCount*spaceWeight);
			int dots = (int)((1.0 - total)*648.0/2.0);
    	
		    s += "^FO"+(140 + dots)+",30,0"
			   + "^A@N,36,32,E:ARI002.TTF" 
			   + "^FD"+ productName +"^FS";
	}
		
    	   	s+= "^FO30,150,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDCode No:^FS";
		
		if(itemCode.length()==10) {
			String temp_1 = itemCode.substring(0, 4);
			String temp_2 = itemCode.substring(4, 10);
			s += "^FO180,138^BY3"
	    	  + "^BCN,68,N,N,Y"
	    	  + "^FD>6 "+ temp_1 +">9"+ temp_2 +"^FS"	        	
		      + "^FO355,215,0"
	    	  + "^A@N,30,28,E:TAH005.TTF" 
	    	  + "^FD"+ itemCode +"^FS";
		}else {			
			s += "^FO180,138^BY3"
			  + "^BCN,68,N,N,Y"
			  + "^FD"+ itemCode +"^FS"	        	
			  + "^FO355,215,0"
			  + "^A@N,30,28,E:TAH005.TTF" 
			  + "^FD"+ itemCode +"^FS";			
		}
			        	
	         s += "^FO30,260,0"
	            + "^A@N,36,25,E:TAH005.TTF"
    	 		+ "^FDBatch No:^FS"
    	 		+ "^FO180,248^BY3"
    	 		+ "^BCN,65,N,N,Y"
    	 		+ "^FD"+ batch +"^FS"
    	 		+ "^FO275,318,0"
    	 		+ "^A@N,30,28,E:TAH005.TTF" 
    	 		+ "^FD"+ batch +"^FS"
    	 		+ "^FO30,365,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FDContents:^FS";
	         
	 		DecimalFormat formatter_1 = new DecimalFormat("###,###,###");
	 		int contents = Integer.valueOf(quantity);
	 		
    	 	s	+="^FO175,365,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FD"+ formatter_1.format(contents) +"^FS"
    	 		+ "^FO320,365,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FDunits/carton^FS"
    	 		+ "^FO480,415,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FDCarton No:^FS";
    	 	
    	 	DecimalFormat formatter_2 = new DecimalFormat("0000");
    	 	int cart = Integer.valueOf(cartonNo);
    	 	
    	 	s	+="^FO640,415,0"
    	 		+ "^A@N,33,29,E:TAH005.TTF" 
    	 		+ "^FD"+ formatter_2.format(cart) +"^FS"
    	 		+ "^FO30,415,0"
    	 		+ "^A@N,36,25,E:TAH005.TTF" 
    	 		+ "^FDDOM:^FS"
	            + "^FO175,415,0"
	         	+ "^A@N,33,29,E:TAH005.TTF" 
	         	+ "^FD"+ useBy +"^FS";
	         
	         s  +="^FO30,455"
	    	 	+ "^GB760,10,5^FS";
    	 		
    	 		
        	 s  += "^FO50,510,0"           
        	    + "^A@N,27,27,E:ARI002.TTF"
        	    + "^CI27"    	 		
        	    + "^FDInstructions :^FS"
        	    
        	    + "^FO190,480,0"
        	    + "^A@N,22,22,E:ARI002.TTF"    	 		
        	    + "^FD* Inspect this bulk product as soon as delivered^FS"
        	    
        	    + "^FO190,505,0"        	    
        	    + "^A@N,22,22,E:ARI002.TTF"    	 		
        	    + "^FD* Store in a cool dry place, below "+ temperature +"°C^FS"
        	    
        	    + "^FO190,530,0"        	    
        	    + "^A@N,22,22,E:ARI002.TTF"    	 		
        	    + "^FD* Fill into packs within three (3) months^FS"    
        	    
        	    + "^FO190,555,0"       	    
        	    + "^A@N,22,22,E:ARI002.TTF"    	 		
        	    + "^FD* This bulk pack is for shipment purposes only^FS"    
        	    + "^XZ";
				
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
