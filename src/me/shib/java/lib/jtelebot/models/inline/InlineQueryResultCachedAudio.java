package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to an mp3 audio file stored on the Telegram servers. By default, this audio file will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the audio.
 */
public final class InlineQueryResultCachedAudio extends InlineQueryResult {

    private String audio_file_id;

    /**
     * Initializes a new InlineQueryResultCachedAudio object
     *
     * @param id            Unique identifier for this result, 1-64 bytes
     * @param audio_file_id A valid file identifier for the audio file
     */
    public InlineQueryResultCachedAudio(String id, String audio_file_id) {
        super(id, InlineQueryResultType.audio);
        this.audio_file_id = audio_file_id;
    }
}
