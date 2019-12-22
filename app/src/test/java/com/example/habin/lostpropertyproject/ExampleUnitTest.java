package com.example.habin.lostpropertyproject;

import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Model.Impl.LandModelImpl;
import com.example.habin.lostpropertyproject.Base.BaseObserver;

import org.junit.Test;

import io.reactivex.disposables.Disposable;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        LandModelImpl landModel = new LandModelImpl();
        landModel.login("admin", "admin", new BaseObserver<BaseResponse>() {

            @Override
            protected void OnSuccess(BaseResponse baseResponse) {

            }

            @Override
            protected void OnFail(Throwable e) {

            }
        });
    }
}