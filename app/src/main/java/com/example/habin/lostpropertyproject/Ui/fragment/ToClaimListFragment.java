package com.example.habin.lostpropertyproject.Ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseFragment;
import com.example.habin.lostpropertyproject.Common.Constants;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Ui.activity.Land.LandActivity;
import com.example.habin.lostpropertyproject.Ui.activity.RecordDetailsActivity;
import com.example.habin.lostpropertyproject.Ui.adapter.ToClaimListAdapter;
import com.example.habin.lostpropertyproject.view.SwipeRecyclerView;

import butterknife.BindView;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 招领列表
 */
public class ToClaimListFragment extends BaseFragment implements ToClaimListAdapter.OnitemClick {


    public static ToClaimListFragment newInstance(int type) {
        ToClaimListFragment fragment = new ToClaimListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }



    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView mSw;
//    ToClaimListAdapter mAdapter;
    private int mType; //0为丢丢 1为拾拾
    private String mAddress;


    public void updateDate(String address) {
        mAddress = address;
        ToastUtils.show_s("-------------"+mAddress+mType);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_to_claim_list;
    }

    @Override
    protected void initView(View view) {
        Bundle arguments = this.getArguments();
        if (arguments != null) {
//            mAddress = arguments.getString("address", null);
            mType = arguments.getInt("type",0);
        }
        mSw.setAdapter(new ToClaimListAdapter(mContext,this,mType));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }



    @Override
    public void onItemClick(int position) {
        if (MyApplication.isLogin(mContext)) {
            startActivity(LandActivity.class,null);
            return;
        }
        ToastUtils.show_s(mActivity,"正在开发中");
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(Constants.IS_SHOW,true);
//        bundle.putSerializable(Constants.ACTICLEINFO_DATA,null);
//        startActivity(RecordDetailsActivity.class,bundle);
    }
}
