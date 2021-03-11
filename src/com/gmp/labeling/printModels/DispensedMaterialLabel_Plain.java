package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DispensedMaterialLabel_Plain extends Label {

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
		String log ="{{labeltype:\"DispensedMaterialLabel_Plain\"},"
		           +"{printMachineName:\""+printMachineName+"\"},"
                   +"{printMachineIp:\""+printMachineIP+"\"}"
                   +"}";
		return log;
	}

	@Override
	public String printZPLFormat() {
      String s = "^XA"       		 
    		  	+"^FO168,20" 
    		  	+"^A0N,36,55" 
    		  	+"^FDGMP Pharmaceuticals^FS"
    		  	+"^FO198,60" 
    		  	+"^A0N,28,42" 
    		  	+"^FDDISPENSED MATERIAL^FS"
    		  	+"^FO38,100" 
    		  	+"^A0N,32,35"  
    		  	+"^FDMaterial:^FS"
    		  	+"^FO38,140" 
    		  	+"^A0N,32,35"  
    		  	+"^FDCode No: AURM^FS"
    		  	+"^FO480,140" 
    		  	+"^A0N,32,35"  
    		  	+"^FDGin:^FS"
    		  	+"^FO38,180" 
    		  	+"^A0N,32,35"  
    		  	+"^FDQty (KG):^FS"
    		  	+"^FO180,212"
    		  	+"^GB548,4,2^FS"
    		  	+"^FO38,235" 
    		  	+"^A0N,32,35"  
    		  	+"^FDProduct:^FS"
    		  	+"^FO38,275" 
    		  	+"^A0N,32,35"  
    		  	+"^FDBatch:^FS"
    		  	+"^FO480,275" 
    		  	+"^A0N,32,35"  
    		  	+"^FDExpire Date:^FS"
    		  	+"^FO38,335" 
    		  	+"^A0N,32,35"  
    		  	+"^FDChecked By:^FS"
    		  	+"^FO480,335" 
    		  	+"^A0N,32,35"  
    		  	+"^FDDate:^FS"
    		  	+ "^XZ" ;
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
