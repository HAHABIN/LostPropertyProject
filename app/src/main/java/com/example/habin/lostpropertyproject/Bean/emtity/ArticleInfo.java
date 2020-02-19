package com.example.habin.lostpropertyproject.Bean.emtity;

import java.util.Date;

/**
 * @author HABIN
 * @date 2020/2/18 15:21
 * 物品信息
 */
public class ArticleInfo {

    //物品id
    private int id;
    //物品类型
    private int type;
    //物品名称
    private String name;
    //发布者id
    private int userId;
    //拾物时间或者丢失时间
    private Date findTime;
    //物品详细地址
    private String addressContent;
    //物品描述
    private String description;
    //物品属性 1为失物 2为拾物a
    private int status;
    //物品照片
    private String imgStr;
    //记录状态1为丢失记录 2为拾物记录 3完成记录 4取消记录
    private int record_status;
    //帮助者id
    private int helperId;
    //完成时间
    private Date backTime;
    //发布时间
    private Date createTime;
    //最后修改信息时间
    private Date lastEditTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getFindTime() {
        return findTime;
    }

    public void setFindTime(Date findTime) {
        this.findTime = findTime;
    }

    public String getAddressContent() {
        return addressContent;
    }

    public void setAddressContent(String addressContent) {
        this.addressContent = addressContent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public int getRecord_status() {
        return record_status;
    }

    public void setRecord_status(int record_status) {
        this.record_status = record_status;
    }

    public int getHelperId() {
        return helperId;
    }

    public void setHelperId(int helperId) {
        this.helperId = helperId;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
