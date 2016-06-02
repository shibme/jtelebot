package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a location on a map. By default, the location will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the location.
 */
public final class InlineQueryResultLocation extends InlineQueryResult {

    private float latitude;
    private float longitude;
    private String title;
    private String thumb_url;
    private int thumb_width;
    private int thumb_height;

    /**
     * Initializes a new InlineQueryResultLocation object
     *
     * @param id        Unique identifier for this result, 1-64 Bytes
     * @param latitude  Location latitude in degrees
     * @param longitude Location longitude in degrees
     * @param title     Location title
     */
    public InlineQueryResultLocation(String id, float latitude, float longitude, String title) {
        super(id, InlineQueryResultType.location);
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
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
