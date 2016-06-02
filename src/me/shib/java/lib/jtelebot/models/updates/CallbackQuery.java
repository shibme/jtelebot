package me.shib.java.lib.jtelebot.models.updates;

import me.shib.java.lib.jtelebot.models.types.User;

/**
 * This object represents an incoming callback query from a callback button in an inline keyboard. If the button that originated the query was attached to a message sent by the bot, the field message will be presented. If the button was attached to a message sent via the bot (in inline mode), the field inline_message_id will be presented.
 */
public final class CallbackQuery {

    private String id;
    private User from;
    private Message message;
    private String inline_message_id;
    private String data;

    /**
     * @return Unique identifier for this query
     */
    public String getId() {
        return id;
    }

    /**
     * @return Sender
     */
    public User getFrom() {
        return from;
    }

    /**
     * @return Message with the callback button that originated the query. Note that message content and message date will not be available if the message is too old
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @return Identifier of the message sent via the bot in inline mode, that originated the query
     */
    public String getInline_message_id() {
        return inline_message_id;
    }

    /**
     * @return Data associated with the callback button. Be aware that a bad client can send arbitrary data in this field
     */
    public String getData() {
        return data;
    }
}
