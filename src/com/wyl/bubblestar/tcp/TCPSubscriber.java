package com.wyl.bubblestar.tcp;

import org.json.JSONObject;

public interface TCPSubscriber {

	public void onReceiveTCP(JSONObject jsonObject);
	
}
