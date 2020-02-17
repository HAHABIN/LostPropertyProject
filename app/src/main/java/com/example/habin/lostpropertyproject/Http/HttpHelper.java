package com.example.habin.lostpropertyproject.Http;

public class HttpHelper {

    public enum TaskType {
        Setting,  // 配置
        //----------登陆模块------------
        Login, //登陆
        Regin, //注册
        Logout, //注销
        UpdatePasswordAuth, //修改密码

        //---------用户信息模块
        UpdateInfo,//修改用户信息
        UploadPhoto,  //上传图片
        QueryCity //获取省市级地址
    }

    public static String getMethod(TaskType type) {
        String method = "";
        switch (type) {
            case QueryCity:
                method = "chinaAddress/City";
                break;
            case Setting:
                method = "settings/get";
                break;
            case Login:
                method = "loadadmin/logincheck";
                break;
            case Regin:
                method = "loadadmin/regincheck";
                break;
            case Logout:
                method = "user/logout";
                break;
            case UpdateInfo:
                method = "user/updateInfo";
                break;
            case UpdatePasswordAuth:
                method = "loadadmin/updatePasswordAuth";
                break;
            case UploadPhoto:
                method = "saveToImgByStr/uploadPhoto";
                break;


        }
        return method;
    }
}
