package com.example.habin.lostpropertyproject.ui.fragment;


import com.example.habin.lostpropertyproject.Base.BaseMVPFragment;
import com.example.habin.lostpropertyproject.Bean.BaseResponse;
import com.example.habin.lostpropertyproject.Presenter.MineTpyePresenter;
import com.example.habin.lostpropertyproject.Presenter.contract.MinePageContract;
import com.example.habin.lostpropertyproject.R;

/**
 * created by habin
 * on 2019/12/27
 * 我的碎片
 */
public class MinePageFragment extends BaseMVPFragment<MinePageContract.Presenter> implements MinePageContract.View {

    public static MinePageFragment newInstance(){
        return new MinePageFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_mine;
    }


    @Override
    protected MinePageContract.Presenter bindPresenter() {
        return new MineTpyePresenter();
    }

    @Override
    public void Sucess(BaseResponse baseResponse) {

    }

    @Override
    public void Fail(String errMsg) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Throwable e) {

    }
}
