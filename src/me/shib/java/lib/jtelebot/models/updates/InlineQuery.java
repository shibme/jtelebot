package me.shib.java.lib.jtelebot.models.updates;

import me.shib.java.lib.jtelebot.models.types.Location;
import me.shib.java.lib.jtelebot.models.types.User;

/**
 * This object represents an incoming inline query. When the user sends an empty query, your bot could return some default or trending results.
 */
public final class InlineQuery {

    private String id;
    private User from;
    private Location location;
    private String query;
    private String offset;

    /**
     * @return Unique identifier for this query
     */
    public String getId() {
        return id;
    }

    /**
     * @return Sender
     */
    public User getFrom() {
        return from;
    }

    /**
     * @return Sender location, only for bots that request user location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Text of the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return Offset of the results to be returned, can be controlled by the bot
     */
    public String getOffset() {
        return offset;
    }
}
