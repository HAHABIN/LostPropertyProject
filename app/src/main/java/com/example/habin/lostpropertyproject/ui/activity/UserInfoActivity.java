package com.example.habin.lostpropertyproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by HABIN on 2020/1/11:55
 * Email:739115041@qq.com
 * 用户信息页面
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_userid)
    TextView mTvUserid;
    @BindView(R.id.tv_gender)
    TextView mTvGender;
    @BindView(R.id.area)
    TextView mTvArea;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_helptimes)
    TextView mTvHelptimes;

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

    @OnClick({R.id.tv_nickname, R.id.tv_gender, R.id.area, R.id.tv_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_nickname:
                break;
            case R.id.tv_gender:
                break;
            case R.id.area:
                break;
            case R.id.tv_email:
                break;
        }
    }
}
