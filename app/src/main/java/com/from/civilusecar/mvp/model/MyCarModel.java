package com.from.civilusecar.mvp.model;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.mvp.contract.MyCarContract;
import com.from.civilusecar.mvp.contract.PersonContract;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class MyCarModel implements MyCarContract.Model {
    @Override
    public Observable<BaseResponse<QueryCarListsBean>> queryCarList(HashMap<String, String> params) {
        return Api.getApiService().queryCarList(params);
    }

    @Override
    public Observable<BaseResponse> changeDefaultCar(HashMap<String, String> params) {
        return Api.getApiService().changeDefaultCar(params);
    }
}
