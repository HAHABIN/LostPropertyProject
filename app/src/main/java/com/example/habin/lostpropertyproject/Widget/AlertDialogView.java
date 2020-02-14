package com.example.habin.lostpropertyproject.Widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.habin.lostpropertyproject.R;

/**
 * 弹窗提示
 */
public class AlertDialogView extends Dialog {

    public interface onClickListener {
        void cancelClick(AlertDialogView dialog);
        void confirmClick(AlertDialogView dialog);
    }

    private Button btn_yes;//确定按钮
    private Button btn_no;//取消按钮
    private View v_line; // 中间的分割线
    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    private String cancelStr;
    private String confimStr;
    private boolean mIsBtn_noShow = true;//是否显示取消按钮

    private onClickListener listener;

    public AlertDialogView(Context context) {
        super(context, R.style.custom_dialog);
    }

    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setMessage(String message) {
        messageStr = message;
    }

    public void setCancelStr(String cancelStr) {
        this.cancelStr = cancelStr;
    }

    public void setConfimStr(String confimStr) {
        this.confimStr = confimStr;
    }

    public void IsBtn_noShow (boolean isShwo){
        this.mIsBtn_noShow = isShwo;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dlg_layout);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setupView();
    }

    private void setupView() {
        btn_yes = findViewById(R.id.yes);
        btn_no = findViewById(R.id.no);
        v_line = findViewById(R.id.v_line);
        titleTv = findViewById(R.id.title);
        messageTv = findViewById(R.id.message);

        btn_yes.setOnClickListener(setViewClick());
        btn_no.setOnClickListener(setViewClick());
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        if (!mIsBtn_noShow){
            btn_no.setVisibility(View.GONE);
        }
        if (confimStr!= null){
            btn_yes.setText(confimStr);
        }
        if (cancelStr!=null){
            btn_no.setText(cancelStr);
        }

    }

    private View.OnClickListener setViewClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    if (v.getId() == R.id.yes) {
                        listener.confirmClick( AlertDialogView.this);
                    }
                    else {
                        listener.cancelClick(AlertDialogView.this);
                    }
                    dismiss();
                }
            }
        };
    }
}
