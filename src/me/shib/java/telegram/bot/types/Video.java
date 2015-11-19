package me.shib.java.telegram.bot.types;

public class Video {
	
	private String file_id;
	private int width;
	private int height;
	private int duration;
	private PhotoSize thumb;
	private String mime_type;
	private int file_size;
	private String caption;

	public String getFile_id() {
		return file_id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDuration() {
		return duration;
	}

	public PhotoSize getThumb() {
		return thumb;
	}

	public String getMime_type() {
		return mime_type;
	}

	public int getFile_size() {
		return file_size;
	}

	public String getCaption() {
		return caption;
	}

	@Override
	public String toString() {
		return "Video [file_id=" + file_id + ", width=" + width + ", height=" + height + ", duration=" + duration
				+ ", thumb=" + thumb + ", mime_type=" + mime_type + ", file_size=" + file_size + ", caption=" + caption
				+ "]";
	}
	
}
