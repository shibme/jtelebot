package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a video file.
 */
public final class Video {

    private String file_id;
    private int width;
    private int height;
    private int duration;
    private PhotoSize thumb;
    private String mime_type;
    private int file_size;
    private String caption;

    /**
     * @return Unique identifier for this file
     */
    public String getFile_id() {
        return file_id;
    }

    /**
     * @return Video width as defined by sender
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Video height as defined by sender
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return Duration of the video in seconds as defined by sender
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return Video thumbnail
     */
    public PhotoSize getThumb() {
        return thumb;
    }

    /**
     * @return Mime type of a file as defined by sender
     */
    public String getMime_type() {
        return mime_type;
    }

    /**
     * @return File size
     */
    public int getFile_size() {
        return file_size;
    }
}
