package com.nudanam.java.telegram.bot.service;

import java.io.IOException;
import java.util.ArrayList;

import com.nudanam.java.rest.client.lib.JsonLib;
import com.nudanam.java.rest.client.lib.Parameter;
import com.nudanam.java.telegram.bot.types.Message;
import com.nudanam.java.telegram.bot.types.ReplyMarkup;
import com.nudanam.java.telegram.bot.types.Update;
import com.nudanam.java.telegram.bot.types.User;

public class BotService {
	
	private static final int longPollInterval = 3600;
	
	private long updateServiceOffset = 0;
	private BotServiceWrapper botServiceWrapper;
	
	public enum ChatAction {
		typing, upload_photo, record_video, upload_video, record_audio, upload_audio, upload_document, find_location;
	}
	
	public BotService(String botApiToken) {
		botServiceWrapper = new BotServiceWrapper(BotServiceClientConfig.telegramBotServiceEndPoint + "/" + "bot" + botApiToken);
	}
	
	public User getMe() throws IOException {
		String methodName = "getMe";
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName);
		if(!botServiceResponse.isOk()) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), User.class);
	}
	
	public Message sendMessage(long chat_id, String text, boolean disable_web_page_preview, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
		String methodName = "sendMessage";
		ArrayList<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		params.add(new Parameter("text", "" + text));
		if(disable_web_page_preview) {
			params.add(new Parameter("disable_web_page_preview", "" + disable_web_page_preview));
		}
		if(reply_to_message_id > 0) {
			params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
		}
		if(null != reply_markup) {
			params.add(new Parameter("reply_markup", "" + JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if(!botServiceResponse.isOk()) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
	}
	
	public Message sendMessage(long chat_id, String text, boolean disable_web_page_preview, long reply_to_message_id) throws IOException {
		return sendMessage(chat_id, text, disable_web_page_preview, reply_to_message_id, null);
	}
	
	public Message sendMessage(long chat_id, String text, boolean disable_web_page_preview) throws IOException {
		return sendMessage(chat_id, text, disable_web_page_preview, 0);
	}
	
	public Message sendMessage(long chat_id, String text) throws IOException {
		return sendMessage(chat_id, text, false);
	}
	
	public Message forwardMessage(long chat_id, long from_chat_id, long message_id) throws IOException {
		String methodName = "forwardMessage";
		ArrayList<Parameter> params=new ArrayList<Parameter>();
		params.add(new Parameter("chat_id", "" + chat_id));
		params.add(new Parameter("from_chat_id", "" + from_chat_id));
		params.add(new Parameter("message_id", "" + message_id));
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if(!botServiceResponse.isOk()) {
			return null;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), Message.class);
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
			params.add(new Parameter("reply_markup", "" + JsonLib.toJson(reply_markup)));
		}
		BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
		if(!botServiceResponse.isOk()) {
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
		if(!botServiceResponse.isOk()) {
			return false;
		}
		return JsonLib.fromJson(JsonLib.toJson(botServiceResponse.getResult()), boolean.class);
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
		if(!botServiceResponse.isOk()) {
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
	
}
