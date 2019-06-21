package com.flyersoft.discuss.javabean.seekbook;

/**
 * 评论实体
 * Created by huzheng on 2017/9/16.
 */

public class Comments {

    private String discussId;//讨论唯一标识符
    private String commId;//评论的唯一标识符
    private String cont;//内容
    private String createTime;//创建时间
    private String replyUserName;//被回复者的名称
    private String userName;//评论的创建者
    private String userIcn;//评论创建者的头像
    private String floorNum;//评论创建者的头像
    private String sameFeelCount;//点赞数量
    private String sharedCount;//分享数量
    private String usefulCount;//有用数量
    private String uselessCount;//无用数量
    private String hot;//是否为热门评论（2为热门评论）

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getSameFeelCount() {
        return sameFeelCount;
    }

    public void setSameFeelCount(String sameFeelCount) {
        this.sameFeelCount = sameFeelCount;
    }

    public String getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(String sharedCount) {
        this.sharedCount = sharedCount;
    }

    public String getUsefulCount() {
        return usefulCount;
    }

    public void setUsefulCount(String usefulCount) {
        this.usefulCount = usefulCount;
    }

    public String getUselessCount() {
        return uselessCount;
    }

    public void setUselessCount(String uselessCount) {
        this.uselessCount = uselessCount;
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    public String getDiscussId() {
        return discussId;
    }

    public void setDiscussId(String discussId) {
        this.discussId = discussId;
    }

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcn() {
        return userIcn;
    }

    public void setUserIcn(String userIcn) {
        this.userIcn = userIcn;
    }
}
