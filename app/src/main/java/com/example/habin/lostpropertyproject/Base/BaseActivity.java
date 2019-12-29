package com.example.habin.lostpropertyproject.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ActivityManagerUtils;

import butterknife.BindView;
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
    private ImageView mIvSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(getLayoutId());
        mActivity = this;
        mContext = this;
        //添加
        ActivityManagerUtils.mActivities.add(this);
        initMainLayout();
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

    private void initMainLayout() {
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
        //是否显示标题栏
        if (!showTitle()) {
            setContentView(getLayoutId());
        } else {
            setContentView(mainLayout);
            //标题空间
            initTitleView();
        }

    }


    @LayoutRes
    protected abstract int getLayoutId();

    /**-------------------标题初始化---------------------------*/
    //是否显示标题栏
    protected abstract boolean showTitle();

    //初始化标题控件
    private void initTitleView() {
        llRoot =  findViewById(R.id.rl_basetitle_root);
        mIvBack = findViewById(R.id.iv_back);
        mTvTitle =  findViewById(R.id.tv_title);
        mIvSearch =  findViewById(R.id.iv_search);
    }

    /**
     *
     * 设置中间标题文字
     * @param c
     */
    public void setTitleText(CharSequence c) {
        if (mTvTitle != null)
            mTvTitle.setText(c);
    }
    /**
     *
     * 设置中间标题文字
     * @param resId
     */
    public void setTitleText(int resId) {
        if (mTvTitle != null)
            mTvTitle.setText(resId);
    }

    public void setShowBack(int visible){
        if (mIvBack!=null) {
            mIvBack.setVisibility(visible);
        }
    }
    public void setShowSearch(int visible){
        if (mIvSearch!=null){
            mIvSearch.setVisibility(visible);
        }
    }
    /**使用方法 setBackOnClick().setOnClickListener(this);*/
    //标题绑定监听
    public TextView setTitleOnClick() {
        return mTvTitle;
    }
    //返回监听
    public ImageView setBackOnClick() {
        return mIvBack;
    }
    //搜索监听
    public ImageView setEOnClick() {
        return mIvSearch;
    }

    /**-----------------------------------------------------*/

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        ActivityManagerUtils.mActivities.remove(this);
        Log.d(TAG, "onDestroy: " + TAG);
    }
}
