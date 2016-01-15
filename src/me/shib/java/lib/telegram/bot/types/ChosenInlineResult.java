package me.shib.java.lib.telegram.bot.types;

public class ChosenInlineResult {

    private String result_id;
    private User from;
    private String query;

    public String getResult_id() {
        return result_id;
    }

    public User getFrom() {
        return from;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "Update [result_id=" + result_id + ", from=" + from + ", query=" + query + "]";
    }

}
