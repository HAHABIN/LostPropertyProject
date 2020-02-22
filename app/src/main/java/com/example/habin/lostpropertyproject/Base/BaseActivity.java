package com.example.habin.lostpropertyproject.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ActivityManagerUtils;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.luck.picture.lib.tools.DoubleUtils;

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
    private RelativeLayout mainLayout;

    private RelativeLayout llRoot;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvsubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
        mContext = this;
        //设置状态栏文字颜色
        StatusBarUtils.setLightStatusBar(mActivity,true);
        //添加
        ActivityManagerUtils.mActivities.add(this);
        initMainLayout();
        //View注入
        mUnBinder = ButterKnife.bind(this);
        processLogic();
        initParam();
        initView();
        initListener();
        initData();

    }

    private void initMainLayout() {

        //是否显示标题栏
        if (!showTitle()) {
            setContentView(getLayoutId());
        } else {
            //添加基类布局
            mainLayout = (RelativeLayout) View.inflate(this, R.layout.activity_base, null);
            //获得布局
            int contentViewId = getLayoutId();
            //设置基类布局
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            //表示 RelativeLayout 中的相应节点放置在一个 id 值为 ll_basetitle 的兄弟节点的下面。
            lp.addRule(RelativeLayout.BELOW, R.id.ll_basetitle);
            if (contentViewId != 0) {
                mainLayout.addView(View.inflate(this, getLayoutId(), null), lp);
            }
            setContentView(mainLayout);
            //标题空间
            initTitleView();
        }

    }

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化参数,包括intent传递过来的参数
     */
    protected void initParam(){}

    /**
     * 初始化view,包括初始化些控件
     */
    protected abstract void initView();

    /**
     * 初始化监听器,各种listener事件
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 执行逻辑
     */
    protected void processLogic() {
    }


    /**
     * -------------------标题初始化---------------------------
     */
    //是否显示标题栏
    protected abstract boolean showTitle();

    //初始化标题控件
    private void initTitleView() {
        llRoot = findViewById(R.id.rl_basetitle_root);
        mIvBack = findViewById(R.id.iv_back);
        mTvsubmit = findViewById(R.id.tv_submit);
        mTvTitle = findViewById(R.id.tv_title);
    }

    public void setTitleText(String titleText){
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(titleText);
    }
    public void setShowBack(int visible) {
        if (mIvBack != null) {
            mIvBack.setVisibility(visible);
        }
    }
    public void setRightText(String rightText) {
        mTvsubmit.setVisibility(View.VISIBLE);
        mTvsubmit.setText(rightText);
    }

    /**
     * 使用方法 setBackOnClick().setOnClickListener(this);
     */
    //标题绑定监听
    public TextView setTitleOnClick() {
        return mTvTitle;
    }

    //返回监听
    public ImageView setBackOnClick() {
        return mIvBack;
    }

    //发布监听
    public TextView setRightOnClick() {
        return mTvsubmit;
    }

    /**-----------------------------------------------------*/




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        ActivityManagerUtils.mActivities.remove(this);
        Log.d(TAG, "onDestroy: " + TAG);
    }



    public void startActivity(Class clz, Bundle bundle) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, clz);
            if (bundle!=null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    public void startActivity(Class clz, Bundle bundle, int requestCode) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, clz);
            if (bundle!=null) {
                intent.putExtras(bundle);
            }
            startActivityForResult(intent, requestCode);
        }
    }
}
