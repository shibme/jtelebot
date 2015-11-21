package me.shib.java.lib.telegram.bot.types;

import java.io.File;

public class TelegramFile {

	private String file_id;
	private File file;
	private long file_size;
	private String file_path;
	
	public TelegramFile(String file_id) {
		this.file_id = file_id;
	}
	
	public TelegramFile(File file) {
		this.file = file;
	}

	public String getFile_id() {
		return file_id;
	}

	public File getFile() {
		return file;
	}
	
	public long getFile_size() {
		return file_size;
	}
	
	public String getFile_path() {
		return file_path;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "TelegramFile [file_id=" + file_id + ", file=" + file + ", file_size=" + file_size + ", file_path="
				+ file_path + "]";
	}
	
}
