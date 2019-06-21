package com.flyersoft.discuss.shuhuang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;
import com.flyersoft.discuss.weight.HeaderModeStyleOne;
import com.flyersoft.discuss.weight.HeaderModeStyleOne.HeaderModeStyleOneListener;
import com.flyersoft.discuss.weight.HeaderModeStyleTwo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 主界面
 * Created by huzheng on 2017/8/31.
 */

public class DiscussionMainActivity extends DiscessBaseActivity {

    private RecyclerView mRecyclerView;
    private DiscussionMainAdapter mAdapter;
    private PullRefreshLayout layout;
    private EndlessRecyclerOnScrollListener listener;
    private List<Discuss> data;
    private Handler handler = new Handler();
    private int sortType = 1;
    private TextView soutTime;
    private TextView soutHot;
    private View addComment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuhuang_main);

        initData();
        initView();

    }

    private void initView() {

        HeaderModeStyleTwo headerModeStyleTwo = findViewById(R.id.header1);
        headerModeStyleTwo.init(TITLE_MAIN, "返回","", new HeaderModeStyleTwo.HeaderModeStyleTwoListener() {
            @Override
            public void onBack() {
                DiscussionMainActivity.this.finish();
            }

            @Override
            public void onOption() {
//                goAddPage();
            }
        });

        layout = findViewById(R.id.swipeRefreshLayout);
        soutTime = findViewById(R.id.main_sort_time_bt);
        soutHot = findViewById(R.id.main_sort_hot_bt);
        addComment = findViewById(R.id.add_comment_icon);
        soutTime.setSelected(true);
        soutTime.setClickable(false);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAddPage();
            }
        });

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.setPage(1);
                getDiscuss(1, sortType, new Observer<BaseRequest<List<Discuss>>>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastTools.showToast(DiscussionMainActivity.this, "刷新失败，请重试....");
                        layout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<Discuss>> list) {
                        data.clear();
                        data.addAll(list.getData());
                        mAdapter.notifyDataSetChanged();
                        // refresh complete
                        layout.setRefreshing(false);
                    }
                });
            }
        });


        mRecyclerView = findViewById(R.id.my_recycler_view);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        mRecyclerView.addItemDecoration(new DividerItemDecorationStyleOne(this, DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider, 0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        listener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LogTools.H("recyclerView loadMore " + currentPage);
                getData(currentPage);
            }
        };
        mRecyclerView.addOnScrollListener(listener);

        layout.setRefreshing(true);
        getData(1);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setRefreshing(false);
            }
        }, 5000);
    }

    private void initData() {

        data = new ArrayList<>();
        mAdapter = new DiscussionMainAdapter(data);
        mAdapter.setOnItemClickListener(new DiscussionMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(DiscussionMainActivity.this, DiscussionArticleActivity.class);
                intent.putExtra("json", new Gson().toJson(data.get(position)));
                intent.putExtra("action", action);
                DiscussionMainActivity.this.startActivity(intent);
            }
        });

    }

    private void getData(int page) {

        getDiscuss(page, sortType, new Observer<BaseRequest<List<Discuss>>>() {

            @Override
            public void onError(Throwable e) {
                LogTools.H("添加书荒： " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(BaseRequest<List<Discuss>> list) {
                LogTools.H("书荒列表： " + list.getData().size());
                if (list.getData().size() > 0) {
                    data.addAll(list.getData());
                    mAdapter.setFoot(true);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.setFoot(false);
                    mAdapter.notifyDataSetChanged();
                }
                layout.setRefreshing(false);
            }
        });
    }

    //按时间排序
    public void onSortByTime(View view){
        layout.setRefreshing(true);
        sortType = 1;
        soutTime.setSelected(true);
        soutTime.setClickable(false);
        soutHot.setClickable(true);
        soutHot.setSelected(false);
        soutTime.setTextColor(Color.parseColor("#D45F50"));
        soutHot.setTextColor(Color.parseColor("#AAAAAA"));
        data.clear();
        getData(1);
    }

    //按热度排序
    public void onSortByHot(View view){
        layout.setRefreshing(true);
        soutTime.setSelected(false);
        soutHot.setClickable(false);
        soutTime.setClickable(true);
        soutHot.setSelected(true);
        soutHot.setTextColor(Color.parseColor("#D45F50"));
        soutTime.setTextColor(Color.parseColor("#AAAAAA"));
        sortType = 2;
        data.clear();
        getData(1);
    }


}
