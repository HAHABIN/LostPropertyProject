package com.example.habin.lostpropertyproject.Base;

/**
 * Create by HABIN on 2019/11/4
 * Time：22:59
 * Email:739115041@qq.com
 * 继承BaseActivity基类
 */
public abstract class BaseMVPActivity<T extends BaseContract.BasePresenter> extends BaseActivity {

    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void processLogic() {
        attachView(bindPresenter());
    }

    private void attachView(T presenter){
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
