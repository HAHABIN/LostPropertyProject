package com.example.habin.lostpropertyproject.ui.activity.Land;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.activity.land.LandPresenter;
import com.example.habin.lostpropertyproject.Presenter.activity.land.contract.LandContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.CodeUtils;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.ui.activity.MainActivity;

import org.json.JSONObject;

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



    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, LandActivity.class));
    }

    @BindView(R.id.login_et_username)
    EditText mEtUsername;
    @BindView(R.id.login_et_password)
    EditText mEtPassword;
    @BindView(R.id.login_btn_login)
    Button mBtnLogin;
    @BindView(R.id.login_et_mail)
    EditText mEtMail;
    @BindView(R.id.login_et_rpassword)
    EditText mEtRpassword;
    @BindView(R.id.login_tv_sign)
    TextView mTvloginSign;
    @BindView(R.id.land_tv_text)
    TextView mTvTitle;
    @BindView(R.id.login_ll_mail)
    LinearLayout mLlMail;
    @BindView(R.id.login_ll_rpassword)
    LinearLayout mLlRpassword;
    @BindView(R.id.ll_login_pane)
    LinearLayout llLoginPane;
    @BindView(R.id.tv_forget_pass)
    TextView mTvForgetPass;
    @BindView(R.id.et_phoneCodes)
    EditText mEtPhoneCodes;
    @BindView(R.id.iv_code)
    ImageView mIvCode;

    //是否是登陆操作
    private boolean isLogin = true;
    //保存登录名
    private String mUsername;
    //验证码
    private Bitmap bitmap;
    private String code;
    @Override
    protected boolean showTitle() {
        return true;
    }

    protected int getLayoutId() {
        return R.layout.activity_land;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleText("");
        setShowBack(View.VISIBLE);
        setBackOnClick().setOnClickListener(v -> finish());
        String username = SharedPreferenceHandler.getUserName(mContext);
        if (username != null) {
            mEtUsername.setText(username);
        }
        //初始化验证码图
        setCode();
    }

    public void setCode(){
        //获取工具类生成的图片验证码对象
        bitmap = CodeUtils.getInstance().createBitmap();
        //获取当前图片验证码的对应内容用于校验  并转化为小写
        code = CodeUtils.getInstance().getCode().toLowerCase();
        //获取需要展示图片验证码的ImageView
        mIvCode.setImageBitmap(bitmap);
    }

    /**
     * 执行登陆动作
     */
    public void login() {
        mUsername = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        if (mUsername.isEmpty()||password.isEmpty()){
            ToastUtils.show_s("帐号密码不能为空");
            return;
        }
        //获取输入验证码 统一转化为小写
        String inputcode = mEtPhoneCodes.getText().toString().trim().toLowerCase();
        if (inputcode.length()==0){
            ToastUtils.show_s(mContext,"验证码不能为空");
        }
        if (!inputcode.equals(code)){
            ToastUtils.show_s(mContext,"验证码错误，请输入正确验证码");
            return;
        }
        ProgressUtils.show(this, "正在登陆...");
        mPresenter.login(mUsername, password);

    }

    /**
     * 执行注册动作
     */
    public void sign() {

        String email = mEtMail.getText().toString().trim();
        mUsername = mEtUsername.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String rpassword = mEtPassword.getText().toString().trim();
        if (email.length() == 0 || mUsername.length() == 0 || password.length() == 0 || rpassword.length() == 0) {
            Toast.makeText(mContext, "请填写必要信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtils.checkEmail(email)) {
            Toast.makeText(mContext, "请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(rpassword)) {
            Toast.makeText(mContext, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        String inputcode = mEtPhoneCodes.getText().toString().trim().toLowerCase();
        if (!inputcode.equals(code)){
            ToastUtils.show_s("验证码错误，请输入正确验证码");
            return;
        }
        ProgressUtils.show(this, "正在注册...");
        mPresenter.signup(mUsername, password, email);
    }

    /**
     * 置换登录注册窗口
     */
    private void changeWindows() {
        if (isLogin) {

            //置换注册界面
            mTvloginSign.setText("登录");
            mBtnLogin.setText("注册");
            mLlRpassword.setVisibility(View.VISIBLE);
            mLlMail.setVisibility(View.VISIBLE);
            mTvTitle.setText("注册");
            mTvForgetPass.setVisibility(View.GONE);
        } else {

            //置换登陆界面
            mTvloginSign.setText("注册");
            mBtnLogin.setText("登录");
            mLlRpassword.setVisibility(View.GONE);
            mLlMail.setVisibility(View.GONE);
            mTvTitle.setText("登录");
            mTvForgetPass.setVisibility(View.VISIBLE);
        }
        isLogin = !isLogin;
    }

    //绑定
    @Override
    protected LandContract.Presenter bindPresenter() {
        return new LandPresenter();
    }

    //返回结果
    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {
        ProgressUtils.dismiss();
        switch (type) {
            case Login:
            case Regin:
                SharedPreferenceHandler.setUserName(mContext, mUsername);
                if (item instanceof PersonInfoEmtity) {
                    PersonInfoEmtity.ResultBean result = ((PersonInfoEmtity) item).getData();
                    try {
                        SharedPreferenceHandler.saveUserInfo(mContext, result);
                        SharedPreferenceHandler.setUserId(mContext, result.getUserId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SnackbarUtils.show(mActivity, item.getMessage());
                    MainActivity.StartAct(mContext);
                    finish();
                }
                break;

        }

    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject JSONObject) {

    }


    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        ProgressUtils.dismiss();
        //更新验证码
        setCode();
        SnackbarUtils.show(mActivity, e.getMessage());

    }


    @OnClick({R.id.login_btn_login, R.id.login_tv_sign, R.id.tv_forget_pass,R.id.iv_code})
    public void onViewClicked(View view) {
        //关闭键盘
        hideSoftKeyboardNoView(mActivity);
        switch (view.getId()) {
            case R.id.login_btn_login:
                if (isLogin) {
                    login();  //登陆
                } else {
                    sign();  //注册
                }
                break;
            case R.id.login_tv_sign:
                changeWindows();
                break;
            case R.id.tv_forget_pass: //忘记密码
//                ForgetPassActivity.StartAct(mContext);
                ToastUtils.show_s("请联系管理员");
                break;
            case R.id.iv_code:
                setCode();
                Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                break;
        }
    }



}
