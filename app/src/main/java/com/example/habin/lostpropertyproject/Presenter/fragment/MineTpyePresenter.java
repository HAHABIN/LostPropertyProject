package com.example.habin.lostpropertyproject.Presenter.fragment;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.MinePageContract;

import java.util.HashMap;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 我的碎片P层
 */
public class MineTpyePresenter extends RxPresenter<MinePageContract.View> implements MinePageContract.Presenter {


    @Override
    public void getData(int userId) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",userId);
        HttpClient.getSingleton().startTask(HttpHelper.TaskType.QueryArticleInfo,this,hashMap, ArticleInfoEntity.class);
    }

    @Override
    public void taskFinished(HttpHelper.TaskType type, HttpItem item) {
        super.taskFinished(type, item);
        mView.onSuccess(type,item);
    }

    @Override
    public void taskError(HttpHelper.TaskType type, ApiError error) {
        super.taskError(type, error);
        mView.onFailure(type,error);
    }
}
