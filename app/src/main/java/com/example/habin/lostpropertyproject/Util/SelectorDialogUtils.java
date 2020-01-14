package com.example.habin.lostpropertyproject.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;

import com.example.habin.lostpropertyproject.Widget.SelectDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * created by habin
 * on 2020/1/10
 * Email 739115041@qq.com
 * 图片选择
 */
public class SelectorDialogUtils {


    private  SelectDialog mDialog;
    private Activity mActivity;

    public SelectorDialogUtils(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 头像选择,包括裁剪以及压缩
     */
    public void openForHeaderInActivity() {
        openDialogInActivity(1, null, true, true);
    }

    /**
     * @param maxSelectNum 最大张数
     * @param selectList   已选择的图片
     * @param crop         是否裁剪
     */
    @SuppressLint("CheckResult")
    public  void openDialogInActivity(final int maxSelectNum, final List<LocalMedia> selectList, final boolean crop, final boolean isHeader) {
        RxPermissions rxPermission = new RxPermissions(mActivity);
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {// 用户已经同意该权限
                            List<String> names = new ArrayList<>();
                            names.add("拍照");
                            names.add("相册");
                            showDialog(new SelectDialog.SelectDialogListener() {
                                @Override
                                public void onItemClick(int position) {
                                    switch (position) {
                                        case 0: // 直接调起相机
                                            takePhoto(maxSelectNum, selectList, crop, isHeader);
                                            break;
                                        case 1:
                                            openAlbum(maxSelectNum, selectList, crop, isHeader);
                                            break;
                                        default:
                                            break;
                                    }
                                    mDialog.dismiss();
                                }
                            }, names);

                        } else {
                            Toast.makeText(mActivity, "拒绝", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    // 单独拍照
    private void takePhoto(int maxSelectNum, List<LocalMedia> selectList, boolean crop, boolean isheader) {
        PictureSelector.create(mActivity).openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .selectionMedia(selectList)// 是否传入已选图片
                .enableCrop(crop)
                .compress(true)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .previewImage(false)// 是否可预览图片
                .circleDimmedLayer(isheader)
                .showCropFrame(!isheader)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(!isheader)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    // 进入相册 以下是例子：不需要的api可以不写
    public void openAlbum(int maxSelectNum, List<LocalMedia> selectList, boolean crop, boolean isheader) {
        PictureSelector.create(mActivity).openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .selectionMedia(selectList)// 是否传入已选图片
                .enableCrop(crop)
                .compress(true)
                .isCamera(false)// 是否显示拍照按钮
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .previewImage(false)// 是否可预览图片
                .circleDimmedLayer(isheader)
                .showCropFrame(!isheader)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(!isheader)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    public SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        if (mDialog == null) {
            mDialog = new SelectDialog(mActivity, listener, names);
        }
        if (!mActivity.isFinishing()) {
            mDialog.show();
        }
        return mDialog;
    }
}
