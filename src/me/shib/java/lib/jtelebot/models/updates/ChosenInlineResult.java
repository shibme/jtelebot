package me.shib.java.lib.jtelebot.models.updates;

import me.shib.java.lib.jtelebot.models.types.Location;
import me.shib.java.lib.jtelebot.models.types.User;

/**
 * Represents a result of an inline query that was chosen by the user and sent to their chat partner.
 */
public final class ChosenInlineResult {

    private String result_id;
    private User from;
    private Location location;
    private String inline_message_id;

    private String query;

    /**
     * @return The unique identifier for the result that was chosen
     */
    public String getResult_id() {
        return result_id;
    }

    /**
     * @return The user that chose the result
     */
    public User getFrom() {
        return from;
    }

    /**
     * @return Sender location, only for bots that require user location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Identifier of the sent inline message. Available only if there is an inline keyboard attached to the message. Will be also received in callback queries and can be used to edit the message.
     */
    public String getInline_message_id() {
        return inline_message_id;
    }

    /**
     * @return The query that was used to obtain the result
     */
    public String getQuery() {
        return query;
    }

}
