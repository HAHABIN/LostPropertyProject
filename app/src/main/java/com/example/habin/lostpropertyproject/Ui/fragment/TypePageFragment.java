package com.example.habin.lostpropertyproject.Ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleTypeEntity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.fragment.TypePagePresenter;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.TypePageContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.activity.Land.LandActivity;
import com.example.habin.lostpropertyproject.Ui.activity.home.SearchActivity;
import com.example.habin.lostpropertyproject.Ui.adapter.ToClaimListAdapter;
import com.example.habin.lostpropertyproject.Ui.adapter.TopNavRecyclerAdapter;
import com.example.habin.lostpropertyproject.Ui.adapter.VpAdapter;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SelectorDialogUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Widget.SwipeRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * created by habin
 * on 2019/12/27
 * 分类碎片
 */
public class TypePageFragment extends BaseMVPFragment<TypePageContract.Presenter> implements TypePageContract.View, ToClaimListAdapter.OnitemClick, TopNavRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.rv_topnav)
    RecyclerView mRvTopnav;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    private List<ArticleTypeEntity.ResultBean> mResult;
    private VpAdapter mVpAdapter;
    private TopNavRecyclerAdapter mTopAdapter;
    //列表
    private List<Fragment> mFragmentList;
    //当前位置
    private int mPostion;
    //当前地址
    private String address;
    public static TypePageFragment newInstance() {
        return new TypePageFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_type;
    }

    @Override
    protected void initView(View view) {

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
                for (int i = 0;i< mResult.size();i++){
                    if (i==mPostion){
                        fragment = (ToClaimListFragment) mVpAdapter.getFragment(mPostion);
                        fragment.updateDate(mTvAddress.getText().toString().trim());
                    }

                }


            }
        });
    }

    @Override
    protected void initData() {
        ProgressUtils.show(mActivity);
        mPresenter.getType();
    }

    public void init() {
        //顶部滑动适配器配置
        //横向
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRvTopnav.setLayoutManager(llm);
        mTopAdapter = new TopNavRecyclerAdapter(getContext(), mResult, this);
        mRvTopnav.setAdapter(mTopAdapter);
        //初始化列表
        mFragmentList = new ArrayList<>();
        for (int i = 1; i <= mResult.size(); i++) {
            mFragmentList.add(ToClaimListFragment.newInstance(i,0));
        }
        //内容ViewPager适配器配置
        mVpAdapter = new VpAdapter(getFragmentManager(), mFragmentList);
        mVpContent.setAdapter(mVpAdapter);
        //预加载
        mVpContent.setOffscreenPageLimit(5);
        //Viewpager滑动监听
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int postion, float v, int i1) {

            }

            @Override
            public void onPageSelected(int postion) {
                mPostion = postion;
                //ViewPager滑动传值给 顶部导航栏RecycleView
                mTopAdapter.setStaus(postion);
                //定位当前位置
                mRvTopnav.scrollToPosition(postion);
            }

            @Override
            public void onPageScrollStateChanged(int postion) {

            }
        });
    }

    @OnClick({R.id.ll_address, R.id.rl_search, R.id.rv_topnav})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                SelectorDialogUtils.ShowCityNoCounty(mActivity, mTvAddress);
                break;
            case R.id.rl_search:
                if (MyApplication.isLogin(mActivity)) {
                    startActivity(LandActivity.class, null);
                    return;
                }
                startActivity(SearchActivity.class, null);
                break;
            case R.id.rv_topnav:
                break;
        }
    }

    @Override
    protected TypePageContract.Presenter bindPresenter() {
        return new TypePagePresenter();
    }


    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {
        ProgressUtils.dismiss();
        switch (type) {
            case queryType:
                if (item instanceof ArticleTypeEntity) {
                    if (mResult == null) {
                        mResult = new ArrayList<>();
                    }
                    mResult = ((ArticleTypeEntity) item).getResult();
                    init();
                }

                break;
        }

    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {

    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        ProgressUtils.dismiss();
    }


    //物品详细item点击事件
    @Override
    public void onItemClick(int position, ArticleInfoEntity.ResultBean resultBean) {

    }

    //顶部导航栏点击
    @Override
    public void onItemClick(int position) {
        //顶部导航栏点击回调
        mVpContent.setCurrentItem(position);
        mPostion = position;
    }
}
