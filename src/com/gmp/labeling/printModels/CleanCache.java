package com.gmp.labeling.printModels;

public class CleanCache extends Label{

	@Override
	public String toString() {
		
		return null;

	}

	@Override
	public String printZPLFormat() {
		String s = "^XA^MCY^XZ";
		return s;
	}

	@Override
	public String printEPLFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
