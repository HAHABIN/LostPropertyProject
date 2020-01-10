package com.example.habin.lostpropertyproject.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Bean.UploadPhotoParams;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.FullyGridLayoutManager;
import com.example.habin.lostpropertyproject.Util.SelectorDialogUtils;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.ui.adapter.GridImageAdapter;
import com.example.habin.lostpropertyproject.view.SelectDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
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
public class ReleaseActivity extends BaseActivity  {

    public static void StartAct(Context context, String type) {
        Intent intent = new Intent(context, ReleaseActivity.class);
        intent.putExtra("type", type);//1为发布丢失 2为发布拾物
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


    private int maxSelectNum = 3;
    private List<LocalMedia>  mSelectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private PopupWindow pop;
    private Disposable mSubscribe;
    private int mIndex = 0;
    private SelectorDialogUtils mPictureSelector;
    List<String> mAddressList;


    
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
        setTitle();
        mPictureSelector = new SelectorDialogUtils(mActivity);
        mAddressList = new ArrayList<>();
        for (int i = 0; i<10;i++){
            mAddressList.add("地址"+i);
        }

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRlImage.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList( mSelectList);
        adapter.setSelectMax(maxSelectNum);
        mRlImage.setAdapter(adapter);
        mRlImage.setLayoutManager(new GridLayoutManager(mContext, 4, LinearLayoutManager.VERTICAL, false));
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if ( mSelectList.size() > 0) {
                    LocalMedia media =  mSelectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file",  mSelectList);
                            PictureSelector.create(mActivity).externalPicturePreview(position,  mSelectList);
                            break;

                    }
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @SuppressLint("CheckResult")
        @Override
        public void onAddPicClick() {
            mPictureSelector.openDialogInActivity(maxSelectNum, mSelectList,true,false);
        }
    };

    //弹窗列表
    public void showList(List<String> mlist){
        SelectorDialogUtils.showDialog(mActivity,new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(int position) {

            }
        }, mlist);
    }



    //顶部设置
    private void setTitle() {
        setShowBack(View.VISIBLE);
        setRightText("发布");
        setRightOnClick().setOnClickListener(v -> {
            SnackbarUtils.show(mContext, "发布成功");
        });
        setBackOnClick().setOnClickListener(v -> finish());
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


    @OnClick({R.id.rl_image, R.id.ll_address, R.id.ll_time, R.id.ll_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_image:
                break;
            case R.id.ll_address:
                showList(mAddressList);
                break;
            case R.id.ll_time:
                showList(mAddressList);
                break;
            case R.id.ll_type:
                showList(mAddressList);
                break;
        }
    }


    private void showPop() {
        View bottomView = View.inflate(mActivity, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        //确认位置
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(mActivity)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                                .minSelectNum(1)// 最小选择数量
                                .imageSpanCount(4)// 每行显示个数
                                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                                .compress(true)// 是否压缩
                                .enableCrop(true)// 是否裁剪
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(mActivity)
                                .openCamera(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    private void uploadPhoto() {
        mSubscribe = Observable.fromArray( mSelectList.get(mIndex).getCompressPath()).map(imagePath -> {
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
//                        dismissDialog();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调

                images = PictureSelector.obtainMultipleResult(data);
                 mSelectList.addAll(images);

                // mSelectList = PictureSelector.obtainMultipleResult(data);

                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                adapter.setList( mSelectList);
                adapter.notifyDataSetChanged();
            }

        }
    }


}
