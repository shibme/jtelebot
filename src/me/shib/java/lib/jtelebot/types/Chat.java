package me.shib.java.lib.jtelebot.types;

public class Chat extends User {

    private String title;
    private String type;

    @Override
    public String toString() {
        return "User [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", username=" + username
                + ", title=" + title + ", type=" + type + "]";
    }

    public User getUser() {
        if (getType() == ChatType.Private) {
            return this;
        }
        return null;
    }

    public ChatType getType() {
        String expectedType = type.toLowerCase().substring(0, 1).toUpperCase() + type.toLowerCase().substring(1);
        return ChatType.valueOf(expectedType);
    }

    public String getTitle() {
        return title;
    }

    public enum ChatType {
        Private, Group, Supergroup, Channel
    }

}
