package com.flyersoft.discuss.shuhuang;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.flyersoft.discuss.BaseActivity;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;
import com.flyersoft.discuss.weight.HeaderModeStyleTwo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 主界面
 * Created by huzheng on 2017/8/31.
 */

public class AttentionListMainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private UserInfoMainAdapter mAdapter;
    private PullRefreshLayout layout;
    private EndlessRecyclerOnScrollListener listener;
    private List<UserInfo> data;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention_list_main);
    }

    @Override
    protected void initView() {
        HeaderModeStyleTwo headerModeStyleTwo = findViewById(R.id.header1);
        headerModeStyleTwo.init("", "返回","", new HeaderModeStyleTwo.HeaderModeStyleTwoListener() {
            @Override
            public void onBack() {
                AttentionListMainActivity.this.finish();
            }

            @Override
            public void onOption() {
//                goAddPage();
            }
        });

        layout = findViewById(R.id.swipeRefreshLayout);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.setPage(1);
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
                getData();
            }
        };
        mRecyclerView.addOnScrollListener(listener);

        layout.setRefreshing(true);
        getData();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setRefreshing(false);
            }
        }, 5000);
    }

    @Override
    protected void initParam() {

    }

    @Override
    protected void initData() {

        data = new ArrayList<>();
        mAdapter = new UserInfoMainAdapter(data);
        mAdapter.setOnItemClickListener(new UserInfoMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });

    }

    private void getData() {

        MRManager.getInstance(this).queryAttenByUser()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<List<UserInfo>>>() {

                    @Override
                    public void onError(Throwable e) {
                        LogTools.H("查询用户的关注列表的队列执行错误");
                    }

                    @Override
                    public void onComplete() {
                        LogTools.H("查询用户的关注列表的队列执行成功");
                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<UserInfo>> baseRequest) {
                        int code = baseRequest.getErrorCode();
                        if(code == 0){
                            data = baseRequest.getData();
                        }else if(code == 6){
                            LogTools.H("该用户下无关注数据");
                        }else {
                            // 其他错误的处理逻辑
                            LogTools.H("服务器出现错误！");
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                });
    }


}
