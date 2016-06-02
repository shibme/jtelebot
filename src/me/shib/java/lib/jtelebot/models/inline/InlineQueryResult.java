package me.shib.java.lib.jtelebot.models.inline;

/**
 * This object represents one result of an inline query. Telegram clients currently support results of 19 different types.
 */
public abstract class InlineQueryResult {

    private String id;
    private InlineQueryResultType type;
    private InlineKeyboardMarkup reply_markup;
    InputMessageContent input_message_content;

    /**
     * Abstract constructor for a new InlineQueryResult type
     *
     * @param id   Unique identifier for this result, 1-64 Bytes
     * @param type Type of the result
     */
    public InlineQueryResult(String id, InlineQueryResultType type) {
        this.id = id;
        this.type = type;
    }

    /**
     * @param reply_markup Inline keyboard attached to the message
     */
    public void setReply_markup(InlineKeyboardMarkup reply_markup) {
        this.reply_markup = reply_markup;
    }

    /**
     * @param input_message_content Content of the message to be sent instead
     */
    public void setInput_message_content(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
    }

    /**
     * Type of the InlineQueryResult
     */
    public enum InlineQueryResultType {
        article, photo, gif, mpeg4_gif, video, audio, voice, document, location, venue, contact, sticker
    }

}