package com.example.habin.lostpropertyproject.Presenter.activity.land;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.EditNicknameContract;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.EditPasswordContract;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/2/2220:02
 * Email:739115041@qq.com
 */
public class EditPasswordPresenter extends RxPresenter<EditPasswordContract.View> implements EditPasswordContract.Presenter {
    @Override
    public void UpdatePasswordAuth(String username, String password, String newpassword) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username", username);
        hashMap.put("password", password);
        hashMap.put("newpassword", newpassword);

        HttpClient.getInstance().startTask(HttpHelper.TaskType.UpdatePasswordAuth, this, hashMap, HttpItem.class);
    }
}
