package com.example.habin.lostpropertyproject.Presenter.activity.mine;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.EditEmailContract;
import com.example.habin.lostpropertyproject.Util.UiUtils;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/2/2219:49
 * Email:739115041@qq.com
 */
public class EditEmailPresenter extends RxPresenter<EditEmailContract.View> implements EditEmailContract.Presenter {
    @Override
    public void updateEmail(String email) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", MyApplication.getUserId(UiUtils.getContext()));
        hashMap.put("email",email);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.UpdateInfo, this, hashMap, HttpItem.class);
    }
}
