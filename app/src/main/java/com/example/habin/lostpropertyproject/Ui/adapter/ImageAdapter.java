package com.example.habin.lostpropertyproject.Ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.habin.lostpropertyproject.Common.Constants;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.luck.picture.lib.photoview.PhotoView;


import java.util.List;

/**
 * Create by HABIN on 2020/2/222:14
 * Email:739115041@qq.com
 */
public class ImageAdapter extends PagerAdapter {
    public static final String TAG = ImageAdapter.class.getSimpleName();
    private List<String> imageUrls;
    private Activity activity;

    public ImageAdapter(List<String> imageUrls, Activity activity) {
        this.imageUrls = imageUrls;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls.get(position);
        PhotoView photoView = new PhotoView(activity);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        UiUtils.GildeLoad(photoView,url);
        Glide.with(activity)
                .load(Constants.BASE_URL+url)
                .into(photoView);
        container.addView(photoView);
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

