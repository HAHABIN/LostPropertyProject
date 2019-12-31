package com.example.habin.lostpropertyproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Presenter.MineTpyePresenter;
import com.example.habin.lostpropertyproject.Presenter.contract.MinePageContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.ui.activity.RecordListActivity;
import com.example.habin.lostpropertyproject.ui.activity.SettingActivity;
import com.example.habin.lostpropertyproject.ui.activity.UserInfoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * created by habin
 * on 2019/12/27
 * 我的碎片
 */
public class MinePageFragment extends BaseMVPFragment<MinePageContract.Presenter> implements MinePageContract.View {



    public static MinePageFragment newInstance() {
        return new MinePageFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_mine;
    }


    @Override
    protected MinePageContract.Presenter bindPresenter() {
        return new MineTpyePresenter();
    }

    @Override
    public void Sucess(BaseResponse baseResponse) {

    }

    @Override
    public void Fail(String errMsg) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Throwable e) {

    }



    @OnClick({R.id.rl_top, R.id.ll_record_lost, R.id.ll_record_find, R.id.ll_record_complete,R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_top:
                UserInfoActivity.StartAct(mContext);
                break;
            case R.id.ll_record_lost:
                RecordListActivity.StartAct(mContext,"1");
                break;
            case R.id.ll_record_find:
                RecordListActivity.StartAct(mContext,"2");
                break;
            case R.id.ll_record_complete:
                RecordListActivity.StartAct(mContext,"3");
                break;
            case R.id.ll_setting:
                SettingActivity.StartAct(mContext);
                break;
        }
    }
}
