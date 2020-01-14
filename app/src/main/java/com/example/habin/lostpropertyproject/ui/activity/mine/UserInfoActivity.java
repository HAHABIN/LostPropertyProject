package com.example.habin.lostpropertyproject.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Bean.UploadPhotoParams;
import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SelectorDialogUtils;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.view.CircleImageView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_helptimes)
    TextView mTvHelptimes;
    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;

    private String mCompressPath;
    private Disposable mSubscribe;
    private SelectorDialogUtils mPictureSelector;
    private PersonInfoEmtity.ResultBean mUserInfo;
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
        mPictureSelector = new SelectorDialogUtils(mActivity);
        setInfo();
    }
    private void setInfo(){
        mUserInfo = MyApplication.getUserInfo(mContext);
        mTvNickname.setText(mUserInfo.getName());
        mTvEmail.setText(mUserInfo.getEmail()!=null?mUserInfo.getEmail():"未设置");
        mTvGender.setText(mUserInfo.getGender()!=null?mUserInfo.getGender():"未设置");
        mTvHelptimes.setText(String.valueOf(mUserInfo.getHelpTimes()));
        mTvUserid.setText(String.valueOf(mUserInfo.getUserId()));
    }

    @OnClick({R.id.ll_avatar,R.id.ll_nickname, R.id.ll_gender, R.id.ll_area, R.id.ll_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:
//                showPop();
                new SelectorDialogUtils(mActivity).openForHeaderInActivity();
                break;
            case R.id.ll_nickname:
                EditNicknameActivity.StartAct(mContext);
                break;
            case R.id.ll_gender:
                EditGenderActivity.StartAct(mContext);
                break;
            case R.id.ll_area:
                break;
            case R.id.ll_email:
                break;
        }
    }






    private void uploadPhoto() {
        ProgressUtils.show(mContext);
        mSubscribe = Observable.fromArray(mCompressPath).map(imagePath -> {
            try {
                return StringUtils.encodeBase64Photo(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
                SnackbarUtils.show(mContext,"图片出错");
            }
            return "";
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s)) {
                        UploadPhotoParams params = new UploadPhotoParams.Builder()
                                .imgStr(s)
                                .resourceType("release")
                                .build();
//                        HttpClient.getInstance().startTask(HttpHelper.TaskType.UploadPhoto, this, HttpParams.getUploadPhotoParams(params), UploadPhotoEmtity.class);
                    } else {
                        ProgressUtils.dismiss();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() > 0) {
                    LocalMedia localMedia = selectList.get(0);
                    mCompressPath = localMedia.getCompressPath();
                    mTvEmail.setText(mCompressPath!=null?mCompressPath:"未设置");
//                    updatePhoto();上传
                }
            }

        }
    }


}

