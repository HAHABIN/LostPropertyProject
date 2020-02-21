package com.example.habin.lostpropertyproject.Ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Http.TaskListener;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.activity.RecordDetailsActivity;
import com.example.habin.lostpropertyproject.Ui.adapter.RecordListAdapter;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 记录列表页面
 */
public class RecordListActivity extends BaseActivity implements RecordListAdapter.OnitemClick, TaskListener {


    private List<ArticleInfoEntity.ResultBean> result;

    public static void StartAct(Context context, int type) {
        Intent intent = new Intent(context, RecordListActivity.class);
        intent.putExtra("type", type);//1为丢失记录 2为拾物记录 3完成记录 4全部记录
        context.startActivity(intent);
    }

    @BindView(R.id.rv_record)
    RecyclerView mRvRecord;
    private String[] titlelist;
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
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        setShowBack(View.VISIBLE);
        setBackOnClick().setOnClickListener(v -> finish());

        recordStatus = getIntent().getIntExtra("type", 1);
        titlelist = getResources().getStringArray(R.array.record_list_name);
        setTitleText(titlelist[recordStatus - 1]);
        mReListAdapter = new RecordListAdapter(mContext, this, recordStatus);
        //设置LayoutManager为LinearLayoutManager
        mRvRecord.setLayoutManager(new LinearLayoutManager(this));
        mRvRecord.setAdapter(mReListAdapter);
        load();
    }

    private void load() {
        ProgressUtils.show(mContext, "加载中....");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", MyApplication.getUserId(mContext));
        if (recordStatus<4){
            hashMap.put("recordStatus", recordStatus);
        }
        HttpClient.getSingleton().startTask(HttpHelper.TaskType.QueryArticleInfo, RecordListActivity.this, hashMap);
    }


    @Override
    public void onItemClick(int position) {
        RecordDetailsActivity.StartAct(mContext, false, result.get(position));
    }


    @Override
    public void taskStarted(HttpHelper.TaskType type) {

    }

    @Override
    public void taskError(HttpHelper.TaskType type, ApiError error) {
        ProgressUtils.dismiss();
        ToastUtils.show_s(error.getMessage());
    }

    @Override
    public void taskFinished(HttpHelper.TaskType type, JSONObject object) {
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
    public void taskFinished(HttpHelper.TaskType type, HttpItem item) {

    }
}
