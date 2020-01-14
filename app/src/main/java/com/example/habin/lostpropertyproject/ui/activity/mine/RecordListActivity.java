package com.example.habin.lostpropertyproject.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.ui.activity.RecordDetailsActivity;
import com.example.habin.lostpropertyproject.ui.adapter.RecordListAdapter;
import com.example.habin.lostpropertyproject.view.SwipeRecyclerView;

import butterknife.BindView;

/**
 * 记录列表页面
 */
public class RecordListActivity extends BaseActivity implements RecordListAdapter.OnitemClick {

    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView mSwp;
    private String[] titlelist;

    public static void StartAct(Context context, String type) {
        Intent intent = new Intent(context, RecordListActivity.class);
        intent.putExtra("type", type);//1为丢失记录 2为拾物记录 3完成记录 4取消记录
        context.startActivity(intent);
    }


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

        String type = getIntent().getStringExtra("type");
        titlelist = getResources().getStringArray(R.array.record_list_name);
        int typeNum = Integer.parseInt(type);
        setTitleText(titlelist[typeNum]);
        mSwp.setAdapter(new RecordListAdapter(mContext, this, typeNum));
    }


    @Override
    public void onItemClick(int position) {
        RecordDetailsActivity.StartAct(mContext,false);
    }
}
