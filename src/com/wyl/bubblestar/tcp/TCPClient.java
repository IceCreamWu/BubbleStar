package com.wyl.bubblestar.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.wyl.bubblestar.config.NetworkConfig;
import com.wyl.bubblestar.utils.SocketUtil;


public class TCPClient {
	
	public final static int STATE_DISCONNECTED = 0;
	public final static int STATE_CONNECTED = 1;
	
	private static volatile TCPClient client = null;
	private Socket clientSocket = new Socket();
	private int state = TCPClient.STATE_DISCONNECTED;
	private HashSet<TCPSubscriber> subscriberSet = new HashSet<TCPSubscriber>();
	
	private TCPClient() {
		
	}
	
	public static TCPClient getInstance() {
		if (client == null) {
			synchronized(TCPClient.class) {
				if (client == null) {
					client = new TCPClient();
				}
			}
		}
		return client;
	}
	
	public void connect() {
		if (state == TCPClient.STATE_DISCONNECTED) {
			new Thread() {
				@Override
				public void run() {
					try {
						SocketAddress socketAddress = new InetSocketAddress(NetworkConfig.TCP_SERVER_HOST, NetworkConfig.TCP_SERVER_PORT);
						clientSocket.connect(socketAddress);
						state = TCPClient.STATE_CONNECTED;
						processLoop();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
	private void processLoop() {
		while (state == TCPClient.STATE_CONNECTED) {
			try {
				byte[] data = SocketUtil.readData(clientSocket);
				process(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				state = TCPClient.STATE_DISCONNECTED;
				e.printStackTrace();
			}
		}
	}

	private void process(byte[] data) {
		String jsonString = new String(data);
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			for (Iterator<TCPSubscriber> iterator = subscriberSet.iterator(); iterator.hasNext();) {
				 TCPSubscriber subscriber = (TCPSubscriber) iterator.next();
				subscriber.onReceiveTCP(jsonObject);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void sendData(byte[] data) {
		try {
			SocketUtil.sendData(clientSocket, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			state = TCPClient.STATE_DISCONNECTED;
			e.printStackTrace();
		}
	}
	
	public void register(TCPSubscriber subscriber) {
		if (!subscriberSet.contains(subscriber)) {
			subscriberSet.add(subscriber);
		}
	}
	
	public void unregister(TCPSubscriber subscriber) {
		if (subscriberSet.contains(subscriber)) {
			subscriberSet.remove(subscriber);
		}
	}
	
}
