package com.example.habin.lostpropertyproject.Ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.adapter.ImageAdapter;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.Widget.PhotoViewPager;
import com.luck.picture.lib.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class OpenPicActivity extends BaseActivity implements ImageAdapter.PicCallBack {

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
    protected void initParam() {
        super.initParam();
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            mImgList =  (List<String>) extras.getSerializable(Constants.OPEN_PIC_MEDIALAST);
            mPosition = extras.getInt(Constants.OPEN_PIC_POSITION, 0);
        }
    }

    @Override
    protected void initView() {
        //设置状态栏文字颜色
        StatusBarUtils.setLightStatusBar(mActivity, false);
        //设置状态栏透明
        StatusBarUtils.transparencyBar(mActivity);
        if (mImgList.size() > 1) {
            mTvVpNum.setVisibility(View.VISIBLE);
            mVpAvatar.setVisibility(View.VISIBLE);
            setViewPage();
        } else {
            mPvAvatar.setVisibility(View.VISIBLE);

                UiUtils.GildeLoad(mActivity,mPvAvatar, mImgList.get(mPosition));

        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    private void setViewPage() {
        title = "%1$d/%2$d";
        mTvVpNum.setText(String.format(title, mPosition + 1, mImgList.size()));
        mVpAvatar.setAdapter(new ImageAdapter(mImgList, OpenPicActivity.this));
        mVpAvatar.setCurrentItem(mPosition);
        mVpAvatar.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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
            case R.id.pv_avatar:
                finish();
                overridePendingTransition(0, R.anim.a3);
                break;
        }
    }

    @OnLongClick(R.id.pv_avatar)
    public boolean onViewLongClicked(View view) {
        ToastUtils.show_s("头像长按");

        return true;
    }

    @Override
    public void onPicClick() {
        finish();
        overridePendingTransition(0, R.anim.a3);
    }
}
