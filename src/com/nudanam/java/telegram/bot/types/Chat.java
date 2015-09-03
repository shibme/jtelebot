package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class Chat {
	
	public enum ChatType {
		User, Group
	}
	
	private long id;
	private String title;
	private String first_name;
	private String last_name;
	private String username;

	public long getId() {
		return id;
	}
	
	public ChatType getType() {
		if((null == title) || (id > 0)) {
			return ChatType.User;
		}
		return ChatType.Group;
	}
	
	public User getUser() {
		if(getType() == ChatType.Group) {
			return null;
		}
		User user = new User(this.id, this.first_name);
		user.setLast_name(this.last_name);
		user.setUsername(this.username);
		return user;
	}
	
	public GroupChat getGroup() {
		if(getType() == ChatType.User) {
			return null;
		}
		return new GroupChat(this.id, this.title);
	}

	public String toString() {
		if(getType() == ChatType.User) {
			return JsonLib.toJson(getUser());
		}
		return JsonLib.toJson(getGroup());
	}
	
}
