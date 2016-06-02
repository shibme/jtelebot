package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a voice note.
 */
public final class Voice {

    private String file_id;
    private int duration;
    private String mime_type;
    private long file_size;

    /**
     * @return Unique identifier for this file
     */
    public String getFile_id() {
        return file_id;
    }

    /**
     * @return Duration of the audio in seconds as defined by sender
     */
    public int getDuration() {
        return duration;
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
