package com.flyersoft.discuss.http.biz;

import android.content.Context;

import com.flyersoft.discuss.http.base.BaseBiz;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.DetailBookInfo;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe:二级页图书详情业务
 * Created by ${zheng.hu} on 2016/9/27.
 */
public class DetailBookBiz extends BaseBiz{

    public DetailBookBiz(Context context){
        super(context);
    }

    /**
     * 获取活动书籍列表
     * @param bookId 书id
     * @param callback
     */
    public void getDetailBook(String bookId, final RequestCallBack<DetailBookInfo> callback){

        Observable<BaseRequest<DetailBookInfo>> observable = mMRRequestService.getDetailBook(bookId);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<DetailBookInfo>>() {
                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<DetailBookInfo> baseRequest) {
                        callback.onSuccess(baseRequest.getData());
                    }
                });
    }
}
