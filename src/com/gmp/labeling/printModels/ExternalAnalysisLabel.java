package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ExternalAnalysisLabel extends Label{
		

	@Override
	public String printZPLFormat() {
		String s = "^XA" +
                "^FO180,20^A0N,32,48^FDGMP Pharmaceuticals^FS" + 
                "^FO150,55^A0N,28,40^FDEXTERNAL ANALYSIS SAMPLE^FS" + 
                "^FO10,100,0"+
                "^A0N,25,32"+
                "^FDEXAM NO:^FS"+
                "^FO160,125,0"+
			    "^GB300,0,3^FS"+
			    "^FO10,145,0"+
                "^A0N,25,32"+
                "^FDItem Code:^FS"+
                "^FO160,170,0"+
			    "^GB300,0,3^FS"+
			    "^FO10,195,0"+
                "^A0N,25,32"+
                "^FDItem Name:^FS"+
                "^FO160,215,0"+
			    "^GB450,0,3^FS"+
			    "^FO160,260,0"+
			    "^GB450,0,3^FS"+
			    "^FO10,290,0"+
                "^A0N,24,30"+
                "^FDGIN No.^FS"+
                "^FO120,310,0"+
			    "^GB100,0,3^FS"+
			    "^FO230,290,0"+
                "^A0N,24,30"+
                "^FDOr B/N:^FS"+
                "^FO330,310,0"+
			    "^GB200,0,3^FS"+
			    "^FO530,290,0"+
                "^A0N,24,30"+
                "^FDSample No:^FS"+
                "^FO680,310,0"+
			    "^GB100,0,3^FS"+
			    "^FO10,330,0"+
                "^A0N,24,30"+
                "^FDSigned:^FS"+
                "^FO130,360,0"+
			    "^GB250,0,3^FS"+
			    "^FO390,330,0"+
                "^A0N,24,30"+
                "^FDDate:^FS"+
                "^FO470,360,0"+
			    "^GB200,0,3^FS"+
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
		String log = "{{labeltype:\"ExternalAnalysisLabel\"},"
		            +"{printMachineName:\""+printMachineName+"\"},"
		            +"{printMachineIp:\""+printMachineIP+"\"}"
		            +"}";
		return log;
	}

}
