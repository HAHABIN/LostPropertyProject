package com.example.habin.lostpropertyproject.ui.fragment;

import android.os.Bundle;

import com.example.habin.lostpropertyproject.Base.BaseFragment;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.ui.activity.Land.LandActivity;
import com.example.habin.lostpropertyproject.ui.activity.RecordDetailsActivity;
import com.example.habin.lostpropertyproject.ui.adapter.ToClaimListAdapter;
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
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_to_claim_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle bundle = this.getArguments();

        mType = bundle.getInt("type");
        mSw.setAdapter(new ToClaimListAdapter(mContext,this,mType));
    }

    @Override
    public void onItemClick(int position) {
        if (MyApplication.isLogin(mContext)) {
            LandActivity.StartAct(mContext);
            return;
        }
        RecordDetailsActivity.StartAct(mContext,true);
    }
}
