package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class Audio {
	
	private String file_id;
	private int duration;
	private String mime_type;
	private long file_size;
	
	public Audio(String file_id, int duration) {
		this.file_id = file_id;
		this.duration = duration;
	}

	public String getFile_id() {
		return file_id;
	}

	public int getDuration() {
		return duration;
	}

	public String getMime_type() {
		return mime_type;
	}

	public long getFile_size() {
		return file_size;
	}

	@Override
	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
