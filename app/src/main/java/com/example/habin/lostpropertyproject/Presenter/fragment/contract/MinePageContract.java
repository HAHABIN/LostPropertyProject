package com.example.habin.lostpropertyproject.Presenter.fragment.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 我的碎片逻辑接口
 */
public interface MinePageContract {
    interface View extends BaseContract.BaseView{

    }

    interface Presenter extends BaseContract.BasePresenter<MinePageContract.View>{
        /**
         * 获取数据
         */
        void getData(String username, String password);

    }
}
