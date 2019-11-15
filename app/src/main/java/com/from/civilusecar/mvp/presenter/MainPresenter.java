package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.bean.BluetoothCmdInfoYidaBean;
import com.from.civilusecar.bean.CarInfo;
import com.from.civilusecar.bean.CarInfoList;
import com.from.civilusecar.bean.FindCarBean;
import com.from.civilusecar.bean.LoginEntity;
import com.from.civilusecar.bean.NearCarInfoBean;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.bean.RefState;
import com.from.civilusecar.bean.RefreshCarBean;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.mvp.contract.MainConstract;
import com.from.civilusecar.mvp.model.LoginModel;
import com.from.civilusecar.mvp.model.MainModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainConstract.View> implements MainConstract.Presenter {

    private final MainModel mainModel;

    public MainPresenter() {
        mainModel = new MainModel();
    }
    @Override
    public void findCarData(HashMap<String, String> params) {
        mainModel.findCar(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentProvider.<BaseResponse<FindCarBean>>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<FindCarBean>>(mContext, mRxManage, Constant.MAINPRESENTER) {
                    @Override
                    protected void _onNext(BaseResponse<FindCarBean> baseResponse) {
                        mView.findCarData(baseResponse);
                    }
                });
    }

    @Override
    public void queryOrgCarDetail(HashMap<String, String> params) {
        mainModel.queryOrgCarDetail(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentProvider.<BaseResponse<CarInfoList>>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<CarInfoList>>(mContext, mRxManage, Constant.MAINPRESENTER) {
                    @Override
                    protected void _onNext(BaseResponse<CarInfoList> baseResponse) {
                        mView.queryOrgCarDetailData(baseResponse);
                    }
                });
    }

    @Override
    public void doorControl(HashMap<String, String> params) {
        mainModel.doorControl(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentProvider.<BaseResponse<FindCarBean>>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<FindCarBean>>(mContext, mRxManage, Constant.MAINPRESENTER) {
                    @Override
                    protected void _onNext(BaseResponse<FindCarBean> baseResponse) {
                        mView.doorControlData(baseResponse);
                    }
                });
    }

    @Override
    public void queryCarList(HashMap<String, String> params) {

        mainModel.queryCarList(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentProvider.<BaseResponse<QueryCarListsBean>>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<QueryCarListsBean>>(mContext, mRxManage, Constant.MAINPRESENTER) {
                    @Override
                    protected void _onNext(BaseResponse<QueryCarListsBean> baseResponse) {
                        mView.queryCarListData(baseResponse);
                    }
                });
    }

    @Override
    public void getNewBtInfo(HashMap<String, String> params) {

        mainModel.getNewBtInfo(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentProvider.<BaseResponse<RefState>>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<RefState>>(mContext, mRxManage, Constant.MAINPRESENTER) {
                    @Override
                    protected void _onNext(BaseResponse<RefState> baseResponse) {
                        mView.getNewBtInfoData(baseResponse);
                    }
                });
    }

    @Override
    public void refreshCar(HashMap<String, String> params) {
        mainModel.refreshCar(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentProvider.<BaseResponse<RefreshCarBean>>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<RefreshCarBean>>(mContext, mRxManage,Constant.MAINPRESENTER) {
                    @Override
                    protected void _onNext(BaseResponse<RefreshCarBean> baseResponse) {
                        mView.refreshCarData(baseResponse);
                    }
                });
    }
}
