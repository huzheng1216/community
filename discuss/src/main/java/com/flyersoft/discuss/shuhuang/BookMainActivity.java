package com.flyersoft.discuss.shuhuang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.BookAndDiscuss;
import com.flyersoft.discuss.javabean.CommentAndDiscuss;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.JsonTools;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;
import com.flyersoft.discuss.weight.ForScrollLayoutRecyclerView;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huzheng on 2017/12/30.
 */

public class BookMainActivity extends Activity {

    private Discuss discuss;
    private BookAndDiscuss book;
    private CommentAndDiscuss cad;

    private ForScrollLayoutRecyclerView discussRecyclerView;
    private CommentAdapter commAdapter;
    private ForScrollLayoutRecyclerView commentRecyclerView;
    private DiscussionMainAdapter discussAdapter;


    private SimpleDraweeView pic;//封面
    private TextView book_main_name;//书名
    private TextView book_main_author;//作者
    private TextView book_main_des;//书籍描述

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.book_main);
        initView();
        initData();
    }

    private void initView() {
        pic = findViewById(R.id.book_main_pic);
        book_main_name = findViewById(R.id.book_main_name);
        book_main_author = findViewById(R.id.book_main_author);
        book_main_des = findViewById(R.id.book_main_des);


        discussRecyclerView = findViewById(R.id.book_main_hot_content_listview);
        commentRecyclerView = findViewById(R.id.book_main_content_listview);
    }

    private void initData() {
        discuss = JsonTools.toObject(getIntent().getStringExtra("discuss"), Discuss.class);

        MRManager.getInstance(this).querySBook("", discuss.getBookName(), discuss.getBookAuthor())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<List<BookAndDiscuss>>>() {

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
                    public void onNext(BaseRequest<List<BookAndDiscuss>> bookBaseRequest) {
                        book = bookBaseRequest.getData().get(0);
                        initBook();
                    }
                });
    }

    //初始化书籍信息
    private void initBook() {

        pic.setImageURI(book.getBookIcn());
        book_main_name.setText(book.getBookName());
        book_main_author.setText(book.getBookAuthor());
        book_main_des.setText(book.getBookIntro());

        //获取评论列表
        MRManager.getInstance(this).getBookInfo(discuss.getBookName(), discuss.getBookAuthor(), "", 0, 5)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<CommentAndDiscuss>>() {
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
                    public void onNext(BaseRequest<CommentAndDiscuss> bookBaseRequest) {
                        cad = bookBaseRequest.getData();
                        initDiscuss();
                    }
                });
    }

    //初始化评论区域
    private void initDiscuss() {

        commAdapter = new CommentAdapter(this, cad.getComment());
        commAdapter.setFoot(false);
        commAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public boolean onItemDis(int position) {
                return false;
            }
        });
        // 设置adapter
        commentRecyclerView.setAdapter(commAdapter);
        commentRecyclerView.setNestedScrollingEnabled(false);//防止滚动卡顿
        // 设置Item添加和移除的动画
        commentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        commentRecyclerView.addItemDecoration(new DividerItemDecorationStyleOne(this, DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider, 0));
        LinearLayoutManager linearLayoutManager1 = new FullyLinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(linearLayoutManager1);
        commAdapter.notifyDataSetChanged();

        discussAdapter = new DiscussionMainAdapter(cad.getDiscuss());
        discussAdapter.setOnItemClickListener(new DiscussionMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(BookMainActivity.this, DiscussionArticleActivity.class);
                intent.putExtra("json", new Gson().toJson(cad.getDiscuss().get(position)));
                intent.putExtra("action", Const.ACTION_SHUHUAN);
                BookMainActivity.this.startActivity(intent);
            }
        });
        discussRecyclerView.setAdapter(discussAdapter);
        discussRecyclerView.setNestedScrollingEnabled(false);//防止滚动卡顿
        // 设置Item添加和移除的动画
        discussRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        discussRecyclerView.addItemDecoration(new DividerItemDecorationStyleOne(this, DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider, 0));
        LinearLayoutManager linearLayoutManager2 = new FullyLinearLayoutManager(this);
        discussRecyclerView.setLayoutManager(linearLayoutManager2);
        discussAdapter.notifyDataSetChanged();
    }
}
