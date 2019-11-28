package com.example.habin.lostpropertyproject.Bean;

/**
 * Create by HABIN on 2019/11/616:06
 * Email:739115041@qq.com
 */
public class BaseResponse<T> {
    private boolean success;
    private String errMsg;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }


    public boolean isSuccess(){
        return success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success=" + success +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
