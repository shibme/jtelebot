package me.shib.java.telegram.bot.types;

import me.shib.java.rest.client.lib.JsonLib;

public class UserProfilePhotos {
	
	private int total_count;
	private PhotoSize[][] photos;

	public int getTotal_count() {
		return total_count;
	}

	public PhotoSize[][] getPhotos() {
		return photos;
	}

	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
