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
    public void QueryArticleInfo(String address, int type, int status, int pageNo, int pageSize) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (address != null) {
            hashMap.put("addressContent", address);
        }
        if (type != 0) {
            hashMap.put("typeId", type);
        } else {
            hashMap.put("recordStatus",status);
        }

        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);

        HttpClient.getInstance().startTask(HttpHelper.TaskType.QueryArticleInfo, this, hashMap);
    }
}
