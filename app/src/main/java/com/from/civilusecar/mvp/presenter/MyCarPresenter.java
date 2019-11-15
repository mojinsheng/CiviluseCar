package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.bean.LoginEntity;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.mvp.contract.MyCarContract;
import com.from.civilusecar.mvp.contract.PersonContract;
import com.from.civilusecar.mvp.model.MyCarModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyCarPresenter extends BasePresenter<MyCarContract.View> implements MyCarContract.Presenter {
    private MyCarModel myCarPresenter=null;

    public MyCarPresenter(){
        myCarPresenter=new MyCarModel();
    }

    @Override
    public void queryCarListData(HashMap<String, String> params) {
        myCarPresenter.queryCarList(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse<QueryCarListsBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<QueryCarListsBean>>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse<QueryCarListsBean> baseResponse) {
                        mView.queryCarListData(baseResponse);
                    }
                });
    }

    @Override
    public void changeDefaultCar(HashMap<String, String> params) {
        myCarPresenter.changeDefaultCar(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.changeDefaultCarData(baseResponse);
                    }
                });
    }
}
