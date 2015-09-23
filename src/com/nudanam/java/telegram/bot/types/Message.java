package com.nudanam.java.telegram.bot.types;

import com.nudanam.java.rest.client.lib.JsonLib;

public class Message {
	
	private int message_id;
	private User from;
	private long date;
	private Chat chat;
	private User forward_from;
	private long forward_date;
	private Message reply_to_message;
	private String text;
	private Audio audio;
	private Document document;
	private PhotoSize[] photo;
	private Sticker sticker;
	private Video video;
	private Contact contact;
	private Location location;
	private User new_chat_participant;
	private User left_chat_participant;
	private String new_chat_title;
	private PhotoSize[] new_chat_photo;
	private Boolean delete_chat_photo;
	private Boolean group_chat_created;

	public int getMessage_id() {
		return message_id;
	}

	public User getFrom() {
		return from;
	}

	public long getDate() {
		return date;
	}

	public Chat getChat() {
		return chat;
	}

	public User getForward_from() {
		return forward_from;
	}

	public long getForward_date() {
		return forward_date;
	}

	public Message getReply_to_message() {
		return reply_to_message;
	}

	public String getText() {
		return text;
	}

	public Audio getAudio() {
		return audio;
	}

	public Document getDocument() {
		return document;
	}

	public PhotoSize[] getPhoto() {
		return photo;
	}

	public Sticker getSticker() {
		return sticker;
	}

	public Video getVideo() {
		return video;
	}

	public Contact getContact() {
		return contact;
	}

	public Location getLocation() {
		return location;
	}

	public User getNew_chat_participant() {
		return new_chat_participant;
	}

	public User getLeft_chat_participant() {
		return left_chat_participant;
	}

	public String getNew_chat_title() {
		return new_chat_title;
	}

	public PhotoSize[] getNew_chat_photo() {
		return new_chat_photo;
	}

	public Boolean getDelete_chat_photo() {
		return delete_chat_photo;
	}

	public Boolean getGroup_chat_created() {
		return group_chat_created;
	}

	public String toString() {
		return JsonLib.toJson(this);
	}
	
}
