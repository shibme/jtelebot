package me.shib.java.lib.telegram.bot.types;

public class Update {
	
	private long update_id;
	private Message message;

	public long getUpdate_id() {
		return update_id;
	}

	public Message getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Update [update_id=" + update_id + ", message=" + message + "]";
	}
	
}
