package com.from.civilusecar.mvp.model;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.bean.UserByUserListBean;
import com.from.civilusecar.mvp.contract.ManagerDriverConstract;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class ManagerDriverModel implements ManagerDriverConstract.Model {
    @Override
    public Observable<BaseResponse<UserByUserListBean>> queryCarLists(HashMap<String, String> params) {
        return Api.getApiService().queryUserByCar(params);
    }

    @Override
    public Observable<BaseResponse> unbindCar(HashMap<String, String> params) {
        return Api.getApiService().unbindCar(params);
    }
}
