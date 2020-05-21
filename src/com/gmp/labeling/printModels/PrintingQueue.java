package com.gmp.labeling.printModels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

import com.gmp.labeling.connections.RestConnection;
import com.gmp.labeling.models.User;

public class PrintingQueue {
	
	private List<Label> list;
	private String driver;
	User logined_user;
	RestConnection connection;
	
	public PrintingQueue(User logined_user, RestConnection connection){
		
		this.list = new ArrayList<>();
		this.driver = "ZPL";
		this.logined_user = logined_user;
		this.connection = connection;
	}

	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}

	public List<Label> getList() {
		return list;
	}
	
	public void addLabelToQueue(Label label) {
		list.add(label);
	}
	
	public boolean listIsEmpty() {
		if(list.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void printJobs() {
		int printBuffercontrol = 0;
		switch(this.driver) {
		    case "ZPL":	
		    	Label cleanCache = new CleanCache();
		    	cleanCache.printLocal(cleanCache.printZPLFormat());
			     for(Label label: list) {
			    	 if(printBuffercontrol >50) {
			    		 label.printLocal(label.printZPLFormat()+"^XA^MCY^XZ");
				    	 connection.sendLog(logined_user, label);
				    	 printBuffercontrol = 0;
			    	 }else {
			    		 label.printLocal(label.printZPLFormat());
				    	 connection.sendLog(logined_user, label);
				    	 printBuffercontrol++;
			    	 }
			    	 			    	 
			     }
			     this.list = new ArrayList<>();
			     break;
			     
		    case "EPL":
		    	 for(Label label: list) {
		    		 label.printLocal(label.printEPLFormat());
		    		 connection.sendLog(logined_user, label);
		    	 }
		    	 this.list = new ArrayList<>();
		    	 break;		
		}
	}
	
	public void printJobs(String ipAddress, int port) {
		int printBuffercontrol = 0;
		switch(this.driver) {
	    case "ZPL":
	    	Label cleanCache = new CleanCache();
	    	cleanCache.printNetwork(ipAddress, port, cleanCache.printZPLFormat());
		     for(Label label: list) {
		    	 if(printBuffercontrol >50) {
		    		 label.printNetwork(ipAddress, port, label.printZPLFormat()+ "^XA^MCY^XZ");
		    		 System.out.println("cleaning");
			    	 connection.sendLog(logined_user, label, ipAddress, String.valueOf(port));
			    	 printBuffercontrol = 0;		    	 
		    	 }else {
		    		 label.printNetwork(ipAddress, port, label.printZPLFormat());
			    	 connection.sendLog(logined_user, label, ipAddress, String.valueOf(port));
			    	 printBuffercontrol++;
		    	 }		    	 
		     }
		     this.list = new ArrayList<>();
		     break;
		     
	    case "EPL":  //EPL dont have buffer control
	    	 for(Label label: list) {
	    		 label.printNetwork(ipAddress, port, label.printEPLFormat());
	    		 connection.sendLog(logined_user, label, ipAddress, String.valueOf(port));
	    	 }
	    	 this.list = new ArrayList<>();
	    	 break;		
	    }
	}
	
	public void CTM_printJobs(JLabel CustomLP_printingCountDisplay_value) {
		switch(this.driver) {
		    case "ZPL":
			     for(Label label: list) {
			    	 label.printLocal(label.printZPLFormat());
			     }
			     this.list = new ArrayList<>();
			     CustomLP_printingCountDisplay_value.setText("0");
			     break;
			     
		    case "EPL":
		    	 for(Label label: list) {
		    		 label.printLocal(label.printEPLFormat());
		    	 }
		    	 try {
					 Thread.sleep(3000);
				     } catch (InterruptedException e) {
					     // TODO Auto-generated catch block
					  e.printStackTrace();
				     }
				 this.list = new ArrayList<>();
				 CustomLP_printingCountDisplay_value.setText("0");
		    	 break;		
		}
	}
	
	public void CTM_printJobs(String ipAddress, int port, JLabel CustomLP_printingCountDisplay_value) {
		switch(this.driver) {
	    case "ZPL":
		     for(Label label: list) {
		    	 label.printNetwork(ipAddress, port, label.printZPLFormat());
		     }
		     try {
				 Thread.sleep(3000);
			     } catch (InterruptedException e) {
				     // TODO Auto-generated catch block
				  e.printStackTrace();
			     }
			 this.list = new ArrayList<>();
			 CustomLP_printingCountDisplay_value.setText("0");
		     break;
		     
	    case "EPL":
	    	 for(Label label: list) {
	    		 label.printNetwork(ipAddress, port, label.printEPLFormat());
	    	 }
	    	 try {
				 Thread.sleep(3000);
			     } catch (InterruptedException e) {
				     // TODO Auto-generated catch block
				  e.printStackTrace();
			     }
			 this.list = new ArrayList<>();
			 CustomLP_printingCountDisplay_value.setText("0");
	    	 break;		
	    }
	}
	
	public void remoreAllObject() {
		list.clear();
	}

}
