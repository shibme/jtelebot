package me.shib.java.lib.telegram.bot.types;

public class ChatId {
	
	private String chat_id_channel;
	private long chat_id;
	
	public ChatId(String chat_id) {
		this.chat_id_channel = chat_id;
		this.chat_id = 0;
	}
	
	public ChatId(long chat_id) {
		this.chat_id = chat_id;
	}

	public String getChatId() {
		if(chat_id_channel == null) {
			return chat_id + "";
		}
		return chat_id_channel;
	}

	public String sstoString() {
		return "ChatId [chat_id_channel=" + chat_id_channel + "]";
	}

	@Override
	public String toString() {
		if(chat_id_channel == null) {
			return "ChatId [chat_id=" + chat_id + "]";
		}
		return "ChatId [chat_id=" + chat_id_channel + "]";
	}
	
}
