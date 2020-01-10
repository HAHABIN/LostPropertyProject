package com.example.habin.lostpropertyproject.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;

public class EditNicknameActivity extends BaseActivity {


    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, EditNicknameActivity.class));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_nickname;
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
        setTitleText("修改昵称");
        setShowBack(View.VISIBLE);
        setRightText("保存");
        setBackOnClick().setOnClickListener(v -> finish());
        setRightOnClick().setOnClickListener(v -> {
            SnackbarUtils.show(this,"保存成功");
        });
    }
}
