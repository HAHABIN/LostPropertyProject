package com.example.habin.lostpropertyproject.Presenter.activity;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.HttpTask;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.ReleaseContract;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/2/2013:43
 * Email:739115041@qq.com
 */
public class ReleasePresenter extends RxPresenter<ReleaseContract.View> implements ReleaseContract.Presenter {
    @Override
    public void InsertArInfo(HashMap<String, Object> hashMap) {
        HttpClient.getInstance().startTask(HttpHelper.TaskType.InsertArInfo,this,hashMap);
    }

    @Override
    public void UploadPhoto(String imgStr) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("imgStr", imgStr);
        hashMap.put("resourceType", "release");
        HttpClient.getInstance().startTask(HttpHelper.TaskType.UploadPhoto, this, hashMap);
    }

}
