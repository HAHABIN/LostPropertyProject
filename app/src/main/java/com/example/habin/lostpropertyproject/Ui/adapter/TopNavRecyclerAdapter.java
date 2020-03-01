package com.example.habin.lostpropertyproject.Ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleTypeEntity;
import com.example.habin.lostpropertyproject.R;

import java.util.ArrayList;
import java.util.List;

/**
*分类模块-分页显示
 * * 顶部滑动分类适配器
*@author HABIN
*create at 2020/03/01
*/
public class TopNavRecyclerAdapter extends RecyclerView.Adapter<TopNavRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<ArticleTypeEntity.ResultBean> mTitles;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色
    private OnItemClickListener mOnItemClickListener = null;



    public TopNavRecyclerAdapter(Context context, List<ArticleTypeEntity.ResultBean> titles, OnItemClickListener mOnItemClickListener) {
        this.mContext = context;
        this.mTitles = titles;
        this.mOnItemClickListener = mOnItemClickListener;
        isClicks = new ArrayList<>();
        for (int i = 0; i < mTitles.size(); i++) {
            if (i == 0) {
                //默认第一个状态为点击
                isClicks.add(true);
            } else {
                isClicks.add(false);
            }

        }
    }
    //修改状态
    public void setStaus(int postion) {
        //点击状态全部设置为false
        for (int i = 0; i < isClicks.size(); i++) {
            isClicks.set(i, false);
        }
        //根据传入的postion改变当前状态
        isClicks.set(postion, true);
        //刷新数据
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_topnav, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int postion) {
        String title = mTitles.get(postion).getTypeName();

        //默认第一个状态字体大小 颜色  下划线是否显示
        if (isClicks.get(postion).equals(true)) {
            holder.mTvTitle.setTextColor(Color.parseColor("#0AF3F3"));
            holder.mTvTitle.getPaint().setFakeBoldText(true);//加粗
            holder.mTvTitle.setTextSize(18);
            holder.mIVLine.setVisibility(View.VISIBLE);
        } else {
            holder.mTvTitle.setTextColor(Color.parseColor("#ffffff"));
            holder.mTvTitle.setTextSize(16);
            holder.mTvTitle.getPaint().setFakeBoldText(false);
            holder.mIVLine.setVisibility(View.INVISIBLE);
        }
        holder.mTvTitle.setText(title);
        //导航栏监听 此处监听整个itemView
        holder.itemView.setOnClickListener(v -> {
            //点击修改状态
            setStaus(postion);
            if (mOnItemClickListener != null) {
                //回调点击位置给ViewPager
                mOnItemClickListener.onItemClick(postion);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private ImageView mIVLine;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mIVLine = itemView.findViewById(R.id.iv_line);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}
