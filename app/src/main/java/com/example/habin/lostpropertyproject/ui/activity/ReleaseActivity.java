package com.example.habin.lostpropertyproject.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.FullyGridLayoutManager;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.ui.adapter.GridImageAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * created by habin
 * on 2020/1/2
 * Email 739115041@qq.com
 * 发布模块
 */
public class ReleaseActivity extends BaseActivity {

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
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private PopupWindow pop;

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


    }

    @Override
    protected void initWidget() {
        super.initWidget();
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRlImage.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        mRlImage.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(mActivity).externalPicturePreview(position, selectList);
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
            //获取写的权限
            RxPermissions rxPermission = new RxPermissions(mActivity);
            rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) {
                            if (permission.granted) {// 用户已经同意该权限
                                //第一种方式，弹出选择和拍照的dialog
                                showPop();

                                //第二种方式，直接进入相册，但是 是有拍照得按钮的
//                                showAlbum();
                            } else {
                                Toast.makeText(mActivity, "拒绝", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };

    //顶部设置
    private void setTitle() {
        setShowBack(View.VISIBLE);
        setShowRelease(View.VISIBLE);
        setReleaseOnClick().setOnClickListener(v -> finish());
        setBackOnClick().setOnClickListener(v -> {
            SnackbarUtils.show(mContext, "发布成功");
        });
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
                break;
            case R.id.ll_time:
                break;
            case R.id.ll_type:
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调

                images = PictureSelector.obtainMultipleResult(data);
                selectList.addAll(images);

                //selectList = PictureSelector.obtainMultipleResult(data);

                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
