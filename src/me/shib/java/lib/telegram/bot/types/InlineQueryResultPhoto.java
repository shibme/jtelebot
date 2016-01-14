package me.shib.java.lib.telegram.bot.types;

public class InlineQueryResultPhoto extends InlineQueryResult {

    private String photo_url;
    private String thumb_url;
    private int photo_width;
    private int getPhoto_height;
    private String title;
    private String description;
    private String caption;
    private String message_text;
    private ParseMode parse_mode;
    private boolean disable_web_page_preview;

    public InlineQueryResultPhoto(String id, String photo_url, String thumb_url) {
        super(id, InlineQueryResultType.photo);
        this.photo_url = photo_url;
        this.thumb_url = thumb_url;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public int getPhoto_width() {
        return photo_width;
    }

    public void setPhoto_width(int photo_width) {
        this.photo_width = photo_width;
    }

    public int getGetPhoto_height() {
        return getPhoto_height;
    }

    public void setGetPhoto_height(int getPhoto_height) {
        this.getPhoto_height = getPhoto_height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public ParseMode getParse_mode() {
        return parse_mode;
    }

    public void setParse_mode(ParseMode parse_mode) {
        this.parse_mode = parse_mode;
    }

    public boolean isDisable_web_page_preview() {
        return disable_web_page_preview;
    }

    public void disableWebPagePreview(boolean disable_web_page_preview) {
        this.disable_web_page_preview = disable_web_page_preview;
    }

}
