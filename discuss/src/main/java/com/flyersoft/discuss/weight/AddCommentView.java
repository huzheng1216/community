package com.flyersoft.discuss.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.flyersoft.discuss.R;

/**
 * creat by: huzheng
 * date: 2019/5/23
 * description: 悬浮添加按钮
 */
public class AddCommentView extends View {

    private int width = 0;
    private int height = 0;

    public AddCommentView(Context context) {
        super(context);
    }

    public AddCommentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddCommentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        Paint paint = new Paint();

        paint.setAntiAlias(true);

        paint.setColor(getResources().getColor(R.color.base_color));

        //找到中心
        int x = width / 2;
        int y = height / 2;
        int radius = width / 2;

        //画圆
        canvas.drawCircle(x, y, radius, paint);

        //画中间十字
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setStrokeWidth(10);
        canvas.drawLine(x - x / 3, y, x + x / 3, y, paint);
        canvas.drawLine(x, y - y / 3, x, y + y / 3, paint);
    }
}
