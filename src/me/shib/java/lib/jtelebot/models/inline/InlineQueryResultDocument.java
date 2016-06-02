package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a file. By default, this file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the file. Currently, only .PDF and .ZIP files can be sent using this method.
 */
public final class InlineQueryResultDocument extends InlineQueryResult {

    private String title;
    private String document_url;
    private String mime_type;
    private String caption;
    private String description;
    private String thumb_url;
    private int thumb_width;
    private int thumb_height;

    /**
     * Initializes a new InlineQueryResultDocument object
     *
     * @param id           Unique identifier for this result, 1-64 bytes
     * @param title        Title for the result
     * @param document_url A valid URL for the file
     * @param mime_type    Mime type of the content of the file, either “application/pdf” or “application/zip”
     */
    public InlineQueryResultDocument(String id, String title, String document_url, DocumentMimeType mime_type) {
        super(id, InlineQueryResultType.document);
        this.title = title;
        this.document_url = document_url;
        this.mime_type = mime_type.toMimeTypeString();
    }

    /**
     * @param caption Caption of the document to be sent, 0-200 characters
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @param description Short description of the result
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param thumb_url URL of the thumbnail (jpeg only) for the file
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

    /**
     * Available mime types for the content of the file
     */
    public enum DocumentMimeType {
        application_pdf("application/pdf"), application_zip("application/zip");

        private String mimeTypeString;

        DocumentMimeType(String mimeTypeString) {
            this.mimeTypeString = mimeTypeString;
        }

        private String toMimeTypeString() {
            return mimeTypeString;
        }
    }

}
