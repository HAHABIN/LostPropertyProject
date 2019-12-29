package com.example.habin.lostpropertyproject.ui.fragment;


import com.example.habin.lostpropertyproject.Base.BaseFragment;
import com.example.habin.lostpropertyproject.R;

/**
 * created by habin
 * on 2019/12/27
 * 我的碎片
 */
public class MinePageFragment extends BaseFragment {

    public static MinePageFragment newInstance(){
        return new MinePageFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_mine;
    }
}
