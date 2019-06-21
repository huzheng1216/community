package com.flyersoft.discuss;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.flyersoft.discuss.tools.ToastTools;

/**
 * creat by: huzheng
 * date: 2019/6/4
 * description:
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    private long lastClick = 0;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initParam();
        initData();
    }

    //防止点击过快
    protected void startActivityLimit(Intent intent) {
        if (Math.abs(System.currentTimeMillis() - lastClick) < 1000) {
            return;
        }
        lastClick = System.currentTimeMillis();
        super.startActivity(intent);
    }

    //防止点击过快
    protected void startActivityLimitForResult(Intent intent, int requestCode) {
        if (Math.abs(System.currentTimeMillis() - lastClick) < 1000) {
            return;
        }
        lastClick = System.currentTimeMillis();
        super.startActivityForResult(intent, requestCode);
    }

    protected void showToast(String str) {
        ToastTools.showToast(getApplicationContext(), str);
    }

    /**
     * 初始化界面
     */
    protected abstract void initView();


    /**
     * 初始化全局变量
     */
    protected abstract void initParam();

    /**
     * 准备数据
     */
    protected abstract void initData();
}
