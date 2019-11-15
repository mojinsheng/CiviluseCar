package com.from.civilusecar.bean;

import java.io.Serializable;

public class NearCarInfoBean implements Serializable {
       private String surplusDistance;
       private String carTypeName;
       private String carName;
       private Integer carTypeId;
       private String carPlate;
       private Integer carId;
       private String carLng;
       private String carLat;
       private Integer distance;
       private String carTypeImgUrl;
       private String surplusPercent;
       private String lng;
       private String lat;

    public String getSurplusDistance() {
        return surplusDistance;
    }

    public void setSurplusDistance(String surplusDistance) {
        this.surplusDistance = surplusDistance;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Integer getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Integer carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarLng() {
        return carLng;
    }

    public void setCarLng(String carLng) {
        this.carLng = carLng;
    }

    public String getCarLat() {
        return carLat;
    }

    public void setCarLat(String carLat) {
        this.carLat = carLat;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getCarTypeImgUrl() {
        return carTypeImgUrl;
    }

    public void setCarTypeImgUrl(String carTypeImgUrl) {
        this.carTypeImgUrl = carTypeImgUrl;
    }

    public String getSurplusPercent() {
        return surplusPercent;
    }

    public void setSurplusPercent(String surplusPercent) {
        this.surplusPercent = surplusPercent;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
