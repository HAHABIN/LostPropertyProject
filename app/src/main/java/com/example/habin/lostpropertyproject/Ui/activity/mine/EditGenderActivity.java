package com.example.habin.lostpropertyproject.Ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.TaskListener;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.EditGenderContract;
import com.example.habin.lostpropertyproject.Presenter.activity.mine.EditGenderPresenter;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class EditGenderActivity extends BaseMVPActivity<EditGenderContract.Presenter> implements EditGenderContract.View {

    public static void StartAct(Activity activity) {
        activity.startActivityForResult(new Intent(activity, EditGenderActivity.class),1000);
    }


    @BindView(R.id.iv_male)
    ImageView mIvMale;
    @BindView(R.id.iv_female)
    ImageView mIvFemale;

    private String ismale; //选中男女状态
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_gender;
    }

    @Override
    protected boolean showTitle() {
        return true;
    }

    @Override
    protected void initView() {
        setTitleStatus();
        //默认选择男
        mIvMale.setVisibility(View.VISIBLE);
        ismale = "男";
    }

    @Override
    protected void initListener() {
        setBackOnClick().setOnClickListener(v -> finish());
        setRightOnClick().setOnClickListener(v -> {
            ProgressUtils.show(mContext,"提交中.....");
            mPresenter.updateGender(ismale);
        });
    }

    @Override
    protected void initData() {

    }


    private void setTitleStatus() {
        setTitleText("设置性别");
        setShowBack(View.VISIBLE);
        setRightText("保存");
    }


    @OnClick({R.id.ll_male, R.id.ll_female})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_male:
                ismale = "男";
                mIvFemale.setVisibility(View.GONE);
                mIvMale.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_female:
                ismale = "女";
                mIvFemale.setVisibility(View.VISIBLE);
                mIvMale.setVisibility(View.GONE);
                break;
        }
    }




    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {
        ProgressUtils.dismiss();
        switch (type){
            case UpdateInfo:
                SharedPreferenceHandler.saveInfo(mContext,ismale,SharedPreferenceHandler.InfoType.Gender);
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
//        ToastUtils.show_s(e.getMessage());
    }

    @Override
    protected EditGenderContract.Presenter bindPresenter() {
        return new EditGenderPresenter();
    }
}
