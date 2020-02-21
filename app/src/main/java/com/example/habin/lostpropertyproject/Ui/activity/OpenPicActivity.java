package com.example.habin.lostpropertyproject.Ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Common.Constants;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.photoview.PhotoView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OpenPicActivity extends BaseActivity {



    public static void StartAct(Context context, int position, List<LocalMedia> mediaList) {
        Intent intent = new Intent(context, OpenPicActivity.class);
        intent.putExtra(Constants.OpenPicMEDIALIST, (Serializable) mediaList);
        intent.putExtra(Constants.OpenPicPosition, position);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.a5, 0);
    }
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.pv_avatar)
    PhotoView mPvAvatar;
    //存储图片路径
    private List<LocalMedia> mMediaList;
    //点击位置
    private int position;


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
        mMediaList = (List<LocalMedia>) getIntent().getSerializableExtra(Constants.OpenPicMEDIALIST);
        position = getIntent().getIntExtra(Constants.OpenPicPosition, 0);
        //设置当前点击位置
        UiUtils.GildeLoad(mPvAvatar,mMediaList.get(0).getPath());
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
