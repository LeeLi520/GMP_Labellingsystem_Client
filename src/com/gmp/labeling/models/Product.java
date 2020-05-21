package com.gmp.labeling.models;

public class Product {
	
	private String CompanyName;
	private String itemCode;
	private String productName;
	private String itemQuantity;
	private String extraInfo;
		
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(String companyName, String itemCode, String productName, String itemQuantity, String extraInfo) {
		super();
		CompanyName = companyName;
		this.itemCode = itemCode;
		this.productName = productName;
		this.itemQuantity = itemQuantity;
	}
		
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
}
