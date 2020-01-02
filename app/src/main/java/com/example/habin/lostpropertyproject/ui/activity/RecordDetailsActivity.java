package com.example.habin.lostpropertyproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, RecordDetailsActivity.class));
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

    }


    @OnClick({R.id.iv_back, R.id.civ_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.civ_pic:
                break;
        }
    }
}
