package com.example.habin.lostpropertyproject.Ui.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Widget.PublishDialog;
import com.example.habin.lostpropertyproject.Ui.activity.Land.LandActivity;
import com.example.habin.lostpropertyproject.Ui.fragment.HomePageFragment;
import com.example.habin.lostpropertyproject.Ui.fragment.MessagePageFragment;
import com.example.habin.lostpropertyproject.Ui.fragment.MinePageFragment;
import com.example.habin.lostpropertyproject.Ui.fragment.TypePageFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_home)
    ImageView mIvHome;
    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.iv_type)
    ImageView mIvType;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.iv_mine)
    ImageView mIvMine;
    @BindView(R.id.tv_mine)
    TextView mTvMine;
    //底栏ImageView控件集合
    private ImageView[] mImageList;
    //底栏TextView控件集合
    private TextView[] mTextList;
    //选中位置
    private int mIsCheck = 0;
    //图片默认样式集合
    private int[] seIds = {R.mipmap.ic_home_normal, R.mipmap.ic_type_normal, R.mipmap.ic_message_normal, R.mipmap.ic_mine_normal};
    //图片选中样式集合
    private int[] noseIds = {R.mipmap.ic_home_selected, R.mipmap.ic_type_selected, R.mipmap.ic_message_selected, R.mipmap.ic_mine_select};
    //当前fragment
    private Fragment fragment;
    //发布动画
    private PublishDialog publishDialog;


    //是否显示标题 默认不显示
    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mImageList = new ImageView[]{mIvHome, mIvType, mIvMessage, mIvMine};
        mTextList = new TextView[]{mTvHome, mTvType, mTvMessage, mTvMine};
        //默认
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, HomePageFragment.newInstance()).commit();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.rl_home, R.id.rl_type, R.id.btn_add, R.id.rl_message, R.id.rl_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                //登录判断
                if (MyApplication.isLogin(mContext)) {
                    startActivity(LandActivity.class,null);
                    return;
                }
                if (publishDialog == null) {
                    publishDialog = new PublishDialog(MainActivity.this);
                    publishDialog.setLostClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.RELEASE_TYPE,"2");
                        startActivity(ReleaseActivity.class,bundle);
                        publishDialog.outDia();
                    });
                    publishDialog.setFindClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.RELEASE_TYPE,"1");
                        startActivity(ReleaseActivity.class,bundle);
                        publishDialog.outDia();
                    });

                }
                publishDialog.show();
                break;
            case R.id.rl_home:
                //如果是当前位置 不刷新
                if (mIsCheck==0){
                    return;
                }
                mIsCheck = 0;
                fragment = HomePageFragment.newInstance();//首页
                setStaus();
                break;
            case R.id.rl_type:
                if (mIsCheck==1){
                    return;
                }
                mIsCheck = 1;
                fragment = TypePageFragment.newInstance();//分类
                setStaus();
                break;
            case R.id.rl_message:
                if (mIsCheck==2){
                    return;
                }
                fragment = MessagePageFragment.newInstance();//消息
                mIsCheck = 2;
                setStaus();
                break;
            case R.id.rl_mine:
                if (mIsCheck==3){
                    return;
                }
                //登录判断
                if (MyApplication.isLogin(mContext)) {
                    LandActivity.StartAct(mContext);
                    return;
                }
                fragment = MinePageFragment.newInstance();//我的
                mIsCheck = 3;
                setStaus();
                break;
        }

    }

    //修改状态
    public void setStaus() {
        for (int i = 0; i < mImageList.length; i++) {
            ImageView imageView = mImageList[i];
            TextView textView = mTextList[i];
            //当前图片选中，并且其他设置为默认样式
            imageView.setBackgroundResource(seIds[i]);
            textView.setTextColor(Color.parseColor("#515151"));
            if (i == mIsCheck) {
                imageView.setBackgroundResource(noseIds[i]);
                textView.setTextColor(Color.parseColor("#386FFE"));
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commit();
    }


}
