package com.example.habin.lostpropertyproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * created by habin
 * on 2020/1/2
 * Email 739115041@qq.com
 * 自定义GridView
 */
public class MyGirdView extends GridView {
    public MyGirdView(Context context) {
        super(context);
    }

    public MyGirdView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGirdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
