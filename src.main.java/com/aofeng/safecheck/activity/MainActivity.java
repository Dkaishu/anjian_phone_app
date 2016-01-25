package com.aofeng.safecheck.activity;

import gueei.binding.Binder;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.aofeng.safecheck.R;
import com.aofeng.safecheck.modelview.MainModel;
import com.aofeng.safecheck.service.GeoService;
import com.aofeng.safecheck.service.ServiceManager;
import com.aofeng.utils.Util;
import com.aofeng.utils.Vault;

public class MainActivity extends Activity {
	public ServiceManager service;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainModel model = new MainModel(this);
		Binder.setAndBindContentView(this, R.layout.main, model);		
		String cname = Util.getSharedPreference(this, Vault.CHECKER_NAME);
		((TextView)findViewById(R.id.loginUserName)).setText("当前用户：" +cname);
		if(service == null)
		{
			service = new ServiceManager(this, GeoService.class, new Handler() {
	        	@Override
	        	public void handleMessage(Message msg) {
	        	}
	        });
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		service.start();
	}
	
	
    @Override
	protected void onPause() {
		super.onPause();
		service.unbind();
	}
}
