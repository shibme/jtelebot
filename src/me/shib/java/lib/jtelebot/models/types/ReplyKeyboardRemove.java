package me.shib.java.lib.jtelebot.models.types;

/**
 * Upon receiving a message with this object, Telegram clients will hide the current custom keyboard and display the default letter-keyboard. By default, custom keyboards are displayed until a new keyboard is sent by a bot. An exception is made for one-time keyboards that are hidden immediately after the user presses a button (see ReplyKeyboardMarkup).
 */
public final class ReplyKeyboardRemove implements ReplyMarkup {

    private final boolean hide_keyboard = true;
    private boolean selective;

    /**
     * Initializes a new ReplyKeyboardRemove object
     *
     * @param selective Use this parameter if you want to hide keyboard for specific users only. Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    public ReplyKeyboardRemove(boolean selective) {
        this.selective = selective;
    }
}
