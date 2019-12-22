package com.example.habin.lostpropertyproject.Model;

import com.example.habin.lostpropertyproject.Base.BaseContract;
import com.example.habin.lostpropertyproject.Base.BaseObserver;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;

/**
 * Create by HABIN on 2019/11/4
 * Time：23:08
 * Email:739115041@qq.com
 * 登录、注册 数据逻辑层接口
 */
public interface ILandModel extends BaseContract.BaseModel {

    /**
     * 用户登陆
     */
    void login(String username, String password,BaseObserver<BaseResponse> baseObserver);

    /**
     * 用户注册
     */
    void signup(String username, String password, String mail,BaseObserver<BaseResponse> baseObserver);




}
