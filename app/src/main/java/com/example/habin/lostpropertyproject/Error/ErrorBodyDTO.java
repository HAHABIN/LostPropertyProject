package com.example.habin.lostpropertyproject.Error;

/**
 * Create by HABIN on 2019/11/5
 * Time：23:40
 * Email:739115041@qq.com
 *
 * 网络请求返回  实体类
 */
public class ErrorBodyDTO {
    private String errCode;
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}