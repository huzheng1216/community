package com.flyersoft.discuss.shuhuang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.DisRecord;
import com.flyersoft.discuss.javabean.DisRecordResult;
import com.flyersoft.discuss.javabean.Movement;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.javabean.seekbook.DiscussUpload;
import com.flyersoft.discuss.tools.StringTools;
import com.flyersoft.discuss.weight.login.LandingPageActivity;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huzheng on 2017/10/7.
 */

public class DiscessBaseActivity extends Activity {

    protected int action;
    protected static String TITLE_MAIN = "";
    protected static String TITLE_CREAT = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        action = getIntent().getIntExtra("action", 1);
        switch (action) {
            case Const.ACTION_SHUHUAN:
                TITLE_MAIN = "书荒互助";
                TITLE_CREAT = "发布书荒求助";
                break;
            case Const.ACTION_ZONGHETAOLUN:
                TITLE_MAIN = "综合讨论";
                TITLE_CREAT = "发布话题";
                break;
            case Const.ACTION_JINGCAISHUPING:
                TITLE_MAIN = "精彩书评";
                break;
            case Const.ACTION_SHUDANG:
                TITLE_MAIN = "书单";
                break;
        }
    }

    protected void getDiscuss(int page, int sortType, Observer<BaseRequest<List<Discuss>>> observable) {
        int type = 1;
        switch (action) {
            case Const.ACTION_SHUHUAN:
                type = 2;
                break;
            case Const.ACTION_ZONGHETAOLUN:
                type = 1;
                break;
            case Const.ACTION_JINGCAISHUPING:
                type = 4;
                break;
            case Const.ACTION_SHUDANG:
                type = 3;
                break;
        }
        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();
        MRManager.getInstance(this).getDiscuss(type, (page - 1) * 20, 20, userInfo == null ? "游客" : userInfo.getPetName(), sortType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observable);
    }

    protected void addDiscuss(String title, String content, String json, Observer<BaseRequest> subscriber) {
        //先判断登陆状态
        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();
        if (userInfo == null || userInfo.isNeedSignin()) {
            goLoadingPage();
            return;
        }
        int type = 1;
        int chiType = 1;
        DiscussUpload discuss = new DiscussUpload();
        if (StringTools.isNotEmpty(json)) {
            discuss = new Gson().fromJson(json, DiscussUpload.class);
        }
        switch (action) {
            case Const.ACTION_SHUHUAN:
                type = 2;
                break;
            case Const.ACTION_ZONGHETAOLUN:
                type = 1;
                break;
            case Const.ACTION_JINGCAISHUPING:
                type = 4;
                break;
            case Const.ACTION_SHUDANG:
                type = 3;
                break;
        }

        discuss.setTitle(title);
        discuss.setCont(content);
        discuss.setType(type);
        discuss.setChiType(chiType);
        discuss.setUserName(userInfo.getPetName());
        MRManager.getInstance(this).addDiscuss(discuss)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //同感/有用/无用
    protected boolean disRecord(String discussId, String commId, String type, Observer<BaseRequest<DisRecordResult>> subscriber) {
        //先判断登陆状态
        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();
        if (userInfo == null || userInfo.isNeedSignin()) {
            goLoadingPage();
            return false;
        }

        DisRecord disRecord = new DisRecord();
        disRecord.setDisId(discussId);
        disRecord.setCommId(commId);
        disRecord.setType(type);
        disRecord.setUserName(userInfo.getPetName());

        MRManager.getInstance(this).disRecord(disRecord)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return true;
    }

    //收藏/取消
    protected void collection(String type, String contId, Observer<BaseRequest> observable) {
        //先判断登陆状态
        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();

        Movement movement = new Movement();
        movement.setType(type);
        movement.setContId(contId);
        movement.setCont("");
        movement.setUserName(userInfo.getPetName());

        MRManager.getInstance(this).movementAdd(movement)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observable);
    }

    //跳转添加界面
    protected void goAddPage() {
        //先判断登陆状况
        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();
        if (userInfo == null || userInfo.isNeedSignin()) {
            goLoadingPage();
        } else {
            if (action == Const.ACTION_JINGCAISHUPING) {
                Intent intent = new Intent(this, DiscussionChooseBookActivity.class);
                intent.putExtra("action", action);
                this.startActivity(intent);
            } else {
                Intent intent = new Intent(this, DiscussionHelpActivity.class);
                intent.putExtra("action", action);
                this.startActivity(intent);
            }
        }
    }

    protected void goLoadingPage() {
        Intent intent = new Intent(this, LandingPageActivity.class);
        this.startActivity(intent);
    }


    //分享文字
    public void shareText(String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.setType("text/plain");

        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


}
