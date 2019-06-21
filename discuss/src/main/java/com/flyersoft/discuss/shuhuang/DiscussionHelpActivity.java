package com.flyersoft.discuss.shuhuang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.flyersoft.discuss.R;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.StringTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.weight.HeaderModeStyleOne;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 求助主界面
 * Created by huzheng on 2017/9/1.
 */

public class DiscussionHelpActivity extends DiscessBaseActivity {

    private EditText title_edit;
    private EditText content_edit;
    private String json;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuhuanghelp_main);

        initData();
        initView();
    }

    private void initData() {
        json = getIntent().getStringExtra("json");
    }

    private void initView() {
        title_edit = findViewById(R.id.shuhuanghelp_main_title_edit);
        content_edit = findViewById(R.id.shuhuanghelp_main_content_edit);
        HeaderModeStyleOne headerModeStyleOne = findViewById(R.id.header1);
        headerModeStyleOne.init(TITLE_CREAT, "发布", new HeaderModeStyleOne.HeaderModeStyleOneListener(){
            @Override
            public void onBack() {
                DiscussionHelpActivity.this.finish();
            }

            @Override
            public void onOption() {

                String title = title_edit.getText().toString();
                String content = content_edit.getText().toString();
                if(StringTools.isEmpty(title) || StringTools.isEmpty(content)){
                    ToastTools.showToast(DiscussionHelpActivity.this, "标题或内容不能为空！");
                    return;
                }

                addDiscuss(title, content, json, new Observer<BaseRequest>() {

                    @Override
                    public void onError(Throwable e) {
                        LogTools.H("添加书荒： "+e.getMessage());
                        ToastTools.showToast(DiscussionHelpActivity.this, "添加失败，请重试....");
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        LogTools.H("添加书荒： "+baseRequest.getErrorMsg());
                        ToastTools.showToast(DiscussionHelpActivity.this, "添加成功！");
                        DiscussionHelpActivity.this.finish();
                    }
                });
            }
        });
    }
}
