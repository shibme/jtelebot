package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a point on the map.
 */
public final class Location {

    private float longitude;
    private float latitude;

    /**
     * @return Longitude as defined by sender
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @return Latitude as defined by sender
     */
    public float getLatitude() {
        return latitude;
    }
}
