package com.gmp.labeling.models;

public class Material {
	
	private String item_code;
	private String item_name;
	private String item_unit;
	
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	public Material(String item_code, String item_name, String item_unit) {
		super();
		this.item_code = item_code;
		this.item_name = item_name;
		this.item_unit = item_unit;
	}
	public Material() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
