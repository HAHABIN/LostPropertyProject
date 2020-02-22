package com.example.habin.lostpropertyproject.Base;

import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.TaskListener;
import com.example.habin.lostpropertyproject.Util.ToastUtils;

import org.json.JSONObject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Create by HABIN on 2019/11/4
 * Time：23:59
 * Email:739115041@qq.com
 * Rxjava 订阅关系
 * 基于Rx的Presenter封装,控制订阅的生命周期
 * unsubscribe() 这个方法很重要，
 * 因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 * 这个引用如果不能及时被释放，将有内存泄露的风险。
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> , TaskListener{

    // Presenter持有的View
    protected T mView;
    /**
     * 1、可以快速解除所有添加的Disposable类.
     * 2、每当我们得到一个Disposable时就调用CompositeDisposable.add()将它添加到容器中,
     * 在退出的时候, 调用CompositeDisposable.clear() 即可快速解除.
     *
     */
    protected CompositeDisposable mDisposable;

    /**
     * 构造函数 new CompositeDisposable对象
     */
    protected void unSubscribe() {

        if (mDisposable != null) {
            // dispose():主动解除订阅
            mDisposable.dispose();
        }
    }

    protected void addDisposable(Disposable subscription) {

        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        //订阅
        mDisposable.add(subscription);
    }

    protected void cancelDisposable(Disposable subscription) {
        if (mDisposable != null) {
            mDisposable.delete(subscription);
        }
    }

    protected void cancelAllDisposable() {
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    @Override
    public void taskStarted(HttpHelper.TaskType type) {

    }

    @Override
    public void taskError(HttpHelper.TaskType type, ApiError error) {
        ToastUtils.show_s(error.getMessage());
        mView.onFailure(type,error);
    }

    @Override
    public void taskFinished(HttpHelper.TaskType type, JSONObject object) {
        mView.onSuccess(type,object);
    }

    @Override
    public void taskFinished(HttpHelper.TaskType type, HttpItem item) {
        mView.onSuccess(type,item);
    }
}
