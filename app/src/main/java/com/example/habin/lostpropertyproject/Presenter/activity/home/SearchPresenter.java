package com.example.habin.lostpropertyproject.Presenter.activity.home;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.SearchContract;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/2/2312:48
 * Email:739115041@qq.com
 */
public class SearchPresenter extends RxPresenter<SearchContract.View> implements SearchContract.Presenter {
    @Override
    public void SearchInfo(String description, int pageNo, int pageSize) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("description", description);
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.QueryArticleInfo, this, hashMap);
    }
}
