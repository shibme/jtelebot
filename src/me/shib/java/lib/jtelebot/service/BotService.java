package me.shib.java.lib.jtelebot.service;

import me.shib.java.lib.common.utils.JsonLib;
import me.shib.java.lib.jtelebot.types.*;
import me.shib.java.lib.rest.client.HTTPFileDownloader;
import me.shib.java.lib.rest.client.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Create an instance for this class with your Bot API token. Instances are singleton and for every new API token.
 */
public class BotService extends TelegramBot {

    private static final String telegramBotServiceEndPoint = "https://api.telegram.org";

    private static Map<String, BotService> botMap;
    private static Logger logger = Logger.getLogger(BotService.class.getName());

    private String botApiToken;
    private long updateServiceOffset = 0;
    private JsonLib jsonLib;
    private BotServiceWrapper botServiceWrapper;
    private User identity;

    private BotService(String botApiToken) {
        this.botApiToken = botApiToken;
        jsonLib = new JsonLib();
        botServiceWrapper = new BotServiceWrapper(telegramBotServiceEndPoint + "/" + "bot" + botApiToken, jsonLib);
    }

    /**
     * Creates a singleton object for the given bot API token. For every unique API token, a singleton object is created.
     *
     * @param botApiToken the API token that is given by @BotFather bot
     * @return A singleton instance of the bot for the given API token. Returns null if the token is invalid.
     */
    public static synchronized BotService getInstance(String botApiToken) {
        if ((botApiToken == null) || (botApiToken.isEmpty())) {
            return null;
        }
        if (botMap == null) {
            botMap = new HashMap<>();
        }
        BotService bot = botMap.get(botApiToken);
        if (bot == null) {
            bot = new BotService(botApiToken);
            if (bot.getIdentity() != null) {
                botMap.put(botApiToken, bot);
            } else {
                bot = null;
            }
        }
        return bot;
    }

    /**
     * Gives the API token of the bot that is associated with the object.
     *
     * @return the API token of the bot is returned.
     */
    public String getBotApiToken() {
        return botApiToken;
    }

    /**
     * A simple method for testing your bot's auth token.
     *
     * @return basic information about the bot in form of a User object.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public User getMe() throws IOException {
        String methodName = "getMe";
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.get(methodName, null);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), User.class);
    }

    /**
     * A simple method for getting your bot's last known identity. Updates the identity only when getMe is called.
     *
     * @return basic information about the bot in form of a User object.
     */
    public User getIdentity() {
        if (identity == null) {
            try {
                identity = getMe();
            } catch (Exception e) {
                logger.throwing(this.getClass().getName(), "getIdentity", e);
                identity = null;
            }
        }
        return identity;
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
        if (parse_mode != null) {
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send answers to an inline query. No more than 50 results per query are allowed.
     *
     * @param inline_query_id Unique identifier for the answered query
     * @param results         A JSON-serialized array of results for the inline query
     * @param next_offset     Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don‘t support pagination. Offset length can’t exceed 64 bytes.
     * @param is_personal     Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
     * @param cache_time      The maximum amount of time in seconds that the result of the inline query may be cached on the server. Defaults to 300.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean answerInlineQuery(String inline_query_id, InlineQueryResult[] results, String next_offset, boolean is_personal, int cache_time) throws IOException {
        String methodName = "answerInlineQuery";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("inline_query_id", inline_query_id));
        params.add(new Parameter("results", "" + jsonLib.toJson(results)));
        if (next_offset != null) {
            params.add(new Parameter("next_offset", next_offset));
        }
        if (is_personal) {
            params.add(new Parameter("is_personal", "" + true));
        }
        if (cache_time >= 0) {
            params.add(new Parameter("cache_time", "" + cache_time));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Boolean.class);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
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
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), UserProfilePhotos.class);
    }

    /**
     * Use this method to receive incoming updates using long polling with the given timeout value.
     *
     * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
     * @param limit   Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
     * @param offset  Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
     * @return An array of Update objects is returned. Returns an empty array if there aren't any updates.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public synchronized Update[] getUpdates(int timeout, int limit, long offset) throws IOException {
        String methodName = "getUpdates";
        ArrayList<Parameter> params = new ArrayList<>();
        if (offset > 0) {
            params.add(new Parameter("offset", "" + offset));
        }
        if ((limit > 0) && (limit <= 100)) {
            params.add(new Parameter("limit", "" + limit));
        }
        if (timeout > 0) {
            params.add(new Parameter("timeout", "" + timeout));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return new Update[0];
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), Update[].class);
    }

    /**
     * Use this method to receive incoming updates using long polling with the given timeout value.
     *
     * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
     * @param limit   Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
     * @return An array of Update objects is returned. Returns an empty array if there aren't any updates.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public synchronized Update[] getUpdates(int timeout, int limit) throws IOException {
        Update[] updates = getUpdates(timeout, limit, updateServiceOffset);
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
    public TelegramFile getFile(String file_id) throws IOException {
        String methodName = "getFile";
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("file_id", "" + file_id));
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.post(methodName, params);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonLib.fromJson(jsonLib.toJson(botServiceResponse.getResult()), TelegramFile.class);
    }

    /**
     * Use this method to download and return a File object of a given file_id . For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id           File identifier to for the file to be downloaded
     * @param downloadToFile    The local file where the content has to be downloaded
     * @param waitForCompletion Waits until the download is complete
     * @return On success, a File object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public HTTPFileDownloader.DownloadProgress downloadToFile(String file_id, File downloadToFile, boolean waitForCompletion) throws IOException {
        TelegramFile tFile = getFile(file_id);
        String downloadableURL = telegramBotServiceEndPoint + "/file/bot" + botApiToken + "/" + tFile.getFile_path();
        HTTPFileDownloader hfd;
        if (downloadToFile == null) {
            hfd = new HTTPFileDownloader(downloadableURL, "TelegramBotDownloads");
        } else {
            hfd = new HTTPFileDownloader(downloadableURL, downloadToFile);
        }
        hfd.start();
        if (waitForCompletion) {
            try {
                hfd.join();
            } catch (InterruptedException e) {
                logger.throwing(this.getClass().getName(), "downloadToFile", e);
            }
        }
        return hfd.getDownloadProgress();
    }

    /**
     * Use this method to download and return a File object of a given file_id . For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id        File identifier to for the file to be downloaded
     * @param downloadToFile The local file where the content has to be downloaded
     * @return On success, a File object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public File downloadFile(String file_id, File downloadToFile) throws IOException {
        HTTPFileDownloader.DownloadProgress progress = downloadToFile(file_id, downloadToFile, true);
        if (progress.getStatus() == HTTPFileDownloader.DownloadStatus.COMPLETED) {
            return progress.getDownloadedFile();
        }
        return null;
    }

}
