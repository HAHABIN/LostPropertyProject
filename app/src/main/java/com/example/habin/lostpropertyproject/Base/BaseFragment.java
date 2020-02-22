package com.example.habin.lostpropertyproject.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luck.picture.lib.tools.DoubleUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 继承BaseActivity基类
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG;
    protected Activity mActivity;
    private Unbinder mUnBinder;
    private View mRoot;


    @LayoutRes
    protected abstract int getLayoutId();


    /**
     * 逻辑使用区
     */
    protected void processLogic(){
    }

    protected abstract void initView(View view);

    protected abstract void initListener();

    protected abstract void initData();




    /******************************lifecycle area*****************************************/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null) {
                parent.removeView(mRoot);
            }
            return mRoot;
        }
        mRoot = inflater.from(getActivity()).inflate(getLayoutId(),container,false);
        mUnBinder = ButterKnife.bind(this, mRoot);
        TAG=getName();
        //绑定Presenter
        processLogic();
        initView(mRoot);
        initListener();
        initData();

        return mRoot;
    }



    protected void startActivity(Class clz, Bundle bundle) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(mActivity, clz);
            if (bundle!=null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    protected void startActivity(Class clz, Bundle bundle, int requestCode) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(mActivity, clz);
            if (bundle!=null) {
                intent.putExtras(bundle);
            }
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }

    /**************************公共类*******************************************/
    public String getName(){
        return getClass().getName();
    }

    protected <VT> VT getViewById(int id){
        if (mRoot == null){
            return  null;
        }
        return (VT) mRoot.findViewById(id);
    }
}
