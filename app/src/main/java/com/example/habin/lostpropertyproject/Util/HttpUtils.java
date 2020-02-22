package com.example.habin.lostpropertyproject.Util;

import com.example.habin.lostpropertyproject.Http.Constants;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtils {
    //登录
    public static void loginWithOkHttp(String username,String password, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("loginUsernamet",username)
                .add("loginPassword",password)
                .build();
        Request request = new Request.Builder()
                .url(Constants.BASE_URL+Constants.USER_LOGIN)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //注册
   public static void registerWithOkHttp(String address,String username,String password,String kfmail,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("registerUsername",username)
                .add("registerPassword",password)
                .add("registerKfmail",kfmail)
                .build();
        Request request = new Request.Builder()
                .url(Constants.BASE_URL+address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}