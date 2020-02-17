package com.example.habin.lostpropertyproject.Presenter.activity.mine.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

/**
 * Create by HABIN on 2020/2/1322:00
 * Email:739115041@qq.com
 * 用户信息契约类
 *
 */
public interface UserInfoContract extends BaseContract{

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{
        /**
         * 图片上传
         */
        void UploadPhoto(String imgStr);

        /**
         * 用户信息更新
         */
        void updateInfo(String str);
    }

}
