package com.example.habin.lostpropertyproject.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.fragment.HomePagePresenter;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.HomePageContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.SelectorDialogUtils;
import com.example.habin.lostpropertyproject.Widget.SelectDialog;
import com.example.habin.lostpropertyproject.ui.activity.home.SearchActivity;
import com.example.habin.lostpropertyproject.ui.adapter.VpAdapter;
import com.example.habin.lostpropertyproject.view.NoScrollViewPager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by habin
 * on 2019/12/27
 * 首页碎片
 */
public class HomePageFragment extends BaseMVPFragment<HomePageContract.Presenter> implements HomePageContract.View {




    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }


    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.vp_content)
    NoScrollViewPager mVpContent;
    @BindView(R.id.tv_lost)
    TextView mTvLost;
    @BindView(R.id.tv_find)
    TextView mTvFind;

    public boolean isLostFind = true;
    private List<Fragment> mFragmentList;
    private VpAdapter mVpAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);


        mFragmentList = new ArrayList<>();
        mFragmentList.add(ToClaimListFragment.newInstance(0));//丢丢 type 0
        mFragmentList.add(ToClaimListFragment.newInstance(1));//拾拾 type 1
        mVpAdapter = new VpAdapter(getFragmentManager(), mFragmentList);
        mVpContent.setAdapter(mVpAdapter);
        //设置禁止左右滑动
        mVpContent.setNoScroll(true);
        //预加载
        mVpContent.setOffscreenPageLimit(1);
        setTitle();
        setListener();

    }

    private void setListener() {

    }

    @Override
    protected HomePageContract.Presenter bindPresenter() {
        return new HomePagePresenter();
    }


    @OnClick({R.id.tv_address, R.id.tv_lost, R.id.tv_find,R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
//                SelectorDialogUtils.getInstance().ShowBankName(mActivity,mTvAddress);
                ToClaimListFragment fragment;
                if (isLostFind){
                    fragment = (ToClaimListFragment) mVpAdapter.getFragment(0);
                } else {
                    fragment = (ToClaimListFragment) mVpAdapter.getFragment(1);

                }
                fragment.updateDate(mTvAddress.getText().toString());
                break;
            case R.id.tv_lost:
                isLostFind = true;
                setTitle();
                //顶部导航栏点击回调
                mVpContent.setCurrentItem(0);
                break;
            case R.id.tv_find:
                isLostFind = false;
                setTitle();
                mVpContent.setCurrentItem(1);
                break;
            case R.id.iv_search:
                SearchActivity.StartAct(mContext);
                break;

        }
    }


    /**
     * 设置状态栏
     */
    protected void setTitle() {
        if (isLostFind) {
            //设置丢丢状态
            mTvLost.setSelected(true);
            mTvFind.setSelected(false);
        } else {
            //设置拾拾状态
            mTvLost.setSelected(false);
            mTvFind.setSelected(true);
        }
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



    @Override
    public void Sucess(BaseResponse baseResponse) {

    }

    @Override
    public void Fail(String errMsg) {

}



}
