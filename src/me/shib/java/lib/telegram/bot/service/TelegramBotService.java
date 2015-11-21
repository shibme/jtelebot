package me.shib.java.lib.telegram.bot.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.shib.java.lib.rest.client.JsonLib;
import me.shib.java.lib.rest.client.Parameter;
import me.shib.java.lib.telegram.bot.types.ChatId;
import me.shib.java.lib.telegram.bot.types.Message;
import me.shib.java.lib.telegram.bot.types.ParseMode;
import me.shib.java.lib.telegram.bot.types.ReplyMarkup;
import me.shib.java.lib.telegram.bot.types.TelegramFile;
import me.shib.java.lib.telegram.bot.types.Update;
import me.shib.java.lib.telegram.bot.types.User;
import me.shib.java.lib.telegram.bot.types.UserProfilePhotos;

public class TelegramBotService {
	
	private static final String telegramBotServiceEndPoint = "https://api.telegram.org";
	private static final int longPollInterval = 3600;
	
	private String botApiToken;
	private long updateServiceOffset = 0;
	private JsonLib jsonLib;
	private BotServiceWrapper botServiceWrapper;
	
	public enum ChatAction {
		typing, upload_photo, record_video, upload_video, record_audio, upload_audio, upload_document, find_location;
	}
	
	public TelegramBotService(String botApiToken) {
		this.botApiToken = botApiToken;
		jsonLib = new JsonLib();
		botServiceWrapper = new BotServiceWrapper(telegramBotServiceEndPoint + "/" + "bot" + botApiToken, jsonLib);
	}
	
	public User getMe() throws IOException {
		String methodName = "getMe";
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.get(methodName);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), User.class);
	}
	
	public Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendMessage";
		ArrayList<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		params.add(new Parameter("text", text));
		if((parse_mode != null) && (parse_mode != ParseMode.None)) {
			params.add(new Parameter("parse_mode", parse_mode.toString()));
		}
		if(disable_web_page_preview) {
			params.add(new Parameter("disable_web_page_preview", "" + disable_web_page_preview));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, long reply_to_message_id) throws IOException {
		return sendMessage(chat_id, text, parse_mode, disable_web_page_preview, reply_to_message_id, null);
	}
	
	public Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview) throws IOException {
		return sendMessage(chat_id, text, parse_mode, disable_web_page_preview, 0);
	}
	
	public Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode) throws IOException {
		return sendMessage(chat_id, text, parse_mode, false);
	}
	
	public Message sendMessage(ChatId chat_id, String text) throws IOException {
		return sendMessage(chat_id, text, ParseMode.None);
	}
	
	public Message forwardMessage(ChatId chat_id, ChatId from_chat_id, long message_id) throws IOException {
		String methodName = "forwardMessage";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		params.add(new Parameter("from_chat_id", from_chat_id.getChatId()));
		params.add(new Parameter("message_id", "" + message_id));
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendPhoto(ChatId chat_id, TelegramFile photo, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendPhoto";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		if(null != photo.getFile_id()) {
			params.add(new Parameter("photo", photo.getFile_id()));
		}
		else {
			params.add(new Parameter("photo", photo.getFile()));
		}
		if((null != caption) && (!caption.isEmpty())) {
			params.add(new Parameter("caption", caption));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendPhoto(ChatId chat_id, TelegramFile photo, String caption, long reply_to_message_id) throws IOException {
		return sendPhoto(chat_id, photo, caption, reply_to_message_id, null);
	}
	
	public Message sendPhoto(ChatId chat_id, TelegramFile photo, String caption) throws IOException {
		return sendPhoto(chat_id, photo, caption, 0);
	}
	
	public Message sendPhoto(ChatId chat_id, TelegramFile photo) throws IOException {
		return sendPhoto(chat_id, photo, null);
	}
	
	public Message sendAudio(ChatId chat_id, TelegramFile audio, int duration, String performer, String title, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendAudio";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		if(null != audio.getFile_id()) {
			params.add(new Parameter("audio", audio.getFile_id()));
		}
		else {
			params.add(new Parameter("audio", audio.getFile()));
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
			params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendAudio(ChatId chat_id, TelegramFile audio, int duration, String performer, String title, long reply_to_message_id) throws IOException {
		return sendAudio(chat_id, audio, duration, performer, title, reply_to_message_id, null);
	}
	
	public Message sendAudio(ChatId chat_id, TelegramFile audio, int duration, String performer, String title) throws IOException {
		return sendAudio(chat_id, audio, duration, performer, title, 0);
	}
	
	public Message sendAudio(ChatId chat_id, TelegramFile audio, int duration, String performer) throws IOException {
		return sendAudio(chat_id, audio, duration, performer, null);
	}
	
	public Message sendAudio(ChatId chat_id, TelegramFile audio, int duration) throws IOException {
		return sendAudio(chat_id, audio, duration, null);
	}
	
	public Message sendAudio(ChatId chat_id, TelegramFile audio) throws IOException {
		return sendAudio(chat_id, audio, 0);
	}
	
	public Message sendDocument(ChatId chat_id, TelegramFile document, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendDocument";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		if(null != document.getFile_id()) {
			params.add(new Parameter("document", document.getFile_id()));
		}
		else {
			params.add(new Parameter("document", document.getFile()));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendDocument(ChatId chat_id, TelegramFile document, long reply_to_message_id) throws IOException {
		return sendDocument(chat_id, document, reply_to_message_id, null);
	}
	
	public Message sendDocument(ChatId chat_id, TelegramFile document) throws IOException {
		return sendDocument(chat_id, document, 0);
	}
	
	public Message sendSticker(ChatId chat_id, TelegramFile sticker, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendSticker";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		if(null != sticker.getFile_id()) {
			params.add(new Parameter("sticker", sticker.getFile_id()));
		}
		else {
			params.add(new Parameter("sticker", sticker.getFile()));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendSticker(ChatId chat_id, TelegramFile sticker, long reply_to_message_id) throws IOException {
		return sendSticker(chat_id, sticker, reply_to_message_id, null);
	}
	
	public Message sendSticker(ChatId chat_id, TelegramFile sticker) throws IOException {
		return sendSticker(chat_id, sticker, 0);
	}
	
	public Message sendVideo(ChatId chat_id, TelegramFile video, int duration, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendVideo";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		if(null != video.getFile_id()) {
			params.add(new Parameter("video", video.getFile_id()));
		}
		else {
			params.add(new Parameter("video", video.getFile()));
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
			params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendVideo(ChatId chat_id, TelegramFile video, int duration, String caption, long reply_to_message_id) throws IOException {
		return sendVideo(chat_id, video, duration, caption, reply_to_message_id, null);
	}
	
	public Message sendVideo(ChatId chat_id, TelegramFile video, int duration, String caption) throws IOException {
		return sendVideo(chat_id, video, duration, caption, 0);
	}
	
	public Message sendVideo(ChatId chat_id, TelegramFile video, int duration) throws IOException {
		return sendVideo(chat_id, video, duration, null);
	}
	
	public Message sendVideo(ChatId chat_id, TelegramFile video) throws IOException {
		return sendVideo(chat_id, video, 0);
	}
	
	public Message sendVoice(ChatId chat_id, TelegramFile voice, int duration, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendVoice";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		if(null != voice.getFile_id()) {
			params.add(new Parameter("voice", voice.getFile_id()));
		}
		else {
			params.add(new Parameter("voice", voice.getFile()));
		}
		if(duration > 0) {
			params.add(new Parameter("duration", "" + duration));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendVoice(ChatId chat_id, TelegramFile voice, int duration, long reply_to_message_id) throws IOException {
		return sendVoice(chat_id, voice, duration, reply_to_message_id, null);
	}
	
	public Message sendVoice(ChatId chat_id, TelegramFile voice, int duration) throws IOException {
		return sendVoice(chat_id, voice, duration, 0);
	}
	
	public Message sendVoice(ChatId chat_id, TelegramFile voice) throws IOException {
		return sendVoice(chat_id, voice, 0);
	}
	
	public Message sendLocation(ChatId chat_id, float latitude, float longitude, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendLocation";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		params.add(new Parameter("latitude", "" + latitude));
		params.add(new Parameter("longitude", "" + longitude));
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendLocation(ChatId chat_id, float latitude, float longitude, long reply_to_message_id) throws IOException {
		return sendLocation(chat_id, latitude, longitude, reply_to_message_id, null);
	}
	
	public Message sendLocation(ChatId chat_id, float latitude, float longitude) throws IOException {
		return sendLocation(chat_id, latitude, longitude, 0);
	}
	
	public boolean sendChatAction(ChatId chat_id, ChatAction action) throws IOException {
		String methodName = "sendChatAction";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", chat_id.getChatId()));
		params.add(new Parameter("action", "" + action));
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return false;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), boolean.class);
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
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), UserProfilePhotos.class);
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
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Update[].class);
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
	
	public TelegramFile getTFile(String file_id) throws IOException {
		String methodName = "getFile";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("file_id", "" + file_id));
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if((null == botServiceResponse) || (!botServiceResponse.isOk())) {
			return null;
		}
		return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), TelegramFile.class);
	}
	
	public File downloadTFile(String file_id, String downloadFilePath, boolean showDownloadProgress) throws IOException {
		TelegramFile tFile = getTFile(file_id);
		File downloadToFile = null;
		if((downloadFilePath != null) && (!downloadFilePath.isEmpty())) {
			downloadToFile = new File(downloadFilePath);
		}
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
	
	public File downloadTFile(String file_id, String downloadFilePath) throws IOException {
		return downloadTFile(file_id, downloadFilePath, false);
	}
	
	public File downloadTFile(String file_id, boolean showDownloadProgress) throws IOException {
		return downloadTFile(file_id, null, showDownloadProgress);
	}
	
	public File downloadTFile(String file_id) throws IOException {
		return downloadTFile(file_id, null, false);
	}
	
}
