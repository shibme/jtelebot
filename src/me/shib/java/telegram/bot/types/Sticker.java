package me.shib.java.telegram.bot.types;

public class Sticker {
	
	private String file_id;
	private int width;
	private int height;
	private PhotoSize thumb;
	private long file_size;

	public String getFile_id() {
		return file_id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public PhotoSize getThumb() {
		return thumb;
	}

	public long getFile_size() {
		return file_size;
	}

	@Override
	public String toString() {
		return "Sticker [file_id=" + file_id + ", width=" + width + ", height=" + height + ", thumb=" + thumb
				+ ", file_size=" + file_size + "]";
	}
	
}
