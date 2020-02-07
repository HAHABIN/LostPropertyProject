package com.example.habin.lostpropertyproject.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;
import com.google.gson.Gson;

/**
 * Create by HABIN on 2019/12/2220:26
 * Email:739115041@qq.com
 */
public class SharedPreferenceHandler {


    public static final String CONFIG_FILE = "lostProperty_config_file";//sharedPreference文件名
    final static String STRING_SAVEUserInfo = "UserInfo";//用户信息
    final static String STRING_SAVEUserId = "UserInfo";//用户id
    final static String STRING_SAVESetting = "Setting";//设置

    /**
     * 保存用户信息
     * @param context
     * @param userinfo
     * @throws Exception
     */
    public static void saveUserInfo(Context context, PersonInfoEmtity.ResultBean userinfo){
        SharedPreferences settings = context.getSharedPreferences(STRING_SAVEUserInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson=new Gson();
        String string = gson.toJson(userinfo);
        editor.putString("userinfo",string);
        editor.apply();
    }
    public static PersonInfoEmtity.ResultBean getUserInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(STRING_SAVEUserInfo, Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String object = settings.getString("userinfo", null);
        PersonInfoEmtity.ResultBean userinfo = gson.fromJson(object,PersonInfoEmtity.ResultBean.class);
        return userinfo;
    }
    public static void setUserId(Context context,int userId){
        SharedPreferences userid = context.getSharedPreferences(STRING_SAVEUserId, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userid.edit();
        editor.putInt("userId",userId);
        editor.apply();
    }
    public static int  getUserId(Context context){
        SharedPreferences userid = context.getSharedPreferences(STRING_SAVEUserId, Context.MODE_PRIVATE);
        return userid.getInt("userId",0);
    }

    public static void cleanUserInfo(Context context){
        saveUserInfo(context,null);
        setUserId(context,0);
    }

//    "userId": 1,
//            * "name": "1",
//            * "profileImg": null,
//            * "email": null,
//            * "gender": null,
//            * "userType": 3,
//            * "createTime": 1573016531000,
//            * "lastEditTime": 1573016534000,
//            * "helpTimes": 3


}
