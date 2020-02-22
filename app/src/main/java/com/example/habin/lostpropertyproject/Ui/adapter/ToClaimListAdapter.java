package com.example.habin.lostpropertyproject.Ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.Bean.UploadPhotoParams;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.Widget.CircleImageView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

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
    private OnitemClick mOnitemClick;
    private int mType;
    private List<ArticleInfoEntity.ResultBean> mDataList;
    //图片选中样式集合
    private int[] ResultPic = {R.mipmap.ic_result_seeking, R.mipmap.ic_result_pickuping};


    public ToClaimListAdapter(Context context,OnitemClick onitemClick,int type) {
        mContext = context;
        mOnitemClick = onitemClick;
        mType = type;
    }

    public void setData(List<ArticleInfoEntity.ResultBean> dataList){
        if (mDataList == null){
            mDataList = new ArrayList<>();
            mDataList.addAll(dataList);
        } else {
            mDataList.clear();
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }
    //定义设置点击事件监听的方法
    public void setOnitemClickLintener(OnitemClick onitemClick) {
        this.mOnitemClick = onitemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_to_claim, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ArticleInfoEntity.ResultBean resultBean = mDataList.get(position);
        viewHolder.mTvReleaseTime.setText(StringUtils.stampToDate(resultBean.getCreateTime()));
        viewHolder.mTvFindTime.setText(StringUtils.stampToDate(resultBean.getFindTime()));
        viewHolder.mTvAddress.setText(resultBean.getAddressContent());
        viewHolder.mTvNoteContext.setText(resultBean.getDescription());
        if (resultBean.getImgStr()!=null){
            List<UploadPhotoParams> uploadPhotoParams = JsonUtil.fromJson(resultBean.getImgStr(), new TypeToken<List<UploadPhotoParams>>() {
            });
            UiUtils.GildeLoad(mContext,viewHolder.mIvContentPic,uploadPhotoParams.get(0).getImgStr());
        }
        viewHolder.mIvResult.setBackgroundResource(ResultPic[mType]);
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnitemClick!=null){
                mOnitemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_pic)
        CircleImageView mCivPic;        //头像
        @BindView(R.id.tv_nickname)
        TextView mTvNickNmae;               //用户名
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
        @BindView(R.id.iv_result)
        ImageView mIvResult;
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
