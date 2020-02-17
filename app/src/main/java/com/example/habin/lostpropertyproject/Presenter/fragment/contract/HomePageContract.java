package com.example.habin.lostpropertyproject.Presenter.fragment.contract;

import com.example.habin.lostpropertyproject.Base.BaseContract;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;

/**
 * created by habin
 * on 2019/12/30
 * 首页碎片Presenter逻辑接口 */
public interface HomePageContract extends BaseContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{
        /**
         * 获取数据
         */
        void getData(String username, String password);

        /**
         * 获取省级列表
         *
         */
        void getProviceList();
        /**
         * 获取市级列表
         * @param pid 区域上级id

         */
        void getCityList(int pid);

        /**
         * 获取区县级列表
         * @param pid 区域上级id
         *
         */
        void getCountyList(int pid);

    }

}
