package com.example.habin.lostpropertyproject.Http;

import com.example.habin.lostpropertyproject.Base.BaseObserver;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//创建实现接口来方便调用
public class HttpEngine {

    private static Apiservice apiservice =RetrofitManager.getSingleton().Apiservice();


    public void login(String username, String password, BaseObserver<BaseResponse> baseObserver) {
        setSubscribe(apiservice.login(username,password),baseObserver);
    }


    private static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
