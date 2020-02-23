package com.example.habin.lostpropertyproject.Presenter.fragment;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.ToClaimListContract;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/2/2312:04
 * Email:739115041@qq.com
 */
public class ToClainListPresenter extends RxPresenter<ToClaimListContract.View> implements ToClaimListContract.Presenter {

    @Override
    public void QueryArticleInfo(String address, int status, int pageNo, int pageSize) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (address != null) {
            hashMap.put("addressContent", address);
        }
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);
        hashMap.put("status", status);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.QueryArticleInfo, this, hashMap);
    }
}
