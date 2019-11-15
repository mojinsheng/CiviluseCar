package com.from.civilusecar.bean;

import java.io.Serializable;

public class CarImageInfo implements Serializable {
    Long id;

    String imgUrl;

    Long carInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getCarInfoId() {
        return carInfoId;
    }

    public void setCarInfoId(Long carInfoId) {
        this.carInfoId = carInfoId;
    }
}
