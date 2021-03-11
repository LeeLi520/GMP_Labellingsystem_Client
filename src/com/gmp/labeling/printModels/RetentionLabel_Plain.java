package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RetentionLabel_Plain extends Label {

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
		String log ="{{labeltype:\"RetentionLabel_Plain\"},"
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
				+"^FO268,70" 
				+"^A0N,30,40" 
				+"^FDRETENTION SAMPLE^FS"
				+"^FO58,120" 
				+"^A0N,25,30" 
				+"^FDPowder^FS"
				+"^FO178,110"
				+"^GB80,32,4^FS"
				+"^FO298,120" 
				+"^A0N,25,30" 
				+"^FDTABLET^FS"
				+"^FO418,110"
				+"^GB80,32,4^FS"
				+"^FO538,120" 
				+"^A0N,25,30" 
				+"^FDCapsule^FS"
				+"^FO658,110"
				+"^GB80,32,4^FS"
				+"^FO38,160" 
				+"^A0N,30,40"  
				+"^FDProduct:^FS"
				+"^FO38,230" 
				+"^A0N,30,40" 
				+"^FDCode:^FS"
				+"^FO448,230" 
				+"^A0N,30,40" 
				+"^FDBatch No:^FS"
				+"^FO38,300" 
				+"^A0N,30,40" 
				+"^FDD.O.M:^FS"
				+"^FO448,300" 
				+"^A0N,30,40"  
				+"^FDSigned/date:^FS"
				+ "^XZ" ;
		
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
