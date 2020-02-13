package com.example.habin.lostpropertyproject.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.UploadPhotoParams;
import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;
import com.example.habin.lostpropertyproject.Common.Constants;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.TaskListener;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SelectorDialogUtils;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.view.CircleImageView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by HABIN on 2020/1/11:55
 * Email:739115041@qq.com
 * 用户信息页面
 */
public class UserInfoActivity extends BaseActivity implements TaskListener {

    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }

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
        UiUtils.GildeLoad(mCivAvatar,mUserInfo.getProfileImg());
    }

    @OnClick({R.id.ll_avatar, R.id.ll_nickname, R.id.ll_gender, R.id.ll_area, R.id.ll_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:
//                showPop();
                new SelectorDialogUtils(mActivity).openForHeaderInActivity();
                break;
            case R.id.ll_nickname:
                EditNicknameActivity.StartAct(mActivity);
                break;
            case R.id.ll_gender:
                EditGenderActivity.StartAct(mActivity);
                break;
            case R.id.ll_area:
                break;
            case R.id.ll_email:
                EditEmailActivity.StartAct(mActivity);
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
                ToastUtils.show_s(mContext, "图片出错");
            }
            return "";
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s)) {
                        UploadPhotoParams params = new UploadPhotoParams.Builder()
                                .imgStr(s)
                                .resourceType("avatar")
                                .build();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imgStr", params.getImgStr());
                        hashMap.put("resourceType", params.getResourceType());
                        HttpClient.getSingleton().startTask(HttpHelper.TaskType.UploadPhoto, UserInfoActivity.this, hashMap);
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
                        mCompressPath = localMedia.getCutPath();
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
    public void taskStarted(HttpHelper.TaskType type) {

    }

    @Override
    public void taskError(HttpHelper.TaskType type, ApiError error) {
        ProgressUtils.dismiss();
        ToastUtils.show_l(error.getMessage());
    }

    @Override
    public void taskFinished(HttpHelper.TaskType type, JSONObject object) {
        ProgressUtils.dismiss();
        switch (type){
            case UploadPhoto:
                try {
                    ToastUtils.show_s(object.getString("message"));
                    if (object.getInt("code") == 1){
                        ProgressUtils.show(mContext,"正在加载图片。。。");
                        String profileimg = object.getString("data");
                        //保存图片地址
                        SharedPreferenceHandler.saveInfo(mContext,profileimg,SharedPreferenceHandler.InfoType.ProfileImg);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("userId", SharedPreferenceHandler.getUserId(mContext));
                        hashMap.put("profileimg",profileimg);
                        HttpClient.getSingleton().startTask(HttpHelper.TaskType.UpdateInfo, UserInfoActivity.this, hashMap,PersonInfoEmtity.class);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void taskFinished(HttpHelper.TaskType type, HttpItem item) {
        ProgressUtils.dismiss();
        switch (type){
            case UpdateInfo:
                ToastUtils.show_s(item.getMessage());
                break;
        }
    }
}

