package com.from.civilusecar.bean;

import com.xslong.xslonglib.utils.MD5;
import com.xslong.xslonglib.utils.StringUtils;

import java.io.Serializable;

public class BluetoothCmdInfoYidaBean implements Serializable {
   private String mac;
   private String pwd;
   private String name;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
