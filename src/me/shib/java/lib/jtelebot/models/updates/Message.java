package me.shib.java.lib.jtelebot.models.updates;

import me.shib.java.lib.jtelebot.models.types.*;

/**
 * This object represents a message.
 */
public final class Message {

    private long message_id;
    private User from;
    private long date;
    private Chat chat;
    private User forward_from;
    private Chat forward_from_chat;
    private long forward_date;
    private Message reply_to_message;
    private long edit_date;
    private String text;
    private MessageEntity[] entities;
    private Audio audio;
    private Document document;
    private PhotoSize[] photo;
    private Sticker sticker;
    private Video video;
    private Voice voice;
    private String caption;
    private Contact contact;
    private Location location;
    private Venue venue;
    private User new_chat_member;
    private User left_chat_member;
    private String new_chat_title;
    private PhotoSize[] new_chat_photo;
    private boolean delete_chat_photo;
    private boolean group_chat_created;
    private boolean supergroup_chat_created;
    private boolean channel_chat_created;
    private long migrate_to_chat_id;
    private long migrate_from_chat_id;
    private Message pinned_message;

    /**
     * @return Unique message identifier
     */
    public long getMessage_id() {
        return message_id;
    }

    /**
     * @return Sender, can be empty for messages sent to channels
     */
    public User getFrom() {
        return from;
    }

    /**
     * @return Date the message was sent in Unix time
     */
    public long getDate() {
        return date;
    }

    /**
     * @return Conversation the message belongs to
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * @return For forwarded messages, sender of the original message
     */
    public User getForward_from() {
        return forward_from;
    }

    /**
     * @return For messages forwarded from a channel, information about the original channel
     */
    public Chat getForward_from_chat() {
        return forward_from_chat;
    }

    /**
     * @return For forwarded messages, date the original message was sent in Unix time
     */
    public long getForward_date() {
        return forward_date;
    }

    /**
     * @return For replies, the original message. Note that the Message object in this field will not contain further reply_to_message fields even if it itself is a reply.
     */
    public Message getReply_to_message() {
        return reply_to_message;
    }

    /**
     * @return Date the message was last edited in Unix time
     */
    public long getEdit_date() {
        return edit_date;
    }

    /**
     * @return For text messages, the actual UTF-8 text of the message, 0-4096 characters.
     */
    public String getText() {
        return text;
    }

    /**
     * @return For text messages, special entities like usernames, URLs, bot commands, etc. that appear in the text
     */
    public MessageEntity[] getEntities() {
        return entities;
    }

    /**
     * @return Message is an audio file, information about the file
     */
    public Audio getAudio() {
        return audio;
    }

    /**
     * @return Message is a general file, information about the file
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @return Message is a photo, available sizes of the photo
     */
    public PhotoSize[] getPhoto() {
        return photo;
    }

    /**
     * @return Message is a sticker, information about the sticker
     */
    public Sticker getSticker() {
        return sticker;
    }

    /**
     * @return Message is a video, information about the video
     */
    public Video getVideo() {
        return video;
    }

    /**
     * @return Message is a voice message, information about the file
     */
    public Voice getVoice() {
        return voice;
    }

    /**
     * @return Caption for the document, photo or video, 0-200 characters
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @return Message is a shared contact, information about the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * @return Message is a shared location, information about the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Message is a venue, information about the venue
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * @return A new member was added to the group, information about them (this member may be the bot itself)
     */
    public User getNew_chat_member() {
        return new_chat_member;
    }

    /**
     * @return A member was removed from the group, information about them (this member may be the bot itself)
     */
    public User getLeft_chat_member() {
        return left_chat_member;
    }

    /**
     * @return A chat title was changed to this value
     */
    public String getNew_chat_title() {
        return new_chat_title;
    }

    /**
     * @return A chat photo was change to this value
     */
    public PhotoSize[] getNew_chat_photo() {
        return new_chat_photo;
    }

    /**
     * @return Service message: the chat photo was deleted
     */
    public boolean isDelete_chat_photo() {
        return delete_chat_photo;
    }

    /**
     * @return Service message: the group has been created
     */
    public boolean isGroup_chat_created() {
        return group_chat_created;
    }

    /**
     * @return Service message: the supergroup has been created
     */
    public boolean isSupergroup_chat_created() {
        return supergroup_chat_created;
    }

    /**
     * @return Service message: the channel has been created
     */
    public boolean isChannel_chat_created() {
        return channel_chat_created;
    }

    /**
     * @return The group has been migrated to a supergroup with the specified identifier, not exceeding 1e13 by absolute value
     */
    public long getMigrate_to_chat_id() {
        return migrate_to_chat_id;
    }

    /**
     * @return The supergroup has been migrated from a group with the specified identifier, not exceeding 1e13 by absolute value
     */
    public long getMigrate_from_chat_id() {
        return migrate_from_chat_id;
    }

    /**
     * @return Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.
     */
    public Message getPinned_message() {
        return pinned_message;
    }
}
