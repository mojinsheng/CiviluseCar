package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.bean.LoginEntity;
import com.from.civilusecar.mvp.contract.LoginContract;
import com.from.civilusecar.mvp.model.LoginModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    private final LoginModel mLoginModel;

    public LoginPresenter() {
        mLoginModel = new LoginModel();
    }
    @Override
    public void login(HashMap<String, String> params) {
        mLoginModel.login(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse<LoginEntity>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<LoginEntity>>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse<LoginEntity> baseResponse) {
                        mView.returnLoginData(baseResponse);
                    }
                });
    }

    @Override
    public void smsCode(HashMap<String, String> params) {
        mLoginModel.getSMSCode(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.returnSMSData(baseResponse);
                    }
                });
    }
}
