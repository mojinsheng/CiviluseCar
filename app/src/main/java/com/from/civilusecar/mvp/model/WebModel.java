package com.from.civilusecar.mvp.model;

import com.from.civilusecar.mvp.contract.SettingCarNameContract;
import com.from.civilusecar.mvp.contract.WebConstract;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class WebModel implements WebConstract.Model {
    @Override
    public Observable<BaseResponse> webData(HashMap<String, String> params) {
        return null;
    }
}
