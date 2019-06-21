package com.flyersoft.discuss.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 滑动到底部加载更多的ScrollView
 * Created by huzheng on 2017/9/2.
 */

public class LoadingMoreScrollview extends ScrollView {

    private OnScroll onScroll;

    public LoadingMoreScrollview(Context context) {
        super(context);
    }

    public LoadingMoreScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingMoreScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScroll(OnScroll onScroll){
        this.onScroll = onScroll;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //当文本的measureheight 等于scroll滚动的长度+scroll的height
        if(onScroll!=null){
            if(getChildAt(0).getMeasuredHeight()<=getScrollY()+getHeight()){
                this.onScroll.loadMore();
            }
            onScroll.clearFocus();
        }
    }

    interface OnScroll{
        abstract void loadMore();
        abstract void clearFocus();
    }
}
