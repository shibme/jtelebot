package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to an article or web page.
 */
public final class InlineQueryResultArticle extends InlineQueryResult {

    private String title;
    private String url;
    private boolean hide_url;
    private String description;
    private String thumb_url;
    private int thumb_width;
    private int thumb_height;

    /**
     * Initializes a new InlineQueryResultArticle object
     *
     * @param id                    Unique identifier for this result, 1-64 Bytes
     * @param title                 Title of the result
     * @param input_message_content Content of the message to be sent
     */
    public InlineQueryResultArticle(String id, String title, InputMessageContent input_message_content) {
        super(id, InlineQueryResultType.article);
        this.title = title;
        this.input_message_content = input_message_content;
    }

    /**
     * @param url URL of the result
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Call this if you don't want the URL to be shown in the message
     */
    public void hideUrl() {
        this.hide_url = true;
    }

    /**
     * @param description Short description of the result
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param thumb_url Url of the thumbnail for the result
     */
    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    /**
     * @param thumb_width Thumbnail width
     */
    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    /**
     * @param thumb_height Thumbnail height
     */
    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }
}
