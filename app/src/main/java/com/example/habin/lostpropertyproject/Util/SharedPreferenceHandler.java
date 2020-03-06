package com.example.habin.lostpropertyproject.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.habin.lostpropertyproject.Bean.entity.PersonInfoEntity;
import com.google.gson.Gson;

/**
 * Create by HABIN on 2019/12/2220:26
 * Email:739115041@qq.com
 */
public class SharedPreferenceHandler {


    public static final String CONFIG_FILE = "lostProperty_config_file";//sharedPreference文件名
    final static String STRING_SAVEUserInfo = "UserInfo";//用户信息
    final static String STRING_SAVEUserName = "UserName";
    final static String STRING_SAVEUserId = "UserId";//用户id
    final static String STRING_SAVESetting = "Setting";//设置

    public enum InfoType {
        NickName,  // 昵称
        Email, //邮箱
        Gender,
        ProfileImg,
        HelpTimes;
    }
    /**
     * 保存用户信息
     * @param context
     * @param userinfo
     * @throws Exception
     */
    public static void saveUserInfo(Context context, PersonInfoEntity.ResultBean userinfo){
        SharedPreferences settings = context.getSharedPreferences(STRING_SAVEUserInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson=new Gson();
        String string = gson.toJson(userinfo);
        editor.putString("userinfo",string);
        editor.apply();
    }
    public static PersonInfoEntity.ResultBean getUserInfo(Context context) {
        SharedPreferences settings = context.getSharedPreferences(STRING_SAVEUserInfo, Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String object = settings.getString("userinfo", null);
        PersonInfoEntity.ResultBean userinfo = gson.fromJson(object,PersonInfoEntity.ResultBean.class);
        return userinfo;
    }
    public static void saveNickName(Context context,String NickName){
        SharedPreferences settings = context.getSharedPreferences(STRING_SAVEUserInfo, Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String object = settings.getString("userinfo", null);
        //获得用户信息
        PersonInfoEntity.ResultBean userinfo = gson.fromJson(object,PersonInfoEntity.ResultBean.class);
        userinfo.setNickname(NickName);
        //修改用户信息
        SharedPreferences.Editor editor = settings.edit();
        String string = gson.toJson(userinfo);
        editor.putString("userinfo",string);
        editor.apply();
    }
    public static void saveInfo(Context context,String Info, InfoType type){
        SharedPreferences settings = context.getSharedPreferences(STRING_SAVEUserInfo, Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String object = settings.getString("userinfo", null);
        //获得用户信息
        PersonInfoEntity.ResultBean userinfo = gson.fromJson(object,PersonInfoEntity.ResultBean.class);
        switch (type){
            case NickName:
                userinfo.setNickname(Info);
                break;
            case Gender:
                userinfo.setGender(Info);
                break;
            case Email:
                userinfo.setEmail(Info);
                break;
            case HelpTimes:
                userinfo.setHelpTimes(Integer.parseInt(Info));
                break;
            case ProfileImg:
                userinfo.setProfileImg(Info);
                break;
        }

        //修改用户信息
        SharedPreferences.Editor editor = settings.edit();
        String string = gson.toJson(userinfo);
        editor.putString("userinfo",string);
        editor.apply();
    }
    public static void setUserName(Context context,String username){
        SharedPreferences sp = context.getSharedPreferences(STRING_SAVEUserName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username",username);
        editor.apply();
    }

    public static String getUserName(Context context){
        SharedPreferences sp = context.getSharedPreferences(STRING_SAVEUserName,Context.MODE_PRIVATE);
        return sp.getString("username",null);
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
