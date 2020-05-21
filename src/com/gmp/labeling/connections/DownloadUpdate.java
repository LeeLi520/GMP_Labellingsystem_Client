package com.gmp.labeling.connections;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.gmp.labeling.userIOs.DownloadUpdateIO;

public class DownloadUpdate implements Runnable{
	
	private String link;
	private File out;

	public DownloadUpdate(String link, File out) {
		super();
		this.link = link;
		this.out = out;
	}

	@Override
	public void run() {
		try {
			URL url = new URL(link);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
			double fileSize = (double) http.getContentLengthLong();
			BufferedInputStream in = new BufferedInputStream(http.getInputStream());
			FileOutputStream fos = new FileOutputStream(this.out);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] buffer = new byte[1024];
			double downloaded = 0.00;
			int read = 0;
			double percentDownloaded = 0.00;
			DownloadUpdateIO download = new DownloadUpdateIO();
			download.setLocationRelativeTo(null);
			download.setVisible(true);
			while((read = in.read(buffer, 0, 1024)) >=0) {
				bout.write(buffer, 0, read);
				downloaded += read;
				percentDownloaded = (downloaded*100)/fileSize;
				int integerValue = (int) percentDownloaded;
				download.setProgress(integerValue);
			}
			bout.close();
			in.close();
			download.completeStatus();
			download.closeWindow();
			try {
				String home = System.getProperty("user.home");
				String filelocation = home+"/Downloads/"+"LabelingSystem_setup.exe";
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+filelocation);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
