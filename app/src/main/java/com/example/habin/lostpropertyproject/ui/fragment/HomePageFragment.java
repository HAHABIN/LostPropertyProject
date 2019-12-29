package com.example.habin.lostpropertyproject.ui.fragment;


import com.example.habin.lostpropertyproject.Base.BaseFragment;
import com.example.habin.lostpropertyproject.R;

/**
 * created by habin
 * on 2019/12/27
 * 首页碎片
 */
public class HomePageFragment extends BaseFragment {

    public static HomePageFragment newInstance(){
        return new HomePageFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_home;
    }
}
