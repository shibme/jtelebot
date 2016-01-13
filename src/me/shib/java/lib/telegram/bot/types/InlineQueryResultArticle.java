package me.shib.java.lib.telegram.bot.types;

public class InlineQueryResultArticle extends InlineQueryResult {

    private InlineQueryResultType type;
    private String title;
    private String message_text;
    private ParseMode parse_mode;
    private boolean disable_web_page_preview;
    private String url;
    private boolean hide_url;
    private String description;
    private String thumb_url;
    private int thumb_width;
    private int thumb_height;

    public InlineQueryResultArticle(String id, String title, String message_text) {
        super(id);
        type = InlineQueryResultType.article;
        this.title = title;
        this.message_text = message_text;
    }

    @Override
    public InlineQueryResultType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage_text() {
        return message_text;
    }

    public ParseMode getParse_mode() {
        return parse_mode;
    }

    public boolean isDisable_web_page_preview() {
        return disable_web_page_preview;
    }

    public String getUrl() {
        return url;
    }

    public boolean isHide_url() {
        return hide_url;
    }

    public String getDescription() {
        return description;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public int getThumb_width() {
        return thumb_width;
    }

    public int getThumb_height() {
        return thumb_height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public void setParse_mode(ParseMode parse_mode) {
        this.parse_mode = parse_mode;
    }

    public void disableWebPagePreview(boolean disable_web_page_preview) {
        this.disable_web_page_preview = disable_web_page_preview;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void hideUrl(boolean hide_url) {
        this.hide_url = hide_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }

}
