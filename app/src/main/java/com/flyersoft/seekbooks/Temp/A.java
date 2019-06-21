package com.flyersoft.seekbooks.Temp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/20 0020.
 */

public class A {

    public static void log(String text) {
        Log.i("MR2", text);
    }

    public static void toast(Context context, String text) {
        log("toast: " + text);
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    public static void error(Throwable e) {
        try {
            if (e instanceof OutOfMemoryError){
                log("####OutOfMemoryError####-----------------------------------");
            } else{
                log("####ERROR####-----------------------------------");
                log(errorMsg(e) + "##");
                e.printStackTrace();
            }
        } catch (Throwable e2) {
            e2.printStackTrace();
        }
    }
    public static String errorMsg(Throwable e) {
        String err = e.getMessage();
        if (err == null)
            try {
                err = e.toString(); //UserRecoverableAuthIOException error here
            } catch (Throwable e2) {
                e2.printStackTrace();
                return "";
            }
        return err;
    }
}
