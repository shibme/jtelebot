package me.shib.java.telegram.bot.types;

import me.shib.java.rest.client.lib.JsonLib;

public class ReplyKeyboardHide implements ReplyMarkup {
	
	private final Boolean hide_keyboard = Boolean.TRUE;
	private boolean selective;

	public Boolean getHide_keyboard() {
		return hide_keyboard;
	}

	public boolean isSelective() {
		return selective;
	}
	
	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
