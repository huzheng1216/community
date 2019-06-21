package com.flyersoft.seekbooks;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Base64;


public class SignDemo {

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> createParamFilter(Map<String, String> sArray) {
		Map<String, String> result = new HashMap<String, String>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> chargeParamFilter(Map<String, String> sArray) {
		Map<String, String> result = new HashMap<String, String>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * RSA签名
	 *
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param encode
	 *            字符集编码
	 * @return 签名值
	 */
	public static boolean doCheck(String content, String sign, String publicKey, String encode) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKey, Base64.DEFAULT);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes(encode));
			boolean bverify = signature.verify(Base64.decode(sign, Base64.DEFAULT));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * RSA签名
	 *
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param encode
	 *            字符集编码
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String encode) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey, Base64.DEFAULT));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSA");
			signature.initSign(priKey);
			signature.update(content.getBytes(encode));
			byte[] signed = signature.sign();
			return Base64.encodeToString(signed, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String doMyChargeOrder() {
		// 你们的私钥
		String myPrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIR2Jk2Op+Ml+KMEgYBYTcxsiYtjgf6jyH6ppbowqkk/0gbuO0ZK+hFCcdYQEW1lTc6QuuZZwGIMqRGqzwbKMnhQrR7+G33puTLSIhLEhZ0aqJFFA3WVf5UU0sU3SBE1BnLkcpqcoLqNEc/JREO/IjGbJxg1P+ZWWWl6POBHkIZDAgMBAAECgYBg28wyVigpjvB6s6bGZMuuuYbyJ/c8biMDi6WlukQqf8JiUv2A7v5gdIetEd7McZEthONF4Uct6zgNtSrhOiUghFHgOs8NRSrlZsUMeepZ/ntNDqZB4mEaiwcon4HbMFB6kuqRc8w5ArMlMeNIW9tpyuXQuTnRcaYHlavYQmdqgQJBAOmsCjqaw7KhtDMpccin7VkGVTPoSnRon+sSIZF1ASDQ2GMdI6hd0UYuw57YyFp0UAK8rWXkX+rJtRjq9lpp9aECQQCRHlo3hkhdclEF+ThyOB3cjPGe6zywvel8GIyev8OEK0noe2bUemZW1lr/ZIk70yCsFksP6w+5NHpQZkckp+ljAkBE45RsbJ4PNr8CalCCQIenvEc4M15n7URgMAs3b9AyVX/F0JxnkakV0MmZNf3zNOE1vVw9ctOYS3kZbIPOafihAkBu02FOuiyVwPIDv9rNz/FuN+1m0nvc9oxTi2QI8KZeT7j35RYY+wkhFD8WgC8WEx0Gj1fv5jJx3JP0xsdaRSnFAkBD6aA9tZEUe+9TwkDM0bBe2hrIMYoljOiNpC6px/TRP9zU6bCU8yJjWju/AjdZDpCnMTwoBYSRutNKjjyTqlbB";
		// 请求参数
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("total_amount", "1.0");
		signMap.put("trade_no", "1802111217236282");
		signMap.put("body", "");
		signMap.put("mchid", "pabank");
		signMap.put("sign_type", "RSA2");
		signMap.put("charset", "utf-8");

		signMap.put("out_trade_no", "6521108370476656078");
		signMap.put("paytime", "2018-02-11 09:51:23");
		signMap.put("trade_status", "SUCCESS");
		signMap.put("payfee_type", "￥");
		signMap.put("timestamp", "1518313899915");
		signMap.put("paytype", "99");
		signMap.put("payamount", "1");
		signMap.put("openid", "600103317713");
		signMap.put("payunit", "人民币");
		signMap.put("trade_state_desc", "");

		// 去除请求参数中的参数：sign和sign_type
		Map<String, String> sPara = chargeParamFilter(signMap);

		// 拼接签名前串
		String prestr = createLinkString(sPara);

		// RSA256签名
		String mySign = sign(prestr, myPrivateKey, "utf-8");

		System.out.println(mySign);

		return mySign;
	}

	/**
	 * 调用我们的下单接口，我们返回的签名sign 生成方式demo如下：
	 */
	public static String doMyCreateOrder() {
		// 你们的私钥
		String myPrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIR2Jk2Op+Ml+KMEgYBYTcxsiYtjgf6jyH6ppbowqkk/0gbuO0ZK+hFCcdYQEW1lTc6QuuZZwGIMqRGqzwbKMnhQrR7+G33puTLSIhLEhZ0aqJFFA3WVf5UU0sU3SBE1BnLkcpqcoLqNEc/JREO/IjGbJxg1P+ZWWWl6POBHkIZDAgMBAAECgYBg28wyVigpjvB6s6bGZMuuuYbyJ/c8biMDi6WlukQqf8JiUv2A7v5gdIetEd7McZEthONF4Uct6zgNtSrhOiUghFHgOs8NRSrlZsUMeepZ/ntNDqZB4mEaiwcon4HbMFB6kuqRc8w5ArMlMeNIW9tpyuXQuTnRcaYHlavYQmdqgQJBAOmsCjqaw7KhtDMpccin7VkGVTPoSnRon+sSIZF1ASDQ2GMdI6hd0UYuw57YyFp0UAK8rWXkX+rJtRjq9lpp9aECQQCRHlo3hkhdclEF+ThyOB3cjPGe6zywvel8GIyev8OEK0noe2bUemZW1lr/ZIk70yCsFksP6w+5NHpQZkckp+ljAkBE45RsbJ4PNr8CalCCQIenvEc4M15n7URgMAs3b9AyVX/F0JxnkakV0MmZNf3zNOE1vVw9ctOYS3kZbIPOafihAkBu02FOuiyVwPIDv9rNz/FuN+1m0nvc9oxTi2QI8KZeT7j35RYY+wkhFD8WgC8WEx0Gj1fv5jJx3JP0xsdaRSnFAkBD6aA9tZEUe+9TwkDM0bBe2hrIMYoljOiNpC6px/TRP9zU6bCU8yJjWju/AjdZDpCnMTwoBYSRutNKjjyTqlbB";
		// 请求参数
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("total_amount", "1.0");
		signMap.put("trade_no", "1802111217236282");
		signMap.put("body", "");
		signMap.put("mchid", "pabank");
		signMap.put("sign_type", "RSA2");
		signMap.put("charset", "utf-8");

		signMap.put("out_trade_no", "6521108370476656078");
		signMap.put("paytime", "2018-02-11 09:51:23");
		signMap.put("trade_status", "SUCCESS");
		signMap.put("payfee_type", "￥");
		signMap.put("timestamp", "1518313899915");
		signMap.put("paytype", "99");
		signMap.put("payamount", "1");
		signMap.put("openid", "600103317713");
		signMap.put("payunit", "人民币");
		signMap.put("trade_state_desc", "");

		// 去除请求参数中的参数：sign和sign_type
		Map<String, String> sPara = createParamFilter(signMap);

		// 拼接签名前串
		String prestr = createLinkString(sPara);

		// RSA256签名
		String mySign = sign(prestr, myPrivateKey, "utf-8");

		System.out.println(mySign);

		return mySign;
	}

	/**
	 * 进行充值，你们调用我们， 我们的验证方式如下：
	 */
	public static void doChargeOrder(String mySign) {
		// 你们的公钥
		String yourPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEdiZNjqfjJfijBIGAWE3MbImLY4H+o8h+qaW6MKpJP9IG7jtGSvoRQnHWEBFtZU3OkLrmWcBiDKkRqs8GyjJ4UK0e/ht96bky0iISxIWdGqiRRQN1lX+VFNLFN0gRNQZy5HKanKC6jRHPyURDvyIxmycYNT/mVllpejzgR5CGQwIDAQAB";
		// 请求参数
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("total_amount", "1.0");
		signMap.put("trade_no", "1802111217236282");
		signMap.put("body", "");
		signMap.put("mchid", "pabank");
		signMap.put("sign_type", "RSA2");
		signMap.put("charset", "utf-8");

		signMap.put("out_trade_no", "6521108370476656078");
		signMap.put("paytime", "2018-02-11 09:51:23");
		signMap.put("trade_status", "SUCCESS");
		signMap.put("payfee_type", "￥");
		signMap.put("timestamp", "1518313899915");
		signMap.put("paytype", "99");
		signMap.put("payamount", "1");
		signMap.put("openid", "600103317713");
		signMap.put("payunit", "人民币");
		signMap.put("trade_state_desc", "");
		signMap.put("sign", mySign);

		// 去除请求参数中的参数：sign和sign_type
		Map<String, String> sPara = chargeParamFilter(signMap);

		// 生成签名前串
		String prestr = createLinkString(sPara);

		boolean checkSignResult = doCheck(prestr, signMap.get("sign"), yourPublicKey, signMap.get("charset"));

		System.out.println(checkSignResult);
	}

//	public static void main(String[] args) {
//		// 调用我们的下单接口签名方式
//		doMyCreateOrder();
//
//		// 调用我们的充值接口签名方式
//		doChargeOrder(doMyChargeOrder());
//
//	}

}