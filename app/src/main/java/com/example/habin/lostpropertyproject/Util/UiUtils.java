package com.example.habin.lostpropertyproject.Util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;


/**
 *
 * Class desc: ui 操作相关封装
 */
public class UiUtils {

    /**
     * 获取上下文
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 获取资源操作类
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取字符串资源
     *
     * @param id 资源id
     * @return 字符串
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }

    /**
     * 获取字符串数组资源
     *
     * @param id 资源id
     * @return 字符串数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取颜色资源
     */
    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    /**
     * 获取颜色资源
     *
     * @param id    资源id
     * @param theme 样式
     * @return
     */
    public static int getColor(int id, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(id, theme);
        }
        return getResources().getColor(id);
    }

    /**
     * 获取drawable资源
     *
     * @param id 资源id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    /**
     * 加载布局（使用View方式）
     *
     * @param resource 布局资源id
     * @return View
     */
    public static View inflate(int resource) {
        return View.inflate(getContext(), resource, null);
    }

    /** 显示不带 null 的字符 */
    public static String show(String text){
        return text != null ? text : "";
    }



    public static Bitmap CompressBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;                       //采样率压缩
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);                   //缩放法压缩
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    //判断是否有网络
    public static boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isAvailable();
        }
        return false;
    }

    public static void GildeLoad(Context context, View view, String imgStr){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_placeholder)//图片加载出来前，显示的图片
                .fallback( R.drawable.ic_placeholder) //url为空的时候,显示的图片
                .error(R.drawable.ic_picfail);//图片加载失败后，显示的图片

        Glide.with(context)
                .load(imgStr)
                .apply(options)
                .into((ImageView) view);
    }
    public static void SavePic(Context context,View view,String imgStr){

        Glide.with(view.getContext())
                .load(Constants.BASE_URL+imgStr)
                .into((ImageView) view);
    }

    /**
     * 隐藏软键盘(无输入框或者说无法获取输入框。比如，微信支付时处于未登录状态，此时输入框
     * 是微信的，返回再隐藏键盘)
     * @param context
     */
    public static void hideSoftKeyboardNoView(@NonNull Activity context)
    {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 隐藏软键盘(无输入框或者说无法获取输入框。比如，微信支付时处于未登录状态，此时输入框
     * 是微信的，返回再隐藏键盘)
     * @param context
     */
    public static void hideSoftKeyboard(@NonNull Activity context)
    {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘(有输入框)
     * @param context
     * @param mEditText
     */
    public static void hideSoftKeyboard(@NonNull Context context,
                                        @NonNull EditText mEditText)
    {
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

}

