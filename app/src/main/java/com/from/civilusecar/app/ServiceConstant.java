package com.from.civilusecar.app;

/**
 * Created by Administrator on 2017/5/8.
 */

public interface ServiceConstant {

    /**
     * 路径
     */
    String PATH = "clientapi";

    /**
     * 基础Hostname
     */

//    String BASE_HOSTNAME = "10.200.20.124:9320";
            //本地192.168.0.188
            //线上139.159.200.145
    String BASE_HOSTNAME = "139.159.200.145:9000";
//    String BASE_HOSTNAME = "leowebha.bom-capital.com:9988";

    /**
     * 基础服务器地址
     */
    String BASE_SERVER_URL = "http://" + BASE_HOSTNAME + "/";

    /**
     * 协议链接
     */
    String BASE_XIEYI_URL = "http://139.159.200.145:9003/";

    /**
     * 图片服务器地址
     */
    String BASE_STATIC_URL = BASE_SERVER_URL;

    String BUGLY_APPID = "c68c9f6e30";

    /**
     * 关于我们
     */
    String ABOUTUS = BASE_XIEYI_URL + "statics/h5/aboutUs.html";

    /**
     * 用户协议
     */
    String USER_CLAUSE = BASE_XIEYI_URL + "statics/h5/userAgreement.html";

    /**
     * 充值协议
     */
    String RECHARGE_CLAUSE = BASE_XIEYI_URL + "statics/h5/RechargeAgreement.html";

    /**
     * 用户指南
     */
    String USER_GUIDE = BASE_XIEYI_URL + "statics/h5/userGuide.html";

    /**
     * 会员费协议
     */
    String DEPOSIT_CLAUSE = BASE_XIEYI_URL + "statics/h5/MarginAgreement.html";

    /**
     * 分享
     */
    String SHARE_URL = BASE_SERVER_URL + "web/share/share";

}
