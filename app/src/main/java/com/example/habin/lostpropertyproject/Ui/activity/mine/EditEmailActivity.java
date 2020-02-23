package com.example.habin.lostpropertyproject.Ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.TaskListener;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.EditEmailContract;
import com.example.habin.lostpropertyproject.Presenter.activity.mine.EditEmailPresenter;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

public class EditEmailActivity extends BaseMVPActivity<EditEmailContract.Presenter> implements EditEmailContract.View {

    public static void StartAct(Activity activity) {
        activity.startActivityForResult(new Intent(activity, EditEmailActivity.class),1000);
    }

    @BindView(R.id.ed_email)
    EditText mEdEmail;
    private String email;
    @Override
    protected boolean showTitle() {
        return true;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_email;
    }

    @Override
    protected void initView() {
        setTitleStatus();
    }

    @Override
    protected void initListener() {
        setBackOnClick().setOnClickListener(v -> finish());
        setRightOnClick().setOnClickListener(v -> {
            email = mEdEmail.getText().toString().trim();
            if (!StringUtils.checkEmail(email)) {
                ToastUtils.show_s("请输入正确的邮箱格式");
                return;
            }
            if (email.isEmpty()) {
                ToastUtils.show_s("邮箱不能为空");
                return;
            }
            ProgressUtils.show(mContext, "提交中.....");
            mPresenter.updateEmail(email);
        });
    }

    @Override
    protected void initData() {

    }

    private void setTitleStatus() {
        setTitleText("修改邮箱");
        setShowBack(View.VISIBLE);
        setRightText("保存");
    }
    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {
        ProgressUtils.dismiss();
        switch (type){
            case UpdateInfo:
                SharedPreferenceHandler.saveInfo(mContext,email,SharedPreferenceHandler.InfoType.Email);
                ToastUtils.show_s(item.getMessage());
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {

    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        ProgressUtils.dismiss();
    }

    @Override
    protected EditEmailContract.Presenter bindPresenter() {
        return new EditEmailPresenter();
    }
}
