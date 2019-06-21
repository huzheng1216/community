package com.flyersoft.discuss.tools;

import android.content.Context;
import android.widget.Toast;

public class ToastTools {

    private static Toast toast;

    public static void showToast(Context context, String str) {

        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        }
        toast.setText(str);
        toast.show();
    }

    public static void showToast(Context context, int str) {

        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        }
        toast.setText(str);
        toast.show();
    }
}
