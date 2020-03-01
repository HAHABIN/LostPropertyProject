package com.example.habin.lostpropertyproject.Bean.entity;

/**
 * create by Vincent on 2019/7/4 15:28
 * describe :上传图片参数
 */
public class UploadPhotoParams {

    private String imgStr; //图片路径
    private String resourceType;  //图片类型

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

}
