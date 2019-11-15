package com.from.civilusecar.mvp.model;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.mvp.contract.DriverConstract;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class DriverModel implements DriverConstract.Model
{
    @Override
    public Observable<BaseResponse> shareCar(HashMap<String, String> params) {
        return Api.getApiService().shareCar(params);
    }
}
