package com.example.habin.lostpropertyproject.Presenter.activity.mine;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.UserInfoContract;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.UiUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/2/1322:04
 * Email:739115041@qq.com
 * 用户信息P层  调度处理类
 */
public class UserInfoPresenter extends RxPresenter<UserInfoContract.View> implements UserInfoContract.Presenter{

    @Override
    public void UploadPhoto(String imgStr) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("imgStr", imgStr);
        hashMap.put("resourceType", "avatar");
        HttpClient.getInstance().startTask(HttpHelper.TaskType.UploadPhoto, this, hashMap);
    }

    @Override
    public void updateInfo(String str) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", MyApplication.getUserId(UiUtils.getContext()));
        hashMap.put("profileimg",str);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.UpdateInfo, this, hashMap);
    }

}
