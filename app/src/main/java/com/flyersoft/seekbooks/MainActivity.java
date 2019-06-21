package com.flyersoft.seekbooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.seekbooks.Temp.A;

public class MainActivity extends AppCompatActivity {

//    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

//        info = (TextView) findViewById(R.id.main_show_person_info);
//        info.setText("登录信息");
        //获取用户登陆信息
//        AccountData.getInstance(this).getUserInfo(this, new RequestCallBack<UserInfo>() {
//            @Override
//            public void onSuccess(UserInfo userInfo) {
//                show(null);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//            }
//        });
    }

    public void loagin(View v) {
        Intent intent = new Intent(this, com.flyersoft.discuss.weight.login.LandingPageActivity.class);
        startActivity(intent);
    }

    public void discuss(View v) {
        Intent intent = new Intent(this, com.flyersoft.discuss.MainActivity.class);
        startActivity(intent);
    }

    public void bookstore(View v) {
        Intent intent = new Intent(this, com.flyersoft.seekbooks.BookStore.class);
        startActivity(intent);
    }

//    public void show(View v) {
//        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();
//        String text = "未登录！";
//        if (userInfo != null) {
//            text = "登录信息：" +
//                    "\n" + "用户名：" + userInfo.getPetName() +
//                    "\n" + "token：" + userInfo.getToken() +
//                    "\n" + "cuid：" + userInfo.getCuid();
//        }
//        info.setText(text);
//        A.log(text);
//    }
}
