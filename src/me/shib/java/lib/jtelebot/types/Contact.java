package me.shib.java.lib.jtelebot.types;

public class Contact {

    private String phone_number;
    private String first_name;
    private String last_name;
    private String user_id;

    public String getPhone_number() {
        return phone_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "Contact [phone_number=" + phone_number + ", first_name=" + first_name + ", last_name=" + last_name
                + ", user_id=" + user_id + "]";
    }

}
