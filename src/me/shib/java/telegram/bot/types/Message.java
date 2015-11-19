package me.shib.java.telegram.bot.types;

import java.util.Arrays;

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
	private Voice voice;
	private String caption;
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

	public Voice getVoice() {
		return voice;
	}

	public String getCaption() {
		return caption;
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

	@Override
	public String toString() {
		return "Message [message_id=" + message_id + ", from=" + from + ", date=" + date + ", chat=" + chat
				+ ", forward_from=" + forward_from + ", forward_date=" + forward_date + ", reply_to_message="
				+ reply_to_message + ", text=" + text + ", audio=" + audio + ", document=" + document + ", photo="
				+ Arrays.toString(photo) + ", sticker=" + sticker + ", video=" + video + ", voice=" + voice
				+ ", caption=" + caption + ", contact=" + contact + ", location=" + location + ", new_chat_participant="
				+ new_chat_participant + ", left_chat_participant=" + left_chat_participant + ", new_chat_title="
				+ new_chat_title + ", new_chat_photo=" + Arrays.toString(new_chat_photo) + ", delete_chat_photo="
				+ delete_chat_photo + ", group_chat_created=" + group_chat_created + "]";
	}
	
}
