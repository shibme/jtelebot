package com.nudanam.java.telegram.bot.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.nudanam.java.rest.client.lib.JsonLib;
import com.nudanam.java.rest.client.lib.Parameter;
import com.nudanam.java.telegram.bot.types.Message;
import com.nudanam.java.telegram.bot.types.ParseMode;
import com.nudanam.java.telegram.bot.types.ReplyMarkup;
import com.nudanam.java.telegram.bot.types.TelegramFile;
import com.nudanam.java.telegram.bot.types.Update;
import com.nudanam.java.telegram.bot.types.User;
import com.nudanam.java.telegram.bot.types.UserProfilePhotos;

public class TelegramBotService {
	
	private static final String telegramBotServiceEndPoint = "https://api.telegram.org";
	private static final int longPollInterval = 3600;
	
	private String botApiToken;
	private long updateServiceOffset = 0;
	private BotServiceWrapper botServiceWrapper;
	
	public enum ChatAction {
		typing, upload_photo, record_video, upload_video, record_audio, upload_audio, upload_document, find_location;
	}
	
	public TelegramBotService(String botApiToken) {
		this.botApiToken = botApiToken;
		botServiceWrapper = new BotServiceWrapper(telegramBotServiceEndPoint + "/" + "bot" + botApiToken);
	}
	
	public User getMe() throws IOException {
		String methodName = "getMe";
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), User.class);
	}
	
	public Message sendMessage(long chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendMessage";
		ArrayList<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		params.add(new Parameter("text", text));
		if(parse_mode != ParseMode.None) {
			params.add(new Parameter("parse_mode", parse_mode.toString()));
		}
		if(disable_web_page_preview) {
			params.add(new Parameter("disable_web_page_preview", "" + disable_web_page_preview));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendMessage(long chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, long reply_to_message_id) throws IOException {
		return sendMessage(chat_id, text, parse_mode, disable_web_page_preview, reply_to_message_id, null);
	}
	
	public Message sendMessage(long chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview) throws IOException {
		return sendMessage(chat_id, text, parse_mode, disable_web_page_preview, 0);
	}
	
	public Message sendMessage(long chat_id, String text, ParseMode parse_mode) throws IOException {
		return sendMessage(chat_id, text, parse_mode, false);
	}
	
	public Message sendMessage(long chat_id, String text) throws IOException {
		return sendMessage(chat_id, text, null);
	}
	
	public Message forwardMessage(long chat_id, long from_chat_id, long message_id) throws IOException {
		String methodName = "forwardMessage";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		params.add(new Parameter("from_chat_id", "" + from_chat_id));
		params.add(new Parameter("message_id", "" + message_id));
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	private Message sendPhoto(long chat_id, String photoId, File photoFile, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendPhoto";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		if(null != photoId) {
			params.add(new Parameter("photo", photoId));
		}
		else {
			params.add(new Parameter("photo", photoFile));
		}
		if((null != caption) && (!caption.isEmpty())) {
			params.add(new Parameter("caption", caption));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendPhoto(long chat_id, String photo, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendPhoto(chat_id, photo, null, caption, reply_to_message_id, reply_markup);
	}
	
	public Message sendPhoto(long chat_id, File photo, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendPhoto(chat_id, null, photo, caption, reply_to_message_id, reply_markup);
	}
	
	private Message sendAudio(long chat_id, String audioId, File audioFile, int duration, String performer, String title, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendAudio";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		if(null != audioId) {
			params.add(new Parameter("audio", audioId));
		}
		else {
			params.add(new Parameter("audio", audioFile));
		}
		if(duration > 0) {
			params.add(new Parameter("duration", "" + duration));
		}
		if((null != performer) && (!performer.isEmpty())) {
			params.add(new Parameter("performer", performer));
		}
		if((null != title) && (!title.isEmpty())) {
			params.add(new Parameter("title", title));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendAudio(long chat_id, String audio, int duration, String performer, String title, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendAudio(chat_id, audio, null, duration, performer, title, reply_to_message_id, reply_markup);
	}
	
	public Message sendAudio(long chat_id, File audio, int duration, String performer, String title, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendAudio(chat_id, null, audio, duration, performer, title, reply_to_message_id, reply_markup);
	}
	
	private Message sendDocument(long chat_id, String documentId, File documentFile, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendDocument";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		if(null != documentId) {
			params.add(new Parameter("document", documentId));
		}
		else {
			params.add(new Parameter("document", documentFile));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendDocument(long chat_id, String document, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendDocument(chat_id, document, null, reply_to_message_id, reply_markup);
	}
	
	public Message sendDocument(long chat_id, File document, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendDocument(chat_id, null, document, reply_to_message_id, reply_markup);
	}
	
	private Message sendSticker(long chat_id, String stickerId, File stickerFile, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendSticker";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		if(null != stickerId) {
			params.add(new Parameter("sticker", stickerId));
		}
		else {
			params.add(new Parameter("sticker", stickerFile));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendSticker(long chat_id, String sticker, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendSticker(chat_id, sticker, null, reply_to_message_id, reply_markup);
	}
	
	public Message sendSticker(long chat_id, File sticker, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendSticker(chat_id, null, sticker, reply_to_message_id, reply_markup);
	}
	
	private Message sendVideo(long chat_id, String videoId, File videoFile, int duration, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendAudio";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		if(null != videoId) {
			params.add(new Parameter("video", videoId));
		}
		else {
			params.add(new Parameter("video", videoFile));
		}
		if(duration > 0) {
			params.add(new Parameter("duration", "" + duration));
		}
		if((null != caption) && (!caption.isEmpty())) {
			params.add(new Parameter("performer", caption));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendVideo(long chat_id, String video, int duration, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendVideo(chat_id, video, null, duration, caption, reply_to_message_id, reply_markup);
	}
	
	public Message sendVideo(long chat_id, File video, int duration, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendVideo(chat_id, null, video, duration, caption, reply_to_message_id, reply_markup);
	}
	
	private Message sendVoice(long chat_id, String voiceId, File voiceFile, int duration, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendAudio";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		if(null != voiceId) {
			params.add(new Parameter("voice", voiceId));
		}
		else {
			params.add(new Parameter("voice", voiceFile));
		}
		if(duration > 0) {
			params.add(new Parameter("duration", "" + duration));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendVoice(long chat_id, String voice, int duration, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendVoice(chat_id, voice, null, duration, reply_to_message_id, reply_markup);
	}
	
	public Message sendVoice(long chat_id, File voice, int duration, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		return sendVoice(chat_id, null, voice, duration, reply_to_message_id, reply_markup);
	}
	
	public Message sendLocation(long chat_id, float latitude, float longitude, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendLocation";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		params.add(new Parameter("latitude", "" + latitude));
		params.add(new Parameter("longitude", "" + longitude));
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendLocation(long chat_id, float latitude, float longitude, long reply_to_message_id) throws IOException {
		return sendLocation(chat_id, latitude, longitude, reply_to_message_id, null);
	}
	
	public Message sendLocation(long chat_id, float latitude, float longitude) throws IOException {
		return sendLocation(chat_id, latitude, longitude, 0);
	}
	
	public boolean sendChatAction(long chat_id, ChatAction action) throws IOException {
		String methodName = "sendChatAction";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		params.add(new Parameter("action", "" + action));
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return false;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), boolean.class);
	}
	
	public UserProfilePhotos getUserProfilePhotos(long user_id, int offset, int limit) throws IOException {
		String methodName = "getUserProfilePhotos";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("user_id", "" + user_id));
		if(offset > 0) {
			params.add(new Parameter("offset", "" + offset));
		}
		if(limit <= 100) {
			params.add(new Parameter("limit", "" + limit));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), UserProfilePhotos.class);
	}
	
	public UserProfilePhotos getUserProfilePhotos(long user_id, int offset) throws IOException {
		return getUserProfilePhotos(user_id, offset, 101);
	}
	
	public UserProfilePhotos getUserProfilePhotos(long user_id) throws IOException {
		return getUserProfilePhotos(user_id, 0);
	}
	
	public synchronized Update[] getUpdates(long offset, int limit, int timeout) throws IOException {
		String methodName = "getUpdates";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		if(offset > 0) {
			params.add(new Parameter("offset", "" + offset));
		}
		if(limit <= 100) {
			params.add(new Parameter("limit", "" + limit));
		}
		if(timeout > 0) {
			params.add(new Parameter("timeout", "" + timeout));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return new Update[0];
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Update[].class);
	}
	
	public synchronized Update[] getUpdates(long offset, int limit) throws IOException {
		return getUpdates(offset, limit, 3600);
	}
	
	public synchronized Update[] getUpdates(long offset) throws IOException {
		return getUpdates(offset, 100, 3600);
	}
	
	public synchronized Update[] getUpdates() throws IOException {
		Update[] updates = getUpdates(updateServiceOffset, 100, longPollInterval);
		if(updates.length > 0) {
			updateServiceOffset = updates[updates.length - 1].getUpdate_id() + 1;
		}
		return updates;
	}
	
	public TelegramFile getFile(String file_id) throws IOException {
		String methodName = "getFile";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("file_id", "" + file_id));
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), TelegramFile.class);
	}
	
	public File getDownloadedFile(String file_id, File downloadToFile, boolean showDownloadProgress) throws IOException {
		TelegramFile tFile = getFile(file_id);
		String downloadableURL = telegramBotServiceEndPoint + "/file/bot" + botApiToken + "/" + tFile.getFile_path();
		HTTPFileDownloader hfd = null;
		if(downloadToFile == null) {
			hfd = new HTTPFileDownloader(downloadableURL, "TelegramBotDownloads");
		}
		else {
			hfd = new HTTPFileDownloader(downloadableURL, downloadToFile);
		}
		hfd.start();
		int prevPercent = -1;
		while(hfd.isAlive()) {
			if(showDownloadProgress) {
				if(hfd.getCompletedPercentage() > prevPercent) {
					prevPercent = hfd.getCompletedPercentage();
					System.out.print(file_id + ": " + prevPercent + "% \r");
				}
			}
		}
		if(showDownloadProgress) {
			if(hfd.getFile() == null) {
				System.out.print(file_id + ": Download Failed.\n");
			}
			else {
				System.out.print(file_id + ": Download Complete.\n");
			}
		}
		return hfd.getFile();
	}
	
	public File getDownloadedFile(String file_id, File downloadToFile) throws IOException {
		return getDownloadedFile(file_id, downloadToFile, false);
	}
	
	public File getDownloadedFile(String file_id, boolean showDownloadProgress) throws IOException {
		return getDownloadedFile(file_id, null, showDownloadProgress);
	}
	
	public File getDownloadedFile(String file_id) throws IOException {
		return getDownloadedFile(file_id, null, false);
	}
	
}
