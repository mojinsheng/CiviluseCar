package com.from.civilusecar.bean;

import java.io.Serializable;
import java.util.List;

public class CarInfo implements Serializable {
//
//    {"carInfo":{"surplusDistance":"0.0",
//            "carTypeName":"GT-sharingbike 2","carName":"测试组力单车","carTypeId":4,
//            "carPlate":"003437678","carId":2061,"lat":"22.901126309809644","lng":"113.81231690277859","
//            imgList":[{"id":80,"picType":1,"imgUrl":"\\statics\\upload\\images\\carstatusimg\\20190604/1559632485245728.png","carInfoId":2061,"entityId":80}],
//            "distance":0.0,"carTypeImgUrl":null,"surplusPercent":"0.0","carSn":"003437678","direction":null}}}
    //private List<CarImageInfo> imgList;
    private String surplusDistance;
    private String carTypeName;
    private String carName;
    private Integer carTypeId;
    private String carPlate;
    private Integer carId;
    private String lat;
    private String lng;
   // private String distance;
    private String carTypeImgUrl;
    private String surplusPercent;
    private String carSn;
    private String direction;

//    public List<CarImageInfo> getImgList() {
//        return imgList;
//    }
//
//    public void setImgList(List<CarImageInfo> imgList) {
//        this.imgList = imgList;
//    }

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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
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

    public String getCarSn() {
        return carSn;
    }

    public void setCarSn(String carSn) {
        this.carSn = carSn;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
