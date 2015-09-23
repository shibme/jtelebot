package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class Contact {
	
	private String phone_number;
	private String first_name;
	private String last_name;
	private String user_id;

	public String getPhone_number() {
		return phone_number;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
