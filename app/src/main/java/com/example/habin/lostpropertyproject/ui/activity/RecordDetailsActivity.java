package com.example.habin.lostpropertyproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.example.habin.lostpropertyproject.view.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by habin
 * on 2020/1/2
 * Email 739115041@qq.com
 * 物品详细页面
 */
public class RecordDetailsActivity extends BaseActivity {


    @BindView(R.id.civ_pic)
    CircleImageView mCivPic;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_release_time)
    TextView mTvReleaseTime;
    @BindView(R.id.tv_note_context)
    TextView mTvNoteContext;
    @BindView(R.id.iv_content_pic)
    ImageView mIvContentPic;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_find_time)
    TextView mTvFindTime;
    @BindView(R.id.ll_bom_help)
    LinearLayout mLlBomHelp;
    private boolean isVis;

    public static void StartAct(Context context,boolean isVis) {
        Intent intent = new Intent(context, RecordDetailsActivity.class);
        //是否显示底部栏
        intent.putExtra("isVis",isVis);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_detail;
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        StatusBarUtils.transparencyBar(mActivity);
        isVis = getIntent().getBooleanExtra("isVis", false);
        if (isVis){
            mLlBomHelp.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.iv_back, R.id.civ_pic,R.id.btn_help, R.id.btn_private_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.civ_pic:
                break;
            case R.id.btn_help:
                break;
            case R.id.btn_private_chat:
                break;
        }
    }


}
