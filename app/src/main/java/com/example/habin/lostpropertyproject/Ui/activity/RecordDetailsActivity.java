package com.example.habin.lostpropertyproject.Ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Bean.entity.UploadPhotoParams;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.Widget.CircleImageView;
import com.google.gson.reflect.TypeToken;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.tv_vpNum)
    TextView mTvVpNum;
    @BindView(R.id.tv_type_name)
    TextView mTvTypeName;

    private boolean isVis;
    private ArticleInfoEntity.ResultBean data;
    private List<String> imgList;

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
            isVis = extras.getBoolean(Constants.IS_SHOW, false);
            data = (ArticleInfoEntity.ResultBean) extras.getSerializable(Constants.ACTICLEINFO_DATA);
            if (data != null && data.getImgStr() != null) {
                List<UploadPhotoParams> uploadPhotoParams = JsonUtil.fromJson(data.getImgStr(), new TypeToken<List<UploadPhotoParams>>() {
                });
                imgList = new ArrayList<>();
                for (UploadPhotoParams imgstr : uploadPhotoParams) {
                    imgList.add(imgstr.getImgStr());
                }
            }
        }
    }

    @Override
    protected void initView() {
        StatusBarUtils.transparencyBar(mActivity);

        if (isVis) {
            mLlBomHelp.setVisibility(View.VISIBLE);
        }
        UiUtils.GildeLoad(mContext, mCivPic, data.getPersonInfo().getProfileImg());
        if (imgList != null && imgList.size() > 0) {
            UiUtils.GildeLoad(mContext, mIvContentPic, imgList.get(0));
            mTvVpNum.setText(String.format("%1$d/%2$d",1,imgList.size()));
        }
        mTvTypeName.setText(StringUtils.typeIdToName(data.getTypeId()));
        mTvNickname.setText(data.getPersonInfo().getName());
        mTvReleaseTime.setText(StringUtils.getTimeFormatText(data.getCreateTime()));
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


    @OnClick({R.id.rl_content_pic, R.id.iv_back,R.id.btn_call, R.id.civ_pic, R.id.btn_help, R.id.btn_private_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_content_pic:
                if (imgList != null && imgList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.OPEN_PIC_POSITION, 0);
                    bundle.putSerializable(Constants.OPEN_PIC_MEDIALAST, (Serializable) imgList);
                    startActivity(OpenPicActivity.class, bundle);
                    overridePendingTransition(R.anim.a5, 0);
                } else {
                    ToastUtils.show_s("图片为空");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.civ_pic:
                break;
            case R.id.btn_help:
                break;
            case R.id.btn_private_chat:
                break;
            case R.id.btn_call:
                checkPermissionRequest(this);
                break;
        }
    }
    @SuppressLint("CheckResult")
    public void checkPermissionRequest(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.request(Manifest.permission.CALL_PHONE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            call();
                        } else {
                            SnackbarUtils.show(mActivity, "拒绝权限");
                            call();
                        }

                    }
                });
    }

    public void call() {
        // 执行拨号动作
        Intent mIntent = new Intent(Intent.ACTION_CALL);
        mIntent.setData(Uri.parse("tel:" + data.getPhone()));
        startActivity(mIntent);
    }
}
