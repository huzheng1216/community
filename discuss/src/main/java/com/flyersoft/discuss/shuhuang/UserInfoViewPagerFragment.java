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
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.Collection;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;
import com.flyersoft.discuss.weight.login.LandingPageActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huzheng on 2017/11/15.
 */

public class UserInfoViewPagerFragment extends Fragment {

    private SimpleDraweeView pic;
    private TextView userName;
    private TextView focesNum;
    private TextView fansNum;
    private View logIn;
    private View logOut;


    private RecyclerView mRecyclerView;
    private DiscussionMainAdapter mAdapter;
    private List<Discuss> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(UserInfo userInfo) {
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData() {

        //填充关注/收藏
        Collection collection = AccountData.getInstance((Activity) getContext()).getCollection();
        data = collection.getDisList() == null ? (new ArrayList<Discuss>()) : collection.getDisList();
        mAdapter.changeData(data);

        UserInfo userInfo = AccountData.getInstance((Activity) getContext()).getmUserInfo();
        if (userInfo != null && !userInfo.isNeedSignin()) {
            pic.setImageURI(userInfo.getHeadPic());
            userName.setText(userInfo.getPetName());
            logIn.setVisibility(View.GONE);
            logOut.setVisibility(View.VISIBLE);
            focesNum.setText(collection.getUserList().size() + "");
            fansNum.setText(collection.getDisList().size() + "");
        }
        LogTools.H("getData ： " + data.size());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_info_info_view_page_item, container, false);
        pic = view.findViewById(R.id.user_info_info_pic);
        userName = view.findViewById(R.id.user_info_name);
        focesNum = view.findViewById(R.id.user_info_focus_num);
        fansNum = view.findViewById(R.id.user_info_fans_num);
        logIn = view.findViewById(R.id.user_info_log_in);
        logOut = view.findViewById(R.id.user_info_log_out);
        //登录
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LandingPageActivity.class);
                getContext().startActivity(intent);
            }
        });
        //退出
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MRManager.getInstance(getContext()).logOut(new RequestCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        ToastTools.showToast(getContext(), "成功退出");
                        ((Activity) getContext()).finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
                AccountData.getInstance((Activity) getContext()).setmUserInfo(null);
            }
        });

        //填充用户信息
        UserInfo userInfo = AccountData.getInstance((Activity) getContext()).getmUserInfo();
        if (userInfo == null || userInfo.isNeedSignin()) {
            logIn.setVisibility(View.VISIBLE);
        } else {
            logOut.setVisibility(View.VISIBLE);
            pic.setImageURI(userInfo.getHeadPic());
            userName.setText(userInfo.getPetName());
        }


        //填充关注/收藏
        Collection collection = AccountData.getInstance((Activity) getContext()).getCollection();
        if (collection == null) {
            //监听回调
            data = new ArrayList<>();
        } else {
            data = collection.getDisList() == null ? (new ArrayList<Discuss>()) : collection.getDisList();
            focesNum.setText(collection.getUserList().size() + "");
            fansNum.setText(collection.getDisList().size() + "");
        }

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
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        mRecyclerView.addItemDecoration(new DividerItemDecorationStyleOne(getContext(), DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider, 0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }


}
