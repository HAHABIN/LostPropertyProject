package com.example.habin.lostpropertyproject.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
/**
 * 记录列表页面
 */
public class RecordListActivity extends BaseActivity {

    public static void StartAct(Context context,String type) {
        Intent intent = new Intent(context, RecordListActivity.class);
        intent.putExtra("type",type);//1为丢失记录 2为拾物记录 3完成记录
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
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        switch (type){
            case "1":
                setTitleText("丢失记录");
                break;
            case "2":
                setTitleText("拾物记录");
                break;
            case "3":
                setTitleText("完成记录");
                break;
        }

    }


}
