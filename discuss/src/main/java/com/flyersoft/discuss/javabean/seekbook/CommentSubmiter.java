package com.flyersoft.discuss.javabean.seekbook;

import java.util.HashMap;
import java.util.Map;

/**
 * 评论提交
 * Created by huzheng on 2017/9/16.
 */

public class CommentSubmiter {

    private String userName;
    private String cont;
    private String discussId;
    private String replyName;
    private String replyCommId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getDiscussId() {
        return discussId;
    }

    public void setDiscussId(String discussId) {
        this.discussId = discussId;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getReplyCommId() {
        return replyCommId;
    }

    public void setReplyCommId(String replyCommId) {
        this.replyCommId = replyCommId;
    }

    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>(5);
        map.put("userName", userName);
        map.put("cont", cont);
        map.put("discussId", discussId);
        map.put("replyName", replyName);
        map.put("replyCommId", replyCommId);
        return map;
    }
}
