package com.example.habin.lostpropertyproject.Model.Impl;



import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Base.BaseObserver;
import com.example.habin.lostpropertyproject.Model.ILandModel;
import com.example.habin.lostpropertyproject.Service.Retrofit.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Create by HABIN on 2019/11/4
 * Time：23:08
 * Email:739115041@qq.com
 * 登录、注册 数据逻辑层
 *
 * 业务逻辑的处理，实现了观察者中的方法，
 * 将其中请求的结果传递到抽象方法中，方便其他类的实现。（注意这里异常的传递和订阅关系的添加）
 */
public class LandModelImpl implements ILandModel {


//    public void getxinxi(String fenzhi, String bianhao, String shijian, BaseObserver<List<BeanGSchaxun>> observer) {
//        Observable<List<BeanGSchaxun>> gSxin = RetrofitManager.getSingleton().Apiservice().getGSxin(fenzhi, bianhao, shijian);
//        gSxin.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
//
//    }


    @Override
    public void login(String username, String password, BaseObserver<BaseResponse> baseObserver) {
        Observable<BaseResponse> login = RetrofitManager.getSingleton().Apiservice().login(username,password);
        login.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(baseObserver);
    }

    @Override
    public void signup(String username, String password, String mail) {

    }
}
