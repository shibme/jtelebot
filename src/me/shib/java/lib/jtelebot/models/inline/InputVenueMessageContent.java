package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents the content of a venue message to be sent as the result of an inline query.
 */
public final class InputVenueMessageContent implements InputMessageContent {

    private float latitude;
    private float longitude;
    private String title;
    private String address;
    private String foursquare_id;

    /**
     * Initializes a new InputVenueMessageContent object
     *
     * @param latitude  Latitude of the venue in degrees
     * @param longitude Longitude of the venue in degrees
     * @param title     Name of the venue
     * @param address   Address of the venue
     */
    public InputVenueMessageContent(float latitude, float longitude, String title, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.address = address;
    }

    /**
     * @param foursquare_id Foursquare identifier of the venue, if known
     */
    public void setFoursquare_id(String foursquare_id) {
        this.foursquare_id = foursquare_id;
    }
}
