package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a voice message stored on the Telegram servers. By default, this voice message will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the voice message.
 */
public final class InlineQueryResultCachedVoice extends InlineQueryResult {

    private String voice_file_id;
    private String title;

    /**
     * Initializes a new InlineQueryResultCachedVoice object
     *
     * @param id            Unique identifier for this result, 1-64 bytes
     * @param voice_file_id A valid file identifier for the voice message
     * @param title         Voice message title
     */
    public InlineQueryResultCachedVoice(String id, String voice_file_id, String title) {
        super(id, InlineQueryResultType.voice);
        this.voice_file_id = voice_file_id;
        this.title = title;
    }
}
