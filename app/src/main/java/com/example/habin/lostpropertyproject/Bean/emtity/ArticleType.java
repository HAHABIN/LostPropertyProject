package com.example.habin.lostpropertyproject.Bean.emtity;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HABIN
 * @date 2020/2/15 23:20
 * 物品类型实体类
 * 实体层 数据库在项目中的类
 */
public class ArticleType implements IPickerViewData {

    //物品类型id
    private int typeId;

    //物品类型名
    private String typeName;

    //类型级别
    private int priority;

//    //创建时间
//    private Date createTime;
//
//    //最后修改时间
//    private Date lastEditTime;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getLastEditTime() {
//        return lastEditTime;
//    }
//
//    public void setLastEditTime(Date lastEditTime) {
//        this.lastEditTime = lastEditTime;
//    }

    @Override
    public String toString() {
        return "PropertyType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", priority=" + priority +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return this.typeName;
    }
}
