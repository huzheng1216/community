package com.flyersoft.discuss.weight.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.AuthTask;
import com.flyersoft.discuss.BaseActivity;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.body.LandingCodeBody;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.account.AccountAction;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.account.WXLandingConfig;
import com.flyersoft.discuss.javabean.account.ZFBLandingConfig;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * 登陆界面
 * Created by 37399 on 2016/11/1.
 */
public class LandingPageActivity extends BaseActivity {

    //    private static final int ALI_SDK_PAY_FLAG = 1;
//    private static final int ALI_SDK_AUTH_FLAG = 2;
    private ZFBLandingConfig zFBLandingConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_main);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initParam() {
    }

    @Override
    protected void initData() {
        MRManager.getInstance(this).getZFBLandingConfig()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZFBLandingConfig>() {

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(final ZFBLandingConfig zfbLandingConfig) {
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "网络获取支付宝登陆配置：" + zfbLandingConfig.getData());
                        zFBLandingConfig = zfbLandingConfig;
                    }
                });
    }

    public void openWXLanding(View view) {
        //从服务器获取微信注册信息
        MRManager.getInstance(this).getLandingConfig(Const.ACCOUNT_WX)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<WXLandingConfig>>() {

                    @Override
                    public void onError(Throwable e) {
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "获取微信登陆配置失败。。。");
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<WXLandingConfig> userInfo) {
                        WXLandingConfig config = userInfo.getData();
                        AccountData.getInstance(LandingPageActivity.this).setwXLandingConfig(config);
                        IWXAPI api = WXAPIFactory.createWXAPI(LandingPageActivity.this, config.getAppid(), true);
                        api.registerApp(config.getAppid());

                        SendAuth.Req req = new SendAuth.Req();
                        req.scope = config.getScope();
                        req.state = config.getState();
                        api.sendReq(req);
                    }
                });
    }

    public void openZFBLanding(View view) {
        getZFBConfig()
                .observeOn(Schedulers.newThread())
                .flatMap(new Function<ZFBLandingConfig, Observable<Map<String, String>>>() {
                    @Override
                    public Observable<Map<String, String>> apply(ZFBLandingConfig zfbLandingConfig) throws Exception {
                        if (zFBLandingConfig == null) {
                            zFBLandingConfig = zfbLandingConfig;
                        }
                        // 构造AuthTask 对象
                        AuthTask authTask = new AuthTask(LandingPageActivity.this);
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "线程：" + Thread.currentThread().getName());
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "支付宝登陆配置：" + zfbLandingConfig.getData());
                        // 调用授权接口，获取授权结果
                        Map<String, String> result = authTask.authV2(zfbLandingConfig.getData(), true);
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "支付宝result：" + result.toString());
                        return Observable.just(result);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Map<String, String>, Observable<AuthResult>>() {
                    @Override
                    public Observable<AuthResult> apply(Map<String, String> stringStringMap) {

                        @SuppressWarnings("unchecked")
                        AuthResult authResult = new AuthResult(stringStringMap, true);
                        String resultStatus = authResult.getResultStatus();
                        // 判断resultStatus 为“9000”且result_code
                        // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                        if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                            String authCode = String.format("authCode:%s", authResult.getAuthCode());
                            // 获取alipay_open_id，调支付时作为参数extern_token 的value
                            // 传入，则支付账户为该授权账户
                            return Observable.just(authResult);
                        } else {
                            // 其他状态值则为授权失败
                            return Observable.error(new Throwable("支付宝授权失败！"));
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<AuthResult, Observable<BaseRequest<UserInfo>>>() {
                    @Override
                    public Observable<BaseRequest<UserInfo>> apply(AuthResult authResult) {

                        return uploadCode(authResult.getAuthCode(), authResult.getUserId());
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<UserInfo>>() {

                    @Override
                    public void onError(Throwable e) {
                        LogTools.showLog(LogTools.TAG_ACCOUNT, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<UserInfo> userInfo) {
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "支付宝用户：" + userInfo.getData().getPetName());
                        success(userInfo.getData());
                    }
                });

    }

    @Subscribe
    public void onEventMainThread(AccountAction token) {
        switch (token.getAction()) {
            case AccountAction.ACCREDIT_LANDING_OK:
                uploadCode(token.getAccredit_landing_code())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseRequest<UserInfo>>() {
                            @Override
                            public void accept(BaseRequest<UserInfo> userInfo) throws Exception {
                                //通知侧边栏登陆成功
                                LogTools.showLog(LogTools.TAG_ACCOUNT, "获取到的用户 : " + userInfo.getData().getPetName());
                                success(userInfo.getData());
                            }
                        });
                break;
            case AccountAction.ACCREDIT_LANDING_NO:
                ToastTools.showToast(this, "微信用户不同意授权!");
                break;
            case AccountAction.ACCREDIT_LANDING_CANCEL:
                ToastTools.showToast(this, "微信用户取消了授权!");
                break;
        }
    }


    //微信用户授权登陆
    public Observable<BaseRequest<UserInfo>> uploadCode(String code) {
        return MRManager.getInstance(this).uploadCode(new LandingCodeBody(UserInfo.ACCOUNTTYPE_WEIXIN, code, ""));
    }

    //支付宝用户授权登陆
    public Observable<BaseRequest<UserInfo>> uploadCode(String code, String user_id) {
        return MRManager.getInstance(this).uploadCode(new LandingCodeBody(UserInfo.ACCOUNTTYPE_ALIPAY, code, user_id));
    }

    /**
     * 登陆成功
     *
     * @param mUserInfo
     */
    private void success(UserInfo mUserInfo) {
        mUserInfo.setNeedSignin(false);
        //登录成功后，立即
        AccountData.getInstance(LandingPageActivity.this).getUserInfo(LandingPageActivity.this, new RequestCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                Intent intent = new Intent();
                intent.putExtra("loading", "ok");
                LandingPageActivity.this.setResult(RESULT_OK, intent);
                LandingPageActivity.this.finish();
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public Observable getZFBConfig() {
        if (zFBLandingConfig != null) {
            return Observable.just(zFBLandingConfig);
        } else {
            //从服务器获取支付宝注册信息
            return MRManager.getInstance(LandingPageActivity.this).getZFBLandingConfig();
        }
    }
}
