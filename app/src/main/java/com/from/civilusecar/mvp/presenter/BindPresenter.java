package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.bean.LoginEntity;
import com.from.civilusecar.mvp.contract.BindConstract;
import com.from.civilusecar.mvp.contract.PersonContract;
import com.from.civilusecar.mvp.model.BindModel;
import com.from.civilusecar.mvp.model.LoginModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BindPresenter extends BasePresenter<BindConstract.View> implements BindConstract.Presenter {

    private final BindModel bindModel;

    public BindPresenter() {
        bindModel = new BindModel();
    }

    @Override
    public void bindCar(HashMap<String, String> params) {
        bindModel.bindCar(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.bindData(baseResponse);
                    }
                });
    }

    @Override
    public void unbindCar(HashMap<String, String> params) {
        bindModel.unbindCar(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.unbindData(baseResponse);
                    }
                });
    }
}
