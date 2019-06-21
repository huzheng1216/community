package com.flyersoft.discuss.weight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 可应用于滑动控件的listview
 * Created by huzheng on 2017/9/2.
 */

public class ForScrollLayoutRecyclerView extends RecyclerView {

    public ForScrollLayoutRecyclerView(Context context) {
        super(context);
    }

    public ForScrollLayoutRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForScrollLayoutRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
