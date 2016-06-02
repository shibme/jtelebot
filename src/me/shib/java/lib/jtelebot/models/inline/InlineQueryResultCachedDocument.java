package me.shib.java.lib.jtelebot.models.inline;

/**
 * Represents a link to a file stored on the Telegram servers. By default, this file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the file. Currently, only pdf-files and zip archives can be sent using this method.
 */
public final class InlineQueryResultCachedDocument extends InlineQueryResult {

    private String title;
    private String document_file_id;
    private String description;
    private String caption;

    /**
     * Initializes a new InlineQueryResultCachedDocument object
     *
     * @param id               Unique identifier for this result, 1-64 bytes
     * @param title            Title for the result
     * @param document_file_id A valid file identifier for the file
     */
    public InlineQueryResultCachedDocument(String id, String title, String document_file_id) {
        super(id, InlineQueryResultType.document);
        this.title = title;
        this.document_file_id = document_file_id;
    }

    /**
     * @param description Short description of the result
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param caption Caption of the document to be sent, 0-200 characters
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
