package me.shib.java.lib.telegram.bot.types;

public class Update {

    private long update_id;
    private Message message;
    private InlineQuery inline_query;
    private ChosenInlineResult chosen_inline_result;

    public long getUpdate_id() {
        return update_id;
    }

    public Message getMessage() {
        return message;
    }

    public InlineQuery getInline_query() {
        return inline_query;
    }

    public ChosenInlineResult getChosen_inline_result() {
        return chosen_inline_result;
    }

    @Override
    public String toString() {
        return "Update [update_id=" + update_id + ", message=" + message + ", inline_query=" + inline_query + ", chosen_inline_result=" + chosen_inline_result + "]";
    }

}
