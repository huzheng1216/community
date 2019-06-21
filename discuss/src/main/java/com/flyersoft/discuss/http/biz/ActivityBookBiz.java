package com.flyersoft.discuss.http.biz;

import android.content.Context;

import java.util.List;

import com.flyersoft.discuss.http.base.BaseBiz;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.Book;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 活动图书列表
 * Created by Administrator on 2016/9/23.
 */
public class ActivityBookBiz extends BaseBiz {

    public ActivityBookBiz(Context context){
        super(context);
    }

    /**
     * 获取活动书籍列表
     */
    public void getActivityBooks(final RequestCallBack<List<Book>> callback){

        Observable<BaseRequest<List<Book>>> observable = mMRRequestService.getActivityBooks();
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<List<Book>>>() {
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
                    public void onNext(BaseRequest<List<Book>> activityBooks) {
                        callback.onSuccess(activityBooks.getData());
                    }
                });
    }
}
