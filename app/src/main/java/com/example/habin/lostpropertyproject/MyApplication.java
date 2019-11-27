package com.example.habin.lostpropertyproject;

import android.app.Application;
import android.content.Context;

/**
 * Create by HABIN on 2019/11/616:21
 * Email:739115041@qq.com
 */
public class MyApplication extends Application {

    public static MyApplication application;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

}
