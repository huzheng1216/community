package com.flyersoft.discuss.tools;

/**
 * 图片上传监听
 * Created by huzheng on 2017/12/16.
 */

public abstract class UploadListener {
    public abstract void onProgress(int progress);
    public abstract void onSuccess(String url);
    public abstract void onFailed();
}
