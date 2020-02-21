package com.example.habin.lostpropertyproject.Ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Common.Constants;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.adapter.ImageAdapter;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.view.PhotoViewPager;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.photoview.PhotoView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenPicActivity extends BaseActivity {




    public static void StartAct(Context context, int position, List<String> mImgList) {
        Intent intent = new Intent(context, OpenPicActivity.class);
        intent.putExtra(Constants.OpenPicMEDIALIST, (Serializable) mImgList);
        intent.putExtra(Constants.OpenPicPosition, position);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.a5, 0);
    }

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.pv_avatar)
    PhotoView mPvAvatar;
    @BindView(R.id.vp_avatar)
    PhotoViewPager mVpAvatar;
    @BindView(R.id.tv_vpNum)
    TextView mTvVpNum;
    //存储图片路径
    private List<String> mImgList;
    //点击位置
    private int mPosition;

    //当前图片位置/数量
    String title;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_pic;
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //设置状态栏文字颜色
        StatusBarUtils.setLightStatusBar(mActivity, false);
        //设置状态栏透明
        StatusBarUtils.transparencyBar(mActivity);
        mImgList = (List<String>) getIntent().getSerializableExtra(Constants.OpenPicMEDIALIST);
        mPosition = getIntent().getIntExtra(Constants.OpenPicPosition, 0);
        if (mImgList.size()>1){
            mTvVpNum.setVisibility(View.VISIBLE);
            mVpAvatar.setVisibility(View.VISIBLE);
            setViewPage();
        } else {
            mPvAvatar.setVisibility(View.VISIBLE);
            //设置当前点击位置
            UiUtils.GildeLoad(mPvAvatar, mImgList.get(mPosition));
        }

    }

    private void setViewPage() {
        title = "%1$d/%2$d";
        mTvVpNum.setText(String.format(title, mPosition + 1, mImgList.size()));

        mVpAvatar.setAdapter(new ImageAdapter(mImgList,OpenPicActivity.this));
        mVpAvatar.setCurrentItem(mPosition);
        mVpAvatar.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mPosition = position;
                mTvVpNum.setText(String.format(title, position + 1, mImgList.size()));
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.pv_avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(0, R.anim.a3);
                break;
            case R.id.pv_avatar:
                break;
        }
    }

}
