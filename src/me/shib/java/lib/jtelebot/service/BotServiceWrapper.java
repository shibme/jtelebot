package me.shib.java.lib.jtelebot.service;

import me.shib.java.lib.common.utils.JsonLib;
import me.shib.java.lib.rest.client.Parameter;
import me.shib.java.lib.rest.client.ServiceAdapter;
import me.shib.java.lib.rest.client.ServiceResponse;

import java.io.IOException;
import java.util.ArrayList;

public class BotServiceWrapper {

    private ServiceAdapter serviceAdapter;
    private JsonLib jsonLib;

    public BotServiceWrapper(String endPoint, JsonLib jsonLib) {
        serviceAdapter = new ServiceAdapter(endPoint);
        this.jsonLib = jsonLib;
    }

    public BotServiceResponse post(String apiName, ArrayList<Parameter> params) throws IOException {
        ServiceResponse serviceResponse = serviceAdapter.post(apiName, params);
        if (serviceResponse.getStatusCode() != 200) {
            return null;
        }
        return jsonLib.fromJson(serviceResponse.getResponse(), BotServiceResponse.class);
    }

    public BotServiceResponse get(String apiName, ArrayList<Parameter> params) throws IOException {
        ServiceResponse serviceResponse = serviceAdapter.get(apiName, params);
        if (serviceResponse.getStatusCode() != 200) {
            return null;
        }
        BotServiceResponse response = jsonLib.fromJson(serviceResponse.getResponse(), BotServiceResponse.class);
        return response;
    }

    public class BotServiceResponse {
        private boolean ok;
        private Object result;

        private BotServiceResponse() {
            this.ok = false;
            this.result = null;
        }

        public boolean isOk() {
            return ok;
        }

        public Object getResult() {
            return result;
        }
    }

}
