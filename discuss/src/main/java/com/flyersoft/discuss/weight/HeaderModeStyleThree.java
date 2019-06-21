package com.flyersoft.discuss.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyersoft.discuss.R;
import com.flyersoft.discuss.tools.StringTools;

/**
 * 头部样式3
 * Created by huzheng on 2017/8/31.
 */

public class HeaderModeStyleThree extends RelativeLayout {

    private LinearLayout back;
    private TextView title;
    private TextView option;

    private HeaderModeStyleThreeListener mHeaderModeStyleOneListener;


    public HeaderModeStyleThree(Context context) {
        super(context);
    }

    public HeaderModeStyleThree(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderModeStyleThree(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showOption(){
        option.setVisibility(View.VISIBLE);
    }
    public void hidenOption(){
        option.setVisibility(View.GONE);
    }

    //外界初始化头部样式
    public void init(String title, HeaderModeStyleThreeListener headerModeStyleOneListener){
        this.title.setText(title);
        mHeaderModeStyleOneListener = headerModeStyleOneListener;
    }

    //外界初始化头部样式2
    public void init(String title, String option, HeaderModeStyleThreeListener headerModeStyleOneListener){
        this.title.setText(title);
        this.option.setText(option);
        if(StringTools.isNotEmpty(option)){
            this.option.setVisibility(View.VISIBLE);
        }
        mHeaderModeStyleOneListener = headerModeStyleOneListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        back = findViewById(R.id.header_moder_left);
        title = findViewById(R.id.header_moder_title_back);
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
    public interface HeaderModeStyleThreeListener{
        void onBack();
        void onOption();
    }
}
