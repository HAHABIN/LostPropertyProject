package com.example.habin.lostpropertyproject.Ui.fragment;


import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.fragment.MessagePagePresenter;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.MessagePageContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Widget.SwipeRecyclerView;

import org.json.JSONObject;

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




    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_message;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }



    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {

    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {

    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {

    }



    @Override
    protected MessagePageContract.Presenter bindPresenter() {
        return new MessagePagePresenter();
    }
}
