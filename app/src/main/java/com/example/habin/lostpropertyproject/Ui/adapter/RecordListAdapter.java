package com.example.habin.lostpropertyproject.Ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Bean.entity.UploadPhotoParams;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

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
    private List<ArticleInfoEntity.ResultBean> mDataList;

    //图片选中样式集合
    private int[] ResultPic = {R.mipmap.ic_result_seeking, R.mipmap.ic_result_pickuping, R.mipmap.ic_result_succsee, R.mipmap.ic_result_cancal};


    public void setData(List<ArticleInfoEntity.ResultBean> dataList) {
        if(mDataList == null){
            mDataList = new ArrayList<>();
            mDataList.addAll(dataList);
        }else{
            mDataList.clear();
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    public RecordListAdapter(Context context, OnitemClick onitemClick, int type) {
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
        ArticleInfoEntity.ResultBean resultBean = mDataList.get(position);
        viewHolder.mTvFindTime.setText(StringUtils.stampToDate(resultBean.getCreateTime()));
        viewHolder.mTvNoteContext.setText(resultBean.getDescription());
        viewHolder.mIvResult.setBackgroundResource(ResultPic[resultBean.getRecordStatus()-1]);
        if (resultBean.getImgStr()!=null){
            List<String> imgStrList = JsonUtil.StringToList(resultBean.getImgStr());
            UiUtils.GildeLoad(mContext,viewHolder.mIvImg,imgStrList.get(0));
        }

        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnitemClick != null) {
                mOnitemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_find_time)
        TextView mTvFindTime;
        @BindView(R.id.iv_result)
        ImageView mIvResult;
        @BindView(R.id.tv_note_context)
        TextView mTvNoteContext;
        @BindView(R.id.iv_img)
        ImageView mIvImg;
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

