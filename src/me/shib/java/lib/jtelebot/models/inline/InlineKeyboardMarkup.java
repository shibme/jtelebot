package me.shib.java.lib.jtelebot.models.inline;

import me.shib.java.lib.jtelebot.models.types.ReplyMarkup;

/**
 * This object represents an inline keyboard that appears right next to the message it belongs to.
 */
public final class InlineKeyboardMarkup implements ReplyMarkup {

    private InlineKeyboardButton[][] inline_keyboard;

    /**
     * Initializes a new InlineKeyboardMarkup object with an Array of Array of InlineKeyboardButton
     *
     * @param inline_keyboard Array of button rows, each represented by an Array of InlineKeyboardButton objects
     */
    public InlineKeyboardMarkup(InlineKeyboardButton[][] inline_keyboard) {
        this.inline_keyboard = inline_keyboard;
    }
}
