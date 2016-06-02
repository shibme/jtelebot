package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents one special entity in a text message. For example, hashtags, usernames, URLs, etc.
 */
public final class MessageEntity {

    private EntityType type;
    private int offset;
    private int length;
    private String url;
    private User user;

    /**
     * @return Type of the entity. One of mention (@username), hashtag, bot_command, url, email, bold (bold text), italic (italic text), code (monowidth string), pre (monowidth block), text_link (for clickable text URLs)
     */
    public EntityType getType() {
        return type;
    }

    /**
     * @return Offset in UTF-16 code units to the start of the entity
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return Length of the entity in UTF-16 code units
     */
    public int getLength() {
        return length;
    }

    /**
     * @return For “text_link” only, url that will be opened after user taps on the text
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return For “text_mention” only, the mentioned user
     */
    public User getUser() {
        return user;
    }

    public enum EntityType {
        mention, hashtag, bot_command, url, email, bold, italic, code, pre, text_link, text_mention
    }
}
