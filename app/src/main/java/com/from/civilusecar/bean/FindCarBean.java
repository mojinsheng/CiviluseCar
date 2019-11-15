package com.from.civilusecar.bean;

import java.io.Serializable;

public class FindCarBean {
    //    rtCode	string	返回码，0：成功；1：失败；3： 设备断开连接；4：车辆在线，但 无信息返回（很可能是设备即将掉 线）；6：命令重复。
//    sysCode
    private FindCarB refState;

    public FindCarB getRefState() {
        return refState;
    }

    public void setRefState(FindCarB refState) {
        this.refState = refState;
    }

    public class FindCarB implements Serializable {

        private String rtCode;
        private String sysCode;

        public String getRtCode() {
            return rtCode;
        }

        public void setRtCode(String rtCode) {
            this.rtCode = rtCode;
        }

        public String getSysCode() {
            return sysCode;
        }

        public void setSysCode(String sysCode) {
            this.sysCode = sysCode;
        }

    }
}
