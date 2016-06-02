package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a page containing an embedded video player or a video file. By default, this video file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the video.
 */
public final class InlineQueryResultVideo extends InlineQueryResult {

    private String video_url;
    private String thumb_url;
    private String mime_type;
    private String title;
    private String caption;
    private int video_width;
    private int video_height;
    private int video_duration;
    private String description;

    /**
     * Initializes a new InlineQueryResultVideo object
     *
     * @param id        Unique identifier for this result, 1-64 bytes
     * @param video_url A valid URL for the embedded video player or video file
     * @param mime_type Mime type of the content of video url, “text/html” or “video/mp4”
     * @param thumb_url URL of the thumbnail (jpeg only) for the video
     * @param title     Title for the result
     */
    public InlineQueryResultVideo(String id, String video_url, VideoMimeType mime_type, String thumb_url, String title) {
        super(id, InlineQueryResultType.video);
        this.video_url = video_url;
        this.mime_type = mime_type.toMimeTypeString();
        this.thumb_url = thumb_url;
        this.title = title;
    }

    /**
     * @param caption Caption of the video to be sent, 0-200 characters
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @param video_width Video width
     */
    public void setVideo_width(int video_width) {
        this.video_width = video_width;
    }

    /**
     * @param video_height Video height
     */
    public void setVideo_height(int video_height) {
        this.video_height = video_height;
    }

    /**
     * @param video_duration Video duration in seconds
     */
    public void setVideo_duration(int video_duration) {
        this.video_duration = video_duration;
    }

    /**
     * @param description Short description of the result
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Available Mime types for the content of video url.
     */
    public enum VideoMimeType {
        text_html("text/html"), video_mp4("video/mp4");

        private String mimeTypeString;

        VideoMimeType(String mimeTypeString) {
            this.mimeTypeString = mimeTypeString;
        }

        private String toMimeTypeString() {
            return mimeTypeString;
        }
    }
}
