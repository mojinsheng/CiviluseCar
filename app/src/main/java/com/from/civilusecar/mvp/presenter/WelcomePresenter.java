package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.bean.AdvertisementInfo;
import com.from.civilusecar.bean.AdvertisementList;
import com.from.civilusecar.mvp.contract.WelcomeContract;
import com.from.civilusecar.mvp.model.WelcomeModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomePresenter extends BasePresenter<WelcomeContract.View> implements WelcomeContract.Presenter {

    private final WelcomeModel welcomeModel;


    public WelcomePresenter() {
        welcomeModel = new WelcomeModel();
    }
    @Override
    public void advert(HashMap<String, String> params) {
        welcomeModel.advert(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse<AdvertisementList>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<AdvertisementList>>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse<AdvertisementList> advertisementInfoBaseResponse) {
                        mView.returnWelcomeData(advertisementInfoBaseResponse);
                    }
                });



    }
}
