package com.example.habin.lostpropertyproject.Ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * create by Vincent on 2019/5/24 10:41
 * describe :
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonViewHolder>{

    protected LayoutInflater layoutInflater;

    protected List<T> dataList;

    protected int layoutId;

    protected Context mContext;

    public CommonRecyclerAdapter(Context context, List<T> dataList, int layoutId){
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = layoutInflater.inflate(layoutId,viewGroup,false);
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder commonViewHolder, int position) {
        bindData(commonViewHolder,dataList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected abstract void bindData(CommonViewHolder holder, T data, int position);
}
