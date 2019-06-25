package com.flyersoft.discuss.http;

import android.content.Context;

import com.flyersoft.discuss.config.HttpBaseConfig;
import com.flyersoft.discuss.http.biz.AccountBiz;
import com.flyersoft.discuss.http.biz.ActivityBookBiz;
import com.flyersoft.discuss.http.biz.BookListBiz;
import com.flyersoft.discuss.http.biz.CatalogBookBiz;
import com.flyersoft.discuss.http.biz.CommentBiz;
import com.flyersoft.discuss.http.biz.DetailBookBiz;
import com.flyersoft.discuss.http.biz.DetailCatalogBookBiz;
import com.flyersoft.discuss.http.biz.DiscussBiz;
import com.flyersoft.discuss.http.biz.ModuleBookBiz;
import com.flyersoft.discuss.http.biz.SearchBookBiz;
import com.flyersoft.discuss.http.biz.ThirdBiz;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.http.body.LandingCodeBody;
import com.flyersoft.discuss.http.body.PayBody;
import com.flyersoft.discuss.http.body.SearchBookBody;
import com.flyersoft.discuss.http.body.WXAccessTokenBody;
import com.flyersoft.discuss.http.callback.RequestCallBack;
import com.flyersoft.discuss.javabean.Book;
import com.flyersoft.discuss.javabean.BookAndDiscuss;
import com.flyersoft.discuss.javabean.BookContent;
import com.flyersoft.discuss.javabean.BookDetail;
import com.flyersoft.discuss.javabean.CatalogDetail;
import com.flyersoft.discuss.javabean.ChargeRecords;
import com.flyersoft.discuss.javabean.CommentAndDiscuss;
import com.flyersoft.discuss.javabean.DefaultCode;
import com.flyersoft.discuss.javabean.DefaultInfo;
import com.flyersoft.discuss.javabean.DetailBookInfo;
import com.flyersoft.discuss.javabean.DetailCatalogDetail;
import com.flyersoft.discuss.javabean.DisRecord;
import com.flyersoft.discuss.javabean.DisRecordResult;
import com.flyersoft.discuss.javabean.Movement;
import com.flyersoft.discuss.javabean.ShelfBook;
import com.flyersoft.discuss.javabean.account.AmountInfo;
import com.flyersoft.discuss.javabean.account.Collection;
import com.flyersoft.discuss.javabean.account.PayConfig;
import com.flyersoft.discuss.javabean.account.TencentYunConfig;
import com.flyersoft.discuss.javabean.account.UserInfo;
import com.flyersoft.discuss.javabean.account.WXAccessToken;
import com.flyersoft.discuss.javabean.account.WXLandingConfig;
import com.flyersoft.discuss.javabean.account.ZFBLandingConfig;
import com.flyersoft.discuss.javabean.seekbook.BookList;
import com.flyersoft.discuss.javabean.seekbook.BookListInfo;
import com.flyersoft.discuss.javabean.seekbook.CommentSubmiter;
import com.flyersoft.discuss.javabean.seekbook.Comments;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.javabean.seekbook.DiscussUpload;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zheng.hu
 * @ClassName: MRManager
 * @Description: 网络请求综合管理类
 * @date 2016/9/26
 */
public class MRManager {

    private static MRManager mRManager;
    private ActivityBookBiz activityBookBiz;
    private ModuleBookBiz moduleBookBiz;
    private SearchBookBiz searchBookBiz;
    private CatalogBookBiz catalogBookBiz;
    private DetailBookBiz detailBookBiz;
    private DetailCatalogBookBiz detailCatalogBookBiz;
    private AccountBiz accountBiz;
    private DiscussBiz discussBiz;
    private CommentBiz commentBiz;
    private BookListBiz bookListBiz;

    private Context context;

    private MRManager(Context context) {
        this.context = context;
    }

    public synchronized static MRManager getInstance(Context context) {
        if (mRManager == null) {
            mRManager = new MRManager(context);
        }
        return mRManager;
    }

    /**
     * 获取活动书籍列表
     */
    public void getActivityBooks(RequestCallBack<List<Book>> callback) {

        if (activityBookBiz == null) {
            activityBookBiz = new ActivityBookBiz(context);
        }
        activityBookBiz.getActivityBooks(callback);
    }

    /**
     * 获取各模块书籍列表
     *
     * @param moduleId 模块id
     * @param from
     * @param limit
     * @param callback
     */
    public void getModuleBooks(String moduleId, int from, int limit, RequestCallBack<List<BookDetail>> callback) {

        if (moduleBookBiz == null) {
            moduleBookBiz = new ModuleBookBiz(context);
        }
        moduleBookBiz.getModuleBooks(moduleId, from, limit, callback);
    }

    /**
     * 获取各模块书籍默认列表
     *
     * @param moduleId 模块id
     * @param callback
     */
    public void getModuleBooks(String moduleId, RequestCallBack<List<BookDetail>> callback) {

        if (moduleBookBiz == null) {
            moduleBookBiz = new ModuleBookBiz(context);
        }
        getModuleBooks(moduleId, 0, 3, callback);
    }


    /**
     * 搜索书籍
     *
     * @param keyWord  关键字
     * @param from     开始位置
     * @param to       结束为止
     * @param callback
     */
    public void getSearchBooks(String keyWord, String categoryId, int from, int to, RequestCallBack<List<BookDetail>> callback) {

        if (searchBookBiz == null) {
            searchBookBiz = new SearchBookBiz(context);
        }
        searchBookBiz.getSearchBooks(new SearchBookBody("", keyWord, categoryId, "", from, to), callback);
    }

    /**
     * 分类书籍
     *
     * @param callback
     */
    public void getCategoryBooks(RequestCallBack<List<CatalogDetail>> callback) {

        if (catalogBookBiz == null) {
            catalogBookBiz = new CatalogBookBiz(context);
        }
        catalogBookBiz.getCatalogBooks(callback);
    }

    /**
     * 分类二级页书籍详情
     *
     * @param callback
     */
    public void getDetailBook(String bookId, RequestCallBack<DetailBookInfo> callback) {

        if (detailBookBiz == null) {
            detailBookBiz = new DetailBookBiz(context);
        }
        detailBookBiz.getDetailBook(bookId, callback);
    }

    /**
     * 二级页书籍目录列表
     *
     * @param callback
     */
    public void getDetailCategoryBooks(String bookId, int from, int maxCountfinal, RequestCallBack<List<DetailCatalogDetail>> callback) {

        if (detailCatalogBookBiz == null) {
            detailCatalogBookBiz = new DetailCatalogBookBiz(context);
        }
        detailCatalogBookBiz.getCatalogBooks(bookId, from, maxCountfinal, callback);
    }

    /**
     * 获取登陆配置
     *
     * @param app 0 支付宝； 1 微信
     */
    public Observable<BaseRequest<WXLandingConfig>> getLandingConfig(int app) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        return accountBiz.getLandingConfig(app);
    }

    /**
     * 获取登陆配置
     */
    public Observable<ZFBLandingConfig> getZFBLandingConfig() {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        return accountBiz.getZFBLandingConfig();
    }

    /**
     * 退出登陆
     */
    public void logOut(RequestCallBack<Boolean> callback) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        accountBiz.logOut(callback);
    }

    /**
     * 获取微信AccessToken
     */
    @Deprecated
    public Observable<WXAccessToken> getAccessToken(WXAccessTokenBody wXAccessTokenBody) {
        return new ThirdBiz(HttpBaseConfig.WX_ACCESS_TOKEN_HOST).getAccessToken(wXAccessTokenBody);
    }

    /**
     * 上传code获取coke
     */
    public Observable<BaseRequest<UserInfo>> uploadCode(LandingCodeBody landingCodeBody) {
        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        return accountBiz.uploadCode(landingCodeBody);
    }

    /**
     * 获取用户信息
     */
    public Observable<BaseRequest<UserInfo>> getUserInfo() {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        return accountBiz.getUserInfo();
    }

    /**
     * 获取账户余额
     */
    public void getAmount(RequestCallBack<AmountInfo> callback) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        accountBiz.getAmount(callback);
    }

    /**
     * 获取微信支付配置
     */
    public Observable<BaseRequest<PayConfig>> getWXPayInfo(PayBody payBody) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        return accountBiz.getWXPayInfo(payBody);
    }

    /**
     * 获取支付宝支付配置
     */
    public Observable<DefaultInfo> getZFBPayInfo(PayBody payBody) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        return accountBiz.getZFBPayInfo(payBody);
    }

    /**
     * 获取已购买书籍
     *
     * @param callback
     */
    public void getBuyBookRecords(int from, int limitfinal, RequestCallBack<List<BookDetail>> callback) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        accountBiz.getBuyBookRecords(from, limitfinal, callback);
    }

    /**
     * 获取用户充值记录
     *
     * @param callback
     */
    public void getChargeRecords(int from, int limitfinal, RequestCallBack<List<ChargeRecords>> callback) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        accountBiz.getChargeRecords(from, limitfinal, callback);
    }

    /**
     * 购买书籍
     *
     * @param bookId      书本id
     * @param chapterNums 章节（以‘；’隔开）
     * @param autoDebits  是否自动购买
     */
    public void buyBook(String bookId, String chapterNums, boolean autoDebits, RequestCallBack<Boolean> callback) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        accountBiz.buyBook(bookId, chapterNums, autoDebits, callback);
    }

    /**
     * 添加到书架
     *
     * @param bookId 书本id
     */
    public void addToSelf(String bookId, RequestCallBack<BookDetail> callback) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        accountBiz.addToSelf(bookId, callback);
    }

    /**
     * 查询书籍是否在书架上
     *
     * @param bookId 书本id
     */
    public void ifInSelf(String bookId, RequestCallBack<DefaultCode> callback) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        accountBiz.ifInSelf(bookId, callback);
    }

    /**
     * 查询书籍是否在书架上
     *
     * @param from  开始位置
     * @param limit 请求数量
     */
    public void getMyShelf(int from, int limit, RequestCallBack<List<ShelfBook>> callback) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        accountBiz.getMyShelf(from, limit, callback);
    }

    /**
     * 获取正文内容
     *
     * @param bookId    id
     * @param chapterNo 章节
     * @param direction 顺序（0：向后阅读，1：向前阅读）
     */
    public void getContent(String bookId, int chapterNo, String direction, RequestCallBack<BaseRequest<BookContent>> callback) {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        accountBiz.getContent(bookId, chapterNo, direction, callback);
    }

    /**
     * 增加讨论
     */
    public Observable<BaseRequest> addDiscuss(DiscussUpload discuss) {

        if (discussBiz == null) {
            discussBiz = new DiscussBiz(context);
        }
        return discussBiz.addDiscuss(discuss);
    }

    /**
     * 获取讨论
     */
    public Observable<BaseRequest<List<Discuss>>> getDiscuss(int type, int from, int maxCount, String userName, int sortType) {

        if (discussBiz == null) {
            discussBiz = new DiscussBiz(context);
        }
        return discussBiz.getDiscuss(type, from, maxCount, userName, sortType);
    }

    /**
     * 获取其他数据
     */
    public Observable<BaseRequest<List<Discuss>>> findData(String softType, int from, int maxCount, String userName, String type) {

        if (discussBiz == null) {
            discussBiz = new DiscussBiz(context);
        }
        return discussBiz.findData(softType, from, maxCount, userName, type);
    }

    /**
     * 获取动态数据
     */
    public Observable<BaseRequest<List<Discuss>>> queryActionData(String userName, int from, int maxCount) {

        if (discussBiz == null) {
            discussBiz = new DiscussBiz(context);
        }
        return discussBiz.queryActionData(userName, from, maxCount);
    }


    /**
     * 搜索书籍
     */
    public Observable<BaseRequest<List<Book>>> queryBook(String userName, String name, String auther) {

        if (discussBiz == null) {
            discussBiz = new DiscussBiz(context);
        }
        return discussBiz.queryBook(userName, name, auther);
    }

    /**
     * 获取收藏/关注列表
     */
    public Observable<BaseRequest<Collection>> queryDataOfUser(String userName, int from, int maxCount) {

        if (discussBiz == null) {
            discussBiz = new DiscussBiz(context);
        }
        return discussBiz.queryDataOfUser(userName, from, maxCount);
    }

    /**
     * 获取评论
     */
    public Observable<BaseRequest<List<Comments>>> getComments(String discussId, int from, int maxCount, String userName) {

        if (commentBiz == null) {
            commentBiz = new CommentBiz(context);
        }
        return commentBiz.getComments(discussId, from, maxCount, userName);
    }

    /**
     * 提交评论
     */
    public Observable<BaseRequest> submitComment(CommentSubmiter submiter) {

        if (commentBiz == null) {
            commentBiz = new CommentBiz(context);
        }
        return commentBiz.submitComment(submiter);
    }

    /**
     * 点赞
     */
    public Observable<BaseRequest<DisRecordResult>> disRecord(DisRecord disRecord) {

        if (commentBiz == null) {
            commentBiz = new CommentBiz(context);
        }
        return commentBiz.disRecord(disRecord);
    }

    /**
     * 收藏/关注
     */
    public Observable<BaseRequest> movementAdd(Movement movement) {

        if (commentBiz == null) {
            commentBiz = new CommentBiz(context);
        }
        return commentBiz.movementAdd(movement);
    }


    /**
     * 获取腾讯云上传配置
     *
     * @return
     */
    public Observable<BaseRequest<TencentYunConfig>> getTencentYunConfig() {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        return accountBiz.getTencentYunConfig();
    }


    /**
     * 获取书籍信息
     *
     * @return
     */
    public Observable<BaseRequest<CommentAndDiscuss>> getBookInfo(String bookName, String bookAuthor, String userName, int from, int maxCount) {

        if (discussBiz == null) {
            discussBiz = new DiscussBiz(context);
        }
        return discussBiz.getBookInfo(bookName, bookAuthor, userName, from, maxCount);
    }


    /**
     * 获取书籍信息
     *
     * @return
     */
    public Observable<BaseRequest<List<BookAndDiscuss>>> querySBook(String userName, String bookName, String bookAuthor) {

        if (discussBiz == null) {
            discussBiz = new DiscussBiz(context);
        }
        return discussBiz.querySBook(userName, bookName, bookAuthor);
    }

    /**
     * 通知阅文充值结果接口
     */
    @Deprecated
    public Observable<RequestCallBack> synYueWenPay(String url) {
        return new ThirdBiz(url).synYueWenPay();
    }

    public Observable<BaseRequest<List<UserInfo>>> queryAttenByUser() {

        if (accountBiz == null) {
            accountBiz = new AccountBiz(context);
        }
        return accountBiz.queryAttenByUser();
    }

    /**
     * @author huzheng
     * @date 2019/6/19
     * @description
     * 添加书单
     */
    public Observable<BaseRequest> addBookList(BookList bookList) {

        if (bookListBiz == null) {
            bookListBiz = new BookListBiz(context);
        }
        return bookListBiz.addBookList(bookList.toMap()).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @author huzheng
     * @date 2019/6/24
     * @description
     * 查询书单列表
     */
    public Observable<BaseRequest<List<BookList>>> queryBookLists(int skip, int maxCount){
        if (bookListBiz == null) {
            bookListBiz = new BookListBiz(context);
        }
        return bookListBiz.queryBookLists(skip, maxCount).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @author huzheng
     * @date 2019/6/24
     * @description
     * 查询某个书单
     */
    public Observable<BaseRequest<List<BookListInfo>>> queryBookList(String listId, int skip, int maxCount){
        if (bookListBiz == null) {
            bookListBiz = new BookListBiz(context);
        }
        return bookListBiz.queryBookList(listId, skip, maxCount).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
