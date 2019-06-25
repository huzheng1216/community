package com.flyersoft.discuss.http.biz;

import android.content.Context;

import com.flyersoft.discuss.http.base.BaseBiz;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.DisRecord;
import com.flyersoft.discuss.javabean.DisRecordResult;
import com.flyersoft.discuss.javabean.Movement;
import com.flyersoft.discuss.javabean.seekbook.BookList;
import com.flyersoft.discuss.javabean.seekbook.BookListInfo;
import com.flyersoft.discuss.javabean.seekbook.CommentSubmiter;
import com.flyersoft.discuss.javabean.seekbook.Comments;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * 书单相关业务
 * Created by zheng.hu on 2019/06/13.
 */
public class BookListBiz extends BaseBiz {

    public BookListBiz(Context context) {
        super(context);
    }

    /**
     * 创建书单
     */
    public Observable<BaseRequest> addBookList(Map<String, Object> map) {

        return mMRRequestService.addBookList(map);
    }

    /**
     * @author huzheng
     * @date 2019/6/19
     * @description
     * 查询书单列表
     */
    public Observable<BaseRequest<List<BookList>>> queryBookLists(int skip, int maxCount) {
        return mMRRequestService.queryBookLists(skip, maxCount);
    }

    /**
     * @author huzheng
     * @date 2019/6/24
     * @description
     * 查询某个书单
     */
    public Observable<BaseRequest<List<BookListInfo>>> queryBookList(String listId, int skip, int maxCount) {
        return mMRRequestService.queryBookList(listId, skip, maxCount);
    }
}
