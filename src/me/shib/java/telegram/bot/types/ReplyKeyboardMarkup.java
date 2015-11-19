package me.shib.java.telegram.bot.types;

import java.util.Arrays;

public class ReplyKeyboardMarkup implements ReplyMarkup {
	
	private String[][] keyboard;
	private boolean resize_keyboard;
	private boolean one_time_keyboard;
	private boolean selective;
	
	public ReplyKeyboardMarkup(String[][] keyboard) {
		initializer(keyboard, false, true, false);
	}
	
	public ReplyKeyboardMarkup(String[][] keyboard, boolean resize_keyboard) {
		initializer(keyboard, resize_keyboard, true, false);
	}
	
	public ReplyKeyboardMarkup(String[][] keyboard, boolean resize_keyboard, boolean one_time_keyboard) {
		initializer(keyboard, resize_keyboard, one_time_keyboard, false);
	}
	
	public ReplyKeyboardMarkup(String[][] keyboard, boolean resize_keyboard, boolean one_time_keyboard, boolean selective) {
		initializer(keyboard, resize_keyboard, one_time_keyboard, selective);
	}
	
	private void initializer(String[][] keyboard, boolean resize_keyboard, boolean one_time_keyboard, boolean selective) {
		this.keyboard = keyboard;
		this.resize_keyboard = resize_keyboard;
		this.one_time_keyboard = one_time_keyboard;
		this.selective = selective;
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

	@Override
	public String toString() {
		return "ReplyKeyboardMarkup [keyboard=" + Arrays.toString(keyboard) + ", resize_keyboard=" + resize_keyboard
				+ ", one_time_keyboard=" + one_time_keyboard + ", selective=" + selective + "]";
	}
	
}
