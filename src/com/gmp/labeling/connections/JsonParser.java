package com.gmp.labeling.connections;

public final class JsonParser {
	
	public static String getObjectValueFromJson(String valueName, String jsonString) {
		if(jsonString.indexOf("\""+valueName+"\"") == -1) {
			return null;
		}
		int valueStartPoint = jsonString.indexOf("\""+valueName+"\"") + valueName.length()+3;
		char[] temp = jsonString.toCharArray();
		String target = null;
		boolean isString = false;
		boolean isObject = false;
		int mark = 0;
		for(int i = valueStartPoint; i <jsonString.length(); i++) {
			if(i>= valueStartPoint) {
				if(i == jsonString.length()-1 && temp[i] =='}') {
					return target;
				}
				if(temp[i] == '\"' && !isString && !isObject) {
					isString = true;
					i++;
				}
				if(temp[i] == '\"' && isString && !isObject){
					return target;
				}
				if(!isString && temp[i]!=',' && !isObject) {
					if(target == null) {
						target =String.valueOf(temp[i]);
					}else {
						target += String.valueOf(temp[i]);
					}			
				}else if(!isString && temp[i]== ',' && !isObject) {
					return target;
				}
				if(isString && !isObject) {
					if(target == null) {
						target =String.valueOf(temp[i]);
					}else {
						target += String.valueOf(temp[i]);
					}	
				}
				if(temp[i] == '{' && !isObject) {
					isObject = true;
					mark++;
					i++;
				}
				if(isObject && temp[i]!='}') {
					if(target == null) {
						target =String.valueOf(temp[i]);
					}else {
						target += String.valueOf(temp[i]);
					}	
				}else if(isObject && temp[i]=='}' && mark ==0) {
					target += String.valueOf(temp[i]);
					return target;
				}else if(isObject && temp[i]=='}' && mark !=0) {
					target += String.valueOf(temp[i]);
					mark--;
				}

			}
		}
		return null;
	}
	
	public static String takeOffstringjson(String jsonString) {		
		String result = jsonString.replace("\\", "");		
		return result;
	}
	
	public static String takeoffComma(String sourceString) {
		String result = sourceString.replaceAll("\"", "");
		return result;
	}
	
	public static String changeJsonStringValue(String fieldName, String value, String jsonString) {
		String result = null;
		int note = jsonString.indexOf(fieldName);
		char[] temp = jsonString.toCharArray();
		for(int i = 0; i < jsonString.length(); i++) {
			if(i < note + fieldName.length()+3) {
				if(result == null) {
					result = String.valueOf(temp[i]);
				}else {
					result += String.valueOf(temp[i]);
				}
			}else if(i == note + fieldName.length()+3){
				result += value;
				i += getObjectValueFromJson(fieldName, jsonString).length()-1;
			}else {
				result += String.valueOf(temp[i]);
			}
		}
		return result;
	}
	
	public static int getVersionPositionNumber(int position, String versionData) {
		char[] version = versionData.toCharArray();
		int temp = 0;
		int i = 0;
		int j = position;
		int value = 0;
		String tempValue = null;
		while(temp<j && i < version.length) {
			if(version[i]!='.') {
				if(tempValue == null) {
					tempValue = String.valueOf(version[i]);
					value = Integer.valueOf(tempValue);
				}else {
					tempValue += String.valueOf(version[i]);
					value = Integer.valueOf(tempValue);
				}
			}else {
				value = Integer.valueOf(tempValue);
				tempValue = null;
				temp++;
			}
			i++;
		}
		return value;
	}
	
	
}
