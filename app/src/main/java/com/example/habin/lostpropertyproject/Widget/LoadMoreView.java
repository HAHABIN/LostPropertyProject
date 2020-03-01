package com.example.habin.lostpropertyproject.Widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.R;

public class LoadMoreView extends LinearLayout {

    public static final int normal = 0;
    public static final int loading = 1;
    public static final int loadFinish = 2;

    private TextView titleView;
    private ImageView indicatorView;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadView();
    }

    public void setStatus(int status) {
        indicatorView.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        switch (status) {
            case normal:
                setVisibility(GONE);
                break;
            case loading:
                titleView.setText("努力加载中...");
                break;
            case loadFinish:
                indicatorView.setVisibility(GONE);
                titleView.setText("没有更多啦...");
                titleView.setVisibility(VISIBLE);
                break;
        }
    }

    private void loadView() {
        setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer_view, this);

        titleView = view.findViewById(R.id.show_text_id);
        indicatorView = view.findViewById(R.id.iv_footer_view_progressbar);
        indicatorView.setImageResource(R.drawable.progressbar);
    }
}
