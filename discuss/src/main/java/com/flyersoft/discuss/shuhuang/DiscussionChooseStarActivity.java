package com.flyersoft.discuss.shuhuang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.flyersoft.discuss.R;
import com.flyersoft.discuss.javabean.seekbook.DiscussUpload;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.weight.HeaderModeStyleOne;
import com.google.gson.Gson;

/**
 * 选择星星界面
 * Created by huzheng on 2017/9/1.
 */

public class DiscussionChooseStarActivity extends DiscessBaseActivity implements View.OnClickListener {

    private View star_5;
    private View star_4;
    private View star_3;
    private View star_2;
    private View star_1;
    private int star = -1;
    private DiscussUpload discuss;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussion_choose_star_main);

        initData();
        initView();
    }

    private void initData() {
        discuss = new Gson().fromJson(getIntent().getStringExtra("json"), DiscussUpload.class);
    }

    private void initView() {
        HeaderModeStyleOne headerModeStyleOne = findViewById(R.id.header1);
        headerModeStyleOne.init("打分", "发布", new HeaderModeStyleOne.HeaderModeStyleOneListener(){
            @Override
            public void onBack() {
                DiscussionChooseStarActivity.this.finish();
            }

            @Override
            public void onOption() {
                if(star > 0){
                    discuss.setAsterisk(star);
                    Intent intent = new Intent(DiscussionChooseStarActivity.this, DiscussionHelpActivity.class);
                    intent.putExtra("json", new Gson().toJson(discuss));
                    DiscussionChooseStarActivity.this.startActivity(intent);
                }else{
                    ToastTools.showToast(DiscussionChooseStarActivity.this, "请打分");
                }
            }
        });

        star_1 = findViewById(R.id.star_1);
        star_2 = findViewById(R.id.star_2);
        star_3 = findViewById(R.id.star_3);
        star_4 = findViewById(R.id.star_4);
        star_5 = findViewById(R.id.star_5);

        star_1.setOnClickListener(this);
        star_2.setOnClickListener(this);
        star_3.setOnClickListener(this);
        star_4.setOnClickListener(this);
        star_5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        star_1.findViewById(R.id.star_checked_img).setVisibility(View.GONE);
        star_2.findViewById(R.id.star_checked_img).setVisibility(View.GONE);
        star_3.findViewById(R.id.star_checked_img).setVisibility(View.GONE);
        star_4.findViewById(R.id.star_checked_img).setVisibility(View.GONE);
        star_5.findViewById(R.id.star_checked_img).setVisibility(View.GONE);
        if (id == R.id.star_1) {
            star = 1;
            star_1.findViewById(R.id.star_checked_img).setVisibility(View.VISIBLE);

        } else if (id == R.id.star_2) {
            star = 2;
            star_2.findViewById(R.id.star_checked_img).setVisibility(View.VISIBLE);

        } else if (id == R.id.star_3) {
            star = 3;
            star_3.findViewById(R.id.star_checked_img).setVisibility(View.VISIBLE);

        } else if (id == R.id.star_4) {
            star = 4;
            star_4.findViewById(R.id.star_checked_img).setVisibility(View.VISIBLE);

        } else if (id == R.id.star_5) {
            star = 5;
            star_5.findViewById(R.id.star_checked_img).setVisibility(View.VISIBLE);

        }
    }
}
