package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class Video {
	
	private String file_id;
	private int width;
	private int height;
	private int duration;
	private PhotoSize thumb;
	private String mime_type;
	private int file_size;
	private String caption;

	public String getFile_id() {
		return file_id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDuration() {
		return duration;
	}

	public PhotoSize getThumb() {
		return thumb;
	}

	public String getMime_type() {
		return mime_type;
	}

	public int getFile_size() {
		return file_size;
	}

	public String getCaption() {
		return caption;
	}

	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
