package com.flyersoft.discuss.shuhuang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyersoft.discuss.BaseFragmentActivity;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.weight.HeaderModeStyleTwo;
import com.flyersoft.discuss.weight.HeaderModeStyleTwo.HeaderModeStyleTwoListener;


/**
 * 书单主界面
 * Created by huzheng on 2017/8/31.
 */
public class BookListMainActivity extends BaseFragmentActivity {

    private HeaderModeStyleTwo headerModeStyleTwo;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_main);
    }

    @Override
    protected void initView() {
        headerModeStyleTwo = findViewById(R.id.header1);
        tabLayout = findViewById(R.id.book_list_main_tablayout);
        viewPager = findViewById(R.id.book_list_main_viewpager);
    }

    @Override
    protected void initParam() {
        headerModeStyleTwo.init("我的书单", "", "", new HeaderModeStyleTwoListener() {
            @Override
            public void onBack() {
                BookListMainActivity.this.finish();
            }

            @Override
            public void onOption() {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new BookListPageAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("loading");//得到新Activity 关闭后返回的数据
        initData();
    }

    //创建书单
    public void creatBookList(View view) {
        Intent intent = new Intent(BookListMainActivity.this, BookListCreatActivity.class);
        startActivityLimit(intent);
    }

}