package com.example.habin.lostpropertyproject.Bean;

/**
 * create by Vincent on 2019/7/4 15:28
 * describe :上传图片参数
 */
public class UploadPhotoParams {

    private String imgStr; //图片路径
    private String resourceType;  //图片类型

    private UploadPhotoParams(Builder builder) {
        setImgStr(builder.imgStr);
        setResourceType(builder.resourceType);
    }

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


    public static final class Builder {
        private String imgStr;
        private String resourceType;

        public Builder() {
        }

        public Builder imgStr(String val) {
            imgStr = val;
            return this;
        }

        public Builder resourceType(String val) {
            resourceType = val;
            return this;
        }

        public UploadPhotoParams build() {
            return new UploadPhotoParams(this);
        }
    }
}
