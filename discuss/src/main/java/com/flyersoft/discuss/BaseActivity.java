package com.flyersoft.discuss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.flyersoft.discuss.tools.ToastTools;

/**
 * Describe:
 * Created by ${zheng.hu} on 2016/10/7.
 */
public abstract class BaseActivity extends Activity {

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
