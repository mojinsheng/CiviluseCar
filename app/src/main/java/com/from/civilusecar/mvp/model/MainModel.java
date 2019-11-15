package com.from.civilusecar.mvp.model;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.bean.BluetoothCmdInfoYidaBean;
import com.from.civilusecar.bean.CarInfo;
import com.from.civilusecar.bean.CarInfoList;
import com.from.civilusecar.bean.FindCarBean;
import com.from.civilusecar.bean.LoginEntity;
import com.from.civilusecar.bean.NearCarInfoBean;
import com.from.civilusecar.bean.QueryCarList;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.bean.RefState;
import com.from.civilusecar.bean.RefreshCarBean;
import com.from.civilusecar.mvp.contract.MainConstract;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public class MainModel implements MainConstract.Model  {
    @Override
    public Observable<BaseResponse<FindCarBean>> findCar(HashMap<String, String> params) {
        return Api.getApiService().findCar(params);
    }

    @Override
    public Observable<BaseResponse<LoginEntity>> openlock(HashMap<String, String> params) {
        return null;
    }

    @Override
    public Observable<BaseResponse<CarInfoList>> queryOrgCarDetail(HashMap<String, String> params) {
        return Api.getApiService().carDetail(params);
    }

    @Override
    public Observable<BaseResponse<FindCarBean>> doorControl(HashMap<String, String> params) {
        return Api.getApiService().doorControl(params);

    }

    @Override
    public Observable<BaseResponse<QueryCarListsBean>> queryCarList(HashMap<String, String> params) {
        return Api.getApiService().queryCarList(params);
    }

    @Override
    public Observable<BaseResponse<RefState>> getNewBtInfo(HashMap<String, String> params) {
        return Api.getApiService().getNewBtInfo(params);
    }

    @Override
    public Observable<BaseResponse<RefreshCarBean>> refreshCar(HashMap<String, String> params) {
        return Api.getApiService().refreshCarData(params);
    }
}
