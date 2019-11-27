package com.example.habin.lostpropertyproject.Base;

/**
 * Create by HABIN on 2019/11/4 22:28
 * Email:739115041@qq.com
 * V层和P层基类接口
 */
public interface BaseContract {

    interface BasePresenter<T> {
        //绑定
        void attachView(T view);
        //解绑
        void detachView();
    }

    interface BaseView {

        void onSuccess();

        void onFailure(Throwable e);
    }
}
