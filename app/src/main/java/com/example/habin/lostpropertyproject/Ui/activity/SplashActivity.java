package com.example.habin.lostpropertyproject.Ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.Widget.AlertDialogView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {


    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        //全屏透明状态栏
        StatusBarUtils.transparencyBar(mActivity);
        restart(UiUtils.isNetworkConnected());
//        checkPermissionRequest(this);

    }
    private void restart(Boolean flag){
        if (!flag){
            AlertDialogView dialogView = new AlertDialogView(this);
            dialogView.setTitle("网络请求");
            dialogView.setMessage("当前无网络，请开启网络");
            dialogView.setConfimStr("退出");
            dialogView.setCancelStr("重试");
            dialogView.setListener(new AlertDialogView.onClickListener() {
                @Override
                public void cancelClick(AlertDialogView dialog) {
                    restart(UiUtils.isNetworkConnected());
                }
                @Override
                public void confirmClick(AlertDialogView dialog) {
                    finish();
                }
            });
            dialogView.show();
        } else {
            StartUp();
        }
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private void StartUp() {
        RelativeLayout layoutSplash = findViewById(R.id.activity_splash);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(2000);//设置动画播放时长1000毫秒（1秒）
        layoutSplash.startAnimation(alphaAnimation);
        //设置动画监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            //动画结束
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(MainActivity.class,null);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }


    @SuppressLint("CheckResult")
    public void checkPermissionRequest(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            StartUp();
                        } else {
                            SnackbarUtils.show(mActivity, "拒绝权限");
                            StartUp();
                        }

                    }
                });
    }
}
