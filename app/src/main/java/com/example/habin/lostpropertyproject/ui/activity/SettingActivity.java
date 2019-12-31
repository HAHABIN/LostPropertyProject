package com.example.habin.lostpropertyproject.ui.activity;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
/**
 * 设置页面
 * */
public class SettingActivity extends BaseActivity {

    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected boolean showTitle() {
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleText("设置");
        setShowBack(View.VISIBLE);
        setBackOnClick().setOnClickListener(v -> finish());
    }
}
