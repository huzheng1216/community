package com.flyersoft.discuss.shuhuang;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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

public class CommentDialog extends Dialog {

    private EditText et;
    private TextView cancel;
    private TextView submit;
    private TextView title;
    private String titleStr = "写评论";
    private Comments comment;

    private DialogListener listener;

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public CommentDialog(@NonNull Context context) {
        super(context, R.style.Theme_Light_NoTitle_Dialog);
    }

    public void setTitle(String title) {
        if (this.title != null) {
            this.title.setText(title);
        } else {
            this.titleStr = title;
        }
    }

    //被回复的评论
    public void setReplay(Comments comment) {
        this.comment = comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.comment_dialog_main);

        et = findViewById(R.id.comment_dialog_edit);
        cancel = findViewById(R.id.comment_dialog_cancel);
        submit = findViewById(R.id.comment_dialog_submit);
        title = findViewById(R.id.comment_dialog_title);

        title.setText(titleStr);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialog.this.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = et.getText().toString();
                if (StringTools.isEmpty(s)) {
                    ToastTools.showToast(getContext(), "内容不能为空！");
                    return;
                }
                if (s.length() < 3) {
                    ToastTools.showToast(getContext(), "内容不能少于3个字！");
                    return;
                }
                if (listener != null) {
                    listener.onSbumit(s, comment);
                    comment = null;
                }
                CommentDialog.this.dismiss();
            }
        });

        Window win = getWindow();
        if (win != null) {
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            // dialog 布局位于底部
            win.setGravity(Gravity.BOTTOM);
            // 设置进出场动画
            win.setWindowAnimations(R.style.Animation_Bottom);
        }
    }

    @Override
    public void show() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et, 0);
            }
        }, 100);
        super.show();
    }

    interface DialogListener {
        abstract void onSbumit(String content, Comments comment);
    }
}
