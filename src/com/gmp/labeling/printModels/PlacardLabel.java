package com.gmp.labeling.printModels;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PlacardLabel extends Label {
	
	private String line_1;
	private String line_2;
	
	public PlacardLabel(String line_1, String line_2) {
		super();
		this.line_1 = line_1;
		this.line_2 = line_2;
	}

	public String getLine_1() {
		return line_1;
	}

	public void setLine_1(String line_1) {
		this.line_1 = line_1;
	}

	public String getLine_2() {
		return line_2;
	}

	public void setLine_2(String line_2) {
		this.line_2 = line_2;
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
				+ "{labeltype:\"PlacardLabel\"},"
				+ "{line1:\""+ line_1 +"\"},"
				+ "{line2:\""+line_2+"\"},"
				+ "{printMachineName:\""+printMachineName+"\"},"
		        + "{printMachineIp:\""+printMachineIP+"\"}"
				+ "}";
		return log;
	}

	@Override
	public String printZPLFormat() {
	 
   	 String s ="^XA"+
   	       	   "^FO"+locationDot(line_1)+",120,0"+
   	           "^A0N,100,100"+
   	           "^FD"+ line_1 +"^FS"+
   	           "^FO"+locationDot(line_2)+",420,0"+
	           "^A0N,100,100"+
	           "^FD"+ line_2 +"^FS"+
   	           "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public int locationDot(String line) {
		int upperCaseCount = 0;
		int lowerCaseCount = 0;
		int numberCount = 0;
		int spaceCount = 0;
		for(int i = 0; i < line.length(); i++) {
			if(Character.isUpperCase(line.charAt(i))) {
				upperCaseCount++;
			}
			if(Character.isLowerCase(line.charAt(i))) {
				lowerCaseCount++;
			}
			
			if(Character.isDigit(line.charAt(i))) {
				numberCount++;
			}
			if(Character.isSpace(line.charAt(i))) {
				spaceCount++;
			}
		}
		double upperWeight = 1.0/14.3;
		double lowerWeight = 1.0/18.0;
		double numberWeight = 1.0/18.0;
		double spaceWeight = 1.0/22.0;
		double total = (upperCaseCount*upperWeight)+(lowerCaseCount*lowerWeight)+(numberCount*numberWeight)+(spaceCount*spaceWeight);
		int dots = (int)((1.05 - total)*822.0/2.0) + 20;
		return dots;
	}

}
