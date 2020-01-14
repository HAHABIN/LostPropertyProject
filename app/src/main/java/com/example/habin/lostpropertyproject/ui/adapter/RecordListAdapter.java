package com.example.habin.lostpropertyproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by habin
 * on 2020/1/14
 * Email 739115041@qq.com
 * 记录列表适配器
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {


    private Context mContext;
    private OnitemClick mOnitemClick;
    private int mType;

    //图片选中样式集合
    private int[] ResultPic = {R.mipmap.ic_result_seeking,R.mipmap.ic_result_pickuping,R.mipmap.ic_result_succsee, R.mipmap.ic_result_cancal};

    public RecordListAdapter(Context context) {
        mContext = context;
    }

    public RecordListAdapter(Context context, OnitemClick onitemClick,int type) {
        mContext = context;
        mOnitemClick = onitemClick;
        mType = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_record_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mIvResult.setBackgroundResource(ResultPic[mType]);
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnitemClick!=null){
                mOnitemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_find_time)
        TextView mTvFindTime;
        @BindView(R.id.iv_result)
        ImageView mIvResult;
        @BindView(R.id.tv_note_context)
        TextView mTvNoteContext;
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

