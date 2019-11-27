package com.example.habin.lostpropertyproject.Model;

/**
 * Create by HABIN on 2019/11/616:18
 * Email:739115041@qq.com
 */

import com.example.habin.lostpropertyproject.Error.ExceptionHandle;

import io.reactivex.disposables.Disposable;



public abstract class Observer<T> implements io.reactivex.Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        //添加订阅关系
        OnDisposable(d);
    }

    @Override
    public void onNext(T t) {
        OnSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        //自定义异常的传递
        OnFail(ExceptionHandle.handleException(e));
    }

    @Override
    public void onComplete() {
        OnCompleted();
    }

    public abstract void OnSuccess(T t);

    public abstract void OnFail(ExceptionHandle.ResponeThrowable e);

    public abstract void OnCompleted();

    public abstract void OnDisposable(Disposable d);
}
