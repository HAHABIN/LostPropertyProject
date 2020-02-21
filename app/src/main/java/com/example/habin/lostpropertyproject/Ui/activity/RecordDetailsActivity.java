package com.example.habin.lostpropertyproject.Ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Bean.entity.PersonInfoEntity;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.view.CircleImageView;

import java.io.Serializable;

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
    private ArticleInfoEntity.ResultBean data;

    public static void StartAct(Context context,boolean isVis) {
        Intent intent = new Intent(context, RecordDetailsActivity.class);
        //是否显示底部栏
        intent.putExtra("isVis",isVis);
        context.startActivity(intent);
    }
    public static void StartAct(Context context, boolean isVis, ArticleInfoEntity.ResultBean data) {
        Intent intent = new Intent(context, RecordDetailsActivity.class);
        //是否显示底部栏
        intent.putExtra("isVis",isVis);
        intent.putExtra("data",data);
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
        data = (ArticleInfoEntity.ResultBean) getIntent().getSerializableExtra("data");
        if (isVis){
            mLlBomHelp.setVisibility(View.VISIBLE);
        } else {
            PersonInfoEntity.ResultBean userInfo = MyApplication.getUserInfo(mContext);
            UiUtils.GildeLoad(mCivPic,userInfo.getProfileImg());
            mTvNickname.setText(userInfo.getName());
        }

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTvReleaseTime.setText(StringUtils.stampToDate(data.getCreateTime()));
        mTvAddress.setText(data.getAddressContent());
        mTvFindTime.setText(StringUtils.stampToDate(data.getFindTime()));
        mTvNoteContext.setText(data.getDescription());
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
