package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents an audio file to be treated as music by the Telegram clients.
 */
public final class Audio {

    private String file_id;
    private int duration;
    private String performer;
    private String title;
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
     * @return Performer of the audio as defined by sender or by audio tags
     */
    public String getPerformer() {
        return performer;
    }

    /**
     * @return Title of the audio as defined by sender or by audio tags
     */
    public String getTitle() {
        return title;
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
