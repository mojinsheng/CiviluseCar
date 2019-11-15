package com.from.civilusecar.api;


import com.xslong.xslonglib.retrofit.RxRetrofit;

/**
 * Desc :
 */
public class Api {
    public static ApiService mApiService;
    public static final String BASE_URL = "http://139.9.93.206:9000/hiapi/";
    public static final String BASE_URL_IMG= "http://file.zsydcloud.com";

    public static ApiService getApiService() {
        if (mApiService == null) {
            mApiService = RxRetrofit.getRetrofit(BASE_URL).create(ApiService.class);
        }
        return mApiService;
    }

}
