package com.example.habin.lostpropertyproject.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleType;
import com.example.habin.lostpropertyproject.Bean.entity.City;
import com.example.habin.lostpropertyproject.Bean.entity.County;
import com.example.habin.lostpropertyproject.Bean.entity.Province;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Widget.SelectDialog;
import com.example.habin.lostpropertyproject.view.SwipeRecyclerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private static ArrayList<Province> proviceList = MyApplication.getProviceList();
    private static ArrayList<ArrayList<City>> cityList = MyApplication.getCityList();
    private static ArrayList<ArrayList<ArrayList<County>>> countyList = MyApplication.getCountyList();

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
                .withAspectRatio(1,1)//int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .scaleEnabled(true)//裁剪是否可放大缩小图片 true or false
                .withAspectRatio(1,1)
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
                .withAspectRatio(1,1)//int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .scaleEnabled(true)//裁剪是否可放大缩小图片 true or false
                .compress(true)
                .isCamera(false)// 是否显示拍照按钮
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .previewImage(false)// 是否可预览图片
                .circleDimmedLayer(isheader)
                .showCropFrame(!isheader)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(!isheader)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
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

    public void ShowTime(Activity activity,final TextView textView){
        Calendar startDate = Calendar.getInstance();
        startDate.set(1999,1,1);
        Calendar endDate = Calendar.getInstance();
        TimePickerView timePicker = new TimePickerBuilder(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                textView.setText(simpleDateFormat.format(date));
            }
        })
                .setDate(endDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)
                .build();
        //精准到秒
//        timePicker.setDate(Calendar.getInstance());
        timePicker.show();
    }
    // 弹出条件选择器
    public void ShowType(Activity activity, final ArrayList<ArticleType> dList, final TextView view) {
      OptionsPickerView pvOptions = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
          @Override
          public void onOptionsSelect(int options1, int options2, int options3, View v) {
//              //返回的分别是三个级别的选中位置
//              String tx = options1Items.get(options1).getPickerViewText()
//                      + options2Items.get(options1).get(option2)
//                      + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
//              tvOptions.setText(tx);
              if (dList != null && !dList.isEmpty()) {
                  String strBankName = dList.get(options1).getPickerViewText();
                  view.setText(strBankName);
                  //        mSwipeView.setRefreshing(true);
              }
          }
      })
              .setDividerColor(Color.BLACK)
              .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
              .setContentTextSize(20)//设置文字大小 滚动文字大小
              .setSubmitColor(Color.parseColor("#386FFE"))//确定按钮文字颜色
              .setCancelColor(Color.parseColor("#386FFE"))//取消按钮文字颜色
              .setOutSideCancelable(false)// 点击屏幕，点在控件外部范围时，是否取消显示
              .build();

      pvOptions.setPicker(dList);
      pvOptions.show();
    }

    // 弹出省市区级选择器
    public void ShowCity(Activity activity, final TextView view) {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    String provinceName = proviceList.get(options1).getPickerViewText();
                    String cityName =  cityList.get(options1).get(options2).getPickerViewText();
                    String countyName = countyList.get(options1).get(options2).get(options3).getPickerViewText();
                    view.setText(String.format("%s%s%s",provinceName,cityName,countyName));
//                view.setText(provinceName+"省"+cityName+"市"+countyName);
            }
        })

                .setDividerColor(Color.BLACK)
                .setTitleText("选择地区")   //标题文字
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小 滚动文字大小
                .setSubmitColor(Color.parseColor("#386FFE"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#386FFE"))//取消按钮文字颜色
                .setOutSideCancelable(false)// 点击屏幕，点在控件外部范围时，是否取消显示
                .setLabels("", "", "")//设置选择的三级单位
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setCyclic(false, false, false)//循环与否
                .isDialog(false)//是否显示为对话框样式
                .build();

        pvOptions.setPicker(proviceList,cityList,countyList);//条件选择器

        pvOptions.show();
    }

    // 弹出省市区级选择器
    public static void ShowCityNoCounty(Activity activity, final TextView view) {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                String provinceName = proviceList.get(options1).getPickerViewText();

                String cityName =  cityList.get(options1).get(options2).getPickerViewText();
                if (cityName.length()>5){
                    cityName = cityName.substring(0,4)+"...";
                }
//                String countyName = countyList.get(options1).get(options2).get(options3).getPickerViewText();
                view.setText(String.format("%s",cityName));

            }
        })

                .setDividerColor(Color.BLACK)
                .setTitleText("选择地区")   //标题文字
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小 滚动文字大小
                .setSubmitColor(Color.parseColor("#386FFE"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#386FFE"))//取消按钮文字颜色
                .setOutSideCancelable(false)// 点击屏幕，点在控件外部范围时，是否取消显示
                .setLabels("", "","")//设置选择的三级单位
                .setSelectOptions(0, 0)  //设置默认选中项
                .setCyclic(false, false, false)//循环与否
                .isDialog(false)//是否显示为对话框样式
                .build();

        pvOptions.setPicker(proviceList,cityList);//条件选择器

        pvOptions.show();
    }
}
