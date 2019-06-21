package com.flyersoft.discuss.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyersoft.discuss.R;

/**
 * 头部样式1
 * Created by huzheng on 2017/8/31.
 */

public class HeaderModeStyleOne extends RelativeLayout {

    private TextView back;
    private TextView title;
    private TextView option;

    private HeaderModeStyleOneListener mHeaderModeStyleOneListener;


    public HeaderModeStyleOne(Context context) {
        super(context);
    }

    public HeaderModeStyleOne(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderModeStyleOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showOption(){
        option.setVisibility(View.VISIBLE);
    }
    public void hidenOption(){
        option.setVisibility(View.GONE);
    }

    //外界初始化头部样式
    public void init(String title, HeaderModeStyleOneListener headerModeStyleOneListener){
        this.title.setText(title);
        mHeaderModeStyleOneListener = headerModeStyleOneListener;
    }

    //外界初始化头部样式2
    public void init(String title,String option, HeaderModeStyleOneListener headerModeStyleOneListener){
        this.title.setText(title);
        this.option.setText(option);
        mHeaderModeStyleOneListener = headerModeStyleOneListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        back = findViewById(R.id.header_moder_left);
        title = findViewById(R.id.header_moder_title);
        option = findViewById(R.id.header_moder_right);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mHeaderModeStyleOneListener){
                    mHeaderModeStyleOneListener.onBack();
                }
            }
        });

        option.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mHeaderModeStyleOneListener){
                    mHeaderModeStyleOneListener.onOption();
                }
            }
        });
    }

    //事件监听
    public interface HeaderModeStyleOneListener{
        void onBack();
        void onOption();
    }
}
