package me.shib.java.lib.jtelebot.types;

public class ChatId {

    private String chat_id_channel;
    private long chat_id;

    /**
     * Initialize a new ChatId with the username of the target channel (in the format @channelusername)
     *
     * @param chat_id username of the target channel (in the format @channelusername)
     */
    public ChatId(String chat_id) {
        this.chat_id_channel = chat_id;
        this.chat_id = 0;
    }

    /**
     * Initialize a new ChatId with the unique identifier of the target chat (Private or Group)
     *
     * @param chat_id Unique identifier of the target chat
     */
    public ChatId(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getChatId() {
        if (chat_id_channel == null) {
            return chat_id + "";
        }
        return chat_id_channel;
    }

    @Override
    public String toString() {
        if (chat_id_channel == null) {
            return "ChatId [chat_id=" + chat_id + "]";
        }
        return "ChatId [chat_id=" + chat_id_channel + "]";
    }

}
