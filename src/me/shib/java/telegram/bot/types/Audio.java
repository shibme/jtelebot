package me.shib.java.telegram.bot.types;

import me.shib.java.rest.client.lib.JsonLib;

public class Audio {
	
	private String file_id;
	private int duration;
	private String mime_type;
	private long file_size;

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
