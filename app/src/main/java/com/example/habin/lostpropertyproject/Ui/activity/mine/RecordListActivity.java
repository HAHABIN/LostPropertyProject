package com.example.habin.lostpropertyproject.Ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.RecordListContract;
import com.example.habin.lostpropertyproject.Presenter.activity.mine.RecordListPresenter;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.activity.RecordDetailsActivity;
import com.example.habin.lostpropertyproject.Ui.adapter.RecordListAdapter;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * 记录列表页面
 */
public class RecordListActivity extends BaseMVPActivity<RecordListContract.Presenter> implements RecordListContract.View,RecordListAdapter.OnitemClick {


    private List<ArticleInfoEntity.ResultBean> result;

    public static void StartAct(Context context, int type) {
        Intent intent = new Intent(context, RecordListActivity.class);
        intent.putExtra(Constants.RECORD_STATUS, type);//1为丢失记录 2为拾物记录 3完成记录 4全部记录
        context.startActivity(intent);
    }

    @BindView(R.id.rv_record)
    RecyclerView mRvRecord;
    @BindArray(R.array.record_list_name)
    String[] titlelist;

    private RecordListAdapter mReListAdapter;
    private int recordStatus;//1为丢失记录 2为拾物记录 3完成记录 4全部记录

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_list;
    }

    @Override
    protected boolean showTitle() {
        return true;
    }

    @Override
    protected void initParam() {
        super.initParam();
        Intent intent = getIntent();
        if (intent != null) {
            recordStatus = intent.getIntExtra(Constants.RECORD_STATUS,1);
        }

    }

    @Override
    protected void initView() {
        setTitle();
        mReListAdapter = new RecordListAdapter(mContext, this, recordStatus);
        //设置LayoutManager为LinearLayoutManager
        mRvRecord.setLayoutManager(new LinearLayoutManager(this));
        mRvRecord.setAdapter(mReListAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        load();
    }


    private void setTitle() {
        setShowBack(View.VISIBLE);
        setBackOnClick().setOnClickListener(v -> finish());
        setTitleText(titlelist[recordStatus - 1]);
    }

    private void load() {
        ProgressUtils.show(mContext, "加载中....");
        mPresenter.QueryArticleInfo(MyApplication.getUserId(mContext),recordStatus);
    }


    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IS_SHOW,false);
        bundle.putSerializable(Constants.ACTICLEINFO_DATA,result.get(position));
        startActivity(RecordDetailsActivity.class,bundle);
    }


    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {

    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {
        ProgressUtils.dismiss();
        switch (type) {
            case QueryArticleInfo:
                ArticleInfoEntity articleInfoEntity = JsonUtil.JSONObjectToBean(object, ArticleInfoEntity.class);
                if (articleInfoEntity != null) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.clear();
                    result = articleInfoEntity.getResult();
                    if (result != null && !result.isEmpty()) {
                        mReListAdapter.setData(result);
                    }
                }
                break;
        }
    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        ProgressUtils.dismiss();
        ToastUtils.show_s(e.getMessage());
    }

    @Override
    protected RecordListContract.Presenter bindPresenter() {
        return new RecordListPresenter();
    }
}
