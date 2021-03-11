package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SoftGelMicroSampleLabel_Plain extends Label {

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
		String log ="{{labeltype:\"SoftGelMicroSampleLabel\"},"
		           +"{printMachineName:\""+printMachineName+"\"},"
                   +"{printMachineIp:\""+printMachineIP+"\"}"
                   +"}";
		return log;
	}

	@Override
	public String printZPLFormat() {
      String s = "^XA"       		 
    		  +"^FO188,30" 
    		  +"^A0N,35,50" 
    		  +"^FDGMP Pharmaceuticals^FS"
    		  +"^FO170,85" 
    		  +"^A0N,32,45" 
    		  +"^FDSOFT GEL MICRO SAMPLE^FS"
    		  +"^FO38,160" 
    		  +"^A0N,30,40"  
    		  +"^FDProduct:^FS"
    		  +"^FO38,230" 
    		  +"^A0N,30,40" 
    		  +"^FDCode:AUBG00^FS"
    		  +"^FO448,230" 
    		  +"^A0N,30,40" 
    		  +"^FDBatch No:^FS"
    		  +"^FO38,300" 
    		  +"^A0N,30,40" 
    		  +"^FDDate of Sample:^FS"
    		  +"^FO448,300" 
    		  +"^A0N,30,40"  
    		  +"^FDSigned:^FS"
    		  + "^XZ" ;   
      
      return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
