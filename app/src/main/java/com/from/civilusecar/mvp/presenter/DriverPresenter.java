package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.mvp.contract.DriverConstract;
import com.from.civilusecar.mvp.contract.MainConstract;
import com.from.civilusecar.mvp.model.DriverModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DriverPresenter extends BasePresenter<DriverConstract.View> implements DriverConstract.Presenter {

    private DriverModel driverModel=null;

    public DriverPresenter(){
        driverModel=new DriverModel();
    }

    @Override
    public void shareCar(HashMap<String, String> params) {
        driverModel.shareCar(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.shareCarData(baseResponse);
                    }
                });
    }
}
