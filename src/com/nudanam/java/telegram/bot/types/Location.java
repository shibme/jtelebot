package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class Location {
	
	private float longitude;
	private float latitude;
	
	public Location(float longitude, float latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
