package me.shib.java.lib.jtelebot.models.types;

import java.io.File;

/**
 * This object represents the contents of a file to be uploaded. Must be posted using multipart/form-data in the usual way that files are uploaded via the browser.
 */
public final class InputFile {

    private String file_id;
    private File file;

    /**
     * Initializes a new InputFile object
     *
     * @param file_id Unique identifier for this file
     */
    public InputFile(String file_id) {
        this.file_id = file_id;
    }

    /**
     * Initializes a new InputFile object
     *
     * @param file File to be uploaded
     */
    public InputFile(File file) {
        this.file = file;
    }

    /**
     * @return Unique identifier for this file
     */
    public String getFile_id() {
        return file_id;
    }

    /**
     * @return File to be uploaded
     */
    public File getFile() {
        return file;
    }
}
