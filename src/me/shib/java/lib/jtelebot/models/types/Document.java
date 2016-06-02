package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a general file (as opposed to photos, voice messages and audio files).
 */
public final class Document {

    private String file_id;
    private PhotoSize thumb;
    private String file_name;
    private String mime_type;
    private long file_size;

    /**
     * @return Unique file identifier
     */
    public String getFile_id() {
        return file_id;
    }

    /**
     * @return Document thumbnail as defined by sender
     */
    public PhotoSize getThumb() {
        return thumb;
    }

    /**
     * @return Original filename as defined by sender
     */
    public String getFile_name() {
        return file_name;
    }

    /**
     * @return MIME type of the file as defined by sender
     */
    public String getMime_type() {
        return mime_type;
    }

    /**
     * @return File size
     */
    public long getFile_size() {
        return file_size;
    }
}
