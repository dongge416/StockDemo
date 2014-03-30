package com.don.socketdemo.http;

import com.don.socketdemo.runnable.SocketRunnable;


public class RequsetManager {

	private static RequsetManager requsetManager = null;
	
	private RequsetManager(){};
	
	public static RequsetManager getInstance(){
		if (requsetManager == null) {
			requsetManager = new RequsetManager();
		}
		return requsetManager;
	}
	
	

	
	/**
	 * 获取股票信息
	 * @return
	 */
	public  String getSocketInfo( String socketNo){
		String socketInfo = "";
		new Thread(new SocketRunnable(socketNo, socketInfo)).start();
		return socketInfo;
	}
	
}
