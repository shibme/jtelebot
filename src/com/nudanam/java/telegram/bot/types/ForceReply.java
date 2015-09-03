package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class ForceReply implements ReplyMarkup {
	
	private final Boolean force_reply = Boolean.TRUE;
	private boolean selective;
	
	public ForceReply(boolean selective) {
		this.selective = selective;
	}

	public Boolean getForce_reply() {
		return force_reply;
	}

	public boolean isSelective() {
		return selective;
	}
	
	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
