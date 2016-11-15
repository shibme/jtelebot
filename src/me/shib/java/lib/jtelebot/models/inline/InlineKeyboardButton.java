package me.shib.java.lib.jtelebot.models.inline;

/**
 * This object represents one button of an inline keyboard. You must use exactly one of the optional fields.
 */
public final class InlineKeyboardButton {

    private String text;
    private String url;
    private String callback_data;
    private String switch_inline_query;
    private String switch_inline_query_current_chat;

    public enum InlineKeyboardButtonType {
        url, callback_data, switch_inline_query, switch_inline_query_current_chat
    }

    /**
     * Initializes a new ReplyKeyboardMarkup object
     *
     * @param text Label text on the button
     * @param buttonType The type of the Inline Keyboard Button based on what it has to do
     * @param buttonValue the value corresponding to the button type
     */
    public InlineKeyboardButton(String text, InlineKeyboardButtonType buttonType, String buttonValue) {
        this.text = text;
        switch (buttonType) {
            case url:
                this.url = buttonValue;
                break;
            case callback_data:
                this.callback_data = buttonValue;
                break;
            case switch_inline_query:
                this.switch_inline_query = buttonValue;
                break;
            case switch_inline_query_current_chat:
                this.switch_inline_query_current_chat = buttonValue;
                break;
        }
    }
}
