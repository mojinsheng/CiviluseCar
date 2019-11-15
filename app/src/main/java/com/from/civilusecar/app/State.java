package com.from.civilusecar.app;

public interface State {
    /**
     * 用户未设置密码
     */
    int USER_NO_PASSWORD = 13;
    /**
     * 用户账号状态异常（删除或禁用）
     */
    int ACCOUNT_EXCEPTION = 12;
    /**
     * 用户未登录
     */
    int NOT_LOGGED_IN = 11;
    /**
     * 用户未注册
     */
    int UNREGISTERED = 10;
    /**
     * 物流距离超限
     */
    int LOGISTICS_DISTANCE_OUT = 6;
    /**
     * 签名验证出错
     */
    int SIGN_ERROR = 5;
    /**
     * 请求超时
     */
    int TIMEOUT = 4;
    /**
     * 关锁
     */
    int ID_USH_SEND = 3;
    /**
     * 缺少参数
     */
    int MISSING_PARAMETER = 2;
    /**
     * 成功
     */
    int SUCCESS = 1;
    /**
     * 失败，系统错误
     */
    int FAILURE = 0;
    /**
     * Session无效
     */
    int INVALID_SESSION = -1;
    /**
     * 无权限操作
     */
    int NO_ACCESS = -2;
}
