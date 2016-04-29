package me.shib.java.lib.jtelebot.service;

import me.shib.java.lib.restiny.MicroRESTClient;
import me.shib.java.lib.restiny.Response;
import me.shib.java.lib.restiny.requests.Request;

import java.io.IOException;

final class BotServiceWrapper {

    private MicroRESTClient microRESTClient;

    BotServiceWrapper(String endPoint) {
        this.microRESTClient = new MicroRESTClient(endPoint);
    }

    BotServiceResponse call(Request request) throws IOException {
        Response response = microRESTClient.call(request);
        if (response.getStatusCode() != 200) {
            return null;
        }
        return response.getResponse(BotServiceResponse.class);
    }

    class BotServiceResponse {
        private boolean ok;
        private Object result;

        private BotServiceResponse() {
            this.ok = false;
            this.result = null;
        }

        boolean isOk() {
            return ok;
        }

        Object getResult() {
            return result;
        }
    }

}
