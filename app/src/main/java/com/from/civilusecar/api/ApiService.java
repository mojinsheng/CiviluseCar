package com.from.civilusecar.api;



import com.from.civilusecar.bean.AdvertisementInfo;
import com.from.civilusecar.bean.AdvertisementList;
import com.from.civilusecar.bean.BluetoothCmdInfoYidaBean;
import com.from.civilusecar.bean.CarInfo;
import com.from.civilusecar.bean.CarInfoList;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.bean.FindCarBean;
import com.from.civilusecar.bean.ImgUrlListBean;
import com.from.civilusecar.bean.LoginEntity;
import com.from.civilusecar.bean.NearCarInfoBean;
import com.from.civilusecar.bean.QueryCarList;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.bean.RefState;
import com.from.civilusecar.bean.RefreshCarBean;
import com.from.civilusecar.bean.UserByUserListBean;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Desc :
 *
 * @author xslong
 */
public interface ApiService {
//    @FormUrlEncoded
//    @POST("api/login/login/")
//    Observable<BaseResponse<LoginEntity>> login(@FieldMap HashMap<String, String> params);
//
//    @FormUrlEncoded
//    @POST("api/login/register_driver/")
//    Observable<BaseResponse> register(@FieldMap HashMap<String, String> params);
//
//    @GET("api/driver/order/")
//    Observable<BaseResponse<List<OrderListBean>>> getOrderList(@QueryMap HashMap<String, String> params);
//
//    @GET("api/driver/order/")
//    Observable<BaseResponse<OrderListBean>> getOrderInfo(@QueryMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("advertiserment/queryAdvertisementList/")
    Observable<BaseResponse<AdvertisementList>> advert(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("account/login/")
    Observable<BaseResponse<LoginEntity>> login(@FieldMap HashMap<String, String> params);


    @FormUrlEncoded
    @POST("account/getPhoneCode/")
    Observable<BaseResponse> getSMSCode(@FieldMap HashMap<String, String> params);


    //寻车
    @FormUrlEncoded
    @POST("obdReal/findCar")
    Observable<BaseResponse<FindCarBean>> findCar(@FieldMap HashMap<String, String> params);

    //车辆详情
    @FormUrlEncoded
    @POST("carManage/queryDefaultBindCar")
    Observable<BaseResponse<CarInfoList>> carDetail(@FieldMap HashMap<String, String> params);

    //绑定车辆
    @FormUrlEncoded
    @POST("carManage/queryCarList")
    Observable<BaseResponse<QueryCarListsBean>> queryCarList(@FieldMap HashMap<String, String> params);

    //开关车门
    @FormUrlEncoded
    @POST("obdReal/doorControl")
    Observable<BaseResponse<FindCarBean>> doorControl(@FieldMap HashMap<String, String> params);


    //绑定车辆
    @FormUrlEncoded
    @POST("carManage/bind")
    Observable<BaseResponse> bindCar(@FieldMap HashMap<String, String> params);

    //车辆授权
    @FormUrlEncoded
    @POST("carManage/unbind")
    Observable<BaseResponse> unbindCar(@FieldMap HashMap<String, String> params);
    //获取蓝牙指令
    @FormUrlEncoded
    @POST("obdReal/getNewBtInfo")
    Observable<BaseResponse<RefState>> getNewBtInfo(@FieldMap HashMap<String, String> params);


    //车辆授权
    @FormUrlEncoded
    @POST("carManage/share")
    Observable<BaseResponse> shareCar(@FieldMap HashMap<String, String> params);

    //车辆使用者
    @FormUrlEncoded
    @POST("carManage/queryUserByCar")
    Observable<BaseResponse<UserByUserListBean>> queryUserByCar(@FieldMap HashMap<String, String> params);

    //修改车辆名称
    @FormUrlEncoded
    @POST("carManage/changeCarName")
    Observable<BaseResponse> changeCarName(@FieldMap HashMap<String, String> params);

    //图片上传
    @Multipart
    @POST("public/imageUpload")
    Observable<BaseResponse<ImgUrlListBean>> imageUpload(@PartMap HashMap<String, String> map, @Part MultipartBody.Part file);


    //修改车辆名称
    @FormUrlEncoded
    @POST("obdReal/refreshCarDate")
    Observable<BaseResponse<RefreshCarBean>> refreshCarData(@FieldMap HashMap<String, String> params);

    //设置默认车辆
    @FormUrlEncoded
    @POST("carManage/changeDefaultCar")
    Observable<BaseResponse> changeDefaultCar(@FieldMap HashMap<String, String> params);
}
