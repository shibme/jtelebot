package com.nudanam.java.telegram.bot.service;

import java.io.IOException;
import java.util.ArrayList;

import com.nudanam.java.rest.client.lib.JsonLib;
import com.nudanam.java.rest.client.lib.Parameter;
import com.nudanam.java.rest.client.lib.ServiceAdapter;
import com.nudanam.java.rest.client.lib.ServiceResponse;

public class BotServiceWrapper {
	
	private ServiceAdapter serviceAdapter;
	
	public BotServiceWrapper(String endPoint) {
		serviceAdapter = new ServiceAdapter(endPoint);
	}
	
	public BotServiceWrapperResponse post(String apiName, ArrayList<Parameter> params) throws IOException {
		ServiceResponse serviceResponse = serviceAdapter.post(apiName, params);
		if(serviceResponse.getStatusCode() != 200) {
			return null;
		}
		BotServiceWrapperResponse response = JsonLib.fromJson(serviceResponse.getResponse(), BotServiceWrapperResponse.class);
		return response;
	}
	
	public BotServiceWrapperResponse post(String apiName) throws IOException {
		return post(apiName, null);
	}
	
}
