package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class PhotoSize {
	
	private String file_id;
	private int width;
	private int height;
	private long file_size;
	
	public PhotoSize(String file_id, int width, int height) {
		this.file_id = file_id;
		this.width = width;
		this.height = height;
	}

	public String getFile_id() {
		return file_id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public long getFile_size() {
		return file_size;
	}

	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
