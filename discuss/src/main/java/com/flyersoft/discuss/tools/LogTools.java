package com.flyersoft.discuss.tools;


import android.util.Log;

import com.flyersoft.discuss.config.AppConfig;


/**
 * Log
 */
public class LogTools {

	public static final String TAG_ACCOUNT = "account";

	/**
	 * 打印信息
	 * 
	 * @param TAG
	 *            标签
	 * @param tmp
	 *            内容
	 */
	public static void showLog(String TAG, String tmp) {
		if (AppConfig.DEBUG)
			Log.i(TAG, "======" + tmp + "======");
	}

	public static void H(String tmp) {
		if (AppConfig.DEBUG)
			showLog("zheng.hu", tmp);
	}

}
