package com.from.civilusecar.bean;

import java.io.Serializable;

public class AdvertisementInfo  implements Serializable {
    /**
     * advertType	图片用途	number
     * advertUrl	超链接	string
     * android	安卓图片url	string
     * drawOrder	显示顺序	number
     * drawPosition	显示位置	number
     * iphoneImgUrl	iphone图片url	string
     * title	标题	string
     */

    Integer advertType;

    String advertUrl;

    String android;

    Integer drawOrder;

    Integer drawPosition;

    String iphoneImgUrl;

    String title;

    public Integer getAdvertType() {
        return advertType;
    }

    public void setAdvertType(Integer advertType) {
        this.advertType = advertType;
    }

    public String getAdvertUrl() {
        return advertUrl;
    }

    public void setAdvertUrl(String advertUrl) {
        this.advertUrl = advertUrl;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public Integer getDrawOrder() {
        return drawOrder;
    }

    public void setDrawOrder(Integer drawOrder) {
        this.drawOrder = drawOrder;
    }

    public Integer getDrawPosition() {
        return drawPosition;
    }

    public void setDrawPosition(Integer drawPosition) {
        this.drawPosition = drawPosition;
    }

    public String getIphoneImgUrl() {
        return iphoneImgUrl;
    }

    public void setIphoneImgUrl(String iphoneImgUrl) {
        this.iphoneImgUrl = iphoneImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
