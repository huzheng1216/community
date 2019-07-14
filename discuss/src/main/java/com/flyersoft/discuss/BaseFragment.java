package com.flyersoft.discuss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyersoft.discuss.tools.ToastTools;

/**
 * 防止重复inflater
 */
public abstract class BaseFragment extends Fragment {

    protected View contentView = null;
    private long lastClick = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = initView();
        }
        if (contentView != null) {
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void showToast(String str) {
        ToastTools.showToast(getContext(), str);
    }
    //防止点击过快
    protected void startActivityLimit(Intent intent) {
        if (Math.abs(System.currentTimeMillis() - lastClick) < 1000) {
            return;
        }
        lastClick = System.currentTimeMillis();
        super.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        if (contentView != null && contentView.getParent()!=null)
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        super.onDestroyView();
    }

    public abstract ViewGroup initView();
}
