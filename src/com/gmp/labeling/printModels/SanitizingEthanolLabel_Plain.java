package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SanitizingEthanolLabel_Plain extends Label {

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
		String log ="{{labeltype:\"SanitizingEthanolLabel_Plain\"},"
		           +"{printMachineName:\""+printMachineName+"\"},"
                   +"{printMachineIp:\""+printMachineIP+"\"}"
                   +"}";
		return log;
	}

	@Override
	public String printZPLFormat() {
      String s ="^XA"
    		  +"^FO80,25" 
    		  +"^A0N,48,60" 
    		  +"^FDSANITIZING 70% ETHANOL^FS"
    		  +"^FO180,75" 
    		  +"^A0N,48,60" 
    		  +"^FDCODE: AUIP000006^FS"
    		  +"^FO280,125" 
    		  +"^A0N,48,60" 
    		  +"^FDSOFT GEL^FS"
    		  +"^FO40,180" 
    		  +"^A0N,36,46" 
    		  +"^FDArea:^FS"
    		  +"^FO40,225" 
    		  +"^A0N,36,46" 
    		  +"^FDBatch NO:^FS"
    		  +"^FO40,270" 
    		  +"^A0N,36,46" 
    		  +"^FDDOM:^FS"
    		  +"^FO40,315" 
    		  +"^A0N,36,46" 
    		  +"^FDEXP:^FS"
    		  +"^FO40,360" 
    		  +"^A0N,36,46" 
    		  +"^FDRefill Signed & date:^FS"
    		  +"^FO40,405" 
    		  +"^A0N,36,46" 
    		  +"^FDRefill Signed & date:^FS"
    		  +"^FO40,450" 
    		  +"^A0N,36,46" 
    		  +"^FDRefill Signed & date:^FS"
    		  +"^FO40,495" 
    		  +"^A0N,36,46" 
    		  +"^FDRefill Signed & date:^FS"
    		  +"^FO40,540" 
    		  +"^A0N,36,46" 
    		  +"^FDRefill Signed & date:^FS"
    		  + "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
