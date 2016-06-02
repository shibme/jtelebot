package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a sticker.
 */
public final class Sticker {

    private String file_id;
    private int width;
    private int height;
    private PhotoSize thumb;
    private String emoji;
    private long file_size;

    /**
     * @return Unique identifier for this file
     */
    public String getFile_id() {
        return file_id;
    }

    /**
     * @return Sticker width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Sticker height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return Sticker thumbnail in .webp or .jpg format
     */
    public PhotoSize getThumb() {
        return thumb;
    }

    /**
     * @return Emoji associated with the sticker
     */
    public String getEmoji() {
        return emoji;
    }

    /**
     * @return File size
     */
    public long getFile_size() {
        return file_size;
    }
}
