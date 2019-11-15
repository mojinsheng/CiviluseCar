package com.from.civilusecar.mvp.model;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.bean.AdvertisementInfo;
import com.from.civilusecar.bean.AdvertisementList;
import com.from.civilusecar.mvp.contract.WelcomeContract;
import com.orhanobut.logger.Logger;
import com.xslong.xslonglib.base.BaseModel;
import com.xslong.xslonglib.base.BaseView;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WelcomeModel implements WelcomeContract.Model{
    @Override
    public Observable<BaseResponse<AdvertisementList>> advert(HashMap<String, String> params) {

        return Api.getApiService().advert(params);
    }


//
//        public static String createLinkStringByGet(Map<String, String> params) throws UnsupportedEncodingException {
//        List<String> keys = new ArrayList<String>(params.keySet());
//        Collections.sort(keys);
//        String prestr = "";
//        for (int i = 0; i < keys.size(); i++) {
//            String key = keys.get(i);
//            String value = params.get(key);
//            value = URLEncoder.encode(value, "UTF-8");
//            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
//                prestr = prestr + key + "=" + value;
//            } else {
//                prestr = prestr + key + "=" + value + "&";
//            }
//        }
//        return prestr;
//    }
}
