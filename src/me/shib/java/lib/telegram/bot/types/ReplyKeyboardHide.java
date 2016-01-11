package me.shib.java.lib.telegram.bot.types;

public class ReplyKeyboardHide implements ReplyMarkup {

    private final boolean hide_keyboard = true;
    private boolean selective;

    public boolean getHide_keyboard() {
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
