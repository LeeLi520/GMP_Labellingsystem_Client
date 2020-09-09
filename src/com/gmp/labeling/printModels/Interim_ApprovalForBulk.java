package com.gmp.labeling.printModels;

public class Interim_ApprovalForBulk extends Label {
	
	private String itemCode;
	private String batch;
	private String DOM;	

	public Interim_ApprovalForBulk(String itemCode, String batch, String dOM) {
		super();
		this.itemCode = itemCode;
		this.batch = batch;
		DOM = dOM;
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



	public String getDOM() {
		return DOM;
	}



	public void setDOM(String dOM) {
		DOM = dOM;
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
	             "^FDThis bulk product has been conditionally released^FS"+
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
	             "^FDDOM: "+ DOM +"^FS"+
	             "^FO410,310"+ 
	             "^A@N,35,42,E:ARI002.TTF"+ 
	             "^FDSign/Date:^FS"+
	             "^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
