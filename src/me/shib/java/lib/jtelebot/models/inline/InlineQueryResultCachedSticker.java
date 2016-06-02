package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a sticker stored on the Telegram servers. By default, this sticker will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the sticker.
 */
public final class InlineQueryResultCachedSticker extends InlineQueryResult {

    private String sticker_file_id;

    /**
     * Initializes a new InlineQueryResultCachedSticker object
     *
     * @param id              Unique identifier for this result, 1-64 bytes
     * @param sticker_file_id A valid file identifier of the sticker
     */
    public InlineQueryResultCachedSticker(String id, String sticker_file_id) {
        super(id, InlineQueryResultType.sticker);
        this.sticker_file_id = sticker_file_id;
    }
}
