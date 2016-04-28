package me.shib.java.lib.jtelebot.service;

import me.shib.java.lib.microrest.MicroRESTClient;
import me.shib.java.lib.microrest.Response;
import me.shib.java.lib.microrest.requests.Request;

import java.io.IOException;

final class BotServiceWrapper {

    private String endPoint;

    BotServiceWrapper(String endPoint) {
        this.endPoint = endPoint;
    }

    BotServiceResponse call(Request request) throws IOException {
        Response response = new MicroRESTClient(endPoint).call(request);
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
