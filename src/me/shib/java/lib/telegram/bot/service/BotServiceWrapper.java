package me.shib.java.lib.telegram.bot.service;

import java.io.IOException;
import java.util.ArrayList;

import me.shib.java.lib.rest.client.JsonLib;
import me.shib.java.lib.rest.client.Parameter;
import me.shib.java.lib.rest.client.ServiceAdapter;
import me.shib.java.lib.rest.client.ServiceResponse;

public class BotServiceWrapper {
	
	private ServiceAdapter serviceAdapter;
	private JsonLib jsonLib;
	
	public BotServiceWrapper(String endPoint, JsonLib jsonLib) {
		serviceAdapter = new ServiceAdapter(endPoint);
		this.jsonLib = jsonLib;
	}
	
	public BotServiceWrapperResponse post(String apiName, ArrayList<Parameter> params) throws IOException {
		ServiceResponse serviceResponse = serviceAdapter.post(apiName, params);
		if(serviceResponse.getStatusCode() != 200) {
			return null;
		}
		BotServiceWrapperResponse response = jsonLib.fromJson(serviceResponse.getResponse(), BotServiceWrapperResponse.class);
		return response;
	}
	
	public BotServiceWrapperResponse post(String apiName) throws IOException {
		return post(apiName, null);
	}
	
	public BotServiceWrapperResponse get(String apiName, ArrayList<Parameter> params) throws IOException {
		ServiceResponse serviceResponse = serviceAdapter.get(apiName, params);
		if(serviceResponse.getStatusCode() != 200) {
			return null;
		}
		BotServiceWrapperResponse response = jsonLib.fromJson(serviceResponse.getResponse(), BotServiceWrapperResponse.class);
		return response;
	}
	
	public BotServiceWrapperResponse get(String apiName) throws IOException {
		return get(apiName, null);
	}
	
}
