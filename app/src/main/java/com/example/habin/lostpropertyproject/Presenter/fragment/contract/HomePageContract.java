package com.example.habin.lostpropertyproject.Presenter.fragment.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;

/**
 * created by habin
 * on 2019/12/30
 * 首页碎片Presenter逻辑接口 */
public interface HomePageContract {

    interface View extends BaseContract.BaseView{
        //
        void Sucess(BaseResponse baseResponse);
        void Fail(String errMsg);
    }

    interface Presenter extends BaseContract.BasePresenter<HomePageContract.View>{
        /**
         * 获取数据
         */
        void getData(String username, String password);

    }

}
