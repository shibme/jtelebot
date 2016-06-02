package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to an animated GIF file. By default, this animated GIF file will be sent by the user with optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the animation.
 */
public final class InlineQueryResultGif extends InlineQueryResult {

    private String gif_url;
    private String thumb_url;
    private int gif_width;
    private int gif_height;
    private String title;
    private String caption;

    /**
     * Initializes a new InlineQueryResultGif object
     *
     * @param id        Unique identifier for this result, 1-64 bytes
     * @param gif_url   A valid URL for the GIF file. File size must not exceed 1MB
     * @param thumb_url URL of the static thumbnail for the result (jpeg or gif)
     */
    public InlineQueryResultGif(String id, String gif_url, String thumb_url) {
        super(id, InlineQueryResultType.gif);
        this.gif_url = gif_url;
        this.thumb_url = thumb_url;
    }

    /**
     * @param gif_width Width of the GIF
     */
    public void setGif_width(int gif_width) {
        this.gif_width = gif_width;
    }

    /**
     * @param gif_height Height of the GIF
     */
    public void setGif_height(int gif_height) {
        this.gif_height = gif_height;
    }

    /**
     * @param title Title for the result
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param caption Caption of the GIF file to be sent, 0-200 characters
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
