package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GelatineUsageRecordLabel_Plain extends Label {

	public GelatineUsageRecordLabel_Plain() {
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
		String log ="{{labeltype:\"GelatineUsageRecordLabel_Plain\"},"
		           +"{printMachineName:\""+printMachineName+"\"},"
                   +"{printMachineIp:\""+printMachineIP+"\"}"
                   +"}";
		return log;
	}

	@Override
	public String printZPLFormat() {
      String s ="^XA"
  			+"^FO80,30" 
            +"^A0N,48,60" 
            +"^FDGELATINE USAGE RECORD^FS"
  			+"^FO40,100" 
            +"^A0N,48,50" 
            +"^FDIP:^FS"
 	 		+"^FO100,140"
 	 		+"^GB80,3,1.5^FS"
  			+"^FO200,100" 
            +"^A0N,48,50" 
            +"^FDW/O:^FS"
 	 		+"^FO300,140"
 	 		+"^GB100,3,1.5^FS"
  			+"^FO420,100" 
            +"^A0N,48,50" 
            +"^FDDATE:^FS"
  			+"^FO150,170" 
            +"^A0N,48,50" 
            +"^FDTOTAL QTY:^FS"
 	 		+"^FO400,210"
 	 		+"^GB100,3,1.5^FS"
 	 		+"^FO20,235"
 	 		+"^GB768,4,2^FS"
  			+"^FO40,260" 
            +"^A0N,48,50" 
            +"^FDMedicine^FS"
            +"^FO40,320" 
            +"^A0N,48,50" 
            +"^FDW/O:^FS"
 	 		+"^FO150,360"
 	 		+"^GB100,3,1.5^FS"
  			+"^FO260,320" 
            +"^A0N,48,50" 
            +"^FDQty:^FS"
 	 		+"^FO350,360"
 	 		+"^GB80,3,1.5^FS"
  			+"^FO440,320" 
            +"^A0N,48,50" 
            +"^FDM/C:^FS"
 	 		+"^FO540,360"
 	 		+"^GB50,3,1.5^FS"
  			+"^FO580,320" 
            +"^A0N,48,50" 
            +"^FDTIME:^FS"
 	 		+"^FO700,360"
 	 		+"^GB90,3,1.5^FS"
 	 		+"^FO40,380" 
            +"^A0N,48,50" 
            +"^FDW/O:^FS"
 	 		+"^FO150,420"
 	 		+"^GB100,3,1.5^FS"
  			+"^FO260,380" 
            +"^A0N,48,50" 
            +"^FDQty:^FS"
 	 		+"^FO350,420"
 	 		+"^GB80,3,1.5^FS"
  			+"^FO440,380" 
            +"^A0N,48,50" 
            +"^FDM/C:^FS"
 	 		+"^FO540,420"
 	 		+"^GB50,3,1.5^FS"
  			+"^FO580,380" 
            +"^A0N,48,50" 
            +"^FDTIME:^FS"
 	 		+"^FO700,420"
 	 		+"^GB90,3,1.5^FS"
 	 		+"^FO40,440" 
            +"^A0N,48,50" 
            +"^FDW/O:^FS"
 	 		+"^FO150,480"
 	 		+"^GB100,3,1.5^FS"
  			+"^FO260,440" 
            +"^A0N,48,50" 
            +"^FDQty:^FS"
 	 		+"^FO350,480"
 	 		+"^GB80,3,1.5^FS"
  			+"^FO440,440" 
            +"^A0N,48,50" 
            +"^FDM/C:^FS"
 	 		+"^FO540,480"
 	 		+"^GB50,3,1.5^FS"
  			+"^FO580,440" 
            +"^A0N,48,50" 
            +"^FDTIME:^FS"
 	 		+"^FO700,480"
 	 		+"^GB90,3,1.5^FS"
 	 		+"^FO40,500" 
            +"^A0N,48,50" 
            +"^FDW/O:^FS"
 	 		+"^FO150,540"
 	 		+"^GB100,3,1.5^FS"
  			+"^FO260,500" 
            +"^A0N,48,50" 
            +"^FDQty:^FS"
 	 		+"^FO350,540"
 	 		+"^GB80,3,1.5^FS"
  			+"^FO440,500" 
            +"^A0N,48,50" 
            +"^FDM/C:^FS"
 	 		+"^FO540,540"
 	 		+"^GB50,3,1.5^FS"
  			+"^FO580,500" 
            +"^A0N,48,50" 
            +"^FDTIME:^FS"
 	 		+"^FO700,540"
 	 		+"^GB90,3,1.5^FS"
      		+ "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
