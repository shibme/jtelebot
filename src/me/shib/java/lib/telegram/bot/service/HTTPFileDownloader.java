package me.shib.java.lib.telegram.bot.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPFileDownloader extends Thread {
	
	private String downloadURL;
	private String downloadDirectoryPath;
	private File file;
	private int completedPercentage;
	private File downloadedFile;
	
	protected HTTPFileDownloader(String downloadURL) {
		downloadInitializer(downloadURL, null, null);
	}
	
	protected HTTPFileDownloader(String downloadURL, String downloadDirectoryPath) {
		downloadInitializer(downloadURL, downloadDirectoryPath, null);
	}
	
	protected HTTPFileDownloader(String downloadURL, File file) {
		downloadInitializer(downloadURL, null, file);
	}
	
	private void downloadInitializer(String downloadURL, String downloadDirectoryPath, File file) {
		this.downloadURL = downloadURL;
		this.downloadDirectoryPath = downloadDirectoryPath;
		this.file = file;
		completedPercentage = 0;
		downloadedFile = null;
	}
	
	private boolean prepareDownloadPath(File downloadFileDir) {
		if(!(downloadFileDir.exists() && downloadFileDir.isDirectory())) {
			return downloadFileDir.mkdirs();
		}
		return true;
	}
	
	private File downloadFile() throws IOException {
		URL url = new URL(downloadURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		String actualFileName = new File(url.getFile()).getName();
		actualFileName = actualFileName.replaceAll("[^[^/]*]+/", "");
		if(file == null) {
			if(downloadDirectoryPath != null) {
				if(!prepareDownloadPath(new File(downloadDirectoryPath))) {
					downloadDirectoryPath = "";
				}
			}
			file = new File(downloadDirectoryPath + File.separator + actualFileName);
		}
		else {
			if(file.getParent() != null) {
				prepareDownloadPath(new File(file.getParent()));
			}
		}
		int filesize = connection.getContentLength();
		float totalDataRead = 0;
		BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
		byte[] data = new byte[1024];
		int i=0;
		while((i=in.read(data,0,1024))>=0)
		{
			totalDataRead=totalDataRead+i;
			bout.write(data,0,i);
			completedPercentage = (int)((totalDataRead*100)/filesize);
		}
		bout.close();
		in.close();
		if (file.exists()) {
			return file;
		}
		return null;
	}
	
	@Override
	public void run() {
		try {
			downloadedFile = downloadFile();
		} catch (IOException e) {
			downloadedFile = null;
		}
	}

	public File getFile() {
		return downloadedFile;
	}

	public int getCompletedPercentage() {
		return completedPercentage;
	}
	
}
