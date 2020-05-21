package com.gmp.labeling.models;

public class FPLabelObject {
	private String CompanyName;
	private String productName;
	private String itemCode;
	private String batch;
	private String quantity;
	private String startFrom;
	private String labelPerCarton;
	private String cartonQuantity;
	private String useBy;
	private String packedBy;
	private String temperature;
	private String btmNoticeInfo;
		
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getStartFrom() {
		return startFrom;
	}
	public void setStartFrom(String startFrom) {
		this.startFrom = startFrom;
	}
	public String getLabelPerCarton() {
		return labelPerCarton;
	}
	public void setLabelPerCarton(String labelPerCarton) {
		this.labelPerCarton = labelPerCarton;
	}
	public String getCartonQuantity() {
		return cartonQuantity;
	}
	public void setCartonQuantity(String cartonQuantity) {
		this.cartonQuantity = cartonQuantity;
	}
	public String getUseBy() {
		return useBy;
	}
	public void setUseBy(String useBy) {
		this.useBy = useBy;
	}
	public String getPackedBy() {
		return packedBy;
	}
	public void setPackedBy(String packedBy) {
		this.packedBy = packedBy;
	}
	public String getBtmNoticeInfo() {
		return btmNoticeInfo;
	}
	public void setBtmNoticeInfo(String btmNoticeInfo) {
		this.btmNoticeInfo = btmNoticeInfo;
	}
	
	public FPLabelObject(String CompanyName, String productName, String itemCode, String batch, String quantity, String startFrom,
			String labelPerCarton, String cartonQuantity, String useBy, String packedBy, String temperature, String btmNoticeInfo) {
		super();
		this.CompanyName = CompanyName;
		this.productName = productName;
		this.itemCode = itemCode;
		this.batch = batch;
		this.quantity = quantity;
		this.startFrom = startFrom;
		this.labelPerCarton = labelPerCarton;
		this.cartonQuantity = cartonQuantity;
		this.useBy = useBy;
		this.packedBy = packedBy;
		this.temperature = temperature;
		this.btmNoticeInfo = btmNoticeInfo;
	}

}
