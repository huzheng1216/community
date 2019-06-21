package com.flyersoft.discuss.javabean;

/**
 * 点赞/有用
 * Created by huzheng on 2017/11/12.
 */

public class DisRecordResult {

    private String disId;//必填
    private String commId;
    private String type;//1=sameFeel(同感);2=shared(分享);3=complaint(举报);4=useful(有用);5=useless(无用);6=comms(新增评论时)
    private String count;
    private String createTime;

    public String getDisId() {
        return disId;
    }

    public void setDisId(String disId) {
        this.disId = disId;
    }

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
