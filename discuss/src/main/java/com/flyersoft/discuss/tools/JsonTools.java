package com.flyersoft.discuss.tools;

import com.google.gson.Gson;

/**
 * json转换工具
 */
public class JsonTools {

	public static <T> T toObject(String tmp, Class<T> classT) {
		return new Gson().fromJson(tmp, classT);
	}

	public static String toJson(Object o) {
		return new Gson().toJson(o);
	}

}
