package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a chat.
 */
public final class Chat {

    private long id;
    private String type;
    private String title;
    private String username;
    private String first_name;
    private String last_name;

    /**
     * @return Unique identifier for this chat, not exceeding 1e13 by absolute value
     */
    public long getId() {
        return id;
    }

    /**
     * @return Type of chat, can be either “private”, “group”, “supergroup” or “channel”
     */
    public ChatType getType() {
        String expectedType = type.toLowerCase().substring(0, 1).toUpperCase() + type.toLowerCase().substring(1);
        getTitle();
        return ChatType.valueOf(expectedType);
    }

    /**
     * @return Title, for channels and group chats
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Username, for private chats and channels if available
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return First name of the other party in a private chat
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @return Last name of the other party in a private chat
     */
    public String getLast_name() {
        return last_name;
    }

    public enum ChatType {
        Private, Group, Supergroup, Channel
    }

}
