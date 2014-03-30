package com.don.socketdemo.runnable;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.don.socketdemo.http.HttpClientHelper;

public class SocketRunnable implements Runnable {
	private String socketNo = "";
	private String socketInfo = "";

	public SocketRunnable(String socketNo, String socketInfo) {
		this.socketNo = socketNo;
		this.socketInfo = socketInfo;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			HttpGet httpRequest = new HttpGet("http://hq.sinajs.cn/list="
					+ this.socketNo);
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpResponse response = HttpClientHelper.getHttpClient().execute(
					httpRequest);
			String result = EntityUtils.toString(response.getEntity());
			this.socketInfo = result;
			Log.i("", "socketInfo:" + this.socketInfo);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
