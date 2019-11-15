package com.from.civilusecar.mvp.model;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.bean.ImgUrlListBean;
import com.from.civilusecar.mvp.contract.HeadConstract;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HeadModel implements HeadConstract.Model {
    @Override
    public Observable<BaseResponse<ImgUrlListBean>> heads(HashMap<String, String> params, MultipartBody.Part body) {
        return Api.getApiService().imageUpload(params,body).subscribeOn(Schedulers.io());
    }
}
