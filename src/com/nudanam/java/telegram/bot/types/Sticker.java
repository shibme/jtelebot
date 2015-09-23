package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class Sticker {
	
	private String file_id;
	private int width;
	private int height;
	private PhotoSize thumb;
	private long file_size;

	public String getFile_id() {
		return file_id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public PhotoSize getThumb() {
		return thumb;
	}

	public long getFile_size() {
		return file_size;
	}
	
	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
