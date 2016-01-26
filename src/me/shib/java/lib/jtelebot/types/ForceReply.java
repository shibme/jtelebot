package me.shib.java.lib.jtelebot.types;

public class ForceReply implements ReplyMarkup {

    private final boolean force_reply = true;
    private boolean selective;

    public ForceReply(boolean selective) {
        this.selective = selective;
    }

    public ForceReply() {
        this.selective = false;
    }

    public boolean getForce_reply() {
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
