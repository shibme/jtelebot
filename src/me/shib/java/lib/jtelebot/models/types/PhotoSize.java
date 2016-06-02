package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents one size of a photo or a file / sticker thumbnail.
 */
public final class PhotoSize {

    private String file_id;
    private int width;
    private int height;
    private long file_size;

    /**
     * @return Unique identifier for this file
     */
    public String getFile_id() {
        return file_id;
    }

    /**
     * @return Photo width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Photo height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return File size
     */
    public long getFile_size() {
        return file_size;
    }
}
