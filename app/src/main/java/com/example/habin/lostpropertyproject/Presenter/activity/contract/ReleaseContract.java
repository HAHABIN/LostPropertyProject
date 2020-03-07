package com.example.habin.lostpropertyproject.Presenter.activity.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/2/2013:44
 * Email:739115041@qq.com
 */
public interface ReleaseContract extends BaseContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{
        /**
         * 发布信息
         */
        void InsertArInfo(HashMap<String, Object> hashMap);
        /**
         * 图片上传
         */
        void UploadPhoto(String imgStr);

        /**
         * 修改物品信息
         */
        void UploadArInfo(HashMap<String,Object> hashMap);
    }
}
