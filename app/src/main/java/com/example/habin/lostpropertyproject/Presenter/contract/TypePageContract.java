package com.example.habin.lostpropertyproject.Presenter.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 分类碎片逻辑接口
 */
public interface TypePageContract {

    interface View extends BaseContract.BaseView{
        //
        void Sucess(BaseResponse baseResponse);
        void Fail(String errMsg);
    }

    interface Presenter extends BaseContract.BasePresenter<TypePageContract.View>{
        /**
         * 获取数据
         */
        void getData(String username, String password);

    }
}
