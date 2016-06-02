package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a venue.
 */
public final class Venue {

    private Location location;
    private String title;
    private String address;
    private String foursquare_id;

    /**
     * @return Venue location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Name of the venue
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Address of the venue
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return Foursquare identifier of the venue
     */
    public String getFoursquare_id() {
        return foursquare_id;
    }
}
