package com.example.habin.lostpropertyproject.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;

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
    public static void saveUserInfo(Context context, PersonInfoEmtity.ResultBean userinfo) throws Exception{
        SharedPreferences settings = context.getSharedPreferences(STRING_SAVEUserInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("userId", userinfo.getUserId());
        editor.putString("name",userinfo.getName());
        editor.putString("profileImg",userinfo.getProfileImg());
        editor.putString("email",userinfo.getEmail());
        editor.putInt("userType",userinfo.getUserType());
        editor.putLong("createTime",userinfo.getCreateTime());
        editor.putLong("lastEditTime",userinfo.getLastEditTime());
        editor.putInt("helpTimes",userinfo.getHelpTimes());
        editor.apply();
    }
    public static PersonInfoEmtity.ResultBean getUserInfo(Context context) throws Exception{
        SharedPreferences settings = context.getSharedPreferences(STRING_SAVEUserInfo, Context.MODE_PRIVATE);
        PersonInfoEmtity.ResultBean userinfo = new PersonInfoEmtity.ResultBean();
        userinfo.setUserId(settings.getInt("userId", 0));
        userinfo.setName(settings.getString("name", null));
        userinfo.setProfileImg(settings.getString("profileImg", null));
        userinfo.setEmail(settings.getString("email", null));
        userinfo.setUserType(settings.getInt("userType", 0));
        userinfo.setCreateTime(settings.getLong("createTime", 0));
        userinfo.setLastEditTime(settings.getLong("lastEditTime", 0));
        userinfo.setHelpTimes(settings.getInt("helpTimes", 0));
        return userinfo;
    }
    public static void setUserId(Context context,int userId){
        SharedPreferences userid = context.getSharedPreferences(STRING_SAVEUserId, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userid.edit();
        editor.putInt("userId",userId);
        editor.apply();
    }
    public static int  getUserId(Context context) throws Exception{
        SharedPreferences userid = context.getSharedPreferences(STRING_SAVEUserId, Context.MODE_PRIVATE);
        return userid.getInt("userId",0);
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
