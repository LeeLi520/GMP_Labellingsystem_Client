package com.gmp.labeling.models;

public class User {
	
	private String uuid;
	private String username;
	private String password;
	private String fullname;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public User(String uuid, String username, String password, String fullname) {
		super();
		this.uuid = uuid;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
