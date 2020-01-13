package com.example.habin.lostpropertyproject.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.emtity.PersonInfoEmtity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.MineTpyePresenter;
import com.example.habin.lostpropertyproject.Presenter.contract.MinePageContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.ui.activity.LandActivity;
import com.example.habin.lostpropertyproject.ui.activity.mine.RecordListActivity;
import com.example.habin.lostpropertyproject.ui.activity.mine.SettingActivity;
import com.example.habin.lostpropertyproject.ui.activity.mine.UserInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * created by habin
 * on 2019/12/27
 * 我的碎片
 */
public class MinePageFragment extends BaseMVPFragment<MinePageContract.Presenter> implements MinePageContract.View {

    @BindView(R.id.tv_name)
    TextView mTvName;

    public static MinePageFragment newInstance() {
//        MinePageFragment fragment = new MinePageFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(RESULT, result);
//        fragment.setArguments(bundle);
        return new MinePageFragment();
    }

    private PersonInfoEmtity.ResultBean mPersonInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_mine;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPersonInfo = MyApplication.getUserInfo(mContext);
        if (mPersonInfo!=null){
            mTvName.setText(mPersonInfo.getName());
        }

    }

    @Override
    protected MinePageContract.Presenter bindPresenter() {
        return new MineTpyePresenter();
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {

    }


    @OnClick({R.id.rl_top, R.id.ll_record_lost, R.id.ll_record_find, R.id.ll_record_complete, R.id.ll_setting, R.id.ll_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_top:
                UserInfoActivity.StartAct(mContext);
                break;
            case R.id.ll_record_lost:
                RecordListActivity.StartAct(mContext, "1");
                break;
            case R.id.ll_record_find:
                RecordListActivity.StartAct(mContext, "2");
                break;
            case R.id.ll_record_complete:
                RecordListActivity.StartAct(mContext, "3");
                break;
            case R.id.ll_setting:
                SettingActivity.StartAct(mContext);
                break;
            case R.id.ll_about:
                LandActivity.StartAct(mContext);
                break;
        }
    }


}
