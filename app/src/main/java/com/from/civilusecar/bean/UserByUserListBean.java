package com.from.civilusecar.bean;

import java.util.List;

public class UserByUserListBean {
    private List<UserByCarBean> userList;

    public List<UserByCarBean> getUserByCarBean() {
        return userList;
    }

    public void setUserByCarBean(List<UserByCarBean> _userList) {
        this.userList = _userList;
    }
}
