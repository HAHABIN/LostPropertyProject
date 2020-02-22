package com.example.habin.lostpropertyproject.Presenter.activity.mine;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.EditGenderContract;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.EditNicknameContract;
import com.example.habin.lostpropertyproject.Util.UiUtils;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/2/2219:49
 * Email:739115041@qq.com
 */
public class EditNickNamePresenter extends RxPresenter<EditNicknameContract.View> implements EditNicknameContract.Presenter {

    @Override
    public void updateNickName(String gender) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", MyApplication.getUserId(UiUtils.getContext()));
        hashMap.put("gender",gender);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.UpdateInfo, this, hashMap);
    }
}
