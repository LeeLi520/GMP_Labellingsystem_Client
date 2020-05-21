package com.gmp.labeling.connections;

public class DateFormatModifier {
	
	public static String changeDate(String source, String format) {
		char[] temp = source.toCharArray();
		String day = String.valueOf(temp[0])+String.valueOf(temp[1]);
		String month = String.valueOf(temp[3]) + String.valueOf(temp[4]);
		String year = String.valueOf(temp[6])+ String.valueOf(temp[7])+ String.valueOf(temp[8])+ String.valueOf(temp[9]);
		
		String result;
		char[] temp_format = format.toCharArray();
		if(temp_format.length==5) {
			String firstValue = String.valueOf(temp_format[0]).replaceAll("([a-z])", "$1").toUpperCase();
			String secondValue = String.valueOf(temp_format[2]).replaceAll("([a-z])", "$1").toUpperCase();
			String thirdValue = String.valueOf(temp_format[4]).replaceAll("([a-z])", "$1").toUpperCase();
			
			if(firstValue.equals("Y")) {
				result = year+"/";
				if(secondValue.equals("M")) {
					result += month + "/";
					if(thirdValue.equals("D")) {
						result += day;
						return result;
					}else {
						return null;
					}					
				}else if(secondValue.equals("D")) {
					result += day + "/";
					if(thirdValue.equals("M")) {
						result += month;
						return result;
					}else {
						return null;
					}
				}else {
					return null;
				}
			}
			if(firstValue.equals("M")) {
				result = month +"/";
				if(secondValue.equals("Y")) {
					result += year + "/";
					if(thirdValue.equals("D")) {
						result += day;
						return result;
					}else {
						return null;
					}
				}else if(secondValue.equals("D")) {
					result += day + "/";
					if(thirdValue.equals("Y")) {
						result += year;
						return result;
					}else {
						return null;
					}
				}else {
					return null;
				}
			}
			if(firstValue.equals("D")) {
				result = day +"/";
				if(secondValue.equals("Y")) {
					result += year + "/";
					if(thirdValue.equals("M")) {
						result += month;
						return result;
					}else {
						return null;
					}
				}else if(secondValue.equals("M")) {
					result += month + "/";
					if(thirdValue.equals("Y")) {
						result += year;
						return result;
					}else {
						return null;
					}
				}else {
					return null;
				}
			}
			
			return null;
		}else {
			return null;
		}		
	}
}
