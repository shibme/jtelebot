package me.shib.java.lib.jtelebot.models.inline;

import me.shib.java.lib.jtelebot.models.types.ParseMode;

/**
 * Represents the content of a text message to be sent as the result of an inline query.
 */
public final class InputTextMessageContent implements InputMessageContent {

    private String message_text;
    private ParseMode parse_mode;
    private boolean disable_web_page_preview;

    /**
     * Initializes a new InputTextMessageContent object
     *
     * @param message_text Text of the message to be sent, 1-4096 characters
     */
    public InputTextMessageContent(String message_text) {
        this.message_text = message_text;
    }

    /**
     * @param parse_mode Set Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
     */
    public void setParse_mode(ParseMode parse_mode) {
        this.parse_mode = parse_mode;
    }

    /**
     * Disables link previews for links in the sent message
     */
    public void disableWebPagePreview() {
        this.disable_web_page_preview = true;
    }
}
