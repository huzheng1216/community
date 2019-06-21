package com.flyersoft.discuss.http.service;

import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.account.WXAccessToken;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 请求第三方服务接口
 * Created by Administrator on 2016/9/23.
 */
public interface ThirdRequestService {

    @GET("sns/oauth2/access_token")
    Observable<WXAccessToken> getAccessToken(@QueryMap Map<String, String> map);

    @GET
    Observable<RequestCallBack> synYueWenPay();
}
