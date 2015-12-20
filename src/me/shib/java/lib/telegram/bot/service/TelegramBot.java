package me.shib.java.lib.telegram.bot.service;

import me.shib.java.lib.common.utils.JsonLib;
import me.shib.java.lib.rest.client.Parameter;
import me.shib.java.lib.telegram.bot.types.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Create an instance for this class with your Bot API token. Instances are singleton and for every new API token.
 */
public class TelegramBot {

    private static final String telegramBotServiceEndPoint = "https://api.telegram.org";
    private static final int longPollInterval = 300;

    private static Map<String, TelegramBot> telegramBotsMap;

    private String botApiToken;
    private long updateServiceOffset = 0;
    private JsonLib jsonLib;
    private BotServiceWrapper botServiceWrapper;

    private TelegramBot(String botApiToken) {
        this.botApiToken = botApiToken;
        jsonLib = new JsonLib();
        botServiceWrapper = new BotServiceWrapper(telegramBotServiceEndPoint + "/" + "bot" + botApiToken, jsonLib);
    }

    /*
    A private constructor to prevent other classes from initializing
    */
    private TelegramBot() {
    }

    /**
     * Creates a singleton object for the given bot API token. For every unique API token, a singleton object is created.
     *
     * @param botApiToken the API token that is given by @BotFather bot
     * @return A singleton instance for the given API token
     */
    public static synchronized TelegramBot getInstance(String botApiToken) {
        if (telegramBotsMap == null) {
            telegramBotsMap = new HashMap<>();
        }
        TelegramBot tBot = telegramBotsMap.get(botApiToken);
        if (tBot == null) {
            tBot = new TelegramBot(botApiToken);
            telegramBotsMap.put(botApiToken, tBot);
        }
        return tBot;
    }

    /**
     * A simple method for testing your bot's auth token.
     *
     * @return basic information about the bot in form of a User object.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public User getMe() throws IOException {
        String methodName = "getMe";
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.get(methodName);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), User.class);
    }

    /**
     * Use this method to send text messages.
     *
     * @param chat_id                  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param text                     Text of the message to be sent
     * @param parse_mode               Send Markdown, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
     * @param disable_web_page_preview Disables link previews for links in this message
     * @param reply_to_message_id      If the message is a reply, ID of the original message
     * @param reply_markup             Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        String methodName = "sendMessage";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        params.add(new Parameter("text", text));
        if ((parse_mode != null) && (parse_mode != ParseMode.None)) {
            params.add(new Parameter("parse_mode", parse_mode.toString()));
        }
        if (disable_web_page_preview) {
            params.add(new Parameter("disable_web_page_preview", "" + true));
        }
        if (reply_to_message_id > 0) {
            params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
        }
        if (null != reply_markup) {
            params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send text messages.
     *
     * @param chat_id                  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param text                     Text of the message to be sent
     * @param parse_mode               Send Markdown, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
     * @param disable_web_page_preview Disables link previews for links in this message
     * @param reply_to_message_id      If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, long reply_to_message_id) throws IOException {
        return sendMessage(chat_id, text, parse_mode, disable_web_page_preview, reply_to_message_id, null);
    }

    /**
     * Use this method to send text messages.
     *
     * @param chat_id                  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param text                     Text of the message to be sent
     * @param parse_mode               Send Markdown, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
     * @param disable_web_page_preview Disables link previews for links in this message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview) throws IOException {
        return sendMessage(chat_id, text, parse_mode, disable_web_page_preview, 0);
    }

    /**
     * Use this method to send text messages.
     *
     * @param chat_id    Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param text       Text of the message to be sent
     * @param parse_mode Send Markdown, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode) throws IOException {
        return sendMessage(chat_id, text, parse_mode, false);
    }

    /**
     * Use this method to send text messages.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param text    Text of the message to be sent
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendMessage(ChatId chat_id, String text) throws IOException {
        return sendMessage(chat_id, text, ParseMode.None);
    }

    /**
     * Use this method to forward messages of any kind.
     *
     * @param chat_id      Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param from_chat_id Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
     * @param message_id   Unique message identifier
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message forwardMessage(ChatId chat_id, ChatId from_chat_id, long message_id) throws IOException {
        String methodName = "forwardMessage";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        params.add(new Parameter("from_chat_id", from_chat_id.getChatId()));
        params.add(new Parameter("message_id", "" + message_id));
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send photos.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param photo               Photo to send. You can either pass a file_id as String to resend a photo that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param caption             Photo caption (may also be used when resending photos by file_id).
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendPhoto(ChatId chat_id, TelegramFile photo, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        String methodName = "sendPhoto";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        if (null != photo.getFile_id()) {
            params.add(new Parameter("photo", photo.getFile_id()));
        } else {
            params.add(new Parameter("photo", photo.getFile()));
        }
        if ((null != caption) && (!caption.isEmpty())) {
            params.add(new Parameter("caption", caption));
        }
        if (reply_to_message_id > 0) {
            params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
        }
        if (null != reply_markup) {
            params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send photos.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param photo               Photo to send. You can either pass a file_id as String to resend a photo that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param caption             Photo caption (may also be used when resending photos by file_id).
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendPhoto(ChatId chat_id, TelegramFile photo, String caption, long reply_to_message_id) throws IOException {
        return sendPhoto(chat_id, photo, caption, reply_to_message_id, null);
    }

    /**
     * Use this method to send photos.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param photo   Photo to send. You can either pass a file_id as String to resend a photo that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param caption Photo caption (may also be used when resending photos by file_id).
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendPhoto(ChatId chat_id, TelegramFile photo, String caption) throws IOException {
        return sendPhoto(chat_id, photo, caption, 0);
    }

    /**
     * Use this method to send photos.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param photo   Photo to send. You can either pass a file_id as String to resend a photo that is already on the Telegram servers, or upload a new file by passing a File object.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendPhoto(ChatId chat_id, TelegramFile photo) throws IOException {
        return sendPhoto(chat_id, photo, null);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param audio               Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration            Duration of the audio in seconds
     * @param performer           Performer
     * @param title               Track name
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendAudio(ChatId chat_id, TelegramFile audio, int duration, String performer, String title, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        String methodName = "sendAudio";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        if (null != audio.getFile_id()) {
            params.add(new Parameter("audio", audio.getFile_id()));
        } else {
            params.add(new Parameter("audio", audio.getFile()));
        }
        if (duration > 0) {
            params.add(new Parameter("duration", "" + duration));
        }
        if ((null != performer) && (!performer.isEmpty())) {
            params.add(new Parameter("performer", performer));
        }
        if ((null != title) && (!title.isEmpty())) {
            params.add(new Parameter("title", title));
        }
        if (reply_to_message_id > 0) {
            params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
        }
        if (null != reply_markup) {
            params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param audio               Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration            Duration of the audio in seconds
     * @param performer           Performer
     * @param title               Track name
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendAudio(ChatId chat_id, TelegramFile audio, int duration, String performer, String title, long reply_to_message_id) throws IOException {
        return sendAudio(chat_id, audio, duration, performer, title, reply_to_message_id, null);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id   Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param audio     Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration  Duration of the audio in seconds
     * @param performer Performer
     * @param title     Track name
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendAudio(ChatId chat_id, TelegramFile audio, int duration, String performer, String title) throws IOException {
        return sendAudio(chat_id, audio, duration, performer, title, 0);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param audio    Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration Duration of the audio in seconds
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendAudio(ChatId chat_id, TelegramFile audio, int duration) throws IOException {
        return sendAudio(chat_id, audio, duration, null, null);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param audio   Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendAudio(ChatId chat_id, TelegramFile audio) throws IOException {
        return sendAudio(chat_id, audio, 0);
    }

    /**
     * Use this method to send general files. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param document            File to send. You can either pass a file_id as String to resend a file that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendDocument(ChatId chat_id, TelegramFile document, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        String methodName = "sendDocument";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        if (null != document.getFile_id()) {
            params.add(new Parameter("document", document.getFile_id()));
        } else {
            params.add(new Parameter("document", document.getFile()));
        }
        if (reply_to_message_id > 0) {
            params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
        }
        if (null != reply_markup) {
            params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send general files. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param document            File to send. You can either pass a file_id as String to resend a file that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendDocument(ChatId chat_id, TelegramFile document, long reply_to_message_id) throws IOException {
        return sendDocument(chat_id, document, reply_to_message_id, null);
    }

    /**
     * Use this method to send general files. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param document File to send. You can either pass a file_id as String to resend a file that is already on the Telegram servers, or upload a new file by passing a File object.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendDocument(ChatId chat_id, TelegramFile document) throws IOException {
        return sendDocument(chat_id, document, 0);
    }

    /**
     * Use this method to send .webp stickers.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param sticker             Sticker to send. You can either pass a file_id as String to resend a sticker that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendSticker(ChatId chat_id, TelegramFile sticker, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        String methodName = "sendSticker";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        if (null != sticker.getFile_id()) {
            params.add(new Parameter("sticker", sticker.getFile_id()));
        } else {
            params.add(new Parameter("sticker", sticker.getFile()));
        }
        if (reply_to_message_id > 0) {
            params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
        }
        if (null != reply_markup) {
            params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send .webp stickers.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param sticker             Sticker to send. You can either pass a file_id as String to resend a sticker that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendSticker(ChatId chat_id, TelegramFile sticker, long reply_to_message_id) throws IOException {
        return sendSticker(chat_id, sticker, reply_to_message_id, null);
    }

    /**
     * Use this method to send .webp stickers.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param sticker Sticker to send. You can either pass a file_id as String to resend a sticker that is already on the Telegram servers, or upload a new file by passing a File object.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendSticker(ChatId chat_id, TelegramFile sticker) throws IOException {
        return sendSticker(chat_id, sticker, 0);
    }

    /**
     * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param video               Video to send. You can either pass a file_id as String to resend a video that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration            Duration of sent video in seconds
     * @param caption             Video caption (may also be used when resending videos by file_id).
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVideo(ChatId chat_id, TelegramFile video, int duration, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        String methodName = "sendVideo";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        if (null != video.getFile_id()) {
            params.add(new Parameter("video", video.getFile_id()));
        } else {
            params.add(new Parameter("video", video.getFile()));
        }
        if (duration > 0) {
            params.add(new Parameter("duration", "" + duration));
        }
        if ((null != caption) && (!caption.isEmpty())) {
            params.add(new Parameter("performer", caption));
        }
        if (reply_to_message_id > 0) {
            params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
        }
        if (null != reply_markup) {
            params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param video               Video to send. You can either pass a file_id as String to resend a video that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration            Duration of sent video in seconds
     * @param caption             Video caption (may also be used when resending videos by file_id).
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVideo(ChatId chat_id, TelegramFile video, int duration, String caption, long reply_to_message_id) throws IOException {
        return sendVideo(chat_id, video, duration, caption, reply_to_message_id, null);
    }

    /**
     * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param video    Video to send. You can either pass a file_id as String to resend a video that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration Duration of sent video in seconds
     * @param caption  Video caption (may also be used when resending videos by file_id).
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVideo(ChatId chat_id, TelegramFile video, int duration, String caption) throws IOException {
        return sendVideo(chat_id, video, duration, caption, 0);
    }

    /**
     * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param video    Video to send. You can either pass a file_id as String to resend a video that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration Duration of sent video in seconds
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVideo(ChatId chat_id, TelegramFile video, int duration) throws IOException {
        return sendVideo(chat_id, video, duration, null);
    }

    /**
     * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param video   Video to send. You can either pass a file_id as String to resend a video that is already on the Telegram servers, or upload a new file by passing a File object.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVideo(ChatId chat_id, TelegramFile video) throws IOException {
        return sendVideo(chat_id, video, 0);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Audio or Document). Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param voice               Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration            Duration of sent audio in seconds
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVoice(ChatId chat_id, TelegramFile voice, int duration, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        String methodName = "sendVoice";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        if (null != voice.getFile_id()) {
            params.add(new Parameter("voice", voice.getFile_id()));
        } else {
            params.add(new Parameter("voice", voice.getFile()));
        }
        if (duration > 0) {
            params.add(new Parameter("duration", "" + duration));
        }
        if (reply_to_message_id > 0) {
            params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
        }
        if (null != reply_markup) {
            params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Audio or Document). Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param voice               Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration            Duration of sent audio in seconds
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVoice(ChatId chat_id, TelegramFile voice, int duration, long reply_to_message_id) throws IOException {
        return sendVoice(chat_id, voice, duration, reply_to_message_id, null);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Audio or Document). Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param voice    Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration Duration of sent audio in seconds
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVoice(ChatId chat_id, TelegramFile voice, int duration) throws IOException {
        return sendVoice(chat_id, voice, duration, 0);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Audio or Document). Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param voice   Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVoice(ChatId chat_id, TelegramFile voice) throws IOException {
        return sendVoice(chat_id, voice, 0);
    }

    /**
     * Use this method to send point on the map.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param latitude            Latitude of location
     * @param longitude           Longitude of location
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendLocation(ChatId chat_id, float latitude, float longitude, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        String methodName = "sendLocation";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        params.add(new Parameter("latitude", "" + latitude));
        params.add(new Parameter("longitude", "" + longitude));
        if (reply_to_message_id > 0) {
            params.add(new Parameter("reply_to_message_id", "" + reply_to_message_id));
        }
        if (null != reply_markup) {
            params.add(new Parameter("reply_markup", jsonLib.toJson(reply_markup)));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send point on the map.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param latitude            Latitude of location
     * @param longitude           Longitude of location
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendLocation(ChatId chat_id, float latitude, float longitude, long reply_to_message_id) throws IOException {
        return sendLocation(chat_id, latitude, longitude, reply_to_message_id, null);
    }

    /**
     * Use this method to send point on the map.
     *
     * @param chat_id   Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param latitude  Latitude of location
     * @param longitude Longitude of location
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendLocation(ChatId chat_id, float latitude, float longitude) throws IOException {
        return sendLocation(chat_id, latitude, longitude, 0);
    }

    /**
     * Use this method when you need to tell the user that something is happening on the bot's side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status). We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param action  Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files, upload_document for general files, find_location for location data.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean sendChatAction(ChatId chat_id, ChatAction action) throws IOException {
        String methodName = "sendChatAction";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("chat_id", chat_id.getChatId()));
        params.add(new Parameter("action", "" + action));
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), boolean.class);
    }

    /**
     * Use this method to get a list of profile pictures for a user.
     *
     * @param user_id Unique identifier of the target user
     * @param offset  Sequential number of the first photo to be returned. By default, all photos are returned.
     * @param limit   Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
     * @return Returns a UserProfilePhotos object.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public UserProfilePhotos getUserProfilePhotos(long user_id, int offset, int limit) throws IOException {
        String methodName = "getUserProfilePhotos";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("user_id", "" + user_id));
        if (offset > 0) {
            params.add(new Parameter("offset", "" + offset));
        }
        if ((limit > 0) && (limit < 100)) {
            params.add(new Parameter("limit", "" + limit));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), UserProfilePhotos.class);
    }

    /**
     * Use this method to get a list of profile pictures for a user.
     *
     * @param user_id Unique identifier of the target user
     * @param offset  Sequential number of the first photo to be returned. By default, all photos are returned.
     * @return Returns a UserProfilePhotos object.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public UserProfilePhotos getUserProfilePhotos(long user_id, int offset) throws IOException {
        return getUserProfilePhotos(user_id, offset, 100);
    }

    /**
     * Use this method to get a list of profile pictures for a user.
     *
     * @param user_id Unique identifier of the target user
     * @return Returns a UserProfilePhotos object.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public UserProfilePhotos getUserProfilePhotos(long user_id) throws IOException {
        return getUserProfilePhotos(user_id, 0);
    }

    /**
     * Use this method to receive incoming updates using long polling.
     *
     * @param offset  Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
     * @param limit   Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
     * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
     * @return An Array of Update objects is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public synchronized Update[] getUpdates(long offset, int limit, int timeout) throws IOException {
        String methodName = "getUpdates";
        ArrayList<Parameter> params = new ArrayList<>();
        if (offset > 0) {
            params.add(new Parameter("offset", "" + offset));
        }
        if (limit <= 100) {
            params.add(new Parameter("limit", "" + limit));
        }
        if (timeout > 0) {
            params.add(new Parameter("timeout", "" + timeout));
        }
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return new Update[0];
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Update[].class);
    }

    /**
     * Use this method to receive incoming updates using long polling.
     *
     * @param offset Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
     * @param limit  Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
     * @return An Array of Update objects is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public synchronized Update[] getUpdates(long offset, int limit) throws IOException {
        return getUpdates(offset, limit, longPollInterval);
    }

    /**
     * Use this method to receive incoming updates using long polling.
     *
     * @param offset Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
     * @return An Array of Update objects is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public synchronized Update[] getUpdates(long offset) throws IOException {
        return getUpdates(offset, 100, longPollInterval);
    }

    /**
     * Use this method to receive incoming updates using long polling.
     *
     * @return An Array of Update objects is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public synchronized Update[] getUpdates() throws IOException {
        Update[] updates = getUpdates(updateServiceOffset, 100, longPollInterval);
        if (updates.length > 0) {
            updateServiceOffset = updates[updates.length - 1].getUpdate_id() + 1;
        }
        return updates;
    }

    /**
     * Use this method to get a TelegramFile object for downloading. For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id File identifier to get TelegramFile object
     * @return On success, a TelegramFile object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public TelegramFile getTFile(String file_id) throws IOException {
        String methodName = "getFile";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("file_id", "" + file_id));
        BotServiceWrapperResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), TelegramFile.class);
    }

    /**
     * Use this method to download and return a File object of a given file_id . For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id          File identifier to for the file to be downloaded
     * @param downloadFilePath The local path where the file has to be downloaded
     * @return On success, a File object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public File downloadTFile(String file_id, String downloadFilePath) throws IOException {
        boolean showDownloadProgress = false;
        TelegramFile tFile = getTFile(file_id);
        File downloadToFile = null;
        if ((downloadFilePath != null) && (!downloadFilePath.isEmpty())) {
            downloadToFile = new File(downloadFilePath);
        }
        String downloadableURL = telegramBotServiceEndPoint + "/file/bot" + botApiToken + "/" + tFile.getFile_path();
        HTTPFileDownloader hfd = null;
        if (downloadToFile == null) {
            hfd = new HTTPFileDownloader(downloadableURL, "TelegramBotDownloads");
        } else {
            hfd = new HTTPFileDownloader(downloadableURL, downloadToFile);
        }
        hfd.start();
        int prevPercent = -1;
        while (hfd.isAlive()) {
            if (showDownloadProgress) {
                if (hfd.getCompletedPercentage() > prevPercent) {
                    prevPercent = hfd.getCompletedPercentage();
                    System.out.print(file_id + ": " + prevPercent + "% \r");
                }
            }
        }
        if (showDownloadProgress) {
            if (hfd.getFile() == null) {
                System.out.print(file_id + ": Download Failed.\n");
            } else {
                System.out.print(file_id + ": Download Complete.\n");
            }
        }
        return hfd.getFile();
    }

    /**
     * Use this method to download and return a File object of a given file_id . For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id File identifier to for the file to be downloaded
     * @return On success, a File object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public File downloadTFile(String file_id) throws IOException {
        return downloadTFile(file_id, null);
    }

    /**
     * The types of chat actions available
     */
    public enum ChatAction {
        typing, upload_photo, record_video, upload_video, record_audio, upload_audio, upload_document, find_location;
    }

}
