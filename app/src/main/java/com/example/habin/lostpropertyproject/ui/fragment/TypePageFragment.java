package com.example.habin.lostpropertyproject.ui.fragment;


import com.example.habin.lostpropertyproject.Base.BaseFragment;
import com.example.habin.lostpropertyproject.R;

/**
 * created by habin
 * on 2019/12/27
 * 分类碎片
 */
public class TypePageFragment extends BaseFragment {

    public static TypePageFragment newInstance() {
        return new TypePageFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_type;
    }
}
