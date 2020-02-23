package com.example.habin.lostpropertyproject.Ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseFragment;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.TaskListener;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Ui.activity.Land.LandActivity;
import com.example.habin.lostpropertyproject.Ui.adapter.ToClaimListAdapter;
import com.example.habin.lostpropertyproject.Widget.SwipeRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 招领列表
 */
public class ToClaimListFragment extends BaseFragment implements TaskListener, ToClaimListAdapter.OnitemClick {


    public static ToClaimListFragment newInstance(int type) {
        ToClaimListFragment fragment = new ToClaimListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView mSw;

    private ToClaimListAdapter mAdapter;
    private int mType; //0为丢丢 1为拾拾
    private String mAddress;
    private int mPageNo = 1;
    private int mPageSize = 10;
    private List<ArticleInfoEntity.ResultBean> mDataList;

    public void updateDate(String address) {
        if (!address.equals("地址")){
            mAddress = address;
            mPageNo = 1;
            mSw.setRefreshing(true);
            mDataList.clear();

        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_to_claim_list;
    }

    @Override
    protected void initView(View view) {
        Bundle arguments = this.getArguments();
        if (arguments != null) {
            mType = arguments.getInt("type", 0);
        }
        mAdapter = new ToClaimListAdapter(getContext(), this, mType);
        mSw.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
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
        mSw.setRefreshing(true);
    }

    private void load() {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (mAddress != null) {
            hashMap.put("addressContent", mAddress);
        }
        hashMap.put("pageNo", mPageNo);
        hashMap.put("pageSize", mPageSize);
        hashMap.put("status",mType+1);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.QueryArticleInfo, this, hashMap);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onItemClick(int position) {
        if (MyApplication.isLogin(mActivity)) {
            startActivity(LandActivity.class, null);
            return;
        }
        ToastUtils.show_s(mActivity, "正在开发中");
    }

    @Override
    public void taskStarted(HttpHelper.TaskType type) {

    }

    @Override
    public void taskError(HttpHelper.TaskType type, ApiError error) {
        ToastUtils.show_s(error.getMessage());
        mSw.stopLoad();
    }

    @Override
    public void taskFinished(HttpHelper.TaskType type, JSONObject object) {

        mSw.stopLoad();
        switch (type) {
            case QueryArticleInfo:
                ArticleInfoEntity articleInfoEntity = JsonUtil.JSONObjectToBean(object, ArticleInfoEntity.class);
                if (mDataList == null) {
                    mDataList = new ArrayList<>();
                }
                if (articleInfoEntity != null) {
                    List<ArticleInfoEntity.ResultBean> result = articleInfoEntity.getResult();
                    if (result.size()==0){
                        if (mDataList.size()>0){
                            ToastUtils.show_s("已经到底.....");
                        } else {
                            ToastUtils.show_s("当前城市无发布信息");
                        }

                    }
                    if (mPageNo == 1) {
                        mDataList.clear();
                    }
                    if (result != null && !result.isEmpty()) {
                        mDataList.addAll(result);
                        if (result.size() < mPageSize) {
                            mSw.noMoreData();
                        }
                    }
                    mAdapter.setData(mDataList);
                }
                break;

    }

}

    @Override
    public void taskFinished(HttpHelper.TaskType type, HttpItem item) {

    }
}
