package com.don.socketdemo;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

import com.don.socketdemo.http.HttpClientHelper;
import com.don.socketdemo.http.RequsetManager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Button btnQuery ;
	private TextView tvInfo ;
	private TextView etStockNo ;
	
	//股票代码
	private String socketNo;
	//股票信息
	private String socketInfo;
	
	private String socketPriceInfo;
	
	
	Handler handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			
			
			tvInfo.setText(socketPriceInfo);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvInfo = (TextView)findViewById(R.id.tv_info);
		etStockNo = (EditText)findViewById(R.id.et_stockno);
		btnQuery = (Button)findViewById(R.id.btn_query);
		btnQuery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				socketNo = etStockNo.getText().toString().trim();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							HttpGet httpRequest = new HttpGet("http://hq.sinajs.cn/list="
									+ socketNo);
							HttpResponse response = HttpClientHelper.getHttpClient().execute(
									httpRequest);
							
							
							//获取请求回来的信息
							String result = EntityUtils.toString(response.getEntity());
							socketInfo = result;
							Log.i("", "socketInfo:" + socketInfo);
							//拆分股票信息
							String[] infos = socketInfo.split(",");
							
							if (infos == null && infos.length <= 0) {
								Toast.makeText(MainActivity.this, "无此股票信息", Toast.LENGTH_SHORT).show();
								return;
							}
							
							//下标是3的为 当前价格
							String curPrice = infos[3];
							
							String stockName = infos[0];
							//去除多余的信息
							int index = stockName.indexOf("=");
							socketPriceInfo = stockName.substring(index + 2, stockName.length()) + "的当前股价:" + curPrice;
							
							//线程内不能对界面的控件进行操作，要在handler内进行操作，发送消息通知handler操作
							handler.sendEmptyMessage(999);
							
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
