package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a video animation (H.264/MPEG-4 AVC video without sound). By default, this animated MPEG-4 file will be sent by the user with optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the animation.
 */
public final class InlineQueryResultMpeg4Gif extends InlineQueryResult {

    private String mpeg4_url;
    private String thumb_url;
    private int mpeg4_width;
    private int mpeg4_height;
    private String title;
    private String caption;

    /**
     * Initializes a new InlineQueryResultMpeg4Gif object
     *
     * @param id        Unique identifier for this result, 1-64 bytes
     * @param mpeg4_url A valid URL for the MP4 file. File size must not exceed 1MB
     * @param thumb_url URL of the static thumbnail (jpeg or gif) for the result
     */
    public InlineQueryResultMpeg4Gif(String id, String mpeg4_url, String thumb_url) {
        super(id, InlineQueryResultType.mpeg4_gif);
        this.mpeg4_url = mpeg4_url;
        this.thumb_url = thumb_url;
    }

    /**
     * @param mpeg4_width Video width
     */
    public void setMpeg4_width(int mpeg4_width) {
        this.mpeg4_width = mpeg4_width;
    }

    /**
     * @param mpeg4_height Video height
     */
    public void setMpeg4_height(int mpeg4_height) {
        this.mpeg4_height = mpeg4_height;
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
