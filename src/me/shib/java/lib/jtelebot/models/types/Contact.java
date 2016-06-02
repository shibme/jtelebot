package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a phone contact.
 */
public final class Contact {

    private String phone_number;
    private String first_name;
    private String last_name;
    private String user_id;

    /**
     * @return Contact's phone number
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * @return Contact's first name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @return Contact's last name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @return Contact's user identifier in Telegram
     */
    public String getUser_id() {
        return user_id;
    }
}
