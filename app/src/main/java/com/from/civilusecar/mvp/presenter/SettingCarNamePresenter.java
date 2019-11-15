package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.mvp.contract.PersonContract;
import com.from.civilusecar.mvp.contract.SettingCarNameContract;
import com.from.civilusecar.mvp.model.SettingCarNamrModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingCarNamePresenter extends BasePresenter<SettingCarNameContract.View> implements SettingCarNameContract.Presenter {

    private SettingCarNamrModel settingCarNamrModel=null;
    public  SettingCarNamePresenter(){
        settingCarNamrModel=new SettingCarNamrModel();
    }

    @Override
    public void changeCarName(HashMap<String, String> params) {
        settingCarNamrModel.changeCarName(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.changeCarNameData(baseResponse);
                    }
                });
    }

    @Override
    public void unbindCar(HashMap<String, String> params) {
        settingCarNamrModel.unbindCar(params).subscribeOn(Schedulers.io())
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
