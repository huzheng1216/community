package com.flyersoft.discuss.tools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.account.TencentYunConfig;
import com.tencent.cos.COSClient;
import com.tencent.cos.COSConfig;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.PutObjectRequest;
import com.tencent.cos.model.PutObjectResult;
import com.tencent.cos.task.listener.IUploadTaskListener;
import com.tencent.cos.utils.FileUtils;

import java.text.SimpleDateFormat;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huzheng on 2017/12/16.
 */

public class TencentUploadTools {

    private static TencentUploadTools tencentUploadTools;
    private COSConfig config;
    COSClient cos;
    private TencentYunConfig tencentYunConfig;

    private TencentUploadTools(Context context) {
    }

    public synchronized static TencentUploadTools getInstance(Context context) {
        if (tencentUploadTools == null) {
            tencentUploadTools = new TencentUploadTools(context);
        }
        return tencentUploadTools;
    }

    public void init(final Context context) {
        MRManager.getInstance(context).getTencentYunConfig()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRequest<TencentYunConfig>>() {
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
                    public void onNext(BaseRequest<TencentYunConfig> listBaseRequest) {
                        //初始化腾讯云图片
                        config = new COSConfig();
                        config.setEndPoint("gz");
//                        config.setConnectionTimeout(20000);
//                        config.setSocketTimeout(20000);
//                        config.setMaxConnectionsCount(10000);
//                        config.setMaxRetryCount(2);
//                        config.setHttpProtocol("http://");
                        tencentYunConfig = listBaseRequest.getData();
                        //创建COSlient对象，实现对象存储的操作
                        cos = new COSClient(context, tencentYunConfig.getAppId(), config, "xxxx");
                    }
                });

    }

    public void upload(Activity context, String path, final UploadListener uploadListener) {

        if (tencentYunConfig == null || tencentYunConfig.getAppId() == null) {
            ToastTools.showToast(context, "上传异常，稍后重试！");
            return;
        }

        //判断文件类型
        if (!MediaFileUtil.isImageFileType(path)) {
            ToastTools.showToast(context, "文件类型错误！");
            return;
        }

        //组装远程路径: 年月/(文件名+用户名)-md5加密
        String filename = FileUtils.getFileName(path);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(new java.util.Date());
        String petName = AccountData.getInstance(context).getmUserInfo().getPetName();
        String cosName = StringTools.generator(filename + petName) + "." + filename.substring(filename.lastIndexOf(".") + 1);

        String bucket = tencentYunConfig.getBucketName();
        final String cosPath = "/" + date + "/" + cosName;
        String srcPath = path;
//        String sign = tencentYunConfig.getSign();
        final PutObjectRequest putObjectRequest = new PutObjectRequest();
        /** 设置Bucket */
        putObjectRequest.setBucket(bucket);
        /** 设置cosPath :远程路径*/
        putObjectRequest.setCosPath(cosPath);
        /** 设置srcPath: 本地文件的路径 */
        putObjectRequest.setSrcPath(srcPath);
        /** 设置sign: 签名，此处使用多次签名 */
        putObjectRequest.setSign(getLocalSign());
        /** 设置 insertOnly: 是否上传覆盖同名文件*/
        putObjectRequest.setInsertOnly("1");
        putObjectRequest.setListener(new IUploadTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {

                PutObjectResult result = (PutObjectResult) cosResult;
                if (result != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" 上传结果： ret=" + result.code + "; msg =" + result.msg + "\n");
                    stringBuilder.append(" access_url= " + result.access_url == null ? "null" : result.access_url + "\n");
                    stringBuilder.append(" resource_path= " + result.resource_path == null ? "null" : result.resource_path + "\n");
                    stringBuilder.append(" url= " + result.url == null ? "null" : result.url);
                    Log.w("TEST", stringBuilder.toString());
                }

                if (uploadListener != null) {
                    uploadListener.onSuccess(cosPath);
                }
            }

            @Override
            public void onFailed(COSRequest COSRequest, final COSResult cosResult) {
                Log.w("TEST", "上传出错： ret =" + cosResult.code + "; msg =" + cosResult.msg);
            }

            @Override
            public void onProgress(COSRequest cosRequest, final long currentSize, final long totalSize) {
                float progress = (float) currentSize / totalSize;
                progress = (progress * 100);
                Log.w("TEST", "进度：  " + (int) progress + "%");
                if (uploadListener != null) {
                    uploadListener.onProgress((int) progress);
                }
            }

            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {

                if (uploadListener != null) {
                    uploadListener.onFailed();
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                cos.putObject(putObjectRequest);
            }
        }).start();
    }


    /**
     * 本地签名
     *
     * @return
     */
    public String getLocalSign() {
        String secretKey = tencentYunConfig.getSecretKey();
        String appid = tencentYunConfig.getAppId();
        String bucket = tencentYunConfig.getBucketName();
        String secretId = tencentYunConfig.getSecretId();
        LocalCredentialProvider localCredentialProvider = new LocalCredentialProvider(tencentYunConfig.getSecretKey());
        return localCredentialProvider.getSign(tencentYunConfig.getAppId(), tencentYunConfig.getBucketName(), tencentYunConfig.getSecretId(), null, 60 * 60);
    }

    public String getLocalOnceSign(String fileId) {
        String secretKey = tencentYunConfig.getSecretKey();
        String appid = tencentYunConfig.getAppId();
        String bucket = tencentYunConfig.getBucketName();
        String secretId = tencentYunConfig.getSecretId();
        LocalCredentialProvider localCredentialProvider = new LocalCredentialProvider(secretKey);
        return localCredentialProvider.getSign(appid, bucket, secretId, fileId, 60 * 60);
    }
}
