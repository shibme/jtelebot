package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a voice recording in an .ogg container encoded with OPUS. By default, this voice recording will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the the voice message.
 */
public final class InlineQueryResultVoice extends InlineQueryResult {

    private String voice_url;
    private String title;
    private int voice_duration;

    /**
     * Initializes a new InlineQueryResultVoice object
     *
     * @param id        Unique identifier for this result, 1-64 bytes
     * @param voice_url A valid URL for the voice recording
     * @param title     Recording title
     */
    public InlineQueryResultVoice(String id, String voice_url, String title) {
        super(id, InlineQueryResultType.voice);
        this.voice_url = voice_url;
        this.title = title;
    }

    /**
     * @param voice_duration Recording duration in seconds
     */
    public void setVoice_duration(int voice_duration) {
        this.voice_duration = voice_duration;
    }
}
