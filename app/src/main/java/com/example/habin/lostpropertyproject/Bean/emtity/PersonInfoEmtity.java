package com.example.habin.lostpropertyproject.Bean.emtity;

import com.example.habin.lostpropertyproject.Bean.HttpItem;

import java.io.Serializable;
import java.util.Date;

/**
 * Create by HABIN on 2020/1/121:11
 * Email:739115041@qq.com
 */

public class PersonInfoEmtity extends HttpItem {
    /**
     * result : {"settings":{"minVersion":"1","upgradeUrl":null,"updateUrl":null,"intro":"版本升级","launchImage":"https://zcyx.pt-ts.my-campus.cn/uploads/2019/08/f9a1798ef543470fb684a78ee7712789_720x1280.jpg","residence":3000,"currentVersion":"1"},"afterSaleUrl":"https://zcyx.pt-ts.my-campus.cn/app/integral/afteSaleInfo?XPS-ClientCode=YNJG","aboutUrl":"https://zcyx.pt-ts.my-campus.cn/app/about/about?XPS-ClientCode=YNJG","commonQA":"https://zcyx.pt-ts.my-campus.cn/app/integral/commonQA?XPS-ClientCode=YNJG","ecard":"https://zcyx.pt-ts.my-campus.cn/app/integral/ecard?XPS-ClientCode=YNJG","payCenterUrl":"https://zcyx.pt-ts.my-campus.cn/app/integral/refundinfo?XPS-ClientCode=YNJG","integralUrl":"https://zcyx.pt-ts.my-campus.cn/app/integral/integral?XPS-ClientCode=YNJG","appMenuUpdate":1565340575173,"contributeUrl":"https://zcyx.pt-ts.my-campus.cn/app/integral/contribute?XPS-ClientCode=YNJG","operate":"https://zcyx.pt-ts.my-campus.cn/app/integral/operate?XPS-ClientCode=YNJG","channelMenus":[{"id":76,"status":"7","name":"首页","menuCode":"channel_1","imageOne":"https://img.my-campus.cn/uploads/initPicture/bottomImage/1-1.png","imageTwo":"https://img.my-campus.cn/uploads/initPicture/bottomImage/1-2.png"},{"id":77,"status":"7","name":"校园","menuCode":"channel_2","imageOne":"https://img.my-campus.cn/uploads/initPicture/bottomImage/2-1.png","imageTwo":"https://img.my-campus.cn/uploads/initPicture/bottomImage/2-2.png"},{"id":78,"status":"7","name":"生活","menuCode":"channel_3","imageOne":"https://img.my-campus.cn/uploads/initPicture/bottomImage/3-1.png","imageTwo":"https://img.my-campus.cn/uploads/initPicture/bottomImage/3-2.png"},{"id":79,"status":"7","name":"同学","menuCode":"channel_4","imageOne":"https://img.my-campus.cn/uploads/initPicture/bottomImage/4-1.png","imageTwo":"https://img.my-campus.cn/uploads/initPicture/bottomImage/4-2.png"},{"id":80,"status":"7","name":"我","menuCode":"channel_5","imageOne":"https://zcyx.pt-ts.my-campus.cn/uploads/2019/07/8b0a60920aad45fcb8be2254cd3182fd_72x72.png","imageTwo":"https://zcyx.pt-ts.my-campus.cn/uploads/2019/07/d56a730eb1d0441987bf1e1ed84ee56b_72x72.png"}],"registerQA":"https://zcyx.pt-ts.my-campus.cn/app/integral/registerQA?XPS-ClientCode=YNJG","config":{"shop_orders_notice":"抱歉，由于系统出现异常或正在升级维护，所以，暂时不能支付，敬请原谅！","umeng_push_ios_key":"5a43849da40fa37bd900001d","android_download_url":"","yly_api_key":"450fbc8a05f0d07816a92cbf14103967fa633b0d","stop_ecard":"1","umeng_push_android_key":"5a438454f43e481a60000011","orders_stop_sms":"0","cancel_orders_reason":"点错商品&不想要了&其他","pay_type":"1","pay_max_integral":"100","sensitive_word":"fuck|SB|尼玛|操你妈|去你妈","tyfw":"你报名的项目{title}扣款{money}元成功！","yly_user_id":"12844","xqsum":"20","shop_orders_control":"1","giftAddress":"以礼品页面说明为准","umeng_push_ios_ams":"7yiobzkzxvjq3bd5a7r0bkh5o3tnek8y","orders_repeat_phone":"13424025610","share_count":"2","start_time":"2017-09-04","ios_download_url":"https://itunes.apple.com/cn/app/hua-shange-jia/id1024942258?l=en&mt=8","bottom_image":"0","xn":"2017-2018","xq":"1","apply_refund_time":"12","umeng_push_android_ams":"gfocvvysurmoaztpjs0cdphj5vwxfajy","refund_sms_control":"1"}}
     */

    private ResultBean data;

    public ResultBean getData() {
        return data;
    }

    public void setData(ResultBean data) {
        this.data = data;
    }

    public static class ResultBean implements Serializable {

        /**
         * {
         * "userId": 1,
         * "name": "1",
         * "profileImg": null,
         * "email": null,
         * "gender": null,
         * "userType": 3,
         * "createTime": 1573016531000,
         * "lastEditTime": 1573016534000,
         * "helpTimes": 3
         * }
         */
        //ID
        private int userId;
        //名字
        private String name;
        //头像
        private String profileImg;
        //邮箱
        private String email;
        //性别
        private String gender;
        //1.店家 2.顾客 3.超级管理员
        private int userType;
        //创建时间
        private long createTime;
        //上次更新时间，。
        private long lastEditTime;
        //帮助次数
        private int helpTimes;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfileImg() {
            return profileImg;
        }

        public void setProfileImg(String profileImg) {
            this.profileImg = profileImg;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getLastEditTime() {
            return lastEditTime;
        }

        public void setLastEditTime(long lastEditTime) {
            this.lastEditTime = lastEditTime;
        }

        public int getHelpTimes() {
            return helpTimes;
        }

        public void setHelpTimes(int helpTimes) {
            this.helpTimes = helpTimes;
        }
    }
}
