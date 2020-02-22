package com.example.habin.lostpropertyproject.Ui.fragment;


import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.fragment.TypePagePresenter;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.TypePageContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.view.SwipeRecyclerView;

import org.json.JSONObject;

import butterknife.BindView;

/**
 * created by habin
 * on 2019/12/27
 * 分类碎片
 */
public class TypePageFragment extends BaseMVPFragment<TypePageContract.Presenter> implements TypePageContract.View{

    public static TypePageFragment newInstance() {
        return new TypePageFragment();
    }

    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView swipeRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_type;
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
    protected TypePageContract.Presenter bindPresenter() {
        return new TypePagePresenter();
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



}
