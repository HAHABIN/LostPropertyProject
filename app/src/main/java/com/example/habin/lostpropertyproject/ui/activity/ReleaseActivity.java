package com.example.habin.lostpropertyproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.view.MyGirdView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by habin
 * on 2020/1/2
 * Email 739115041@qq.com
 * 发布模块
 */
public class ReleaseActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.gv_pic)
    MyGirdView gvPic;

    public static void StartAct(Context context, String type) {
        Intent intent = new Intent(context, ReleaseActivity.class);
        intent.putExtra("type", type);//1为发布丢失 2为发布拾物
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release;
    }

    @Override
    protected boolean showTitle() {
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setShowBack(View.VISIBLE);
        setShowRelease(View.VISIBLE);
        setReleaseOnClick().setOnClickListener(this);
        setBackOnClick().setOnClickListener(this);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        switch (type) {
            case "1":
                setTitleText("编辑丢失物品");
                break;
            case "2":
                setTitleText("编辑拾物物品");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
            case R.id.tv_submit:
                SnackbarUtils.show(mContext, "发布成功");
                break;
        }
    }



    @OnClick({R.id.gv_pic, R.id.ll_address, R.id.ll_time, R.id.ll_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gv_pic:
                break;
            case R.id.ll_address:
                break;
            case R.id.ll_time:
                break;
            case R.id.ll_type:
                break;
        }
    }
}
