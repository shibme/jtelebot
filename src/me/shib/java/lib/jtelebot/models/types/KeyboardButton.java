package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents one button of the reply keyboard. Optional fields are mutually exclusive.
 */
public final class KeyboardButton {

    private String text;
    private boolean request_contact;
    private boolean request_location;

    /**
     * Initializes a new KeyboardButton object
     *
     * @param text Text of the button. If none of the optional fields are used, it will be sent to the bot as a message when the button is pressed
     */
    public KeyboardButton(String text) {
        this.text = text;
        this.request_contact = false;
        this.request_location = false;
    }

    /**
     * If set, the user's phone number will be sent as a contact when the button is pressed. Available in private chats only
     */
    public void requestContact() {
        this.request_contact = true;
        this.request_location = false;
    }

    /**
     * If set, the user's current location will be sent when the button is pressed. Available in private chats only
     */
    public void requestLocation() {
        this.request_location = true;
        this.request_contact = false;
    }
}
