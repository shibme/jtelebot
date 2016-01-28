package me.shib.java.lib.jtelebot.types;

public class Chat {

    private String title;
    private String type;
    private long id;
    private String first_name;
    private String last_name;
    private String username;

    @Override
    public String toString() {
        if (getType() == ChatType.Private) {
            return getUser().toString();
        }
        return "Chat [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", username=" + username
                + ", title=" + title + ", type=" + type + "]";
    }

    public User getUser() {
        if (getType() == ChatType.Private) {
            return new User(id, first_name, last_name, username);
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

    public long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public enum ChatType {
        Private, Group, Supergroup, Channel
    }

}
