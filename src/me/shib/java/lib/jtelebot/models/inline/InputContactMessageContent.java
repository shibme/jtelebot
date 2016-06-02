package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents the content of a contact message to be sent as the result of an inline query.
 */
public final class InputContactMessageContent implements InputMessageContent {

    private String phone_number;
    private String first_name;
    private String last_name;

    /**
     * Initializes a new InputContactMessageContent object
     *
     * @param phone_number Contact's phone number
     * @param first_name   Contact's first name
     */
    public InputContactMessageContent(String phone_number, String first_name) {
        this.phone_number = phone_number;
        this.first_name = first_name;
    }

    /**
     * @param last_name Contact's last name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
