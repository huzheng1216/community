package com.flyersoft.discuss.http.biz;

import android.content.Context;

import com.flyersoft.discuss.http.base.BaseBiz;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.DisRecord;
import com.flyersoft.discuss.javabean.DisRecordResult;
import com.flyersoft.discuss.javabean.Movement;
import com.flyersoft.discuss.javabean.seekbook.CommentSubmiter;
import com.flyersoft.discuss.javabean.seekbook.Comments;

import java.util.List;

import io.reactivex.Observable;


/**
 * 处理评论相关业务
 * Created by 37399 on 2016/10/23.
 */
public class CommentBiz extends BaseBiz {

    public CommentBiz(Context context) {
        super(context);
    }

    /**
     * 获取评论
     */
    public Observable<BaseRequest<List<Comments>>> getComments(String discussId, int from, int maxCount, String userName) {

        return mMRRequestService.getComments(discussId, from, maxCount, userName);
    }

    /**
     * 提交评论
     */
    public Observable<BaseRequest> submitComment(CommentSubmiter commentSubmiter) {

        return mMRRequestService.submitComment(commentSubmiter.toMap());
    }

    /**
     * 点赞/有用
     * @param disRecord
     * @return
     */
    public Observable<BaseRequest<DisRecordResult>> disRecord(DisRecord disRecord) {
        return mMRRequestService.disRecord(disRecord.toMap());
    }

    /**
     * 关注/收藏
     * @param movement
     * @return
     */
    public Observable<BaseRequest> movementAdd(Movement movement) {
        return mMRRequestService.movementAdd(movement.toMap());
    }
}
