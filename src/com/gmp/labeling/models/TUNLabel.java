package com.gmp.labeling.models;

public class TUNLabel {
	
	String labelType;
	String itemCode;
	String productName;
	String pcCode;
	String quantity;
	String barcode;
	String grossWeight;
	
	
	public TUNLabel(String labelType, String itemCode, String productName, String pcCode, String quantity,
			String barcode, String grossWeight) {
		super();
		this.labelType = labelType;
		this.itemCode = itemCode;
		this.productName = productName;
		this.pcCode = pcCode;
		this.quantity = quantity;
		this.barcode = barcode;
		this.grossWeight = grossWeight;
	}
		
	public TUNLabel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getLabelType() {
		return labelType;
	}
	public void setLabelType(String labelType) {
		this.labelType = labelType;
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
	public String getPcCode() {
		return pcCode;
	}
	public void setPcCode(String pcCode) {
		this.pcCode = pcCode;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	
	

}
