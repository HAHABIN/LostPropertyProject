package com.example.habin.lostpropertyproject.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.habin.lostpropertyproject.Widget.SelectDialog;
import com.example.habin.lostpropertyproject.view.SwipeRecyclerView;
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
 * 图片选择弹出框工具
 */
public class SelectorDialogUtils {


    private SelectDialog mDialog;
    private static SelectorDialogUtils mSelectorUtils;

    public static SelectorDialogUtils getInstance() {
        if (mSelectorUtils == null) {
            mSelectorUtils = new SelectorDialogUtils();
        }
        return new SelectorDialogUtils();
    }

    /**
     * 头像选择,包括裁剪以及压缩
     */
    public void openForHeaderInActivity(Activity activity) {
        openDialogInActivity(activity, 1, null, true, true);
    }

    /**
     * @param maxSelectNum 最大张数
     * @param selectList   已选择的图片
     * @param crop         是否裁剪
     */
    @SuppressLint("CheckResult")
    public void openDialogInActivity(final Activity activity, final int maxSelectNum, final List<LocalMedia> selectList, final boolean crop, final boolean isHeader) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {// 用户已经同意该权限
                            List<String> names = new ArrayList<>();
                            names.add("拍照");
                            names.add("相册");
                            showDialog(activity, new SelectDialog.SelectDialogListener() {
                                @Override
                                public void onItemClick(int position) {
                                    switch (position) {
                                        case 0: // 直接调起相机
                                            takePhoto(activity, maxSelectNum, selectList, crop, isHeader);
                                            break;
                                        case 1:
                                            openAlbum(activity, maxSelectNum, selectList, crop, isHeader);
                                            break;
                                        default:
                                            break;
                                    }
                                    mDialog.dismiss();
                                }
                            }, names);

                        } else {
                            Toast.makeText(activity, "拒绝", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    // 单独拍照
    private void takePhoto(Activity activity, int maxSelectNum, List<LocalMedia> selectList, boolean crop, boolean isheader) {
        PictureSelector.create(activity).openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
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
    private void openAlbum(Activity activity, int maxSelectNum, List<LocalMedia> selectList, boolean crop, boolean isheader) {
        PictureSelector.create(activity).openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
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

    public void showDialog(Activity activity, SelectDialog.SelectDialogListener listener, List<String> names) {
        if (mDialog == null) {
            mDialog = new SelectDialog(activity, listener, names);
        }
        if (!activity.isFinishing()) {
            mDialog.show();
        }
    }

    // 弹出条件选择器
    public void ShowBankName_T(Activity activity, final ArrayList<String> dList, final SwipeRecyclerView mSwipeView,final TextView view) {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(activity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (dList != null && !dList.isEmpty()) {
                    String strBankName = dList.get(options1);
                    view.setText(strBankName);
            //        mSwipeView.setRefreshing(true);
                }

            }
        })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小
                .setSubmitColor(Color.parseColor("#386FFE"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#386FFE"))//取消按钮文字颜色
                .setOutSideCancelable(false)// default is true
                .build();

        pvOptions.setPicker(dList);//条件选择器

        pvOptions.show();
    }
    // 弹出条件选择器
    public void ShowBankName(Activity activity, final ArrayList<String> dList, final TextView view) {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(activity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (dList != null && !dList.isEmpty()) {
                    String strBankName = dList.get(options1);
                    view.setText(strBankName);
                    //        mSwipeView.setRefreshing(true);
                }

            }
        })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小
                .setSubmitColor(Color.parseColor("#386FFE"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#386FFE"))//取消按钮文字颜色
                .setOutSideCancelable(false)// default is true
                .build();

        pvOptions.setPicker(dList);//条件选择器

        pvOptions.show();
    }
}
