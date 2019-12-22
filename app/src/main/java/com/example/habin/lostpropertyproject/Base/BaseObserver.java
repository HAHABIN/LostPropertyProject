package com.example.habin.lostpropertyproject.Base;

/**
 * Create by HABIN on 2019/11/616:18
 * Email:739115041@qq.com
 */


import android.accounts.NetworkErrorException;



import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;



public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        //添加订阅关系
        OnDisposable(d);
    }

    @Override
    public void onNext(T t) {

        try {
            OnSuccess(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        //自定义异常的传递

        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                OnFail(e);
            } else {
                OnFail(e);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
        OnCompleted();
    }

    protected abstract void OnSuccess(T t);

    protected abstract void OnFail(Throwable e);

    protected void OnCompleted() {

    }

    protected void OnDisposable(Disposable d) {

    }
}
