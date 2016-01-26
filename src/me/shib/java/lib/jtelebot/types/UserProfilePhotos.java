package me.shib.java.lib.jtelebot.types;

import java.util.Arrays;

public class UserProfilePhotos {

    private int total_count;
    private PhotoSize[][] photos;

    public int getTotal_count() {
        return total_count;
    }

    public PhotoSize[][] getPhotos() {
        return photos;
    }

    @Override
    public String toString() {
        return "UserProfilePhotos [total_count=" + total_count + ", photos=" + Arrays.toString(photos) + "]";
    }

}
