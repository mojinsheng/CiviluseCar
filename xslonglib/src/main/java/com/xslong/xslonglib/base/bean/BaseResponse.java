package com.xslong.xslonglib.base.bean;

import java.io.Serializable;

/**
 * @author xslong
 * @time 2017/7/11  11:58
 * @desc ${TODD}
 */


public class BaseResponse<T> implements Serializable {
    private int state;
    private T data;
    private String msg;
    private String systemTime;
    private int SUCCESS;

    public int getSUCCESS() {
        return SUCCESS;
    }

    public void setSUCCESS(int SUCCESS) {
        this.SUCCESS = SUCCESS;
    }

    public T getData() {
        return data;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean success() {
        return state == 1||state == 0;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + state + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", systemTime='" + systemTime + '\'' +
                '}';
    }
}
