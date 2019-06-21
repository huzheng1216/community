package com.flyersoft.discuss.javabean.account;

import com.flyersoft.discuss.javabean.seekbook.Discuss;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏关注列表
 * Created by huzheng on 2017/11/22.
 */

public class Collection {

    private List<FocusAuther> userList = new ArrayList<FocusAuther>();//关注列表
    private List<Discuss> disList = new ArrayList<Discuss>();//收藏列表

    public List<Discuss> getDisList() {
        return disList;
    }

    public void setDisList(List<Discuss> disList) {
        this.disList = disList;
    }

    public List<FocusAuther> getUserList() {
        return userList;
    }

    public void setUserList(List<FocusAuther> userList) {
        this.userList = userList;
    }
}
