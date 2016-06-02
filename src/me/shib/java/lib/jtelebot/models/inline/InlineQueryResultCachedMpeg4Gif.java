package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a video animation (H.264/MPEG-4 AVC video without sound) stored on the Telegram servers. By default, this animated MPEG-4 file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the animation.
 */
public final class InlineQueryResultCachedMpeg4Gif extends InlineQueryResult {

    private String mpeg4_file_id;
    private String title;
    private String caption;

    /**
     * Initializes a new InlineQueryResultCachedMpeg4Gif object
     *
     * @param id            Unique identifier for this result, 1-64 bytes
     * @param mpeg4_file_id A valid file identifier for the MP4 file
     */
    public InlineQueryResultCachedMpeg4Gif(String id, String mpeg4_file_id) {
        super(id, InlineQueryResultType.mpeg4_gif);
        this.mpeg4_file_id = mpeg4_file_id;
    }

    /**
     * @param title Title for the result
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param caption Caption of the MPEG-4 file to be sent, 0-200 characters
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
