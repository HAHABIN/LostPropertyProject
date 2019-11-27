package com.example.habin.lostpropertyproject.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.habin.lostpropertyproject.Util.ActivityManagerUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by HABIN on 2019/11/4
 * Time：22:38
 * Email:739115041@qq.com
 * Activity基类
 */
//继承AppCompatActivity主要是为了兼容低版本的一些问题；
public abstract class BaseActivity extends AppCompatActivity {

    protected static String TAG;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mActivity = this;
        mContext = this;
        //添加
        ActivityManagerUtils.mActivities.add(this);
        // 设置 TAG
        TAG = this.getClass().getSimpleName();
        Log.d(TAG, "onCreate: "+TAG);
        //View注入
        mUnBinder = ButterKnife.bind(this);
        //init
        initData(savedInstanceState);
//        initToolbar();
        initWidget();
        initEvent();
        initClick();
        processLogic();
    }

    @LayoutRes
    protected abstract int getLayoutId();


    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {
    }

    /**
     * 初始化零件
     */
    protected void initWidget() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    /**
     * 初始化点击事件
     */
    protected void initClick() {
    }

    /**
     * 执行逻辑
     */
    protected void processLogic() {
    }

    protected void beforeDestroy() {
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        ActivityManagerUtils.mActivities.remove(this);
        Log.d(TAG, "onDestroy: "+TAG);
    }
}
