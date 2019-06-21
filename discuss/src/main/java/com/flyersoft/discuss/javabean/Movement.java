package com.flyersoft.discuss.javabean;

import java.util.HashMap;
import java.util.Map;

/**
 * 关注/收藏
 * Created by huzheng on 2017/11/19.
 */

public class Movement {

    private String type;   //1=关注作者 2=收藏帖子据
    private String cont;  //收藏的帖子的标题或关注的作者的名
    private String contId;//收藏的帖子的Id 或关注作者的id
    private String userName;//当前用户
    private String userIcn; //关注的用户的头像，为了兼容抓取过来的用户
    private String createTime;  // 关注或收藏的时间
    private String moveId;  // 动态Id

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>(5);
        map.put("type", type);
        map.put("cont", cont);
        map.put("contId", contId);
        map.put("userName", userName);
        map.put("userIcn",userIcn);
        return map;
    }

    public String getUserIcn() {
        return userIcn;
    }

    public void setUserIcn(String userIcn) {
        this.userIcn = userIcn;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
