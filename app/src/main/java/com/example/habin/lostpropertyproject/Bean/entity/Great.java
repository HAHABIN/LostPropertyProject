package com.example.habin.lostpropertyproject.Bean.entity;

import java.io.Serializable;

/**
 * @author HABIN
 * @date 2020/3/18 23:26
 * 点赞表实体类
 */
public class Great implements Serializable {

    //id
    private int gId;
    //评论id
    private int acId;
    //用户id
    private int userId;


    public int getgId() {
        return gId;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public int getAcId() {
        return acId;
    }

    public void setAcId(int acId) {
        this.acId = acId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
