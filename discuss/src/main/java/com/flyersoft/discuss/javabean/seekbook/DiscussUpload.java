package com.flyersoft.discuss.javabean.seekbook;

import java.util.HashMap;
import java.util.Map;

/**
 * 书单／讨论／书荒
 * ---上报数据用
 * <p>
 * Created by huzheng on 2017/9/11.
 */

public class DiscussUpload {

    private String title;
    private String cont;
    private int type;//1综合讨论 2书荒 3书单 4书评
    private int chiType;//当type=1是，chiType取值为：1话题2投票（当type=1时，必填）
    private String userName;//第三方账户的用户名

    //当type=4时，必填
    private int asterisk;//星的个数
    private String bookNme;
    private String bookAuthor;
    private String bookType = "0";
    private String bookicn;

    public String getBookicn() {
        return bookicn;
    }

    public void setBookicn(String bookicn) {
        this.bookicn = bookicn;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChiType() {
        return chiType;
    }

    public void setChiType(int chiType) {
        this.chiType = chiType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAsterisk() {
        return asterisk;
    }

    public void setAsterisk(int asterisk) {
        this.asterisk = asterisk;
    }

    public String getBookNme() {
        return bookNme;
    }

    public void setBookNme(String bookNme) {
        this.bookNme = bookNme;
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

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(10);
        if (title != null) {
            map.put("title", title);
        }
        if (cont != null) {
            map.put("cont", cont);
        }
        map.put("type", type + "");
        map.put("chiType", chiType + "");
        if (userName != null) {
            map.put("userName", userName);
        }
        map.put("asterisk", asterisk + "");
        if (bookAuthor != null) {
            map.put("bookAuthor", bookAuthor);
        }
        if (userName != null) {
            map.put("userName", userName);
        }
        if (bookType != null) {
            map.put("bookType", bookType);
        }
        if (bookNme != null) {
            map.put("bookName", bookNme);
        }
        if (bookicn != null) {
            map.put("bookicn", bookicn);
        }
        return map;
    }
}
