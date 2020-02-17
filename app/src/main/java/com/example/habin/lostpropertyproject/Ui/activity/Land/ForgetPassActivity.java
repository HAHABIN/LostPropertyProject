package com.example.habin.lostpropertyproject.Ui.activity.Land;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;

public class ForgetPassActivity extends BaseActivity {

    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, ForgetPassActivity.class));
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pass;
    }

    @Override
    protected boolean showTitle() {
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleStatus();

    }

    private void setTitleStatus() {
        setTitleText("忘记密码");
        setShowBack(View.VISIBLE);
        setRightText("保存");
        setBackOnClick().setOnClickListener(v -> finish());
        setRightOnClick().setOnClickListener(v -> {

        });
    }
}
