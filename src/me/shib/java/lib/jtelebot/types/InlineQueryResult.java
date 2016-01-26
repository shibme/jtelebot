package me.shib.java.lib.jtelebot.types;

public abstract class InlineQueryResult {

    private String id;
    private InlineQueryResultType type;

    public InlineQueryResult(String id, InlineQueryResultType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultType getType() {
        return type;
    }

    public enum InlineQueryResultType {
        article, photo, gif, mpeg4_gif, video
    }

}