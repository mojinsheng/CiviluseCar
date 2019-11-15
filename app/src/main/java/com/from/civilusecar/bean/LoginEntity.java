package com.from.civilusecar.bean;

import org.greenrobot.greendao.annotation.Entity;

public class LoginEntity {
    private CustomerInfo customerInfo;

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
}
