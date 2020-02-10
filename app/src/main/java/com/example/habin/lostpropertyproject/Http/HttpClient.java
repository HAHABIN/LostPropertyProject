package com.example.habin.lostpropertyproject.Http;

import android.content.Context;
import android.text.TextUtils;

import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;
import com.example.habin.lostpropertyproject.Common.Constants;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;

import java.util.ArrayList;
import java.util.HashMap;
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
 * HttpClient管理器的创建，
 * 保证Retrofit在类中只有一个实例，避免请求体的多次创建。
 */
public class HttpClient {

    private volatile static HttpClient retrofitManager;
    private Retrofit retrofit;
    private static final int DEFAULT_CONNECT_TIME = 10;
    private static final int DEFAULT_WRITE_TIME = 30;
    private static final int DEFAULT_READ_TIME = 30;
    private OkHttpClient okHttpClient;
    private ApiServer apiServer;

    private Context mContext;
    private ArrayList<HttpTask> mTaskArray;
    private int userId ;

    //用户信息
    private PersonInfoEmtity.ResultBean mPersonInfo;

    //无参的单利模式
    public static HttpClient getSingleton() {
        if (retrofitManager == null) {
            synchronized (HttpClient.class) {
                retrofitManager = new HttpClient();
            }
        }
        return retrofitManager;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void startTask(HttpHelper.TaskType type, TaskListener listener, HashMap<String, Object> params) {

        if (listener != null) listener.taskStarted(type);

        HttpTask task = new HttpTask(mContext, apiServer, listener).load(type, params);
        mTaskArray.add(task);
    }

    public void startTask(HttpHelper.TaskType type, TaskListener listener, HashMap<String, Object> params, Class item) {
        if (listener != null) listener.taskStarted(type);
        HttpTask task = new HttpTask(mContext, apiServer, listener, item).load(type, params);
        mTaskArray.add(task);
    }


    //无参的构造方法
    private HttpClient() {
        initRetrofitManager();
    }

    //构造方法创建Retrofit实例
    private void initRetrofitManager() {
        mTaskArray = new ArrayList<>();

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
        apiServer = retrofit.create(ApiServer.class);
    }

    public ApiServer Apiservice() {
        return retrofit.create(ApiServer.class);
    }



}
