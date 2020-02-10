package com.example.habin.lostpropertyproject;

import android.app.Application;
import android.content.Context;

import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.ToastUtils;

/**
 * Create by HABIN on 2019/11/616:21
 * Email:739115041@qq.com
 */
public class MyApplication extends Application {

    public static MyApplication application;
    private static Context context;
    public static int userId;
    private static PersonInfoEmtity.ResultBean userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
        ToastUtils.init(this);
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }


    public static int getUserId(Context context){
        try {
            userId = SharedPreferenceHandler.getUserId(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }
    public static PersonInfoEmtity.ResultBean getUserInfo(Context context){
        try {
            userInfo = SharedPreferenceHandler.getUserInfo(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static boolean isLogin(Context context){
        return getUserId(context) == 0 ;
    }
}
