package com.example.habin.lostpropertyproject;

import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Base.BaseObserver;
import com.example.habin.lostpropertyproject.Model.ILandModel;
import com.example.habin.lostpropertyproject.Service.Retrofit.RetrofitManager;

import io.reactivex.Observable;

/**
 * Create by HABIN on 2019/11/915:52
 * Email:739115041@qq.com
 */
public class landDao implements ILandModel {
    @Override
    public void login(String username, String password, BaseObserver<BaseResponse> baseObserver) {
        Observable<BaseResponse> login = RetrofitManager.getSingleton().Apiservice().login(username,password);
        login.subscribe(baseObserver);

    }

    @Override
    public void signup(String username, String password, String mail) {

    }
}
