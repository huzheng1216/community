package com.flyersoft.discuss.javabean.seekbook;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 书单
 */
public class BookList {

    // 1.itemtype ,无实际意义，爬虫区分时使用
    private String itemType;
    //2.头像url
    private String headUrl;
    // 3.用户Id = yousuu + id
    private String userId;
    // 4.书单用户名
    private String userName;
    // 5.书单备注
    private String listNote = "";
    // 6.书单简介
    private String listIntro = "";
    // 7.书单分类
    private String listType = "1";
    // 8.书单名称
    private String listName;
    // 9.书单点击数
    private int clickCount;
    // 10.书单点赞数
    private int likeCount;
    //11.书单收藏数
    private int keepCount;
    //12.书单Id = yousuu + id
    private String listId;
    private String createTime;
    private String updateTime;
    // 是否允许，外人添加书籍，0，不允许（默认），1允许，但是需要用户审核，2直接可以上线
    private int other;

    //书单列表
    private List<BookListInfo> list;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getListNote() {
        return listNote;
    }

    public void setListNote(String listNote) {
        this.listNote = listNote;
    }

    public String getListIntro() {
        return listIntro;
    }

    public void setListIntro(String listIntro) {
        this.listIntro = listIntro;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getKeepCount() {
        return keepCount;
    }

    public void setKeepCount(int keepCount) {
        this.keepCount = keepCount;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public List<BookListInfo> getList() {
        return list;
    }

    public void setList(List<BookListInfo> list) {
        this.list = list;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("listName", this.listName);
        map.put("listIntro", this.listIntro);
        map.put("listNote", this.listNote);
        map.put("listType", this.listType);
        map.put("userId", this.userId);
        map.put("userName", this.userName);
        map.put("list", new Gson());
        return map;
    }
}
