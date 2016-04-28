package me.shib.java.lib.jtelebot.service;

import me.shib.java.lib.common.utils.JsonLib;
import me.shib.java.lib.jtelebot.models.updates.Update;
import me.shib.java.lib.microrest.requests.POST;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

final class BotUpdateService {

    private static Map<String, BotUpdateService> botUpdateServiceMap = new HashMap<>();

    private long updateServiceOffset;
    private JsonLib jsonLib;
    private BotServiceWrapper botServiceWrapper;

    private BotUpdateService(String botApiToken, String endPoint) {
        this.jsonLib = new JsonLib();
        this.botServiceWrapper = new BotServiceWrapper(endPoint + "/bot" + botApiToken);
        this.updateServiceOffset = 0;
    }

    /**
     * Creates a singleton object for the given bot API token. For every unique API token, a singleton object is created.
     *
     * @param botApiToken the API token that is given by @BotFather bot
     * @param endPoint    the endpoint to call the Bot API service. Might be used in case of proxy services.
     * @return A singleton instance of the bot's update receiver for a given API token. Returns null if null or empty values are provided.
     */
    static synchronized BotUpdateService getInstance(String botApiToken, String endPoint) {
        if ((botApiToken == null) || (botApiToken.isEmpty())) {
            return null;
        }
        BotUpdateService botUpdateService = botUpdateServiceMap.get(endPoint + "/bot" + botApiToken);
        if (botUpdateService == null) {
            botUpdateService = new BotUpdateService(botApiToken, endPoint);
            botUpdateServiceMap.put(endPoint + "/bot" + botApiToken, botUpdateService);
        }
        return botUpdateService;
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
    synchronized Update[] getUpdates(int timeout, int limit, long offset) throws IOException {
        POST postRequest = new POST("getUpdates");
        if (offset > 0) {
            postRequest.addParameter("offset", "" + offset);
        }
        if ((limit > 0) && (limit <= 100)) {
            postRequest.addParameter("limit", "" + limit);
        }
        if (timeout > 0) {
            postRequest.addParameter("timeout", "" + timeout);
        }
        BotServiceWrapper.BotServiceResponse botServiceResponse = botServiceWrapper.call(postRequest);
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
    synchronized Update[] getUpdates(int timeout, int limit) throws IOException {
        Update[] updates = getUpdates(timeout, limit, updateServiceOffset);
        if (updates.length > 0) {
            updateServiceOffset = updates[updates.length - 1].getUpdate_id() + 1;
        }
        return updates;
    }
}
