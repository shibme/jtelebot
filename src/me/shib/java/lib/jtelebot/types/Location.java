package me.shib.java.lib.jtelebot.types;

public class Location {

    private float longitude;
    private float latitude;

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Location [longitude=" + longitude + ", latitude=" + latitude + "]";
    }

}
