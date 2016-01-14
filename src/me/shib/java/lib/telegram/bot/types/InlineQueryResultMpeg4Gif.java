package me.shib.java.lib.telegram.bot.types;

public class InlineQueryResultMpeg4Gif extends InlineQueryResult {

    private String mpeg4_url;
    private String thumb_url;
    private int mpeg4_width;
    private int mpeg4_height;
    private String title;
    private String caption;
    private String message_text;
    private ParseMode parse_mode;
    private boolean disable_web_page_preview;

    public InlineQueryResultMpeg4Gif(String id, String mpeg4_url, String thumb_url) {
        super(id, InlineQueryResultType.mpeg4_gif);
        this.mpeg4_url = mpeg4_url;
        this.thumb_url = thumb_url;
    }

    public String getMpeg4_url() {
        return mpeg4_url;
    }

    public void setMpeg4_url(String mpeg4_url) {
        this.mpeg4_url = mpeg4_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public int getMpeg4_width() {
        return mpeg4_width;
    }

    public void setMpeg4_width(int mpeg4_width) {
        this.mpeg4_width = mpeg4_width;
    }

    public int getMpeg4_height() {
        return mpeg4_height;
    }

    public void setMpeg4_height(int mpeg4_height) {
        this.mpeg4_height = mpeg4_height;
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
