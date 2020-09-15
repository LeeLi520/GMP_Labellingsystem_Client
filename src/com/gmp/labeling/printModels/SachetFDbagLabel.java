package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SachetFDbagLabel extends Label {

	public SachetFDbagLabel() {
		super();
		// TODO Auto-generated constructor stub
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
				+ "{labeltype:\"SachetFDbagLabel\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
		String s =  "^XA"
	            +"^FO120,60" 
	            +"^A0N,80,80" 
	            +"^FDW/O:^FS"
	            +"^FO120,200" 
	            +"^A0N,80,80"  
	            +"^FDWeight Check^FS"
	            +"^FO120,320" 
	            +"^A0N,80,80"  
	            +"^FDOnline Sorting^FS"
	            +"^FO120,460" 
	            +"^AEN,32,15"  
	            +"^FDShift: ^FS"
	            +"^FO300,460" 
	            +"^AEN,32,15"  
	            +"^FDM/S  N/S^FS"
	            +"^FO540,460" 
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
