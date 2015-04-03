package com.wechat.pojo;

import java.util.List;
import java.util.Map;

public class PicMessage {
	
	private Map<String,String> filter;
	
	private Map<String,String> mpnews;
	
	private String msgtype;

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Map<String, String> getMpnews() {
		return mpnews;
	}

	public void setMpnews(Map<String, String> mpnews) {
		this.mpnews = mpnews;
	}

	public Map<String, String> getFilter() {
		return filter;
	}

	public void setFilter(Map<String, String> filter) {
		this.filter = filter;
	}
	
	
	

}
