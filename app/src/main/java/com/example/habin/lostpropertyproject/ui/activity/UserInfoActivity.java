package com.example.habin.lostpropertyproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;

/**
 * Create by HABIN on 2020/1/11:55
 * Email:739115041@qq.com
 * 用户信息页面
 */
public class UserInfoActivity extends BaseActivity {

    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected boolean showTitle() {
        return true;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleText("个人信息");
        setShowBack(View.VISIBLE);
        setBackOnClick().setOnClickListener(v -> finish());
    }
}
