package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.bean.LoginEntity;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.bean.UserByUserListBean;
import com.from.civilusecar.mvp.contract.ManagerDriverConstract;
import com.from.civilusecar.mvp.contract.MonitorConstranct;
import com.from.civilusecar.mvp.model.MainModel;
import com.from.civilusecar.mvp.model.ManagerDriverModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManagerDriverPresenter extends BasePresenter<ManagerDriverConstract.View> implements ManagerDriverConstract.Presenter {
    private ManagerDriverModel managerDriverModel;

    public ManagerDriverPresenter() {
        managerDriverModel = new ManagerDriverModel();
    }

    @Override
    public void queryCarLists(HashMap<String, String> params) {
        managerDriverModel.queryCarLists(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse<UserByUserListBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<UserByUserListBean>>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse<UserByUserListBean> baseResponse) {
                        mView.queryCarListsData(baseResponse);
                    }
                });
    }

    @Override
    public void unbindCar(HashMap<String, String> params) {
        managerDriverModel.unbindCar(params).subscribeOn(Schedulers.io())
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
