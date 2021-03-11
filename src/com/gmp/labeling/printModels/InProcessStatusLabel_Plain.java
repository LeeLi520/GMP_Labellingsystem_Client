package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InProcessStatusLabel_Plain extends Label {

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
		String log ="{{labeltype:\"InProcessStatusLabel_Plain\"},"
		           +"{printMachineName:\""+printMachineName+"\"},"
                   +"{printMachineIp:\""+printMachineIP+"\"}"
                   +"}";
		return log;
	}

	@Override
	public String printZPLFormat() {
      String s =  "^XA"
        +"^FO150,30" 
        +"^A0N,55,70" 
        +"^FDIn-Process Status^FS"
        +"^FO30,80" 
        +"^A0N,30,36" 
        +"^FDProduct Name:^FS"
 		+"^FO30,120"
 		+"^GB748,4,2^FS"
        +"^FO30,140" 
        +"^A0N,30,36" 
        +"^FDCode:^FS"
        +"^FO30,190" 
        +"^A0N,30,36" 
        +"^FDQty (kg):^FS"
        +"^FO30,240" 
        +"^A0N,30,36" 
        +"^FDDOM:^FS"
        +"^FO30,290" 
        +"^A0N,30,36" 
        +"^FDDate last full clean:^FS"
        +"^FO30,340" 
        +"^A0N,30,36" 
        +"^FDSupply Operator:^FS"
        +"^FO410,140" 
        +"^A0N,30,36" 
        +"^FDBatch:^FS"
        +"^FO410,190" 
        +"^A0N,30,36" 
        +"^FDQty (No.):^FS"
        +"^FO410,240" 
        +"^A0N,30,36" 
        +"^FDDischarge Time:^FS"
        +"^FO410,290" 
        +"^A0N,30,36" 
        +"^FDMobile tank No.:^FS"
        +"^FO410,340" 
        +"^A0N,30,36" 
        +"^FDDate:^FS"
 		+"^FO30,390"
 		+"^GB748,4,2^FS"
 		+"^FO30,410" 
        +"^A0N,30,36" 
        +"^FDCode required:^FS"
        +"^FO30,460" 
        +"^A0N,30,36" 
        +"^FDM/C Operator:^FS"
        +"^FO410,410" 
        +"^A0N,30,36" 
        +"^FDTank No.:^FS"
        +"^FO410,460" 
        +"^A0N,30,36" 
        +"^FDDate:^FS"
        +"^FO340,520" 
        +"^A0N,55,70" 
        +"^FDHOLD^FS"
        + "^XZ" ;
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
