package com.example.habin.lostpropertyproject.Bean.entity;

import com.example.habin.lostpropertyproject.Bean.HttpItem;

import java.io.Serializable;
import java.util.Date;

/**
 * Create by HABIN on 2020/3/191:34
 * Email:739115041@qq.com
 */
public class CommentEntity implements Serializable {

    private int cId;

    private int userId;

    private String nickName;

    private String userImg;

    private String content;

    private long createTime;

    private int articleId;

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
