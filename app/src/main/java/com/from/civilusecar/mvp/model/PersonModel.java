package com.from.civilusecar.mvp.model;

import com.from.civilusecar.mvp.contract.PersonContract;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class PersonModel implements PersonContract.Model {
    @Override
    public Observable<BaseResponse> getData(HashMap<String, String> params) {
        return null;
    }
}
