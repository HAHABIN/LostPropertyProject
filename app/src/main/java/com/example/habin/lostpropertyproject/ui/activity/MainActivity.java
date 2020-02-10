package com.example.habin.lostpropertyproject.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Widget.PublishDialog;
import com.example.habin.lostpropertyproject.ui.activity.Land.LandActivity;
import com.example.habin.lostpropertyproject.ui.fragment.HomePageFragment;
import com.example.habin.lostpropertyproject.ui.fragment.MessagePageFragment;
import com.example.habin.lostpropertyproject.ui.fragment.MinePageFragment;
import com.example.habin.lostpropertyproject.ui.fragment.TypePageFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String RESULT = "result";

    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void StartAct(Context context, PersonInfoEmtity.ResultBean result) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(RESULT, result);
        context.startActivity(intent);
    }

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
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mImageList = new ImageView[]{mIvHome, mIvType, mIvMessage, mIvMine};
        mTextList = new TextView[]{mTvHome, mTvType, mTvMessage, mTvMine};
        //默认
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, HomePageFragment.newInstance()).commit();
    }


    @OnClick({R.id.rl_home, R.id.rl_type, R.id.btn_add, R.id.rl_message, R.id.rl_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                //登录判断
                if (MyApplication.isLogin(mContext)) {
                    LandActivity.StartAct(mContext);
                    return;
                }
                if (publishDialog == null) {
                    publishDialog = new PublishDialog(MainActivity.this);
                    publishDialog.setLostClickListener(v -> {
                        ReleaseActivity.StartAct(mContext, "2");
                        publishDialog.outDia();
                    });
                    publishDialog.setFindClickListener(v -> {
                        ReleaseActivity.StartAct(mContext, "1");
                        publishDialog.outDia();
                    });
                    publishDialog.setPingguClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LandActivity.StartAct(mContext);
                        }
                    });
                }
                publishDialog.show();
                break;
            case R.id.rl_home:
                mIsCheck = 0;
                fragment = HomePageFragment.newInstance();//首页
                setStaus();
                break;
            case R.id.rl_type:
                mIsCheck = 1;
                fragment = TypePageFragment.newInstance();//分类
                setStaus();
                break;
            case R.id.rl_message:
                fragment = MessagePageFragment.newInstance();//消息
                mIsCheck = 2;
                setStaus();
                break;
            case R.id.rl_mine:
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
