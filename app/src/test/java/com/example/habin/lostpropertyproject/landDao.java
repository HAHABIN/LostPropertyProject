package com.example.habin.lostpropertyproject;

import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Model.ILandModel;
import com.example.habin.lostpropertyproject.Model.Observer;
import com.example.habin.lostpropertyproject.Presenter.contract.LandContract;
import com.example.habin.lostpropertyproject.Retrofit.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by HABIN on 2019/11/915:52
 * Email:739115041@qq.com
 */
public class landDao implements ILandModel {
    @Override
    public void login(String username, String password, Observer<BaseResponse> observer) {
        Observable<BaseResponse> login = RetrofitManager.getSingleton().Apiservice().login(username,password);
        login.subscribe(observer);

    }

    @Override
    public void signup(String username, String password, String mail) {

    }
}
