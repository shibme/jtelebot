package me.shib.java.lib.jtelebot.models.inline;

/**
 * This object represents one button of an inline keyboard. You must use exactly one of the optional fields.
 */
public final class InlineKeyboardButton {

    private String text;
    private String url;
    private String callback_data;
    private String switch_inline_query;

    /**
     * Initializes a new ReplyKeyboardMarkup object
     *
     * @param text Label text on the button
     */
    public InlineKeyboardButton(String text) {
        this.text = text;
    }

    /**
     * Sets the HTTP url to be opened when button is pressed
     *
     * @param url HTTP url to be opened when button is pressed
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets the Data to be sent in a callback query to the bot when button is pressed
     *
     * @param callback_data Data to be sent in a callback query to the bot when button is pressed
     */
    public void setCallback_data(String callback_data) {
        this.callback_data = callback_data;
    }

    /**
     * If set, pressing the button will prompt the user to select one of their chats, open that chat and insert the bot‘s username and the specified inline query in the input field. Can be empty, in which case just the bot’s username will be inserted.
     *
     * @param switch_inline_query If set, pressing the button will prompt the user to select one of their chats, open that chat and insert the bot‘s username and the specified inline query in the input field. Can be empty, in which case just the bot’s username will be inserted.
     */
    public void setSwitch_inline_query(String switch_inline_query) {
        this.switch_inline_query = switch_inline_query;
    }
}
