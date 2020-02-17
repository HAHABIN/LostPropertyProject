package com.example.habin.lostpropertyproject.Ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * create by Vincent on 2019/5/24 10:33
 * describe :
 */
public class CommonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private SparseArray<View> mViewSparseArray;
    private onItemCommonClickListener commonClickListener;

    public CommonViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mViewSparseArray = new SparseArray<>();
    }

    @Override
    public void onClick(View v) {
        if (commonClickListener != null) {
            commonClickListener.onItemClickListener(getAdapterPosition());
        }
    }

    public <T extends View> T getView(int viewId){
        View view = mViewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewSparseArray.put(viewId,view);
        }
        return (T)view;
    }

    public CommonViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public CommonViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public interface onItemCommonClickListener{
        void onItemClickListener(int position);
    }

    public void setCommonClickListener(onItemCommonClickListener commonClickListener) {
        this.commonClickListener = commonClickListener;
    }

    public CommonViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
}
