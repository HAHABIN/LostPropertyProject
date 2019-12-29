package com.example.habin.lostpropertyproject.ui.fragment;


import com.example.habin.lostpropertyproject.Base.BaseFragment;
import com.example.habin.lostpropertyproject.R;

/**
 * created by habin
 * on 2019/12/27
 * 消息碎片
 */
public class MessagePageFragment  extends BaseFragment {

    public static MessagePageFragment newInstance(){
        return new MessagePageFragment();
    }




    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_message;
    }
}
