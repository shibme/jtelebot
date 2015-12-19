package me.shib.java.lib.telegram.bot.types;

public class ForceReply implements ReplyMarkup {

    private final Boolean force_reply = Boolean.TRUE;
    private boolean selective;

    public ForceReply(boolean selective) {
        this.selective = selective;
    }

    public ForceReply() {
        this.selective = false;
    }

    public Boolean getForce_reply() {
        return force_reply;
    }

    public boolean isSelective() {
        return selective;
    }

    @Override
    public String toString() {
        return "ForceReply [force_reply=" + force_reply + ", selective=" + selective + "]";
    }

}
