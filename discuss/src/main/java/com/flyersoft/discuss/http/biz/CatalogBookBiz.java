package com.flyersoft.discuss.http.biz;

import android.content.Context;

import java.util.List;

import com.flyersoft.discuss.http.base.BaseBiz;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.CatalogDetail;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe:搜索图书业务
 * Created by ${zheng.hu} on 2016/9/27.
 */
public class CatalogBookBiz extends BaseBiz{

    public CatalogBookBiz(Context context){
        super(context);
    }

    /**
     * 获取活动书籍列表
     */
    public void getCatalogBooks(final RequestCallBack<List<CatalogDetail>> callback){

        Observable<BaseRequest<List<CatalogDetail>>> observable = mMRRequestService.getCatalogBooks();
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<List<CatalogDetail>>>() {
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
                    public void onNext(BaseRequest<List<CatalogDetail>> listBaseRequest) {
                        callback.onSuccess(listBaseRequest.getData());
                    }
                });

    }
}
