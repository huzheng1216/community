package com.flyersoft.discuss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.weight.login.LandingPageActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取用户登陆信息
        AccountData.getInstance(this).getUserInfo(this, new RequestCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
            }
            @Override
            public void onFailure(String msg) {
            }
        });
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initParam() {
    }

    @Override
    protected void initData() {
    }

    //支付宝登陆
    public void zfb_login(View view){
        Intent intent = new Intent(MainActivity.this, LandingPageActivity.class);
        intent.putExtra("action", Const.ACTION_JINGCAISHUPING);
        startActivity(intent);
    }
}
