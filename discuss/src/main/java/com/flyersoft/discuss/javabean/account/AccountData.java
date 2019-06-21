package com.flyersoft.discuss.javabean.account;

import android.app.Activity;
import android.content.Context;

import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.StringTools;
import com.flyersoft.discuss.tools.Tools;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 37399 on 2016/10/31.
 */
public class AccountData {

    private WXLandingConfig wXLandingConfig;//微信登陆配置，供WXEntryActivity获取
    private PayConfig wxPayConfig;//微信支付配置，供WXEntryActivity获取

    private UserInfo mUserInfo;
    private Collection collection;//收藏/关注列表

    private static volatile AccountData accountData = null;

    private AccountData(Activity context) {
    }

    public static AccountData getInstance(Activity context) {
        synchronized (AccountData.class) {
            if (accountData == null) {
                accountData = new AccountData(context);
            }
            return accountData;
        }
    }

    public void getUserInfo(final Context context, final RequestCallBack<UserInfo> requestCallBack) {
        if (mUserInfo == null) {
            MRManager.getInstance(context).getUserInfo()
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<BaseRequest<UserInfo>, ObservableSource<BaseRequest<Collection>>>() {
                        @Override
                        public Observable<BaseRequest<Collection>> apply(BaseRequest<UserInfo> userInfoBaseRequest) {
                            mUserInfo = userInfoBaseRequest.getData();
                            if (mUserInfo != null) {
                                mUserInfo.setNeedSignin(false);
                                mUserInfo.setCuid(Tools.getInformain("cuid", "", context));
                            }
                            requestCallBack.onSuccess(mUserInfo);
                            return MRManager.getInstance(context).queryDataOfUser(mUserInfo.getPetName(), 0, 100);
                        }
                    })
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Observer<BaseRequest<Collection>>() {

                        @Override
                        public void onSubscribe(Disposable disposable) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            collection = new Collection();
                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onNext(BaseRequest<Collection> collectionBaseRequest) {
                            collection = collectionBaseRequest.getData();
                            EventBus.getDefault().post(mUserInfo);
                        }
                    });
        } else {
            requestCallBack.onSuccess(mUserInfo);
        }
    }

    public Collection getCollection() {
        return collection;
    }

    //是否关注了某个作者
    public boolean hasFocused(String userName) {
        if (collection.getUserList() == null) {
            return false;
        }
        for (FocusAuther focusAuther : collection.getUserList()) {
            if (userName.equals(focusAuther.getUserName())) {
                return true;
            }
        }
        return false;
    }

    //关注某个作者
    public void focused(String userName) {

        if (collection.getUserList() != null) {
            FocusAuther focusAuther = new FocusAuther();
            focusAuther.setUserName(userName);
            collection.getUserList().add(focusAuther);
        }
    }

    //取注某个作者
    public void unFocused(String userName) {
        int index = 0;
        for (int i = 0; i < collection.getUserList().size(); i++) {
            if (collection.getUserList().get(i).getUserName().equals(userName)) {
                index = i;
            }
        }
        collection.getUserList().remove(index);
    }

    //是否收藏了某个文章
    public boolean hasColl(String discussId) {
        if (collection.getDisList() == null) {
            return false;
        }
        for (Discuss discuss : collection.getDisList()) {
            if (discussId.equals(discuss.getDiscussId())) {
                return true;
            }
        }
        return false;
    }

    //收藏某个文章
    public void coll(Discuss discuss) {

        if (collection.getDisList() != null) {
            collection.getDisList().add(discuss);
        }
    }

    //取注收藏
    public void unColl(String discussId) {

        if (collection.getDisList() != null) {
            int index = 0;
            for (int i = 0; i < collection.getDisList().size(); i++) {
                if (collection.getDisList().get(i).getDiscussId().equals(discussId)) {
                    index = i;
                }
            }
            collection.getDisList().remove(index);
        }
    }

    private String dis_list;

    public String getDis(Context context) {
        return Tools.getInformain("dis_list", "", context);
    }

    //是否点赞过
    public boolean hasDis(Context context, String discussId) {

        if (dis_list == null) {
            dis_list = Tools.getInformain("dis_list", "", context);
        }
        if (StringTools.isNotEmpty(dis_list) && dis_list.indexOf(discussId) >= 0) {
            return true;
        }
        return false;
    }

    //点赞
    public void dis(Context context, String discussId) {
        if (dis_list == null) {
            dis_list = Tools.getInformain("dis_list", "", context);
        }
        if (StringTools.isNotEmpty(dis_list) || dis_list.indexOf(discussId) <= 0) {
            dis_list = Tools.getInformain("dis_list", "", context) + discussId + ",";
            Tools.setInformain("dis_list", dis_list, context);
        }
    }

    public WXLandingConfig getwXLandingConfig() {
        return wXLandingConfig;
    }

    public void setwXLandingConfig(WXLandingConfig wXLandingConfig) {
        this.wXLandingConfig = wXLandingConfig;
    }

    public PayConfig getWxPayConfig() {
        return wxPayConfig;
    }

    public void setWxPayConfig(PayConfig wxPayConfig) {
        this.wxPayConfig = wxPayConfig;
    }

    public UserInfo getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(UserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
