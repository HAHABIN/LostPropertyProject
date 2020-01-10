package com.example.habin.lostpropertyproject.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.R;
import com.example.habin.lostpropertyproject.ui.adapter.SelectRecyclerAdapter;

import java.util.List;

/**
 * 选择对话框
 */
public class SelectDialog extends Dialog implements OnClickListener ,SelectRecyclerAdapter.OnitemClick{

    private SelectDialogListener mListener;
    private Activity mActivity;
    private Button mBtnCancel;
    private TextView mTvTitle;
    private List<String> mName;
    private String mTitle;
    private boolean cancelDismiss = true;



    public interface SelectDialogListener {
        public void onItemClick(int position);
    }

    /**
     * 取消事件监听接口
     */
    private SelectDialogCancelListener mCancelListener;

    public interface SelectDialogCancelListener {
        public void onCancelClick();
    }


    public SelectDialog(Activity activity, SelectDialogListener listener, List<String> names) {
        super(activity, R.style.transparentFrameWindowStyle);
        mActivity = activity;
        mListener = listener;
        this.mName = names;

        setCanceledOnTouchOutside(true);
    }

    /**
     * @param activity       调用弹出菜单的activity
     * @param listener       菜单项单击事件
     * @param cancelListener 取消事件
     * @param names          菜单项名称
     */
    public SelectDialog(Activity activity, SelectDialogListener listener, SelectDialogCancelListener cancelListener, List<String> names) {
        super(activity,R.style.transparentFrameWindowStyle);
        mActivity = activity;
        mListener = listener;
        mCancelListener = cancelListener;
        this.mName = names;
        cancelDismiss = true;
        // 设置是否点击外围解散
        setCanceledOnTouchOutside(true);
    }

    /**
     * @param activity 调用弹出菜单的activity
     * @param listener 菜单项单击事件
     * @param names    菜单项名称
     * @param title    菜单标题文字
     */
    public SelectDialog(Activity activity, SelectDialogListener listener, List<String> names, String title) {
        super(activity,R.style.transparentFrameWindowStyle);
        mActivity = activity;
        mListener = listener;
        this.mName = names;
        mTitle = title;

        // 设置是否点击外围可解散
        setCanceledOnTouchOutside(true);
    }

    public SelectDialog(Activity activity, SelectDialogListener listener, SelectDialogCancelListener cancelListener, List<String> names, String title) {
        super(activity, R.style.transparentFrameWindowStyle);
        mActivity = activity;
        mListener = listener;
        mCancelListener = cancelListener;
        this.mName = names;
        mTitle = title;
        cancelDismiss = true;
        // 设置是否点击外围可解散
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.view_dialog_select, null);
        setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        onWindowAttributesChanged(wl);

        initViews();
    }

    private void initViews() {


        mBtnCancel =  findViewById(R.id.btn_cancel);
        mTvTitle = findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(mTitle) && mTvTitle != null) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(mTitle);
        } else {
            mTvTitle.setVisibility(View.GONE);
        }

        SelectRecyclerAdapter dialogAdapter = new SelectRecyclerAdapter(getContext(),mName,SelectDialog.this);
        dialogAdapter.setTitleStatus(mTvTitle.getVisibility()==View.VISIBLE);
        RecyclerView dialogRecyclerView = findViewById(R.id.dialog_recycler);
        dialogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogRecyclerView.setAdapter(dialogAdapter);

        //取消按钮
        mBtnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (cancelDismiss && mCancelListener != null) {
                    mCancelListener.onCancelClick();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    //Recycler item点击事件
    @Override
    public void onItemClick(int position) {
        mListener.onItemClick(position);
        cancelDismiss = false;
        dismiss();
    }




}
