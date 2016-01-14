package me.shib.java.lib.telegram.bot.types;

public class Chat {

    private long id;
    private String type;
    private String title;
    private String first_name;
    private String last_name;
    private String username;

    public long getId() {
        return id;
    }

    public User getUser() {
        if (getType() == ChatType.Group) {
            return null;
        }
        User user = new User(this.id, this.first_name);
        user.setLast_name(this.last_name);
        user.setUsername(this.username);
        return user;
    }

    public GroupChat getGroup() {
        if (getType() == ChatType.Private) {
            return null;
        }
        return new GroupChat(this.id, this.title);
    }

    public String toString() {
        if (getType() == ChatType.Private) {
            return getUser().toString();
        }
        return getGroup().toString();
    }

    public ChatType getType() {
        String expectedType = type.toLowerCase().substring(0, 1).toUpperCase() + type.toLowerCase().substring(1);
        return ChatType.valueOf(expectedType);
    }

    public enum ChatType {
        Private, Group, Supergroup, Channel
    }

}
