package com.example.habin.lostpropertyproject;

import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Error.ExceptionHandle;
import com.example.habin.lostpropertyproject.Model.Impl.LandModelImpl;
import com.example.habin.lostpropertyproject.Model.Observer;


import org.junit.Test;

import io.reactivex.disposables.Disposable;


/**
 * Create by HABIN on 2019/11/621:14
 * Email:739115041@qq.com
 */

public class LoginTestTest {
    @Test
    public void test() {

        landDao landModel = new landDao();
        System.out.println("------------------test------------");
        landModel.login("admin", "admin", new Observer<BaseResponse>() {
            @Override
            public void OnSuccess(BaseResponse baseResponse) {
                System.out.print("登陆成功");
            }

            @Override
            public void OnFail(ExceptionHandle.ResponeThrowable e) {
                System.out.print("登陆失败");
            }

            @Override
            public void OnCompleted() {

            }

            @Override
            public void OnDisposable(Disposable d) {

            }
        });

    }
}