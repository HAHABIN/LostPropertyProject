package com.example.habin.lostpropertyproject.Ui.fragment;


import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.HttpItem;

import com.example.habin.lostpropertyproject.Bean.entity.City;
import com.example.habin.lostpropertyproject.Bean.entity.County;
import com.example.habin.lostpropertyproject.Bean.entity.Province;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.fragment.HomePagePresenter;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.HomePageContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.activity.Land.LandActivity;
import com.example.habin.lostpropertyproject.Ui.activity.home.SearchActivity;
import com.example.habin.lostpropertyproject.Ui.adapter.VpAdapter;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SelectorDialogUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Widget.NoScrollViewPager;

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


    private String str;

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

    //判断目前所在列表
    public boolean isLostFind = true;
    //丢拾列表
    private List<Fragment> mFragmentList;
    //ViewPage适配器
    private VpAdapter mVpAdapter;


    //二级联动省级
    private ArrayList<Province> mOptions1List;
    //二级联动市级
    private ArrayList<ArrayList<City>> mOptions2List;
    //三级联动区县
    private ArrayList<ArrayList<ArrayList<County>>> mOptions3List;

    //省级列表
    private ArrayList<Province> mProvinceList;
    //市级列表
    private ArrayList<City> mCityList;
    //区县列表
    private ArrayList<County> mCountyList;
    //选中省份
    private Province mSelectedProvice;
    //选中城市
    private City mSelectCity;
    //选中区县
    private County mSelectCounty;

    //当前选中级别
    private int currentLevel;

    //最新地址
    String address = null;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_home;
    }

    @Override
    protected void initView(View view) {
        //初始化列表
        mFragmentList = new ArrayList<>();
        mFragmentList.add(ToClaimListFragment.newInstance(0));//丢丢 type 0
        mFragmentList.add(ToClaimListFragment.newInstance(1));//拾拾 type 1
        //初始化适配器和数据源
        mVpAdapter = new VpAdapter(getFragmentManager(), mFragmentList);
        //设置适配器
        mVpContent.setAdapter(mVpAdapter);
        //设置禁止左右滑动
        mVpContent.setNoScroll(true);
        //预加载
//        mVpContent.setOffscreenPageLimit(1);
        //设置标题栏内容
        setTitle();
    }

    @Override
    protected void initListener() {
//监听地址栏
        mTvAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address = mTvAddress.getText().toString();
                ToClaimListFragment fragment;
                if (isLostFind){
                    fragment = (ToClaimListFragment) mVpAdapter.getFragment(0);
                } else {
                    fragment = (ToClaimListFragment) mVpAdapter.getFragment(1);

                }
                fragment.updateDate(mTvAddress.getText().toString().trim());

            }
        });
    }

    @Override
    protected void initData() {

    }



    @Override
    protected HomePageContract.Presenter bindPresenter() {
        return new HomePagePresenter();
    }


    @OnClick({R.id.ll_address, R.id.tv_lost, R.id.tv_find, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                SelectorDialogUtils.ShowCityNoCounty(mActivity,mTvAddress);
                break;
            case R.id.tv_lost:
                isLostFind = true;
                setTitle();
                //顶部导航栏点击回调
                mVpContent.setCurrentItem(0);
                ToClaimListFragment fragment0 = (ToClaimListFragment) mVpAdapter.getFragment(0);
                fragment0.updateDate(mTvAddress.getText().toString().trim());
                break;
            case R.id.tv_find:
                isLostFind = false;
                setTitle();
                mVpContent.setCurrentItem(1);
                ToClaimListFragment fragment1 = (ToClaimListFragment) mVpAdapter.getFragment(1);
                fragment1.updateDate(mTvAddress.getText().toString().trim());
                break;
            case R.id.iv_search:
                if (MyApplication.isLogin(mActivity)) {
                    startActivity(LandActivity.class, null);
                    return;
                }
                startActivity(SearchActivity.class,null);
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
        ProgressUtils.dismiss();

        switch (type) {
            case QueryCity:
                break;
        }
    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        ProgressUtils.dismiss();
//        ToastUtils.show_s(e.getMessage());
    }


}
