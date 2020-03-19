package com.example.habin.lostpropertyproject.Ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Bean.entity.CommentEntity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.Widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by HABIN on 2020/3/1723:41
 * Email:739115041@qq.com
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private Context mContext;
    private List<CommentEntity> mDataList;

    public CommentAdapter(Context mContext, List<CommentEntity> dataList) {
        this.mContext = mContext;
        this.mDataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CommentEntity commentEntity = mDataList.get(i);
        viewHolder.mName.setText(commentEntity.getNickName());
        viewHolder.mContent.setText(commentEntity.getContent());
        viewHolder.mTime.setText(StringUtils.getTimeFormatText(commentEntity.getCreateTime()));
        if (commentEntity.getUserImg()!=null){
            List<String> strings = JsonUtil.StringToList(commentEntity.getUserImg());
            UiUtils.GildeLoad(mContext,viewHolder.mAvatar,strings.get(0));
        }
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.comment_item_userName)
        TextView mName;
        @BindView(R.id.comment_item_content)
        TextView mContent;
        @BindView(R.id.comment_item_time)
        TextView mTime;
        @BindView(R.id.comment_item_logo)
        CircleImageView mAvatar;
        @BindView(R.id.comment_item_like)
        ImageView  mLike;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
