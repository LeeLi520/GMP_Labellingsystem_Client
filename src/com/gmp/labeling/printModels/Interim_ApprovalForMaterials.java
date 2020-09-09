package com.gmp.labeling.printModels;

public class Interim_ApprovalForMaterials extends Label {
	
	private String itemCode;
	private String batch;
	private String signature;
	private String date;
	
	public Interim_ApprovalForMaterials(String itemCode, String batch, String signature, String date) {
		super();
		this.itemCode = itemCode;
		this.batch = batch;
		this.signature = signature;
		this.date = date;
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



	public String getSignature() {
		return signature;
	}



	public void setSignature(String signature) {
		this.signature = signature;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printZPLFormat() {
        String s = "^XA" +
	             "^FO200,20"+ 
	             "^A0N,35,50"+ 
	             "^FDGMP Pharmaceuticals^FS"+
	             "^FO218,65"+ 
	             "^A0N,35,50"+ 
	             "^FDINTERIM APPROVAL^FS"+
	             "^FO40,105"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDThis material has been conditionally released^FS"+
	             "^FO50,150"+ 
	             "^A0N,38,50"+
	             "^FDAPPROVAL FROM QA IS REQUIRED^FS"+
	             "^FO255,195"+ 
	             "^A@N,32,38,E:ARI002.TTF"+ 
	             "^FDBEFORE EACH USE^FS"+
	             "^FO40,240"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDCode: "+ itemCode +"^FS"+
	             "^FO410,240"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDBatch No: "+ batch +"^FS"+
	             "^FO40,310"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDSign: "+ signature +"^FS"+
	             "^FO410,310"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDDate: "+ date +"^FS"+
	             "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
