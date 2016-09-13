package com.example.aidlclient;

import com.scu.aidldemo.IAIDLServerService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private IAIDLServerService mIaidlServerService = null;
	private ServiceConnection mConnection  = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mIaidlServerService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mIaidlServerService = IAIDLServerService.Stub.asInterface(service);
			try {
				String mText ="Say Hello:"+mIaidlServerService.sayHello()+"\n";
				mText +="书名："+mIaidlServerService.getBook().getBookName()+"\n";
				mText +="价格:"+mIaidlServerService.getBook().getBookPrice();
				mTextView.setText(mText);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};
	private TextView mTextView;
	private Button mButton;
	
	@Override
	protected void onDestroy() {
		try {
			unbindService(mConnection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView)findViewById(R.id.textview);
		mButton = (Button)findViewById(R.id.button);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent service = new Intent("com.scu.aidldemo.IAIDLServerService");
				service.setPackage("com.scu.aidldemo");
				bindService(service, mConnection, BIND_AUTO_CREATE);
			}
		});
	}
}
