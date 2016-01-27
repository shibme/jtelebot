package me.shib.java.lib.jtelebot.types;

public abstract class InlineQuery {

    private String id;
    private User from;
    private String query;
    private String offset;

    public String getId() {
        return id;
    }

    public User getFrom() {
        return from;
    }

    public String getQuery() {
        return query;
    }

    public String getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "InlineQuery [id=" + id + ", from=" + from + ", query=" + query + ", offset=" + offset + "]";
    }

}
