package com.from.civilusecar.mvp.model;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.mvp.contract.BindConstract;
import com.from.civilusecar.mvp.contract.PersonContract;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class BindModel implements BindConstract.Model {
    @Override
    public Observable<BaseResponse> bindCar(HashMap<String, String> params) {
        return Api.getApiService().bindCar(params);
    }

    @Override
    public Observable<BaseResponse> unbindCar(HashMap<String, String> params) {
        return Api.getApiService().unbindCar(params);

    }
}
