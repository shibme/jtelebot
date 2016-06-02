package me.shib.java.lib.jtelebot.service;

import me.shib.java.lib.jtelebot.models.inline.InlineKeyboardMarkup;
import me.shib.java.lib.jtelebot.models.inline.InlineQueryResult;
import me.shib.java.lib.jtelebot.models.types.*;
import me.shib.java.lib.jtelebot.models.updates.Message;
import me.shib.java.lib.jtelebot.models.updates.Update;
import me.shib.java.lib.restiny.HTTPFileDownloader;
import me.shib.java.lib.restiny.requests.GET;
import me.shib.java.lib.restiny.requests.POST;
import me.shib.java.lib.restiny.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Create an instance for this class with your Bot API token. Instances are singleton and for every new API token, a singleton object is created.
 */
public final class BotService extends TelegramBot {

    private static final String telegramBotServiceEndPoint = "https://api.telegram.org";
    private static Logger logger = Logger.getLogger(BotService.class.getName());

    private String botApiToken;
    private JsonUtil jsonUtil;
    private BotServiceWrapper botServiceWrapper;
    private User identity;
    private String endPoint;
    private BotUpdateService botUpdateService;


    /**
     * Creates an object for the given bot API token. For every unique API token, a singleton update receiver is created
     * is created to avoid duplicate update reception throughout the JVM.
     *
     * @param botApiToken the API token that is given by @BotFather bot
     * @param endPoint    the endpoint to call the Bot API service. Might be used in case of proxy services
     */
    public BotService(String botApiToken, String endPoint) {
        if ((endPoint == null) || (endPoint.isEmpty())) {
            this.endPoint = telegramBotServiceEndPoint;
        } else {
            this.endPoint = endPoint;
        }
        this.botApiToken = botApiToken;
        this.jsonUtil = new JsonUtil();
        this.botServiceWrapper = new BotServiceWrapper(this.endPoint + "/bot" + botApiToken);
        this.botUpdateService = BotUpdateService.getInstance(this.botApiToken, this.endPoint);
    }

    /**
     * Creates an object for the given bot API token. For every unique API token, a singleton update receiver is created
     * is created to avoid duplicate update reception throughout the JVM.
     *
     * @param botApiToken the API token that is given by @BotFather bot
     */
    public BotService(String botApiToken) {
        this(botApiToken, null);
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
        return botUpdateService.getUpdates(timeout, limit, offset);
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
        return botUpdateService.getUpdates(timeout, limit);
    }

    /**
     * Use this method to specify a url and receive incoming updates via an outgoing webhook.
     * Whenever there is an update for the bot, an HTTPS POST request to the specified url will be sent, containing a JSON-serialized Update.
     * In case of an unsuccessful request, it will give up after a reasonable amount of attempts.
     * You will not be able to receive updates using getUpdates for as long as an outgoing webhook is set up.
     * To use a self-signed certificate, you need to upload your public key certificate using certificate parameter.
     * Please upload the certificate as InputFile, sending a String will not work.
     * Ports currently supported for Webhooks: 443, 80, 88, 8443.
     *
     * @param url         HTTPS url to send updates to. Use an empty string to remove webhook integration
     * @param certificate Upload your public key certificate so that the root certificate in use can be checked. See our self-signed guide for details.
     * @return An array of Update objects is returned. Returns an empty array if there aren't any updates.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean setWebhook(String url, InputFile certificate) throws IOException {
        POST postRequest = new POST("setWebhook");
        if (url != null) {
            postRequest.addParameter("url", url);
        }
        if (null != certificate.getFile_id()) {
            postRequest.addParameter("certificate", certificate.getFile_id());
        } else {
            postRequest.addParameter("certificate", certificate.getFile());
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Boolean.class);
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
        GET getRequest = new GET("getMe");
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(getRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), User.class);
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
     * @param disable_notification     Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        POST postRequest = new POST("sendMessage");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        postRequest.addParameter("text", text);
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        if (parse_mode != null) {
            postRequest.addParameter("parse_mode", parse_mode.toString());
        }
        if (disable_web_page_preview) {
            postRequest.addParameter("disable_web_page_preview", "" + true);
        }
        if (reply_to_message_id > 0) {
            postRequest.addParameter("reply_to_message_id", "" + reply_to_message_id);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to forward messages of any kind.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param from_chat_id         Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
     * @param message_id           Unique message identifier
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message forwardMessage(ChatId chat_id, ChatId from_chat_id, long message_id, boolean disable_notification) throws IOException {
        POST postRequest = new POST("forwardMessage");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        postRequest.addParameter("from_chat_id", from_chat_id.getChatId());
        postRequest.addParameter("message_id", "" + message_id);
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send photos.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param photo                Photo to send. You can either pass a file_id as String to resend a photo that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param caption              Photo caption (may also be used when resending photos by file_id).
     * @param reply_to_message_id  If the message is a reply, ID of the original message
     * @param reply_markup         Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendPhoto(ChatId chat_id, InputFile photo, String caption, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        POST postRequest = new POST("sendPhoto");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        if (null != photo.getFile_id()) {
            postRequest.addParameter("photo", photo.getFile_id());
        } else {
            postRequest.addParameter("photo", photo.getFile());
        }
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        if ((null != caption) && (!caption.isEmpty())) {
            postRequest.addParameter("caption", caption);
        }
        if (reply_to_message_id > 0) {
            postRequest.addParameter("reply_to_message_id", "" + reply_to_message_id);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param audio                Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration             Duration of the audio in seconds
     * @param performer            Performer
     * @param title                Track name
     * @param reply_to_message_id  If the message is a reply, ID of the original message
     * @param reply_markup         Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendAudio(ChatId chat_id, InputFile audio, int duration, String performer, String title, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        POST postRequest = new POST("sendAudio");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        if (null != audio.getFile_id()) {
            postRequest.addParameter("audio", audio.getFile_id());
        } else {
            postRequest.addParameter("audio", audio.getFile());
        }
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        if (duration > 0) {
            postRequest.addParameter("duration", "" + duration);
        }
        if ((null != performer) && (!performer.isEmpty())) {
            postRequest.addParameter("performer", performer);
        }
        if ((null != title) && (!title.isEmpty())) {
            postRequest.addParameter("title", title);
        }
        if (reply_to_message_id > 0) {
            postRequest.addParameter("reply_to_message_id", "" + reply_to_message_id);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send general files. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param document             File to send. You can either pass a file_id as String to resend a file that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param caption              Document caption (may also be used when resending documents by file_id), 0-200 characters
     * @param reply_to_message_id  If the message is a reply, ID of the original message
     * @param reply_markup         Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendDocument(ChatId chat_id, InputFile document, String caption, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        POST postRequest = new POST("sendDocument");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        if (null != document.getFile_id()) {
            postRequest.addParameter("document", document.getFile_id());
        } else {
            postRequest.addParameter("document", document.getFile());
        }
        if (null != caption) {
            postRequest.addParameter("caption", caption);
        }
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        if (reply_to_message_id > 0) {
            postRequest.addParameter("reply_to_message_id", "" + reply_to_message_id);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send .webp stickers.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param sticker              Sticker to send. You can either pass a file_id as String to resend a sticker that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param reply_to_message_id  If the message is a reply, ID of the original message
     * @param reply_markup         Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendSticker(ChatId chat_id, InputFile sticker, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        POST postRequest = new POST("sendSticker");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        if (null != sticker.getFile_id()) {
            postRequest.addParameter("sticker", sticker.getFile_id());
        } else {
            postRequest.addParameter("sticker", sticker.getFile());
        }
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        if (reply_to_message_id > 0) {
            postRequest.addParameter("reply_to_message_id", "" + reply_to_message_id);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param video                Video to send. You can either pass a file_id as String to resend a video that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration             Duration of sent video in seconds
     * @param caption              Video caption (may also be used when resending videos by file_id).
     * @param reply_to_message_id  If the message is a reply, ID of the original message
     * @param reply_markup         Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param width                Video width
     * @param height               Video height
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVideo(ChatId chat_id, InputFile video, int duration, String caption, long reply_to_message_id, ReplyMarkup reply_markup, int width, int height, boolean disable_notification) throws IOException {
        POST postRequest = new POST("sendVideo");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        if (null != video.getFile_id()) {
            postRequest.addParameter("video", video.getFile_id());
        } else {
            postRequest.addParameter("video", video.getFile());
        }
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        if (duration > 0) {
            postRequest.addParameter("duration", "" + duration);
        }
        if ((null != caption) && (!caption.isEmpty())) {
            postRequest.addParameter("performer", caption);
        }
        if (reply_to_message_id > 0) {
            postRequest.addParameter("reply_to_message_id", "" + reply_to_message_id);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        if (width > 0) {
            postRequest.addParameter("width", "" + width);
        }
        if (height > 0) {
            postRequest.addParameter("height", "" + height);
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Audio or Document). Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param voice                Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration             Duration of sent audio in seconds
     * @param reply_to_message_id  If the message is a reply, ID of the original message
     * @param reply_markup         Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVoice(ChatId chat_id, InputFile voice, int duration, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        POST postRequest = new POST("sendVoice");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        if (null != voice.getFile_id()) {
            postRequest.addParameter("voice", voice.getFile_id());
        } else {
            postRequest.addParameter("voice", voice.getFile());
        }
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        if (duration > 0) {
            postRequest.addParameter("duration", "" + duration);
        }
        if (reply_to_message_id > 0) {
            postRequest.addParameter("reply_to_message_id", "" + reply_to_message_id);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    private Message sendLocationAndVenue(String methodName, ChatId chat_id, float latitude, float longitude, String title, String address, String foursquare_id, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        POST postRequest = new POST(methodName);
        postRequest.addParameter("chat_id", chat_id.getChatId());
        postRequest.addParameter("latitude", "" + latitude);
        postRequest.addParameter("longitude", "" + longitude);
        if (null != title) {
            postRequest.addParameter("title", title);
        }
        if (null != address) {
            postRequest.addParameter("address", address);
        }
        if (null != foursquare_id) {
            postRequest.addParameter("foursquare_id", foursquare_id);
        }
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        if (reply_to_message_id > 0) {
            postRequest.addParameter("reply_to_message_id", "" + reply_to_message_id);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to send point on the map.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param latitude             Latitude of location
     * @param longitude            Longitude of location
     * @param reply_to_message_id  If the message is a reply, ID of the original message
     * @param reply_markup         Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendLocation(ChatId chat_id, float latitude, float longitude, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        return sendLocationAndVenue("sendLocation", chat_id, latitude, longitude, null, null, null, reply_to_message_id, reply_markup, disable_notification);
    }

    /**
     * Use this method to send information about a venue.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param latitude             Latitude of location
     * @param longitude            Longitude of location
     * @param title                Name of the venue
     * @param address              Address of the venue
     * @param foursquare_id        Foursquare identifier of the venue
     * @param reply_to_message_id  If the message is a reply, ID of the original message
     * @param reply_markup         Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVenue(ChatId chat_id, float latitude, float longitude, String title, String address, String foursquare_id, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        return sendLocationAndVenue("sendVenue", chat_id, latitude, longitude, title, address, foursquare_id, reply_to_message_id, reply_markup, disable_notification);
    }

    /**
     * Use this method to send phone contacts.
     *
     * @param chat_id              Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param phone_number         Contact's phone number
     * @param first_name           Contact's first name
     * @param last_name            Contact's last name
     * @param reply_to_message_id  If the message is a reply, ID of the original message
     * @param reply_markup         Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param disable_notification Sends the message silently
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendContact(ChatId chat_id, String phone_number, String first_name, String last_name, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException {
        POST postRequest = new POST("sendContact");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        if (null != phone_number) {
            postRequest.addParameter("phone_number", phone_number);
        }
        if (null != first_name) {
            postRequest.addParameter("first_name", first_name);
        }
        if (null != last_name) {
            postRequest.addParameter("last_name", last_name);
        }
        if (disable_notification) {
            postRequest.addParameter("disable_notification", "" + true);
        }
        if (reply_to_message_id > 0) {
            postRequest.addParameter("reply_to_message_id", "" + reply_to_message_id);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
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
        POST postRequest = new POST("sendChatAction");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        postRequest.addParameter("action", "" + action);
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), boolean.class);
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
        POST postRequest = new POST("getUserProfilePhotos");
        postRequest.addParameter("user_id", "" + user_id);
        if (offset > 0) {
            postRequest.addParameter("offset", "" + offset);
        }
        if ((limit > 0) && (limit < 100)) {
            postRequest.addParameter("limit", "" + limit);
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), UserProfilePhotos.class);
    }

    /**
     * Use this method to get a InputFile object for downloading. For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id File identifier to get TFile object
     * @return On success, a TFile object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public TFile getFile(String file_id) throws IOException {
        POST postRequest = new POST("getFile");
        postRequest.addParameter("file_id", "" + file_id);
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), TFile.class);
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
        TFile tFile = getFile(file_id);
        if ((tFile == null) || (tFile.getFile_path() == null) || (tFile.getFile_path().isEmpty())) {
            return null;
        }
        String downloadableURL = endPoint + "/file/bot" + botApiToken + "/" + tFile.getFile_path();
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
        if ((progress != null) && (progress.getStatus() == HTTPFileDownloader.DownloadStatus.COMPLETED)) {
            return progress.getDownloadedFile();
        }
        return null;
    }

    /**
     * Use this method to send answers to an inline query. No more than 50 results per query are allowed.
     *
     * @param inline_query_id     Unique identifier for the answered query
     * @param results             A JSON-serialized array of results for the inline query
     * @param next_offset         Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don‘t support pagination. Offset length can’t exceed 64 bytes.
     * @param is_personal         Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
     * @param cache_time          The maximum amount of time in seconds that the result of the inline query may be cached on the server. Defaults to 300.
     * @param switch_pm_text      If passed, clients will display a button with specified text that switches the user to a private chat with the bot and sends the bot a start message with the parameter switch_pm_parameter
     * @param switch_pm_parameter Parameter for the start message sent to the bot when user presses the switch button
     *                            Example: An inline bot that sends YouTube videos can ask the user to connect the bot to their YouTube account to adapt search results accordingly.
     *                            To do this, it displays a ‘Connect your YouTube account’ button above the results, or even before showing any.
     *                            The user presses the button, switches to a private chat with the bot and, in doing so, passes a start parameter that instructs the bot to return an oauth link.
     *                            Once done, the bot can offer a switch_inline button so that the user can easily return to the chat where they wanted to use the bot's inline capabilities.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean answerInlineQuery(String inline_query_id, InlineQueryResult[] results, String next_offset, boolean is_personal, int cache_time, String switch_pm_text, String switch_pm_parameter) throws IOException {
        POST postRequest = new POST("answerInlineQuery");
        postRequest.addParameter("inline_query_id", inline_query_id);
        postRequest.addParameter("results", "" + jsonUtil.toJson(results));
        if (next_offset != null) {
            postRequest.addParameter("next_offset", next_offset);
        }
        if (is_personal) {
            postRequest.addParameter("is_personal", "" + true);
        }
        if (cache_time >= 0) {
            postRequest.addParameter("cache_time", "" + cache_time);
        }
        if (null != switch_pm_text) {
            postRequest.addParameter("switch_pm_text", switch_pm_text);
        }
        if (null != switch_pm_parameter) {
            postRequest.addParameter("switch_pm_parameter", switch_pm_parameter);
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Boolean.class);
    }

    private boolean manageGroupMember(String methodName, ChatId chat_id, long user_id) throws IOException {
        POST postRequest = new POST(methodName);
        postRequest.addParameter("chat_id", chat_id.getChatId());
        postRequest.addParameter("user_id", "" + user_id);
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Boolean.class);
    }

    /**
     * Use this method to kick a user from a group or a supergroup.
     * In the case of supergroups, the user will not be able to return to the group on their own using invite links, etc., unless unbanned first.
     * The bot must be an administrator in the group for this to work.
     *
     * @param chat_id Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
     * @param user_id Unique identifier of the target user
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean kickChatMember(ChatId chat_id, long user_id) throws IOException {
        return manageGroupMember("kickChatMember", chat_id, user_id);
    }

    /**
     * Use this method for your bot to leave a group, supergroup or channel.
     *
     * @param chat_id Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean leaveChat(ChatId chat_id) throws IOException {
        POST postRequest = new POST("leaveChat");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Boolean.class);
    }

    /**
     * Use this method to unban a previously kicked user in a supergroup.
     * The user will not return to the group automatically, but will be able to join via link, etc.
     * The bot must be an administrator in the group for this to work.
     *
     * @param chat_id Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
     * @param user_id Unique identifier of the target user
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean unbanChatMember(ChatId chat_id, long user_id) throws IOException {
        return manageGroupMember("unbanChatMember", chat_id, user_id);
    }

    /**
     * Use this method to get up to date information about the chat
     * (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.).
     *
     * @param chat_id Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
     * @return On success, returns a Chat object.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Chat getChat(ChatId chat_id) throws IOException {
        POST postRequest = new POST("getChat");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Chat.class);
    }

    /**
     * Use this method to get a list of administrators in a chat.
     *
     * @param chat_id Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
     * @return On success, returns an Array of ChatMember objects that contains information about all chat administrators except other bots.
     * If the chat is a group or a supergroup and no administrators were appointed, only the creator will be returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public ChatMember[] getChatAdministrators(ChatId chat_id) throws IOException {
        POST postRequest = new POST("getChatAdministrators");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), ChatMember[].class);
    }

    /**
     * Use this method to get the number of members in a chat.
     *
     * @param chat_id Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
     * @return On success, returns the number of members in a chat as an integer
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public int getChatMembersCount(ChatId chat_id) throws IOException {
        POST postRequest = new POST("getChatMembersCount");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return 0;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Integer.class);
    }

    /**
     * Use this method to get information about a member of a chat.
     *
     * @param chat_id Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
     * @param user_id Unique identifier of the target user
     * @return On success, returns a ChatMember object
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public ChatMember getChatMember(ChatId chat_id, long user_id) throws IOException {
        POST postRequest = new POST("getChatMember");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        postRequest.addParameter("user_id", user_id + "");
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), ChatMember.class);
    }

    /**
     * Use this method to send answers to callback queries sent from inline keyboards.
     * The answer will be displayed to the user as a notification at the top of the chat screen or as an alert.
     *
     * @param callback_query_id Unique identifier for the query to be answered
     * @param text              Text of the notification. If not specified, nothing will be shown to the user
     * @param show_alert        If true, an alert will be shown by the client instead of a notification at the top of the chat screen.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean answerCallbackQuery(String callback_query_id, String text, boolean show_alert) throws IOException {
        POST postRequest = new POST("answerCallbackQuery");
        postRequest.addParameter("callback_query_id", callback_query_id);
        if (null != text) {
            postRequest.addParameter("text", text);
        }
        if (show_alert) {
            postRequest.addParameter("show_alert", "" + true);
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Boolean.class);
    }

    /**
     * Use this method to edit text messages sent by the bot.
     *
     * @param chat_id                  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param message_id               Unique identifier of the sent message
     * @param text                     New text of the message
     * @param parse_mode               Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
     * @param disable_web_page_preview Disables link previews for links in this message
     * @param reply_markup             A JSON-serialized object for an inline keyboard.
     * @return On success, if edited message is sent by the bot, the edited Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    @Override
    public Message editMessageText(ChatId chat_id, long message_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, InlineKeyboardMarkup reply_markup) throws IOException {
        POST postRequest = new POST("editMessageText");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        postRequest.addParameter("message_id", "" + message_id);
        postRequest.addParameter("text", text);
        if (parse_mode != null) {
            postRequest.addParameter("parse_mode", parse_mode.toString());
        }
        if (disable_web_page_preview) {
            postRequest.addParameter("disable_web_page_preview", "" + true);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to edit text messages sent via the bot (for inline bots).
     *
     * @param inline_message_id        Identifier of the inline message
     * @param text                     New text of the message
     * @param parse_mode               Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
     * @param disable_web_page_preview Disables link previews for links in this message
     * @param reply_markup             A JSON-serialized object for an inline keyboard.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    @Override
    public boolean editMessageText(String inline_message_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, InlineKeyboardMarkup reply_markup) throws IOException {
        POST postRequest = new POST("editMessageText");
        postRequest.addParameter("inline_message_id", inline_message_id);
        postRequest.addParameter("text", text);
        if (parse_mode != null) {
            postRequest.addParameter("parse_mode", parse_mode.toString());
        }
        if (disable_web_page_preview) {
            postRequest.addParameter("disable_web_page_preview", "" + true);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Boolean.class);
    }

    /**
     * Use this method to edit captions of messages sent by the bot.
     *
     * @param chat_id      Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param message_id   Unique identifier of the sent message
     * @param caption      New caption of the message
     * @param reply_markup A JSON-serialized object for an inline keyboard.
     * @return On success, if edited message is sent by the bot, the edited Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    @Override
    public Message editMessageCaption(ChatId chat_id, long message_id, String caption, InlineKeyboardMarkup reply_markup) throws IOException {
        POST postRequest = new POST("editMessageCaption");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        postRequest.addParameter("message_id", "" + message_id);
        if (null != caption) {
            postRequest.addParameter("caption", caption);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to edit captions of messages sent via the bot (for inline bots).
     *
     * @param inline_message_id Identifier of the inline message
     * @param caption           New caption of the message
     * @param reply_markup      A JSON-serialized object for an inline keyboard.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    @Override
    public boolean editMessageCaption(String inline_message_id, String caption, InlineKeyboardMarkup reply_markup) throws IOException {
        POST postRequest = new POST("editMessageCaption");
        postRequest.addParameter("inline_message_id", inline_message_id);
        if (null != caption) {
            postRequest.addParameter("caption", caption);
        }
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Boolean.class);
    }

    /**
     * Use this method to edit only the reply markup of messages sent by the bot.
     *
     * @param chat_id      Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param message_id   Unique identifier of the sent message
     * @param reply_markup A JSON-serialized object for an inline keyboard.
     * @return On success, if edited message is sent by the bot, the edited Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    @Override
    public Message editMessageReplyMarkup(ChatId chat_id, long message_id, InlineKeyboardMarkup reply_markup) throws IOException {
        POST postRequest = new POST("editMessageReplyMarkup");
        postRequest.addParameter("chat_id", chat_id.getChatId());
        postRequest.addParameter("message_id", "" + message_id);
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return null;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Message.class);
    }

    /**
     * Use this method to edit only the reply markup of messages sent via the bot (for inline bots).
     *
     * @param inline_message_id Identifier of the inline message
     * @param reply_markup      A JSON-serialized object for an inline keyboard.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    @Override
    public boolean editMessageReplyMarkup(String inline_message_id, InlineKeyboardMarkup reply_markup) throws IOException {
        POST postRequest = new POST("editMessageReplyMarkup");
        postRequest.addParameter("inline_message_id", inline_message_id);
        if (null != reply_markup) {
            postRequest.addParameter("reply_markup", jsonUtil.toJson(reply_markup));
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
        if ((null == botServiceResponse) || (!botServiceResponse.isOk())) {
            return false;
        }
        return jsonUtil.fromJson(jsonUtil.toJson(botServiceResponse.getResult()), Boolean.class);
    }

}
