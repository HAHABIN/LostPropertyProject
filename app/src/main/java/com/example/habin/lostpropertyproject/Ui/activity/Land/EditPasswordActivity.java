package com.example.habin.lostpropertyproject.Ui.activity.Land;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.TaskListener;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.EditPasswordContract;
import com.example.habin.lostpropertyproject.Presenter.activity.land.EditPasswordPresenter;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SharedPreferenceHandler;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 修改密码模块
 */
public class EditPasswordActivity extends BaseMVPActivity<EditPasswordContract.Presenter> implements EditPasswordContract.View {




    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, EditPasswordActivity.class));
    }

    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.ed_old_password)
    EditText mEdOldPassword;
    @BindView(R.id.ed_new_password)
    EditText mEdNewPassword;
    @BindView(R.id.ed_re_new_password)
    EditText mEdReNewPassword;

    private String mSpusername;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_password;
    }

    @Override
    protected boolean showTitle() {
        return true;
    }

    @Override
    protected void initView() {
        setTitleStatus();
    }

    @Override
    protected void initListener() {
        setBackOnClick().setOnClickListener(v -> finish());
        setRightOnClick().setOnClickListener(v -> {
            UiUtils.hideSoftKeyboardNoView(mActivity);
            String oldPassword = mEdOldPassword.getText().toString().trim();
            String Np = mEdNewPassword.getText().toString().trim();
            String ReNp = mEdReNewPassword.getText().toString().trim();
            if (mSpusername.length() == 0 || oldPassword.length() == 0 || Np.length() == 0 || ReNp.length() == 0) {
                ToastUtils.show_s("请填写必要信息");
                return;
            }
            if (oldPassword.equals(Np)) {
                ToastUtils.show_s("原密码不可以与新密码相同");
                return;
            }
            if (ReNp.equals(Np)) {
                ProgressUtils.show(mContext, "正在提交修改");
                mPresenter.UpdatePasswordAuth(mSpusername,mEdOldPassword.getText().toString(),Np);
            } else {
                SnackbarUtils.show(mActivity, "密码不相同，请重新输入");
            }

        });
    }

    @Override
    protected void initData() {

    }
    //设置标题与标题点击事件
    private void setTitleStatus() {
        //获取保存的用户名
        mSpusername = SharedPreferenceHandler.getUserName(mContext);
        setTitleText("修改密码");
        setShowBack(View.VISIBLE);
        setRightText("保存");
        //设置用户名
        mTvUsername.setText(mSpusername);

    }
    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {
        ProgressUtils.dismiss();
        ToastUtils.show_s(mActivity, item.getMessage());
        finish();
    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {

    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        ProgressUtils.dismiss();
//        SnackbarUtils.show(mActivity, e.getMessage());
    }

    @Override
    protected EditPasswordContract.Presenter bindPresenter() {
        return new EditPasswordPresenter();
    }
}
