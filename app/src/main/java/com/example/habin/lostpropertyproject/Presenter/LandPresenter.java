package com.example.habin.lostpropertyproject.Presenter;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Model.Impl.LandModelImpl;
import com.example.habin.lostpropertyproject.Presenter.contract.LandContract;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * Create by HABIN on 2019/11/4
 * Time：22:53
 * Email:739115041@qq.com
 * P层的调度处理类
 * 主要负责调用View层以及Model层的方法或接口以实现调度的职责
 */
public class LandPresenter extends RxPresenter<LandContract.View> implements LandContract.Presenter{

    private String TAG="LandPresenter";

    private LandModelImpl mlandmodel = new LandModelImpl();

    @Override
    public void login(String username, String password) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username",username);
        hashMap.put("password",password);

        HttpClient.getSingleton().startTask(HttpHelper.TaskType.Login,this,hashMap, PersonInfoEmtity.class);

//
//        mlandmodel.login(username, password, new BaseObserver<BaseResponse>() {
//            @Override
//            public void OnSuccess(BaseResponse baseResponse) {
//                if (baseResponse.getSuccess()){
//                    mView.landSucess(baseResponse);
//                } else {
//                    mView.landFail(baseResponse.getErrMsg());
//                }
//
//            }
//
//            @Override
//            public void OnFail(Throwable e) {
//                Log.d(TAG, "OnFail: "+e);
//                mView.onFailure(e);
//            }
//
//        });
    }

    @Override
    public void signup(String username, String password, String mail) {
//        mlandmodel.signup(username,password,mail, new BaseObserver<BaseResponse>() {
//            @Override
//            protected void OnSuccess(BaseResponse baseResponse) {
//                if (baseResponse.getSuccess()){
//                    mView.landSucess(baseResponse);
//                } else {
//                    mView.landFail(baseResponse.getErrMsg());
//                }
//
//            }
//
//            @Override
//            protected void OnFail(Throwable e) {
//                Log.d(TAG, "OnFail: "+e);
//                mView.onFailure(e);
//            }
//        });
    }

    @Override
    public void taskError(HttpHelper.TaskType type, ApiError error) {
        super.taskError(type, error);
        mView.onFailure(type,error);
    }

    @Override
    public void taskFinished(HttpHelper.TaskType type, HttpItem item) {
        super.taskFinished(type, item);
        mView.landSucess(type,item);

    }


    @Override
    public void taskFinished(HttpHelper.TaskType type, JSONObject object) {
        super.taskFinished(type, object);
        mView.landSucess(type,object);

    }
}
