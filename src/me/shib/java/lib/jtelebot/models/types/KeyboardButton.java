package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents one button of the reply keyboard. Optional fields are mutually exclusive.
 */
public final class KeyboardButton {

    private String text;
    private boolean request_contact;
    private boolean request_location;

    public enum KeyboardButtonRequest {
        request_contact, request_location
    }

    /**
     * Initializes a new KeyboardButton object
     *
     * @param text Text of the button. If none of the optional fields are used, it will be sent to the bot as a message when the button is pressed
     */
    public KeyboardButton(String text) {
        this(text, null);
    }

    /**
     * Initializes a new KeyboardButton object
     *
     * @param text Text of the button. If none of the optional fields are used, it will be sent to the bot as a message when the button is pressed
     * @param keyboardButtonRequest the content that has to be sent when pressing the button - contact/location
     */
    public KeyboardButton(String text, KeyboardButtonRequest keyboardButtonRequest) {
        this.text = text;
        switch (keyboardButtonRequest) {
            case request_contact:
                this.request_contact = true;
                this.request_location = false;
                break;
            case request_location:
                this.request_contact = false;
                this.request_location = true;
                break;
            default:
                this.request_contact = false;
                this.request_location = false;
        }
    }
}
