package com.example.habin.lostpropertyproject.Presenter.activity.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

/**
 * Create by HABIN on 2020/2/2220:02
 * Email:739115041@qq.com
 */
public interface EditPasswordContract extends BaseContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{
        void UpdatePasswordAuth(String username,String password,String newpassword);
    }
}
