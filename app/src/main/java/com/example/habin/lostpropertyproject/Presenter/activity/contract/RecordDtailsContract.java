package com.example.habin.lostpropertyproject.Presenter.activity.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

/**
 * Create by HABIN on 2020/3/622:08
 * Email:739115041@qq.com
 */
public interface RecordDtailsContract extends BaseContract {
    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{
        void updateArticle(int id,int recordStatus);
        void addComment(int ArticleId,String Content);
    }
}
