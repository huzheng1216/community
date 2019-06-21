package com.flyersoft.discuss.shuhuang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;
import com.flyersoft.discuss.weight.login.LandingPageActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huzheng on 2017/11/15.
 */

public class UserActionViewPagerFragment extends Fragment {

    private int mTitle;
    private int mColor;
    private TextView mTextView;
    private LinearLayout mLinear;
    private View logInText;
    private View logIn;


    private RecyclerView mRecyclerView;
    private DiscussionMainAdapter mAdapter;
    private PullRefreshLayout refreshLayout;
    private EndlessRecyclerOnScrollListener listener;
    private List<Discuss> data;

    public static UserActionViewPagerFragment newInstance(int title , int color){
        UserActionViewPagerFragment fragment = new UserActionViewPagerFragment();
        Bundle bundle = new Bundle();
//        bundle.putInt("title",title);
//        bundle.putInt("color",color);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mTitle = getArguments().getInt("title");
//        mColor = getArguments().getInt("color");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_info_action_view_page_item, container, false);
//        mTextView = (TextView) view.findViewById(R.id.fragment_textView);
//        mTextView.setText("Page"+(mTitle + 1));
//        mLinear = (LinearLayout) view.findViewById(R.id.fragment_ll);
//        /**这里注意是setBackgroundResource不是setBackgroundColor；setBackgroundResource(int resId)方法的参数是一个组件的id值。该方法也是用于加载组件的背景图片的；setBackgroundColor(Color.XXX)方法参数为一个Color类的静态常量.顾名思义,它是用来设置背景颜色的方法.*/
//        mLinear.setBackgroundResource(mColor);

        logInText = view.findViewById(R.id.user_info_log_in_text);
        logIn = view.findViewById(R.id.user_info_log_in);
        //登录
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LandingPageActivity.class);
                getContext().startActivity(intent);
            }
        });


        data = new ArrayList<>();
        mAdapter = new DiscussionMainAdapter(data);
        mAdapter.setOnItemClickListener(new DiscussionMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), DiscussionArticleActivity.class);
                intent.putExtra("json", new Gson().toJson(data.get(position)));
                intent.putExtra("action", Const.ACTION_JINGCAISHUPING);
                getContext().startActivity(intent);
            }
        });

        mRecyclerView = view.findViewById(R.id.user_info__recycler_view);
        refreshLayout = view.findViewById(R.id.user_info_swipeRefreshLayout);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        mRecyclerView.addItemDecoration(new DividerItemDecorationStyleOne(getContext(), DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider, 0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        listener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getData(currentPage);
            }
        };
        mRecyclerView.addOnScrollListener(listener);

        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.setPage(1);
                getDiscuss(1, new Observer<BaseRequest<List<Discuss>>>() {

                    @Override
                    public void onError(Throwable e) {
                        ToastTools.showToast(getContext(), "刷新失败，请重试....");
                        refreshLayout.setRefreshing(false);
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
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        refreshLayout.setRefreshing(true);
//
//        UserInfo userInfo = AccountData.getInstance((Activity) getContext()).getmUserInfo();
//        if(userInfo==null|| userInfo.isNeedSignin()){
//            logInText.setVisibility(View.VISIBLE);
//        }else{
//            logInText.setVisibility(View.GONE);
//            getData(0);
//        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        UserInfo userInfo = AccountData.getInstance((Activity) getContext()).getmUserInfo();
        if(userInfo==null|| userInfo.isNeedSignin()){
            logInText.setVisibility(View.VISIBLE);
        }else{
            logInText.setVisibility(View.GONE);
            getData(1);
        }
    }

    private void getData(int page) {

        getDiscuss(page, new Observer<BaseRequest<List<Discuss>>>() {

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
                refreshLayout.setRefreshing(false);
            }
        });
    }


    private void getDiscuss(int page, Observer<BaseRequest<List<Discuss>>> subscriber) {
        UserInfo userInfo = AccountData.getInstance((Activity) getContext()).getmUserInfo();
        MRManager.getInstance(getContext()).queryActionData(userInfo.getPetName(), (page - 1) * 20, 20)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
