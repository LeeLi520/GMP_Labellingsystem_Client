package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CustomerSampleLabelT extends Label {
	
	private String sampleName;
	private String customer;
	
	public CustomerSampleLabelT(String sampleName, String customer) {
		super();
		this.sampleName = sampleName;
		this.customer = customer;
	}

	public CustomerSampleLabelT() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Override
	public String printZPLFormat() {
		String s = "^XA" +
                "^FO238,50^A0N,25,36^FDGMP Pharmaceuticals^FS" + 
                "^FO38,120,0"+
                "^AAN,38,15"+
                "^FDSample:^FS"+
                "^FO255,120,0"+
                "^AAN,38,15"+
                "^FD"+ sampleName +"^FS"+
                "^FO250,150,0"+
			    "^GB400,0,3^FS"+
                "^FO38,220,0"+
                "^AAN,38,15"+
                "^FDCustomer:^FS"+
                "^FO255,220,0"+
                "^AAN,38,15"+
                "^FD"+ customer +"^FS"+
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
		String log ="{{labeltype:\"CustomerSampleLabelT\"},"
				+ "{samplename:\""+sampleName+"\"},"
				+ "{customer:\""+customer+"\"}"
				+ "{printMachineName:\""+printMachineName+"\"},"
				+ "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}	

}
