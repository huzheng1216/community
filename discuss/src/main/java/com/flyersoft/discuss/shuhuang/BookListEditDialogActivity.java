package com.flyersoft.discuss.shuhuang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.BaseActivity;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.javabean.seekbook.BookListInfo;
import com.flyersoft.discuss.tools.StringTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.google.gson.Gson;

/**
 * creat by: huzheng
 * date: 2019/6/24
 * description:
 * 编辑推荐语专用
 */
public class BookListEditDialogActivity extends BaseActivity {

    private BookListInfo bookListInfo;
    private SimpleDraweeView pic;
    private EditText content;
    private TextView tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.book_list_edit_dialog_main);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
    }

    @Override
    protected void initView() {
        pic = findViewById(R.id.book_list_edit_book_pic);
        content = findViewById(R.id.book_list_edit_book_intro);
        tag = findViewById(R.id.book_list_edit_content_tag);
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tag.setText((content.getText().toString().length()) + "/200");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected void initParam() {
    }

    @Override
    protected void initData() {
        String data = getIntent().getStringExtra("data");
        if(StringTools.isNotEmpty(data)){
            bookListInfo = new Gson().fromJson(data, BookListInfo.class);
            pic.setImageURI(bookListInfo.getBookIcn());
            content.setText(bookListInfo.getBookAppraise());
            tag.setText((content.getText().toString().length()) + "/200");
        }else {
            ToastTools.showToast(this, "无法获取书籍信息。。。");
            this.finish();
        }
    }

    public void onClick(View view){
        int id = view.getId();
        if(id==R.id.book_list_creat_cancel){
            //取消
            this.finish();
        }else if(id==R.id.book_list_creat_ok){
            //保存
            String str = content.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("content", str);
            this.setResult(1002, intent);
            this.finish();
        }
    }
}
