package me.shib.java.lib.jtelebot.types;

public class InlineQueryResultVideo extends InlineQueryResult {
    private String video_url;
    private String thumb_url;
    private String mime_type;
    private String message_text;
    private String title;
    private String description;
    private int mpeg4_width;
    private int mpeg4_height;
    private int mpeg4_duration;
    private ParseMode parse_mode;
    private boolean disable_web_page_preview;

    public InlineQueryResultVideo(String id, String video_url, String thumb_url, String mime_type, String message_text, String title) {
        super(id, InlineQueryResultType.video);
        this.video_url = video_url;
        this.thumb_url = thumb_url;
        this.mime_type = mime_type;
        this.message_text = message_text;
        this.title = title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
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

    public int getMpeg4_duration() {
        return mpeg4_duration;
    }

    public void setMpeg4_duration(int mpeg4_duration) {
        this.mpeg4_duration = mpeg4_duration;
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

    public void setDisable_web_page_preview(boolean disable_web_page_preview) {
        this.disable_web_page_preview = disable_web_page_preview;
    }

}
