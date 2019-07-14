package com.flyersoft.discuss.shuhuang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.DisRecordResult;
import com.flyersoft.discuss.javabean.Movement;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.seekbook.CommentSubmiter;
import com.flyersoft.discuss.javabean.seekbook.Comments;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.JsonTools;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;
import com.flyersoft.discuss.weight.ForScrollLayoutRecyclerView;
import com.flyersoft.discuss.weight.HeaderModeStyleTwo;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 评论/书评内容页面
 * Created by huzheng on 2017/9/1.
 */
public class DiscussionArticleActivity extends DiscessBaseActivity implements CommentDialog.DialogListener {

    private Discuss discuss;
    private List<Comments> hotData;
    private List<Comments> data;

    private SimpleDraweeView pic;//用户头像
    private TextView auther;//用户名
    private TextView time;//发表时间
    private TextView title;//书慌标题
    private TextView content;//求助内容
    private TextView comment_count;//评论数量
    private View hotLayout;
    private ForScrollLayoutRecyclerView hotComments;//热门评论列表
    private ForScrollLayoutRecyclerView comments;//评论列表
    private ScrollBottomScrollView scrollBottomScrollView;//评论列表
    private View submit;//提交评论按钮
//    private RadioButton dic;//点赞按钮
    private Button coll;//收藏按钮
    private ImageView share;//分享
    private CommentDialog pop;
    private CommentAdapter hotAdapter;
    private CommentAdapter adapter;
    private int page = 1;
    private boolean noData = false;
    private Button attention;

    private View commCount;
    private View collBt;
    private View shareBt;

    private View book;//书籍组件
    private SimpleDraweeView bookImg;//书籍图片
    private TextView bookName;//书籍名称
    private TextView bookAuther;//书籍作者

    private boolean submiting;//是否正在提交

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuhuang_article_main);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }


    /**
     * 监听用户登陆，登陆后弹出评论框
     *
     * @param userInfo
     */
    @Subscribe
    public void onEventMainThread(UserInfo userInfo) {
//        ToastTools.showToast(this, "哈哈");
        if (pop != null) {
            pop.show();
        }

    }

    private void initData() {
        hotData = new ArrayList<>(20);
        data = new ArrayList<>(20);
        discuss = new Gson().fromJson(getIntent().getStringExtra("json"), Discuss.class);

        //填充数据
        pic.setImageURI(discuss.getUserIcn());
        auther.setText(discuss.getUserName());
        time.setText(discuss.getCreateTime());
        title.setText(discuss.getTitle());
        content.setText(discuss.getCont());

        bookImg.setImageURI(discuss.getBookIcn());
        bookName.setText(discuss.getBookName());
        bookAuther.setText("作者：" + discuss.getBookAuthor());
        comment_count.setText(discuss.getCommCount() + "条评论");

        //判断是否关注过
        boolean hasFoces = AccountData.getInstance(this).hasFocused(discuss.getUserName());
        attention.setSelected(hasFoces);
        attention.setText(hasFoces ? "取关" : "关注");
        //判断是否点赞过
//        boolean hasDis = AccountData.getInstance(this).hasDis(this, discuss.getDiscussId());
//        if (hasDis) {
//            dic.setSelected(true);
//            dic.setTextColor(Color.parseColor("#FFFFFF"));
//            dic.setClickable(false);
//        }
        //是否收藏了
        boolean hasColl = AccountData.getInstance(this).hasColl(discuss.getDiscussId());
        if (hasColl) {
            coll.setSelected(true);
            collBt.setSelected(true);
            coll.setText("取消收藏");
        }

        //热门评论
        hotAdapter = new CommentAdapter(this, hotData);
        hotAdapter.setFoot(false);
        hotComments.setAdapter(hotAdapter);
        hotAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (pop == null) {
                    pop = new CommentDialog(DiscussionArticleActivity.this);
                    pop.setListener(DiscussionArticleActivity.this);
                }
                pop.setTitle("回复：" + hotData.get(position).getUserName());
                pop.setReplay(hotData.get(position));
                pop.show();
            }

            @Override
            public boolean onItemDis(final int position) {
                if (AccountData.getInstance(DiscussionArticleActivity.this).hasDis(DiscussionArticleActivity.this, hotData.get(position).getCommId())) {
                    return false;
                }
                return disRecord(discuss.getDiscussId(), hotData.get(position).getCommId(), "1", new Observer<BaseRequest<DisRecordResult>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<DisRecordResult> disRecordResultBaseRequest) {
                        AccountData.getInstance(DiscussionArticleActivity.this).dis(DiscussionArticleActivity.this, hotData.get(position).getCommId());
                        ToastTools.showToast(DiscussionArticleActivity.this, "已赞！");
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });

        //评论
        adapter = new CommentAdapter(this, data);
        adapter.setFoot(true);
        comments.setAdapter(adapter);
        adapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (pop == null) {
                    pop = new CommentDialog(DiscussionArticleActivity.this);
                    pop.setListener(DiscussionArticleActivity.this);
                }
                pop.setTitle("回复：" + data.get(position).getUserName());
                pop.setReplay(data.get(position));
                pop.show();
            }

            @Override
            public boolean onItemDis(final int position) {
                if (AccountData.getInstance(DiscussionArticleActivity.this).hasDis(DiscussionArticleActivity.this, data.get(position).getCommId())) {
                    return false;
                }
                return disRecord(discuss.getDiscussId(), data.get(position).getCommId(), "1", new Observer<BaseRequest<DisRecordResult>>() {

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<DisRecordResult> disRecordResultBaseRequest) {
                        AccountData.getInstance(DiscussionArticleActivity.this).dis(DiscussionArticleActivity.this, data.get(position).getCommId());
                        ToastTools.showToast(DiscussionArticleActivity.this, "已赞！");
                    }
                });
            }
        });

        getComm(1);
    }

    private void initView() {

        //初始化头部
        HeaderModeStyleTwo headerModeStyleTwo = findViewById(R.id.header1);
        headerModeStyleTwo.init(TITLE_MAIN, "", "", new HeaderModeStyleTwo.HeaderModeStyleTwoListener() {
            @Override
            public void onBack() {
                DiscussionArticleActivity.this.finish();
            }

            @Override
            public void onOption() {
            }
        });
        //初始化
        pic = findViewById(R.id.shuhuang_article_auther_pic);
        auther = findViewById(R.id.shuhuang_article_auther_name);
        time = findViewById(R.id.shuhuang_article_auther_time);
        title = findViewById(R.id.shuhuang_article_auther_title);
        content = findViewById(R.id.shuhuang_article_auther_content);
        comment_count = findViewById(R.id.shuhuang_article_auther_content_count);
        submit = findViewById(R.id.shuhuang_article_comment_bt);
//        dic = findViewById(R.id.shuhuang_article_auther_content_same_dic_bt);
        coll = findViewById(R.id.shuhuang_article_auther_content_same_coll_bt);
        book = findViewById(R.id.shuhuang_article_auther_book_layout);
        bookImg = findViewById(R.id.shuhuang_article_auther_book_img);
        bookName = findViewById(R.id.shuhuang_article_auther_book_name);
        bookAuther = findViewById(R.id.shuhuang_article_auther_book_auther);
        share = findViewById(R.id.shuhuang_article_auther_content_share);
        attention = findViewById(R.id.shuhuang_article_auther_attention);

        shareBt = findViewById(R.id.shuhuang_article_share_bt);
        commCount = findViewById(R.id.shuhuang_article_comment_count_bt);
        collBt = findViewById(R.id.shuhuang_article_collection_bt);

        scrollBottomScrollView = findViewById(R.id.shuhuang_article_comment_scrollview);
        scrollBottomScrollView.registerOnScrollViewScrollToBottom(new ScrollBottomScrollView.OnScrollBottomListener() {
            @Override
            public void srollToBottom() {
                LogTools.H("scrollBottomScrollView loadMore ");
                if (!noData) {
                    getComm(page + 1);
                }
            }
        });
        //热门评论
        hotLayout = findViewById(R.id.shuhuang_article_auther_hot_com_layout);
        hotComments = findViewById(R.id.shuhuang_article_auther_hot_content_listview);
        hotComments.setNestedScrollingEnabled(false);//防止滚动卡顿
        // 设置adapter
        hotComments.setAdapter(adapter);
        // 设置Item添加和移除的动画
        hotComments.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        hotComments.addItemDecoration(new DividerItemDecorationStyleOne(this, DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider, 0));

        LinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        hotComments.setLayoutManager(linearLayoutManager);

        //评论
        comments = findViewById(R.id.shuhuang_article_auther_content_listview);
        comments.setNestedScrollingEnabled(false);//防止滚动卡顿
        // 设置adapter
        comments.setAdapter(adapter);
        // 设置Item添加和移除的动画
        comments.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        comments.addItemDecoration(new DividerItemDecorationStyleOne(this, DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider, 0));

        LinearLayoutManager linearLayoutManager1 = new FullyLinearLayoutManager(this);
        comments.setLayoutManager(linearLayoutManager1);

        commCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scrollBottomScrollView.getScrollY() > 0) {
                    scrollBottomScrollView.fullScroll(ScrollView.FOCUS_UP);
                } else {
                    scrollBottomScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }
        });

        //跳转书籍主界面
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscussionArticleActivity.this, BookMainActivity.class);
                intent.putExtra("discuss", JsonTools.toJson(discuss));
                DiscussionArticleActivity.this.startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pop == null) {
                    pop = new CommentDialog(DiscussionArticleActivity.this);
                    pop.setListener(DiscussionArticleActivity.this);
                }
                pop.setTitle("写评论");
                pop.show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(discuss.getShareLink());
            }
        });
        shareBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(discuss.getShareLink());
            }
        });

        //收藏
        coll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先判断登陆状态
                UserInfo userInfo = AccountData.getInstance(DiscussionArticleActivity.this).getmUserInfo();
                if (userInfo == null || userInfo.isNeedSignin()) {
                    goLoadingPage();
                    return;
                }
                if (submiting) {
                    return;
                }
                submiting = true;
                String type = AccountData.getInstance(DiscussionArticleActivity.this).hasColl(discuss.getDiscussId()) ? "4" : "2";
                collection(type, discuss.getDiscussId(), new Observer<BaseRequest>() {

                    @Override
                    public void onError(Throwable e) {
                        submiting = false;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        submiting = false;
                        if (coll.isSelected()) {
                            AccountData.getInstance(DiscussionArticleActivity.this).unColl(discuss.getDiscussId());
                            ToastTools.showToast(DiscussionArticleActivity.this, "已取消收藏！");
                            coll.setSelected(false);
                            collBt.setSelected(false);
                            coll.setText("收藏");
                        } else {
                            AccountData.getInstance(DiscussionArticleActivity.this).coll(discuss);
                            coll.setSelected(true);
                            collBt.setSelected(true);
                            coll.setText("取消收藏");
                            ToastTools.showToast(DiscussionArticleActivity.this, "收藏成功！");
                        }
                    }
                });
            }
        });
        //收藏
        collBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先判断登陆状态
                UserInfo userInfo = AccountData.getInstance(DiscussionArticleActivity.this).getmUserInfo();
                if (userInfo == null || userInfo.isNeedSignin()) {
                    goLoadingPage();
                    return;
                }
                if (submiting) {
                    return;
                }
                submiting = true;
                String type = AccountData.getInstance(DiscussionArticleActivity.this).hasColl(discuss.getDiscussId()) ? "4" : "2";
                collection(type, discuss.getDiscussId(), new Observer<BaseRequest>() {

                    @Override
                    public void onError(Throwable e) {
                        submiting = false;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        submiting = false;
                        if (collBt.isSelected()) {
                            AccountData.getInstance(DiscussionArticleActivity.this).unColl(discuss.getDiscussId());
                            collBt.setSelected(false);
                            coll.setSelected(false);
                            coll.setText("收藏");
                            ToastTools.showToast(DiscussionArticleActivity.this, "已取消收藏！");
                        } else {
                            AccountData.getInstance(DiscussionArticleActivity.this).coll(discuss);
                            collBt.setSelected(true);
                            coll.setSelected(true);
                            coll.setText("取消收藏");
                            ToastTools.showToast(DiscussionArticleActivity.this, "收藏成功！");
                        }
                    }
                });
            }
        });
        //关注/取消关注
        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先判断登陆状态
                UserInfo userInfo = AccountData.getInstance(DiscussionArticleActivity.this).getmUserInfo();
                if (userInfo == null || userInfo.isNeedSignin()) {
                    goLoadingPage();
                    return;
                }
                if (submiting) {
                    return;
                }
                submiting = true;
                //判断关注态
                final boolean hasFoc = AccountData.getInstance(DiscussionArticleActivity.this).hasFocused(discuss.getUserName());
                String type = hasFoc ? "3" : "1";
                Movement movement = new Movement();
                movement.setType(type);
                movement.setContId(discuss.getUserId());
                movement.setCont(discuss.getUserName());
                movement.setUserIcn(discuss.getUserIcn());
                movement.setUserName(userInfo.getPetName());
                MRManager.getInstance(DiscussionArticleActivity.this).movementAdd(movement)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseRequest>() {

                            @Override
                            public void onError(Throwable e) {
                                submiting = false;
                                ToastTools.showToast(DiscussionArticleActivity.this, "失败，请重试。。。。");
                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onSubscribe(Disposable disposable) {

                            }

                            @Override
                            public void onNext(BaseRequest baseRequest) {
                                submiting = false;
                                if (baseRequest.getErrorCode() == 0) {
                                    if (hasFoc) {
                                        AccountData.getInstance(DiscussionArticleActivity.this).unFocused(discuss.getUserName());
                                        attention.setSelected(false);
                                        attention.setText("关注");
                                        ToastTools.showToast(DiscussionArticleActivity.this, "已取关！");
                                    } else {
                                        AccountData.getInstance(DiscussionArticleActivity.this).focused(discuss.getUserName());
                                        attention.setSelected(true);
                                        attention.setText("已关注");
                                        ToastTools.showToast(DiscussionArticleActivity.this, "已关注！");
                                    }
                                } else {
                                    ToastTools.showToast(DiscussionArticleActivity.this, baseRequest.getErrorMsg());
                                }
                            }
                        });
            }
        });

        switch (action) {
            case Const.ACTION_SHUHUAN:
            case Const.ACTION_ZONGHETAOLUN:
//                usefulTag.setVisibility(View.GONE);
//                usefulLayout.setVisibility(View.GONE);
//                dic.setVisibility(View.VISIBLE);
//                coll.setVisibility(View.VISIBLE);
                collBt.setVisibility(View.VISIBLE);
                book.setVisibility(View.GONE);
                break;
            case Const.ACTION_JINGCAISHUPING:
            case Const.ACTION_SHUDANG:
//                usefulTag.setVisibility(View.VISIBLE);
//                usefulLayout.setVisibility(View.VISIBLE);
//                dic.setVisibility(View.GONE);
//                coll.setVisibility(View.VISIBLE);
                collBt.setVisibility(View.VISIBLE);
                book.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onSbumit(String con, Comments comment) {
        //先判断登陆状态
        UserInfo userInfo = AccountData.getInstance(this).getmUserInfo();

        if (userInfo == null || userInfo.isNeedSignin()) {
            goLoadingPage();
            return;
        }
        CommentSubmiter commentSubmiter = new CommentSubmiter();
        commentSubmiter.setDiscussId(discuss.getDiscussId());
        commentSubmiter.setCont(con);
        commentSubmiter.setReplyCommId(comment == null ? "" : comment.getCommId());
        commentSubmiter.setUserName(userInfo.getPetName());
        commentSubmiter.setReplyName(comment == null ? "" : comment.getUserName());

        MRManager.getInstance(this).submitComment(commentSubmiter)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest>() {

                    @Override
                    public void onError(Throwable e) {
                        ToastTools.showToast(DiscussionArticleActivity.this, "评论失败，请重试。。。。");
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest list) {
                        ToastTools.showToast(DiscussionArticleActivity.this, "评论成功！");
                        //立即刷新评论列表
                        data.clear();
                        getComm(1);
                    }
                });
    }

    public void getComm(int page) {
        this.page = page;
        MRManager.getInstance(this).getComments(discuss.getDiscussId(), (page - 1) * 20, page * 20, "client")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<List<Comments>>>() {

                    @Override
                    public void onError(Throwable e) {
                        LogTools.H("获取书荒评论： " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<Comments>> list) {
                        LogTools.H("获取列表评论： " + list.getData().size());
//                        data.addAll(list.getData());
                        //区分热门评论
                        if(hotData.size()<1){
                            for (Comments com:list.getData()){
                                if(com.getHot()=="2"){
                                    hotData.add(com);
                                }else{
                                    data.add(com);
                                }
                            }
                            if(hotData.size()>0){
                                hotLayout.setVisibility(View.VISIBLE);
                                hotAdapter.notifyDataSetChanged();
                            }
                        }else{
                            for (Comments com:list.getData()){
                                if(com.getHot()!="2"){
                                    data.add(com);
                                }
                            }
                        }
                        if (list.getData().size() < 20) {
                            adapter.setFoot(false);
                            noData = true;
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
