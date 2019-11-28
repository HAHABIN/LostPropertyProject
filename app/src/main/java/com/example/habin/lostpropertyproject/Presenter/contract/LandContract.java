package com.example.habin.lostpropertyproject.Presenter.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;

/**
 * Create by HABIN on 2019/11/4
 * Time：22:48
 * Email:739115041@qq.com
 * 登录，注册 契约类，
 * 集成了View层的ui更新接口以及Presenter层调用逻辑接口。
 */
public interface LandContract extends BaseContract {



    interface View extends BaseContract.BaseView{
        //
        void landSucess(BaseResponse baseResponse);
        void landFail(String errMsg);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 用户登陆
         */
        void login(String username, String password);

        /**
         * 用户注册
         */
        void signup(String username, String password, String mail);
    }



}
