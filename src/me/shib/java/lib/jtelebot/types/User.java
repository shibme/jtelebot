package me.shib.java.lib.jtelebot.types;

public class User {

    private long id;
    private String first_name;
    private String last_name;
    private String username;

    protected User(long id, String first_name, String last_name, String username) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
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

    @Override
    public String toString() {
        return "User [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", username=" + username
                + "]";
    }

}
