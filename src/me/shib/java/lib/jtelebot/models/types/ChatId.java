package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a Chat ID (either username or unique identifier of the chat).
 */
public final class ChatId {

    private String chat_id;

    /**
     * Initialize a new ChatId with the username of the target channel (in the format @channelusername)
     *
     * @param chat_id username of the target channel (in the format @channelusername)
     */
    public ChatId(String chat_id) {
        this.chat_id = chat_id;
    }

    /**
     * Initialize a new ChatId with the unique identifier of the target chat (Private or Group)
     *
     * @param chat_id Unique identifier of the target chat
     */
    public ChatId(long chat_id) {
        this.chat_id = chat_id + "";
    }

    /**
     * @return the chat id as a string
     */
    public String getChatId() {
        return this.chat_id;
    }

}