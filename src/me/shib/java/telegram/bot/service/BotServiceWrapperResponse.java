package me.shib.java.telegram.bot.service;

public class BotServiceWrapperResponse {
	
	private boolean ok;
	private Object result;
	
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
