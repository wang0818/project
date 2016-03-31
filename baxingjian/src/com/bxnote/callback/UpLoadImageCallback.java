package com.bxnote.callback;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.tauth.IRequestListener;

public  class UpLoadImageCallback implements IRequestListener {

	@Override
	public void onComplete(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectTimeoutException(ConnectTimeoutException arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHttpStatusException(
			com.tencent.utils.HttpUtils.HttpStatusException arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIOException(IOException arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJSONException(JSONException arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMalformedURLException(MalformedURLException arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNetworkUnavailableException(
			com.tencent.utils.HttpUtils.NetworkUnavailableException arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSocketTimeoutException(SocketTimeoutException arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnknowException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}}
