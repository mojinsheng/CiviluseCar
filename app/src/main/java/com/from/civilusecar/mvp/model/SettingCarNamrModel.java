package com.from.civilusecar.mvp.model;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.mvp.contract.SettingCarNameContract;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class SettingCarNamrModel implements SettingCarNameContract.Model {

    @Override
    public Observable<BaseResponse> unbindCar(HashMap<String, String> params) {
        return Api.getApiService().unbindCar(params);
    }

    @Override
    public Observable<BaseResponse> changeCarName(HashMap<String, String> params) {
        return Api.getApiService().changeCarName(params);
    }
}
