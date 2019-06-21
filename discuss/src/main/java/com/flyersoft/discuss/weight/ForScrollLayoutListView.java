package com.flyersoft.discuss.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 可应用于滑动控件的listview
 * Created by huzheng on 2017/9/2.
 */

public class ForScrollLayoutListView extends ListView {

    public ForScrollLayoutListView(Context context) {
        super(context);
    }

    public ForScrollLayoutListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForScrollLayoutListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
