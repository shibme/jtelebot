package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class ReplyKeyboardMarkup implements ReplyMarkup {
	
	private String[][] keyboard;
	private boolean resize_keyboard;
	private boolean one_time_keyboard;
	private boolean selective;
	
	public ReplyKeyboardMarkup(String[][] keyboard) {
		this.keyboard = keyboard;
		this.resize_keyboard = false;;
		this.one_time_keyboard = false;
		this.selective = false;
	}

	public String[][] getKeyboard() {
		return keyboard;
	}

	public boolean isResize_keyboard() {
		return resize_keyboard;
	}

	public boolean isOne_time_keyboard() {
		return one_time_keyboard;
	}

	public boolean isSelective() {
		return selective;
	}
	
	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
