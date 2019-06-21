package com.flyersoft.seekbooks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alipay.sdk.app.PayTask;
import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.body.PayBody;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.DefaultInfo;
import com.flyersoft.discuss.javabean.account.AccountAction;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.AmountInfo;
import com.flyersoft.discuss.javabean.account.PayConfig;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.google.gson.Gson;
import com.flyersoft.seekbooks.Temp.A;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huzheng on 2018/1/13.
 */

public class BookStore extends Activity {

    private WebView webView;


    //充值
    private RechargeDialog rechargeDialog;
    private RechargeChoseDialog rechargeChoseDialog;

    //总价格/余额
    private int counts = 0;
    private int balanceCount = 0;

    //
//    private Handler handler = new MyHandler();
//
//
//
//
//    private class MyHandler extends Handler {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            switch (msg.what) {
//                case 1:
//                    rechargeDialog = new RechargeDialog(BookStore.this, new RechargeDialog.RechargeListener() {
//                        @Override
//                        public void recharge(int count) {
//                            rechargeDialog.dismiss();
//                            choseRechargeType(count);
//                        }
//                    });
//                    rechargeDialog.show();
//                default:
//                    break;
//            }
//        }
//    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_store);

        EventBus.getDefault().register(this);
        //url = getIntent().getStringExtra("url");

        webView = findViewById(R.id.webview);
        enableWebViewCookie(webView);
        WebSettings webSettings = webView.getSettings();
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false);
        //调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
        ////设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        // 设置Web视图
        webView.setWebViewClient(new MyWebViewClient());

//        webView.loadUrl("file:///android_asset/bookstore.html");
        webView.loadUrl(getUrl("https://mcp.yuewen.com/moon/index.html"));
    }


    @Subscribe
    public void onEventMainThread(AccountAction token) {
        switch (token.getAction()) {
            case AccountAction.ACCREDIT_PAY_OK:
                returnPayToken(token.getOut_trade_no(), token.getTotal(), "1", 2);
                break;
            case AccountAction.ACCREDIT_PAY_NO:
                ToastTools.showToast(this, "微信用户不同意授权!");
                break;
            case AccountAction.ACCREDIT_PAY_CANCEL:
                ToastTools.showToast(this, "微信用户取消了授权!");
                break;
        }
    }

    private String getUrl(String url) {
        String token = getToken();
        String paramTag = "_cptoken=";
        if (!url.contains(paramTag) && token != null)
            url = url + (url.contains("?") ? "&" : "?") + paramTag + token;
        return url;
    }

    private String getToken() {
        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();
        return userInfo == null ? null : userInfo.getCuid();
    }


    //去充值
    public void recharge() {
        rechargeDialog = new RechargeDialog(this, new RechargeDialog.RechargeListener() {
            @Override
            public void recharge(int count) {
                rechargeDialog.dismiss();
                choseRechargeType(count);
            }
        });
        rechargeDialog.show();
    }


    //选择充值类型
    private void choseRechargeType(int count) {
        rechargeChoseDialog = new RechargeChoseDialog(this, count, new RechargeChoseDialog.RechargeListener() {
            @Override
            public void rechargeWX(int count) {
                rechargeChoseDialog.dismiss();
                wxPay(count);
            }

            @Override
            public void rechargeZFB(int count) {
                rechargeChoseDialog.dismiss();
                zfbPay(count);
            }
        });
        rechargeChoseDialog.show();
    }


    /**
     * 微信支付
     *
     * @param count
     */
    private void wxPay(int count) {

        MRManager.getInstance(this).getWXPayInfo(new PayBody(Const.ACCOUNTTYPE_WEIXIN, count * 10))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<BaseRequest<PayConfig>, ObservableSource<PayConfig>>() {
                    @Override
                    public Observable<PayConfig> apply(BaseRequest<PayConfig> userInfoBaseRequest) {
                        return Observable.just(userInfoBaseRequest.getData());
                    }
                })
                .subscribe(new Observer<PayConfig>() {
                    @Override
                    public void onError(Throwable e) {
                        A.log("onError = " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(PayConfig payConfig) {
                        IWXAPI api = WXAPIFactory.createWXAPI(BookStore.this, payConfig.getAppid(), true);
                        PayReq request = new PayReq();
                        request.appId = payConfig.getAppid();
                        request.partnerId = payConfig.getPartnerid();
                        request.prepayId = payConfig.getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = payConfig.getNoncestr();
                        request.timeStamp = payConfig.getTimestamp();
                        request.sign = payConfig.getSign();
                        api.sendReq(request);
                    }
                });
    }

    /**
     * 支付宝支付
     *
     * @param count
     */
    private void zfbPay(int count) {

        MRManager.getInstance(this).getZFBPayInfo(new PayBody(Const.ACCOUNTTYPE_ALIPAY, count * 10))
                .observeOn(Schedulers.newThread())
                .flatMap(new Function<DefaultInfo, ObservableSource<Map<String, String>>>() {
                    @Override
                    public Observable<Map<String, String>> apply(DefaultInfo defaultInfo) {

                        A.log("defaultInfo.getData() = " + defaultInfo.getData());
                        PayTask alipay = new PayTask(BookStore.this);
                        Map<String, String> result = alipay.payV2(defaultInfo.getData(), true);
                        return Observable.just(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onError(Throwable e) {
                        A.log("zfbPay onError: " + e.toString());
                        A.toast(BookStore.this, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        A.log("zfbPay onCompleted");
                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Map<String, String> result) {
                        A.log("onNext = " + result.toString());
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult(result);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            ToastTools.showToast(BookStore.this, "支付成功。");
                            //获取订单号，并通知阅文
                            AliPayResult aliPayResult = new Gson().fromJson(resultInfo, AliPayResult.class);
                            String out_trade_no = aliPayResult.getAlipay_trade_app_pay_response().getOut_trade_no();
                            String total = aliPayResult.getAlipay_trade_app_pay_response().getTotal_amount();
                            returnPayToken(out_trade_no, total, "1", 1);
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                            A.toast(BookStore.this, "支付异常。");
                            A.toast(BookStore.this, result.toString());
                        }
                        initBalance();
                    }
                });
    }


    private void initBalance() {
        MRManager.getInstance(this).getAmount(new RequestCallBack<AmountInfo>() {
            @Override
            public void onSuccess(AmountInfo userInfo) {
                balanceCount = userInfo.getData();
//                balance.setText(userInfo.getData()+"书币");
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }


    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            A.log("shouldOverrideUrlLoading:" + url);
            String newUrl = getUrl(url);
            if (!newUrl.equals(url)) {
                url = newUrl;
                A.log("url changed to:" + url);
            }

            if (url.contains("comm.moonreader.cn/yueliang/longin"))
                loginUrl = url;

            //这里进行url拦截
            if (url.contains("loading") || url.contains("longin")) {
                Intent intent = new Intent(BookStore.this, com.flyersoft.discuss.weight.login.LandingPageActivity.class);
                startActivityForResult(intent, 100);
                return true;
            }
            //这里进行url拦截
            if (url.contains("pay")) {
//                handler.sendEmptyMessage(1);
                payUrl = url;
                rechargeDialog = new RechargeDialog(BookStore.this, new RechargeDialog.RechargeListener() {
                    @Override
                    public void recharge(int count) {
                        A.log("************recharge: " + count);
                        rechargeDialog.dismiss();
                        choseRechargeType(count);
                    }
                });
                rechargeDialog.show();
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            A.log("onPageStarted:" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            A.log("onPageFinished:" + url);
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
        }
    }

    public static String getValueByName(String url, String name) {
        String result = null;
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            returnLoginToken();
        }
    }

    private String payUrl;

    //只有支付成功，才会吊起这个函数
    private void returnPayToken(String no, final String total, final String result, final int paytype) {
        //获取阅文参数
        Map<String, String> params = YWPayUtil.fromQueryString(payUrl);
        final String url1 = params.get("returnurl");
        final String url2 = params.get("noticeurl");
        Map<String, String> params2 = YWPayUtil.analysisParam(params);
        //通知接口2
        final String data2 = YWPayUtil.assembleParam2(params2, no, result, total);
        //通知接口3
        final String data3 = YWPayUtil.assembleParam3(params2, no, result, total, paytype);
        LogTools.H("通知阅文接口2：" + url1);
        LogTools.H("通知阅文接口3：" + url2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String get = URLDecoder.decode(url1, "utf-8") + "?" + data2;
                    URL url = new URL(get);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    LogTools.H("阅文接口2返回:" + conn.getResponseCode());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String get = URLDecoder.decode(url2, "utf-8") + "?" + data3;
                    URL url = new URL(get);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    LogTools.H("阅文接口3:" + conn.getResponseCode());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String loginUrl;

    private void returnLoginToken() {
        String token = getToken();
        if (token != null && loginUrl != null) {
            String state = getValueByName(loginUrl, "state");
            String redirect_uri = getValueByName(loginUrl, "redirect_uri");
            if (state != null && redirect_uri != null) {
                redirect_uri = Uri.decode(redirect_uri);
                redirect_uri = redirect_uri + "&_cptoken=" + token + "&state=" + state + "&appflag=moon";
                webView.loadUrl(redirect_uri);
            }
        }
        try {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(token);
            A.toast(this, "token 已拷贝到粘贴板: " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enableWebViewCookie(WebView webView) {
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payUrl = "";
        EventBus.getDefault().unregister(this);
    }

}
