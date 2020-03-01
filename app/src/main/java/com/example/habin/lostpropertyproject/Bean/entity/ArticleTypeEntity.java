package com.example.habin.lostpropertyproject.Bean.entity;

import android.support.annotation.NonNull;

import com.contrarywind.interfaces.IPickerViewData;
import com.example.habin.lostpropertyproject.Bean.HttpItem;

import java.io.Serializable;
import java.util.List;

/**
 * @author HABIN
 * @date 2020/2/15 23:20
 * 物品类型实体类
 * 实体层 数据库在项目中的类
 */
public class ArticleTypeEntity extends HttpItem {


    private List<ArticleTypeEntity.ResultBean> result;

    public List<ArticleTypeEntity.ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ArticleTypeEntity.ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable,IPickerViewData {
        //物品类型id
        private int typeId;

        //物品类型名
        private String typeName;

        //类型级别
        private int priority;

        //创建时间
        private long createTime;

        //最后修改时间
        private long lastEditTime;

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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getLastEditTime() {
            return lastEditTime;
        }

        public void setLastEditTime(long lastEditTime) {
            this.lastEditTime = lastEditTime;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "typeId=" + typeId +
                    ", typeName='" + typeName + '\'' +
                    ", priority=" + priority +
                    ", createTime=" + createTime +
                    ", lastEditTime=" + lastEditTime +
                    '}';
        }

        @Override
        public String getPickerViewText() {
            return this.typeName;
        }
    }

    @Override
    public String toString() {
        return "ArticleTypeEntity{" +
                "result=" + result +
                '}';
    }
}
