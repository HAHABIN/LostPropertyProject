package com.example.habin.lostpropertyproject.Presenter.fragment;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.HomePageContract;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 首页碎片Presenter层
 */
public class HomePagePresenter extends RxPresenter<HomePageContract.View> implements HomePageContract.Presenter {

    @Override
    public void getData(String username, String password) {

    }

    @Override
    public void getProviceList() {
        getCityData( -1, 1);
    }

    @Override
    public void getCityList(int pid) {
        getCityData( pid,2);
    }

    @Override
    public void getCountyList(int pid) {
        getCityData(pid,3);
    }

    private void getCityData(int pid, int type) {
        HashMap<String, Object> hashMap = new HashMap<>();
//        if (id != 0) {
//            hashMap.put("id", id);
//        }
        if (pid != 0) {
            hashMap.put("pid", pid);
        }
//        if (cityname != null) { //hashMap的Value切记不能填null
//            hashMap.put("cityname", cityname);
//        }
        if (type != 0) {
            hashMap.put("type", type);
        }
        HttpClient.getSingleton().startTask(HttpHelper.TaskType.QueryCity, this, hashMap);
    }

    @Override
    public void taskFinished(HttpHelper.TaskType type, JSONObject object) {
        super.taskFinished(type, object);
        mView.onSuccess(type,object);
    }

    @Override
    public void taskError(HttpHelper.TaskType type, ApiError error) {
        super.taskError(type, error);
        mView.onFailure(type,error);
    }
}
