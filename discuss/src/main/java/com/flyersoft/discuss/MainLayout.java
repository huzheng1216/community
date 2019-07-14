package com.flyersoft.discuss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.baoyz.widget.PullRefreshLayout;
import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
//import com.flyersoft.discuss.shuhuang.BookListMainActivity;
import com.flyersoft.discuss.shuhuang.DiscussionArticleActivity;
//import com.flyersoft.discuss.shuhuang.DiscussionMainActivity;
import com.flyersoft.discuss.shuhuang.DiscussionMainAdapter;
import com.flyersoft.discuss.shuhuang.EndlessRecyclerOnScrollListener;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by huzheng on 2017/11/7.
 */
public class MainLayout extends RelativeLayout {

    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpAdapter;
    private int[] img = {R.mipmap.ic_tweet_enter, R.mipmap.fbreader,
            R.mipmap.home_find_oneyuan, R.mipmap.comment_section};
    private String[] imgName = {"我的书单", "综合讨论", "书荒互助", "精彩书评"};

    private RecyclerView mRecyclerView;
    private EndlessRecyclerOnScrollListener listener;
    private DiscussionMainAdapter mAdapter;
    private PullRefreshLayout layout;
    private List<Discuss> data;
    private Handler handler = new Handler();

    private Activity context;
    private long lastClick = 0;

    public MainLayout(Context context) {
        super(context);
    }

    public MainLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        context = (Activity) getContext();
        initData();
        initView();
    }

    private void initData() {
        data = new ArrayList<>();
        mAdapter = new DiscussionMainAdapter(data);
        mAdapter.setOnItemClickListener(new DiscussionMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(Math.abs(System.currentTimeMillis() - lastClick)<1000)
                {
                    return;
                }
                lastClick = System.currentTimeMillis();
                Intent intent = new Intent(context, DiscussionArticleActivity.class);
                intent.putExtra("json", new Gson().toJson(data.get(position)));
                intent.putExtra("action", Const.ACTION_JINGCAISHUPING);
                context.startActivity(intent);
            }
        });

    }

    private void initView() {
        gridView = findViewById(R.id.main_gridview);
        dataList = new ArrayList<Map<String, Object>>(); // step2
        simpAdapter = new SimpleAdapter(getContext(), getData(), R.layout.activity_main_grid_item,
                new String[]{"img", "txt"}, new int[]{R.id.img_item, R.id.txt_item});
        gridView.setAdapter(simpAdapter); // step3
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                switch (i) {
                    case 0:
//                        intent = new Intent(context, UserInfoMainActivity.class);
//                        intent = new Intent(context, BookListMainActivity.class);
//                        intent.putExtra("action", Const.ACTION_ZONGHETAOLUN);
                        break;
//                    case 1:
//                        intent = new Intent(context, DiscussionMainActivity.class);
//                        intent.putExtra("action", Const.ACTION_ZONGHETAOLUN);
//                        break;
//                    case 2:
//                        intent = new Intent(context, DiscussionMainActivity.class);
//                        intent.putExtra("action", Const.ACTION_SHUHUAN);
//                        break;
//                    case 3:
//                        intent = new Intent(context, DiscussionMainActivity.class);
//                        intent.putExtra("action", Const.ACTION_JINGCAISHUPING);
//                        break;
                }
                context.startActivity(intent);
            }
        });


        layout = findViewById(R.id.swipeRefreshLayout);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.setPage(1);
                getDiscuss(1, new Observer<BaseRequest<List<Discuss>>>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastTools.showToast(context, "刷新失败，请重试....");
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
        mRecyclerView.addItemDecoration(new DividerItemDecorationStyleOne(context, DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider, 0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
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
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < img.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", img[i]);
            map.put("txt", imgName[i]);
            dataList.add(map);
        }
        return dataList;
    }


    private void getData(int page) {

        getDiscuss(page, new Observer<BaseRequest<List<Discuss>>>() {
            @Override
            public void onError(Throwable e) {
                LogTools.H("添加书荒： " + e.getMessage());
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


    private void getDiscuss(int page, Observer<BaseRequest<List<Discuss>>> subscriber) {
        int type = 4;
        LogTools.H("haha:"+page);
        UserInfo userInfo = AccountData.getInstance(context).getmUserInfo();
        MRManager.getInstance(context).getDiscuss(type, (page - 1) * 10, 10, userInfo == null ? "游客" : userInfo.getPetName(), 2)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
