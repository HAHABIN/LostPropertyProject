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
        QueryCity, //获取省市级地址

        //发布物品信息模块
        InsertArInfo,  //发布信息
        QueryArticleInfo, //查询获取信息列表
        SearchArInfo, //搜索信息列表
        queryType,
        updateArticle, //更新物品信息
        updateArticleStatus,//更新物品状态

        AddComment,//添加评论
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
            case InsertArInfo:
                method = "articleInfo/InsertArInfo";
                break;
            case QueryArticleInfo:
                method = "articleInfo/QueryArticleInfo";
                break;
            case SearchArInfo:
                method = "articleInfo/searchArInfo";
                break;
            case queryType:
                method = "articletype/queryAll";
                break;
            case updateArticle:
                method = "articleInfo/updateArticle";
                break;
            case updateArticleStatus:
                method = "articleInfo/updateArticleStatus";
                break;
            case AddComment:
                method = "Comment/addComment";
                break;
        }
        return method;
    }
}
