package com.example.habin.lostpropertyproject;

import android.app.Application;
import android.content.Context;

import com.example.habin.lostpropertyproject.Bean.entity.ArticleTypeEntity;
import com.example.habin.lostpropertyproject.Bean.entity.City;
import com.example.habin.lostpropertyproject.Bean.entity.County;
import com.example.habin.lostpropertyproject.Bean.entity.PersonInfoEntity;
import com.example.habin.lostpropertyproject.Bean.entity.Province;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.ToastUtils;


import java.util.ArrayList;

/**
 * Create by HABIN on 2019/11/616:21
 * Email:739115041@qq.com
 */
public class MyApplication extends Application {

    public static MyApplication application;
    private static Context context;
    public static int userId;
    private static PersonInfoEntity.ResultBean userInfo;

    private static ArrayList<Province> options1Items;
    private static ArrayList<ArrayList<City>> options2Items;
    private static ArrayList<ArrayList<ArrayList<County>>> options3Items;
    private static ArrayList<ArticleTypeEntity.ResultBean> jsonType;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
        HttpClient.getInstance().setContext(this);
        //配置LitePal数据库
//        LitePal.initialize(this);
        ToastUtils.init(this);
        //初始化省级列表
        initProvice();
    }

    private void initProvice() {
        //获取类型分类
        jsonType = JsonUtil.JsontoListT(getContext(), "article.json", ArticleTypeEntity.ResultBean.class);
        if (options1Items == null) {
            options1Items = new ArrayList<>(); //省
            options2Items = new ArrayList<>();//市
            options3Items = new ArrayList<>();
        }
        //获取省份数据
        ArrayList<Province> jsonProvince = JsonUtil.JsontoListT(getContext(),"provice.json",Province.class);
//        //添加全部
//        options1Items.add(new Province(5000,"全国",1));
        //添加省份
        options1Items = jsonProvince;
        for (int i = 0; i < jsonProvince.size(); i++) {
            ArrayList<City> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<County>> CountyList = new ArrayList<>();//该省的所有地区列表（第三级）
            //获取当前省份中的城市列表 ct代表当前简写
            ArrayList<City> ctCityList = jsonProvince.get(i).getCityList();
            //遍历城市列表
            for (int j = 0; j < ctCityList.size(); j++) {
                //获取当前省份遍历中的城市
                City city = ctCityList.get(j);
                //添加城市
                CityList.add(city);
                //该城市的所有地区列表
                ArrayList<County> ctCountList = new ArrayList<>();
                //遍历当前城市所有区县
                for (int k = 0; k < city.getCountyList().size(); k++) {
                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (city.getCountyList() == null || city.getCountyList().size() == 0) {
                        ctCountList.add(new County(""));
                    } else {
                        ctCountList.add(city.getCountyList().get(k));
                    }
                }
                //添加区县级数据
                CountyList.add(ctCountList);
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(CountyList);
        }
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }


    public static int getUserId(Context context) {
        try {
            userId = SharedPreferenceHandler.getUserId(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static PersonInfoEntity.ResultBean getUserInfo(Context context) {
        try {
            userInfo = SharedPreferenceHandler.getUserInfo(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static boolean isLogin(Context context) {
        return getUserId(context) == 0;
    }

    public static ArrayList<Province> getProviceList() {
        return options1Items;
    }

    public static ArrayList<ArrayList<City>> getCityList() {
        return options2Items;
    }
    public static ArrayList<ArrayList<ArrayList<County>>> getCountyList() {
        return options3Items;
    }

    public static ArrayList<ArticleTypeEntity.ResultBean> getTypeList(){
        return jsonType;
    }
}
