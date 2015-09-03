package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class User {
	
	private long id;
	private String first_name;
	private String last_name;
	private String username;
	
	public User(long id, String first_name) {
		this.id = id;
		this.first_name = first_name;
	}

	protected void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	protected void setUsername(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getUsername() {
		return username;
	}
	
	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
