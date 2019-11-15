package com.from.civilusecar.mvp.model;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.mvp.contract.MainConstract;
import com.from.civilusecar.mvp.contract.MonitorConstranct;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class MonitorModel implements MonitorConstranct.Model {
    @Override
    public Observable<BaseResponse<QueryCarListsBean>> queryCarList(HashMap<String, String> params) {
        return Api.getApiService().queryCarList(params);
    }
}
