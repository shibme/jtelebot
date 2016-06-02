package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a photo. By default, this photo will be sent by the user with optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the photo.
 */
public final class InlineQueryResultPhoto extends InlineQueryResult {

    private String photo_url;
    private String thumb_url;
    private int photo_width;
    private int photo_height;
    private String title;
    private String description;
    private String caption;

    /**
     * Initializes a new InlineQueryResultPhoto object
     *
     * @param id        Unique identifier for this result, 1-64 bytes
     * @param photo_url A valid URL of the photo. Photo must be in jpeg format. Photo size must not exceed 5MB
     * @param thumb_url URL of the thumbnail for the photo
     */
    public InlineQueryResultPhoto(String id, String photo_url, String thumb_url) {
        super(id, InlineQueryResultType.photo);
        this.photo_url = photo_url;
        this.thumb_url = thumb_url;
    }

    /**
     * @param photo_width Width of the photo
     */
    public void setPhoto_width(int photo_width) {
        this.photo_width = photo_width;
    }

    /**
     * @param photo_height Height of the photo
     */
    public void setPhoto_height(int photo_height) {
        this.photo_height = photo_height;
    }

    /**
     * @param title Title for the result
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param description Short description of the result
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param caption Caption of the photo to be sent, 0-200 characters
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
