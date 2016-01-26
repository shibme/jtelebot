package me.shib.java.lib.jtelebot.types;

public class Voice {

    private String file_id;
    private int duration;
    private String mime_type;
    private long file_size;

    public String getFile_id() {
        return file_id;
    }

    public int getDuration() {
        return duration;
    }

    public String getMime_type() {
        return mime_type;
    }

    public long getFile_size() {
        return file_size;
    }

    @Override
    public String toString() {
        return "Voice [file_id=" + file_id + ", duration=" + duration + ", mime_type=" + mime_type + ", file_size="
                + file_size + "]";
    }

}
