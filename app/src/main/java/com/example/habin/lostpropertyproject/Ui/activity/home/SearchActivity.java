package com.example.habin.lostpropertyproject.Ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.SearchContract;
import com.example.habin.lostpropertyproject.Presenter.activity.home.SearchPresenter;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.adapter.ToClaimListAdapter;
import com.example.habin.lostpropertyproject.Widget.SwipeRecyclerView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseMVPActivity<SearchContract.Presenter> implements SearchContract.View,ToClaimListAdapter.OnitemClick {



    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }


    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.iv_clean)
    ImageView mIvClean;
    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView mSw;
    @BindView(R.id.ll_no_order)
    LinearLayout mllNoOrder;

    private ToClaimListAdapter mAdapter;

    private String mKey;
    private int mPageNo = 1;
    private int mPageSize = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected void initView() {
        mAdapter = new ToClaimListAdapter(mContext, this);
        mSw.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        //监控输入框
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mIvClean.setVisibility(View.GONE);
                } else {
                    mIvClean.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    String searchKey = mEtSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(searchKey)) {
                        mKey = searchKey;
                        load();
                    }

                }
                return false;

            }
        });
        mSw.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                load();
            }

            @Override
            public void onLoadMore() {
                mPageNo++;
                load();
            }
        });
        mSw.addLoadMoreView();
//        mSw.setEnabled(false);
    }

    private void load() {
        mPresenter.SearchInfo(mEtSearch.getText().toString().trim(),mPageNo,mPageSize);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.et_search, R.id.iv_clean, R.id.tv_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                load();
                break;
            case R.id.iv_clean:
                mEtSearch.setText("");
                break;
            case R.id.tv_out:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {
        mSw.stopLoad();
    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {
        mSw.stopLoad();
    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        mSw.stopLoad();
    }

    @Override
    protected SearchContract.Presenter bindPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void onItemClick(int position) {

    }
}
