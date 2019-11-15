package com.from.civilusecar.bean;

import java.io.Serializable;

public class RefreshCarBean {
    private RefreshCar carData;

    public RefreshCar getCarData() {
        return carData;
    }

    public void setCarData(RefreshCar carData) {
        this.carData = carData;
    }

    public class RefreshCar implements Serializable {
        String carId;
        String latitude;
        String longitude;
        Integer distance;
        String surplusPercent;
        String surplusDistance;
        String voltage;
        Integer accStatus;
        Integer lockStatus;

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
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

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public String getSurplusPercent() {
            return surplusPercent;
        }

        public void setSurplusPercent(String surplusPercent) {
            this.surplusPercent = surplusPercent;
        }

        public String getSurplusDistance() {
            return surplusDistance;
        }

        public void setSurplusDistance(String surplusDistance) {
            this.surplusDistance = surplusDistance;
        }

        public String getVoltage() {
            return voltage;
        }

        public void setVoltage(String voltage) {
            this.voltage = voltage;
        }

        public Integer getAccStatus() {
            return accStatus;
        }

        public void setAccStatus(Integer accStatus) {
            this.accStatus = accStatus;
        }

        public Integer getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(Integer lockStatus) {
            this.lockStatus = lockStatus;
        }
    }

}
