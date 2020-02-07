package com.example.habin.lostpropertyproject.ui.fragment;


import android.os.Bundle;

import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.MessagePagePresenter;
import com.example.habin.lostpropertyproject.Presenter.contract.MessagePageContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.view.SwipeRecyclerView;

import butterknife.BindView;

/**
 * created by habin
 * on 2019/12/27
 * 消息碎片
 */
public class MessagePageFragment  extends BaseMVPFragment<MessagePageContract.Presenter> implements MessagePageContract.View {

    public static MessagePageFragment newInstance(){
        return new MessagePageFragment();
    }

    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView swipeRecyclerView;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_message;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {

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

    @Override
    protected MessagePageContract.Presenter bindPresenter() {
        return new MessagePagePresenter();
    }
}
