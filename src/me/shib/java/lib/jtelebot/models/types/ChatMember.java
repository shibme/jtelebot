package me.shib.java.lib.jtelebot.models.types;

/**
 * This object contains information about one member of the chat.
 */
public class ChatMember {

    private User user;
    private String status;

    /**
     * @return Information about the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return The member's status in the chat. Can be “creator”, “administrator”, “member”, “left” or “kicked”
     */
    public String getStatus() {
        return status;
    }

    public enum MemberStatus {
        creator, administrator, member, left, kicked
    }

}
