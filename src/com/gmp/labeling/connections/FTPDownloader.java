package com.gmp.labeling.connections;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.gmp.labeling.userIOs.DownloadDataFile;

public class FTPDownloader implements Runnable{
	
	private FTPClient ftp = null;
	private RestConnection connection;
	private String host;
	private String user;
	private String pwd;
	private String targetFile;
	private String saveFile;

    public FTPDownloader(String host, String user, String pwd, String targetFile, String saveFile) throws Exception {
    	this.host = host;
    	this.user = user;
    	this.pwd = pwd;
    	this.targetFile = targetFile;
    	this.saveFile = saveFile;
    	connection = new RestConnection();
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(this.host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(this.user, this.pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

    public void downloadFile(String remoteFilePath, String localFilePath) {
        try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
            this.ftp.retrieveFile(remoteFilePath, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already downloaded from FTP server
            }
        }
    }

	@Override
	public void run() {
		try {
			DownloadDataFile downloadDataFileIO = new DownloadDataFile();
			downloadDataFileIO.setLocationRelativeTo(null);
			downloadDataFileIO.setVisible(true);
            com.gmp.labeling.connections.FTPDownloader ftpDownloader =
                new com.gmp.labeling.connections.FTPDownloader(this.host, this.user , this.pwd, this.targetFile, this.saveFile);
//            ftpDownloader.downloadFile("stk.csv", connection.loadProperties().getProperty("materiallistpath"));
            ftpDownloader.downloadFile(this.targetFile, connection.loadProperties().getProperty(this.saveFile));
            downloadDataFileIO.finished();
            ftpDownloader.disconnect();
            downloadDataFileIO.closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }		
	}

}
