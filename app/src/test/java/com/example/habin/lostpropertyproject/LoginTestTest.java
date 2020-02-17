package com.example.habin.lostpropertyproject;

import com.example.habin.lostpropertyproject.Bean.Local.City.Province;
import com.example.habin.lostpropertyproject.Util.JsonUtil;

import org.junit.Test;

import java.util.ArrayList;


/**
 * Create by HABIN on 2019/11/621:14
 * Email:739115041@qq.com
 */

public class LoginTestTest {
    @Test
    public void test() {
        ArrayList<Province> provinceArrayList = JsonUtil.JsontoProvince(MyApplication.getContext());
        for (Province province :provinceArrayList){
            System.out.println(province.toString());
        }
//        landDao landModel = new landDao();
//        System.out.println("------------------test------------");
//        landModel.login("admin", "admin", new BaseObserver<BaseResponse>() {
//            @Override
//            public void OnSuccess(BaseResponse baseResponse) {
//                System.out.print("登陆成功");
//            }
//
//            @Override
//            public void OnFail(Throwable e) {
//                System.out.print("登陆失败");
//            }
//
//            @Override
//            public void OnCompleted() {
//
//            }
//
//            @Override
//            public void OnDisposable(Disposable d) {
//
//            }
//        });

    }
}