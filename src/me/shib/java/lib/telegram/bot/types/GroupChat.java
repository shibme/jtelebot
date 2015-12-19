package me.shib.java.lib.telegram.bot.types;

public class GroupChat {

    private long id;
    private String title;

    protected GroupChat(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "GroupChat [id=" + id + ", title=" + title + "]";
    }

}
