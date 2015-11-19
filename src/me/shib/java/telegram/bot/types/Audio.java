package me.shib.java.telegram.bot.types;

public class Audio {
	
	private String file_id;
	private int duration;
	private String performer;
	private String title;
	private String mime_type;
	private long file_size;

	public String getFile_id() {
		return file_id;
	}

	public int getDuration() {
		return duration;
	}

	public String getPerformer() {
		return performer;
	}

	public String getTitle() {
		return title;
	}

	public String getMime_type() {
		return mime_type;
	}

	public long getFile_size() {
		return file_size;
	}

	@Override
	public String toString() {
		return "Audio [file_id=" + file_id + ", duration=" + duration + ", performer=" + performer + ", title=" + title
				+ ", mime_type=" + mime_type + ", file_size=" + file_size + "]";
	}
	
}
