package me.shib.java.lib.telegram.bot.types;

public class ReplyKeyboardHide implements ReplyMarkup {

    private final Boolean hide_keyboard = Boolean.TRUE;
    private boolean selective;

    public Boolean getHide_keyboard() {
        return hide_keyboard;
    }

    public boolean isSelective() {
        return selective;
    }

    @Override
    public String toString() {
        return "ReplyKeyboardHide [hide_keyboard=" + hide_keyboard + ", selective=" + selective + "]";
    }

}
