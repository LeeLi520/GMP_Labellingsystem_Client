package com.gmp.labeling.models;

public class PrintObject {
	
	private String item_code;
	private String item_name;
	private String gin_number;
	private String item_quantity;
	private String no_container;
	private String received_date;
	private int printing_quantity;
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
	public String getGin_number() {
		return gin_number;
	}
	public void setGin_number(String gin_number) {
		this.gin_number = gin_number;
	}
	public String getItem_quantity() {
		return item_quantity;
	}
	public void setItem_quantity(String item_quantity) {
		this.item_quantity = item_quantity;
	}
	public String getNo_container() {
		return no_container;
	}
	public void setNo_container(String no_container) {
		this.no_container = no_container;
	}
	public String getReceived_date() {
		return received_date;
	}
	public void setReceived_date(String received_date) {
		this.received_date = received_date;
	}
	public int getPrinting_quantity() {
		return printing_quantity;
	}
	public void setPrinting_quantity(int printing_quantity) {
		this.printing_quantity = printing_quantity;
	}
	public PrintObject(String item_code, String item_name, String gin_number, String item_quantity, String no_container,
			String received_date, int printing_quantity) {
		super();
		this.item_code = item_code;
		this.item_name = item_name;
		this.gin_number = gin_number;
		this.item_quantity = item_quantity;
		this.no_container = no_container;
		this.received_date = received_date;
		this.printing_quantity = printing_quantity;
	}
	public PrintObject() {
		super();
		// TODO Auto-generated constructor stub
	}

}
