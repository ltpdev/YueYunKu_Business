package com.gdcp.yueyunku_business.model;

import android.text.GetChars;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus- on 2017/10/13.
 */

public class SpaceComment extends BmobObject{
    private String content;
    //评论的具体的场地
    private Space space;
    /*评论者*/
    private User commenter;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public Space getSpace() {
        return space;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public User getCommenter() {
        return commenter;
    }
}
