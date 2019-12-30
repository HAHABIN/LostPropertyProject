package com.example.habin.lostpropertyproject.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 禁止左右滑动的ViewPage
 */
public class NoScrollViewPager extends ViewPager {
    private boolean mNoScoll;

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //设置是否禁止左右滑动
    public void setNoScroll(boolean noScroll) {
        mNoScoll = noScroll;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mNoScoll) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mNoScoll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }
}
