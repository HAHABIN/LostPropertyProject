package com.example.habin.lostpropertyproject.Presenter.activity.land;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.PersonInfoEntity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.LandContract;


import java.util.HashMap;


/**
 * Create by HABIN on 2019/11/4
 * Time：22:53
 * Email:739115041@qq.com
 * P层的调度处理类
 * 主要负责调用View层以及Model层的方法或接口以实现调度的职责
 */
public class LandPresenter extends RxPresenter<LandContract.View> implements LandContract.Presenter{

    private String TAG="LandPresenter";


    @Override
    public void login(String username, String password) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username",username);
        hashMap.put("password",password);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.Login,this,hashMap, PersonInfoEntity.class);

    }

    @Override
    public void signup(String username, String password, String email) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username",username);
        hashMap.put("password",password);
        hashMap.put("email",email);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.Regin,this,hashMap, PersonInfoEntity.class);
    }

}
