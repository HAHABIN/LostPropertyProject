package com.example.habin.lostpropertyproject.Presenter.activity;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.RecordDtailsContract;
import com.example.habin.lostpropertyproject.Ui.activity.RecordDetailsActivity;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/3/622:08
 * Email:739115041@qq.com
 */
public class RecordDtailsPresenter extends RxPresenter<RecordDtailsContract.View> implements RecordDtailsContract.Presenter {
    @Override
    public void updateArticle(int id, int recordStatus) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("id",id);
        hashMap.put("recordStatus",recordStatus);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.updateArticleStatus,this,hashMap, HttpItem.class);
    }
}
