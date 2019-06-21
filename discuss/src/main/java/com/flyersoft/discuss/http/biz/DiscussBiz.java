package com.flyersoft.discuss.http.biz;

import android.content.Context;

import com.flyersoft.discuss.http.base.BaseBiz;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.Book;
import com.flyersoft.discuss.javabean.BookAndDiscuss;
import com.flyersoft.discuss.javabean.CommentAndDiscuss;
import com.flyersoft.discuss.javabean.DetailBookInfo;
import com.flyersoft.discuss.javabean.account.Collection;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.javabean.seekbook.DiscussUpload;

import java.util.List;

import io.reactivex.Observable;

/**
 * 处理书荒相关业务
 * Created by 37399 on 2016/10/23.
 */
public class DiscussBiz extends BaseBiz {

    public DiscussBiz(Context context) {
        super(context);
    }

    /**
     * 提交书慌主题
     */
    public Observable<BaseRequest> addDiscuss(DiscussUpload discuss) {

        return mMRRequestService.addDiscuss(discuss.toMap());
    }

    /**
     * 搜索书慌讨论
     */
    public Observable<BaseRequest<List<Discuss>>> getDiscuss(int type, int from, int maxCount, String userName, int sortType) {

        return mMRRequestService.getDiscuss(type, from, maxCount, userName, sortType);
    }

    /**
     * 查找其他数据
     *
     * @param softType
     * @param from
     * @param maxCount
     * @param userName
     * @param type
     * @return
     */
    public Observable<BaseRequest<List<Discuss>>> findData(String softType, int from, int maxCount, String userName, String type) {
        return mMRRequestService.findData(softType, from, maxCount, userName, type);
    }

    /**
     * 查找动态数据
     *
     * @param from
     * @param maxCount
     * @param userName
     * @return
     */
    public Observable<BaseRequest<List<Discuss>>> queryActionData(String userName, int from, int maxCount) {
        return mMRRequestService.queryActionData(userName, from, maxCount);
    }

    /**
     * 获取收藏/关注列表
     *
     * @param userName
     * @param from
     * @param maxCount
     * @return
     */
    public Observable<BaseRequest<Collection>> queryDataOfUser(String userName, int from, int maxCount) {
        return mMRRequestService.queryDataOfUser(userName, from, maxCount);
    }

    /**
     * 搜索书籍
     * @param userName
     * @param name
     * @param auther
     * @return
     */
    public Observable<BaseRequest<List<Book>>> queryBook(String userName, String name, String auther) {
        return mMRRequestService.queryBook(userName, name, auther);
    }

    /**
     * 获取书籍信息
     * @param bookName
     * @param bookAuthor
     * @param userName
     * @return
     */
    public Observable<BaseRequest<CommentAndDiscuss>> getBookInfo(String bookName, String bookAuthor, String userName, int from, int maxCount) {
        return mMRRequestService.getBookInfo(bookName, bookAuthor, userName, from, maxCount);
    }

    /**
     * 获取书籍信息
     * @param bookName
     * @param bookAuthor
     * @param userName
     * @return
     */
    public Observable<BaseRequest<List<BookAndDiscuss>>> querySBook(String userName, String bookName, String bookAuthor) {
        return mMRRequestService.querySBook(userName, bookName, bookAuthor);
    }
}
