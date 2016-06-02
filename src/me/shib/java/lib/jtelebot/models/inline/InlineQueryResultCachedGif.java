package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to an animated GIF file stored on the Telegram servers. By default, this animated GIF file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with specified content instead of the animation.
 */
public final class InlineQueryResultCachedGif extends InlineQueryResult {

    private String gif_file_id;
    private String title;
    private String caption;

    /**
     * Initializes a new InlineQueryResultCachedGif object
     *
     * @param id          Unique identifier for this result, 1-64 bytes
     * @param gif_file_id A valid file identifier for the GIF file
     */
    public InlineQueryResultCachedGif(String id, String gif_file_id) {
        super(id, InlineQueryResultType.gif);
        this.gif_file_id = gif_file_id;
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
