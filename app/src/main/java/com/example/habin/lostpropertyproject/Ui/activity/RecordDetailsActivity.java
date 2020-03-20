package com.example.habin.lostpropertyproject.Ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habin.lostpropertyproject.Base.BaseMVPActivity;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Bean.entity.CommentEntity;
import com.example.habin.lostpropertyproject.Bean.entity.Great;
import com.example.habin.lostpropertyproject.Http.ApiError;
import com.example.habin.lostpropertyproject.Http.Constants;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.activity.RecordDtailsPresenter;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.RecordDtailsContract;
import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.Ui.adapter.CommentAdapter;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.ProgressUtils;
import com.example.habin.lostpropertyproject.Util.SnackbarUtils;
import com.example.habin.lostpropertyproject.Util.StatusBarUtils;
import com.example.habin.lostpropertyproject.Util.StringUtils;
import com.example.habin.lostpropertyproject.Util.ToastUtils;
import com.example.habin.lostpropertyproject.Util.UiUtils;
import com.example.habin.lostpropertyproject.Widget.CircleImageView;
import com.google.gson.reflect.TypeToken;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * created by habin
 * on 2020/1/2
 * Email 739115041@qq.com
 * 物品详细页面
 */
public class RecordDetailsActivity extends BaseMVPActivity<RecordDtailsContract.Presenter> implements RecordDtailsContract.View {


    @BindView(R.id.civ_pic)
    CircleImageView mCivPic; //用户头像
    @BindView(R.id.tv_nickname)
    TextView mTvNickname; //用户昵称
    @BindView(R.id.tv_release_time)
    TextView mTvReleaseTime; //发布时间
    @BindView(R.id.tv_note_context)
    TextView mTvNoteContext; //发布内容
    @BindView(R.id.iv_content_pic)
    ImageView mIvContentPic; //发布图片
    @BindView(R.id.tv_address)
    TextView mTvAddress; //发布地址
    @BindView(R.id.tv_find_time)
    TextView mTvFindTime; //丢失时间
    @BindView(R.id.ll_bom_help)
    LinearLayout mLlBomHelp; //其他用户浏览显示
    @BindView(R.id.tv_vpNum)
    TextView mTvVpNum; //图片数量
    @BindView(R.id.tv_type_name)
    TextView mTvTypeName; //类型名字
    @BindView(R.id.ll_bom_Set)
    LinearLayout mLlBomSet; //个人显示
    @BindView(R.id.iv_edit)
    ImageView mIvEdit; // 修改信息
    @BindView(R.id.ll_back_time)
    LinearLayout mLlBackTime; //完成时间框
    @BindView(R.id.tv_backTime)
    TextView mTvBackTime; //完成时间
    @BindView(R.id.tv_back_name)
    TextView mTvBackName; //完成/取消
    @BindView(R.id.rv_comment_list)
    RecyclerView mRvCommentList; //评论列表
    @BindView(R.id.tv_comment_total)
    TextView mTvCommentTotal; //评论数
    @BindView(R.id.tv_great_num)
    TextView mTvGreatNum;//点赞数
    @BindView(R.id.iv_great)
    ImageView mIvGreat;

    private boolean isVis; //判断是否显示底部栏
    private boolean isGreat = false;//是否点赞过
    private ArticleInfoEntity.ResultBean data;
    private List<String> imgList; //图片地址
    private BottomSheetDialog dialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_detail;
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected void initParam() {
        super.initParam();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //是否显示底部栏
            isVis = extras.getBoolean(Constants.IS_SHOW, false);
            data = (ArticleInfoEntity.ResultBean) extras.getSerializable(Constants.ACTICLEINFO_DATA);
            if (data != null && data.getImgStr() != null) {

                List<String> imgStrList = JsonUtil.StringToList(data.getImgStr());
                imgList = new ArrayList<>();
                for (String imgstr : imgStrList) {
                    imgList.add(imgstr);
                }
            }
        }
    }

    @Override
    protected void initView() {
        StatusBarUtils.transparencyBar(mActivity);

        if (isVis) {
            mLlBomHelp.setVisibility(View.VISIBLE);
        } else {
            if (data.getRecordStatus() < 3) {
                mLlBomSet.setVisibility(View.VISIBLE);
                mIvEdit.setVisibility(View.VISIBLE);
            } else {
                mLlBackTime.setVisibility(View.VISIBLE);
                String TimeName = data.getRecordStatus() == 3 ? "完成时间:" : "取消时间:";
                mTvBackName.setText(TimeName);

            }

        }
        if (data.getPersonInfo().getProfileImg()!=null) {
            List<String> strings = JsonUtil.StringToList(data.getPersonInfo().getProfileImg());
            UiUtils.GildeLoad(mContext, mCivPic, strings.get(0));
        }
        if (imgList != null && imgList.size() > 0) {
            UiUtils.GildeLoad(mContext, mIvContentPic, imgList.get(0));
            mTvVpNum.setText(String.format("%1$d/%2$d", 1, imgList.size()));
        }
        mTvTypeName.setText(StringUtils.typeIdToName(data.getTypeId()));
        mTvNickname.setText(data.getPersonInfo().getNickname());
        mTvReleaseTime.setText(StringUtils.getTimeFormatText(data.getCreateTime()));
        mTvAddress.setText(data.getAddressContent());
        mTvFindTime.setText(StringUtils.stampToDate(data.getFindTime()));
        mTvNoteContext.setText(data.getDescription());
        if (data.getCommentList()!=null&&data.getCommentList().size()>0){
            mTvCommentTotal.setText("全部评论("+data.getCommentList().size()+")");
        }
        if (data.getBackTime()!=0){
            mTvBackTime.setText(StringUtils.stampToDate(data.getBackTime()));
        }
        //评论列表
        mRvCommentList.setLayoutManager(new LinearLayoutManager(this));
        mRvCommentList.setAdapter(new CommentAdapter(mContext,data.getCommentList()));
        //点赞数
        List<Great> greats = data.getGreatList();

        if (greats!=null&&greats.size()>0){
            mTvGreatNum.setText(greats.size()+"");
            int userId = MyApplication.getUserId(mContext);
            for (Great great:greats){
                if (great.getUserId() == userId){
                    isGreat = true;
                    mIvGreat.setImageResource(R.drawable.icon_work_dolike);
                    break;
                }
            }
        } else {
            mTvGreatNum.setText("0");
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.rl_content_pic,R.id.btn_share,R.id.iv_edit, R.id.iv_back,
            R.id.btn_success, R.id.btn_quit, R.id.btn_call, R.id.civ_pic,
            R.id.btn_help, R.id.btn_private_chat,R.id.detail_page_do_comment,
            R.id.iv_great})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_content_pic:
                if (imgList != null && imgList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.OPEN_PIC_POSITION, 0);
                    bundle.putSerializable(Constants.OPEN_PIC_MEDIALAST, (Serializable) imgList);
                    startActivity(OpenPicActivity.class, bundle);
                    overridePendingTransition(R.anim.a5, 0);
                } else {
                    ToastUtils.show_s("图片为空");
                }



                break;
            case R.id.btn_share:
                UMImage image = new UMImage(mActivity,imgList.get(0));
                image.setThumb(new UMImage(mActivity,imgList.get(0)));//缩略图
                new ShareAction(mActivity)
                        .withText(data.getDescription())
                        .withMedias(image).setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)
                        .setCallback(shareListener).open();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.civ_pic:
                break;
            case R.id.btn_help:
                break;
            case R.id.btn_private_chat:
                break;
            case R.id.btn_success:
                mPresenter.updateArticle(data.getId(), 3);
                break;
            case R.id.btn_quit:
                mPresenter.updateArticle(data.getId(), 4);
                break;
            case R.id.btn_call:
                checkPermissionRequest(this);
                break;
            case R.id.iv_edit:
                ToastUtils.show_s("修改页面");
                Bundle bundle = new Bundle();
                bundle.putString(Constants.RELEASE_TYPE,String.valueOf(data.getStatus()));
                bundle.putSerializable(Constants.ACTICLEINFO_DATA,data);
                startActivity(ReleaseActivity.class,bundle);
                break;
            case R.id.detail_page_do_comment:
                showCommentDialog();
                break;
            case R.id.iv_great:
                int size = data.getGreatList().size();
                if (isGreat){
                    isGreat = false;
                    size = size - 1;
                    mIvGreat.setImageResource(R.drawable.icon_work_like);
                    mTvGreatNum.setText(size+"");
                    mPresenter.CancelLike(data.getId());
                } else {
                    isGreat = true;
                    mIvGreat.setImageResource(R.drawable.icon_work_dolike);
                     size = size + 1;
                    mTvGreatNum.setText(size+"");
                    mPresenter.AddLike(data.getId());
                }
                break;
        }
    }

    @SuppressLint("CheckResult")
    public void checkPermissionRequest(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.request(Manifest.permission.CALL_PHONE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            call();
                        } else {
                            SnackbarUtils.show(mActivity, "拒绝权限");
                            call();
                        }

                    }
                });
    }

    public void call() {
        // 执行拨号动作
        Intent mIntent = new Intent(Intent.ACTION_CALL);
        mIntent.setData(Uri.parse("tel:" + data.getPhone()));
        startActivity(mIntent);
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.show_s(mContext,"分享成功");
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.show_s(mContext,"失败"+t.getMessage());
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.show_s(mContext,"取消分享");
        }
    };
    @Override
    public void onSuccess(HttpHelper.TaskType type, HttpItem item) {
        switch (type) {
            case updateArticleStatus:
                ToastUtils.show_s(mContext, item.getMessage());
                break;
            case AddGreat:
            case DeleteGreat:
            case AddComment:
                ToastUtils.show_s(mContext, item.getMessage());
                ProgressUtils.show(mContext,"正在刷新");
                mPresenter.reArticle(data.getId());
                break;
        }
    }

    @Override
    public void onSuccess(HttpHelper.TaskType type, JSONObject object) {
        ProgressUtils.dismiss();
        switch (type){
            case QueryArticleInfo:
                ArticleInfoEntity articleInfoEntity = JsonUtil.JSONObjectToBean(object, ArticleInfoEntity.class);
                data = articleInfoEntity.getResult().get(0);
                initView();
                break;
        }
    }

    @Override
    public void onFailure(HttpHelper.TaskType type, ApiError e) {
        ProgressUtils.dismiss();
        switch (type) {
            case updateArticleStatus:
            case AddComment:
                ToastUtils.show_s(mContext, e.getMessage());
                break;
        }
    }

    @Override
    protected RecordDtailsContract.Presenter bindPresenter() {
        return new RecordDtailsPresenter();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this,R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
//        View parent = (View) commentView.getParent();
//        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
//        commentView.measure(0,0);
//        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    mPresenter.addComment(data.getId(),commentContent);
                }else {
                    ToastUtils.show_s(mContext,"评论内容不能为空");
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>1){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }


}
