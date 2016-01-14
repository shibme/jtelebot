package me.shib.java.lib.telegram.bot.types;

public class User {

    private long id;
    private String first_name;
    private String last_name;
    private String username;

    protected User(long id, String first_name) {
        this.id = id;
        this.first_name = first_name;
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

    protected void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", username=" + username
                + "]";
    }

}
