package com.wyl.bubblestar;

import com.wyl.bubblestar.tcp.TCPClient;

import android.app.Application;

public class CustomApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		TCPClient.getInstance().connect();
	}
	
}
