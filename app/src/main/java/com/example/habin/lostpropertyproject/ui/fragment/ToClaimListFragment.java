package com.example.habin.lostpropertyproject.ui.fragment;

import android.os.Bundle;

import com.example.habin.lostpropertyproject.Base.BaseFragment;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.ui.adapter.ToClaimListAdapter;
import com.example.habin.lostpropertyproject.view.SwipeRecyclerView;

import butterknife.BindView;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 招领列表
 */
public class ToClaimListFragment extends BaseFragment {


    public static ToClaimListFragment newInstance(String type) {
        ToClaimListFragment fragment = new ToClaimListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }



    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView mSw;
//    ToClaimListAdapter mAdapter;
    private String mType; //1为丢丢 2为拾拾
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_to_claim_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle bundle = this.getArguments();

        mType = bundle.getString("type");
        mSw.setAdapter(new ToClaimListAdapter(mContext));
    }

}
