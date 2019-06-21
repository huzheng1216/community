package com.flyersoft.discuss.shuhuang;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.javabean.seekbook.Comments;
import com.flyersoft.discuss.tools.StringTools;
import com.flyersoft.discuss.tools.ToastTools;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 评论弹窗
 * Created by huzheng on 2017/9/17.
 */

public class CreatBookDialog extends Dialog {

    private EditText name;
    private EditText auther;
    private TextView cancel;
    private TextView submit;
    private TextView select;
    private SimpleDraweeView bookicn;
//    private GridView gridView;

    private DialogListener listener;

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public CreatBookDialog(@NonNull Context context) {
        super(context, R.style.Theme_Light_NoTitle_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.creat_book_dialog_main);

        name = findViewById(R.id.discussion_book_choose_creat_name_edit);
        auther = findViewById(R.id.discussion_book_choose_creat_auther_edit);
        bookicn = findViewById(R.id.discussion_book_choose_dialog_book_pic);
        select = findViewById(R.id.discussion_book_choose_select_bookicon);
        cancel = findViewById(R.id.discussion_book_choose_creat_no);
        submit = findViewById(R.id.discussion_book_choose_creat_ok);
//        gridView = findViewById(R.id.discussion_book_choose_creat_pic_gridview);


//        gridView.setAdapter(new MyAdapter(getContext()));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatBookDialog.this.dismiss();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null) {
                    listener.onBookSelect();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                String a = auther.getText().toString();
                if (StringTools.isEmpty(n)||StringTools.isEmpty(a)) {
                    ToastTools.showToast(getContext(), "必须输入完整信息！");
                    return;
                }
                if (listener != null) {
                    listener.onSbumit(n, a);
                }
                CreatBookDialog.this.dismiss();
            }
        });

        Window win = getWindow();
        if (win != null) {
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            // dialog 布局位于底部
            win.setGravity(Gravity.CENTER);
            // 设置进出场动画
            win.setWindowAnimations(R.style.Animation_Bottom);
        }
    }

    @Override
    public void show() {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                InputMethodManager inputManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(et, 0);
//            }
//        }, 100);
        super.show();
    }

    public void setBookImg(String bookImg) {
        bookicn.setImageURI(bookImg);
    }

    interface DialogListener {
        abstract void onSbumit(String name, String auther);
        abstract void onBookSelect();
    }



    //自定义适配器
    class MyAdapter extends BaseAdapter {
        //上下文对象
        private Context context;
        //图片数组
        private Integer[] imgs = {
                R.mipmap.ic_launcher,
                R.mipmap.logo_zhifubao,
                R.mipmap.logo_weixin,
                R.mipmap.ic_launcher_round,
        };
        MyAdapter(Context context){
            this.context = context;
        }
        public int getCount() {
            return imgs.length;
        }

        public Object getItem(int item) {
            return item;
        }

        public long getItemId(int id) {
            return id;
        }

        //创建View方法
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.book_creat_pic_select_item, null);
//                imageView.setLayoutParams(new GridView.LayoutParams(75, 75));//设置ImageView对象布局
//                imageView.setAdjustViewBounds(false);//设置边界对齐
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
//                imageView.setPadding(8, 8, 8, 8);//设置间距
            }
            else {
                view = convertView;
            }
            ImageView pic = view.findViewById(R.id.book_creat_item_pic);
            TextView select = view.findViewById(R.id.book_creat_item_pic_select);
            pic.setImageResource(imgs[position]);//为ImageView设置图片资源
            return view;
        }
    }
}
