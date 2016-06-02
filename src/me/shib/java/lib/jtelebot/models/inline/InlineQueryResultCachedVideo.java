package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a video file stored on the Telegram servers. By default, this video file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the video.
 */
public final class InlineQueryResultCachedVideo extends InlineQueryResult {

    private String video_file_id;
    private String title;
    private String description;
    private String caption;

    /**
     * Initializes a new InlineQueryResultCachedVideo object
     *
     * @param id            Unique identifier for this result, 1-64 bytes
     * @param video_file_id A valid file identifier for the video file
     * @param title         Title for the result
     */
    public InlineQueryResultCachedVideo(String id, String video_file_id, String title) {
        super(id, InlineQueryResultType.video);
        this.video_file_id = video_file_id;
        this.title = title;
    }

    /**
     * @param description Short description of the result
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param caption Caption of the video to be sent, 0-200 characters
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
