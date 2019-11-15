package com.from.civilusecar.bis;

/**
 * Created by Administrator on 2018/4/2.
 */

public class TaskInfo {
    private Integer orderNum;
    private Integer standardNum;
    private Double orderTotalMoney;
    private Double standardMoney;
    private Long canSignOutTime;

    public Long getCanSignOutTime() {
        return canSignOutTime;
    }

    public void setCanSignOutTime(Long canSignOutTime) {
        this.canSignOutTime = canSignOutTime;
    }

    public Double getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(Double orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public Double getStandardMoney() {
        return standardMoney;
    }

    public void setStandardMoney(Double standardMoney) {
        this.standardMoney = standardMoney;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getStandardNum() {
        return standardNum;
    }

    public void setStandardNum(Integer standardNum) {
        this.standardNum = standardNum;
    }
}
