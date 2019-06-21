package com.flyersoft.discuss.javabean;

/**
 * 书本简介
 * Created by Administrator on 2016/9/23.
 */
public class Book {


    private String allwords;
    private String author;//作者
    private String bookId;//书Id
    private String bookName;//书名
    private String brief;//简介
    private String feeType;
    private String keyword;//关键词
    private String leastCharpterNo;//最后更新章节号
    private String leastCharpterTitle;//最后更新章节名
    private String maxFreeChapter;
    private String midImage;//封面图片
    private String price;//价格
    private String size;//字数
    private String status;
    private String updatetime;//最近更新时间
    private String writeStatus;//更新状态
    private String categoryId;//分类id

    private boolean enable;
    private String id;
    private String title;
    private String imgUrl;
    private String linkUrl;
    private boolean choosed;

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getAllwords() {
        return allwords;
    }

    public void setAllwords(String allwords) {
        this.allwords = allwords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLeastCharpterNo() {
        return leastCharpterNo;
    }

    public void setLeastCharpterNo(String leastCharpterNo) {
        this.leastCharpterNo = leastCharpterNo;
    }

    public String getLeastCharpterTitle() {
        return leastCharpterTitle;
    }

    public void setLeastCharpterTitle(String leastCharpterTitle) {
        this.leastCharpterTitle = leastCharpterTitle;
    }

    public String getMaxFreeChapter() {
        return maxFreeChapter;
    }

    public void setMaxFreeChapter(String maxFreeChapter) {
        this.maxFreeChapter = maxFreeChapter;
    }

    public String getMidImage() {
        return midImage;
    }

    public void setMidImage(String midImage) {
        this.midImage = midImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getWriteStatus() {
        return writeStatus;
    }

    public void setWriteStatus(String writeStatus) {
        this.writeStatus = writeStatus;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
