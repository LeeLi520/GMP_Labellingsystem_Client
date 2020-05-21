package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SamplingLabel extends Label{

	private String trialNumber;
	private String sampleName;
	
	
	public SamplingLabel(String trialNumber, String sampleName) {
		super();
		this.trialNumber = trialNumber;
		this.sampleName = sampleName;
	}

	public String getTrialNumber() {
		return trialNumber;
	}

	public void setTrialNumber(String trialNumber) {
		this.trialNumber = trialNumber;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	@Override
	public String printZPLFormat() {
		String s =  "^XA" +
                "^FO238,50^A0N,25,36^FDGMP Pharmaceuticals^FS" + 
                "^FO38,120,0"+
                "^AAN,38,15"+
                "^FDTrial:^FS"+
                "^FO280,120,0"+
                "^AAN,38,15"+
                "^FD# "+ trialNumber +"^FS"+
                "^FO38,200,0"+
                "^AAN,38,15"+
                "^FDSample:^FS";
		if(sampleName.length()>34) {
			String line_1;
	 		String line_2;
	 		int i = 34;
	 		char temp = sampleName.charAt(i);
	 		while(temp!=' ') {
	 			i--;
	 			if(i<0) {
	 				break;
	 			}else {
	 				temp = sampleName.charAt(i);
	 			}	 			
	 		}
	 		if(i==-1) {
	 			line_1 = sampleName.substring(0,34);
	 			line_2 = sampleName.substring(35, sampleName.length());
	 		}else {
	 			line_1 = sampleName.substring(0, i);
		 		line_2 = sampleName.substring(i+1, sampleName.length());
	 		}	 		
	 		s +="^FO190,200,0"+
			    "^AAN,38,15"+
			    "^FD"+line_1+"^FS"+
			    "^FO190,235,0"+
			    "^AAN,38,15"+
			    "^FD"+line_2+"^FS";			
		}else {
			 s +="^FO190,200,0"+
		         "^AAN,38,15"+
		         "^FD"+sampleName+"^FS";
		}
           
            s +="^FO225,320,0"+
                "^AAN,38,15"+
                "^FDSample Use Only^FS"+  
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
		String log = "{"
				+ "{labeltype:\"SamplingLabel\"},"				
				+ "{trialNumber:\""+trialNumber+"\"},"
				+ "{sampleName:\""+sampleName+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

}
