package com.flyersoft.discuss.shuhuang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.BaseActivity;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.weight.HeaderModeStyleOne;
import com.flyersoft.discuss.weight.HeaderModeStyleOne.HeaderModeStyleOneListener;
import com.flyersoft.discuss.weight.login.LandingPageActivity;


/**
 * 我的动态主界面
 * Created by huzheng on 2017/8/31.
 */

public class UserInfoMainActivity extends BaseActivity {

    private HeaderModeStyleOne headerModeStyleOne;
    private View header;
    private SimpleDraweeView userIcon;
    private TextView userName;
    private TextView lv;
    private TextView jf;
    private TextView fans;
    private TextView balance;
    private TextView gold;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_main);
    }

    @Override
    protected void initView() {
        headerModeStyleOne = findViewById(R.id.header1);
        userIcon = findViewById(R.id.user_info_icn);
        userName = findViewById(R.id.user_info_name);
        lv = findViewById(R.id.user_info_Lv);
        jf = findViewById(R.id.user_info_jifen);
        fans = findViewById(R.id.user_info_fans);
        balance = findViewById(R.id.user_info_balance);
        gold = findViewById(R.id.user_info_gold);
        header = findViewById(R.id.user_info_header_layout);
    }

    @Override
    protected void initParam() {
        headerModeStyleOne.init("我的动态", "", new HeaderModeStyleOneListener() {
            @Override
            public void onBack() {
                UserInfoMainActivity.this.finish();
            }

            @Override
            public void onOption() {
            }
        });
    }

    @Override
    protected void initData() {
        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();
        if(userInfo!=null){
            userIcon.setImageURI(userInfo.getHeadPic());
            userName.setText(userInfo.getPetName());
            lv.setText("Lv:倔强青铜");
            jf.setText("0");
            fans.setText("0");
            balance.setText("0");
            gold.setText("0");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("loading");//得到新Activity 关闭后返回的数据
        initData();
    }

    public void onClick(View view) {

        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();
        if (userInfo == null) {
            Intent intent = new Intent(this, LandingPageActivity.class);
            startActivityLimitForResult(intent, RESULT_OK);
            return;
        }

        int id = view.getId();
        if (id == R.id.user_info_header_layout) {
            //头像
//            showToast("头像");
        } else if (id == R.id.user_info_header_jifen_layout) {
            //积分
            Intent intet = new Intent(UserInfoMainActivity.this, AttentionListMainActivity.class);
            startActivityLimit(intet);
//            showToast("积分");
        } else if (id == R.id.user_info_header_fans_layout) {
            //粉丝
            showToast("粉丝");
        } else if (id == R.id.user_info_header_balance_layout) {
            //余额
            showToast("余额");
        } else if (id == R.id.user_info_header_gold_layout) {
            //金币
            showToast("金币");
        } else if (id == R.id.user_info_attention_layout) {
            //关注列表
            showToast("关注列表");
        } else if (id == R.id.user_info_collenction_layout) {
            //收藏列表
            showToast("收藏列表");
        } else if (id == R.id.user_info_comment_layout) {
            //帖子列表
            showToast("帖子列表");
        } else if (id == R.id.user_info_discuss_layout) {
            //评论列表
            showToast("评论列表");
        } else if (id == R.id.user_info_recharge_layout) {
            //充值记录
            showToast("充值记录");
        }
    }

}