package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CustomerSampleLabel extends Label{
	
	@Override
	public String printZPLFormat() {
		String s = "^XA" +
                "^FO238,50^A0N,25,36^FDGMP Pharmaceuticals^FS" + 
                "^FO38,120,0"+
                "^AAN,38,15"+
                "^FDSample:^FS"+
                "^FO250,150,0"+
			    "^GB400,0,3^FS"+
                "^FO38,220,0"+
                "^AAN,38,15"+
                "^FDCustomer:^FS"+
                "^FO250,250,0"+
			    "^GB400,0,3^FS"+
                "^FO225,320,0"+
                "^AAN,38,15"+
                "^FDCustomer Sample Only^FS"+  
                "^XZ";
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
		String log ="{{labeltype:\"CustomerSampleLabel\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
				+ "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}	

}
