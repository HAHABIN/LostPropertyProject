package com.example.habin.lostpropertyproject.Ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Base.BaseActivity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.view.SwipeRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {


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
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

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
//                        initload();
                    }

                }
                return false;

            }
        });

    }



    @OnClick({R.id.et_search, R.id.iv_clean, R.id.tv_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                break;
            case R.id.iv_clean:
                mEtSearch.setText("");
                break;
            case R.id.tv_out:
                finish();
                break;
        }
    }
}
