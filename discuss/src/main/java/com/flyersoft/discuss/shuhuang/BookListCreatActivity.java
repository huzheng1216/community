package com.flyersoft.discuss.shuhuang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyersoft.discuss.BaseFragmentActivity;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.seekbook.BookList;
import com.flyersoft.discuss.javabean.seekbook.BookListInfo;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.StringTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;
import com.flyersoft.discuss.weight.HeaderModeStyleThree;
import com.flyersoft.discuss.weight.HeaderModeStyleThree.HeaderModeStyleThreeListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 书单创建界面
 * Created by huzheng on 2017/8/31.
 */
public class BookListCreatActivity extends BaseFragmentActivity {

    private HeaderModeStyleThree headerModeStyleTwo;
    private EditText title;
    private EditText content;
    private TextView tag;
    private RecyclerView recyclerView;
    private BookListCreatAdapter mAdapter;
    private List<BookListInfo> data;
    private long lastClick = 0;
    private int currentPosition = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_creat_main);
    }

    @Override
    protected void initView() {
        headerModeStyleTwo = findViewById(R.id.header1);
        title = findViewById(R.id.book_list_creat_title);
        content = findViewById(R.id.book_list_creat_content);
        tag = findViewById(R.id.book_list_creat_content_tag);
        recyclerView = findViewById(R.id.my_recycler_view);
        // 设置Item添加和移除的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        recyclerView.addItemDecoration(new DividerItemDecorationStyleOne(BookListCreatActivity.this, DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider_book_list_item, 0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookListCreatActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initParam() {
        headerModeStyleTwo.init("创建书单", "发布", new HeaderModeStyleThreeListener() {
            @Override
            public void onBack() {
                BookListCreatActivity.this.finish();
            }

            @Override
            public void onOption() {
                BookList bookList = new BookList();
                bookList.setListName(title.getText().toString());
                bookList.setListIntro(content.getText().toString());
                bookList.setList(data);
                bookList.setUserName(AccountData.getInstance(BookListCreatActivity.this).getmUserInfo().getPetName());
                bookList.setUserId(AccountData.getInstance(BookListCreatActivity.this).getmUserInfo().getId());
                LogTools.H("创建书单。。。。");
                MRManager.getInstance(BookListCreatActivity.this).addBookList(bookList).subscribe(new Observer<BaseRequest>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        ToastTools.showToast(BookListCreatActivity.this, baseRequest.getErrorCode() == 0 ? "创建成功！" : "失败。。。。");
                        if (baseRequest.getErrorCode() == 0) {
                            BookListCreatActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogTools.H(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
        //字数显示
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                LogTools.H(i + "-" + i1 + "-" + i2);
                tag.setText((content.getText().toString().length()) + "/200");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected void initData() {
        String json = getIntent().getStringExtra("data");
        this.data = new ArrayList<>();
        if (StringTools.isNotEmpty(json)) {
            BookList bookList = new Gson().fromJson(json, BookList.class);
            this.title.setText(bookList.getListName());
            this.content.setText(bookList.getListIntro());
            MRManager.getInstance(this).queryBookList(bookList.getListId(), 0, 50).subscribe(new Observer<BaseRequest<List<BookListInfo>>>() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onNext(BaseRequest<List<BookListInfo>> listBaseRequest) {
                    data.clear();
                    data.addAll(listBaseRequest.getData());
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(Throwable throwable) {
                    ToastTools.showToast(BookListCreatActivity.this, throwable.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        } else {
            for (int i = 0; i < 5; i++) {
                BookListInfo bli = new BookListInfo();
                bli.setBookName("魔法师的意志");
                bli.setBookAuthor("血之舞");
                bli.setBookIcn("http://img.1391.com/api/v1/bookcenter/cover/1/1183612/_1183612_416925.jpg/");
                bli.setBookAppraise("不错不错，很好的一本书，看了很多遍了，非常的推荐。。。。");
                this.data.add(bli);
            }
        }

        mAdapter = new BookListCreatAdapter(this.data);
        mAdapter.setOnItemClickListener(new BookListCreatAdapter.OnItemRemoveListener() {
            @Override
            public void onItemEdit(int position) {
                LogTools.H("编辑：" + position);
                Intent intent = new Intent(BookListCreatActivity.this, BookListEditDialogActivity.class);
                intent.putExtra("data", new Gson().toJson(data.get(position)));
//                intent.putExtra("position", position);
                currentPosition = position;
                startActivityLimitForResult(intent, 1001);
            }

            @Override
            public void onItemRemove(int position) {
                LogTools.H("删除：" + position);
                data.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        // 设置adapter
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1002) {
            String content = data.getStringExtra("content");
            LogTools.H(currentPosition + " = " + content);
            if (currentPosition > -1) {
                this.data.get(currentPosition).setBookAppraise(content);
                this.mAdapter.notifyDataSetChanged();
            }
        }
    }

    //添加书籍
    public void addBookList(View view) {

    }

}