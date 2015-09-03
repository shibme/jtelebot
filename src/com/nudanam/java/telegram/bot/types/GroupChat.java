package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;


public class GroupChat {
	
	private long id;
	private String title;
	
	protected GroupChat(long id, String title) {
		this.id = id;
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
