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
import com.example.habin.lostpropertyproject.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 首页列表适配器
 */
public class ToClaimListAdapter extends RecyclerView.Adapter<ToClaimListAdapter.ViewHolder> {
    private Context mContext;


    public ToClaimListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_to_claim, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }




    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_pic)
        CircleImageView mCivPic;        //头像
        @BindView(R.id.tv_name)
        TextView mTvName;               //用户名
        @BindView(R.id.tv_release_time)
        TextView mTvReleaseTime;        //丢失寻找时间
        @BindView(R.id.tv_note_context)
        TextView mTvNoteContext;        //内容
        @BindView(R.id.iv_content_pic)
        ImageView mIvContentPic;        //发布时间
        @BindView(R.id.tv_address)
        TextView mTvAddress;            //丢失地点
        @BindView(R.id.tv_find_time)
        TextView mTvFindTime;           //发布时间

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
