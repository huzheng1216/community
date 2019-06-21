package com.flyersoft.discuss.weight;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.flyersoft.discuss.R;

/**
 * Created by huzheng on 2017/9/17.
 */

public abstract class BaseDialog {

    //这些属性，Context 是肯定要的，基本对话框要用它
    protected Context context;
    private Display display;//这个设置显示属性用的
    private Dialog dialog;//自定义Dialog，Dialog还是要有一个的吧
    private View view;

    //对话框布局的样式ID (通过这个抽象方法，我们可以给不同的对话框设置不同样式主题)
    protected abstract int getDialogStyleId();
    //构建对话框的方法(都说了是不同的对话框，布局什么的肯定是不一样的)
    protected abstract View getView();

    //构造方法 来实现 最基本的对话框
    public BaseDialog(Context context) {
        this.context = context;
        this.view = getView();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.comment_dialog_main);
        Window dlgwindow=dialog.getWindow();
        dlgwindow.setGravity(Gravity.BOTTOM);

        //参数可以是Gravity.TOP（顶部），Gravity.BUTTON（底部），Gravity.LEFT（左边），Gravity.RIGHT（右边），Gravity.CENTER（居中）以及它们的组合

                //设置对话框大小

        //通过获取屏幕的大小，按照比例的方法设置比较便捷些

                //首先获得WindowManager实例

        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        WindowManager.LayoutParams p = dlgwindow.getAttributes(); // 获取对话框当前的参数值

        p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.5

        p.width = (int) (d.getWidth() * 1); // 宽度设置为整个屏幕宽度

        p.alpha = 0.5f; //设置对话框的透明度

        dlgwindow.setAttributes(p);//


    }

    /** * Dialog 的基础方法，
     *凡是要用的就在这写出来，然后直接用对话框调本来的方法就好了，不够自己加~hhh */

    //像这类设置对话框属性的方法，就返回值写自己，这样就可以一条链式设置了
    public BaseDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

    public BaseDialog setdismissListeren(DialogInterface.OnDismissListener dismissListener){
        dialog.setOnDismissListener(dismissListener);
        return this;
    }
}
