package com.flyersoft.discuss.applocation;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.tools.DeviceConfig;
import com.flyersoft.discuss.tools.TencentUploadTools;
import com.tencent.cos.COSClient;
import com.tencent.cos.COSClientConfig;
//import com.tencent.bugly.Bugly;

/**
 * Created by Administrator on 2016/9/23.
 */
public class MoonReaderApplication extends Application {

//    private RefWatcher refWatcher;
//
//    public static RefWatcher getRefWatcher(Context context) {
//        MoonReaderApplication application = (MoonReaderApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化手机配置参数
        DeviceConfig.initScreenSize(this);
        DeviceConfig.initDeviceData(this);
        //初始化网络模块
        MRManager.getInstance(this);
        //初始化crash捕获以及版本升级
//        Bugly.init(getApplicationContext(), "eb3175c9b6", AppConfig.DEBUG);
        //初始化内存监测
//        refWatcher = LeakCanary.install(this);
        //初始化图片框架
        Fresco.initialize(this);

        //初始化腾讯云图片
        TencentUploadTools.getInstance(this).init(this);

        //初始化登录信息
        AccountData.initUserInfo(this);
    }
}
