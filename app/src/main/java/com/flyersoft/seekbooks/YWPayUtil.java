package com.flyersoft.seekbooks;

import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.seekbooks.Temp.ThirdConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 阅文充值文档，支付工具类
 *
 * @author wds
 */
public class YWPayUtil {
//	private static final Logger log = LoggerFactory.getLogger(YWPayUtil.class);

    /**
     * 解析支付url
     */
    public static Map<String, String> fromQueryString(String url) {
        Map<String, String> arrParam = new HashMap<>();
        String[] strSplit = url.split("[?]");
        if (strSplit.length > 1) {
            String[] arrSplit = strSplit[1].split("[&]");
            for (String itemSplit : arrSplit) {
                String[] arrSplitEqual = null;
                arrSplitEqual = itemSplit.split("[=]");
                //解析出键值
                if (arrSplitEqual.length > 1) {
                    //正确解析
                    arrParam.put(arrSplitEqual[0], arrSplitEqual[1]);

                }
            }
        }
        return arrParam;
    }

    /**
     * 参数解析
     *
     * @param map 接口1，阅文带的参数
     * @return 解析后，得到参数map
     */
    public static Map<String, String> analysisParam(Map<String, String> map) {
        Map<String, String> params = new HashMap<String, String>();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        String sign_type = "";
        String sign = "";
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if (entry.getKey().equals("sign_type")) {
                sign_type = entry.getValue();
            } else if (entry.getKey().equals("sign")) {
                sign = entry.getValue();
            } else {
                params.put(entry.getKey(), entry.getValue());
            }
        }

//		log.debug("阅文传过来的sign=" + sign);
//		log.debug("阅文传过来的sign的长度=" + sign.length());

        //验证sign
        List<String> keys = new ArrayList<String>();
        keys.addAll(params.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        String val = null;
        for (String key : keys) {
            val = params.get(key);
            if (null == val || "".equals(val.trim())) {
                continue;
            }
            sb.append(key).append("=").append(val).append("&");
        }

        String str = sb.substring(0, sb.length() - 1).toString();
//		log.debug("准备验证的数据=" + str);

		boolean check = SignDemo.doCheck(str, sign, ThirdConfig.getYueWenPublicKey(), "utf-8");
		if(check){
			LogTools.H("sign 验证通过");
		}else {
            LogTools.H("sign 验证没有通过");
		}


        return params;
    }


    /**
     * 组装参数，用于接口2
     *
     * @param map    直接传analysisParam方法返回的map
     * @param tranNo 支付产出的订单号
     * @param result 支付结果  1 SUCCESS 2 REFUND 3 NOTPAY 4 CLOSED 5 REVOKED 6 USERPAYING 7 PAYERROR
     *               支付成功         转入退款        未支付              已关闭          已撤销              用户支付中              支付失败
     *               注意  result 填写英文
     * @param amount 支付钱，保留两位有效数字
     * @return 用于backURL的参数，没有带?
     */
    public static String assembleParam2(Map<String, String> map, String tranNo, String result, String amount) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mchid", map.get("mchid"));
        params.put("charset", map.get("charset"));
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("tarde_no", tranNo);
        params.put("out_tarde_no", map.get("out_tarde_no"));
        params.put("openid", map.get("openid"));
        params.put("total_amount", amount);
        params.put("trade_status", result);

        // 签名
        List<String> keys = new ArrayList<String>();
        keys.addAll(params.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        String val = null;
        for (String key : keys) {
            val = params.get(key);
            if (null == val || "".equals(val.trim())) {
                continue;
            }
            sb.append(key).append("=").append(val).append("&");
        }
        String str = sb.substring(0, sb.length() - 1).toString();
		String sign = SignDemo.sign(str, ThirdConfig.getYueWenPrivateKey(), "utf-8");
		
        sb.append("sign=").append(sign);
        sb.append("&sign_type=RSA2");
        return sb.toString();
    }


    /**
     * 组装参数，用于接口3
     *
     * @param map     直接传analysisParam方法返回的map
     * @param tranNo  支付产出的订单号
     * @param result  支付结果  1 SUCCESS 2 REFUND 3 NOTPAY 4 CLOSED 5 REVOKED 6 USERPAYING 7 PAYERROR
     *                支付成功         转入退款        未支付              已关闭          已撤销              用户支付中              支付失败
     *                注意  result 填写英文
     * @param amount  支付钱，保留两位有效数字
     * @param paytype 1支付宝  2微信
     * @return 用于noticeurl的参数，没有带?
     */
    public static String assembleParam3(Map<String, String> map, String tranNo, String result, String amount, int paytype) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mchid", map.get("mchid"));
        params.put("charset", map.get("charset"));
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("tarde_no", tranNo);
        params.put("out_tarde_no", map.get("out_tarde_no"));
        params.put("openid", map.get("openid"));
        params.put("mchid", map.get("mchid"));
        params.put("total_amount", amount);
        params.put("trade_status", result);

        params.put("paytype", String.valueOf(paytype));
        String date = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss").format(new Date());
        params.put("paytime", date);

        // 签名
        List<String> keys = new ArrayList<String>();
        keys.addAll(params.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        String val = null;
        for (String key : keys) {
            val = params.get(key);
            if (null == val || "".equals(val.trim())) {
                continue;
            }
            sb.append(key).append("=").append(val).append("&");
        }
        String str = sb.substring(0, sb.length() - 1).toString();
		String sign = SignDemo.sign(str, ThirdConfig.getYueWenPrivateKey(), "utf-8");
        sb.append("sign=").append(sign);
        sb.append("&sign_type=RSA2");
        return sb.toString();
    }

}
