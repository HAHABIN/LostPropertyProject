package com.example.habin.lostpropertyproject.Http;

import com.example.habin.lostpropertyproject.Common.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
    private static final int DEFAULT_CONNECT_TIME = 10;
    private static final int DEFAULT_WRITE_TIME = 30;
    private static final int DEFAULT_READ_TIME = 30;
    private OkHttpClient okHttpClient;


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
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)//连接超时时间
                .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)//设置写操作超时时间
                .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)//设置读操作超时时间
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)//设置使用okhttp网络请求
                .baseUrl(Constants.BASE_URL)//设置服务器路径
                .addConverterFactory(GsonConverterFactory.create())//添加转化库，默认是Gson
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加回调库，采用RxJava
                .build();

    }

    public Apiservice Apiservice() {
        return retrofit.create(Apiservice.class);
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
