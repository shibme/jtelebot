package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represent a user's profile pictures.
 */
public final class UserProfilePhotos {

    private int total_count;
    private PhotoSize[][] photos;

    /**
     * @return Total number of profile pictures the target user has
     */
    public int getTotal_count() {
        return total_count;
    }

    /**
     * @return Requested profile pictures (in up to 4 sizes each)
     */
    public PhotoSize[][] getPhotos() {
        return photos;
    }
}
