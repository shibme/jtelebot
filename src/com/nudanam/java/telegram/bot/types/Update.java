package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class Update {
	
	private long update_id;
	private Message message;

	public long getUpdate_id() {
		return update_id;
	}

	public Message getMessage() {
		return message;
	}
	
	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
