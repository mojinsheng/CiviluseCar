package com.from.civilusecar.mvp.contract;

import com.from.civilusecar.bean.AdvertisementInfo;
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
import com.xslong.xslonglib.base.BaseModel;
import com.xslong.xslonglib.base.BaseView;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public interface MainConstract {
    //Model把视图view和后台耗时的页面，被Presenter调用
    public interface Model extends BaseModel {
        Observable<BaseResponse<FindCarBean>> findCar(HashMap<String, String> params);
        Observable<BaseResponse<LoginEntity>> openlock(HashMap<String, String> params);
        Observable<BaseResponse<CarInfoList>> queryOrgCarDetail(HashMap<String, String> params);
        Observable<BaseResponse<FindCarBean>> doorControl(HashMap<String, String> params);
        Observable<BaseResponse<QueryCarListsBean>> queryCarList(HashMap<String, String> params);
        Observable<BaseResponse<RefState>> getNewBtInfo(HashMap<String, String> params);
        Observable<BaseResponse<RefreshCarBean>> refreshCar(HashMap<String, String> params);





    }

    //把请求到的数据返回到View，在Presenter调用
    public interface View extends BaseView {
        void findCarData(BaseResponse<FindCarBean> data);
        void queryOrgCarDetailData(BaseResponse<CarInfoList> data);
        void doorControlData(BaseResponse<FindCarBean> data);
        void queryCarListData(BaseResponse<QueryCarListsBean> data);
        void getNewBtInfoData(BaseResponse<RefState> data);
        void refreshCarData(BaseResponse<RefreshCarBean> params);

    }

    //存储View中的对象封装到Map中，被View调用
    public interface Presenter {
        void findCarData(HashMap<String, String> params);
        void queryOrgCarDetail(HashMap<String, String> params);
        void doorControl(HashMap<String, String> params);
        void queryCarList(HashMap<String, String> params);
        void getNewBtInfo(HashMap<String, String> params);
        void refreshCar(HashMap<String, String> params);


    }
}
