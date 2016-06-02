package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a photo stored on the Telegram servers. By default, this photo will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the photo.
 */
public final class InlineQueryResultCachedPhoto extends InlineQueryResult {

    private String photo_file_id;
    private String title;
    private String description;
    private String caption;

    /**
     * Initializes a new InlineQueryResultCachedPhoto object
     *
     * @param id            Unique identifier for this result, 1-64 bytes
     * @param photo_file_id A valid file identifier of the photo
     */
    public InlineQueryResultCachedPhoto(String id, String photo_file_id) {
        super(id, InlineQueryResultType.photo);
        this.photo_file_id = photo_file_id;
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
