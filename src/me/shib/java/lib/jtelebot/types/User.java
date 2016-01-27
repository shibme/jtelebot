package me.shib.java.lib.jtelebot.types;

public class User {

    protected long id;
    protected String first_name;
    protected String last_name;
    protected String username;

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
