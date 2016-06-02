package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a venue. By default, the venue will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the venue.
 */
public final class InlineQueryResultVenue extends InlineQueryResult {

    private float latitude;
    private float longitude;
    private String title;
    private String address;
    private String foursquare_id;
    private String thumb_url;
    private int thumb_width;
    private int thumb_height;

    /**
     * Initializes a new InlineQueryResultVenue object
     *
     * @param id        Unique identifier for this result, 1-64 Bytes
     * @param latitude  Latitude of the venue location in degrees
     * @param longitude Longitude of the venue location in degrees
     * @param title     Title of the venue
     * @param address   Address of the venue
     */
    public InlineQueryResultVenue(String id, float latitude, float longitude, String title, String address) {
        super(id, InlineQueryResultType.venue);
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.address = address;
    }

    /**
     * @param foursquare_id Foursquare identifier of the venue if known
     */
    public void setFoursquare_id(String foursquare_id) {
        this.foursquare_id = foursquare_id;
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
