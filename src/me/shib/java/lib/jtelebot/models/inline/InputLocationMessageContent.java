package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents the content of a location message to be sent as the result of an inline query.
 */
public final class InputLocationMessageContent implements InputMessageContent {

    private float latitude;
    private float longitude;

    /**
     * Initializes a new InputContactMessageContent object
     *
     * @param latitude  Latitude of the location in degrees
     * @param longitude Longitude of the location in degrees
     */
    public InputLocationMessageContent(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
