package com.example.habin.lostpropertyproject.Presenter.activity.mine.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

/**
 * Create by HABIN on 2020/2/1322:00
 * Email:739115041@qq.com
 * 用户信息契约类
 *
 */
public interface UserInfoContract {

    interface View extends BaseContract.BaseView{

    }

    interface Presenter extends BaseContract.BasePresenter<UserInfoContract.View>{
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
