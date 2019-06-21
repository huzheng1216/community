package com.flyersoft.discuss.http.biz;

import android.content.Context;

import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.base.BaseBiz;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.body.LandingCodeBody;
import com.flyersoft.discuss.http.body.PayBody;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.BookContent;
import com.flyersoft.discuss.javabean.BookDetail;
import com.flyersoft.discuss.javabean.ChargeRecords;
import com.flyersoft.discuss.javabean.DefaultCode;
import com.flyersoft.discuss.javabean.DefaultInfo;
import com.flyersoft.discuss.javabean.ShelfBook;
import com.flyersoft.discuss.javabean.account.AmountInfo;
import com.flyersoft.discuss.javabean.account.PayConfig;
import com.flyersoft.discuss.javabean.account.TencentYunConfig;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.account.WXLandingConfig;
import com.flyersoft.discuss.javabean.account.ZFBLandingConfig;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 处理账户相关业务
 * Created by 37399 on 2016/10/23.
 */
public class AccountBiz extends BaseBiz {

    public AccountBiz(Context context) {
        super(context);
    }

    /**
     * 获取登陆配置
     */
    public Observable<BaseRequest<WXLandingConfig>> getLandingConfig(int app) {

        String appStr = Const.ACCOUNT_ZFB == app ? "alipay" : (Const.ACCOUNT_WX == app ? "weixin" : "");
        if (appStr.isEmpty()) {
            return null;
        }
        return mMRRequestService.getLandingConfig(appStr);
    }

    /**
     * 获取支付宝登陆配置
     *
     * @return
     */
    public Observable<ZFBLandingConfig> getZFBLandingConfig() {
        return mMRRequestService.getZFBLandingConfig();
    }

    /**
     * 获取用户信息
     */
    public Observable<BaseRequest<UserInfo>> getUserInfo() {

        return mMRRequestService.getUserInfo();
    }

    /**
     * 获取已购买书籍
     */
    public void getBuyBookRecords(int from, int limitfinal, final RequestCallBack<List<BookDetail>> callback) {

        Observable<BaseRequest<List<BookDetail>>> observable = mMRRequestService.getBuyBookRecords(from, limitfinal);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<List<BookDetail>>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<BookDetail>> listBaseRequest) {
                        callback.onSuccess(listBaseRequest.getData());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailure(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取用户充值记录
     */
    public void getChargeRecords(int from, int limitfinal, final RequestCallBack<List<ChargeRecords>> callback) {

        Observable<BaseRequest<List<ChargeRecords>>> observable = mMRRequestService.getChargeRecords(from, limitfinal);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<List<ChargeRecords>>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<ChargeRecords>> listBaseRequest) {
                        callback.onSuccess(listBaseRequest.getData());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailure(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 上传授权的code
     *
     * @return
     */
    public Observable<BaseRequest<UserInfo>> uploadCode(LandingCodeBody landingCodeBody) {
        return mMRRequestService.uploadCode(landingCodeBody.toMap());
    }

    /**
     * 获取微信支付配置
     *
     * @param payBody
     * @return
     */
    public Observable<BaseRequest<PayConfig>> getWXPayInfo(PayBody payBody) {
        return mMRRequestService.getWXPayInfo(payBody.toMap());
    }

    /**
     * 获取支付宝支付配置
     *
     * @param payBody
     * @return
     */
    public Observable<DefaultInfo> getZFBPayInfo(PayBody payBody) {
        return mMRRequestService.getZFBPayInfo(payBody.toMap());
    }

    /**
     * 获取账户余额
     * @return
     */
    public void getAmount(final RequestCallBack<AmountInfo> callback) {
        Observable<AmountInfo> observable = mMRRequestService.getAmount();
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AmountInfo>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(AmountInfo amountInfo) {
                        callback.onSuccess(amountInfo);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailure(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 购买书籍
     * @param bookId
     * @param chapterNums
     * @param autoDebits
     * @param callback
     */
    public void buyBook(String bookId, String chapterNums, boolean autoDebits, final RequestCallBack<Boolean> callback) {
        Observable<BaseRequest> observable = mMRRequestService.buyBook(bookId,chapterNums,autoDebits);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.getErrorCode() == 0){
                            callback.onSuccess(true);
                        }else{
                            callback.onFailure("");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailure(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 添加到书架
     * @param bookId
     * @param callback
     */
    public void addToSelf(String bookId, final RequestCallBack<BookDetail> callback) {
        Observable<BaseRequest<BookDetail>> observable = mMRRequestService.addToSelf(bookId);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<BookDetail>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<BookDetail> bookDetailBaseRequest) {
                        if(bookDetailBaseRequest.getErrorCode() == 0){
                            callback.onSuccess(bookDetailBaseRequest.getData());
                        }else{
                            callback.onFailure("");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailure(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 是否在书架上
     * @param bookId
     * @param callback
     */
    public void ifInSelf(String bookId, final RequestCallBack<DefaultCode> callback) {
        Observable<BaseRequest<DefaultCode>> observable = mMRRequestService.ifInSelf(bookId);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<DefaultCode>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<DefaultCode> defaultCodeBaseRequest) {
                        if(defaultCodeBaseRequest.getData().getCode() == 2){
                            callback.onSuccess(defaultCodeBaseRequest.getData());
                        }else{
                            callback.onFailure("");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailure(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取书架内容
     * @param from
     * @param limit
     * @param callback
     */
    public void getMyShelf(int from, int limit, final RequestCallBack<List<ShelfBook>> callback) {
        Observable<BaseRequest<List<ShelfBook>>> observable = mMRRequestService.getMyShelf(from, limit);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<List<ShelfBook>>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<ShelfBook>> listBaseRequest) {
                        callback.onSuccess(listBaseRequest.getData());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailure(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取正文内容
     * @param bookId
     * @param chapterNo
     * @param direction
     * @param callback
     */
    public void getContent(String bookId, int chapterNo, String direction, final RequestCallBack<BaseRequest<BookContent>> callback) {
        Observable<BaseRequest<BookContent>> observable = mMRRequestService.getContent(bookId, chapterNo, direction);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<BookContent>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<BookContent> bookContentBaseRequest) {
                        callback.onSuccess(bookContentBaseRequest);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailure(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 退出登陆
     * @return
     * @param callback
     */
    public void logOut(final RequestCallBack<Boolean> callback) {
        Observable<BaseRequest<Boolean>> observable = mMRRequestService.logOut();
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<Boolean> booleanBaseRequest) {
                        callback.onSuccess(true);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onSuccess(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取腾讯云上传配置
     * @return
     */
    public Observable<BaseRequest<TencentYunConfig>> getTencentYunConfig() {
        return mMRRequestService.getTencentYunConfig();
    }



    /**
     * @author huzheng
     * @date 2019/5/28
     * @description
     * 获取关注列表
     */
    public Observable<BaseRequest<List<UserInfo>>> queryAttenByUser() {
        return mMRRequestService.queryAttenByUser();
    }
}
