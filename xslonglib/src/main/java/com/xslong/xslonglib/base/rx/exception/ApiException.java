package com.xslong.xslonglib.base.rx.exception;


import com.xslong.xslonglib.base.app.AppConfig;

/**
 * Desc :
 * Created by huangyue on 2017/10/10.
 */

public class ApiException extends RuntimeException {
    private int errCode;

    public ApiException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }


    /**
     * Token是否已失效
     */
    public boolean isTokenExpried() {
        return errCode == AppConfig.TOKEN_EXPRIED;
    }

    public int getCode() {
        return errCode;
    }
}
