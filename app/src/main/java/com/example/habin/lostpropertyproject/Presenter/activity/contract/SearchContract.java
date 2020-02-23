package com.example.habin.lostpropertyproject.Presenter.activity.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

/**
 * Create by HABIN on 2020/2/2312:45
 * Email:739115041@qq.com
 */
public interface SearchContract extends BaseContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        void SearchInfo(String description,int pageNo,int pageSize);
    }

}
