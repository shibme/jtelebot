package me.shib.java.lib.jtelebot.service;

import me.shib.java.lib.jtelebot.models.inline.InlineKeyboardMarkup;
import me.shib.java.lib.jtelebot.models.inline.InlineQueryResult;
import me.shib.java.lib.jtelebot.models.types.*;
import me.shib.java.lib.jtelebot.models.updates.Message;
import me.shib.java.lib.jtelebot.models.updates.Update;
import me.shib.java.lib.restiny.HTTPFileDownloader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class TelegramBot {

    private static final String telegramBotServiceEndPoint = "https://api.telegram.org";
    private static final int defaultLongPollInterval = 300;
    private static final int defaultUpdateListLength = 100;
    private static final Map<String, TelegramBot> botMap = new HashMap<>();

    /**
     * Creates a singleton object for the given bot API token. For every unique API token, a singleton object is created.
     *
     * @param botApiToken the API token for the bot
     * @param endPoint    the endpoint to call the Bot API service. Might be used in case of proxy services
     * @return A singleton instance of the bot for the given API token. Returns null if the token is invalid.
     */
    public static synchronized TelegramBot getInstance(String botApiToken, String endPoint) {
        if ((botApiToken == null) || (botApiToken.isEmpty())) {
            return null;
        }
        if ((endPoint == null) || (endPoint.isEmpty())) {
            endPoint = telegramBotServiceEndPoint;
        }
        TelegramBot bot = botMap.get(endPoint + "/bot" + botApiToken);
        if (bot == null) {
            bot = new BotService(botApiToken, endPoint);
            if (bot.getIdentity() != null) {
                botMap.put(endPoint + "/bot" + botApiToken, bot);
            } else {
                bot = null;
            }
        }
        return bot;
    }

    /**
     * Creates a singleton object for the given bot API token. For every unique API token, a singleton object is created.
     *
     * @param botApiToken the API token for the bot
     * @return A singleton instance of the bot for the given API token. Returns null if the token is invalid.
     */
    public static synchronized TelegramBot getInstance(String botApiToken) {
        return getInstance(botApiToken, null);
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
    public abstract Update[] getUpdates(int timeout, int limit, long offset) throws IOException;

    /**
     * Use this method to receive incoming updates using long polling with the given timeout value.
     *
     * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
     * @param limit   Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
     * @return An array of Update objects is returned. Returns an empty array if there aren't any updates.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public abstract Update[] getUpdates(int timeout, int limit) throws IOException;

    /**
     * Use this method to receive incoming updates using long polling with the given timeout value.
     *
     * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
     * @return An array of Update objects is returned. Returns an empty array if there aren't any updates.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Update[] getUpdates(int timeout) throws IOException {
        return getUpdates(timeout, defaultUpdateListLength);
    }

    /**
     * Use this method to receive incoming updates using long polling with timeout value of 5 minutes.
     *
     * @return An array of Update objects is returned. Returns an empty array if there aren't any updates.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Update[] getUpdates() throws IOException {
        Update[] updates = null;
        while ((updates == null) || (updates.length == 0)) {
            updates = getUpdates(defaultLongPollInterval);
        }
        return updates;
    }

    /**
     * Use this method to receive updates immediately (short polling).
     *
     * @return An array of Update objects is returned. Returns an empty array if there aren't any updates.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Update[] getUpdatesImmediately() throws IOException {
        return getUpdates(0);
    }

    /**
     * Gives the API token of the bot that is associated with the object.
     *
     * @return the API token of the bot is returned.
     */
    public abstract String getBotApiToken();

    /**
     * A simple method for testing your bot's auth token.
     *
     * @return basic information about the bot in form of a User object.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public abstract User getMe() throws IOException;

    /**
     * A simple method for getting your bot's last known identity. Updates the identity only when getMe is called.
     *
     * @return basic information about the bot in form of a User object.
     */
    public abstract User getIdentity();

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
    public abstract Message sendMessage(ChatId chat_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException;

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
        return sendMessage(chat_id, text, parse_mode, disable_web_page_preview, reply_to_message_id, reply_markup, false);
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
        return sendMessage(chat_id, text, null);
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
    public abstract Message forwardMessage(ChatId chat_id, ChatId from_chat_id, long message_id, boolean disable_notification) throws IOException;

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
        return forwardMessage(chat_id, from_chat_id, message_id, false);
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
    public abstract Message sendPhoto(ChatId chat_id, InputFile photo, String caption, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException;

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
    public Message sendPhoto(ChatId chat_id, InputFile photo, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        return sendPhoto(chat_id, photo, caption, reply_to_message_id, reply_markup, false);
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
    public Message sendPhoto(ChatId chat_id, InputFile photo, String caption, long reply_to_message_id) throws IOException {
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
    public Message sendPhoto(ChatId chat_id, InputFile photo, String caption) throws IOException {
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
    public Message sendPhoto(ChatId chat_id, InputFile photo) throws IOException {
        return sendPhoto(chat_id, photo, null);
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
    public abstract Message sendAudio(ChatId chat_id, InputFile audio, int duration, String performer, String title, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException;

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
    public Message sendAudio(ChatId chat_id, InputFile audio, int duration, String performer, String title, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        return sendAudio(chat_id, audio, duration, performer, title, reply_to_message_id, reply_markup, false);
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
    public Message sendAudio(ChatId chat_id, InputFile audio, int duration, String performer, String title, long reply_to_message_id) throws IOException {
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
    public Message sendAudio(ChatId chat_id, InputFile audio, int duration, String performer, String title) throws IOException {
        return sendAudio(chat_id, audio, duration, performer, title, 0);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id   Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param audio     Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration  Duration of the audio in seconds
     * @param performer Performer
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendAudio(ChatId chat_id, InputFile audio, int duration, String performer) throws IOException {
        return sendAudio(chat_id, audio, duration, performer, null);
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
    public Message sendAudio(ChatId chat_id, InputFile audio, int duration) throws IOException {
        return sendAudio(chat_id, audio, duration, null);
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param audio   Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new file by passing a File object.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendAudio(ChatId chat_id, InputFile audio) throws IOException {
        return sendAudio(chat_id, audio, 0);
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
    public abstract Message sendDocument(ChatId chat_id, InputFile document, String caption, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException;

    /**
     * Use this method to send general files. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param document            File to send. You can either pass a file_id as String to resend a file that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param caption             Document caption (may also be used when resending documents by file_id), 0-200 characters
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendDocument(ChatId chat_id, InputFile document, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        return sendDocument(chat_id, document, caption, reply_to_message_id, reply_markup, false);
    }

    /**
     * Use this method to send general files. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param document            File to send. You can either pass a file_id as String to resend a file that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param caption             Document caption (may also be used when resending documents by file_id), 0-200 characters
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendDocument(ChatId chat_id, InputFile document, String caption, long reply_to_message_id) throws IOException {
        return sendDocument(chat_id, document, caption, reply_to_message_id, null);
    }

    /**
     * Use this method to send general files. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param document File to send. You can either pass a file_id as String to resend a file that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param caption  Document caption (may also be used when resending documents by file_id), 0-200 characters
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendDocument(ChatId chat_id, InputFile document, String caption) throws IOException {
        return sendDocument(chat_id, document, caption, 0);
    }

    /**
     * Use this method to send general files. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param document File to send. You can either pass a file_id as String to resend a file that is already on the Telegram servers, or upload a new file by passing a File object.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendDocument(ChatId chat_id, InputFile document) throws IOException {
        return sendDocument(chat_id, document, null);
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
    public abstract Message sendSticker(ChatId chat_id, InputFile sticker, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException;

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
    public Message sendSticker(ChatId chat_id, InputFile sticker, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        return sendSticker(chat_id, sticker, reply_to_message_id, reply_markup, false);
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
    public Message sendSticker(ChatId chat_id, InputFile sticker, long reply_to_message_id) throws IOException {
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
    public Message sendSticker(ChatId chat_id, InputFile sticker) throws IOException {
        return sendSticker(chat_id, sticker, 0);
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
    public abstract Message sendVideo(ChatId chat_id, InputFile video, int duration, String caption, long reply_to_message_id, ReplyMarkup reply_markup, int width, int height, boolean disable_notification) throws IOException;

    /**
     * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param video               Video to send. You can either pass a file_id as String to resend a video that is already on the Telegram servers, or upload a new file by passing a File object.
     * @param duration            Duration of sent video in seconds
     * @param caption             Video caption (may also be used when resending videos by file_id).
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @param width               Video width
     * @param height              Video height
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVideo(ChatId chat_id, InputFile video, int duration, String caption, long reply_to_message_id, ReplyMarkup reply_markup, int width, int height) throws IOException {
        return sendVideo(chat_id, video, duration, caption, reply_to_message_id, reply_markup, width, height, false);
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
    public Message sendVideo(ChatId chat_id, InputFile video, int duration, String caption, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        return sendVideo(chat_id, video, duration, caption, reply_to_message_id, reply_markup, 0, 0);
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
    public Message sendVideo(ChatId chat_id, InputFile video, int duration, String caption, long reply_to_message_id) throws IOException {
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
    public Message sendVideo(ChatId chat_id, InputFile video, int duration, String caption) throws IOException {
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
    public Message sendVideo(ChatId chat_id, InputFile video, int duration) throws IOException {
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
    public Message sendVideo(ChatId chat_id, InputFile video) throws IOException {
        return sendVideo(chat_id, video, 0);
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
    public abstract Message sendVoice(ChatId chat_id, InputFile voice, int duration, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException;

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
    public Message sendVoice(ChatId chat_id, InputFile voice, int duration, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        return sendVoice(chat_id, voice, duration, reply_to_message_id, reply_markup, false);
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
    public Message sendVoice(ChatId chat_id, InputFile voice, int duration, long reply_to_message_id) throws IOException {
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
    public Message sendVoice(ChatId chat_id, InputFile voice, int duration) throws IOException {
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
    public Message sendVoice(ChatId chat_id, InputFile voice) throws IOException {
        return sendVoice(chat_id, voice, 0);
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
    public abstract Message sendLocation(ChatId chat_id, float latitude, float longitude, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException;

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
        return sendLocation(chat_id, latitude, longitude, reply_to_message_id, reply_markup, false);
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
    public abstract Message sendVenue(ChatId chat_id, float latitude, float longitude, String title, String address, String foursquare_id, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException;

    /**
     * Use this method to send information about a venue.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param latitude            Latitude of location
     * @param longitude           Longitude of location
     * @param title               Name of the venue
     * @param address             Address of the venue
     * @param foursquare_id       Foursquare identifier of the venue
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVenue(ChatId chat_id, float latitude, float longitude, String title, String address, String foursquare_id, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        return sendVenue(chat_id, latitude, longitude, title, address, foursquare_id, reply_to_message_id, reply_markup, false);
    }

    /**
     * Use this method to send information about a venue.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param latitude            Latitude of location
     * @param longitude           Longitude of location
     * @param title               Name of the venue
     * @param address             Address of the venue
     * @param foursquare_id       Foursquare identifier of the venue
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVenue(ChatId chat_id, float latitude, float longitude, String title, String address, String foursquare_id, long reply_to_message_id) throws IOException {
        return sendVenue(chat_id, latitude, longitude, title, address, foursquare_id, reply_to_message_id, null);
    }

    /**
     * Use this method to send information about a venue.
     *
     * @param chat_id       Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param latitude      Latitude of location
     * @param longitude     Longitude of location
     * @param title         Name of the venue
     * @param address       Address of the venue
     * @param foursquare_id Foursquare identifier of the venue
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVenue(ChatId chat_id, float latitude, float longitude, String title, String address, String foursquare_id) throws IOException {
        return sendVenue(chat_id, latitude, longitude, title, address, foursquare_id, 0);
    }

    /**
     * Use this method to send information about a venue.
     *
     * @param chat_id   Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param latitude  Latitude of location
     * @param longitude Longitude of location
     * @param title     Name of the venue
     * @param address   Address of the venue
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendVenue(ChatId chat_id, float latitude, float longitude, String title, String address) throws IOException {
        return sendVenue(chat_id, latitude, longitude, title, address, null);
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
    public abstract Message sendContact(ChatId chat_id, String phone_number, String first_name, String last_name, long reply_to_message_id, ReplyMarkup reply_markup, boolean disable_notification) throws IOException;

    /**
     * Use this method to send phone contacts.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param phone_number        Contact's phone number
     * @param first_name          Contact's first name
     * @param last_name           Contact's last name
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. An object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendContact(ChatId chat_id, String phone_number, String first_name, String last_name, long reply_to_message_id, ReplyMarkup reply_markup) throws IOException {
        return sendContact(chat_id, phone_number, first_name, last_name, reply_to_message_id, reply_markup, false);
    }

    /**
     * Use this method to send phone contacts.
     *
     * @param chat_id             Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param phone_number        Contact's phone number
     * @param first_name          Contact's first name
     * @param last_name           Contact's last name
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendContact(ChatId chat_id, String phone_number, String first_name, String last_name, long reply_to_message_id) throws IOException {
        return sendContact(chat_id, phone_number, first_name, last_name, reply_to_message_id, null);
    }

    /**
     * Use this method to send phone contacts.
     *
     * @param chat_id      Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param phone_number Contact's phone number
     * @param first_name   Contact's first name
     * @param last_name    Contact's last name
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendContact(ChatId chat_id, String phone_number, String first_name, String last_name) throws IOException {
        return sendContact(chat_id, phone_number, first_name, last_name, 0);
    }

    /**
     * Use this method to send phone contacts.
     *
     * @param chat_id      Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param phone_number Contact's phone number
     * @param first_name   Contact's first name
     * @return On success, the sent Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message sendContact(ChatId chat_id, String phone_number, String first_name) throws IOException {
        return sendContact(chat_id, phone_number, first_name, null);
    }

    /**
     * Use this method when you need to tell the user that something is happening on the bot's side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status). We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
     *
     * @param chat_id Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param action  Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files, upload_document for general files, find_location for location data.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public abstract boolean sendChatAction(ChatId chat_id, ChatAction action) throws IOException;

    /**
     * Use this method to get a list of profile pictures for a user.
     *
     * @param user_id Unique identifier of the target user
     * @param offset  Sequential number of the first photo to be returned. By default, all photos are returned.
     * @param limit   Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
     * @return Returns a UserProfilePhotos object.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public abstract UserProfilePhotos getUserProfilePhotos(long user_id, int offset, int limit) throws IOException;

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
     * Use this method to get a InputFile object for downloading. For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id File identifier to get TFile object
     * @return On success, a TFile object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public abstract TFile getFile(String file_id) throws IOException;

    /**
     * Use this method to download and return a File object of a given file_id . For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id           File identifier to for the file to be downloaded
     * @param downloadToFile    The local file where the content has to be downloaded
     * @param waitForCompletion Waits until the download is complete
     * @return On success, a File object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public abstract HTTPFileDownloader.DownloadProgress downloadToFile(String file_id, File downloadToFile, boolean waitForCompletion) throws IOException;

    /**
     * Use this method to download and return a File object of a given file_id . For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id        File identifier to for the file to be downloaded
     * @param downloadToFile The local file where the content has to be downloaded
     * @return On success, a File object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public abstract File downloadFile(String file_id, File downloadToFile) throws IOException;

    /**
     * Use this method to download and return a File object of a given file_id . For the moment, bots can download files of up to 20MB in size.
     *
     * @param file_id File identifier to for the file to be downloaded
     * @return On success, a File object is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public File downloadFile(String file_id) throws IOException {
        return downloadFile(file_id, null);
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
    public abstract boolean answerInlineQuery(String inline_query_id, InlineQueryResult[] results, String next_offset, boolean is_personal, int cache_time, String switch_pm_text, String switch_pm_parameter) throws IOException;

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
        return answerInlineQuery(inline_query_id, results, next_offset, is_personal, cache_time, null, null);
    }

    /**
     * Use this method to send answers to an inline query. No more than 50 results per query are allowed.
     *
     * @param inline_query_id Unique identifier for the answered query
     * @param results         A JSON-serialized array of results for the inline query
     * @param next_offset     Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don‘t support pagination. Offset length can’t exceed 64 bytes.
     * @param is_personal     Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean answerInlineQuery(String inline_query_id, InlineQueryResult[] results, String next_offset, boolean is_personal) throws IOException {
        return answerInlineQuery(inline_query_id, results, next_offset, is_personal, -1);
    }

    /**
     * Use this method to send answers to an inline query. No more than 50 results per query are allowed.
     *
     * @param inline_query_id Unique identifier for the answered query
     * @param results         A JSON-serialized array of results for the inline query
     * @param next_offset     Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don‘t support pagination. Offset length can’t exceed 64 bytes.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean answerInlineQuery(String inline_query_id, InlineQueryResult[] results, String next_offset) throws IOException {
        return answerInlineQuery(inline_query_id, results, next_offset, false);
    }

    /**
     * Use this method to send answers to an inline query. No more than 50 results per query are allowed.
     *
     * @param inline_query_id Unique identifier for the answered query
     * @param results         A JSON-serialized array of results for the inline query
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean answerInlineQuery(String inline_query_id, InlineQueryResult[] results) throws IOException {
        return answerInlineQuery(inline_query_id, results, null);
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
    public abstract boolean kickChatMember(ChatId chat_id, long user_id) throws IOException;

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
    public abstract boolean unbanChatMember(ChatId chat_id, long user_id) throws IOException;

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
    public abstract boolean answerCallbackQuery(String callback_query_id, String text, boolean show_alert) throws IOException;

    /**
     * Use this method to send answers to callback queries sent from inline keyboards.
     * The answer will be displayed to the user as a notification at the top of the chat screen or as an alert.
     *
     * @param callback_query_id Unique identifier for the query to be answered
     * @param text              Text of the notification. If not specified, nothing will be shown to the user
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean answerCallbackQuery(String callback_query_id, String text) throws IOException {
        return answerCallbackQuery(callback_query_id, text, false);
    }

    /**
     * Use this method to send answers to callback queries sent from inline keyboards.
     * The answer will be displayed to the user as a notification at the top of the chat screen or as an alert.
     *
     * @param callback_query_id Unique identifier for the query to be answered
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean answerCallbackQuery(String callback_query_id) throws IOException {
        return answerCallbackQuery(callback_query_id, null);
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
    public abstract Message editMessageText(ChatId chat_id, long message_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, InlineKeyboardMarkup reply_markup) throws IOException;

    /**
     * Use this method to edit text messages sent by the bot.
     *
     * @param chat_id                  Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param message_id               Unique identifier of the sent message
     * @param text                     New text of the message
     * @param parse_mode               Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
     * @param disable_web_page_preview Disables link previews for links in this message
     * @return On success, if edited message is sent by the bot, the edited Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message editMessageText(ChatId chat_id, long message_id, String text, ParseMode parse_mode, boolean disable_web_page_preview) throws IOException {
        return editMessageText(chat_id, message_id, text, parse_mode, disable_web_page_preview, null);
    }

    /**
     * Use this method to edit text messages sent by the bot.
     *
     * @param chat_id    Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param message_id Unique identifier of the sent message
     * @param text       New text of the message
     * @param parse_mode Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
     * @return On success, if edited message is sent by the bot, the edited Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message editMessageText(ChatId chat_id, long message_id, String text, ParseMode parse_mode) throws IOException {
        return editMessageText(chat_id, message_id, text, parse_mode, false);
    }

    /**
     * Use this method to edit text messages sent by the bot.
     *
     * @param chat_id    Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param message_id Unique identifier of the sent message
     * @param text       New text of the message
     * @return On success, if edited message is sent by the bot, the edited Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message editMessageText(ChatId chat_id, long message_id, String text) throws IOException {
        return editMessageText(chat_id, message_id, text, null);
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
    public abstract boolean editMessageText(String inline_message_id, String text, ParseMode parse_mode, boolean disable_web_page_preview, InlineKeyboardMarkup reply_markup) throws IOException;

    /**
     * Use this method to edit text messages sent via the bot (for inline bots).
     *
     * @param inline_message_id        Identifier of the inline message
     * @param text                     New text of the message
     * @param parse_mode               Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
     * @param disable_web_page_preview Disables link previews for links in this message
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean editMessageText(String inline_message_id, String text, ParseMode parse_mode, boolean disable_web_page_preview) throws IOException {
        return editMessageText(inline_message_id, text, parse_mode, disable_web_page_preview, null);
    }

    /**
     * Use this method to edit text messages sent via the bot (for inline bots).
     *
     * @param inline_message_id Identifier of the inline message
     * @param text              New text of the message
     * @param parse_mode        Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean editMessageText(String inline_message_id, String text, ParseMode parse_mode) throws IOException {
        return editMessageText(inline_message_id, text, parse_mode, false);
    }

    /**
     * Use this method to edit text messages sent via the bot (for inline bots).
     *
     * @param inline_message_id Identifier of the inline message
     * @param text              New text of the message
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean editMessageText(String inline_message_id, String text) throws IOException {
        return editMessageText(inline_message_id, text, null);
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
    public abstract Message editMessageCaption(ChatId chat_id, long message_id, String caption, InlineKeyboardMarkup reply_markup) throws IOException;

    /**
     * Use this method to edit captions of messages sent by the bot.
     *
     * @param chat_id    Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param message_id Unique identifier of the sent message
     * @param caption    New caption of the message
     * @return On success, if edited message is sent by the bot, the edited Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message editMessageCaption(ChatId chat_id, long message_id, String caption) throws IOException {
        return editMessageCaption(chat_id, message_id, caption, null);
    }

    /**
     * Use this method to edit captions of messages sent by the bot.
     *
     * @param chat_id    Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param message_id Unique identifier of the sent message
     * @return On success, if edited message is sent by the bot, the edited Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message editMessageCaption(ChatId chat_id, long message_id) throws IOException {
        return editMessageCaption(chat_id, message_id, null);
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
    public abstract boolean editMessageCaption(String inline_message_id, String caption, InlineKeyboardMarkup reply_markup) throws IOException;

    /**
     * Use this method to edit captions of messages sent via the bot (for inline bots).
     *
     * @param inline_message_id Identifier of the inline message
     * @param caption           New caption of the message
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean editMessageCaption(String inline_message_id, String caption) throws IOException {
        return editMessageCaption(inline_message_id, caption, null);
    }

    /**
     * Use this method to edit captions of messages sent via the bot (for inline bots).
     *
     * @param inline_message_id Identifier of the inline message
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean editMessageCaption(String inline_message_id) throws IOException {
        return editMessageCaption(inline_message_id, null);
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
    public abstract Message editMessageReplyMarkup(ChatId chat_id, long message_id, InlineKeyboardMarkup reply_markup) throws IOException;

    /**
     * Use this method to edit only the reply markup of messages sent by the bot.
     *
     * @param chat_id    Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     * @param message_id Unique identifier of the sent message
     * @return On success, if edited message is sent by the bot, the edited Message is returned.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public Message editMessageReplyMarkup(ChatId chat_id, long message_id) throws IOException {
        return editMessageReplyMarkup(chat_id, message_id, null);
    }

    /**
     * Use this method to edit only the reply markup of messages sent via the bot (for inline bots).
     *
     * @param inline_message_id Identifier of the inline message
     * @param reply_markup      A JSON-serialized object for an inline keyboard.
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public abstract boolean editMessageReplyMarkup(String inline_message_id, InlineKeyboardMarkup reply_markup) throws IOException;

    /**
     * Use this method to edit only the reply markup of messages sent via the bot (for inline bots).
     *
     * @param inline_message_id Identifier of the inline message
     * @return On success, returns True.
     * @throws IOException an exception is thrown in case of any service call failures
     */
    public boolean editMessageReplyMarkup(String inline_message_id) throws IOException {
        return editMessageReplyMarkup(inline_message_id, null);
    }

}
