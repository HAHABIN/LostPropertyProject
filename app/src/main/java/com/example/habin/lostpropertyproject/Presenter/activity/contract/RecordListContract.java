package com.example.habin.lostpropertyproject.Presenter.activity.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

/**
 * Create by HABIN on 2020/2/2218:47
 * Email:739115041@qq.com
 */
public interface RecordListContract extends BaseContract {


    interface View extends BaseContract.BaseView{

    }

    interface Presenter extends BasePresenter<View>{
        void QueryArticleInfo(int userId,int recordStatus);
    }
}
