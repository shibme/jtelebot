package me.shib.java.lib.jtelebot.service;

import me.shib.java.lib.restiny.RESTinyClient;
import me.shib.java.lib.restiny.Response;
import me.shib.java.lib.restiny.requests.Request;

import java.io.IOException;

final class BotServiceWrapper {

    private RESTinyClient resTinyClient;

    BotServiceWrapper(String endPoint) {
        this.resTinyClient = new RESTinyClient(endPoint);
    }

    BotServiceResponse call(Request request) throws IOException {
        Response response = resTinyClient.call(request);
        if (response.getStatusCode() != 200) {
            return null;
        }
        return response.getResponse(BotServiceResponse.class);
    }

    class BotServiceResponse {
        private boolean ok;
        private int error_code;
        private String description;
        private Object result;

        private BotServiceResponse() {
            this.ok = false;
            this.error_code = 0;
            this.description = null;
            this.result = null;
        }

        boolean isOk() {
            return ok;
        }

        public int getError_code() {
            return error_code;
        }

        public String getDescription() {
            return description;
        }

        Object getResult() {
            return result;
        }
    }

}
