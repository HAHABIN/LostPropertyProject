package com.example.habin.lostpropertyproject.Presenter.fragment.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

/**
 * Create by HABIN on 2020/2/2312:04
 * Email:739115041@qq.com
 */
public interface ToClaimListContract extends BaseContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{
        void QueryArticleInfo(String address,int status,int pageNo,int pageSize);
    }

}
