package me.shib.java.lib.jtelebot.models.types;

/**
 * This object represents a Telegram user or bot.
 */
public final class User {

    private long id;
    private String first_name;
    private String last_name;
    private String username;

    /**
     * @return Unique identifier for this user or bot
     */
    public long getId() {
        return id;
    }

    /**
     * @return User‘s or bot’s first name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @return User‘s or bot’s last name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @return User‘s or bot’s username
     */
    public String getUsername() {
        return username;
    }
}
