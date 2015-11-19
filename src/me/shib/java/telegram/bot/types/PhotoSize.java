package me.shib.java.telegram.bot.types;

public class PhotoSize {
	
	private String file_id;
	private int width;
	private int height;
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

	public long getFile_size() {
		return file_size;
	}

	@Override
	public String toString() {
		return "PhotoSize [file_id=" + file_id + ", width=" + width + ", height=" + height + ", file_size=" + file_size
				+ "]";
	}
	
}
