package com.flyersoft.seekbooks.Temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.commons.codec.digest.DigestUtils;

//import com.third.api.integrate.ThirdConfig;

public class YWSignCheck {
	
	public static void main(String[] args) {
		String sign = "Z3CNXhjTgXSfKjBY1cmMadwSXG9WQMPEZMMu2RH2qoQdC91e0zq/MFKEQHR45eiWpR8soojJcyd0lTs1YknRhXDNutc0kz7SDPILq/NsIh15DSuzeQwnpLQbDmuecvDF6k2R917aeqhJzGzCyMR/KwcsodEzddGO4P1NkgetqoHM+LK2dFc0xYyQ2F+uyU723hTO51bznL6Rlr8m1bOm6BUpvAjMHbf3Bppm7GQE5nbEaxbIUX0duoHehKevpBe9dRuDdI2k4aSH/+PWGB253JTBZCJZcqxl+rhUbNGZEqC/1015DSlCAHlf03bBLsD6ulv7zNS/11cRzq/VYCeQOg==";
		String value = "";
		//String value = DigestUtils.md2Hex(sign);
		
		//String value = DigestUtils.sha256Hex(sign);
		//DigestUtils.
		
		//DigestUtils.
		
		//PublicKey publicKey = PublicKey.
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("charset", "utf-8");
		params.put("mchid", "moon");
		params.put("noticeurl", "https://paycallback.reader.qq.com/FoapiPayNotice");
		params.put("openid", "37");
		params.put("out_trade_no", "6534952789102843844");
		params.put("returnurl", "https://paycallback.reader.qq.com/oapiPayReturn");
		params.put("timestamp", "1521537264282");
		
		
		List<String> keys = new ArrayList<String>();
		keys.addAll(params.keySet());
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		String val = null;
		for (String string : keys) {
			val = params.get(string);
			if (null == val || "".equals(val.trim())) {
				continue;
			}
			sb.append(string).append("=").append(val).append("&");
		}
		
		String str = sb.substring(0,sb.length()-1).toString();
		
		System.out.println(str);
		
		byte[] bytes = sign.getBytes();
		
		System.out.println(sign.length());
		/*
		try {
			byte[] date = RSAUtil.decrypt(bytes, ThirdConfig.getYueWenPublicKey());
			value = new String(date);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//byte[] bytes = sign.getBytes();
		byte[] date = str.getBytes();
		
		
		try {
			
			// boolean ss = RSAUtil.verify(date, bytes, ThirdConfig.getYueWenPublicKey());
			
			
			
			boolean ss = RSAUtil.verifySign(RSAUtil.getPublicKey(ThirdConfig.getYueWenPublicKey(), "RSA"), str, bytes);
			
			System.out.println(ss);
			value = new String(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println(value);
	}

}
