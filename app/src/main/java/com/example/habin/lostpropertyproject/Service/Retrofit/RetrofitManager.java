package com.example.habin.lostpropertyproject.Service.Retrofit;

import com.example.habin.lostpropertyproject.Common.Constants;
import com.example.habin.lostpropertyproject.Service.Apiservice;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by HABIN on 2019/11/5
 * Time：23:51
 * Email:739115041@qq.com
 *
 * RetrofitManager管理器的创建，
 * 保证Retrofit在类中只有一个实例，避免请求体的多次创建。
 */


public class RetrofitManager {
    private volatile static RetrofitManager retrofitManager;
    private Retrofit retrofit;

    //无参的单利模式
    public static RetrofitManager getSingleton() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }


    //无参的构造方法
    private RetrofitManager() {
        initRetrofitManager();
    }

    //构造方法创建Retrofit实例
    private void initRetrofitManager() {
        retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Apiservice Apiservice() {
        return retrofit.create(Apiservice.class);
    }
}
