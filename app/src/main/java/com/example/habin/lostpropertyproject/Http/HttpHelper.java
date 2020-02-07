package com.example.habin.lostpropertyproject.Http;

public class HttpHelper {

    public enum TaskType {
        Setting,  // 配置
        //----------登陆模块------------
        Login, //登陆
        Regin, //注册
        Logout, //注销

        UploadPhoto,  //上传图片


    }

    public static String getMethod(TaskType type) {
        String method = "";
        switch (type) {
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
            case UploadPhoto:
                method = "user/uploadPhoto";
                break;

        }
        return method;
    }
}
