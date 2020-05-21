package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IdentifyDiscardedLabel extends Label{
	
	@Override
	public String printZPLFormat() {
		String s = "^XA" +
	             "^FO238,50"+ 
	             "^A0N,25,36"+ 
	             "^FDGMP Pharmaceuticals^FS" +
	             "^FO138,80"+ 
	             "^A0N,21,33"+ 
	             "^FDIdentification of Discarded Equipment^FS" +
	             "^FO98,120,0"+ 
	             "^A0N,20,30"+
	             "^FDEquipment Name:^FS" +
	             "^FO68,160,0"+ 
	             "^A0N,20,30"+
	             "^FDDepartment:^FS" +
	             "^FO428,160,0"+ 
	             "^A0N,20,30"+
	             "^FDAsset No:^FS" +
	             "^FO38,200,0"+ 
	             "^A0N,20,30"+
	             "^FDReason to keep:^FS" +
	             "^FO18,290,0"+ 
	             "^A0N,20,30"+
	             "^FDReason to Dispose:^FS" +
	             "^FO38,380,0"+ 
	             "^A0N,20,30"+
	             "^FDResponsible person(print):^FS" +
	             "^FO98,460,0"+ 
	             "^A0N,20,30"+
	             "^FDSignature:^FS" +
	             "^FO168,520,0"+ 
	             "^A0N,20,30"+
	             "^FDDate:^FS" +
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
		String log ="{{labeltype:\"IdentifyDiscardedLabel\"},"
		           +"{printMachineName:\""+printMachineName+"\"},"
                   +"{printMachineIp:\""+printMachineIP+"\"}"
                   +"}";
		return log;
	}	

}
