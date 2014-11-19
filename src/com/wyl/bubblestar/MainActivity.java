package com.wyl.bubblestar;

import org.json.JSONException;
import org.json.JSONObject;

import com.wyl.bubblestar.tcp.TCPClient;
import com.wyl.bubblestar.tcp.TCPSubscriber;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements TCPSubscriber {
	
	@InjectView(R.id.text_chat) TextView chatText;
	@InjectView(R.id.button) Button connectBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		TCPClient.getInstance().register(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		TCPClient.getInstance().unregister(this);
	}
	
	@OnClick(R.id.button)
	public void connect() {
		connectBtn.setText("Sending...");
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("username", "wyl");
			TCPClient.getInstance().sendData(jsonObject.toString().getBytes());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onReceiveTCP(final JSONObject jsonObject) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				chatText.append(jsonObject.toString() + "\n");
				connectBtn.setText("Send");
			}
		});
	}
}
