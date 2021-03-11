package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ProductionInProcessLabel_Plain extends Label {

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
		String log ="{{labeltype:\"ProductionInProcessLabel_Plain\"},"
		           +"{printMachineName:\""+printMachineName+"\"},"
                   +"{printMachineIp:\""+printMachineIP+"\"}"
                   +"}";
		return log;
	}

	@Override
	public String printZPLFormat() {
      String s = "^XA"       		 
			 +"^FO188,30" 
             +"^A0N,30,45" 
             +"^FDGMP Pharmaceuticals^FS"
             +"^FO218,80" 
             +"^A0N,38,60" 
             +"^FDIN-PROCESS^FS"
             +"^FO88,130" 
             +"^A0N,38,60"  
             +"^FDPRODUCTION APPROVAL^FS"
             +"^FO38,210" 
             +"^AEN,32,15"  
             +"^FDCode:^FS"
             +"^FO448,210" 
             +"^AEN,32,15"  
             +"^FDBatch No:^FS"
             +"^FO38,290" 
             +"^AEN,32,15"  
             +"^FDSign:^FS"
             +"^FO448,290" 
             +"^AEN,32,15"  
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
