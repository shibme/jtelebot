package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a contact with a phone number. By default, this contact will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the contact.
 */
public final class InlineQueryResultContact extends InlineQueryResult {

    private String phone_number;
    private String first_name;
    private String last_name;
    private String thumb_url;
    private int thumb_width;
    private int thumb_height;

    /**
     * Initializes a new InlineQueryResultContact object
     *
     * @param id           Unique identifier for this result, 1-64 Bytes
     * @param phone_number Contact's phone number
     * @param first_name   Contact's first name
     */
    public InlineQueryResultContact(String id, String phone_number, String first_name) {
        super(id, InlineQueryResultType.contact);
        this.phone_number = phone_number;
        this.first_name = first_name;
    }

    /**
     * @param last_name Contact's last name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @param thumb_url Url of the thumbnail for the result
     */
    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    /**
     * @param thumb_width Thumbnail width
     */
    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    /**
     * @param thumb_height Thumbnail height
     */
    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }
}
