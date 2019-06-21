package com.flyersoft.discuss.javabean;

import com.flyersoft.discuss.javabean.seekbook.Comments;
import com.flyersoft.discuss.javabean.seekbook.Discuss;

import java.util.List;

/**
 * Created by huzheng on 2018/1/7.
 */

public class CommentAndDiscuss {

    private List<Comments> comment;
    private List<Discuss> discuss;

    public List<Comments> getComment() {
        return comment;
    }

    public void setComment(List<Comments> comment) {
        this.comment = comment;
    }

    public List<Discuss> getDiscuss() {
        return discuss;
    }

    public void setDiscuss(List<Discuss> discuss) {
        this.discuss = discuss;
    }
}
