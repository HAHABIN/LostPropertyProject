package com.example.habin.lostpropertyproject;

import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Error.ExceptionHandle;
import com.example.habin.lostpropertyproject.Model.Impl.LandModelImpl;
import com.example.habin.lostpropertyproject.Model.Observer;

import org.junit.Test;

import io.reactivex.disposables.Disposable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        LandModelImpl landModel = new LandModelImpl();
        landModel.login("admin", "admin", new Observer<BaseResponse>() {
            @Override
            public void OnSuccess(BaseResponse baseResponse) {
                System.out.print("SS成功"+baseResponse.toString());
            }

            @Override
            public void OnFail(ExceptionHandle.ResponeThrowable e) {
                System.out.print("SS失败"+e);
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