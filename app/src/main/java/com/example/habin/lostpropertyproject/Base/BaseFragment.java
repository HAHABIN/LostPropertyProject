package com.example.habin.lostpropertyproject.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 继承BaseActivity基类
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG;

    protected CompositeDisposable mDisposable;

    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    private View mRoot = null;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;

        super.onAttach(context);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    /*******************************init area*********************************/
    protected void addDisposable(Disposable d){
        if (mDisposable == null){
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }


    protected void initData(Bundle savedInstanceState){
    }

    /**
     * 初始化点击事件
     */
    protected void initClick(){
    }

    /**
     * 逻辑使用区
     */
    protected void processLogic(){
    }

    /**
     * 初始化零件
     */
    protected void initWidget(Bundle savedInstanceState){
    }

    protected void beforeDestroy(){

    }

    /******************************lifecycle area*****************************************/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = getLayoutId();
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null) {
                parent.removeView(mRoot);
            }
            return mRoot;
        }
        mRoot = LayoutInflater.from(getActivity()).inflate(getLayoutId(),container,false);
//        mRoot = inflater.inflate(resId,container,false);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        initData(savedInstanceState);
        TAG=getName();
        initWidget(savedInstanceState);
        initClick();
        processLogic();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        beforeDestroy();
        if (mDisposable != null){
            mDisposable.clear();
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
