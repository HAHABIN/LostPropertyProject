package com.example.habin.lostpropertyproject.Ui.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.PersonInfoEntity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.fragment.MineTpyePresenter;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.MinePageContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.Ui.activity.Land.LandActivity;
import com.example.habin.lostpropertyproject.Ui.activity.mine.RecordListActivity;
import com.example.habin.lostpropertyproject.Ui.activity.mine.SettingActivity;
import com.example.habin.lostpropertyproject.Ui.activity.mine.UserInfoActivity;
import com.example.habin.lostpropertyproject.view.CircleImageView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by habin
 * on 2019/12/27
 * 我的碎片
 */
public class MinePageFragment extends BaseMVPFragment<MinePageContract.Presenter> implements MinePageContract.View {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.civ_pic)
    CircleImageView mCivPic;


    public static MinePageFragment newInstance() {
        return new MinePageFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_mine;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        PersonInfoEntity.ResultBean mPersonInfo = MyApplication.getUserInfo(mContext);
        if (mPersonInfo != null) {
            mTvName.setText(mPersonInfo.getName());
        }
        UiUtils.GildeLoad(mCivPic,mPersonInfo.getProfileImg());

    }

    @Override
    protected MinePageContract.Presenter bindPresenter() {
        return new MineTpyePresenter();
    }


    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {

    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {

    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
    }


    @OnClick({R.id.rl_top, R.id.ll_record_lost, R.id.ll_record_mine, R.id.ll_record_find, R.id.ll_record_complete, R.id.ll_setting, R.id.ll_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_top:
                UserInfoActivity.StartAct(mContext);
                break;
            case R.id.ll_record_lost:
                RecordListActivity.StartAct(mContext, 1);
                break;
            case R.id.ll_record_find:
                RecordListActivity.StartAct(mContext, 2);
                break;
            case R.id.ll_record_complete:
                RecordListActivity.StartAct(mContext, 3);
                break;
            case R.id.ll_record_mine:
                RecordListActivity.StartAct(mContext, 4);
                break;
            case R.id.ll_setting:
                SettingActivity.StartAct(mContext);
                break;
            case R.id.ll_about:
//                String  username = SharedPreferenceHandler.getUserName(mContext);
//                SnackbarUtils.show(mActivity,username);
                LandActivity.StartAct(mContext);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        PersonInfoEntity.ResultBean personInfo = MyApplication.getUserInfo(mContext);
        if (personInfo != null) {
            mTvName.setText(personInfo.getName());
            UiUtils.GildeLoad(mCivPic,personInfo.getProfileImg());
        }
    }

}
