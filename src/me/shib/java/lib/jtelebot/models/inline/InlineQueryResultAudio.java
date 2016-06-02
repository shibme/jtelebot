package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to an mp3 audio file. By default, this audio file will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the audio.
 */
public final class InlineQueryResultAudio extends InlineQueryResult {

    private String audio_url;
    private String title;
    private String performer;
    private int audio_duration;

    /**
     * Initializes a new InlineQueryResultAudio object
     *
     * @param id        Unique identifier for this result, 1-64 bytes
     * @param audio_url A valid URL for the audio file
     * @param title     Title for the result
     */
    public InlineQueryResultAudio(String id, String audio_url, String title) {
        super(id, InlineQueryResultType.audio);
        this.audio_url = audio_url;
        this.title = title;
    }

    /**
     * @param performer Performer
     */
    public void setPerformer(String performer) {
        this.performer = performer;
    }

    /**
     * @param audio_duration Audio duration in seconds
     */
    public void setAudio_duration(int audio_duration) {
        this.audio_duration = audio_duration;
    }
}
