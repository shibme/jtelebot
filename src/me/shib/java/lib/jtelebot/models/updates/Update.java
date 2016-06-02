package me.shib.java.lib.jtelebot.models.updates;

/**
 * This object represents an incoming update. Only one of the optional parameters can be present in any given update.
 */
public final class Update {

    private long update_id;
    private Message message;
    private Message edited_message;
    private InlineQuery inline_query;
    private ChosenInlineResult chosen_inline_result;
    private CallbackQuery callback_query;

    /**
     * @return The update‘s unique identifier. Update identifiers start from a certain positive number and increase sequentially.
     */
    public long getUpdate_id() {
        return update_id;
    }

    /**
     * @return New incoming message of any kind — text, photo, sticker, etc.
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @return New version of a message that is known to the bot and was edited
     */
    public Message getEdited_message() {
        return edited_message;
    }

    /**
     * @return New incoming inline query
     */
    public InlineQuery getInline_query() {
        return inline_query;
    }

    /**
     * @return The result of an inline query that was chosen by a user and sent to their chat partner.
     */
    public ChosenInlineResult getChosen_inline_result() {
        return chosen_inline_result;
    }

    /**
     * @return New incoming callback query
     */
    public CallbackQuery getCallback_query() {
        return callback_query;
    }
}
