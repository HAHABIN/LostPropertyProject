package com.example.habin.lostpropertyproject.Presenter.activity.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

/**
 * Create by HABIN on 2020/2/2219:42
 * Email:739115041@qq.com
 */
public interface EditGenderContract extends BaseContract{

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 用户信息更新
         */
        void updateGender(String gender);
    }
}
