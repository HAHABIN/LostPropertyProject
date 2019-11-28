package com.example.habin.lostpropertyproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Presenter.LandPresenter;
import com.example.habin.lostpropertyproject.Presenter.contract.LandContract;
import com.example.habin.lostpropertyproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by HABIN on 2019/11/4
 * Time：22:46
 * Email:739115041@qq.com
 * <p>
 * 用户登录、注册Activity
 * <p>
 * 该类为V层的UI处理类，实现LandContract.View接口。
 * 主要负责Presenter，Model的初始化，以及UI的更新操作。
 */
public class LandActivity extends BaseMVPActivity<LandContract.Presenter> implements LandContract.View {


    @BindView(R.id.login_et_username)
    EditText loginEtUsername;
    @BindView(R.id.login_et_password)
    EditText loginEtPassword;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;

    protected int getLayoutId() {
        return R.layout.activity_land;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    /**
     * 执行登陆动作
     */
    public void login() {
        String username = loginEtUsername.getText().toString();
        String password = loginEtPassword.getText().toString();
        mPresenter.login(username, password);
    }

    /**
     * 执行注册动作
     */
    public void sign() {
        String email = null;//emailET.getText().toString();
        String username = null;//usernameET.getText().toString();
        String password = null; //passwordET.getText().toString();
        String rpassword = null;//rpasswordET.getText().toString();
        mPresenter.signup(username, password, email);
    }


    //绑定
    @Override
    protected LandContract.Presenter bindPresenter() {
        return new LandPresenter();
    }

    // 成功显示
    @Override
    public void landSucess(BaseResponse baseResponse) {
        Log.d("TESTDDD", "成功 ");
        Toast.makeText(mActivity, "TTT", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void landFail(String errMsg) {
        Toast.makeText(mActivity, "失败原因"+errMsg, Toast.LENGTH_SHORT).show();
    }

    //返回结果
    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Throwable e) {
        Log.d(TAG, "onFailure: 登录失败"+e);
        Toast.makeText(mActivity, "登录失败", Toast.LENGTH_SHORT).show();

    }



    @OnClick(R.id.login_btn_login)
    public void onViewClicked() {
        login();
    }
}
