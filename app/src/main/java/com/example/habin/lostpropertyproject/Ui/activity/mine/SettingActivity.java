package com.example.habin.lostpropertyproject.Ui.activity.mine;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Ui.activity.Land.EditPasswordActivity;
import com.example.habin.lostpropertyproject.Ui.activity.MainActivity;
import com.example.habin.lostpropertyproject.Widget.AlertDialogView;

import butterknife.OnClick;

/**
 * 设置页面
 */
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

    @OnClick({R.id.ll_clear_data, R.id.ll_exit,R.id.ll_edit_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_pass:
                EditPasswordActivity.StartAct(mContext);
                break;
            case R.id.ll_clear_data:
                break;
            case R.id.ll_exit:
                AlertDialogView dialogView = new AlertDialogView(this);
                dialogView.setTitle("退出登录");
                dialogView.setMessage("确认退出，数据清除");
                dialogView.setConfimStr("确认");
                dialogView.setCancelStr("取消");
                dialogView.setListener(new AlertDialogView.onClickListener() {
                    @Override
                    public void cancelClick(AlertDialogView dialog) {
                    }

                    @Override
                    public void confirmClick(AlertDialogView dialog) {
                        SharedPreferenceHandler.cleanUserInfo(mContext);
                        MainActivity.StartAct(mContext);
                        mActivity.finish();
                    }
                });
                dialogView.show();
                break;
        }
    }
}
