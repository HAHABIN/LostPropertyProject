package com.example.habin.lostpropertyproject.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class EditGenderActivity extends BaseActivity {

    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, EditGenderActivity.class));
    }


    @BindView(R.id.iv_male)
    ImageView mIvMale;
    @BindView(R.id.iv_female)
    ImageView mIvFemale;

    private boolean ismale; //选中男女状态
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_gender;
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
        setTitleText("设置性别");
        setShowBack(View.VISIBLE);
        setRightText("保存");
        setBackOnClick().setOnClickListener(v -> finish());
        setRightOnClick().setOnClickListener(v -> {
            SnackbarUtils.show(this, "保存成功");
        });
    }


    @OnClick({R.id.ll_male, R.id.ll_female})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_male:
                ismale = true;
                mIvFemale.setVisibility(View.GONE);
                mIvMale.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_female:
                ismale = false;
                mIvFemale.setVisibility(View.VISIBLE);
                mIvMale.setVisibility(View.GONE);
                break;
        }
    }
}
