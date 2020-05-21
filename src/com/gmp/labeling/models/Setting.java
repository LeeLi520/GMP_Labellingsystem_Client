package com.gmp.labeling.models;

public class Setting {
	
	private boolean localPrintingMode;
	private String ipAddress;
	private int port;
	private String[] template;
	private String version;	
	private User logined_user;
			
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isLocalPrintingMode() {
		return localPrintingMode;
	}
	public void setLocalPrintingMode(boolean localPrintingMode) {
		this.localPrintingMode = localPrintingMode;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public Setting(boolean localPrintingMode, String ipAddress, int port) {
		super();
		this.localPrintingMode = localPrintingMode;
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public Setting() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User getLogined_user() {
		return logined_user;
	}
	public void setLogined_user(User logined_user) {
		this.logined_user = logined_user;
	}
	public String[] getTemplate() {
		return template;
	}
	public void setTemplate(String[] template) {
		this.template = template;
	}
		
}
