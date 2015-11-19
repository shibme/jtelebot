package me.shib.java.telegram.bot.types;

public class Document {
	
	private String file_id;
	private PhotoSize thumb;
	private long duration;
	private String mime_type;
	private long file_size;

	public String getFile_id() {
		return file_id;
	}

	public PhotoSize getThumb() {
		return thumb;
	}

	public long getDuration() {
		return duration;
	}

	public String getMime_type() {
		return mime_type;
	}

	public long getFile_size() {
		return file_size;
	}

	@Override
	public String toString() {
		return "Document [file_id=" + file_id + ", thumb=" + thumb + ", duration=" + duration + ", mime_type="
				+ mime_type + ", file_size=" + file_size + "]";
	}
	
}
