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
import com.example.habin.lostpropertyproject.Presenter.activity.contract.EditNicknameContract;
import com.example.habin.lostpropertyproject.Presenter.activity.mine.EditNickNamePresenter;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

public class EditNicknameActivity extends BaseMVPActivity<EditNicknameContract.Presenter> implements EditNicknameContract.View {


    @BindView(R.id.ed_nickname)
    EditText edNickname;
    private String nickname = null;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_nickname;
    }

    @Override
    protected void initView() {
        setTitleStatus();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean showTitle() {
        return true;
    }


    private void setTitleStatus() {
        setTitleText("修改昵称");
        setShowBack(View.VISIBLE);
        setRightText("保存");
        setBackOnClick().setOnClickListener(v -> finish());
        setRightOnClick().setOnClickListener(v -> {
            nickname = edNickname.getText().toString().trim();
            if (nickname.isEmpty()){
                ToastUtils.show_s("昵称不能为空");
                return;
            }
            ProgressUtils.show(mContext,"提交中.....");
            mPresenter.updateNickName(nickname);
        });
    }



    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {
        ProgressUtils.dismiss();
        switch (type){
            case UpdateInfo:
                SharedPreferenceHandler.saveInfo(mContext,nickname,SharedPreferenceHandler.InfoType.NickName);
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
        ToastUtils.show_s(e.getMessage());
    }

    @Override
    protected EditNicknameContract.Presenter bindPresenter() {
        return new EditNickNamePresenter();
    }
}
