package com.example.habin.lostpropertyproject;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.example.habin.lostpropertyproject.Bean.entity.ArticleTypeEntity;
import com.example.habin.lostpropertyproject.Bean.entity.City;
import com.example.habin.lostpropertyproject.Bean.entity.County;
import com.example.habin.lostpropertyproject.Bean.entity.PersonInfoEntity;
import com.example.habin.lostpropertyproject.Bean.entity.Province;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Ui.activity.RecordDetailsActivity;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


import java.util.ArrayList;

import static com.example.habin.lostpropertyproject.Http.Constants.APP_ID;
import static com.example.habin.lostpropertyproject.Http.Constants.APP_KEY;

/**
 * Create by HABIN on 2019/11/616:21
 * Email:739115041@qq.com
 */
public class MyApplication extends Application {


    private static final String TAG = MyApplication.class.getName();;

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
        //
        MultiDex.install(this);
        //初始化友盟推送
        initUm();

        //配置LitePal数据库
//        LitePal.initialize(this);
        ToastUtils.init(this);
        //初始化省级列表
        initProvice();
    }

    private void initUm() {
        UMConfigure.init(context,Constants.UM_KEY,"Umeng",UMConfigure.DEVICE_TYPE_PHONE,Constants.UM_MESSAGE_SECRET);
        UMShareAPI.get(context);
        PushAgent mPushAgent = PushAgent.getInstance(context);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG,"注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
        //自定义通知栏打开动作
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Log.i(TAG,"返回消息：-------->  " + msg.custom);
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.IS_SHOW,false);
                bundle.putString("id",msg.custom);
                bundle.putSerializable(Constants.ACTICLEINFO_DATA,null);
                Intent intent = new Intent();

                if (bundle!=null) {
                    intent.putExtras(bundle);
                }
                intent.setClass(context,RecordDetailsActivity.class);
                //开启了一个新的任务队列，且其位于队列的开始的模式打开。
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    //各个平台的配置
    {
        //QQ
        PlatformConfig.setQQZone(APP_ID, APP_KEY);

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
