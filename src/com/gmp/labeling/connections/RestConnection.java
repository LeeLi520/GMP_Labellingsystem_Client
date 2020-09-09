package com.gmp.labeling.connections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.gmp.labeling.models.Material;
import com.gmp.labeling.models.RestErrorMessage;
import com.gmp.labeling.models.Setting;
import com.gmp.labeling.models.User;
import com.gmp.labeling.printModels.Label;
import com.gmp.labeling.userIOs.Settings;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestConnection {
	
	private static final String WebserviceUuid = "3e20358d-cba2-4fec-a329-360335bb2d94";
	// get user connection
	public User getUser(String username, String password) {	
		
		String result = null;
		OkHttpClient client = new OkHttpClient();
		
		String url = loadProperties().getProperty("host")+ WebserviceUuid +"/Employee/findEmployeeByUsernameAndPassword/"+username+"/"+password;
		
		RequestBody formBody = new FormBody.Builder()
		        .add("message", "Your message")
		        .build();
		
		Request request = new Request.Builder()
				.url(url)
				.post(formBody)
				.build();
		
		Response response;
		try {
			response = client.newCall(request).execute();
			result = response.body().string();
		}catch(IOException e) {
			e.printStackTrace();
		}
		User user;
		if(!result.equals("")) {
			RestErrorMessage message = detectRestError(result);
			if(message==null) {
				Gson g = new Gson();
				user = g.fromJson(result, User.class);
				return user;
			}else {
				writeRestErrorLog(loadProperties().getProperty("errorlogpath"), message);
				return null;
			}
		}else {
			return null;
		}	
	}
	
	//Identify the rest errors
	public RestErrorMessage detectRestError(String result) {
		Gson g = new Gson();
		RestErrorMessage errorMessage = g.fromJson(result, RestErrorMessage.class);
		if(errorMessage.getTimestamp()!=0) {
		    return errorMessage;
		}else {
			return null;
		}		
	}
	
	// write log for rest connection errors
	public boolean writeRestErrorLog(String path, RestErrorMessage message) {
		String csvFile = path;
        String line = "";
        String cvsSplitBy = ",";
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
            	if(line.equals("")) {
            		//first line dont input
            	}else {
                String[] items = line.split(cvsSplitBy);
                sb.append(items[0]);
                sb.append(',');
                sb.append(items[1]);
                sb.append(',');
                sb.append(items[2]);
                sb.append(',');
                sb.append(items[3]);
                sb.append(',');
                sb.append(items[4]);
                sb.append(',');
                sb.append(items[5]);
                sb.append('\n');
            	}
            }

        } catch (IOException e) {
            return false;
        }
        
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File(path));	        
	        sb.append(message.getTimestamp());
	        sb.append(',');
	        sb.append(message.getStatus());
	        sb.append(',');
	        sb.append(message.getError());
	        sb.append(',');
	        sb.append(message.getException());
	        sb.append(',');
	        sb.append(message.getMessage());
	        sb.append(',');
	        sb.append(message.getPath());
	        sb.append('\n');
	  
	        pw.write(sb.toString());
	        pw.close(); 
	        return true;
		} catch (FileNotFoundException e) {
			return false;
		}
     
	}
	
	//get Material list from rest API
	public List<Material> getMaterialList(){
		return null;
	}
	
	//get application version for comparing
	public String CompareVersion() {
		String result = null;
		OkHttpClient client = new OkHttpClient();
		
		String getVersionInfo = loadProperties().getProperty("host")+ WebserviceUuid +"/WebService/Version";
		
		Request request = new Request.Builder()
				.url(getVersionInfo)
				.get()
				.build();
		
		Response response;
		
		try {
			response = client.newCall(request).execute();
			result = response.body().string();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	//Download updated application
	public void updateApplication() {
		String updateLink = loadProperties().getProperty("host")+ WebserviceUuid +"/WebService/Download";
		String home = System.getProperty("user.home");
		File out = new File(home+"/Downloads/"+"LabelingSystem_setup.exe");
		
		new Thread(new DownloadUpdate(updateLink, out)).start();		
	}
	
	public void FtpUpdateFiles(Settings settings) {
		try {
			synchronized(this) {				
				 new Thread(new FTPDownloader("ftp.gmpadmin.online", "pronto@gmpadmin.online", "pro8863ftP")).start();				 
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveSettings(User logined_user, Setting setting) {
		String saveData = null;
		String result = null;
		OkHttpClient client = new OkHttpClient();
		
		String getInfoUrl = loadProperties().getProperty("host")+ WebserviceUuid +"/WebService/LoginDetails/"+ logined_user.getUuid();
		
		Request request = new Request.Builder()
				.url(getInfoUrl)
				.get()
				.build();
		
		Response response;
		try {
			response = client.newCall(request).execute();
			result = response.body().string();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		result = JsonParser.takeOffstringjson(result);
		
		String temp = JsonParser.changeJsonStringValue("port", String.valueOf(setting.getPort()), JsonParser.changeJsonStringValue("IpAddress", String.valueOf(setting.getIpAddress()), JsonParser.changeJsonStringValue("LocalPrintingMode", String.valueOf(setting.isLocalPrintingMode()), result)));
		saveData = JsonParser.getObjectValueFromJson("loginDetails", temp);
		
        String postInfoUrl = loadProperties().getProperty("host")+ WebserviceUuid +"/Employee/addUseRelationship/"+logined_user.getUuid()+"/"+WebserviceUuid+"/loginDetails";
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), saveData);
		Request request_next = new Request.Builder()
				.url(postInfoUrl)
				.post(body)
				.build();
		
		try {
			client.newCall(request_next).execute();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendLog(User logined_user, Label label) {
		String logBody = label.toString();
		OkHttpClient client = new OkHttpClient();
		Date date = new Date();
		String postInfoUrl = loadProperties().getProperty("logserverhost")+ "90593f97-9dd6-4988-9f8f-d18c23505838" +"/Label/newLog/"+logined_user.getUuid()+"/"+logined_user.getFullname()+"/"+date.toString();
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), logBody);
		Request request_next = new Request.Builder()
				.url(postInfoUrl)
				.post(body)
				.build();
		
		try {
			client.newCall(request_next).execute();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
    public void sendLog(User logined_user, Label label, String ipaddress, String port) {
    	String logBody = "{"+label.toString()+",{ipaddress:\""+ipaddress+"\"},{port:\""+port+"\"}}";
		OkHttpClient client = new OkHttpClient();
		Date date = new Date();
		//temp use location
		String postInfoUrl = loadProperties().getProperty("logserverhost")+ "90593f97-9dd6-4988-9f8f-d18c23505838" +"/Label/newLog/"+logined_user.getUuid()+"/"+logined_user.getFullname()+"/"+date.toString();
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), logBody);
		Request request_next = new Request.Builder()
				.url(postInfoUrl)
				.post(body)
				.build();
		
		try {
			client.newCall(request_next).execute();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public List<String> getUserTemplateList(User logined_user){
		List<String> list = null;		
		String result = null;
		OkHttpClient client = new OkHttpClient();
		
		String url = loadProperties().getProperty("host")+ WebserviceUuid +"/WebService/LoginDetails/"+ logined_user.getUuid();
		
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		
		Response response;
		try {
			response = client.newCall(request).execute();
			result = response.body().string();
		}catch(IOException e) {
			e.printStackTrace();
		}

		result = JsonParser.takeOffstringjson(result);

		if(JsonParser.getObjectValueFromJson("loginDetails", result)!=null){
			list = new ArrayList<String>();
			if(JsonParser.getObjectValueFromJson("Template", result)!=null) {
				char[] temp = JsonParser.getObjectValueFromJson("Template", result).toCharArray();
				String template = null;
				for(char i : temp) {
					if(i!=' ') {
					if(i!= ',') {
						if(template == null) {
							template = String.valueOf(i);
						}else {
							template += String.valueOf(i);
						}
					}else {
						list.add(template);
						template = null;
					}
					}
				}
				if(template != null) {
					list.add(template);
				}
			}			
		}
		return list;
	}
	
	//get service connected users
	public List<User> getConnected(){
		
		List<User> users = new ArrayList<>();
		String result = null;
		OkHttpClient client = new OkHttpClient();
		
		String url = "http://10.0.28.98:8089/" + WebserviceUuid + "/WebService/findConnectingEmployees";
		
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		
		Response response;
		try {
			response = client.newCall(request).execute();
			result = response.body().string();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		char[] temp = result.toCharArray();
		int i = 0;
		String ind = null;
		while(i<temp.length) {
			if(temp[i] == '[') {
				
			}else if(temp[i] == ']') {
				if(ind == null) {
					break;
				}else {
					Gson g = new Gson();
					User user = g.fromJson(ind, User.class);
					users.add(user);
					break;
				}				
			}else if(temp[i] == '}'){
				ind += temp[i];
				Gson g = new Gson();
				User user = g.fromJson(ind, User.class);
				users.add(user);
				ind = null;
				if(temp[i+1] == ',') {
					i++;
				}
			}else{
				if(ind == null) {
					ind = String.valueOf(temp[i]);
				}else{
					ind += String.valueOf(temp[i]);
				}
			}
			i++;
		}
		
		return users;
		
	}
	
	public Setting getSetting(User logined_user) {		
		String result = null;
		OkHttpClient client = new OkHttpClient();
		
		String getInfoUrl = loadProperties().getProperty("host")+ WebserviceUuid +"/WebService/LoginDetails/"+ logined_user.getUuid();
		
		Request request = new Request.Builder()
				.url(getInfoUrl)
				.get()
				.build();
		
		Response response;
		try {
			response = client.newCall(request).execute();
			result = response.body().string();
		}catch(IOException e) {
			e.printStackTrace();
		}
		result = JsonParser.takeOffstringjson(result);
		Setting setting = new Setting();
		if(JsonParser.getObjectValueFromJson("LocalPrintingMode", result).equals("true")) {
			setting.setLocalPrintingMode(true);
		}else {
			setting.setLocalPrintingMode(false);
		}
		setting.setIpAddress(JsonParser.getObjectValueFromJson("IpAddress", result));
		try {
			Integer.valueOf(JsonParser.getObjectValueFromJson("port", result));
			setting.setPort(Integer.valueOf(JsonParser.getObjectValueFromJson("port", result)));
		}catch(Exception e) {
			setting.setPort(0);
		}		
		return setting;		
	}
	
	public Properties loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("Datafiles/properties/config.properties");
//			input = new FileInputStream(".\\src\\com\\gmp\\labeling\\resources\\properties\\config.properties");
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
