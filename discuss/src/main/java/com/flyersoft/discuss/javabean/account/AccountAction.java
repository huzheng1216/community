package com.flyersoft.discuss.javabean.account;

/**
 * 账户相关行为封装
 * Created by 37399 on 2016/11/5.
 */
public class AccountAction {

    //登陆授权
    public static final int ACCREDIT_LANDING_OK = 1;
    public static final int ACCREDIT_LANDING_NO = 2;
    public static final int ACCREDIT_LANDING_CANCEL = 3;

    //支付
    public static final int ACCREDIT_PAY_OK = 4;
    public static final int ACCREDIT_PAY_NO = 5;
    public static final int ACCREDIT_PAY_CANCEL = 6;

    private int action;
    private String accredit_landing_code;//登陆授权的code
    private String out_trade_no;//支付订单号
    private String total;//支付金额

    public AccountAction(int action){
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public String getAccredit_landing_code() {
        return accredit_landing_code;
    }

    public void setAccredit_landing_code(String accredit_landing_code) {
        this.accredit_landing_code = accredit_landing_code;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
