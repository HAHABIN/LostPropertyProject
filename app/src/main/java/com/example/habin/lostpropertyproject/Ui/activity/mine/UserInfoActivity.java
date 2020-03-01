package com.example.habin.lostpropertyproject.Ui.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.PersonInfoEntity;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.activity.mine.UserInfoPresenter;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.UserInfoContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.activity.OpenPicActivity;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SelectorDialogUtils;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.Widget.CircleImageView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
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
public class UserInfoActivity extends BaseMVPActivity<UserInfoContract.Presenter> implements UserInfoContract.View {

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
    private PersonInfoEntity.ResultBean mUserInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected boolean showTitle() {
        return true;
    }

    @Override
    protected void initView() {
        setTitleText("个人信息");
        setShowBack(View.VISIBLE);
        setBackOnClick().setOnClickListener(v -> finish());
        setInfo();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }



    /**
     * 设置用户信息
     * */
    private void setInfo() {
        mUserInfo = MyApplication.getUserInfo(mContext);
        mTvNickname.setText(mUserInfo.getName());
        mTvEmail.setText(mUserInfo.getEmail() != null ? mUserInfo.getEmail() : "未设置");
        mTvGender.setText(mUserInfo.getGender() != null ? mUserInfo.getGender() : "未设置");
        mTvHelptimes.setText(String.valueOf(mUserInfo.getHelpTimes()));
        mTvUserid.setText(String.valueOf(mUserInfo.getUserId()));
        UiUtils.GildeLoad(mContext,mCivAvatar,mUserInfo.getProfileImg());
    }

    @OnClick({R.id.ll_avatar, R.id.civ_avatar,R.id.ll_nickname, R.id.ll_gender, R.id.ll_area, R.id.ll_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:
//                showPop();
                 SelectorDialogUtils.getInstance().openForHeaderInActivity(mActivity);
                break;
            case R.id.civ_avatar:
                List<String> imgList = new ArrayList<>();
                imgList.add(mUserInfo.getProfileImg());
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.OPEN_PIC_POSITION,0);
                bundle.putSerializable(Constants.OPEN_PIC_MEDIALAST,(Serializable)imgList);
                startActivity(OpenPicActivity.class,bundle);
//                OpenPicActivity.StartAct(mContext,0,imgList);
                overridePendingTransition(R.anim.a5, 0);
                break;
            case R.id.ll_nickname:
                startActivity(EditNicknameActivity.class,null, Constants.NICKNAME_REQUEST);
                break;
            case R.id.ll_gender:
                startActivity(EditGenderActivity.class,null, Constants.NICKNAME_REQUEST);
                break;
            case R.id.ll_area:
                break;
            case R.id.ll_email:
                startActivity(EditEmailActivity.class,null, Constants.NICKNAME_REQUEST);
                break;
        }
    }

    //图片转为base 并上传
    private void uploadPhoto() {
        ProgressUtils.show(mContext);

        mSubscribe = Observable.fromArray(mCompressPath).map(imagePath -> {
            try {
                return StringUtils.encodeBase64Photo(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.show_s(mContext, "图片出错");
            }
            return "";
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s)) {
                        //上传图片
                        mPresenter.UploadPhoto(s);
                    } else {
                        ProgressUtils.dismiss();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST: // 图片选择结果回调
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        LocalMedia localMedia = selectList.get(0);
                        if (localMedia.isCut()){
                            mCompressPath = localMedia.getCutPath();
                        } else {
                            mCompressPath = localMedia.getCompressPath();
                        }
                        //加载头像
                        Glide.with(mContext).load(Uri.fromFile(new File(mCompressPath))).into(mCivAvatar);
                        uploadPhoto();//上传
                    }
                }
                break;
            case 1000://用户信息返回
                setInfo();
                break;

        }
    }


    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {

    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {
        ProgressUtils.dismiss();
        switch (type){
            case UploadPhoto:
                try {
                    ToastUtils.show_s(object.getString("message"));
                    if (object.getInt("code") == 1){
                        ProgressUtils.show(mContext,"正在加载图片。。。");
                        String profileimg = object.getString("result");
                        //保存图片地址
                        SharedPreferenceHandler.saveInfo(mContext, profileimg,SharedPreferenceHandler.InfoType.ProfileImg);
                        mPresenter.updateInfo(profileimg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UpdateInfo:
                try {
                    ToastUtils.show_s(object.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        ProgressUtils.dismiss();
//        ToastUtils.show_l(e.getMessage());
    }

    @Override
    protected UserInfoContract.Presenter bindPresenter() {
        return new UserInfoPresenter();
    }
}

