package com.example.habin.lostpropertyproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by habin
 * on 2020/1/10
 * Email 739115041@qq.com
 * 弹出选择窗适配器
 */
public class SelectRecyclerAdapter extends RecyclerView.Adapter<SelectRecyclerAdapter.ViewHolder> {

    protected List<String> mDataList;

    private Context mContext;
    private OnitemClick mOnitemClick;
    private boolean isShowTitle;

    public SelectRecyclerAdapter(Context context, List<String> dataList,OnitemClick onitemClick) {
        mContext = context;
        mDataList = dataList;
        mOnitemClick = onitemClick;
    }

    public SelectRecyclerAdapter(Context context, OnitemClick onitemClick) {
        mContext = context;
        mOnitemClick = onitemClick;
    }

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener(OnitemClick onitemClick) {
        this.mOnitemClick = onitemClick;
    }
    public void setTitleStatus(boolean isshow){
        isShowTitle = isshow;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_dialog_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dialogItemBt.setText(mDataList.get(position));
        holder.dialogItemLine.setVisibility(View.GONE);
        if (1 == getItemCount()) {
            holder.dialogItemLine.setBackgroundResource(isShowTitle ? R.drawable.select_dialog_item_bg_buttom: R.drawable.dialog_item_bg_only);
        } else if (position == 0) {
            holder.dialogItemLine.setVisibility(View.VISIBLE);
            holder.dialogItemBt.setBackgroundResource(isShowTitle? R.drawable.select_dialog_item_bg_center: R.drawable.select_dialog_item_bg_top);
        } else if (position == getItemCount() - 1) {
            holder.dialogItemBt.setBackgroundResource(R.drawable.select_dialog_item_bg_buttom);
        } else {
            holder.dialogItemLine.setVisibility(View.VISIBLE);
            holder.dialogItemBt.setBackgroundResource(R.drawable.select_dialog_item_bg_center);
        }
        holder.itemView.setOnClickListener(v -> {
            if (mOnitemClick != null){
                mOnitemClick.onItemClick(position);
//                cancelDismiss = false;
//                dismiss();
            }
        });
//        holder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
//            @Override
//            public void onItemClickListener(int position) {
//                if (mListener != null) {
//                    mListener.onItemClick(position);
//                    cancelDismiss = false;
//                    dismiss();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dialog_item_bt)
        TextView dialogItemBt;
        @BindView(R.id.dialog_item_line)
        View dialogItemLine;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }
}
