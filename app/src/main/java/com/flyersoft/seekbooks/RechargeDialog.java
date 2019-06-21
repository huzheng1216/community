package com.flyersoft.seekbooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


/**
 * Created by zheng.hu on 2016/11/10.
 */
public class RechargeDialog extends BaseDialog implements View.OnClickListener {

    public RechargeDialog(Context context, RechargeListener rechargeListener) {
        super(context);
        this.rechargeListener = rechargeListener;
    }

    private RechargeListener rechargeListener;

    @Override
    protected int getDialogStyleId() {
        return DIALOG_COMMON_STYLE;
    }

    @Override
    protected View getView() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.userinfo_racharge_dialog_layout, null);
        view.findViewById(R.id.userinfo_recharge_del_img).setOnClickListener(this);
        view.findViewById(R.id.userinfo_recharge_bt1).setOnClickListener(this);
        view.findViewById(R.id.userinfo_recharge_bt2).setOnClickListener(this);
        view.findViewById(R.id.userinfo_recharge_bt3).setOnClickListener(this);
        view.findViewById(R.id.userinfo_recharge_bt4).setOnClickListener(this);
        view.findViewById(R.id.userinfo_recharge_bt5).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int count = 0;
        switch (id){
            case R.id.userinfo_recharge_bt1:
                count = 1;
                break;
            case R.id.userinfo_recharge_bt2:
                count = 10;
                break;
            case R.id.userinfo_recharge_bt3:
                count = 20;
                break;
            case R.id.userinfo_recharge_bt4:
                count = 50;
                break;
            case R.id.userinfo_recharge_bt5:
                count = 100;
                break;
            case R.id.userinfo_recharge_del_img:
                dismiss();
                return;
        }
        if(rechargeListener!=null){
            rechargeListener.recharge(count);
        }
    }

    public interface RechargeListener{
        abstract void recharge(int count);
    }
}
