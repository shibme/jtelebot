package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class TelegramFile {

	private String file_id;
	private long file_size;
	private String file_path;
	
	public String getFile_id() {
		return file_id;
	}
	
	public long getFile_size() {
		return file_size;
	}
	
	public String getFile_path() {
		return file_path;
	}

	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
