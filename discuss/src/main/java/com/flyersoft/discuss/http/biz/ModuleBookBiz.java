package com.flyersoft.discuss.http.biz;

import android.content.Context;

import java.util.List;

import com.flyersoft.discuss.http.base.BaseBiz;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.BookDetail;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe:模块图书业务
 * Created by ${zheng.hu} on 2016/9/27.
 */
public class ModuleBookBiz extends BaseBiz{

    public ModuleBookBiz(Context context){
        super(context);
    }

    /**
     * 获取活动书籍列表
     * @param moduleId 活动id
     * @param from
     * @param limit
     * @param callback
     */
    public void getModuleBooks(String moduleId, int from, int limit, final RequestCallBack<List<BookDetail>> callback){

        Observable<BaseRequest<List<BookDetail>>> observable = mMRRequestService.getActivityBooks(moduleId, from, limit);

        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<List<BookDetail>>>() {
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
                    public void onNext(BaseRequest<List<BookDetail>> listBaseRequest) {
                        callback.onSuccess(listBaseRequest.getData());
                    }
                });

    }
}
