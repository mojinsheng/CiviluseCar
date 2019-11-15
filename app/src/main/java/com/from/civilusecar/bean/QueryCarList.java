package com.from.civilusecar.bean;

import java.io.Serializable;

public class QueryCarList implements Serializable {
    private String carPlate;
    private Integer carTypeId;
    private String latitude;
    private String longitude;
    private String direction;
    private Integer carId;
    private String carSn;
    private String isDefault;

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public Integer getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Integer carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarSn() {
        return carSn;
    }

    public void setCarSn(String carSn) {
        this.carSn = carSn;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
