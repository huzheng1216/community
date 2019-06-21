package com.flyersoft.discuss.javabean.seekbook;

/**
 * 讨论或帖子的实体，要与评论分开，
 * 精彩书评，书荒互助，综合讨论，都用此表示，只是类型不同
 */

public class Discuss {

    private String discussId;
    private String title;
    private String cont;
    private String createTime;
    private String shareLink;
    private int discussChildType;
    private int asterisk;
    private String bookName;
    private String bookAuthor;
    private String bookType;
    private String bookIcn;
    private String userName;
    private String userIcn;
    private String userId;
    private int commCount;
    private int sameFeelCount;
    private int usefulCount;
    private int uselessCount;
    private int sharedCount;
    private int discussType;

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getBookIcn() {
        return bookIcn;
    }

    public void setBookIcn(String bookIcn) {
        this.bookIcn = bookIcn;
    }

    public int getCommCount() {
        return commCount;
    }

    public void setCommCount(int commCount) {
        this.commCount = commCount;
    }

    public int getSameFeelCount() {
        return sameFeelCount;
    }

    public void setSameFeelCount(int sameFeelCount) {
        this.sameFeelCount = sameFeelCount;
    }

    public int getUselessCount() {
        return uselessCount;
    }

    public void setUselessCount(int uselessCount) {
        this.uselessCount = uselessCount;
    }

    public int getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(int sharedCount) {
        this.sharedCount = sharedCount;
    }

    public String getDiscussId() {
        return discussId;
    }

    public void setDiscussId(String discussId) {
        this.discussId = discussId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getDiscussChildType() {
        return discussChildType;
    }

    public void setDiscussChildType(int discussChildType) {
        this.discussChildType = discussChildType;
    }

    public int getAsterisk() {
        return asterisk;
    }

    public void setAsterisk(int asterisk) {
        this.asterisk = asterisk;
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

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
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

    public int getUsefulCount() {
        return usefulCount;
    }

    public void setUsefulCount(int usefulCount) {
        this.usefulCount = usefulCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDiscussType() {
        return discussType;
    }

    public void setDiscussType(int discussType) {
        this.discussType = discussType;
    }
}
