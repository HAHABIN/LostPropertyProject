package com.example.habin.lostpropertyproject.Presenter.fragment.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 分类碎片逻辑接口
 */
public interface TypePageContract extends BaseContract{

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{
        /**
         * 获取数据
         */
        void getData(String username, String password);

    }
}
