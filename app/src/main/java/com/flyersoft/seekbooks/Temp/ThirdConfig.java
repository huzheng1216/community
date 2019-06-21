package com.flyersoft.seekbooks.Temp;

public class ThirdConfig {
	
	// wangdesheng 腾讯云信息-----start
	public static final long AppId = 1254143911;
	public static final String SecretId = "AKIDLUN0NWy4SbXEixDg9MJ0RcXCcmgKqrkO";
	public static final String SecretKey = "VlmX2HH1UhfMnyt76oGTQ6WgbFdlfJh0";
	public static final String BucketBookIcn = "bookicn";
	public static final String BucketUserIcn = "usericn";
	public static final String BucketImg = "img";
	
	
	
	// zk
	//public static final String Url1 = "http://car.moyumedia.com/web/jump/ssdszkjrbk0402.html?s=da1caf646044a8e5222b5d23cbf8cb6d";
	public static final String Url1 = "http://pup.vvrun.com/web/jump/SSDSZKUC0101.html?s=da1caf646044a8e5222b5d23cbf8cb6d";
	public static final String Url2 = "https://yz.m.sm.cn/s?from=wm931778&q=";
	public static final String ConfigUrl = "http://cat.ibimuyu.com/skbk/cfg?";
	
	// 腾讯云信息-----end

	public static String getWXAppId() {
		// 书城使用使用这个
		//return "wxdbabfbf203f80e6d";
		// 搜书使用这个
		return "wxfece615095a8cfff";
	}

	public static String getWXOrderUrl() {
		return "https://api.mch.weixin.qq.com/pay/unifiedorder";

	}

	public static String getWXMch_id() {
		// 书城
		//return "1349055501";
		// 搜书
		return "1488614422";
	}

	public static String getWXKey() {
		return "jdtxkey0000000000000000000000000";
	}

	public static String getWXSecret() {
		//return "3dd35181be33418ccaf449b86775159e";
		//return "130c58590ba25d338e6bea0e6415a694";
		return "83e4c01d64850b15f60b5527c11dfb97";
	}

	public static String getAliAppId() {
		// 静读天下
		// return "2016060401482630";
		// 搜书大师
		return "2017111509942400";
	}

	public static String getAliSeller_id() {
		return "2088521138104980";
		//return "2088221194211250"; 
	}

	public static String getAliPrivateKey() {
		return "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUbqkxjYwjJB2KJFb71eBAOkjBz/FgtlPxXuIB+lmAdd2GK6RkjiG6a6NrF2E79ayjqjArzp097bzi4EJGxxItPvaUXeq85cBmyiVDujlwfj3gbW6h0ZUOwfikoOy7+CgTJ82DtFInTwWjXNqt0MrOnFaOt2qNdENt2GsXIIbbXOwt57vgV5zu3pp46x9/TbsX3UtPFBgsXsxHBwfMe1DS6YuwwTgJqpNDOPP3tkxSIiil4Z2NMPZ2Ar8V6CAcv7Gx9tx6YBtJaCSc7roXgQr7h9Y2a38D8pF1wC1rmi73P8Bq2OkyRhCBX5yg8VYsrVWUNKg7nXcChgrC1r0/In/3AgMBAAECggEAFT39ArVyH7lUbOekdyf1jtuIfAwpTCDrxHhCueu9EVBM4p2uSiG5G/e5jGLtLGqNSWLav/oysKEsTf+wHHby1/zRxwvlwyDSNyOoz5eADuRSMKMYmRnoThNeZRkOqBvsiElC9R1CQJPjh6yLMyLVU1o2XPhMzG+eEsNNGCM0iYH7ZyKI1FYn2TAMOZ0H6wPHfmcx5ShWTr4Zpj4zrxq3dcHWkhBFtWX5FbR2pF/t2kEc91RH1oaAAE+d1mzGi7kwsB9b6mqQMdYR0KpNSpvUh7GtCkmeHli68KAJWcd4szkqvqIqQPWyTxk4c3R/NzjleCO6S6BqP/Y/vdS0aGU8yQKBgQDjqGt+V7Ny6Hmo34lGONA3bPVExcqII21ttbyRGAqzvlsidfyWITWMuAFCp86TCCxvWNyJunQDncD/Wp06p1ikcRA7FPyXWdMjHCrrEYvQ0iU2IFy3yLNYBzbIlQAbbq0nEICez0/tUYF3OATj4SinQbEz7dlCwwzabd9+oG78LQKBgQCm6UcSFWbvtqQe1hRQ/XUC/nAHSnmkzeSAtEmCptYGx+5YF0nykuVb0n9HKozMQQIOHyhPjMyMB5HNGuPpuvUccsSCuPr1ceiXvfUF1YiB4PBLMxvlZLZU/XQ9rMpuFFOoV+QLTesaWVd8G5Ip3JaYS1RbyH4kIjMTc5seDeIvMwKBgAX+44XaTXGTSdD2wijFSkaLahI7m0/mjvU6uhKgLXf6ZXAM53AUw9JG7lGRFdGtf24XSwedj3yg5PpUqptm2ODL0X+c1+IsjXD5RvAV8Da5sh0w0QBOGtsQpZK2yd/vFERZHOoRz6sSYHSBVy+hOG7fPgl+FCC7/lSqXB57nzCpAoGABRyJv6UGJPsr5YHa12wbpD7UgXSnawOfJs3ennCrh0OiKfJ0okgbXAeDPFOyQiMPhA0yN9VxAizNK0HSjvrZvMhK+lHd5ZxaaiM4GLikWI3h7fuftBO0fb/RSn47KMnYkgQ3CJ2XQJRR2IwjmsKVng5YhAMM0eB2PfdBXddBAP8CgYEApBrmIgpRMwn4LmaBMTWwnFqaaBZszb9WwPr864wBjJfV+ibUZBQK3sIShC2N9vnAyrZw28PEbHKzsyW79cYMQdBLmI58BdcI1BwM/cEd04iZYq5BPkDzYZP38USwUBfZkYHsx0iOLclDEwvvfNbegQgWFeB/PXl6NYiSn71F8GA=";
		//return "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANAJ2WeWyuoFxyPmGvmCu9YT9nJ6XL8UwZ503mva9nPmE/ahNtpcRRegCgTZC5D4hgk2wAQjJxACS/dpwIHsoe2mYhbspoqrCtsRvXFQNV83svRuLhwdU3+Hs8/IH4RiXtYpWDcQSumdrgdkZX+6HEPKdD+H5VRnL55N8DaX2CffAgMBAAECgYEApLLnducehd9FJNqLCHXDl1v+g+rzeiNIldYYMHMNIfxIWbyP3Jen62g8BlJ+oarfEnRR1/SsfDQVSC5kxXc9SaVS+ZEkKSelUjnp5UqFDW1AEyIwc9zRWjkV8KRvwo39v0avjDlWbvjsZy3Dp2Z14lYU4HdcrHwBXA1S8C5R+QECQQD378SD/WuhlqintfSp+3B1GhjZc09wgc+0O3Xgqknb9z8iNCq5WikASLonPnrl6qv0+HqXEx0PR6/8566fGMJ/AkEA1s3naubsknOJUSkuRjeaoe0SHvwYz8Qh+A97x5n7jrZP20XSjBjV2rMpOqbTW/QhVk5xLbO+eYcQkMljHiMqoQJBANP4W0x09vX5dkucFobU/vo+y4vtN7d6Y/c09ryTGf5DTeiSLQy2dmNVykEO+8dhcI5JeZV7s9aCdYWFFhfS9UUCQAO5Lfm8Zv1i40502Eh/Tj7gTSAcXpG7ZJtYKV0cXi/rGRdD7pJfG9XepShPG/rMEIfedDfGldalcLQRyZYMJgECQQDELdCPYEwh+mZkF4RtB+5rROH2AI5OHqr0cFQxcvlkFk9scOuA/9M2Y7c8qcyDrmQdsqXFJSgQSf3T8+SOWggt";
	}

	public static String getAliPublicKey() {
		//return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlG6pMY2MIyQdiiRW+9XgQDpIwc/xYLZT8V7iAfpZgHXdhiukZI4humujaxdhO/Wso6owK86dPe284uBCRscSLT72lF3qvOXAZsolQ7o5cH494G1uodGVDsH4pKDsu/goEyfNg7RSJ08Fo1zardDKzpxWjrdqjXRDbdhrFyCG21zsLee74Fec7t6aeOsff027F91LTxQYLF7MRwcHzHtQ0umLsME4CaqTQzjz97ZMUiIopeGdjTD2dgK/FeggHL+xsfbcemAbSWgknO66F4EK+4fWNmt/A/KRdcAta5ou9z/AatjpMkYQgV+coPFWLK1VlDSoO513AoYKwta9PyJ/9wIDAQAB";
		return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgH5a25B1+Y5EPAY9r1Z3pA+y12YGWuyFI5fxGtXAahSYq39XP1+lElX43r0sks/PfluDcpeEl+ImV4g472T4AkraaFYC+TH9ITCqUNmEVuzMwup2qZU+E8UrNInITEDFAnB1li1DA9z+iDxivDn8hPfncxwWy2tJd29sFxTfOT02h/72Ma1W06xpU7J2pt4zHlJY6/yDCTFt2amEVfBfCHz+o+D9E+YVmCsVGEuLMNu3D2iMz42tm431er1YtjC029abz8NfXb1k75Lby4E+ZJiOKP4z6hPnYg/vGHDryPBTLIOnSzB6mqNn7vyZSJn611KV0yx8xwxOT9ulzlQvYQIDAQAB";
		//return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	}

	public static String getAliGateway() {
		return "https://openapi.alipay.com/gateway.do";
	}

	public static String getAliCharset() {
		return "UTF-8";
	}

	public static String getWX_TOKEN_URL() {
		return "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	}

	public static String getWX_UserInfo_URL() {
		return "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
	}

	public static String getAliNotify_url() {
		//return "http://www.moonreader.cn/mreader/third/onAliNotify.do";
		return "http://comm.moonreader.cn/mreader/third/onAliNotify.do";
	}

	public static String getWXNotify_url() {
		//return "http://www.moonreader.cn/mreader/third/onWxNotify.do";
		return "http://comm.moonreader.cn/mreader/third/onWxNotify.do";
	}

	public static String getChargeTitle() {
		return "reader pay";
	}
	
	/**
	 * 阅文H5书城，使用的秘钥
	 * start
	 */
	
	/**
	 * 应用私钥，用于发给阅文的数据进行加密，生成sign
	 * @return
	 */
	public static String getYueWenPrivateKey(){
		return "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCuV6Pnc+8at1szMqxdMa2ukUaiflS1lGF5oOcWIX+6WaNpF4NMrQKvkV1A6PtPMOuIOuZdbjS60pbV8hAGrSSlUKMs8mp4Mi2BhLzLaL7LKPkwsLu3DWCIRxD7eD3j1MwPC3OhvU4mSJG2uXDhLgIQWzeE9hzx66WNasLhnG56Ypgz/7T/jdRmOmiknktCaTq6MXsT2XIqRd5Svj53dq06cQnx7Qd4XvoOrDRBLJ48UiO7Xd6uaF2vX4nYMCC3hLlOikszANNUMinfEVKvCLTeBAhPnxAO4DH3sFOdKDvNXA8bAm4xzPBt3+7I/afe8VtJXYN+3PyLvehaIrcBaTIxAgMBAAECggEALLXPE+FYdWqGWffp2jCOURFyouNxB+wYn1+TQ8qtffXVAXOFOZm48Zt+5dGZ+XIbRSGNyHIl1a+B/TYJxByNJFQstK8qfM6peui2nherrmBAn6sDSOuGUQygn0zz2kTNXCJcsiLhMtIzhD/EYgsxfk5WS6PuQmBjYYP6nG9IqdvIsXFyq3cRiWo/kCQTLZZlB9uA10lJBhGOb1ApsVBaW5hyFIQmA0TGv4zzhg9I4jfNAwwE+ilUexIOcwjLJ1qSR1pSvbNv/a1nmZmgfDmeB1PJPoYxxIDfiVqYAevPXGVJ3AGhSsUeYsZCrhTiWidIGo5eNNJG8psjN8xtT39ywQKBgQDZ+X0AaQT72UsYY8JPoOiG3h3FEWC7RlagA16Eqjbt4/7MretdewwuYg8YpnWPWh7zjRv2BdzNJbl/NzhHzu0HqV2rDeHn7bi5ruwwehSUy4LPevX8i4E85GxrYXNaJI4Qj7je6hfhSrC0AoBTVR8lgtuN9X6x2JhuRbT/gzn3KQKBgQDMwZVkn2dGbMyR+PDE+CHYVfvY8HNRUdaHoz7+H8YgfI8fp9zDwVv3AO0O4rvTmA59zdMkp0qfMyOqQZFpMllPbprjAxM56w+K+wsxUan1QbX1+U0l7tYdXBn739TZP5LoDrrZUMd06uiX0iYD5qI9yi/Byp0gU6wonxLIFNhryQKBgQDEXZ3e4L08pWShdD14vty7ox3uitk53QPs4xwjJ7SUWvEQMx31+QPgRTaUXmFJ40Y+4sBtwppHtdSGZpitGB0w6cMYdkktR1tC6/KbOV+RLEWL5Jn2CTdERAPoOMJSUtwoz1EcgIdCzqahhB9W8zg78scX/DFGxnmL3gBQr0dpAQKBgQCjYmlaNJRNLu4CINnG4pTnMp1XG295WUtNSmcukp4fOCVgxiq0DavbqmJ/OU9VZYVSv5WXLuKtrDnuVom4npODfaecM8z4r2fkp7Pjs91htgCDeV4lQbKbFO4q/Q20GTWZdH+Gfx3ZXsUguHDbNgMKOTbodUvyCrJaa6yp+XBEeQKBgAF9ticS21ARfZmn1f/UqU3eZQCt553OEIjoSZtU0rFAQZX/brI6p7430AMhT0HWDV+o4S11d/iP5oKNkqXTYvgVDOOe7cvjj2EiiIfSa3lhbM1UObuslu30kcAQ26K7mDpnGSeZqMTPltWt6XzqlH++102x275FEWFevZLyC9Bv";
	}
	
	/**
	 * 阅文的支付秘钥，用于对阅文发来的数据进行解密
	 * @return
	 */
	public static String getYueWenPublicKey(){
		return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkvsnraNX4moSCj3z23sw0s2E9y2SS0wfMCZa6qv8341BXu/aEwPe4/S3v5wyL3vJP63v2LXgRKWvgye2xGY9fsXf1aJYA1xFI46HVQH9D1uRMFC78PVI7szDKaKCvt5cB+2+DIcJmgujhJtoR26+x0Z7kM1jo8IhI/zcr27r4i5bqq6XHrPFdtWjA92nwlu6PYk4uY9itZJ7hlP/83TTcIRGHxD7gN3kTkHv468wwJUCTUxrr0sS4kQRq5f+2k7+cnePCsAuk2iDxt+aEs4z2Te5AiLNcFH+HxwKBNdWYF7VtZtCdI6B3ainDrPvz8PnFt1R0MSiR/aziG+5Z9Zq4QIDAQAB";
	}
	
	/**
	 * 阅文H5书城，使用的秘钥
	 * end
	 */

}
