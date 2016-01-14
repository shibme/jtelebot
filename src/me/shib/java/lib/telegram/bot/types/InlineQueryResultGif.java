package me.shib.java.lib.telegram.bot.types;

public class InlineQueryResultGif extends InlineQueryResult {

    private String gif_url;
    private String thumb_url;
    private int gif_width;
    private int gif_height;
    private String title;
    private String caption;
    private String message_text;
    private ParseMode parse_mode;
    private boolean disable_web_page_preview;

    public InlineQueryResultGif(String id, String gif_url, String thumb_url) {
        super(id, InlineQueryResultType.gif);
        this.gif_url = gif_url;
        this.thumb_url = thumb_url;
    }

    public String getGif_url() {
        return gif_url;
    }

    public void setGif_url(String gif_url) {
        this.gif_url = gif_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public int getGif_width() {
        return gif_width;
    }

    public void setGif_width(int gif_width) {
        this.gif_width = gif_width;
    }

    public int getGif_height() {
        return gif_height;
    }

    public void setGif_height(int gif_height) {
        this.gif_height = gif_height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
