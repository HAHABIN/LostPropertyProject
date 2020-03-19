package com.example.habin.lostpropertyproject.Ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.habin.lostpropertyproject.Bean.entity.UploadPhotoParams;
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
    private List<ArticleInfoEntity.ResultBean> mDataList;
    //图片选中样式集合
    private int[] ResultPic = {R.mipmap.ic_result_seeking, R.mipmap.ic_result_pickuping};


    public ToClaimListAdapter(Context context,OnitemClick onitemClick) {
        mContext = context;
        mOnitemClick = onitemClick;
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
        String profileImg = resultBean.getPersonInfo().getProfileImg();
        if (profileImg!=null){
            List<String> strings = JsonUtil.StringToList(profileImg);
            UiUtils.GildeLoad(mContext,viewHolder.mCivPic,strings.get(0));
        } else {
            //清除原有图片 防止重复显示
            Glide.with(mContext).clear(viewHolder.mCivPic);
        }
        viewHolder.mTvTypeName.setText(StringUtils.typeIdToName(resultBean.getTypeId()));
        viewHolder.mTvNickNmae.setText(resultBean.getPersonInfo().getNickname());
//        viewHolder.mTvReleaseTime.setText(StringUtils.stampToDate(resultBean.getCreateTime()));
        viewHolder.mTvReleaseTime.setText(StringUtils.getTimeFormatText(resultBean.getCreateTime()));
        viewHolder.mTvFindTime.setText(StringUtils.stampToDate(resultBean.getFindTime()));
        viewHolder.mTvAddress.setText(resultBean.getAddressContent());
        viewHolder.mTvNoteContext.setText(resultBean.getDescription());
        //设置Tag 防止图片重用
        viewHolder.mIvContentPic.setTag(R.id.iv_content_pic,position);
        if (resultBean.getImgStr()!=null){
                List<String> strings = JsonUtil.StringToList(resultBean.getImgStr());
                UiUtils.GildeLoad(mContext,viewHolder.mIvContentPic,strings.get(0));

        } else {
            //清除原有图片 防止重复显示
            Glide.with(mContext).clear(viewHolder.mIvContentPic);
        }
        viewHolder.mIvResult.setBackgroundResource(ResultPic[resultBean.getStatus()-1]);
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnitemClick!=null){
                mOnitemClick.onItemClick(position,resultBean);
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
        ImageView mIvResult;            //发布类型
        @BindView(R.id.tv_type_name)
        TextView mTvTypeName;           //物品类型
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position, ArticleInfoEntity.ResultBean resultBean);
    }
}
