package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a custom keyboard with reply options.
 */
public final class ReplyKeyboardMarkup implements ReplyMarkup {

    private KeyboardButton[][] keyboard;
    private boolean resize_keyboard;
    private boolean one_time_keyboard;
    private boolean selective;

    /**
     * Initializes a new ReplyKeyboardMarkup object
     *
     * @param keyboard Array of button rows, each represented by an Array of KeyboardButton objects
     */
    public ReplyKeyboardMarkup(KeyboardButton[][] keyboard) {
        this(keyboard, false);
    }

    /**
     * Initializes a new ReplyKeyboardMarkup object
     *
     * @param keyboard        Array of button rows, each represented by an Array of KeyboardButton objects
     * @param resize_keyboard Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false, in which case the custom keyboard is always of the same height as the app's standard keyboard.
     */
    public ReplyKeyboardMarkup(KeyboardButton[][] keyboard, boolean resize_keyboard) {
        this(keyboard, resize_keyboard, true);
    }

    /**
     * Initializes a new ReplyKeyboardMarkup object
     *
     * @param keyboard          Array of button rows, each represented by an Array of KeyboardButton objects
     * @param resize_keyboard   Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false, in which case the custom keyboard is always of the same height as the app's standard keyboard.
     * @param one_time_keyboard Requests clients to hide the keyboard as soon as it's been used. The keyboard will still be available, but clients will automatically display the usual letter-keyboard in the chat – the user can press a special button in the input field to see the custom keyboard again. Defaults to false.
     */
    public ReplyKeyboardMarkup(KeyboardButton[][] keyboard, boolean resize_keyboard, boolean one_time_keyboard) {
        this(keyboard, resize_keyboard, one_time_keyboard, false);
    }

    /**
     * Initializes a new ReplyKeyboardMarkup object
     *
     * @param keyboard          Array of button rows, each represented by an Array of KeyboardButton objects
     * @param resize_keyboard   Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false, in which case the custom keyboard is always of the same height as the app's standard keyboard.
     * @param one_time_keyboard Requests clients to hide the keyboard as soon as it's been used. The keyboard will still be available, but clients will automatically display the usual letter-keyboard in the chat – the user can press a special button in the input field to see the custom keyboard again. Defaults to false.
     * @param selective         Use this parameter if you want to show the keyboard to specific users only. Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    public ReplyKeyboardMarkup(KeyboardButton[][] keyboard, boolean resize_keyboard, boolean one_time_keyboard, boolean selective) {
        this.keyboard = keyboard;
        this.resize_keyboard = resize_keyboard;
        this.one_time_keyboard = one_time_keyboard;
        this.selective = selective;
    }
}
