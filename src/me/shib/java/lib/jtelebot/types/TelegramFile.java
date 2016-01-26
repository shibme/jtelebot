package me.shib.java.lib.jtelebot.types;

import java.io.File;

public class TelegramFile {

    private String file_id;
    private File file;
    private long file_size;
    private String file_path;

    /**
     * Initialize a Telegram file with an existing file_id.
     *
     * @param file_id the file_id of the file that was already uploaded to telegram servers.
     */
    public TelegramFile(String file_id) {
        this.file_id = file_id;
    }

    /**
     * Initialize a Telegram file object with a local file for uploading it to telegram servers.
     *
     * @param file the File object of a local file that has to be uploaded to the telegram servers.
     */
    public TelegramFile(File file) {
        this.file = file;
    }

    public String getFile_id() {
        return file_id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public long getFile_size() {
        return file_size;
    }

    public String getFile_path() {
        return file_path;
    }

    @Override
    public String toString() {
        return "TelegramFile [file_id=" + file_id + ", file=" + file + ", file_size=" + file_size + ", file_path="
                + file_path + "]";
    }

}
