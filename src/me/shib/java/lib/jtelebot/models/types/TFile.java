package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a file ready to be downloaded. The file can be downloaded by calling downloadFile. It is guaranteed that the link will be valid for at least 1 hour. When the link expires, a new one can be requested by calling getFile.
 */
public final class TFile {
    private String file_id;
    private long file_size;
    private String file_path;

    /**
     * @return Unique identifier for this file
     */
    public String getFile_id() {
        return file_id;
    }

    /**
     * @return File size, if known
     */
    public long getFile_size() {
        return file_size;
    }

    /**
     * @return File path.
     */
    public String getFile_path() {
        return file_path;
    }

}
