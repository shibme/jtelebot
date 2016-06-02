package me.shib.java.lib.jtelebot.models.types;

/**
 * Upon receiving a message with this object, Telegram clients will display a reply interface to the user (act as if the user has selected the bot‘s message and tapped ’Reply'). This can be extremely useful if you want to create user-friendly step-by-step interfaces without having to sacrifice privacy mode.
 */
public final class ForceReply implements ReplyMarkup {

    private final boolean force_reply = true;
    private boolean selective;

    /**
     * Initializes a new ForceReply object
     *
     * @param selective Use this parameter if you want to force reply from specific users only. Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    public ForceReply(boolean selective) {
        this.selective = selective;
    }
}
