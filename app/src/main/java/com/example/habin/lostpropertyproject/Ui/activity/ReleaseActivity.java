package com.example.habin.lostpropertyproject.Ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.UploadPhotoParams;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.activity.ReleasePresenter;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.ReleaseContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.adapter.GridImageAdapter;
import com.example.habin.lostpropertyproject.Util.FullyGridLayoutManager;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SelectorDialogUtils;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * created by habin
 * on 2020/1/2
 * Email 739115041@qq.com
 * 发布模块
 */
public class ReleaseActivity extends BaseMVPActivity<ReleaseContract.Presenter> implements ReleaseContract.View {

    //弃用
    public static void StartAct(Context context, String type) {
        Intent intent = new Intent(context, ReleaseActivity.class);
        intent.putExtra(Constants.RELEASE_TYPE, type);//1为发布丢失 2为发布拾物
        context.startActivity(intent);
    }


    @BindView(R.id.rl_image)
    RecyclerView mRlImage;
    @BindView(R.id.ll_address)
    LinearLayout mLlAddress;
    @BindView(R.id.ll_time)
    LinearLayout mLlTime;
    @BindView(R.id.ll_type)
    LinearLayout mLlType;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_address_detail)
    EditText mEtAddressDetail;
    @BindView(R.id.et_description)
    EditText mEtDescription;
    private String statusType;

    private int maxSelectNum = 3;
    private List<LocalMedia> mSelectList = new ArrayList<>();
    private List<UploadPhotoParams> uploadPhotoList;
    private GridImageAdapter adapter;
    private Disposable mSubscribe;
    private int mIndex = 0;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_release;
    }

    @Override
    protected boolean showTitle() {
        return true;
    }

    @Override
    protected void initParam() {
        super.initParam();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            statusType = extras.getString(Constants.RELEASE_TYPE);
        }

    }

    @Override
    protected void initView() {
        setTitle();
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRlImage.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(mSelectList);
        adapter.setSelectMax(maxSelectNum);
        mRlImage.setAdapter(adapter);
        mRlImage.setLayoutManager(new GridLayoutManager(mContext, 4, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mSelectList.size() > 0) {
                    LocalMedia media = mSelectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file",  mSelectList);
                            PictureSelector.create(mActivity).externalPicturePreview(position, mSelectList);
                            break;

                    }
                }
            }
        });
    }

    @Override
    protected void initData() {

    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @SuppressLint("CheckResult")
        @Override
        public void onAddPicClick() {
            SelectorDialogUtils.getInstance().openDialogInActivity(mActivity, maxSelectNum, mSelectList, true, false);
        }
    };


    //顶部设置
    private void setTitle() {
        setShowBack(View.VISIBLE);
        setRightText("发布");
        setRightOnClick().setOnClickListener(v -> {
            //关闭键盘
            UiUtils.hideSoftKeyboardNoView(mActivity);
            load();
        });
        setBackOnClick().setOnClickListener(v -> finish());

        switch (statusType) {
            case "1":
                setTitleText("编辑丢失物品");
                break;
            case "2":
                setTitleText("编辑拾物物品");
                break;
        }
    }

    //上传发布信息
    private void load() {
        String typename = mTvType.getText().toString();
        String time = mTvTime.getText().toString();
        String address = mTvAddress.getText().toString();
        String description = mEtDescription.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        if (!StringUtils.checkPhoneNumber(phone)) {
            ToastUtils.show_s("请填写正确联系电话");
            return;
        }
        if (description.length() == 0) {
            ToastUtils.show_s("请填写物品信息");
        }
        if (address.equals("选择地点")) {
            ToastUtils.show_s("请选择丢失范围");
            return;
        }
        if (time.equals("丢失时间")) {
            ToastUtils.show_s("请填写大概丢失日");
            return;
        }
        if (typename.equals("选择类别")) {
            ToastUtils.show_s("请选择类别");
            return;
        }
        if (mSelectList.size() == 0) {
            ToastUtils.show_s("请至少选一张照片");
            return;
        } else {
            uploadPhoto();
        }
        ProgressUtils.show(mContext, "正在发布....");

    }


    @OnClick({R.id.rl_image, R.id.ll_address, R.id.ll_time, R.id.ll_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_image:
                break;
            case R.id.ll_address:
                UiUtils.hideSoftKeyboardNoView(mActivity);
                SelectorDialogUtils.getInstance().ShowCity(mActivity, mTvAddress);
                break;
            case R.id.ll_time:
                UiUtils.hideSoftKeyboardNoView(mActivity);
                SelectorDialogUtils.getInstance().ShowTime(mActivity, mTvTime);
                break;
            case R.id.ll_type:
                UiUtils.hideSoftKeyboardNoView(mActivity);
                SelectorDialogUtils.getInstance().ShowType(mActivity, MyApplication.getTypeList(), mTvType);
                break;
        }
    }


    private void uploadPhoto() {
//        ProgressUtils.show(mContext, "正在上传..");
        mSubscribe = Observable.fromArray(mSelectList.get(mIndex).getCompressPath()).map(imagePath -> {
            try {
                return StringUtils.encodeBase64Photo(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
                SnackbarUtils.show(mContext, "图片出错");
            }
            return "";
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s)) {
//                        UploadPhotoParams params = new UploadPhotoParams.Builder()
//                                .imgStr(s)
//                                .resourceType("release")
//                                .build();
                        mPresenter.UploadPhoto(s);
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
                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
                mSelectList.clear();
                mSelectList.addAll(images);
                // mSelectList = PictureSelector.obtainMultipleResult(data);
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                adapter.setList(mSelectList);
                adapter.notifyDataSetChanged();
            }

        }
    }


    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {

    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {
        ProgressUtils.dismiss();
        switch (type) {
            case InsertArInfo:
                ToastUtils.show_s("发布成功");
                finish();
                break;
            case UploadPhoto:
                if (uploadPhotoList == null) {
                    uploadPhotoList = new ArrayList<>();
                }
                UploadPhotoParams params = new UploadPhotoParams();
                try {
                    if (object.getInt("code") == 1) {
                        String profileimgs = object.optString("result");
                        params.setImgStr(profileimgs);
                        params.setResourceType("release");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                uploadPhotoList.add(mIndex, params);
                mIndex++;
                if (mIndex != mSelectList.size()) {
                    uploadPhoto();
                } else {
                    Gson g = new Gson();
                    //将图片地址转化为json字符串
                    String imgStr = g.toJson(uploadPhotoList);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("userId", MyApplication.getUserId(mContext));
                    hashMap.put("typeId", StringUtils.typeNameToId(mTvType.getText().toString()));
                    hashMap.put("phone", mEtPhone.getText().toString().trim());
                    hashMap.put("findTime", StringUtils.dateToStamp(mTvTime.getText().toString()));
                    hashMap.put("addressContent", String.format("%s", mTvAddress.getText().toString()));
                    hashMap.put("description", mEtDescription.getText().toString().trim());
                    hashMap.put("status", statusType);
                    hashMap.put("imgStr", imgStr);
                    hashMap.put("recordStatus", statusType);
                    mPresenter.InsertArInfo(hashMap);
                }
                break;
        }
    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        ProgressUtils.dismiss();
//        ToastUtils.show_s(mContext, e.getMessage());
    }

    @Override
    protected ReleaseContract.Presenter bindPresenter() {
        return new ReleasePresenter();
    }


}
