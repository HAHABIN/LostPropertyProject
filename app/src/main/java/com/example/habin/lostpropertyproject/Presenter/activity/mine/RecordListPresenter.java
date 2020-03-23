package com.example.habin.lostpropertyproject.Presenter.activity.mine;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.RecordListContract;
import com.example.habin.lostpropertyproject.Ui.activity.mine.RecordListActivity;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/2/2218:46
 * Email:739115041@qq.com
 */
public class RecordListPresenter extends RxPresenter<RecordListContract.View> implements RecordListContract.Presenter {
    @Override
    public void QueryArticleInfo(int userId, int recordStatus) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", userId);
        hashMap.put("pageSize",100);
        if (recordStatus<4){
            hashMap.put("recordStatus", recordStatus);
        }
        HttpClient.getInstance().startTask(HttpHelper.TaskType.QueryArticleInfo, this, hashMap);
    }

}
