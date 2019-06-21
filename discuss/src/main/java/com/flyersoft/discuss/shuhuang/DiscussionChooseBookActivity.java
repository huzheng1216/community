package com.flyersoft.discuss.shuhuang;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flyersoft.discuss.R;
import com.flyersoft.discuss.config.Const;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.Book;
import com.flyersoft.discuss.javabean.ShelfBook;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.seekbook.DiscussUpload;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.StringTools;
import com.flyersoft.discuss.tools.TencentUploadTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.tools.UploadListener;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;
import com.flyersoft.discuss.weight.HeaderModeStyleOne;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 选择书籍主界面
 * Created by huzheng on 2017/9/1.
 */

public class DiscussionChooseBookActivity extends DiscessBaseActivity implements CreatBookDialog.DialogListener {

    private RecyclerView mRecyclerView;
    private EndlessRecyclerOnScrollListener listener;
    private DiscussionChooseBookAdapter mAdapter;
    private List<Book> data;
    private Button search;
    private Button creat;
    private EditText name;
    private EditText auther;
    private CreatBookDialog dialog;
    private HeaderModeStyleOne headerModeStyleOne;
    String currentPath = null;
    private String bookUrl = "default";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussion_choose_book_main);

        initData();
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case 1:
                Uri uri = data.getData();
                currentPath = getPath(this, uri);
                uiload(currentPath);
                break;
            default:
                break;
        }
    }

    private void uiload(final String currentPath) {
//        String path = Environment.getExternalStorageDirectory() + "/icon.png";
//        File file = new File(path);
        LogTools.H("文件路径：" + currentPath);
        TencentUploadTools.getInstance(DiscussionChooseBookActivity.this).upload(DiscussionChooseBookActivity.this, currentPath, new UploadListener() {
            @Override
            public void onProgress(int progress) {
                LogTools.H("上传进度：" + progress);
            }

            @Override
            public void onSuccess(String url) {
                LogTools.H("上传成功: " + url);
                bookUrl = "http://bicn.moonreader.cn" + url;
                if (dialog != null) {
                    dialog.setBookImg(bookUrl);
                }
            }

            @Override
            public void onFailed() {
                LogTools.H("上传失败");
                ToastTools.showToast(DiscussionChooseBookActivity.this, "图片上传失败！");
            }
        });

    }


    protected String getPath(Context context, Uri uri) {
        String path = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.MediaColumns.DATA};
            String colum_name = "_data";
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                Log.w("XIAO", "count =" + cursor.getCount());
                if (cursor != null && cursor.moveToFirst()) {
                    int colum_index = cursor.getColumnIndex(colum_name);
                    path = cursor.getString(colum_index);
                }
            } catch (Exception e) {
                Log.w("XIAO", e.getMessage(), e);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        } else {
            Toast.makeText(this, "选择文件路径为空", Toast.LENGTH_SHORT).show();
        }
        return path;
    }

    private void initData() {
        data = new ArrayList<>();
        MRManager.getInstance(this).getMyShelf(0, 20, new RequestCallBack<List<ShelfBook>>() {
            @Override
            public void onSuccess(List<ShelfBook> shelfBooks) {
                for (ShelfBook s : shelfBooks) {
                    Book book = new Book();
                    book.setTitle(s.getBookName());
                    data.add(book);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
        mAdapter = new DiscussionChooseBookAdapter(data);
        mAdapter.setOnItemClickListener(new DiscussionChooseBookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mAdapter.setCurrentPosition(position);
                mAdapter.notifyDataSetChanged();
                headerModeStyleOne.showOption();
            }
        });
    }

    private void initView() {
        headerModeStyleOne = findViewById(R.id.header1);
        headerModeStyleOne.init("选择你要评的书", "下一步", new HeaderModeStyleOne.HeaderModeStyleOneListener() {
            @Override
            public void onBack() {
                DiscussionChooseBookActivity.this.finish();
            }

            @Override
            public void onOption() {
                if (mAdapter.getCurrentPosition() < 0) {
                    ToastTools.showToast(DiscussionChooseBookActivity.this, "请选择书籍");
                } else {
                    //下一步
                    Intent intent = new Intent(DiscussionChooseBookActivity.this, DiscussionHelpActivity.class);
                    Book book = data.get(mAdapter.getCurrentPosition());
                    DiscussUpload discuss = new DiscussUpload();
                    discuss.setBookNme(book.getBookName());
                    discuss.setBookAuthor(book.getAuthor());
                    discuss.setBookicn(bookUrl);
                    discuss.setChiType(1);
                    discuss.setType(4);
                    discuss.setUserName(AccountData.getInstance(DiscussionChooseBookActivity.this).getmUserInfo().getPetName());
                    discuss.setBookType(book.getFeeType());
                    intent.putExtra("json", new Gson().toJson(discuss));
                    intent.putExtra("action", Const.ACTION_JINGCAISHUPING);
                    DiscussionChooseBookActivity.this.startActivity(intent);
                    DiscussionChooseBookActivity.this.finish();
                }
            }
        });
        headerModeStyleOne.hidenOption();

        search = findViewById(R.id.discussion_book_choose_search_bt);
        creat = findViewById(R.id.discussion_book_choose_creat_bt);
        name = findViewById(R.id.discussion_book_choose_edit);
        auther = findViewById(R.id.discussion_book_choose_edit2);


        mRecyclerView = findViewById(R.id.discussion_book_choose_recycler_view);
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
                mAdapter.notifyDataSetChanged();
            }
        };
        mRecyclerView.addOnScrollListener(listener);
    }

    //搜书
    public void onSearch(View view) {

        String n = name.getText().toString();
        String a = auther.getText().toString();
        if (StringTools.isNotEmpty(n)) {
            data.clear();
            MRManager.getInstance(this).queryBook(AccountData.getInstance(this).getmUserInfo().getPetName(), n, a)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseRequest<List<Book>>>() {

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
                        public void onNext(BaseRequest<List<Book>> listBaseRequest) {
                            List<Book> books = listBaseRequest.getData();
                            if (books.size() > 0) {
                                data.addAll(books);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                ToastTools.showToast(DiscussionChooseBookActivity.this, "没有这本书，可以尝试创建！");
                            }
                        }
                    });
        } else {
            ToastTools.showToast(this, "请输入书名！");
        }
    }

    //创建
    public void onCreat(View view) {
        if (dialog == null) {
            dialog = new CreatBookDialog(this);
            dialog.setListener(this);
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onSbumit(String name, String auther) {
        data.clear();
        Book book = new Book();
        book.setBookName(name);
        book.setAuthor(auther);
        book.setMidImage(bookUrl);
        data.add(book);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBookSelect() {

        //请求权限
        if (ActivityCompat.checkSelfPermission(DiscussionChooseBookActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DiscussionChooseBookActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }
}
