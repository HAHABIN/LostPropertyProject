package com.example.habin.lostpropertyproject.Ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Bean.entity.PersonInfoEntity;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.Widget.CircleImageView;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_detail;
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected void initParam() {
        super.initParam();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //是否显示底部栏
            isVis =  extras.getBoolean(Constants.IS_SHOW, false);
            data = (ArticleInfoEntity.ResultBean)extras.getSerializable(Constants.ACTICLEINFO_DATA);
        }
    }

    @Override
    protected void initView() {
        StatusBarUtils.transparencyBar(mActivity);

        if (isVis){
            mLlBomHelp.setVisibility(View.VISIBLE);
        } else {
            PersonInfoEntity.ResultBean userInfo = MyApplication.getUserInfo(mContext);
            UiUtils.GildeLoad(mContext,mCivPic,userInfo.getProfileImg());
            mTvNickname.setText(userInfo.getName());
        }
        mTvReleaseTime.setText(StringUtils.stampToDate(data.getCreateTime()));
        mTvAddress.setText(data.getAddressContent());
        mTvFindTime.setText(StringUtils.stampToDate(data.getFindTime()));
        mTvNoteContext.setText(data.getDescription());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

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
