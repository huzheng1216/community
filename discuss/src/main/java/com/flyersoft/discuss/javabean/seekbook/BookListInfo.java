package com.flyersoft.discuss.javabean.seekbook;

/**
 * 书单书籍
 */

public class BookListInfo {

    private String itemType;
    private String bookIcn;
    private String bookName;
    private String bookAuthor;
    // 字数
    private int bookWords;
    private String lastTime;  // 书籍最后更新时间

    //书籍评价
    private String bookAppraise;
    private String listId;    // 书单Id
    private String infoId;    // 详情Id
    private String createTime;
    private String updateTime;

    private int infoshow;  // 书籍是否展示 1展示  2不展示（审核后可展示）


    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getBookIcn() {
        return bookIcn;
    }

    public void setBookIcn(String bookIcn) {
        this.bookIcn = bookIcn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookWords() {
        return bookWords;
    }

    public void setBookWords(int bookWords) {
        this.bookWords = bookWords;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getBookAppraise() {
        return bookAppraise;
    }

    public void setBookAppraise(String bookAppraise) {
        this.bookAppraise = bookAppraise;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
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

    public int getInfoshow() {
        return infoshow;
    }

    public void setInfoshow(int infoshow) {
        this.infoshow = infoshow;
    }
}
